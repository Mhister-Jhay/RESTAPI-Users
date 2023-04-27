package com.users.service.impl;

import com.users.domain.Roles;
import com.users.domain.Users;
import com.users.dto.PageResponse;
import com.users.dto.UserResponse;
import com.users.dto.request.UpdateRequest;
import com.users.dto.request.UserRequest;
import com.users.exceptions.AlreadyExistException;
import com.users.exceptions.NotFoundException;
import com.users.repository.RoleRepository;
import com.users.repository.UserRepository;
import com.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest){
        if(userRepository.existsByUsernameOrEmail(userRequest.getUsername(),userRequest.getEmail())){
            throw new AlreadyExistException("User Already Exist, Please choose a different username or Email");
        }
        Set<Roles> roles = new HashSet<>();
        Users users = Users.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
        if(userRequest.getUsername().startsWith("admin")){
            Roles role1 = roleRepository.findByRole("ADMIN");
            Roles role2 = roleRepository.findByRole("USER");
            roles.add(role1);
            roles.add(role2);
        }else{
            Roles role = roleRepository.findByRole("USER");
            roles.add(role);
        }
        users.setRoles(roles);
        return mapToUserResponse(userRepository.save(users));
    }

    @Override
    public PageResponse getAllUsers(int pageNo, int pageSize){
        Page<Users> usersPage = userRepository.findAll(PageRequest.of(pageNo,pageSize));
        List<UserResponse> userResponseList = usersPage.getContent().stream().map(this::mapToUserResponse)
                .toList();
        return PageResponse.builder()
                .content(userResponseList)
                .pageNo(usersPage.getNumber())
                .pageSize(usersPage.getSize())
                .totalElements(usersPage.getTotalElements())
                .totalPages(usersPage.getTotalPages())
                .last(usersPage.isLast())
                .build();
    }
    @Override
    public PageResponse getAllUsersByRole(int pageNo, int pageSize, String role){
        String updatedRole = role.toUpperCase();
        if(!roleRepository.existsByRole(updatedRole)){
            throw new NotFoundException("Role with name ("+role+") not found");
        }
        Page<Users> usersPage;
        if(updatedRole.equals("ADMIN")){
            Roles roles1 = Roles.builder().role(updatedRole).build();
          usersPage = userRepository.findAllByRolesContaining(roles1,PageRequest.of(pageNo,pageSize));
        }else{
            Roles roles1 = Roles.builder().role("ADMIN").build();
           usersPage = userRepository.findAllByRolesNotContaining(roles1,PageRequest.of(pageNo,pageSize));
        }

        List<UserResponse> userResponseList = usersPage.getContent().stream().map(this::mapToUserResponse)
                .toList();
        return PageResponse.builder()
                .content(userResponseList)
                .pageNo(usersPage.getNumber())
                .pageSize(usersPage.getSize())
                .totalElements(usersPage.getTotalElements())
                .totalPages(usersPage.getTotalPages())
                .last(usersPage.isLast())
                .build();
    }

    @Override
    public UserResponse editUserDetails(Long userId, UpdateRequest updateRequest){
        Optional<Users> optionalUsers = userRepository.findById(userId);
        if(optionalUsers.isEmpty()){
            throw new NotFoundException("User with id ("+userId+") not found");
        }
        Users users = optionalUsers.get();
        users.setFirstName(updateRequest.getFirstName());
        users.setLastName(updateRequest.getLastName());
        return mapToUserResponse(userRepository.save(users));
    }
    @Transactional
    @Override
    public String deleteUser(Long userId){
        Optional<Users> optionalUsers = userRepository.findById(userId);
        if(optionalUsers.isEmpty()){
            throw new NotFoundException("User with id ("+userId+") not found");
        }
        Users users = optionalUsers.get();
        users.setRoles(null);
        userRepository.delete(users);
        return "User Deleted Successfully";
    }

    private UserResponse mapToUserResponse(Users users){
        return modelMapper.map(users,UserResponse.class);
    }
}
