package com.example.app.service;


import com.example.app.entity.Booking;
import com.example.app.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void notifyBooking(Booking booking) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);
        try {
            FileWriter fileWriter =new FileWriter("src/main/resources/powiadomienia.txt", true);
            fileWriter.write( date + " " + time + " " + booking.getUser().toString()
                    + "  MSG: Dokonano rezerwacji na prelekcję '" + booking.getLecture().getPath()
                    +"' na godzinę " + booking.getLecture().getTime() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Wysłanie powiadomienia się nie powiodło.");
        }

    }
}
