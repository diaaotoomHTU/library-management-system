package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.exceptions.NotFoundException;
import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepo memberRepo;

    @Autowired
    public MemberService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    public Member addMember(Member member) {
        return memberRepo.save(member);
    }

    public List<Member> findAllMembers() {
        return memberRepo.findAll();
    }

    public Member updateMember(Member member) {
        return memberRepo.save(member);
    }

    public Member findMemberById(Integer id) {
        return memberRepo.findMemberById(id).orElseThrow(() -> new NotFoundException("Not Found"));
    }

    public void deleteMember(Integer id) {
        memberRepo.deleteMemberById(id);
    }

    public List<Member> findByMemberId(Integer id) {
        return memberRepo.findByMemberId(id);
    }

    public List<Member> findByMemberName(String name) {
        return memberRepo.findByMemberName(name);
    }
}
