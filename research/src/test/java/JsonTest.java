import by.passthrough.research.entities.groups.*;
import by.passthrough.research.entities.groups.commands.*;
import by.passthrough.research.utils.jsoner.Jsonable;
import by.passthrough.research.utils.jsoner.Jsoner;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonTest {
    private static Jsonable jsonable;

    @BeforeAll
    public static void init(){
        Group group = new Group();

        group.setId(4512543481L);

        final String roleNames[] = {"TM", "Lead", "Dev"};
        final String someResources[] = {"10.12.14.16", "10.12.14.18", "10.12.14.20"};
        final String anotherResources[] = {"10.112.0.2", "10.112.0.4"};

        /* Create roles from names*/
        List<Role> roles = Stream.of(roleNames)
                .map( Role::new)
                .collect(Collectors.toList());

        List<Role> devRoles = roles.subList(1,3);
        group.getRoles().addAll(roles);

        /*Resources*/
        ArrayList<Resource> resources = group.getResources();
        Resource devservs = new Resource("devservs", someResources);
        Resource cis = new Resource("cis", anotherResources);
        resources.addAll( Arrays.asList(devservs, cis) );

        /*Commands*/
        ArrayList<Command> commands = group.getCommands();
        Command restart = new Command(CommandType.REQUEST, "restart");
        Command up = new Command(CommandType.ACTION, "up");
        commands.addAll( Arrays.asList(restart, up));

        /*Substituions*/
        Substitution min = new Substitution("min", SubstitutionType.NUMBER);
        Substitution server = new Substitution("server", SubstitutionType.RESOURCE);
        min.setDefaultValue(5);
        server.setReference(devservs.getName());

        /*Set values to commands*/
        restart.getFrom().addAll(devRoles);
        restart.getTo().addAll(devRoles);
        restart.getSubstitutions().addAll( Arrays.asList(server, min) );

        up.getFrom().addAll(devRoles);
        up.getTo().addAll(devRoles);
        up.getSubstitutions().add(server);

        JsonTest.jsonable = group;
    }

    @Test
    public void jsonTest(){
        String json = JsonTest.jsonable.toJSONString();
        System.out.println(json);
        Group newGroup = Jsoner.getInstance().parse(json, Group.class);

        String restored = newGroup.toJSONString();
        Assertions.assertArrayEquals(json.getBytes(), restored.getBytes());
    }
}
