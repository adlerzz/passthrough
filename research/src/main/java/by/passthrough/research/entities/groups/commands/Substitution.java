package by.passthrough.research.entities.groups.commands;

import by.passthrough.research.utils.jsoner.JsonField;
import by.passthrough.research.utils.jsoner.Jsonable;
import by.passthrough.research.utils.jsoner.Jsoner;

/**
 * Created by alst0816 on 14.04.2018
 */
public class Substitution implements Jsonable {
    @JsonField
    private SubstitutionType type;

    @JsonField
    private Integer min;

    @JsonField
    private Integer max;

    @JsonField(key = "default")
    private Object defaultValue;

    @JsonField(key = "ref")
    private String reference;

    public Substitution(SubstitutionType type) {
        this.type = type;
        this.min = null;
        this.max = null;
        this.defaultValue = null;
        this.reference = null;
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

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return Jsoner.getInstance().toJSONString(this);
    }
}
