package top.onepecent.oneu.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找的问题不在了，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "问题或评论不在了"),
    NO_LOGIN(2003, "当前用户未登录，请先登录！"),
    SYS_ERROR(2004, "服务器异常"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在！"),
    CONTENT_IS_EMPTY(2007, "输入的内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008, "读取错误信息!?"),
    NOTIFICATION_NOT_FOUND(2009, "你找的消息不在了，要不换个试试？"),
    ;

    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
/////////////////////////////////////////////
//    CustomizeErrorCode(String message) {
//        this.message = message;
//    }
    ////////////////////////
    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
