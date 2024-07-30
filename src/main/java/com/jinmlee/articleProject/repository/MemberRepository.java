package com.jinmlee.articleProject.repository;

import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.entity.member.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);


}
