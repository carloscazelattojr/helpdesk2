package br.com.carlosjunior.authserviceapi.services;

import br.com.carlosjunior.authserviceapi.repositories.UserRepository;
import br.com.carlosjunior.authserviceapi.security.dtos.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        final var entity = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return UserDetailsDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .userName(entity.getEmail())
                .password(entity.getPassword())
                .authorities(entity.getProfiles().stream()
                        .map(item -> new SimpleGrantedAuthority(item.getDescription()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
