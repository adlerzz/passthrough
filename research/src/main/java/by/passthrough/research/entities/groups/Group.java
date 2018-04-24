package by.passthrough.research.entities.groups;

import by.passthrough.research.entities.groups.commands.Command;
import by.passthrough.research.utils.jsoner.Jsonable;

import java.util.ArrayList;

public class Group implements Jsonable{

    private long id;
    private ArrayList<Role> roles;
    private ArrayList<Command> commands;
    private ArrayList<Resource> resources;

    public Group() {
        this.roles = new ArrayList<>();
        this.commands = new ArrayList<>();
        this.resources = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

}
