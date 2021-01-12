package cl.example.dashboard;

import cl.example.entities.domain.entities.AdminEntity;
import cl.example.entities.domain.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class DashboardSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DashboardConfigurationProperties props;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/resources/**",
                        "/",
                        "/javax.faces.resource/**",
                        "/login.xhtml"
                ).permitAll()
                .anyRequest()
                .hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .successHandler(new RefererRedirectionAuthenticationSuccessHandler())
                .loginPage("/login.xhtml")
                .loginProcessingUrl("/loginProcess")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.xhtml")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(getUserDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return username -> {
            AdminEntity admin = adminRepository.findByUsernameAndClient_Id(username, props.getClientId());
            if (admin == null) {
                throw new UsernameNotFoundException(username);
            }
            Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(username, admin.getPassword(), authorities);
        };
    }

    public class RefererRedirectionAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        public RefererRedirectionAuthenticationSuccessHandler() {
            super();
            setUseReferer(true);
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            if (authentication != null && authentication.isAuthenticated()) {
                String principal = ((User) authentication.getPrincipal()).getUsername();
                AdminEntity admin = adminRepository.findByUsernameAndClient_Id(principal, props.getClientId());

                HttpSession sess = request.getSession();
                sess.setAttribute("admin", admin);
            }
            new DefaultRedirectStrategy()
                    .sendRedirect(request, response, "/index.html");
        }

    }

}
