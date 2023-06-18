package com.example.app.controller;

import com.example.app.entity.Booking;
import com.example.app.entity.User;
import com.example.app.service.BookingService;
import com.example.app.service.LectureService;
import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private LectureService lectureService;

    /*
    @PostMapping("/user")
    private ResponseEntity createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity("New user created" + " " + user.getLogin(), HttpStatus.CREATED);
    }
    */

    @PostMapping("/booking")
    private ResponseEntity createBooking(@RequestBody Booking booking) {
        booking.setLecture(lectureService.getLecture(booking.getLecture()));
        userService.saveUser(booking.getUser());
        bookingService.saveBooking(booking);
        return new ResponseEntity("Zapisano pomyślnie. login: "+ booking.getUser().getLogin()
                + " | godzina: " + booking.getLecture().getTime()
                + " | wybrana ścieżka: " + booking.getLecture().getPath()
                , HttpStatus.CREATED);
    }


}
