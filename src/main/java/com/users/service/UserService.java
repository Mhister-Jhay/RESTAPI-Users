package com.users.service;

import com.users.dto.PageResponse;
import com.users.dto.UserResponse;
import com.users.dto.request.UpdateRequest;
import com.users.dto.request.UserRequest;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    PageResponse getAllUsers(int pageNo, int pageSize);

    PageResponse getAllUsersByRole(int pageNo, int pageSize, String role);

    UserResponse editUserDetails(Long userId, UpdateRequest updateRequest);

    @Transactional
    String deleteUser(Long userId);
}
