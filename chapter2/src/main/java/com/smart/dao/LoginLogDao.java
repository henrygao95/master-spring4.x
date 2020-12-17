package com.smart.dao;

import com.smart.domain.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Henry Gao
 * @date 2020-12-16 - 19:34
 */

@Repository
public class LoginLogDao {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_LOGIN_LOG_SQL = " INSERT INTO t_login_log (user_id, ip, login_datetime) VALUES (?,?,?)";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertLoginLog(LoginLog loginLog) {
        Object[] args = {loginLog.getLoginLogId(), loginLog.getIp(), loginLog.getLoginDate()};
        jdbcTemplate.update(INSERT_LOGIN_LOG_SQL, args);
    }
}
