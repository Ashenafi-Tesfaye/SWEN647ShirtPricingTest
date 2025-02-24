package swen647.umgc.SWEN647ShirtPricingTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import swen647.umgc.SWEN647ShirtPricingTest.model.PriceRequest;



@Controller
public class ViewController {
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("priceRequest", new PriceRequest());
        return "index";
    }
}