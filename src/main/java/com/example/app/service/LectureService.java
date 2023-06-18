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
}
