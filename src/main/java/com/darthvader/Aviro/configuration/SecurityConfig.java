package com.darthvader.Aviro.configuration;

import com.darthvader.Aviro.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
    @Autowired
    CustomUserDetailService customUserDetailService;

//    @Bean
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests()
//                .requestMatchers("/", "/shop/**", "/register", "/h2-console/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .failureUrl("/login?error = true")
//                .defaultSuccessUrl("/")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
//                .successHandler(googleOAuth2SuccessHandler)
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .and()
//                .exceptionHandling()
//                .and()
//                .csrf()
//                .disable();
//
//        http
//                .headers()
//                .frameOptions()
//                .disable();
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailService);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/", "/shop/**", "/register").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                                .failureUrl("/login?error = true")
                                .defaultSuccessUrl("/")
                                .usernameParameter("email")
                                .passwordParameter("password")
                )
                .oauth2Login((oauth2Login) ->
                        oauth2Login
                                .loginPage("/login")
                                .successHandler(googleOAuth2SuccessHandler)
                )
                .logout((logout) ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                )
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .accessDeniedPage("/errors/access-denied")
                )
                .csrf((csrf) ->
                        csrf
                                .disable()
                )
                .authenticationProvider(authenticationProvider());

        http.headers((headers) ->
                headers
                        .frameOptions((frameOption) ->
                                frameOption
                                        .sameOrigin()
                                        .disable()
                        )
        );

//        http
//                .headers((headers) ->
//                        headers
//                                .frameOptions((frameOption) ->
//                                        frameOption
//                                                .disable()
//                                )
//                );


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailService);
//    }

//    @Bean
//    protected void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .requestMatchers("/resources/**", "/static/**", "/images/**", "/producxtImages/**", "/css/**", "/js/**");
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/js/**"));
    }
}
