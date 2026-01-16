package com.giuliopastore.BankApp.controllers;

import com.giuliopastore.BankApp.entities.payment.PaymentIntentRequest;
import com.giuliopastore.BankApp.entities.payment.PaymentIntentResponse;
import com.giuliopastore.BankApp.services.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequest request) {
        try {
            PaymentIntentResponse response = stripeService.createPaymentIntent(request);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Errore durante la creazione del payment intent: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore interno del server: " + e.getMessage());
        }
    }
}

