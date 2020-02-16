package com.presentation.demo.config;

import com.presentation.demo.service.user.userdetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_ADMIN;
import static com.presentation.demo.constants.enums.AUTHORITIES.ROLE_USER;

@Configuration
//@EnableOAuth2Client

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//    @Qualifier("oauth2ClientContext")
//    @Autowired
//    private OAuth2ClientContext oauth2ClientContext;
//
//    @Bean
//    @ConfigurationProperties("facebook.client")
//    public AuthorizationCodeResourceDetails facebook()
//    {
//        return new AuthorizationCodeResourceDetails();
//    }
//
//    @Bean
//    @ConfigurationProperties("facebook.resource")
//    public ResourceServerProperties facebookResource()
//    {
//        return new ResourceServerProperties();
//    }
//
//    @Bean
//    public FilterRegistrationBean oAuth2ClientFilterRegistration(OAuth2ClientContextFilter oAuth2ClientContextFilter)
//    {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(oAuth2ClientContextFilter);
//        registration.setOrder(-100);
//        return registration;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bcryptPasswordEncoder());
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(bcryptPasswordEncoder().encode("password"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(bcryptPasswordEncoder().encode("password"))
//                .roles("ADMIN");
    }


//    private Filter ssoFilter()
//    {
//        CompositeFilter filter = new CompositeFilter();//some filters
//        List<Filter> filters = new ArrayList<>();
//
//        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
//        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
//        facebookFilter.setRestTemplate(facebookTemplate);
//        UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
//        tokenServices.setRestTemplate(facebookTemplate);
//        facebookFilter.setTokenServices(tokenServices);
//        filters.add(facebookFilter);
//        filter.setFilters(filters);
//        return filter;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/registration","/about","/index","/","allusers",
                        "/login", "/static/**", "/src/**", "/styles/**","/css/**","/js/**","/activate/*,/password/reset/*,/password/change/*")
                .permitAll()
                .antMatchers("/admin/**")
                .hasAnyAuthority(ROLE_ADMIN.getAuthority())
                .antMatchers("/userpage/**")
                .hasAnyAuthority(ROLE_USER.getAuthority())
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .permitAll();
}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
