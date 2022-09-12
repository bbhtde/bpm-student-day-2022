package de.bbht.bpm.delegates;

import de.bbht.bpm.common.ProcessVariableNames;
import de.bbht.bpm.dto.CourseInformationDto;
import de.bbht.bpm.dto.UserDto;
import de.bbht.bpm.service.CourseService;
import de.bbht.bpm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component("check_prerequisites")
public class CheckPrerequisites implements JavaDelegate {

    private final UserService userService;
    private final CourseService courseService;

    @Override
    public void execute(DelegateExecution execution) {
        final String emailAddress = (String) execution.getVariable(ProcessVariableNames.EMAIL_ADDRESS);
        final String courseNumber = (String) execution.getVariable(ProcessVariableNames.COURSE_NUMBER);

        boolean prerequisitesMet = false;

        if (emailAddress != null && courseNumber != null) {
            final Optional<UserDto> user = userService.loadUser(emailAddress);
            final Optional<CourseInformationDto> courseInformation = courseService.loadCourse(courseNumber);

            if (user.isPresent() && courseInformation.isPresent()) {
                execution.setVariable(ProcessVariableNames.IBAN, user.get().getIban());
                execution.setVariable(ProcessVariableNames.PRICING, courseInformation.get().getPricing());

                prerequisitesMet = true;
            }
        }

        execution.setVariable(ProcessVariableNames.PREREQUISITES_MET, prerequisitesMet);
    }
}
