package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.dto.request.UserCreationRequest;
import com.bookstore.onlinebookstore.dto.request.UserUpdateRequest;
import com.bookstore.onlinebookstore.dto.response.UserResponse;
import com.bookstore.onlinebookstore.enums.Role;
import com.bookstore.onlinebookstore.exception.AppException;
import com.bookstore.onlinebookstore.exception.ErrorCode;
import com.bookstore.onlinebookstore.mapper.UserMapper;
import com.bookstore.onlinebookstore.entity.User;
import com.bookstore.onlinebookstore.respository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse addUser(UserCreationRequest request){
        log.info("Adding new user");
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTS);


        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        String role = Role.USER.name();
        user.setRole(role);
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        log.info("In method get user");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(long userId){
        log.info("in method get user by id");
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException(("User not found"))));
    }
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(Long id, UserUpdateRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(("User not found")));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var role = user.getRole();
        user.setRole(role);

        return userMapper.toUserResponse(userRepository.save(user));
    }
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }


}
