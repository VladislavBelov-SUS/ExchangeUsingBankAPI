package com.exchangeusingbankapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final String URL_PATTERN = "https://api.privatbank.ua/p24api/exchange_rates?json&date=";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final Calendar calendar = Calendar.getInstance();

    {
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 29);
    }

    private final Date date = calendar.getTime();
    private final int day = 24 * 60 * 60 * 1_000;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }
    @Bean
    public CommandLineRunner start(final ExchangeService exchangeService){
    return args -> {
        for (int i = 0; i < 365; i++) {
            date.setTime(date.getTime()-day);
            URL url = new URL(URL_PATTERN + simpleDateFormat.format(date));
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))){
                StringBuilder beforeJson = new StringBuilder();
                int r;
                while((r = bufferedReader.read())!=-1){
                    beforeJson.append((char) r);
                }
                String json = beforeJson.substring(beforeJson.indexOf("\"baseCurrency\":\"UAH\",\"currency\":\"EUR\""));
                json ="{\"date\":\"" + simpleDateFormat.format(date) + "\"," + json.substring(0, json.indexOf('}') + 1);
                ExchangeRate exchangeRate = objectMapper.readValue(json,ExchangeRate.class);
                System.out.println(exchangeRate);
                exchangeService.add(exchangeRate);
            }
        }
    };
    }
}
