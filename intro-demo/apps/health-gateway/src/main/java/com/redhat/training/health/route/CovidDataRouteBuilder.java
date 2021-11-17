package com.redhat.training.health.route;

import java.net.URI;

import javax.xml.bind.JAXBContext;

import com.redhat.training.health.model.CovidCase;
import com.redhat.training.health.model.CovidVaccination;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CovidDataRouteBuilder extends RouteBuilder {

    @Value("${sftp.username}")
    private String SFTP_USERNAME;
    
    @Value("${sftp.password}")
    private String SFTP_PASSWORD;

    @Override
    public void configure() throws Exception {
        URI casesDataURL = new URIBuilder()
            .setScheme("sftp")
            .setHost("127.0.0.1")
            .setPort(2222)
            .setPath("/")
            .addParameter("username", SFTP_USERNAME)
            .addParameter("password", SFTP_PASSWORD)
            .addParameter("useUserKnownHostsFile", "false")
            .addParameter("fileName", "covid-19-all-days-by-country.csv")
            .build();

        from(casesDataURL.toString())
            .transform(body())
            .unmarshal()
            .bindy(BindyType.Csv, CovidCase.class)
            .split(body())
            .streaming()
            .to("jpa:com.redhat.training.health.model.CovidCase");

        URI vaccinationDataURL = new URIBuilder()
            .setScheme("sftp")
            .setHost("127.0.0.1")
            .setPort(2222)
            .setPath("/")
            .addParameter("username", SFTP_USERNAME)
            .addParameter("password", SFTP_PASSWORD)
            .addParameter("useUserKnownHostsFile", "false")
            .addParameter("fileName", "covid-19-vaccination.xml")
            .build();

        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
		JAXBContext jaxbContext = JAXBContext.newInstance(CovidVaccination.class);
		xmlDataFormat.setContext(jaxbContext);

        from(vaccinationDataURL.toString())
            .split(xpath("//records/record"))
            .streaming()
            .transform(body())
            .unmarshal(xmlDataFormat)
            .to("jpa:com.redhat.training.health.model.CovidVaccination");

            from("direct:logger")
                .log("${body}");
    } 
}
