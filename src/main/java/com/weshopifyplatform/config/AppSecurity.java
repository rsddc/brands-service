package com.weshopifyplatform.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@Slf4j
public class AppSecurity {
    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(req->{
            try {
//==================================TRY THIS IN DEBUG===============================================
//                AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl =
//                        req.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**");
//                AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry = authorizedUrl.permitAll();
//                var authorizedUrl1 = authorizationManagerRequestMatcherRegistry.anyRequest();
//                AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authenticated = authorizedUrl1.authenticated();
//                var httpSecurity = authenticated.and();
//                CsrfConfigurer csrfConfigurer = httpSecurity.csrf();
//                HttpSecurityBuilder s = csrfConfigurer.disable();
//                s.addFilter().build();
//==================================TRY THIS IN DEBUG===============================================

                        req.anyRequest().authenticated().and().csrf().disable().
                        addFilterBefore(new JwtAuthenticationFilter(jwtAuthenticationService),
                                BasicAuthenticationFilter.class);
            } catch (Exception e) {
                log.info("jwt filter not set successfully");
                throw new RuntimeException(e);
            }
        });
        log.info("====================http req passed=============================");
        return http.build();
    }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
            log.info("########################ignoring the security########################");
            return (web) -> web.ignoring().requestMatchers("/swagger-ui.html","/swagger-ui/**","/v3/api-docs/**");
        }

}
