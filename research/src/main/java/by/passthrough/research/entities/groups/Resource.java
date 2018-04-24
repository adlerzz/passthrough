package by.passthrough.research.entities.groups;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alst0816 on 13.04.2018
 */
public class Resource{
    private String name;
    private ArrayList<String> units;
    private ArrayList<String> statuses;
    private ArrayList<String> values;

    public Resource(String name) {
        this.name = name;
        this.units = new ArrayList<>();
        this.statuses = null;
        this.values = null;
    }

    public Resource(String name, String... units) {
        this.name = name;
        this.units = new ArrayList<>( Arrays.asList(units) );
        this.statuses = null;
        this.values = null;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getUnits() {
        return units;
    }

    public ArrayList<String> getStatuses() {
        return statuses;
    }

    public ArrayList<String> getValues() {
        return values;
    }

}
