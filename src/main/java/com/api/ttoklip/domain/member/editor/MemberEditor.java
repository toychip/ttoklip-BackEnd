package com.api.ttoklip.domain.member.editor;

import lombok.Getter;

@Getter
public class MemberEditor {
    private String userNickname;
    private String userPassword;

    public MemberEditor(String userNickname, String userPassword){
        this.userNickname=userNickname;
        this.userPassword=userPassword;
    }

    public static MemberEditorBuilder builder(){
        return new MemberEditorBuilder();
    }

    public static class MemberEditorBuilder{
        private String userNickname;
        private String userPassword;

        MemberEditorBuilder(){
        }
        public MemberEditorBuilder userNickname(final String userNickname){
            if(userNickname!=null&&!userNickname.isEmpty()){
                this.userNickname=userNickname;
            }
            return this;
        }
        public MemberEditorBuilder userPassword(final String userPassword){
            if(userPassword!=null&&!userPassword.isEmpty()){
                this.userPassword=userPassword;
            }
            return this;
        }

        public MemberEditor build(){
            return new MemberEditor(userNickname, userPassword);
        }
    }
}
