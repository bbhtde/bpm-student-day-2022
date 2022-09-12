package de.bbht.bpm.delegates;

import de.bbht.bpm.common.BpmnErrors;
import de.bbht.bpm.common.ProcessVariableNames;
import de.bbht.bpm.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
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
        final String emailAddress = (String) execution.getVariable(ProcessVariableNames.EMAIL_ADDRESS);
        final String courseNumber = (String) execution.getVariable(ProcessVariableNames.COURSE_NUMBER);

        boolean result = courseService.makeReservation(courseNumber, emailAddress);

        log.info("Result if makeReservation is " + result);

        if (!result) {
            throw new BpmnError(BpmnErrors.RESERVATION_FAILED);
        }
    }
}
