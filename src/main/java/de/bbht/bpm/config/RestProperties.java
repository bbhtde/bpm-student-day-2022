package de.bbht.bpm.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("rest.api")
@NoArgsConstructor
@Getter
@Setter
public class RestProperties {
    private String baseUrl;
}
