package com.Iris.demo.repository;

import com.Iris.demo.entity.Members;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, String> {

    void deleteByMemberId(String memberId);

    Page<Members> findByGender(String gender, Pageable pageable);

    List<Members> findAllByOrderByOrderNumberTotalDesc();
}
