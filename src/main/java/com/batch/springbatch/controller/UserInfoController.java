package com.batch.springbatch.controller;

import com.batch.springbatch.model.UserInfo;
import com.batch.springbatch.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UserInfoController {
    private PasswordEncoder passwordEncoder;
    private UserInfoRepository userInfoRepository;
    @PostMapping("/adicionar")
    public String addNewUser(@RequestBody UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User add to database";
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserInfo> listUsers(){
        return userInfoRepository.findAll();
    }
}
