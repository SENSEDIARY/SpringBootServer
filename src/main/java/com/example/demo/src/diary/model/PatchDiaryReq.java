package com.example.demo.src.diary.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.
public class PatchDiaryReq {
    private int userIdx;
    private String title;
    private String content;
    private String tags;
    private int angry;
    private int happy;
    private int sad;
    private String sentiment;
    private String vid;
}
