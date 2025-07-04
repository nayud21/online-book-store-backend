package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.dto.request.ApiResponse;
import com.bookstore.onlinebookstore.dto.request.UserCreationRequest;
import com.bookstore.onlinebookstore.dto.request.UserUpdateRequest;
import com.bookstore.onlinebookstore.dto.response.UserResponse;
import com.bookstore.onlinebookstore.entity.User;
import com.bookstore.onlinebookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        log.info("Controller: userController");
        ApiResponse<User> apiResponse = new ApiResponse<>();

        return ApiResponse.<UserResponse>builder()
                .result(userService.addUser(request))
                .build();
    }
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}", authentication.getName());
        log.info("Role: {}", authentication.getAuthorities());

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }
    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") long userId){
        return userService.getUser(userId);
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){

        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}
