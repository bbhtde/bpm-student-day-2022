package de.bbht.bpm.rest;

import de.bbht.bpm.common.ProcessVariableNames;
import de.bbht.bpm.rest.dto.CourseCancellationDto;
import de.bbht.bpm.rest.dto.CourseReservationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
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

    @Operation(description = "Will create a reservation for a given course number and a participants' email address.")
    @ApiResponse(responseCode = "200", description = "Course reservation has been created successfully")
    @ApiResponse(responseCode = "409", description = "Course reservation process for this course number and email address is already active")
    @ApiResponse(responseCode = "422", description = "Request did not contain the information required.")
    @ApiResponse(responseCode = "500", description = "An unexpected exception occurred.")
    @PutMapping(path = "/make-reservation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makeReservation(@RequestBody CourseReservationDto courseReservation) {
        if (courseReservation != null && courseReservation.getCourseNumber() != null && courseReservation.getEmailAddress() != null) {
            final Map<String, Object> processVariables = new HashMap<>();
            processVariables.put(ProcessVariableNames.EMAIL_ADDRESS, courseReservation.getEmailAddress());
            processVariables.put(ProcessVariableNames.COURSE_NUMBER, courseReservation.getCourseNumber());
            List<ProcessInstance> processInstancesForEmailAddress = runtimeService
                    .createProcessInstanceQuery()
                    .variableValueEquals(ProcessVariableNames.EMAIL_ADDRESS, courseReservation.getEmailAddress())
                    .processInstanceBusinessKey(courseReservation.getCourseNumber())
                    .list();
            if (processInstancesForEmailAddress != null && !processInstancesForEmailAddress.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            try {
                final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("course_reservation", courseReservation.getCourseNumber(), processVariables);
                return new ResponseEntity<>(processInstance.getId(), HttpStatus.CREATED);
            } catch (ProcessEngineException exception) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(description = "Will cancel a course and message all processes for the given course number to be cancelled.")
    @ApiResponse(responseCode = "200", description = "Course instances have been cancelled successfully")
    @ApiResponse(responseCode = "422", description = "Request did not contain the information required.")
    @ApiResponse(responseCode = "500", description = "An unexpected exception occurred.")
    @PostMapping(path = "/cancel-course", consumes = MediaType.APPLICATION_JSON_VALUE)
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
