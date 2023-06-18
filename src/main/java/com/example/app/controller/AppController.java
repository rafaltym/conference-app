package com.example.app.controller;

import com.example.app.entity.Booking;
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
        try {
            booking.setLecture(lectureService.getLecture(booking.getLecture()));
            if(!lectureService.canJoinLecture(booking.getLecture())) {
                return new ResponseEntity("Limit miejsc na prelekcji '"
                        + booking.getLecture().getPath() + "' został osiągnięty"
                        , HttpStatus.NOT_ACCEPTABLE);

            }

        switch(userService.validateUser(booking.getUser())) {
                case "exist":
                    booking.setUser(userService.getUser(booking.getUser()));
                    if(!userService.canUserJoinLecture(booking.getUser(), booking.getLecture())) {
                        return new ResponseEntity("Nie możesz zapisać się na prelekcję, która odbywa się o "
                                + booking.getLecture().getTime(), HttpStatus.NOT_ACCEPTABLE);
                    }

                case "correct":
                    userService.saveUser(booking.getUser());
                    bookingService.saveBooking(booking);
                    bookingService.notifyBooking(booking);
                    return new ResponseEntity("Zapisano pomyślnie. login: "+ booking.getUser().getLogin()
                            + " | godzina: " + booking.getLecture().getTime()
                            + " | wybrana ścieżka: " + booking.getLecture().getPath()
                            , HttpStatus.CREATED);

                case "email exist":
                    return new ResponseEntity<>("Podany login jest nieprawidłowy", HttpStatus.NOT_ACCEPTABLE);

                case "email incorrect":
                    return new ResponseEntity<>("Email powinien zostać podany w formacie 'example@gmail.com'", HttpStatus.NOT_ACCEPTABLE);
                case "login incorrect":
                    return new ResponseEntity<>("Login powinien składać się z 3-12 znaków(0-9, a-z) ", HttpStatus.NOT_ACCEPTABLE);
                case "login exist":
                    return new ResponseEntity<>("Podany login jest już zajęty", HttpStatus.NOT_ACCEPTABLE);

        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);

        } catch (NullPointerException e) {
            return new ResponseEntity("Podane dane nie są prawidłowe", HttpStatus.NOT_ACCEPTABLE);
        }
    }


}
