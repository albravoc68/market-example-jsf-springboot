package cl.example.market.beans;

import cl.example.entities.domain.entities.ClientEntity;
import cl.example.entities.domain.repositories.ClientRepository;
import cl.example.market.MarketConfigurationProperties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@ApplicationScope
public class MarketBean {

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

}
