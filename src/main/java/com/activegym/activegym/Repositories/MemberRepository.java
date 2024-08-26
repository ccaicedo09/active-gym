package com.activegym.activegym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.activegym.activegym.Entities.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
}
