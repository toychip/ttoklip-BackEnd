package com.api.ttoklip.domain.login.main.main.constant;

public class LoginResponseConstant {
    public static final String successResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "로그인 성공",
            "result": {
                "userId": 123,
                "username": "john_doe",
                "email": "john.doe@example.com",
                "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            }
        }
        """;

    public static final String logoutSuccessResponse = """
        {
            "time": "2024-01-11T16:10:45.123Z",
            "status": 200,
            "code": "200",
            "message": "로그아웃 성공",
            "result": {
                "userId": 123,
                "username": "john_doe",
                "message": "로그아웃 되었습니다."
            }
        }
        """;

}
