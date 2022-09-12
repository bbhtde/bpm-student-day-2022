package de.bbht.bpm.plugins.kontextlogging;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public class KontextLoggingExecutionListener implements ExecutionListener {

    public void notify(DelegateExecution execution) {
        log.info(String.format("Prozesskontext von %s:", execution.getCurrentActivityName()));

        execution.getVariables().keySet().stream()
                .forEach(key -> log.info(
                        String.format("- %s = %s", key, execution.getVariable(key))));
    }
}
