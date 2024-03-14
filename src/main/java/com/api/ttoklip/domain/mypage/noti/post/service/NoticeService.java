package com.api.ttoklip.domain.mypage.noti.post.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.mypage.noti.post.domain.NoticeRepository;
import com.api.ttoklip.domain.mypage.noti.post.domain.Notice;
import com.api.ttoklip.domain.mypage.noti.post.domain.NoticePagingRepository;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeEditRequest;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeCreateRequest;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticePaging;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeResponse;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeSingleResponse;
import com.api.ttoklip.domain.mypage.noti.post.editor.NoticePostEditor;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticePagingRepository noticePagingRepository;
    private final MemberService memberService;

    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Notice findNoticeById(final Long noticeId) {
        return noticeRepository.findByIdActivated(noticeId);
    }
    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final NoticeCreateRequest request) {
        Member member = memberService.findByIdWithProfile(getCurrentMember().getId());
        if(member.getRole()==Role.MANAGER){
            Notice notice = Notice.of(request);
            noticeRepository.save(notice);
            Long noticeId = notice.getId();
            return Message.registerPostSuccess(Notice.class, noticeId);
        }else {
            throw new ApiException(ErrorType._USER_NOT_ALLOWED);
        }
    }


    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */
    public NoticeResponse getSingleNotice(final Long noticeId) {//하나 조회
        Notice notice = findNoticeById(noticeId);
        NoticeResponse noticeResponse = NoticeResponse.of(notice);//
        return noticeResponse;
    }

    public NoticePaging getNoticeList(final Pageable pageable) {//전체 조회
        Page<Notice> contentPaging = noticePagingRepository.getContain(pageable);
        List<Notice> contents = contentPaging.getContent();
        List<NoticeSingleResponse> noticeSingleData = contents.stream()
                .map(NoticeSingleResponse::noticeFrom)
                .toList();
        //추후 구현 02.08
        //return noticeDefaultRepository.findAll();
        return NoticePaging.builder()
                .notices(noticeSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    /* -------------------------------------------- DELETE  -------------------------------------------- */
    @Transactional
    public Message deleteNotice(final Long noticeId) {
        //소프트삭제 구현
        Member member = memberService.findByIdWithProfile(getCurrentMember().getId());
        if(member.getRole()==Role.MANAGER){
            Notice notice = findNoticeById(noticeId);
            notice.deactivate();
            return Message.deletePostSuccess(Notice.class, noticeId);
        }else{
            throw new ApiException(ErrorType._USER_NOT_ALLOWED);
        }

    }
    /* -------------------------------------------- DELETE 끝   -------------------------------------------- */

    /* -------------------------------------------- EDIT  -------------------------------------------- */
    @Transactional
    public Message edit(final Long noticeId, final NoticeEditRequest request) {

        // 기존 게시글 찾기
        Notice notice = findNoticeById(noticeId);
        Member member = memberService.findByIdWithProfile(getCurrentMember().getId());
        // ToDO Validate currentMember
        if(member.getRole()== Role.MANAGER){
            NoticePostEditor noticePostEditor = getPostEditor(request, notice);
            notice.edit(noticePostEditor);

            return Message.editPostSuccess(Notice.class, notice.getId());//message후에 추가
        }else{
            throw new ApiException(ErrorType._USER_NOT_ALLOWED);
        }
        // title, content 수정
    }

    private NoticePostEditor getPostEditor(final NoticeEditRequest request, final Notice notice) {
        NoticePostEditor.NoticePostEditorBuilder editorBuilder = notice.toEditor();
        NoticePostEditor noticePostEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return noticePostEditor;
    }

    /* -------------------------------------------- EDIT  -------------------------------------------- */
}
