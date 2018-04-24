package by.passthrough.research;

import by.passthrough.research.entities.groups.Group;
import by.passthrough.research.entities.groups.Resource;
import by.passthrough.research.entities.groups.Role;
import by.passthrough.research.entities.groups.commands.Command;
import by.passthrough.research.entities.groups.commands.CommandType;
import by.passthrough.research.entities.groups.commands.Substitution;
import by.passthrough.research.entities.groups.commands.SubstitutionType;
import by.passthrough.research.utils.Logger;
import by.passthrough.research.utils.jsoner.Jsonable;
import by.passthrough.research.utils.jsoner.Jsoner;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.*;

public class Main {
    private static Logger log = Logger.createLogger(Main.class);
    public static void main(String[] args){

        Group group = new Group();

        group.setId(4512543481L);

        /*Roles*/
        ArrayList<Role> roles = group.getRoles();
        Role tm = new Role("TM");
        Role lead = new Role("Lead");
        Role dev = new Role("Dev");
        roles.addAll( Arrays.asList(tm, lead, dev));
        List<Role> devRoles = Arrays.asList(lead, dev);

        /*Resources*/
        ArrayList<Resource> resources = group.getResources();
        Resource devservs = new Resource("devservs", "10.12.14.16", "10.12.14.18", "10.12.14.20");
        Resource cis = new Resource("cis", "10.112.0.2", "10.112.0.4");
        resources.addAll( Arrays.asList(devservs, cis) );

        /*Commands*/
        ArrayList<Command> commands = group.getCommands();
        Command restart = new Command(CommandType.REQUEST, "restart");
        Command up = new Command(CommandType.ACTION, "up");
        commands.addAll( Arrays.asList(restart, up));

        Substitution min = new Substitution("min", SubstitutionType.NUMBER);
        Substitution server = new Substitution("server", SubstitutionType.RESOURCE);
        min.setDefaultValue(5);
        server.setReference(devservs.getName());

        restart.getFrom().addAll(devRoles);
        restart.getTo().addAll(devRoles);
        restart.getSubstitutions().addAll( Arrays.asList(server, min));

        up.getFrom().addAll(devRoles);
        up.getTo().addAll(devRoles);
        up.getSubstitutions().add(server);

        String json = group.toJSONString();
        /*INPUT*/
        System.out.println(json);

        try {
            Group newGroup = Jsoner.getInstance().parse(json, Group.class);

            /*OUTPUT*/
            System.out.println( newGroup.toJSONString() );
        } catch (JsonSyntaxException e){
            log.error(e);
        }

    }

}
