package com.example.app.service;


import com.example.app.entity.Booking;
import com.example.app.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void notification(Booking booking) {
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
    public Booking getBooking(Booking booking) {
        return bookingRepository.findAll().stream()
                .filter(booking1 -> booking1.getLecture() == booking.getLecture())
                .filter(booking1 -> booking.getUser() == booking.getUser())
                .findFirst().orElse(null);
    }

    public void deleteBooking(Booking booking) {
        bookingRepository.delete(booking);
    }
}
