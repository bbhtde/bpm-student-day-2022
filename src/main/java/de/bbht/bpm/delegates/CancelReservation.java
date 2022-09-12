package de.bbht.bpm.delegates;

import de.bbht.bpm.common.ProcessVariableNames;
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
        final String emailAddress = (String) execution.getVariable(ProcessVariableNames.EMAIL_ADDRESS);
        final String courseNumber = (String) execution.getVariable(ProcessVariableNames.COURSE_NUMBER);

        boolean result = courseService.cancelReservation(courseNumber, emailAddress);
    }
}
