package com.users.controller;

import com.users.dto.PageResponse;
import com.users.dto.UserResponse;
import com.users.dto.request.UpdateRequest;
import com.users.dto.request.UserRequest;
import com.users.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createNewUser(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userServiceImpl.createUser(userRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<UserResponse> updateUserDetails(@Valid @RequestBody UpdateRequest updateRequest, @PathVariable Long userId){
        return new ResponseEntity<>(userServiceImpl.editUserDetails(userId,updateRequest),HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<PageResponse> getAllUsers(@RequestParam(value = "pageNo",defaultValue = "0") int pageNo,
                                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        return new ResponseEntity<>(userServiceImpl.getAllUsers(pageNo,pageSize),HttpStatus.OK);
    }

    @GetMapping("/users/{role}")
    public ResponseEntity<PageResponse> getAllUsers(@RequestParam(value = "pageNo",defaultValue = "0") int pageNo,
                                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                    @PathVariable String role){
        return new ResponseEntity<>(userServiceImpl.getAllUsersByRole(pageNo,pageSize,role),HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        return new ResponseEntity<>(userServiceImpl.deleteUser(userId),HttpStatus.OK);
    }
}
