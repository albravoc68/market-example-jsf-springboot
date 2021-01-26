package cl.example.market.beans;

import cl.example.entities.entities.UserEntity;
import cl.example.market.MarketConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Component
public abstract class BaseBean implements Serializable {

    @Autowired
    private HttpSession session;

    @Autowired
    private MarketConfigurationProperties props;

    protected UserEntity getSessionUser() {
        return (UserEntity) session.getAttribute("user");
    }

    protected int getClientId() {
        return props.getClientId();
    }

    protected void showError(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    protected void showInfo(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    protected String getBaseUrl() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = ((HttpServletRequest) context.getRequest());

        return request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

}
