package com.egerton.bugs.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TownWrapper {

    private ArrayList<Town> townWrapper = new ArrayList<>();

    public TownWrapper() {
    }

    public TownWrapper(ArrayList<Town> townWrapper) {
        this.townWrapper = townWrapper;
    }

    public List<Town> getTownWrapper() {
        return townWrapper;
    }

    public void setTownWrapper(ArrayList<Town> townWrapper) {
        this.townWrapper = townWrapper;
    }
}
