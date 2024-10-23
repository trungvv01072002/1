package com.example.managementuser.mappers;

import com.example.managementuser.dtos.res.UserRes;
import com.example.managementuser.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "user.roles")
    UserRes toUserRes(User user);
    List<UserRes> toUserResList(List<User> users);

}
