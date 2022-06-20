package com.example.helloWorld.service;

import com.example.helloWorld.exception.NotFoundException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class TranslateService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, String> countries;

    public TranslateService() {
        if (countries == null) {
            this.countries = new HashMap<>();
            for (Locale locale : Locale.getAvailableLocales()) {
                this.countries.put(locale.getDisplayLanguage(), locale.getLanguage().toLowerCase(Locale.ROOT));
            }
        }
    }

    public String translate(String language) throws IOException, InterruptedException {
        if (!countries.containsKey(language)) {
            throw new NotFoundException("Language " + language + " does not exists!");
        }

        String toIsoCode = countries.get(language);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "435b4185f3msha39104fa407a5fcp11d99ejsncb1b4e4ef800")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("q=Hello%2C%20world!&target="+toIsoCode+"&source=en"))
                .build();


        var response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String res = parse(response.body());
        logger.warn(res);

        return res;
    }

    private String parse(String jsonLine) {
        JsonElement jsonElement = JsonParser.parseString(jsonLine).getAsJsonObject();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject = jsonObject.getAsJsonObject("data");
        JsonArray jsonArray = jsonObject.getAsJsonArray("translations");
        jsonObject = jsonArray.get(0).getAsJsonObject();
        String result = jsonObject.get("translatedText").getAsString();
        return result;
    }
}
