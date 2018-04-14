package by.passthrough.research;

import by.passthrough.research.entities.groups.Group;
import by.passthrough.research.entities.groups.Resource;
import by.passthrough.research.entities.groups.Role;
import by.passthrough.research.entities.groups.commands.Command;
import by.passthrough.research.entities.groups.commands.CommandType;
import by.passthrough.research.entities.groups.commands.Substitution;
import by.passthrough.research.entities.groups.commands.SubstitutionType;
import by.passthrough.research.utils.CommonUtils;
import by.passthrough.research.utils.jsoner.Jsoner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Group group = new Group();

        group.setId(45L);

        ArrayList<Role> roles = group.getRoles();
        Role tm = new Role("TM");
        Role lead = new Role("Lead");
        Role dev = new Role("Dev");
        roles.add(tm);
        roles.add(lead);
        roles.add(dev);
        List<Role> devRoles = Arrays.asList(lead, dev);

        ArrayList<Resource> resources = group.getResources();
        resources.add(new Resource("devservs", "10.12.14.16", "10.12.14.18", "10.12.14.20"));
        resources.add(new Resource("cis", "10.112.0.2", "10.112.0.4"));

        ArrayList<Command> commands = group.getCommands();
        Command restart = new Command(CommandType.REQUEST, "restart");
        commands.add(restart);
        restart.getFrom().addAll(devRoles);
        restart.getTo().addAll(devRoles);
        restart.getSubstitutions().add(new Substitution(SubstitutionType.INT));

        Command up = new Command(CommandType.ACTION, "up");
        commands.add(up);
        up.getFrom().addAll(devRoles);
        up.getTo().addAll(devRoles);
        up.getSubstitutions().add(new Substitution(SubstitutionType.RESOURCE));

        String json = group.toString();
        System.out.println(json);

        try {
            JSONObject jsonObject = (JSONObject)(new JSONParser()).parse(json);
            Group newGroup = new Group();
            Jsoner.getInstance().fillObject(jsonObject, newGroup);
            System.out.println(newGroup);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
