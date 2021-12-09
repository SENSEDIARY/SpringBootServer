package com.example.demo.src.diary;

import com.example.demo.src.user.model.GetUserRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.diary.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/diaries")
public class DiaryController {
    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final DiaryProvider diaryProvider;
    @Autowired
    private final DiaryService diaryService;
    @Autowired
    private final JwtService jwtService;

    public DiaryController(DiaryProvider diaryProvider, DiaryService diaryService, JwtService jwtService) {
        this.diaryProvider = diaryProvider;
        this.diaryService = diaryService;
        this.jwtService = jwtService;
    }
    // ******************************************************************************



    // 다이어리 생성
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<PostDiaryRes> createDiary(@PathVariable("userIdx") int userIdx,@RequestBody PostDiaryReq postDiaryReq) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx!= userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if (postDiaryReq.getTitle() == null) {
                return new BaseResponse<>(Diary_Error);
            }
            if (postDiaryReq.getContent() == null) {
                return new BaseResponse<>(Diary_Error);
            }
            PostDiaryRes postDiaryRes = diaryService.createDiary(postDiaryReq);
            return new BaseResponse<>(postDiaryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // 유저의 전체 다이어리 보기
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetDiaryRes>> getDiariesByUserIdx(@PathVariable("userIdx") int userIdx) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetDiaryRes> getDiariesRes = diaryProvider.getDiariesByUserIdx(userIdx);
            return new BaseResponse<>(getDiariesRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }




    // 특정 다이어리 보기
    @ResponseBody
    @GetMapping("/{userIdx}/{diaryIdx}")
    public BaseResponse<GetDiaryRes> getDiary(@PathVariable("userIdx") int userIdx, @PathVariable("diaryIdx") int diaryIdx) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetDiaryRes getDiaryRes = diaryProvider.getDiary(diaryIdx);
            return new BaseResponse<>(getDiaryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    // 다이어리 정보 변경
    @ResponseBody
    @PatchMapping("/{userIdx}/{diaryIdx}")
    public BaseResponse<String> modifyDiaryContent(@PathVariable("userIdx") int userIdx, @PathVariable("diaryIdx") int diaryIdx, @RequestBody Diary diary) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            Diary diary1 = new Diary(userIdx,diaryIdx , diary.getTitle(),diary.getContent(),diary.getTags(),diary.getAngry(),diary.getHappy(), diary.getSad(), diary.getSentiment(), diary.getVid());
            diaryService.modifyDiaryContent(diary1);

            String result = "다이어리가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 다이어리 삭제
    @ResponseBody
    @DeleteMapping("/{userIdx}/{diaryIdx}")
    public BaseResponse<String> modifyDiary(@PathVariable("userIdx") int userIdx, @PathVariable("diaryIdx") int diaryIdx) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            diaryService.deleteDiary(diaryIdx);

            String result = "다이어리가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
