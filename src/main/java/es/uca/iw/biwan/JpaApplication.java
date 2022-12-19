package es.uca.iw.biwan;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@EnableJpaRepositories
@Theme(value = "biwan")
@PWA(name = "Biwan", shortName = "Biwan", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class JpaApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }
}
