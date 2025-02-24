package swen647.umgc.SWEN647ShirtPricingTest.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;


public class PriceRequest {
    @Min(1) @Max(100)
    private int waist;
    @Min(1) @Max(100)
    private int length;
    private boolean collar;
    private boolean sleeves;
    @Pattern(regexp = "USD|EUR", message = "Currency must be 'USD' or 'EUR'")
    private String currency;

    public int getWaist() { return waist; }
    public void setWaist(int waist) { this.waist = waist; }
    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }
    public boolean isCollar() { return collar; }
    public void setCollar(boolean collar) { this.collar = collar; }
    public boolean isSleeves() { return sleeves; }
    public void setSleeves(boolean sleeves) { this.sleeves = sleeves; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}