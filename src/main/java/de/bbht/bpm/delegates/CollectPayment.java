package de.bbht.bpm.delegates;

import de.bbht.bpm.common.BpmnErrors;
import de.bbht.bpm.common.ProcessVariableNames;
import de.bbht.bpm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("collect_payment")
@RequiredArgsConstructor
public class CollectPayment implements JavaDelegate {

    private final PaymentService paymentService;

    @Override
    public void execute(DelegateExecution execution) {
        final String iban = (String) execution.getVariable(ProcessVariableNames.IBAN);
        final Double payment = (Double) execution.getVariable(ProcessVariableNames.PAYMENT);

        boolean paymentCollected = paymentService.collectPayment(iban, payment);

        if (!paymentCollected) {
            throw new BpmnError(BpmnErrors.PAYMENT_COLLECTION_FAILED);
        }
    }
}
