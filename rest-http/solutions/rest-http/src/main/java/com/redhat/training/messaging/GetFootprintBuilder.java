package com.redhat.training.messaging;

import com.redhat.training.carbonfootprintservice.CarbonFootprintRequest;

public class GetFootprintBuilder {

     public CarbonFootprintRequest getFootprint(String id) {
         CarbonFootprintRequest request = new CarbonFootprintRequest();
         request.setID(id);

         return request;
     }
 }
