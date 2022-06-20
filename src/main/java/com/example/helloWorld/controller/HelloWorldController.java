package com.example.helloWorld.controller;

import com.example.helloWorld.model.Language;
import com.example.helloWorld.service.LanguageService;
import com.example.helloWorld.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class HelloWorldController {
    private final LanguageService languageService;
    private final TranslateService translateService;

    @Autowired
    private Environment environment;

    public HelloWorldController(LanguageService languageService, TranslateService translateService) {
        this.languageService = languageService;
        this.translateService = translateService;
    }

    @GetMapping("/hello-rest")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/html/hello")
    public ResponseEntity<ModelAndView> getHelloHtml() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("HelloWorld.html");
        return ResponseEntity.ok(modelAndView);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getByLanguage(@RequestParam String language) throws IOException, InterruptedException {
        if (environment.getActiveProfiles()[0].equals("external")) {
            return ResponseEntity.ok(translateService.translate(language));
        }

        return ResponseEntity.ok(languageService.getTextByLanguage(language));
    }

    @GetMapping("/secure/hello")
    public ResponseEntity<String> secureHello() {
      return ResponseEntity.ok("Hello world!");
    }

    @PostMapping("/secure/language/add")
    public ResponseEntity<Language> addLanguageMessage(@RequestBody Language language) {

        return ResponseEntity.ok(languageService.addLanguage(language));
    }
}
