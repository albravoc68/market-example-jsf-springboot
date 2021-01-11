package cl.example.dashboard.beans;

import cl.example.dashboard.model.UserDataModel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("view")
public class UsersBean extends BaseBean {

    @Autowired
    @Getter
    private UserDataModel userDM;

    @PostConstruct
    public void init() {
        userDM.setClientId(getClientId());
    }

}
