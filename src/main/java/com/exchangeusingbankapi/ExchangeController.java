package com.exchangeusingbankapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "first", required = false) String first,
                        @RequestParam(value = "second", required = false) String second,
                        Model model) {
        ExchangeRate er = exchangeService.findByDate(first,second);
        model.addAttribute("er", er);
        return "index";
    }

}
