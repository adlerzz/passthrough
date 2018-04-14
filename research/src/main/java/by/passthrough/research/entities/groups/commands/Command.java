package by.passthrough.research.entities.groups.commands;

import by.passthrough.research.entities.groups.Role;
import by.passthrough.research.utils.jsoner.JsonField;
import by.passthrough.research.utils.jsoner.Jsonable;
import by.passthrough.research.utils.jsoner.Jsoner;

import java.util.ArrayList;

/**
 * Created by alst0816 on 13.04.2018
 */
public class Command implements Jsonable {
    @JsonField
    private CommandType type;

    @JsonField
    private ArrayList<Role> from;

    @JsonField
    private ArrayList<Role> to;

    @JsonField
    private String text;

    @JsonField
    private ArrayList<Substitution> substitutions;

    public Command(CommandType type, String text) {
        this.type = type;
        this.from = new ArrayList<>();
        this.to = new ArrayList<>();
        this.text = text;
        this.substitutions = new ArrayList<>();
    }


    public CommandType getType() {
        return type;
    }

    public ArrayList<Role> getFrom() {
        return from;
    }

    public ArrayList<Role> getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Substitution> getSubstitutions() {
        return substitutions;
    }

    @Override
    public String toString() {
        return Jsoner.getInstance().toJSONString(this);
    }
}
