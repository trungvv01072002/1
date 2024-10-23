package com.example.managementuser.services.impl;

import com.example.managementuser.Exceptions.CustomException;
import com.example.managementuser.enums.RoleEnum;
import com.example.managementuser.dtos.domains.UserAndRoleI;
import com.example.managementuser.dtos.req.JWTAuthDto;
import com.example.managementuser.dtos.req.UserLoginReq;
import com.example.managementuser.dtos.req.UserRegisterReq;
import com.example.managementuser.dtos.res.UserRes;
import com.example.managementuser.entities.Role;
import com.example.managementuser.entities.User;
import com.example.managementuser.mappers.UserMapper;
import com.example.managementuser.repositories.RoleRepository;
import com.example.managementuser.repositories.UserRepository;
import com.example.managementuser.services.CustomUserDetailService;
import com.example.managementuser.services.UserService;
import com.example.managementuser.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;


    @Override
    public JWTAuthDto registerUser(UserRegisterReq userRegisterReq) throws Exception {

        boolean isExistMail = userRepository.existsByEmailOrUserName(userRegisterReq.getEmail(), userRegisterReq.getUserName());

        if(isExistMail ) throw new Exception("Email or UserName is Already used with another account");
        User user = User.builder()
                .email(userRegisterReq.getEmail())
                .password(passwordEncoder.encode(userRegisterReq.getPassword()))
                .fullName(userRegisterReq.getFullName())
                .userName(userRegisterReq.getUserName())
                .updated_at(LocalDate.now())
                .created_at(LocalDate.now())
                .build();
        User savedUSer = userRepository.save(user);
        Role role = Role.builder().user(savedUSer).name(RoleEnum.ROLE_USER).build();
        roleRepository.save(role);

        Authentication authentication = authenticate(savedUSer.getUserName(),userRegisterReq.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(),authentication);
        JWTAuthDto jwtAuthDto = new JWTAuthDto();
        jwtAuthDto.setAccessToken(token);
        jwtAuthDto.setRefreshToken(refreshToken);
//        savedUSer.setOneTimeToken(token);
//        userRepository.save(savedUSer);

        return jwtAuthDto;
    }

    @Override
    public JWTAuthDto loginUser(UserLoginReq userLoginReq) {
        Authentication authentication = authenticate(userLoginReq.getEmailOrUsername(),userLoginReq.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(),authentication);
        JWTAuthDto jwtAuthDto = new JWTAuthDto();
        jwtAuthDto.setAccessToken(token);
        jwtAuthDto.setRefreshToken(refreshToken);
        return jwtAuthDto;
    }

//    public UserAndRole getUserByJwt(String jwt){
//            String userName = jwtTokenProvider.getUsername(jwt.substring(7));
//            return userRepository.findUserByUserName(userName);
//    }

    public UserRes getUserByJwt(String jwt){
        String userName = jwtTokenProvider.getUsername(jwt.substring(7));
//        User user = userRepository.findUserByUserName(userName)
//                .orElseThrow(() -> new BadCredentialsException("User not found"));

//        if(user.getOneTimeToken()!=null  && user.getOneTimeToken().equals(jwt.substring(7))){
//            throw new BadCredentialsException("Token is invalid");
//        }
//
//        if(user.getOneTimeToken() == null){
//            user.setOneTimeToken(jwt.substring(7));
//            userRepository.save(user);
//        }


        return userMapper.toUserRes(userRepository.findUserByUserNameV2(userName));


    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserRes> getAllUser(int page, int size, String keySearch) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllUserByFilter(keySearch, keySearch,keySearch,pageable).map(userMapper::toUserRes);
    }

    @Override
    public UserRes updateUser(UserRegisterReq userRegisterReq, Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setFullName(userRegisterReq.getFullName());
            user.get().setEmail(userRegisterReq.getEmail());
            user.get().setPassword(passwordEncoder.encode(userRegisterReq.getPassword()));
            user.get().setUserName(userRegisterReq.getUserName());
            user.get().setUpdated_at(LocalDate.now());
            User savedUser = userRepository.save(user.get());
            return userMapper.toUserRes(savedUser);

        }
        throw new CustomException("User not found", HttpStatus.BAD_REQUEST);

    }

    public JWTAuthDto refreshToken(String refreshToken) throws Exception {
        JWTAuthDto jwtAuthDto = new JWTAuthDto();
        String userName = jwtTokenProvider.getUsername(refreshToken);
//        Optional<Users> user = userRepository.findByUserNameOrEmail(userName,userName);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        if(jwtTokenProvider.validateToken(refreshToken)  && authentication !=null){
            String jwt  = jwtTokenProvider.generateToken(authentication);

            jwtAuthDto.setRefreshToken(refreshToken);
            jwtAuthDto.setAccessToken(jwt);

        }
        return jwtAuthDto;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if(userDetails == null){

            throw new BadCredentialsException("Invalid Username");
        }

        if (!passwordEncoder.matches(password,userDetails.getPassword())){

            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }


}
