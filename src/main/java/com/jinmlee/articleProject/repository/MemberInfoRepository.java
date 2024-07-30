package com.jinmlee.articleProject.repository;

import com.jinmlee.articleProject.entity.member.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
}
