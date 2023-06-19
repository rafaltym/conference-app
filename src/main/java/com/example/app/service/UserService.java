package com.example.app.service;

import com.example.app.entity.Booking;
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


    //return String with lectures to which user is enrolled
    public String userLectures(String login) {
        StringBuilder stringBuilder = new StringBuilder();
        int num = 1;
        for (Booking booking: userRepository.findByLogin(login).getBookingSet()){
            stringBuilder.append(num).append(". ").append(booking.getLecture()).append("<br>");
            num += 1;
        }

        return stringBuilder.toString();
    }
    public String userList() {
        StringBuilder stringBuilder = new StringBuilder();
        int num = 1;
        for (User user: userRepository.findAll()){
            stringBuilder.append(num).append(". ").append(user.toString()).append("<br>");
            num += 1;
        }

        return stringBuilder.toString();
    }

    public boolean emailUpdate(String login, String email, String newEmail) {
        if (validateEmail(newEmail) && doesUserExist(login, email)){
            User user = userRepository.findByEmail(email);
            user.setEmail(newEmail);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }

    }
    private boolean doesUserExist(String login, String email) {
        if(userRepository.findByEmail(email).equals(userRepository.findByLogin(login))) {
            return true;
        }
        return false;
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
        else if (!validateEmail(user.getEmail())) {
            return "email incorrect";
        }else if (!loginValidation(user.getLogin())) {
            return "login incorrect";
        }
        else if (userRepository.findByLogin(user.getLogin()) != null) {
            return "login exist";
        }
            return "correct";

    }

    private boolean validateEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(regexPattern);
    }
    private boolean loginValidation(String login) {
        String regexPattern = "^[a-z0-9_-]{3,12}$";
        return login.matches(regexPattern);
    }





}
