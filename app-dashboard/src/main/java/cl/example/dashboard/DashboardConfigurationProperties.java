package cl.example.dashboard;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@Getter
public class DashboardConfigurationProperties {

    @Value("${example.client.id}")
    private Integer clientId;

}
