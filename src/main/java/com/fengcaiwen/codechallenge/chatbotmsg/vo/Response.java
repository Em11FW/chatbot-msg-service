package com.fengcaiwen.codechallenge.chatbotmsg.vo;

import org.springframework.http.HttpStatus;

/**
 * encapsulating Response
 * only getter
 */
public class Response {

    private HttpStatus code;
    private String message;
    private Object data;


    private Response(Object data) {
        this.code = HttpStatus.OK;
        this.message = "success";
        this.data = data;
    }

    private Response(String message) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
    }

    private Response() {
        this.code = HttpStatus.OK;
        this.message = "success";
    }

    public static Response success(Object data) {
        return new Response(data);
    }

    public static Response success() {
        return new Response();
    }

    public static Response fail(String message) {
        Response response = new Response(message);
        return response;
    }

    public HttpStatus getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }


    public Object getData() {
        return data;
    }


}