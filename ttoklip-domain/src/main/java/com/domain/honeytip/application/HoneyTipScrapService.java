package com.domain.honeytip.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipScrap;
import com.domain.honeytip.domain.HoneyTipScrapRepository;
import com.domain.member.domain.Member;
import com.common.annotation.SendNotification;
import com.common.NotiCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipScrapService {

    private final HoneyTipScrapRepository honeyTipScrapRepository;

    public boolean isHoneyTipScrapExists(final Long postId, final Long currentMemberId) {
        return honeyTipScrapRepository.existsByHoneyTipIdAndMemberId(postId, currentMemberId);
    }

    // 스크랩 생성
    @SendNotification(notiCategory = NotiCategory.HONEY_TIP_SCRAP)
    public void register(final HoneyTip honeyTip, final Member member) {
        HoneyTipScrap honeyTipScrap = HoneyTipScrap.of(honeyTip, member);
        honeyTipScrapRepository.save(honeyTipScrap);
    }

    // 스크랩 취소
    public void cancelScrap(final HoneyTip honeyTip, final Long currentMemberId) {
        // HoneyTipId (게시글 ID)
        Long findHoneyTipId = honeyTip.getId();
        HoneyTipScrap honeyTipScrap = honeyTipScrapRepository.findByHoneyTipIdAndMemberId(findHoneyTipId,
                        currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType.SCRAP_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByHoneyTipIdAndMemberId 결과가 존재하므로, 현재 사용자가 스크랩을 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        honeyTipScrapRepository.deleteById(honeyTipScrap.getId());
    }

    public Long countHoneyTipScraps(final Long postId) {
        return honeyTipScrapRepository.countHoneyTipScrapsByHoneyTipId(postId);
    }

    public Page<HoneyTip> getScrapPaging(final Long memberId, final Pageable pageable) {
        return honeyTipScrapRepository.getScrapPaging(memberId, pageable);
    }
}
