package com.example.app.service;

import com.example.app.entity.Lecture;
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

    public User getUser(User user) {
        return userRepository.findAll().stream()
                .filter(user1 -> user1.getLogin().equals(user.getLogin()))
                .filter(user1 -> user1.getEmail().equals(user.getEmail()))
                .findFirst().orElse(null);
    }

    public boolean canUserJoinLecture(User user, Lecture lecture) {
        return user.getBookingSet().stream()
                .noneMatch(booking -> booking.getLecture().getTime() == lecture.getTime());
    }

    public String validateUser(User user) {
        if (getUser(user)!=null) {
            return "exist";
        }
        else if(userRepository.findByEmail(user.getEmail()) != null) {
            return "email exist";
        }
        else if (!emailValidation(user.getEmail())) {
            return "email incorrect";
        }else if (!loginValidation(user.getLogin())) {
            return "login incorrect";
        }
        else if (userRepository.findByLogin(user.getLogin()) != null) {
            return "login exist";
        }
            return "correct";

    }

    private boolean emailValidation(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(regexPattern);
    }
    private boolean loginValidation(String login) {
        String regexPattern = "^[a-z0-9_-]{3,12}$";
        return login.matches(regexPattern);
    }





}
