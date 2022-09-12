package de.bbht.bpm.service;

import de.bbht.bpm.config.RestProperties;
import de.bbht.bpm.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String API_PATH = "/api/user";
    private static final String REQUEST_PARAM_EMAIL = "email";

    private final RestTemplate restTemplate;
    private final RestProperties restProperties;

    public Optional<UserDto> loadUser(String emailAddress) {
        if (emailAddress != null) {
            final String apiPath = String.format("%1$s%2$s/%3$s", restProperties.getBaseUrl(), API_PATH, emailAddress);
            try {
                ResponseEntity<UserDto> result = restTemplate.getForEntity(apiPath, UserDto.class);
                if (result.getBody() != null) {
                    return Optional.of(result.getBody());
                }
            } catch (RestClientException e) {
                log.error("Error in loadUser", e);
            }
        }
        return Optional.empty();
    }
}
