package de.bbht.bpm.delegates;

import de.bbht.bpm.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("make_reservation")
@RequiredArgsConstructor
public class MakeReservation implements JavaDelegate {

    private final CourseService courseService;

    @Override
    public void execute(DelegateExecution execution) {
        // to make a reservation, use the following:
        // boolean courseService.makeReservation(String courseNumber, String emailAddress);
    }
}
