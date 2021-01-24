package cl.example.market.beans;

import cl.example.entities.entities.ClientEntity;
import cl.example.entities.entities.UserEntity;
import cl.example.entities.repositories.ClientRepository;
import cl.example.market.MarketConfigurationProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@ApplicationScope
public class MarketBean extends BaseBean {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MarketConfigurationProperties props;

    @Getter
    private String marketName;

    @PostConstruct
    public void init() {
        Optional<ClientEntity> client = clientRepository.findById(props.getClientId());
        if (!client.isPresent()) {
            throw new RuntimeException("Unidentified client id.");
        }
        marketName = client.get().getName();
    }

    public String getUserFullName() {
        UserEntity user = getSessionUser();
        return user == null ? null : user.getFullName();
    }

}
