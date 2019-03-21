package com.cezary.projectboard.service;

import com.cezary.projectboard.domain.User;
import com.cezary.projectboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUSer) {
        newUSer.setPassword(bCryptPasswordEncoder.encode(newUSer.getPassword()));
        //Username has to be unique

        //Make sure that password and confirmPassword match

        //We dont persist or show the confirm password
        return userRepository.save(newUSer);
    }
}