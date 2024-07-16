package br.com.carlosjunior.authserviceapi.controller.impl;

import br.com.carlosjunior.authserviceapi.controller.AuthController;
import br.com.carlosjunior.authserviceapi.security.JWTAuthenticationImpl;
import br.com.carlosjunior.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok().body(
         new JWTAuthenticationImpl(jwtUtils, authenticationConfiguration.getAuthenticationManager()).authenticate(request)
        );
    }

}
