package com.example.librarymanagementsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.librarymanagementsystem.model.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member, Integer>{
    void deleteMemberById(Integer id);

    Optional<Member> findMemberById(Integer id);

    @Query(value="select * from Member e where e.name like %:membername%", nativeQuery = true)
    List<Member> findByMemberName(@Param("membername") String membername);

    @Query(value="select * from Member e where e.id like %:memberid%", nativeQuery = true)
    List<Member> findByMemberId(@Param("memberid") Integer memberid);
}
