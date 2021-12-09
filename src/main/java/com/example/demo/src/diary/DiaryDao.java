package com.example.demo.src.diary;


import com.example.demo.src.diary.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DiaryDao {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    // Diary생성
    public int createDiary(PostDiaryReq postDiaryReq) {
        String createDiaryQuery = "insert into Diary (userIdx, title, content,tags,angry, happy, sad, sentiment, vid) VALUES (?,?,?,?,?,?,?,?,?)";
        Object[] createDiaryParams = new Object[]{postDiaryReq.getUserIdx(), postDiaryReq.getTitle(),postDiaryReq.getContent(),postDiaryReq.getTags(),postDiaryReq.getAngry(),postDiaryReq.getHappy(),postDiaryReq.getSad(),postDiaryReq.getSentiment(),postDiaryReq.getVid()};
        this.jdbcTemplate.update(createDiaryQuery, createDiaryParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }



    // Diary정보 변경
    public int modifyDiaryContent(Diary diary) {
        String modifyDiaryContentQuery = "update Diary set title=?, content = ?, tags=?, angry=?,happy=?, sad=?, sentiment=?, vid=?  where diaryIdx = ? ";
        Object[] modifyDiaryContentParams = new Object[]{diary.getTitle(), diary.getContent(), diary.getTags(), diary.getAngry(), diary.getHappy(),diary.getSad(),diary.getSentiment(), diary.getVid(),diary.getDiaryIdx()};

        return this.jdbcTemplate.update(modifyDiaryContentQuery, modifyDiaryContentParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 특정 Diary 삭제
    public int deleteDiary(int diaryIdx) {
        String deleteDiaryQuery = "delete from Diary where diaryIdx = ? ";
        int deleteDiaryParams = diaryIdx;
        return this.jdbcTemplate.update(deleteDiaryQuery, deleteDiaryParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
    // 해당 유저의 Diary들 정보 조회
    public List<GetDiaryRes> getDiariesByUserIdx(int userIdx) {
        String getDiariesByUserIdxQuery = "select * from Diary where userIdx =?";
        int getDiariesByUserIdxParams = userIdx;
        return this.jdbcTemplate.query(getDiariesByUserIdxQuery,
                (rs, rowNum) -> new GetDiaryRes(
                        rs.getInt("diaryIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("tags"),
                        rs.getInt("angry"),
                        rs.getInt("happy"),
                        rs.getInt("sad"),
                        rs.getString("sentiment"),
                        rs.getString("vid"),
                        rs.getTimestamp("createdAt")),
                getDiariesByUserIdxParams);
    }

    // 해당 diaryIdx를 갖는 Diary조회
    public GetDiaryRes getDiary(int diaryIdx) {
        String getDiaryQuery = "select * from Diary where diaryIdx = ?";
        int getDiaryParams = diaryIdx;
        return this.jdbcTemplate.queryForObject(getDiaryQuery,
                (rs, rowNum) -> new GetDiaryRes(
                        rs.getInt("diaryIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("tags"),
                        rs.getInt("angry"),
                        rs.getInt("happy"),
                        rs.getInt("sad"),
                        rs.getString("sentiment"),
                        rs.getString("vid"),
                        rs.getTimestamp("createdAt")),
                getDiaryParams);
    }



}
