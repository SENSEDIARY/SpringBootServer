package com.example.demo.src.diary;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.diary.model.GetDiaryRes;
import com.example.demo.src.diary.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service    // [Business Layer에서 Service를 명시하기 위해서 사용] 비즈니스 로직이나 respository layer 호출하는 함수에 사용된다.
// [Business Layer]는 컨트롤러와 데이터 베이스를 연결

public class DiaryProvider {


    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final DiaryDao diaryDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public DiaryProvider(DiaryDao diaryDao) {
        this.diaryDao = diaryDao;
    }
    // ******************************************************************************



    // 해당 유저의 Diary들 정보 조회
    public List<GetDiaryRes> getDiariesByUserIdx(int userIdx) throws BaseException {
        try {
            List<GetDiaryRes> getDiarysRes = diaryDao.getDiariesByUserIdx(userIdx);
            return getDiarysRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetDiaryRes getDiary(int diaryIdx) throws BaseException {
        try {
            GetDiaryRes getDiaryRes = diaryDao.getDiary(diaryIdx);
            return getDiaryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
