package com.batch.springbatch.config;

import com.batch.springbatch.model.UserInfo;
import com.batch.springbatch.repository.UserInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserInfoConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserInfoRepository userInfoRepository) {
        return args -> {
            UserInfo userInfo = new UserInfo(); //new UserInfo(0,"Lucas","lucas@gmail.com", "123","ROLE_ADMIN");
            userInfo.setName("Lucas");
            userInfo.setEmail("lucas@mail.com");
            userInfo.setRoles("ROLE_ADMIN");
            userInfo.setPassword(passwordEncoder().encode("123"));
            userInfoRepository.save(userInfo);
            userInfo = new UserInfo();
            userInfo.setName("Paulo");
            userInfo.setEmail("paulo@mail.com");
            userInfo.setRoles("ROLE_USER");
            userInfo.setPassword(passwordEncoder().encode("123"));
            userInfoRepository.save(userInfo);
        };
    }

    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}