package com.api.ttoklip.domain.mypage.noti.post.service;

import com.api.ttoklip.domain.mypage.noti.post.domain.NoticeRepository;
import com.api.ttoklip.domain.mypage.noti.post.domain.Notice;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeCreateRequest;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeResponse;
import com.api.ttoklip.domain.mypage.noti.post.editor.NoticePostEditor;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotiService {

    private final NoticeRepository noticeRepository;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Notice findNoticeById(final Long noticeId){
        return noticeRepository.findById(noticeId)
                .orElseThrow(()->new ApiException(ErrorType.NOTICE_NOT_FOUND));//notice 에러 추가 필요 SJ02.04
    }
    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Long register(final NoticeCreateRequest request){

        Notice notice=Notice.of(request);
        noticeRepository.save(notice);
        return notice.getId();
    }
    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */
    public NoticeResponse getSingleNotice(final Long noticeId) {//하나 조회
        Notice notice=findNoticeById(noticeId);
        NoticeResponse noticeResponse = NoticeResponse.of(notice);//
        return noticeResponse;
    }
    public List<Notice> getNoticeList() {//전체 조회
        //추후 구현
        return noticeRepository.findAll();
    }
    /* -------------------------------------------- DELETE  -------------------------------------------- */
    @Transactional
    public Message deleteNotice(final Long noticeId){//소프트삭제 구현
        Notice notice = findNoticeById(noticeId);
        notice.deactivate();

        return Message.deletePostSuccess(Notice.class, noticeId);
    }
    /* -------------------------------------------- DELETE 끝   -------------------------------------------- */

    /* -------------------------------------------- EDIT  -------------------------------------------- */
    @Transactional
    public Message edit(final Long noticeId, final NoticeCreateRequest request) {

        // 기존 게시글 찾기
        Notice notice = findNoticeById(noticeId);

        // ToDO Validate currentMember

        // title, content 수정
        NoticePostEditor noticePostEditor = getPostEditor(request, notice);
        notice.edit(noticePostEditor);

        return Message.editPostSuccess(Notice.class, notice.getId());//message후에 추가
    }

    private NoticePostEditor getPostEditor(final NoticeCreateRequest request, final Notice notice) {
        NoticePostEditor.NoticePostEditorBuilder editorBuilder = notice.toEditor();
        NoticePostEditor noticePostEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return noticePostEditor;
    }

    /* -------------------------------------------- EDIT  -------------------------------------------- */
}