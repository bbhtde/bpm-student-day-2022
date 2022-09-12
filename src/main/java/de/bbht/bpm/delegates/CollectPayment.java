package de.bbht.bpm.delegates;

import de.bbht.bpm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("collect_payment")
@RequiredArgsConstructor
public class CollectPayment implements JavaDelegate {

    private final PaymentService paymentService;

    @Override
    public void execute(DelegateExecution execution) {
        // To collect a payment, use the following:
        // boolean paymentService.collectPayment(String iban, Double payment)
    }
}
