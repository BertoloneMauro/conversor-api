package com.mauro.conversor_api.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ConversorApiService {
    WebClient client = WebClient.create();

    @Value("${api.key}")
    private String apiKey;

    public double convertirMoneda(String from, String to, Double cantidad) {
        try {
            String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + from + "/" + to;
            //GET para obtener los datos en forma de json
            String json = client.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("Respuesta JSON: " + json);

            //Esta parte se encarga de leer el json
            ObjectMapper mapper = new ObjectMapper();

            //Transforma el json en una estructura de arbol, mas leible
            JsonNode root = mapper.readTree(json);

            //aca busco la clave "conversion_rate" en el json
            double tasa = root.path("conversion_rate").asDouble();

            //validación
            String resultado = root.path("result").asText();
            if(!"success".equals(resultado)){
                String error = root.path("error-type").asText();
                throw new RuntimeException("Error al intentar conversión "+ error);
            }


            return cantidad * tasa;


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}