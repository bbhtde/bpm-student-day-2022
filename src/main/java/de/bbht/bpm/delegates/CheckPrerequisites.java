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

        // to load a user, use the following:
        // Optional<UserDto> userService.loadUser(String emailAddress)

        // to load a course, use the following:
        // Optional<CourseInformationDto> courseService.loadCourse(String courseNumber)
    }
}
