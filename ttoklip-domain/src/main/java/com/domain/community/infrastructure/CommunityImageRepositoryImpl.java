package com.domain.community.infrastructure;

import com.domain.community.domain.CommunityImage;
import com.domain.community.domain.CommunityImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityImageRepositoryImpl implements CommunityImageRepository {

    private final CommunityImageJpaRepository jpaRepository;
    private final CommunityImageQueryRepository queryRepository;


    @Override
    public void allImageOwner(final List<Long> imageIds, final Long memberId) {
        queryRepository.allImageOwner(imageIds, memberId);
    }

    @Override
    public boolean doAllImageIdsExist(final List<Long> imageIds) {
        return queryRepository.doAllImageIdsExist(imageIds);
    }

    @Override
    public void deleteByImageIds(final List<Long> imageIds) {
        queryRepository.deleteByImageIds(imageIds);
    }

    @Override
    public CommunityImage save(final CommunityImage newCommunityImage) {
        return jpaRepository.save(newCommunityImage);
    }
}
