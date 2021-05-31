package com.smartinn.smartclinic.web.rest.vm;

import java.util.List;

/**
 * View Model object for storing the user's key and password.
 */
public class ChartModel {

    private Long id;

    private String name;

    private List<List<Double>> data;

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

    public List<List<Double>> getData() {
        return data;
    }

    public void setData(List<List<Double>> data) {
        this.data = data;
    }
}
