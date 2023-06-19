package com.example.app.controller;

import com.example.app.entity.Booking;
import com.example.app.service.BookingService;
import com.example.app.service.LectureService;
import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conference")
public class ConferenceController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private LectureService lectureService;


    @GetMapping("/user/{login}/getbookings")
    public ResponseEntity showBookings(@PathVariable String login) {
        try {
            return new ResponseEntity<>(userService.userLectures(login), HttpStatus.OK);
        } catch(NullPointerException e) {
            return new ResponseEntity<>("Brak loginu '" + login + "' w bazie", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/user/emailupdate")
    public ResponseEntity userEmailChange(@RequestParam String login, String email, String newEmail) {
        try
        {
            if (userService.emailUpdate(login, email, newEmail)) {
                return new ResponseEntity<>("Email został zmieniony pomyślnie.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Użytkownik nie istnieje lub podany nowy email jest niepoprawny.", HttpStatus.NOT_ACCEPTABLE);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Nie znaleziono."  +e, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getusers")
    public ResponseEntity getUser() {
        return new ResponseEntity<>(userService.userList(), HttpStatus.OK);
    }



    @GetMapping("/getschedule")
    public ResponseEntity getSchedule() {
        return new ResponseEntity<>(lectureService.lectureSchedule(), HttpStatus.OK);
    }

    @GetMapping("/booking/delete")
    public ResponseEntity deleteBooking(@RequestBody Booking booking) {
        try {
            booking.setLecture(lectureService.getLecture(booking.getLecture()));
            booking.setUser(userService.getUser(booking.getUser()));
            booking.setId(bookingService.getBooking(booking).getId());
            bookingService.deleteBooking(booking);
            return new ResponseEntity<>("Pomyślnie anulowano rezerwację." , HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Nie znaleziono rezerwacji. " + e, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/booking/create")
    private ResponseEntity createBooking(@RequestBody Booking booking) {
        try {
            booking.setLecture(lectureService.getLecture(booking.getLecture()));
            if(!lectureService.canJoinLecture(booking.getLecture())) {
                return new ResponseEntity<>("Limit miejsc na prelekcji '"
                        + booking.getLecture().getPath() + "' został osiągnięty"
                        , HttpStatus.NOT_ACCEPTABLE);

            }

        switch(userService.validateUser(booking.getUser())) {
                case "exist":
                    booking.setUser(userService.getUser(booking.getUser()));
                    if(!userService.canUserJoinLecture(booking.getUser(), booking.getLecture())) {
                        return new ResponseEntity<>("Nie możesz zapisać się na kolejną prelekcję, która odbywa się o "
                                + booking.getLecture().getTime() + ":00", HttpStatus.NOT_ACCEPTABLE);
                    }

                case "correct":
                    userService.saveUser(booking.getUser());
                    bookingService.saveBooking(booking);
                    bookingService.notification(booking);
                    return new ResponseEntity<>("Dokonano rezerwacji na email: " +
                            booking.getUser().getEmail() +
                            "   login: "+ booking.getUser().getLogin() +
                            " | godzina: " + booking.getLecture().getTime() + ":00" +
                            "   ścieżka: '" + booking.getLecture().getPath() +
                            "'", HttpStatus.CREATED);

                case "email exist":
                    return new ResponseEntity<>("Podany login jest nieprawidłowy", HttpStatus.NOT_ACCEPTABLE);

                case "email incorrect":
                    return new ResponseEntity<>("Email powinien zostać podany w formacie 'example@gmail.com'", HttpStatus.NOT_ACCEPTABLE);
                case "login incorrect":
                    return new ResponseEntity<>("Login powinien składać się z 3-12 znaków(0-9, a-z) ", HttpStatus.NOT_ACCEPTABLE);
                case "login exist":
                    return new ResponseEntity<>("Podany login jest już zajęty", HttpStatus.NOT_ACCEPTABLE);

        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (NullPointerException e) {
            return new ResponseEntity<>("Podane dane nie są prawidłowe", HttpStatus.NOT_ACCEPTABLE);
        }
    }


}
