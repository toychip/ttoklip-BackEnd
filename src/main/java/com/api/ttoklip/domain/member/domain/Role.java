package com.api.ttoklip.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    GENERAL("일반사용자",1),
    MANAGER("관리자",2);

    private final String name;
    private final int code;

}
