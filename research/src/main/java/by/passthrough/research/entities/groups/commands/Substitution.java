package by.passthrough.research.entities.groups.commands;

/**
 * Created by alst0816 on 14.04.2018
 */
public class Substitution {
    private String name;
    private SubstitutionType type;
    private Integer min;
    private Integer max;
    private Integer defaultValue;
    private String reference;

    public Substitution(String name, SubstitutionType type) {
        this.name = name;
        this.type = type;
        this.min = null;
        this.max = null;
        this.defaultValue = null;
        this.reference = null;
    }

    public String getName() {
        return name;
    }

    public SubstitutionType getType() {
        return type;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Integer defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

}
