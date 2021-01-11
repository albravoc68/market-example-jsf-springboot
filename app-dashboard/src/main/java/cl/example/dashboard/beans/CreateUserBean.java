package cl.example.dashboard.beans;

import cl.example.dashboard.services.UserService;
import cl.example.entities.domain.entities.vo.UserVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.util.Map;

@Component
@Scope("view")
public class CreateUserBean extends BaseBean {

    @Autowired
    private UserService userService;

    @Getter
    @Setter
    private UserVO user;

    @PostConstruct
    private void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String val = params.get("userId");

        if (!StringUtils.isEmpty(val)) {
            int id = Integer.parseInt(val);
            user = userService.getUserById(id, getClientId());
        } else {
            user = new UserVO();
            user.setClientId(getClientId());
        }
    }

    public void saveUser() {
        try {
            user = userService.saveUser(user);
            showInfo("El usuario se ha guardado exitosamente!");
        } catch (Exception e) {
            showError("Error al guardar el usuario: " + e.getMessage());
        }
    }

}
