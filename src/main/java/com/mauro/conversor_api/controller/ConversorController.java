package com.mauro.conversor_api.controller;
import com.mauro.conversor_api.service.ConversorApiService;
import javafx.scene.control.Label;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ConversorController {
    private final ConversorApiService conversorApiService;
    public Label labelResultado;

    public ConversorController(ConversorApiService conversorApiService) {
        this.conversorApiService = conversorApiService;
    }

    @GetMapping("/convertir")
    public double convertir(@RequestParam String from, @RequestParam String to, @RequestParam double cantidad)
    {
        return conversorApiService.convertirMoneda(from, to, cantidad);
    }



}
