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
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @PostConstruct
    private void postConstruct() {
        User user = userRepository.findUserByUserName("admin")
                .orElse(null);
        if (user == null) {
            User savedUser = userRepository.save(User.builder()
                    .email("admin")
                    .password("admin")
                    .fullName("admin")
                    .userName("admin")
                    .build());
            roleRepository.save(Role.builder().user(savedUser).name(RoleEnum.ROLE_ADMIN).build());
        }
    }


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByUserNameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email")));
        Optional<Role> role = Optional.ofNullable(roleRepository.findByUserId(user.get().getId())
                .orElseThrow(() -> new UsernameNotFoundException("Role not exists by UserId")));
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(role.get().getName().name()));

        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.get().getPassword(),
                authorities
        );
    }
}