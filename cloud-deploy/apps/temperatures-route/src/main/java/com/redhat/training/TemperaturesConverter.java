package com.redhat.training;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TemperaturesConverter {

    public List<Double> valuesToFahrenheit(List<Double> celsiusValues) {
        return celsiusValues
                .stream()
                .map(v -> toFahrenheit(v))
                .collect(Collectors.toList());
    }

    public Double toFahrenheit(Double celsiusValue) {
        return (celsiusValue * 9 / 5) + 32;
    }
}
