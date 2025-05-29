package com.mauro.conversor_api.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConversorFxController {


    @FXML
    public Label labelResultado;
    @FXML
    private ComboBox<String> comboBoxDe;
    @FXML
    private ComboBox<String> comboBoxA;
    @FXML
    private TextField textFieldCantidad;
    @FXML
    private Button botonConvertir;




    @FXML
    public void initialize() {
        // las 10 monedas mas fuertes del mundo... y el peso argnetino
        comboBoxA.getItems().addAll("USD", "ARS","EUR","JPY","GBP","CNY","CHF","CAD","AUD","INR","BRL");

        comboBoxDe.getItems().addAll("USD", "ARS","EUR","JPY","GBP","CNY","CHF","CAD","AUD","INR","BRL");

        botonConvertir.setOnAction(e -> convertir());

    }
    @FXML
    private void convertir() {
        String from = comboBoxDe.getValue();
        String to = comboBoxA.getValue();
        String cantidadTexto = textFieldCantidad.getText();

        try {
            double cantidad = Double.parseDouble(cantidadTexto);

            // Construcción de URL para llamada al backend
            String url = "http://localhost:8080/api/convertir?from=" + from + "&to=" + to + "&cantidad=" + cantidad;

            // Realizamos la petición HTTP
            URL apiUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) apiUrl.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder respuesta = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    respuesta.append(inputLine);
                }
                in.close();

                double resultado = Double.parseDouble(respuesta.toString());
                labelResultado.setText("Resultado: $" + resultado);
                System.out.println("Resultado: " + resultado);
            } else {
                System.out.println("Error en la solicitud: " + responseCode);
            }

        } catch (NumberFormatException e) {
            System.out.println("Cantidad inválida.");
        } catch (IOException e) {
            System.out.println("Error de conexión con el backend.");
            e.printStackTrace();
        }


    }}

