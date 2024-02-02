package com.api.ttoklip.domain.SignUp.main.constant;

public class SignUpResponseConstant {
    public static final String successSignUpResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 201,
            "code": "201",
            "message": "회원가입 성공",
            "result": {
                "userId": 456,
                "userName": "john_doe",
                "userEmail": "john.doe@example.com",
                "userNickName": "johndoe123"
            }
        }
        """;

    public static final String successNickNameResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "닉네임 중복 확인 성공",
            "result": {
                "duplicate": false
            }
        }
        """;

    public static final String authSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "아이디 중복 확인 성공",
            "result": {
                "duplicate": false
            }
        }
        """;

    public static final String requestSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "이메일 인증번호 요청 성공",
            "result": {
                "verificationCode": "123456"
            }
        }
        """;

    public static final String verificationSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "이메일 인증번호 확인 성공",
            "result": {
                "verificationStatus": true
            }
        }
        """;

}