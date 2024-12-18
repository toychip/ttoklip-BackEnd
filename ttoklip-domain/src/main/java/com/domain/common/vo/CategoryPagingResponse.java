package com.domain.common.vo;

import java.util.List;
import lombok.Builder;

@Builder
public record CategoryPagingResponse(List<TitleResponse> data, Category category, Integer totalPage,
                                     Long totalElements, Boolean isFirst, Boolean isLast) {
}
