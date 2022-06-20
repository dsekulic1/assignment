package com.example.helloWorld.service;

import com.example.helloWorld.exception.BadRequestException;
import com.example.helloWorld.exception.NotFoundException;
import com.example.helloWorld.model.Language;
import com.example.helloWorld.repository.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public String getTextByLanguage(String language) {
        if (language == null) {
            throw new BadRequestException("Language must not be null!");
        }

        Language existingLanguage = languageRepository.findByLanguage(language);

        if (existingLanguage == null) {
            throw new NotFoundException("Language " + language + " does not exists!");
        }

        return existingLanguage.getMessage();
    }

    public Language addLanguage(Language language) {
        if (languageRepository.existsByLanguage(language.getLanguage())) {
            throw new BadRequestException("Language already exists!");
        }

        return languageRepository.save(language);
    }
}
