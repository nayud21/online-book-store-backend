package com.bookstore.onlinebookstore.mapper;

import com.bookstore.onlinebookstore.dto.request.UserCreationRequest;
import com.bookstore.onlinebookstore.dto.request.UserUpdateRequest;
import com.bookstore.onlinebookstore.dto.response.UserResponse;
import com.bookstore.onlinebookstore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
