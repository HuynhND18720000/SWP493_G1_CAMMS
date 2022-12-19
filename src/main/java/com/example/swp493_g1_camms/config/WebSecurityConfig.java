package com.example.swp493_g1_camms.config;

import com.example.swp493_g1_camms.entities.ERole;
import com.example.swp493_g1_camms.entities.Role;
import com.example.swp493_g1_camms.security.jwt.AuthEntryPointJwt;
import com.example.swp493_g1_camms.security.jwt.AuthTokenFilter;
import com.example.swp493_g1_camms.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,
        jsr250Enabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizeHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizeHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/api/auth/**").permitAll()
                //configure category
                .and().authorizeRequests().antMatchers("/api/category/**")
                .permitAll()
                //configure ExportOrder
                .and().authorizeRequests().antMatchers("/api/export/export-product")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/listProduct")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/create-exportOrder")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/list")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/getOrderDetail")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/confirm")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/cancel")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/editOrder")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/exported")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/getOrderDetailForCancelExportedExportOrder")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/cancelExportedExportOrder")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/export/getDetailCancelExportedExportOrder")
                .permitAll()
                //configure forgot password
                .and().authorizeRequests().antMatchers("/api/forgot_password").permitAll()
                .and().authorizeRequests().antMatchers("/api/check_otp")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/reset_password")
                .permitAll()
                //configure home
                .and().authorizeRequests().antMatchers("/api/home/**")
                .permitAll()
                //configure import order
                .and().authorizeRequests().antMatchers("/api/import/createOrder")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/import/getAllProductByManufacturer/{id}")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/import/list")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/import/getOrderDetail")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/import/confirm")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/import/cancel")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/import/editOrder")
                .permitAll()
                //config manufacturer
                .and().authorizeRequests().antMatchers("/api/manufacturers/**")
                .permitAll()
                //config product
                .and().authorizeRequests().antMatchers("/api/products/**")
                .permitAll()
                //config return order
                .and().authorizeRequests().antMatchers("/api/return/createReturnOrder")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/return/getOrderDetail")
                .permitAll()
                .and().authorizeRequests().antMatchers("/api/return/list")
                .permitAll()
                //config staff
                .and().authorizeRequests().antMatchers("/api/staff/**")
                .permitAll()
                //config stock taking
                .and().authorizeRequests().antMatchers("/api/stockTakingHistory/**")
                .permitAll()
                //config sub category
                .and().authorizeRequests().antMatchers("/api/subCategory/**")
                .permitAll()
                //config user
                .and().authorizeRequests().antMatchers("/api/user/**")
                .permitAll()
                //config warehouse
                .and().authorizeRequests().antMatchers("/api/warehouses/**")
                .permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
