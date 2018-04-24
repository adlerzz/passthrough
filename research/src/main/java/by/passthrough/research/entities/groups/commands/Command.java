package by.passthrough.research.entities.groups.commands;

import by.passthrough.research.entities.groups.Role;

import java.util.ArrayList;

public class Command {
    private CommandType type;
    private ArrayList<Role> from;
    private ArrayList<Role> to;
    private String text;
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
}
