package com.izec.bank.controller;

import com.izec.bank.domain.User;
import com.izec.bank.dto.UserDTO;
import com.izec.bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user-num/{phoneNumber}")
    UserDTO findUserByPhoneNumber(@PathVariable String phoneNumber) {
        User u = userService.findByNumber(phoneNumber);
        return new UserDTO(u.getFirstname(),
                u.getLastname(),
                u.isActive(),
                u.getEmail(),
                u.getActivationCode(),
                u.getPhoneNumber(),
                u.getRoles());
    }

    @GetMapping("/user-id/{id}")
    UserDTO findUserById(@PathVariable long id) {
        User u = userService.findById(id);
        return new UserDTO(u.getFirstname(),
                u.getLastname(),
                u.isActive(),
                u.getEmail(),
                u.getActivationCode(),
                u.getPhoneNumber(),
                u.getRoles());
    }

    @PostMapping("/new-client")
    ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getFirstname(),
                userDTO.getLastname(),
                userDTO.isActive(),
                userDTO.getEmail(),
                userDTO.getActivationCode(),
                userDTO.getPhoneNumber(),
                userDTO.getRoles());

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    List<UserDTO> findAll() {
        List<User> list = userService.getAll();
        List<UserDTO> result = new ArrayList<>();
        list.forEach(o -> {
            result.add(new UserDTO(o.getFirstname(),
                    o.getLastname(),
                    o.isActive(),
                    o.getEmail(),
                    o.getActivationCode(),
                    o.getPhoneNumber(),
                    o.getRoles()));
        });

        return result;
    }
}
