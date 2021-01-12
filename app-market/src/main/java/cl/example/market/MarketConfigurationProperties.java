package cl.example.market;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@Getter
public class MarketConfigurationProperties {

    @Value("${example.client.id}")
    private Integer clientId;

}
