package com.redhat.training.health.route;

import java.util.concurrent.ExecutorService;
import javax.xml.bind.JAXBContext;
import com.redhat.training.health.model.CovidCase;
import com.redhat.training.health.model.CovidData;
import com.redhat.training.health.model.CovidVaccinations;
import com.redhat.training.health.route.aggregation.CovidDataAggregationStrategy;
import com.redhat.training.health.route.filter.CovidCaseFilter;
import com.redhat.training.health.route.splitter.CovidVaccinationXMLSplitter;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ThreadPoolBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CovidDataRouteBuilder extends RouteBuilder {

    @Value("${sftp.username}")
    private String sftpUsername;
    
    @Value("${sftp.password}")
    private String sftpPassword;

    @Value("${sftp.host}")
    private String sftpHost;

    @Value("${sftp.port}")
    private Integer sftpPort;

    @Override
    public void configure() throws Exception {

        CamelContext context = new DefaultCamelContext();

        ThreadPoolBuilder builder = new ThreadPoolBuilder(context);
        ExecutorService executorService = builder.poolSize(500)
                                            .maxPoolSize(1000)
                                            .maxQueueSize(-1)
                                            .build("customExecutorService");

        
        String casesDataURL="sftp://"+sftpHost+":"+sftpPort+"/upload?" +
        "username="+sftpUsername+"&password=" + sftpPassword +
        "&useUserKnownHostsFile=false&" +
        "fileName=covid-19-all-days-by-country.csv";

        String vaccinationDataURL="sftp://"+sftpHost+":"+sftpPort+"/upload?" +
        "username="+sftpUsername+"&password=" + sftpPassword +
        "&useUserKnownHostsFile=false&" +
        "fileName=covid-19-vaccination.xml";
        from(casesDataURL)
            .transform(body())
            .unmarshal()
            .bindy(BindyType.Csv, CovidCase.class)
            .split(body()).streaming()
            .parallelProcessing()
            .filter().method(CovidCaseFilter.class, "isLastDayOfWeek")
            .wireTap("direct:cases-to-enrich")
            .to("seda:covid-cases");

        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
		JAXBContext jaxbContext = JAXBContext.newInstance(CovidVaccinations.class);
		xmlDataFormat.setContext(jaxbContext);


        from(vaccinationDataURL)
            .unmarshal(xmlDataFormat)
            .split(method(new CovidVaccinationXMLSplitter(), "split")).streaming()
            .executorService(executorService)
            .split(body()).streaming()
            .executorService(executorService)
            .wireTap("direct:vaccinations-to-enrich")
            .to("seda:covid-vaccinations");

        from("direct:cases-to-enrich")
            .pollEnrich("direct:vaccinations-to-enrich", new CovidDataAggregationStrategy())
            .choice()
                .when().simple("${body.countryCode} != null")
                    .marshal(new JacksonDataFormat(CovidData.class))
                    .log("${body}")
                    .to("kafka:covid-data")
            .end();

        from("seda:covid-cases")
            .to("jpa:com.redhat.training.health.model.CovidCase");

        from("seda:covid-vaccinations")
            .to("jpa:com.redhat.training.health.model.CovidVaccination");
    } 
}
