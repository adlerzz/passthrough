package by.passthrough.research.entities.groups;

import by.passthrough.research.utils.jsoner.JsonField;
import by.passthrough.research.utils.jsoner.Jsonable;

/**
 * Created by alst0816 on 13.04.2018
 */
public class Role implements Jsonable {
    @JsonField
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role() {
        this(null);
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString(){
        //return Jsoner.getInstance().toJSONString(this);
        return "\"" + roleName + "\"";
    }
}
