package com.library.common.pojo;

public class ResultModel<T> {

    private int code;
    private String message;
    private T data;

    public ResultModel() {
	super();
    }

    public ResultModel(int code, String message, T data) {
	super();
	this.code = code;
	this.message = message;
	this.data = data;
    }

    public int getCode() {
	return code;
    }

    public void setCode(int code) {
	this.code = code;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public T getData() {
	return data;
    }

    public void setData(T data) {
	this.data = data;
    }
}
