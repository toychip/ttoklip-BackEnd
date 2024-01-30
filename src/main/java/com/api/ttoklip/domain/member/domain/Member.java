package com.api.ttoklip.domain.member.domain;

import com.api.ttoklip.domain.member.editor.MemberEditor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userAuth;

    private String userNickname;

    @Email
    private String userEmail;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 후에 프로필에 사용할 것 private Profile profile;
    /*@OneToMany
    @Builder.Default
    private List<Post> posts=new ArrayList<>();나중에 사용할 멤버 포스트 개수*/

    private String provider;
    private String providerId;

    public void edit (MemberEditor memberEditor){
        if(memberEditor.getUserNickname()!= null){
            userNickname =memberEditor.getUserNickname();
        }
        if(memberEditor.getUserPassword()!=null){
            password=memberEditor.getUserPassword();//비밀번호 수정 로직
        }
    }
}
