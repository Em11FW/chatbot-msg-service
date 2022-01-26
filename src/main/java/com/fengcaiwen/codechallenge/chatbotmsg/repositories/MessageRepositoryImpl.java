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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    private static final String SQL_CREATE_MESSAGE = "INSERT INTO MESSAGES(MESSAGE_ID, CUSTOMER_ID, DIALOG_ID, MESSAGE, LANGUAGE, CREATED) VALUES(NEXTVAL('MESSAGES_SEQ'), ?, ?, ?, ?, ?)";
    private static final String SQL_CREATE_CONSENT = "INSERT INTO CONSENTS(DIALOG_ID) VALUES(?)";
    private static final String SQL_DELETE_BY_DIALOG_ID = "DELETE FROM MESSAGES WHERE DIALOG_ID = ?";
    private static final String SQL_FIND_BY_CUSTOMER_ID_AND_LANGUAGE = "SELECT * FROM MESSAGES INNER JOIN CONSENTS ON MESSAGES.DIALOG_ID = CONSENTS.DIALOG_ID WHERE CUSTOMER_ID = ? AND LANGUAGE = ? ORDER BY CREATED DESC LIMIT %s OFFSET %s";
    private static final String SQL_FIND_BY_CUSTOMER_ID = "SELECT * FROM MESSAGES INNER JOIN CONSENTS ON MESSAGES.DIALOG_ID = CONSENTS.DIALOG_ID WHERE CUSTOMER_ID = ? ORDER BY CREATED DESC LIMIT %s OFFSET %s";
    private static final String SQL_FIND_BY_LANGUAGE = "SELECT * FROM MESSAGES INNER JOIN CONSENTS ON MESSAGES.DIALOG_ID = CONSENTS.DIALOG_ID WHERE LANGUAGE = ? ORDER BY CREATED DESC LIMIT %s OFFSET %s";
    private static final String SQL_FIND_ALL = "SELECT * FROM MESSAGES INNER JOIN CONSENTS ON MESSAGES.DIALOG_ID = CONSENTS.DIALOG_ID ORDER BY CREATED DESC LIMIT %s OFFSET %s";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Long create(Long customerID, Long dialogID, String text, String language, LocalDateTime now){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_MESSAGE, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, customerID);
            ps.setLong(2, dialogID);
            ps.setString(3, text);
            ps.setString(4, language);
            ps.setTimestamp(5, Timestamp.valueOf(now));
            return ps;
        }, keyHolder);
        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("MESSAGE_ID");
    }

    @Override
    public Boolean createConsent(Long dialogID) {
        return jdbcTemplate.update(SQL_CREATE_CONSENT, dialogID) == 1;
    }

    @Override
    public Integer deleteByConsent(Long dialogID) {
        return jdbcTemplate.update(SQL_DELETE_BY_DIALOG_ID, dialogID);
    }

    @Override
    public List<Message> findAll(String limit, String offset) {
        return jdbcTemplate.query(String.format(SQL_FIND_ALL, limit, offset), messageRowMapper);
    }

    @Override
    public List<Message> findByCustomerIdAndLanguage(Long customerId, String language, String limit, String offset) {
        return jdbcTemplate.query(String.format(SQL_FIND_BY_CUSTOMER_ID_AND_LANGUAGE, limit, offset), messageRowMapper, customerId, language);
    }

    @Override
    public List<Message> findByCustomerId(Long customerId, String limit, String offset) {
        return jdbcTemplate.query(String.format(SQL_FIND_BY_CUSTOMER_ID, limit, offset), messageRowMapper, customerId);
    }

    @Override
    public List<Message> findByLanguage(String language, String limit, String offset) {
        return jdbcTemplate.query(String.format(SQL_FIND_BY_LANGUAGE, limit, offset), messageRowMapper, language);
    }



    private RowMapper<Message> messageRowMapper = ((rs, rowNum) -> {
        return new Message(rs.getLong("MESSAGE_ID"),
                rs.getLong("CUSTOMER_ID"),
                rs.getLong("DIALOG_ID"),
                rs.getString("MESSAGE"),
                rs.getString("LANGUAGE"),
                rs.getTimestamp("CREATED").toLocalDateTime());
    });
}