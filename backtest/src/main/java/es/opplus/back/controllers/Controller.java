package es.opplus.back.controllers;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
public class Controller {

    @GetMapping("/error")
    public String error() {
        return "Se ha producido un error";
    }

    @GetMapping("/months")
    @PreAuthorize("hasRole('ROLE_PICASSO_BACK_USER') OR hasRole('ROlE_PICASSO_BACK_ADMIN')")
    public List<String> welcomePage() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object p = authentication.getPrincipal();
        if (p != null && p instanceof KeycloakPrincipal) {

            KeycloakPrincipal principal = (KeycloakPrincipal) p;

            AccessToken token = principal.getKeycloakSecurityContext().getToken();
            System.out.println("Id:               " + token.getId());
            System.out.println("Name:             " + token.getName());
            System.out.println("Username:         " + token.getPreferredUsername());
            System.out.println("Email:            " + token.getEmail());
            System.out.println("Token Hash:       " + token.getAccessTokenHash());
            System.out.println("Scope:            " + token.getScope());
            System.out.println("Fecha nacimiento: " + token.getBirthdate());
            System.out.println("Picture:          " + token.getPicture());
            System.out.println("Atributos:");
            for (Object key : token.getOtherClaims().keySet()) {
                System.out.println("   " + key.toString() + ": " + token.getOtherClaims().get(key).toString());
            }
        }

        return  List.of(
                authentication.getAuthorities().toString()
        );
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ROLE_PICASSO_BACK_ADMIN')")
    public String adminPage() {
        return "Admin page";
    }
}