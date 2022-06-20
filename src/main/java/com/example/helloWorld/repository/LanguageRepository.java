package com.example.helloWorld.repository;

import com.example.helloWorld.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {
    Language findByLanguage(String language);

    boolean existsByLanguage(String language);
}
