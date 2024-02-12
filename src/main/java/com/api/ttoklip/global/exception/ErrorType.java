package com.api.ttoklip.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorType {

    /**
     * Error Message Convention
     * <p>
     * name : _(head) + Error Name status : HttpStatus
     * <p>
     * errorCode : 400번 오류인 상황이 여러개 올텐데, 4001, 4002, 4003.. 이런식으로 설정 (해당 오류들은 MEMBER 와 관련된 400 오류들) ex) Member Error,
     * Http Status Code: 400 -> MEMBER_4000
     * <p>
     * message : 사람이 알아볼 수 있도록 작성 ex) "인증이 필요합니다."
     */

    // ------------------------------------------ S3 ------------------------------------------
    EXCEEDING_FILE_COUNT(BAD_REQUEST, "S4001", "사진 개수가 너무 많습니다."),
    EXCEEDING_FILE_SIZE(BAD_REQUEST, "S4002", "사진 용량이 너무 큽니다."),
    S3_UPLOAD(INTERNAL_SERVER_ERROR, "S5001", "서버오류, S3 사진 업로드 에러입니다."),
    S3_CONNECT(INTERNAL_SERVER_ERROR, "S5002", "서버오류, S3 연결 에러입니다."),
    S3_CONVERT(INTERNAL_SERVER_ERROR, "S5003", "서버오류, S3 변환 에러입니다."),

    // ------------------------------------------ Category ------------------------------------------

    CATEGORY_NOT_FOUND(NOT_FOUND, "Category_4040", "카테고리를 찾을 수 없습니다."),


    // ------------------------------------------ Report ------------------------------------------
    REPORT_NOT_FOUND(NOT_FOUND, "Report_4040", "신고 타입을 찾을 수 없습니다."),


    // ------------------------------------------ Question ------------------------------------------
    QUESTION_NOT_FOUND(NOT_FOUND, "Question_4040", "질문해요를 찾을 수 없습니다."),


    // ------------------------------------------ Cart ------------------------------------------
    CART_NOT_FOUND(NOT_FOUND, "Cart_4040", "함께해요를 찾을 수 없습니다."),


    // ------------------------------------------ Community ------------------------------------------
    COMMUNITY_NOT_FOUND(NOT_FOUND, "Community_4040", "소통해요를 찾을 수 없습니다."),


    // ------------------------------------------ Comment ------------------------------------------
    COMMENT_NOT_FOUND(NOT_FOUND, "Comment_4040", "댓글을 찾을 수 없습니다."),


    // ------------------------------------------ HoneyTip ------------------------------------------
    HONEY_TIP_NOT_FOUND(NOT_FOUND, "HoneyTip_4040", "꿀팁공유해요를 찾을 수 없습니다."),


    // ------------------------------------------ Newsletter ------------------------------------------
    NEWSLETTER_NOT_FOUND(NOT_FOUND, "Newsletter_4040", "뉴스레터를 찾을 수 없습니다."),


    // ---------------------------------------- JWT TOKEN ----------------------------------------
    _JWT_PARSING_ERROR(BAD_REQUEST, "JWT_4001", "JWT Token이 올바르지 않습니다."),
    _JWT_EXPIRED(UNAUTHORIZED, "JWT_4010", "Jwt Token의 유효 기간이 만료되었습니다."),


    // ------------------------------------------ Auth ------------------------------------------
    OAUTH_INVALID_PROVIDER(INTERNAL_SERVER_ERROR, "OAUTH_5000", "올바르지 않은 Provider입니다."),
    OAUTH_NOTFOUND_NAME(INTERNAL_SERVER_ERROR, "OAUTH_5001", "Oauth 제공자로부터 name을 받을 수 없습니다."),
    OAUTH_NOTFOUND_EMAIL(INTERNAL_SERVER_ERROR, "OAUTH_5002", "Oauth 제공자로부터 email을 받을 수 없습니다."),


    // ------------------------------------------ USER ------------------------------------------
    _USER_NOT_FOUND_BY_TOKEN(NOT_FOUND, "USER_4040", "제공된 토큰으로 사용자를 찾을 수 없습니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "USER_4010", "로그인되지 않은 상태입니다."),
    _USER_NOT_FOUND_DB(NOT_FOUND, "USER_4041", "존재하지 않는 회원입니다."),


    // ------------------------------------------ LIKE ------------------------------------------
    LIKE_NOT_FOUND(NOT_FOUND, "LIKE_4041", "좋아요가 존재하지 않습니다."),


    // ------------------------------------------ AUTHORIZATION ------------------------------------------
    UNAUTHORIZED_EDIT_POST(FORBIDDEN, "AUTH_4031", "게시글의 작성자만 수정할 수 있습니다."),
    UNAUTHORIZED_DELETE_COMMENT(FORBIDDEN, "AUTH_4032", "댓글의 작성자만 삭제할 수 있습니다."),
    UNAUTHORIZED_CANCEL_LIKE(FORBIDDEN, "AUTH_4033", "좋아요한 사용자만 본인의 좋아요를 취소할 수 있습니다."),


    // ------------------------------------------ Privacy ------------------------------------------
    INVALID_CATEGORIES_SIZE(BAD_REQUEST, "Privacy_4041", "회원가입시 카테고리는 최대 3개까지 선택가능합니다."),
    ALREADY_EXISTS_NICKNAME(BAD_REQUEST, "Privacy_4042", "이미 사용중인 닉네임입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    ErrorType(final HttpStatus status, final String errorCode, final String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
