package com.redhat.training.messaging;

import footprintservice.GetFootprint;

public class GetFootprintBuilder {

     public GetFootprint getFootprint(String id) {
         GetFootprint request = new GetFootprint();
         request.setID(id);

         return request;
     }
 }