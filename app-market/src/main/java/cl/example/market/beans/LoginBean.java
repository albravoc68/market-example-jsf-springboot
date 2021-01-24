package cl.example.market.beans;

import cl.example.entities.entities.vo.UserVO;
import cl.example.entities.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("view")
public class LoginBean extends BaseBean {

    @Autowired
    private UserService userService;

    @Getter
    private UserVO user;

    @PostConstruct
    public void init() {
        prepareNewUser();
    }

    public void registerUser() {
        userService.saveUser(user);
        prepareNewUser();
        showInfo("¡Nuevo usuario registrado!");
    }

    public void prepareNewUser() {
        user = new UserVO();
        user.setEnable(true);
        user.setClientId(getClientId());
    }

}
