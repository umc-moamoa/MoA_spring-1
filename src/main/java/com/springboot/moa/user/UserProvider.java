package com.springboot.moa.user;

import com.springboot.moa.config.BaseException;
import com.springboot.moa.user.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.springboot.moa.config.BaseResponseStatus.*;

@Service
public class UserProvider {
    private final UserDao userDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    public GetUserInfoRes retrieveUser(long userId) throws BaseException {
        try{
            GetUserInfoRes getUserInfoRes = userDao.selectUser(userId);
            return getUserInfoRes;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserPostRes> retrieveUserPosts(long userId) throws BaseException{
        try{
            List<GetUserPostRes> getUserPosts = userDao.selectUserPosts(userId);

            return getUserPosts;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserPartPostRes> retrieveUserPartPosts(long userId) throws BaseException{
        try{
            List<GetUserPartPostRes> getUserPartPosts = userDao.selectUserPartPosts(userId);

            return getUserPartPosts;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserInterestRes> retrieveUserInterest(long userId) throws BaseException {
        try {
            List<GetUserInterestRes> getUserInterestRes = userDao.selectUserInterest(userId);
            return getUserInterestRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetPointHistoryRes> getPointHistory(long userId) throws BaseException {
        try {
            List<GetPointHistoryRes> getPointHistoryRes = userDao.selectPointHistory(userId);
            return getPointHistoryRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkIdExist(String id) throws BaseException{
        try {
            return userDao.checkUserIdExist(id);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkNickExist(String nick) throws BaseException{
        try {
            return userDao.checkUserNickExist(nick);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
