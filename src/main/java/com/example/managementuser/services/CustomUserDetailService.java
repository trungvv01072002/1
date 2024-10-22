package com.example.managementuser.services;

import com.example.managementuser.entities.Role;
import com.example.managementuser.entities.User;
import com.example.managementuser.repositories.RoleRepository;
import com.example.managementuser.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        System.out.println(usernameOrEmail + "________________");
        Optional<User> user = Optional.ofNullable(userRepository.findUserByUserNameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email")));
        Optional<Role> role = Optional.ofNullable(roleRepository.findByUserId(user.get().getId())
                .orElseThrow(() -> new UsernameNotFoundException("Role not exists by UserId")));
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.get().getPassword(),
                authorities
        );
    }
}