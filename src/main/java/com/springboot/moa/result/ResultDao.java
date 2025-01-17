package com.springboot.moa.result;

import com.springboot.moa.post.model.GetPostDetailRes;
import com.springboot.moa.result.model.GetResultStatisticsRes;
import com.springboot.moa.result.model.PostDetailResultReq;
import com.springboot.moa.result.model.PostResultReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ResultDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    // 앞 인자는 다른 곳에서 받아올 수 있는 PK (다른 Res 에서 반환 된 값)
    public long insertResults(PostResultReq postResultReq) {
        String insertResultQuery = "INSERT INTO result (post_id, user_id) VALUES (?, ?)";
        Object[] insertResultsParams = new Object[]{postResultReq.getPostId(), postResultReq.getUserId()};
        this.jdbcTemplate.update(insertResultQuery, insertResultsParams);
        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, long.class);
    }

    public long insertResultDetails(long resultId, PostDetailResultReq postDetailResultReq) {
        String insertResultDetailsQuery = "INSERT INTO result_detail (result_id, post_detail_id, result) VALUES (?, ?, ?)";
        Object[] insertResultDetailsParams = new Object[]{resultId,  postDetailResultReq.getPostDetailId(), postDetailResultReq.getResult()};
        this.jdbcTemplate.update(insertResultDetailsQuery, insertResultDetailsParams);
        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, long.class);
    }


    public int selectPostPoint(long postId){
        String selectPostPointQuery = "select point from post where post_id = ? and status = 'ACTIVE'";
        long selectPostPointParam = postId;

        return this.jdbcTemplate.queryForObject(selectPostPointQuery, int.class, selectPostPointParam);
    }

    public List<GetResultStatisticsRes> selectResult(long postDetailId) {

        String selectResultQuery = "select result from result_detail where post_detail_id = ? order by result";
        long selectResultParams = postDetailId;

        return this.jdbcTemplate.query(selectResultQuery,
                (rs, rowNum) -> new GetResultStatisticsRes(
                        rs.getString("result")
                ), selectResultParams);
    }

    // postDetailId가 존재하는지 확인 (질문의 번호가 올바른가)
    public int checkResultPostDetailId (long postDetailId){
        String checkResultPostDetailIdQuery = "select exists(select post_detail_id from post_detail where post_detail_id = ?);";
        long checkResultPostDetailIdParams = postDetailId;
        return this.jdbcTemplate.queryForObject(checkResultPostDetailIdQuery,
                int.class,
                checkResultPostDetailIdParams);
    }

    // 중복 답변 방지 (동일한 유저가 동일한 설문조사에 답변 달지 못하도록)
    public int checkDuplicatedResult(long postId, long userId) {
        String checkDuplicatedResultQuery = "select exists(select post_id, user_id from result where post_id = ? and user_id = ?)";
        Object[] checkDuplicatedResultParams = new Object[]{postId, userId};
        return this.jdbcTemplate.queryForObject(checkDuplicatedResultQuery,
                int.class,
                checkDuplicatedResultParams);
    }
}