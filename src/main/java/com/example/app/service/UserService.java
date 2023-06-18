package com.example.app.service;

import com.example.app.entity.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }
/*
    public String createUser(String login, String email) {
        if(loginCheck(login) == null && emailCheck(email) != null) {
            return "Podany email jest zajęty";
        }
        else if (!emailValidation(email)) {
            return "Podany email jest niepoprawny";
        }
        else if (loginCheck(login) != null && emailCheck(email) == null) {
            return "Podany login jest już zajęty";
        }
        else if(loginCheck(login) == null && emailCheck(email) == null) {
            userRepository.save();
            return "Podane dane są prawidłowe";
        }
        else {
            return "Podane dane są prawidłowe";
        }
    }

    private User emailCheck(String email) {
        try {
            return userRepository.findAll().stream()
                    .filter(user -> user.getEmail().equals(email))
                    .findFirst().orElse(null);
        } catch(NullPointerException e) {
            return null;
        }

    }

    private boolean emailValidation(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(regexPattern);
    }

    private User loginCheck(String login) {
        try {
            return userRepository.findAll().stream()
                    .filter(user -> user.getLogin().equals(login))
                    .findFirst().orElse(null);
        } catch(NullPointerException e) {
            return null;
        }
        }
*/
}
