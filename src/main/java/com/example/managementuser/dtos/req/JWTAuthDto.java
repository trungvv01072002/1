package com.example.managementuser.dtos.req;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JWTAuthDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

}