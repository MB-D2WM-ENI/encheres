package fr.eni.ecole.projet.encheres.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class EncheresSecurityConfig {
    private static final String SELECT_USER = "SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo = ?";
    private static final String SELECT_ROLES = "SELECT u.pseudo, r.role FROM UTILISATEURS u INNER JOIN ROLES r ON r.is_admin = u.administrateur WHERE u.pseudo = ? ";

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(SELECT_USER);
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(SELECT_ROLES);
        return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Gère l'accès aux pages de l'application
        http.authorizeHttpRequests(auth -> {
            // Accès à la page d'accueil sans être connecté
            auth.requestMatchers(HttpMethod.GET,"/").permitAll();
            auth.requestMatchers(HttpMethod.POST,"/recherche").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers(HttpMethod.GET,"/inscription").permitAll();
            // Accès aux fichiers CSS et Images
            auth.requestMatchers("/css/*").permitAll();
            auth.requestMatchers("/images/*").permitAll();
            auth.requestMatchers("/js/*").permitAll();
            // Il faut être connecté pour toutes les autres URLs
            auth.anyRequest().authenticated();
        });

        // Permet d'afficher la page de connexion personnalisée plutôt que celle par défaut
        http.formLogin( form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/").permitAll();
        });

        // Permet la déconnexion avec nettoyage et suppression du cookie JSESSIONID et de revenir à la page d'accueil
        http.logout( logout -> {
            logout.invalidateHttpSession(true);
            logout.clearAuthentication(true);
            logout.deleteCookies("JSESSIONID");
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
            logout.logoutSuccessUrl("/");
            logout.permitAll();
        });

        // A ajouter obligatoirement car version 3.4.1 de spring boot + empêche la soumission de form en POST même avec permission ajouté
        http.csrf(secu -> secu.disable());

        return http.build();
    }

}
