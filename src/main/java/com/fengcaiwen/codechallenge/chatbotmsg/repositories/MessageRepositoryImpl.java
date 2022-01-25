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
    private static final String SQL_FIND_BY_CUSTOMER_ID_AND_LANGUAGE = "SELECT * FROM MESSAGES WHERE CUSTOMER_ID = ? AND LANGUAGE = ? AND CONSENT = TRUE ORDER BY MESSAGE_ID DESC";
    private static final String SQL_FIND_BY_CUSTOMER_ID = "SELECT * FROM MESSAGES WHERE CUSTOMER_ID = ? AND CONSENT = TRUE ORDER BY MESSAGE_ID DESC";
    private static final String SQL_FIND_BY_LANGUAGE = "SELECT * FROM MESSAGES WHERE LANGUAGE = ? AND CONSENT = TRUE ORDER BY MESSAGE_ID DESC";
    private static final String SQL_FIND_ALL = "SELECT * FROM MESSAGES WHERE CONSENT = TRUE ORDER BY MESSAGE_ID DESC";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Long create(Long customerID, Long dialogID, String text, String language){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, customerID);
            ps.setLong(2, dialogID);
            ps.setString(3, text);
            ps.setString(4, language);
            return ps;
        }, keyHolder);
        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("MESSAGE_ID");
    }

    @Override
    public Integer updateConsent(Long dialogID) {
        return jdbcTemplate.update(SQL_UPDATE_CONSENT, dialogID);
    }

    @Override
    public Integer deleteByConsent(Long dialogID) {
        return jdbcTemplate.update(SQL_DELETE_BY_DIALOG_ID, dialogID);
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, messageRowMapper);
    }

    @Override
    public List<Message> findByCustomerIdAndLanguage(Long customerId, String language) {
        return jdbcTemplate.query(SQL_FIND_BY_CUSTOMER_ID_AND_LANGUAGE, messageRowMapper, customerId, language);
    }

    @Override
    public List<Message> findByCustomerId(Long customerId) {
        return jdbcTemplate.query(SQL_FIND_BY_CUSTOMER_ID, messageRowMapper, customerId);
    }

    @Override
    public List<Message> findByLanguage(String language) {
        return jdbcTemplate.query(SQL_FIND_BY_LANGUAGE, messageRowMapper, language);
    }



    private RowMapper<Message> messageRowMapper = ((rs, rowNum) -> {
        return new Message(rs.getLong("MESSAGE_ID"),
                rs.getLong("CUSTOMER_ID"),
                rs.getLong("DIALOG_ID"),
                rs.getString("MESSAGE"),
                rs.getString("LANGUAGE"),
                rs.getBoolean("CONSENT"));
    });
}