package com.smart.dao;

import com.smart.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Henry Gao
 * @date 2020-12-16 - 16:39
 */

@Repository //1. Use Spring annotation to define a DAO
public class UserDao {


    private static final String MATCH_COUNT = " SELECT count(*) FROM t_user where user_name = ? and password = ?";
    private static final String FIND_BY_USERNAME = " SELECT * FROM t_user where user_name = ?";
    private static final String UPDATE_SQL = "UPDATE t_user Set last_visit=?, last_ip=?,credits=? WHERE user_id=?";
    private JdbcTemplate jdbcTemplate;

    @Autowired //auto injects JdbcTemplate bean
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getMatachCount(String userName, String password) {
        return jdbcTemplate.queryForObject(MATCH_COUNT, new Object[] {userName, password}, Integer.class);

    }

    //TEST
    public User findUserByUserName(String userName) {
        return jdbcTemplate.queryForObject(FIND_BY_USERNAME, new Object[] {userName}, User.class);
    }

    //book
    public User findUserByUserName2(String userName) {

        final User user = new User();
        jdbcTemplate.query(FIND_BY_USERNAME, new Object[]{userName},
                new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet resultSet) throws SQLException {
                        user.setUserId(resultSet.getInt("user_id"));
                        user.setUserName(userName);
                        user.setCredits(resultSet.getInt("credits"));
                    }
                });

        return user;
    }

    public void updateLoginInfo(User user) {
        jdbcTemplate.update(UPDATE_SQL, new Object[] {user.getLastVisit(), user.getLastIp(), user.getCredits(), user.getUserId()});
    }

}
