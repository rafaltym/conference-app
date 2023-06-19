package com.example.app.service;

import com.example.app.entity.Lecture;
import com.example.app.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;


    //get lecture object(with particular time and path) from database
    public Lecture getLecture(Lecture lecture) {
        return lectureRepository.findAll().stream()
                .filter(lecture1 -> lecture.getTime() == lecture1.getTime())
                .filter(lecture1 -> lecture.getPath() == lecture1.getPath())
                .findAny().orElse(null);
    }

    public boolean canJoinLecture(Lecture lecture) {
        return lecture.getBookingSet().size() < 5;
    }

    public String lectureSchedule() {
        return  "Rozkład prelekcji na Konferencji - 1 czerwiec 2023<br>" +
                "--------------------------------------------------<br>" +
                "10:00 - 11:45 prelekcja I - ścieżki '1', '2', '3'<br>" +
                "11:45 - 12:00 przerwa na kawę<br>" +
                "12:00 - 13:45 prelekcja II - ścieżki '1', '2', '3'<br>" +
                "13:45 - 14:00 przerwa na kawę<br>" +
                "14:00 - 15:45 prelekcja III - ścieżki '1', '2', '3'";
    }


}
