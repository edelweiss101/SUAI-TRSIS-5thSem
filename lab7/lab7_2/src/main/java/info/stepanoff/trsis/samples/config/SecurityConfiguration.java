/*
 * this code is available under GNU GPL v3
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package info.stepanoff.trsis.samples.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Pavel Stepanov
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry
                -> authorizationManagerRequestMatcherRegistry.requestMatchers("/js/**", "/css/**", "/login", "/SecSys", "/company/**").permitAll()
                .anyRequest().authenticated())
            .formLogin(httpSecurityFormLoginConfigurer
                    -> httpSecurityFormLoginConfigurer
                    .loginPage("/login")
                    .usernameParameter("login")
                    .passwordParameter("pass")
                    .permitAll()
                    .defaultSuccessUrl("/SecSys", true)
            )
            .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
            );
            // .sessionManagement(session -> session
            //     .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::none)
            //     .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            // );
        return http.build();
    }

    // @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user")
            .password(passwordEncoder.encode("password"))
            .roles("ALL")
            .and()
            .withUser("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("ALL");
    }

}
