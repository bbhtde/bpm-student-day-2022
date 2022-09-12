package de.bbht.bpm.rest;

import de.bbht.bpm.common.ProcessVariableNames;
import de.bbht.bpm.rest.dto.CourseCancellationDto;
import de.bbht.bpm.rest.dto.CourseReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class ReservationController {

    private final RuntimeService runtimeService;

    @PutMapping(path = "/make-reservation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makeReservation(@RequestBody CourseReservationDto courseReservation) {
        if (courseReservation != null && courseReservation.getCourseNumber() != null && courseReservation.getEmailAddress() != null) {
            final Map<String, Object> processVariables = new HashMap<>();
            processVariables.put(ProcessVariableNames.EMAIL_ADDRESS, courseReservation.getEmailAddress());
            processVariables.put(ProcessVariableNames.COURSE_NUMBER, courseReservation.getCourseNumber());
            try {
                final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("course_reservation", courseReservation.getCourseNumber(), processVariables);
                return new ResponseEntity<>(processInstance.getId(), HttpStatus.CREATED);
            } catch (ProcessEngineException exception) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PostMapping(path = "/cancel-reservation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> cancelCourse(@RequestBody CourseCancellationDto courseCancellation) {
        if (courseCancellation != null && courseCancellation.getCourseNumber() != null) {
            List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(courseCancellation.getCourseNumber()).list();
            try {
                instances
                        .stream()
                        .map(ProcessInstance::getProcessInstanceId)
                        .forEach(processInstanceId -> runtimeService.createMessageCorrelation("cancel_course_message")
                                .processInstanceId(processInstanceId).correlate());

                return new ResponseEntity<>(HttpStatus.OK);
            } catch (ProcessEngineException exception) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
