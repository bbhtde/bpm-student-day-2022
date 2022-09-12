package de.bbht.bpm.delegates;

import de.bbht.bpm.common.ProcessVariableNames;
import de.bbht.bpm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("cancel_payment")
@RequiredArgsConstructor
@Slf4j
public class CancelPayment implements JavaDelegate {

    private final PaymentService paymentService;

    @Override
    public void execute(DelegateExecution execution) {
        log.debug("start CancelPayment#execute");
        final String iban = (String) execution.getVariable(ProcessVariableNames.IBAN);
        final Double payment = (Double) execution.getVariable(ProcessVariableNames.PAYMENT);

        paymentService.cancelPayment(iban, payment);
        log.debug("end CancelPayment#execute");
    }
}
