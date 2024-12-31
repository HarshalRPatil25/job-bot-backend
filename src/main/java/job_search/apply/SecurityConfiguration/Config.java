package job_search.apply.SecurityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class Config {
    @Autowired private UserDetailservice userDetailservice;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer->Customizer.disable()) 
        // Disables CSRF
            .httpBasic(Customizer.withDefaults()) // Enables HTTP Basic Authentication
            .formLogin(Customizer.withDefaults()) // Enables default form login
            .authorizeHttpRequests(request -> 
                request
                    .requestMatchers("/public/**").permitAll() // Allows public endpoints
                   
                    .anyRequest().authenticated() // Requires authentication for other requests
            );
        return http.build();
    }
    

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
         provider.setPasswordEncoder(encoder);
         provider.setUserDetailsService(userDetailservice);
        return provider;
    }

    
    
}
