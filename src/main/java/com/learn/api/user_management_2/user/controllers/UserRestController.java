package com.learn.api.user_management_2.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.api.user_management_2.user.dtos.UserDTO;
import com.learn.api.user_management_2.user.dtos.UserListDTO;
import com.learn.api.user_management_2.user.dtos.requests.CreateOrUpdateUserDTO;
import com.learn.api.user_management_2.user.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserListDTO> getUserPresentaionList() {
        List<UserDTO> list = userService.getUserPresentationList();
        UserListDTO userListDTO = new UserListDTO();
        list.stream().forEach(e -> userListDTO.getUserList().add(e));
        return ResponseEntity.ok(userListDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateOrUpdateUserDTO createUserDTO) {
        return new ResponseEntity<>(new UserDTO(userService.createUser(createUserDTO)), null, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable("id") Integer id) {
        return new UserDTO(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Integer id, @RequestBody CreateOrUpdateUserDTO updateUserDTO) {
        return new ResponseEntity<>(new UserDTO(userService.updateUser(id, updateUserDTO)), null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // Add or remove a Role on a user
    @PostMapping("/{id}/roles/{roleId}")
    public ResponseEntity<UserDTO> addRole(@PathVariable("id") Integer id, @PathVariable("roleId") Integer roleId) {
        return new ResponseEntity<>(new UserDTO(userService.addRole(id, roleId)), null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/roles/{roleId}")
    public ResponseEntity<UserDTO> removeRole(@PathVariable("id") Integer id, @PathVariable("roleId") Integer roleId) {
        return new ResponseEntity<>(new UserDTO(userService.removeRole(id, roleId)), null, HttpStatus.OK);
    }
}
