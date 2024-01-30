package com.api.ttoklip.domain.honeytip.post.post.service;

import com.api.ttoklip.domain.honeytip.post.post.domain.Honeytip;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneytipImage;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneytipUrl;
import com.api.ttoklip.domain.honeytip.post.post.domain.repository.HoneytipRepository;
import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneytipCreateReq;
import com.api.ttoklip.domain.honeytip.post.post.dto.response.HoneytipWithCommentRes;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneytipPostService {
    private final HoneytipRepository honeytipRepository;
    private final S3FileUploader s3FileUploader;

    @Transactional
    public Long register(final HoneytipCreateReq request) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> imageUrls = s3FileUploader.uploadMultipartFiles(request.getImages());
        for (String imageUrl : imageUrls) {
            System.out.println("imageUrl = " + imageUrl);
        }

        // HoneytipImage 객체 생성
        List<HoneytipImage> honeytipImages = imageUrls.stream()
                .map(url -> new HoneytipImage(url, null))
                .collect(Collectors.toList());

        //HoneytipUrl 객체 생성
        List<HoneytipUrl> honeytipUrls = request.getUrl().stream()
                .map(url -> new HoneytipUrl(url, null))
                .collect(Collectors.toList());


        // Honeytip 객체 생성 및 연관 관계 설정
        Honeytip honeytip =Honeytip.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .honeytipImageList(honeytipImages)
                .honeytipUrlList(honeytipUrls)
                .build();

        honeytipImages.forEach(image -> image.updateHoneytip(honeytip));
        honeytipUrls.forEach(url -> url.updateHoneytip(honeytip));


        // 엔티티에 저장
        honeytipRepository.save(honeytip);

        // 작성된 꿀팁의 id 값 리턴
        return honeytip.getId();
    }

    public HoneytipWithCommentRes getSinglePost(final Long postId) {
        return null;
    }

    public Long edit(final Long postId, final HoneytipCreateReq request) {

        // 기존 게시글 찾기
        Honeytip existingHoneytip = honeytipRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> imageUrls = s3FileUploader.uploadMultipartFiles(request.getImages());
        for (String imageUrl : imageUrls) {
            System.out.println("imageUrl = " + imageUrl);
        }

        // HoneytipImage 객체 생성
        List<HoneytipImage> honeytipImages = imageUrls.stream()
                .map(url -> new HoneytipImage(url, null))
                .collect(Collectors.toList());

        //HoneytipUrl 객체 생성
        List<HoneytipUrl> honeytipUrls = request.getUrl().stream()
                .map(url -> new HoneytipUrl(url, null))
                .collect(Collectors.toList());





        // Honeytip 객체 생성 및 연관 관계 설정
        Honeytip honeytip =Honeytip.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .honeytipImageList(honeytipImages)
                .honeytipUrlList(honeytipUrls)
                .build();

        honeytipImages.forEach(image -> image.updateHoneytip(honeytip));
        honeytipUrls.forEach(url -> url.updateHoneytip(honeytip));

        // 꿀팁 업데이트
        existingHoneytip.updateHoneytip(request.getTitle(), request.getContent(), request.getCategory(), honeytipImages, honeytipUrls);


        // 엔티티에 저장
        honeytipRepository.save(existingHoneytip);

        // 작성된 꿀팁의 id 값 리턴
        return existingHoneytip.getId();
    }

    public Message delete(final Long postId) {

        // 유저 정보 찾기


        // 저장된 게시글 찾기
        Honeytip honeytip = honeytipRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        honeytipRepository.delete(honeytip);


        return Message.builder()
                .message("게시글 삭제에 성공했습니다.")
                .build();
    }
}
