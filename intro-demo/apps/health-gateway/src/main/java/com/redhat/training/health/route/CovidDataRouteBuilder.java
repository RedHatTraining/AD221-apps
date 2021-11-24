package com.redhat.training.health.route;

import java.net.URI;

import javax.xml.bind.JAXBContext;

import com.redhat.training.health.model.CovidCase;
import com.redhat.training.health.model.CovidData;
import com.redhat.training.health.model.CovidVaccination;
import com.redhat.training.health.model.CovidVaccinationEntity;
import com.redhat.training.health.route.aggregation.CovidDataAggregationStrategy;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CovidDataRouteBuilder extends RouteBuilder {

    @Autowired
	private CovidDataAggregationStrategy covidDataAggregationStrategy;

    @Value("${sftp.username}")
    private String sftpUsername;
    
    @Value("${sftp.password}")
    private String sftpPassword;

    @Override
    public void configure() throws Exception {
        URI casesDataURL = new URIBuilder()
            .setScheme("sftp")
            .setHost("127.0.0.1")
            .setPort(2222)
            .setPath("/")
            .addParameter("username", sftpUsername)
            .addParameter("password", sftpPassword)
            .addParameter("useUserKnownHostsFile", "false")
            .addParameter("fileName", "covid-19-all-days-by-country.csv")
            .build();

        URI vaccinationDataURL = new URIBuilder()
            .setScheme("sftp")
            .setHost("127.0.0.1")
            .setPort(2222)
            .setPath("/")
            .addParameter("username", sftpUsername)
            .addParameter("password", sftpPassword)
            .addParameter("useUserKnownHostsFile", "false")
            .addParameter("fileName", "covid-19-vaccination.xml")
            .build();

        from(casesDataURL.toString())
            .transform(body())
            .unmarshal()
            .bindy(BindyType.Csv, CovidCase.class)
            .split(body())
            .streaming()
            .wireTap("direct:cases-to-kafka")
            .to("jpa:com.redhat.training.health.model.CovidCase");

        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
		JAXBContext jaxbContext = JAXBContext.newInstance(CovidVaccination.class);
		xmlDataFormat.setContext(jaxbContext);

        from(vaccinationDataURL.toString())
            .split().tokenizeXML("record")
            .streaming()
            .unmarshal(xmlDataFormat)
            .wireTap("direct:vaccinations-to-kafka")
            .convertBodyTo(CovidVaccinationEntity.class)
            .to("jpa:com.redhat.training.health.model.CovidVaccinationEntity");

        from("direct:cases-to-kafka")
            .pollEnrich("direct:vaccinations-to-kafka", covidDataAggregationStrategy)
            .choice()
                .when().simple("${body.countryName} != null")
                .marshal(new JacksonDataFormat(CovidData.class))
                .to("kafka:covid-data")
            .end();

        from("direct:logger")
            .log("${body}");
    } 
}
