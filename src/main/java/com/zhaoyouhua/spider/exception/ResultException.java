package com.zhaoyouhua.spider.exception;

public class ResultException extends RuntimeException {

    private String message;
    private int pageNum;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public ResultException() {
    }

    public ResultException(String message, int pageNum) {
        super(message);
        this.message = message;
        this.pageNum = pageNum;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
