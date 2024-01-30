package com.api.ttoklip.domain.member.repository;

import com.api.ttoklip.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserEmail(String userEmail);
    List<Member> findMemberByUserEmail(String userEmail);
    Optional<Member> findMemberByUserEmailAndProvider(String userEmail,String Provider);
    Optional<Member> findById(Long userId);
    Optional<Member> findByUserEmailAndUserNickname(String userEmail, String provider);
}
