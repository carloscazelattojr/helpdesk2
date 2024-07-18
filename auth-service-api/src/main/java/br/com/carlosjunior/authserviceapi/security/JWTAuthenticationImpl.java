package br.com.carlosjunior.authserviceapi.security;

import br.com.carlosjunior.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.carlosjunior.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(final AuthenticateRequest request) {
        try {
            log.info("Authenticating user: {}", request.email());
            final var authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));

            return buildAuthenticationResponse((UserDetailsDTO) authResult.getPrincipal());
        } catch (BadCredentialsException ex) {
            log.error("Error on authenticate user: {}", request.email());
            throw new BadCredentialsException("Email or password invalid");

        }
    }

    private AuthenticationResponse buildAuthenticationResponse(UserDetailsDTO detailsDTO) {
        log.info("Successfully authentication user : {}", detailsDTO.getUsername());

        final var token = jwtUtils.generateToken(detailsDTO);
        return AuthenticationResponse.builder()
                .token(token)
                .type("Bearer")
                .build();
    }

}
