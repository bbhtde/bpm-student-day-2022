package de.bbht.bpm.delegates;

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
        // Use the following method to cancel a payment:
        // paymentService.cancelPayment(String iban, Double payment);
    }
}
