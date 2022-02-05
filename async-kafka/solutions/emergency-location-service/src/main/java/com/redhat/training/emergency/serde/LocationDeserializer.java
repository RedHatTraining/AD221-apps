package com.redhat.training.emergency.serde;

import com.redhat.training.emergency.model.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class LocationDeserializer implements Deserializer<Location> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Location deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data, "UTF-8"), Location.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
