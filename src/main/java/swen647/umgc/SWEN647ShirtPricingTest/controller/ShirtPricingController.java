package swen647.umgc.SWEN647ShirtPricingTest.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swen647.umgc.SWEN647ShirtPricingTest.model.PriceRequest;
import swen647.umgc.SWEN647ShirtPricingTest.model.PriceResponse;
import swen647.umgc.SWEN647ShirtPricingTest.model.ErrorResponse;


@RestController
@RequestMapping("/api")
@Validated
public class ShirtPricingController {

    @PostMapping("/calculate-price")
    public PriceResponse calculatePrice(@RequestBody @Validated PriceRequest request) {
        if (request.getWaist() < 1 || request.getWaist() > 100 ||
            request.getLength() < 1 || request.getLength() > 100) {
            throw new IllegalArgumentException("Waist and length must be between 1 and 100 inches.");
        }
        
        BigDecimal basePrice = BigDecimal.valueOf(request.getWaist() * request.getLength() * 0.09);
        
        if (request.isCollar()) basePrice = basePrice.add(BigDecimal.valueOf(2));
        if (request.isSleeves()) basePrice = basePrice.add(BigDecimal.valueOf(5));
        
        if (request.getCurrency().equalsIgnoreCase("EUR")) {
            basePrice = basePrice.multiply(BigDecimal.valueOf(0.88));
        }
        
        basePrice = basePrice.setScale(2, RoundingMode.HALF_UP);
        return new PriceResponse(request.getCurrency().equalsIgnoreCase("EUR") ? "€" + basePrice : "$" + basePrice);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}