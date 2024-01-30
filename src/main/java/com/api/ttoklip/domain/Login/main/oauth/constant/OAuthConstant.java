package com.api.ttoklip.domain.login.main.oauth.constant;

public class OAuthConstant {
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

    public static final String kakaoSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "카카오 로그인 성공",
            "result": {
                "userId": "kakao123",
                "userName": "KakaoUser",
                "userEmail": "kakao.user@example.com"
            }
        }
        """;

    public static final String naverSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "네이버 로그인 성공",
            "result": {
                "userId": "naver123",
                "userName": "NaverUser",
                "userEmail": "Naver.user@example.com"
            }
        }
        """;
}
