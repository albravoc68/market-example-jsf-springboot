package cl.example.dashboard.beans;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

@ManagedBean
@SessionScope
@Component
public class ProfileBean extends BaseBean {

    @Getter
    private String clientName;

    @Getter
    private String adminName;

    @PostConstruct
    public void init() {
        clientName = getAdmin().getClient().getName();
        adminName = getAdmin().getFullName();
    }

}
