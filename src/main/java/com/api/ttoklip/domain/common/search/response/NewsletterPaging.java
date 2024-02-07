package com.api.ttoklip.domain.common.search.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NewsletterPaging(List<SingleResponse> newsletters, Integer totalPage,
                               Long totalElements, Boolean isFirst, Boolean isLast) {
}
