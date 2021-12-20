package com.redhat.training.health.route.aggregation;

import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class CovidCountryJsonDataAggregationStrategy implements AggregationStrategy {

    private static final String NAME = "name";
    private static final String FLAGS = "flags";
    private static final String PNG = "png";
    private static final String COMMON = "common";
    private static final String FLAG_IMAGE_URL = "flagImageURL";
    private static final String COUNTRY_NAME = "countryName";
    private static final String POPULATION = "population";

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        try {

            String covidDataJson = newExchange.getIn().getBody(String.class);
            String countriesDataJson = oldExchange.getIn().getBody(String.class);

            ObjectMapper objectMapper = new ObjectMapper();

            ArrayNode covidDataRootNode = (ArrayNode) objectMapper.readTree(covidDataJson);
            final ArrayNode countriesDataRootNode = (ArrayNode) objectMapper.readTree(countriesDataJson);

            IntStream.range(0, countriesDataRootNode.size()).forEach(countriesIndex -> {
                JsonNode countriesDataNode = countriesDataRootNode.get(countriesIndex);
                TextNode flagImageURLNode = (TextNode) countriesDataNode.get(FLAGS).get(PNG);
                IntNode populationNode = (IntNode) countriesDataNode.get(POPULATION);

                IntStream.range(0, covidDataRootNode.size()).forEach(covidIndex -> {
                    JsonNode covidDataNode = covidDataRootNode.get(covidIndex);
                    if (covidDataNode.get(COUNTRY_NAME).asText()
                            .equals(countriesDataNode.get(NAME).get(COMMON).asText())){
                        ((ObjectNode) covidDataNode).set(FLAG_IMAGE_URL, flagImageURLNode);
                        ((ObjectNode) covidDataNode).set(POPULATION, populationNode);
                            }
                });
            });

            newExchange.getIn().setBody(covidDataRootNode);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return newExchange;
    }

}
