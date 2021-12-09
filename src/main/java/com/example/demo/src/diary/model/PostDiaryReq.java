package com.example.demo.src.diary.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDiaryReq {
    private String userIdx;
    private String title;
    private String content;
    private String tags;
    private int angry;
    private int happy;
    private int sad;
    private String sentiment;
    private String vid;
}
