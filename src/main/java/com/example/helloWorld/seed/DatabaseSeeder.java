package com.example.helloWorld.seed;

import com.example.helloWorld.model.Language;
import com.example.helloWorld.model.Role;
import com.example.helloWorld.model.UserEntity;
import com.example.helloWorld.repository.LanguageRepository;
import com.example.helloWorld.repository.RoleRepository;
import com.example.helloWorld.repository.UserEntityRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Component
public class DatabaseSeeder {
    private final RoleRepository roleRepository;
    private final UserEntityRepository userEntityRepository;
    private final LanguageRepository languageRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(RoleRepository roleRepository, UserEntityRepository userEntityRepository, LanguageRepository languageRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userEntityRepository = userEntityRepository;
        this.languageRepository = languageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void seed(final ContextRefreshedEvent event) {
        seedRoleUserTable();
        seedLanguageTable();
    }

   private void seedRoleUserTable() {
        if(roleRepository.count() == 0 || userEntityRepository.count() == 0) {
            // Create user roles
            var userRole = createRoleIfNotFound(Role.ROLE_USER);
            var adminRole = createRoleIfNotFound(Role.ROLE_ADMIN);

            // Create users
            createUserIfNotFound("user", userRole);
            createUserIfNotFound("admin", adminRole);
        }
    }

    private void seedLanguageTable() {
        if (languageRepository.count() == 0) {
            createLanguageIfNotFound(UUID.fromString("1c7c7786-b086-4a12-87c3-3b6f7e644608"),"English","Hello World");
            createLanguageIfNotFound(UUID.fromString("2c7c7786-b086-4a12-87c3-3b6f7e644608"),"Croatian","Pozdrav svijete");
            createLanguageIfNotFound(UUID.fromString("3c7c7786-b086-4a12-87c3-3b6f7e644608"),"Danish","Hej Verden");
            createLanguageIfNotFound(UUID.fromString("4c7c7786-b086-4a12-87c3-3b6f7e644608"),"Dutch","Hallo Wereld");
            createLanguageIfNotFound(UUID.fromString("5c7c7786-b086-4a12-87c3-3b6f7e644608"),"French","Bonjour monde");
            createLanguageIfNotFound(UUID.fromString("6c7c7786-b086-4a12-87c3-3b6f7e644608"),"German","Hallo Welt");
            createLanguageIfNotFound(UUID.fromString("7c7c7786-b086-4a12-87c3-3b6f7e644608"),"Italian","Ciao mondo");
            createLanguageIfNotFound(UUID.fromString("8c7c7786-b086-4a12-87c3-3b6f7e644608"),"Macedonian","Здраво свету");
            createLanguageIfNotFound(UUID.fromString("9c7c7786-b086-4a12-87c3-3b6f7e644608"),"Norwegian","Hei Verden");
            createLanguageIfNotFound(UUID.fromString("1d7c7786-b086-4a12-87c3-3b6f7e644608"),"Slovenian","Pozdravljen svet");

        }
    }

    @Transactional
    Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);

        if (role == null) {
            role = new Role(name);
            role = roleRepository.save(role);
        }

        return role;
    }

    @Transactional
    UserEntity createUserIfNotFound(final String name, final Role role) {
        UserEntity user = userEntityRepository.findByUsername(name);

        if (user == null) {
            // password is same as name
            user = new UserEntity(name,  passwordEncoder.encode(name));
            user.setRoles(Set.of(role));
            user = userEntityRepository.save(user);
        }

        return user;
    }

    @Transactional
    Language createLanguageIfNotFound(final UUID id, final String languageName, final String message) {
        Language language = languageRepository.findByLanguage(languageName);

        if (language == null) {
            language = new Language(id, languageName, message);
            language = languageRepository.save(language);
        }

        return language;
    }
}
