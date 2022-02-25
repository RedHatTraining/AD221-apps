package com.redhat.training.environment.soap;

import com.redhat.training.carbonfootprintservice.CarbonFootprintEndpoint;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarbonFootprintBean {
    @Value("${soap.endpoint.wsdl}")
    private String ServiceWsdl;

    @Bean(name = "cxfGetCarbonFootprint")
    public CxfEndpoint buildCxfEndpoint() {
        CxfEndpoint cxf = new CxfEndpoint();
        cxf.setAddress(ServiceWsdl);
        cxf.setServiceClass(CarbonFootprintEndpoint.class);

        return cxf;
    }
}
