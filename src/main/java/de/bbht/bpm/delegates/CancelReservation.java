package de.bbht.bpm.delegates;

import de.bbht.bpm.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("cancel_reservation")
@RequiredArgsConstructor
public class CancelReservation implements JavaDelegate {

    private final CourseService courseService;

    @Override
    public void execute(DelegateExecution execution) {
        // use the following to cancel a reservation
        // courseService.cancelReservation(String courseNumber, String emailAddress)
    }
}
