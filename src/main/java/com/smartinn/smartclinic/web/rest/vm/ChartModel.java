package com.smartinn.smartclinic.web.rest.vm;

import java.util.List;

/**
 * View Model object for storing the user's key and password.
 */
public class ChartModel {

    private Long id;

    private String name;

    private List<List<Double>> lengthData;

    private List<List<Double>> weightData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<List<Double>> getLengthData() {
        return lengthData;
    }

    public void setLengthData(List<List<Double>> lengthData) {
        this.lengthData = lengthData;
    }

    public List<List<Double>> getWeightData() {
        return weightData;
    }

    public void setWeightData(List<List<Double>> weightData) {
        this.weightData = weightData;
    }
}
