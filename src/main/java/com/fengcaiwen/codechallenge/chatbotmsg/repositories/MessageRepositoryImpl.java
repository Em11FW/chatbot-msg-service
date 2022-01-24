package com.fengcaiwen.codechallenge.chatbotmsg.repositories;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    private static final String SQL_CREATE = "INSERT INTO MESSAGES(MESSAGE_ID, CUSTOMER_ID, DIALOG_ID, MESSAGE, LANGUAGE) VALUES(NEXTVAL('MESSAGES_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_UPDATE_CONSENT = "UPDATE MESSAGES SET CONSENT = TRUE WHERE DIALOG_ID = ?";
    private static final String SQL_DELETE_BY_DIALOG_ID = "DELETE FROM MESSAGES WHERE DIALOG_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String customerID, String dialogID, String text, String language) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerID);
            ps.setString(2, dialogID);
            ps.setString(3, text);
            ps.setString(4, language);
            return ps;
        }, keyHolder);
        return (Integer) Objects.requireNonNull(keyHolder.getKeys()).get("MESSAGE_ID");
    }

    @Override
    public Integer updateConsent(String dialogID) {
        return jdbcTemplate.update(SQL_UPDATE_CONSENT, dialogID);
    }

    @Override
    public Integer deleteByConsent(String dialogID) {
        return jdbcTemplate.update(SQL_DELETE_BY_DIALOG_ID, dialogID);
    }
}