package com.example.demo.src.diary.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetDiaryRes {
    private int diaryIdx;
    private int userIdx;
    private String title;
    private String content;
    private String tags;
    private int angry;
    private int happy;
    private int sad;
    private String sentiment;
    private String vid;
    private Timestamp createdAt;
}
