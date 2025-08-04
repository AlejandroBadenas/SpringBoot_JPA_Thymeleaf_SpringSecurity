package com.example.demo.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Solo manejo de redirección, no inyectes más dependencias aquí    //Obtenemos el rol del usuario y hacemos el if
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_Admin")) {
            response.sendRedirect("/usuario/panelcontrol");
        } else if (roles.contains("ROLE_Usuario")) {
            response.sendRedirect("/usuario/principal");
        } else {
            response.sendRedirect("/usuario/login?error");
        }
    }
}
