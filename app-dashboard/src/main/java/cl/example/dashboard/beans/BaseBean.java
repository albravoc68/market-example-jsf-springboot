package cl.example.dashboard.beans;

import cl.example.entities.domain.entities.AdminEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Component
public abstract class BaseBean implements Serializable {

    @Autowired
    private HttpSession session;

    protected AdminEntity getAdmin() {
        return (AdminEntity) session.getAttribute("admin");
    }

    protected void showError(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    protected void showInfo(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
