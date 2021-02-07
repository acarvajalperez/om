package es.opplus.back.controllers;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class Controller {

    @GetMapping("/error")
    public String error() {
        return "Se ha producido un error";
    }

    @GetMapping("/months")
    public String welcomePage() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();

        return  authentication.getAuthorities().toString();

        /*,
                authentication.getCredentials().toString(),
                authentication.getDetails().toString(),
                principal.getKeycloakSecurityContext().toString()
        );
         */
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "Admin page";
    }
}