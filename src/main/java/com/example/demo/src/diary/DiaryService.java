package com.example.demo.src.diary;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.diary.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import static com.example.demo.config.BaseResponseStatus.*;

@Service    // [Business Layer에서 Service를 명시하기 위해서 사용] 비즈니스 로직이나 respository layer 호출하는 함수에 사용된다.
// [Business Layer]는 컨트롤러와 데이터 베이스를 연결
public class DiaryService {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final DiaryDao diaryDao;
    private final DiaryProvider diaryProvider;


    @Autowired //readme 참고
    public DiaryService(DiaryDao diaryDao, DiaryProvider diaryProvider) {
        this.diaryDao = diaryDao;
        this.diaryProvider = diaryProvider;

    }
    // 다이어리 생성
    public PostDiaryRes createDiary(PostDiaryReq postDiaryReq) throws BaseException {
        try {
            int diaryIdx = diaryDao.createDiary(postDiaryReq);
            return new PostDiaryRes(diaryIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 다이어리 수정(Patch)
    public void modifyDiaryContent(Diary diary) throws BaseException {
        try {
            int result = diaryDao.modifyDiaryContent(diary);
            if (result == 0) {
                throw new BaseException(Diary_Error);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 다이어리 수정(Patch)
    public void deleteDiary(int diaryIdx) throws BaseException {
        try {
            int result = diaryDao.deleteDiary(diaryIdx);
            if (result == 0) {
                throw new BaseException(Diary_Error);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
