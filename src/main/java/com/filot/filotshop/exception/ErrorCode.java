package com.filot.filotshop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),

    INVALID_INDEX(BAD_REQUEST, "인덱스를 잘못 요청하셨습니다"),

    INVALID_REQUEST(BAD_REQUEST ,"입력한 정보로부터 에러가 발생했습니다."),
    INVALID_REQUEST_RATE(BAD_REQUEST ,"리뷰는 0부터 5까지 입력 받을 수 있습니다."),
    INVALID_REQUEST_SORT(BAD_REQUEST, "해당 방법으로 조회 할 수 없습니다"),
    INVALID_REQUEST_IMAGE(BAD_REQUEST ,"파일 형식이 옳바르지 않습니다."),
    //조건을  장바구니에서 ORDER로 바꿔야함
    INVALID_REQUEST_REVIEW(BAD_REQUEST, "상품 리뷰를 쓰기 위해선 주문을 하셔야합니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
//    CANNOT_FIND_PRODUCT_ID(BAD_REQUEST, "일치하는 상품이 없습니다."),

    CATEGORY_NOT_FOUND(BAD_REQUEST, "카테고리가 존재하지 않습니다"),
    INVALID_REQUEST_FOR_DATABASE(BAD_REQUEST,"제약조건에 위반되었습니다"),


    MISMATCH_PASSWORD(BAD_REQUEST, "입력한 비밀번호가 일치하지 않습니다."),
    MISMATCH_REGEXP_PWD(BAD_REQUEST, "비밀번호는 영어 숫자 특수문자, 8자 이상이여합니다"),

    MISMATCH_FILE_MIMETYPE(BAD_REQUEST,"이미지 파일만 처리할 수 있습니다."),

    MISMATCH_VERIFY_CODE(UNAUTHORIZED,"이메일 인증 코드가 옳바르지 않습니다."),
    NOT_AUTHORIZATION(UNAUTHORIZED,"권한이 없습니다."),
    FAIL_JOIN(UNAUTHORIZED,"회원가입에 실패하셨습니다."),



    // product

    INVALID_AMOUNT(BAD_REQUEST, "상품 수량은 0개 이상이여야합니다."),
    NOT_ENOUGH_AMOUNT(BAD_REQUEST, "매장 내 수량이 부족합니다"),

    // 401


    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    PRODUCT_NOT_FOUND(NOT_FOUND,"해당 상품을 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),

    USER_NOT_FOUND(UNAUTHORIZED, "해당 유저 정보를 찾을 수 없습니다"),
    DUPLICATE_USER(CONFLICT, "이미 존재하는 회원입니다."),
    NOT_ENOUGH_MONEY(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    MISMATCH_ID_PWD(BAD_REQUEST,"아이디와 패스워드가 일치하지 않습니다"),


    // basket

    INVALID_NUMBER(BAD_REQUEST , "0 이상의 수를 입력해주세요"),
    FAIL_UPLOAD_IMAGE(METHOD_NOT_ALLOWED, "이미지 업로드에 실패하였습니다")
    ;



    private final HttpStatus httpStatus;
    private final String detail;
}