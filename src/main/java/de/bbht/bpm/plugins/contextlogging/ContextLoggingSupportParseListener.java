package de.bbht.bpm.plugins.contextlogging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContextLoggingSupportParseListener extends AbstractBpmnParseListener {

  private final ContextLoggingExecutionListener contextLoggingExecutionListener;

  @Override
  public void parseBusinessRuleTask(Element businessRuleTaskElement, ScopeImpl scope,
      ActivityImpl activity) {
    activity.addListener(ExecutionListener.EVENTNAME_END, contextLoggingExecutionListener);
  }

  @Override
  public void parseExclusiveGateway(Element exclusiveGwElement, ScopeImpl scope, ActivityImpl activity) {
    activity.addListener(ExecutionListener.EVENTNAME_END, contextLoggingExecutionListener);
  }

  @Override
  public void parseInclusiveGateway(Element inclusiveGwElement, ScopeImpl scope, ActivityImpl activity) {
    activity.addListener(ExecutionListener.EVENTNAME_END, contextLoggingExecutionListener);
  }

  @Override
  public void parseParallelGateway(Element parallelGwElement, ScopeImpl scope, ActivityImpl activity) {
    activity.addListener(ExecutionListener.EVENTNAME_END, contextLoggingExecutionListener);
  }

  @Override
  public void parseEventBasedGateway(Element eventBasedGwElement, ScopeImpl scope, ActivityImpl activity) {
    activity.addListener(ExecutionListener.EVENTNAME_END, contextLoggingExecutionListener);
  }

  @Override
  public void parseEndEvent(Element endEventElement, ScopeImpl scope, ActivityImpl activity) {
    activity.addListener(ExecutionListener.EVENTNAME_END, contextLoggingExecutionListener);
  }

  @Override
  public void parseServiceTask(Element serviceTaskElement, ScopeImpl scope, ActivityImpl activity) {
    activity.addListener(ExecutionListener.EVENTNAME_END, contextLoggingExecutionListener);
  }

  @Override
  public void parseUserTask(Element userTaskElement, ScopeImpl scope, ActivityImpl activity) {
    activity.addListener(ExecutionListener.EVENTNAME_END, contextLoggingExecutionListener);
  }
}
