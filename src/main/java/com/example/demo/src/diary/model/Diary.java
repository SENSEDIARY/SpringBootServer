package com.example.demo.src.diary.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Diary {
    private int userIdx;
    private int diaryIdx;
    private String title;
    private String content;
    private String tags;
    private float angry;
    private float happy;
    private float sad;
    private String sentiment;
    private String vid;
}
