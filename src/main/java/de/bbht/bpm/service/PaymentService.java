package de.bbht.bpm.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean collectPayment(String iban, Double price) {
        return iban != null && price != null && !iban.startsWith("QA");
    }

    public boolean cancelPayment(String iban, Double price) {
        return iban != null && price != null;
    }
}
