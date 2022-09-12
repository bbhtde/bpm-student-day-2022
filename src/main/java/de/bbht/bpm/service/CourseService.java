package de.bbht.bpm.service;

import de.bbht.bpm.config.RestProperties;
import de.bbht.bpm.dto.CourseInformationDto;
import de.bbht.bpm.dto.ReservationDto;
import de.bbht.bpm.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private static final String API_PATH = "/api/course";

    private final RestTemplate restTemplate;
    private final RestProperties restProperties;

    public Optional<CourseInformationDto> loadCourse(String courseNumber) {
        if (courseNumber != null) {
            final String apiPath = String.format("%1$s%2$s/%3$s", restProperties.getBaseUrl(), API_PATH, courseNumber);
            try {
                ResponseEntity<CourseInformationDto> result = restTemplate.getForEntity(apiPath, CourseInformationDto.class);
                if (result.getBody() != null) {
                    return Optional.of(result.getBody());
                }
            } catch (RestClientException e) {
                // leads to return Optional.empty()
            }
        }
        return Optional.empty();
    }

    public boolean makeReservation(String courseNumber, String emailAddress) {
        if (courseNumber != null && emailAddress != null) {
            final String apiPath = String.format("%1$s%2$s/%3$s", restProperties.getBaseUrl(), API_PATH, courseNumber);
            final ReservationDto reservation = new ReservationDto(emailAddress);
            try {
                ResponseEntity<Void> result = restTemplate.exchange(apiPath, HttpMethod.PUT, new HttpEntity<>(reservation), Void.class);
                if (result.getStatusCode() == HttpStatus.CREATED) {
                    return true;
                }
            } catch (RestClientException e) {
                // leads also to return false
            }
        }
        return false;
    }

    public boolean cancelReservation(String courseNumber, String emailAddress) {
        if (courseNumber != null && emailAddress != null) {
            final String apiPath = String.format("%1$s%2$s/%3$s/%4$s", restProperties.getBaseUrl(), API_PATH, courseNumber, emailAddress);
            try {
                ResponseEntity<Void> result = restTemplate.exchange(apiPath, HttpMethod.DELETE, null, Void.class);
                if (result.getStatusCode() == HttpStatus.OK) {
                    return true;
                }
            } catch (RestClientException e) {
                // leads also to return false
            }
        }
        return false;
    }
}
