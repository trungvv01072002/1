package com.example.managementuser.services;

import com.example.managementuser.entities.Role;
import com.example.managementuser.entities.User;
import com.example.managementuser.enums.RoleEnum;
import com.example.managementuser.repositories.RoleRepository;
import com.example.managementuser.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void postConstruct() {
        User user = userRepository.findUserByUserName("admin")
                .orElse(null);
        if (user == null) {
            User savedUser = userRepository.save(User.builder()
                    .email("admin")
                    .password(passwordEncoder.encode("admin"))
                    .fullName("admin")
                    .userName("admin")
                    .oneTimeToken("admin")
                    .isTokenExpired(true)
                    .build());
            roleRepository.save(Role.builder().user(savedUser).name(RoleEnum.ROLE_ADMIN).build());
        }
    }


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email")));
//        Role role
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (user.get().getRoles() != null) {
            authorities = user.get().getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                    .collect(Collectors.toSet());
        }

        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.get().getPassword(),
                authorities
        );
    }
}