package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.model.Librarian;
import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.service.LibrarianService;
import com.example.librarymanagementsystem.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MemberResource {
    private final MemberService memberService;
    private final LibrarianService librarianService;

    public MemberResource(MemberService memberService, LibrarianService librarianService) {
        this.memberService = memberService;
        this.librarianService = librarianService;
    }

    public boolean validateLogin(HttpSession httpSession) {
        try {
            for (Librarian currentLibrarian: librarianService.findAllLibrarians()) {
                if (httpSession.getAttribute("username").equals(currentLibrarian.getUsername()) && httpSession.getAttribute("password").equals(currentLibrarian.getPassword())) {
                    return true;
                }
            }
        } catch (Exception e){

        }
        return false;
    }

    @GetMapping("/members")
    public ModelAndView getFirstFiveMembers(String membername, Integer memberid, HttpSession httpSession){
        if (!validateLogin(httpSession)) {
            return new ModelAndView("redirect:/index");
        }
        ModelAndView mav = new ModelAndView("members");
        List<Member> members = new ArrayList<Member>();
        LinkedHashSet<Member> membersById = new LinkedHashSet<Member>();
        LinkedHashSet<Member> membersByName = new LinkedHashSet<Member>();
        if (membername != null && !membername.equals("")) {
            membersByName.addAll(memberService.findByMemberName(membername));
        } else {
            membersByName.addAll(memberService.findAllMembers());
        }
        if (memberid != null) {
            membersById.addAll(memberService.findByMemberId(memberid));
        } else {
            membersById.addAll(memberService.findAllMembers());
        }
        for (Member member: membersById) {
            if (membersByName.contains(member)) {
                members.add(member);
            }
        }
        List<Member> membersToReturn = new ArrayList<Member>();
        List<Boolean> pageButtons = new ArrayList<Boolean>();
        pageButtons.add(true);
        for (int i = 5; i < members.size(); i += 5) {
            pageButtons.add(false);
        }
        for (int i = 0; i < 5 && i < members.size(); ++i) {
            membersToReturn.add(members.get(i));
        }
        Member newMember = new Member();
        Member updateMember = new Member();
        Member deleteMember = new Member();
        mav.addObject("members", members);
        mav.addObject("newMember", newMember);
        mav.addObject("updateMember", updateMember);
        mav.addObject("deleteMember", deleteMember);
        mav.addObject("membersToReturn", membersToReturn);
        mav.addObject("pageButtons", pageButtons);
        return mav;
    }
    @GetMapping("/members/{page}")
    public ModelAndView getAllMembers(@PathVariable("page") Integer page, String membername, Integer memberid, HttpSession httpSession){
        if (!validateLogin(httpSession)) {
            return new ModelAndView("redirect:/index");
        }
        ModelAndView mav = new ModelAndView("members");
        List<Member> members = new ArrayList<Member>();
        LinkedHashSet<Member> membersById = new LinkedHashSet<Member>();
        LinkedHashSet<Member> membersByName = new LinkedHashSet<Member>();
        if (membername != null && !membername.equals("")) {
            membersByName.addAll(memberService.findByMemberName(membername));
        } else {
            membersByName.addAll(memberService.findAllMembers());
        }
        if (memberid != null) {
            membersById.addAll(memberService.findByMemberId(memberid));
        } else {
            membersById.addAll(memberService.findAllMembers());
        }
        for (Member member: membersById) {
            if (membersByName.contains(member)) {
                members.add(member);
            }
        }
        List<Member> membersToReturn = new ArrayList<Member>();
        page *= 5;
        List<Boolean> pageButtons = new ArrayList<Boolean>();
        for (int i = 5; i < members.size() + 5; i += 5) {
            if (i == page) {
                pageButtons.add(true);
            } else {
                pageButtons.add(false);
            }
        }
        for (int i = page - 5; i < page && i < members.size(); ++i) {
            membersToReturn.add(members.get(i));
        }
        Member newMember = new Member();
        Member updateMember = new Member();
        Member deleteMember = new Member();
        mav.addObject("members", members);
        mav.addObject("newMember", newMember);
        mav.addObject("updateMember", updateMember);
        mav.addObject("deleteMember", deleteMember);
        mav.addObject("membersToReturn", membersToReturn);
        mav.addObject("pageButtons", pageButtons);
        return mav;
    }

    /*@GetMapping("/find/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Integer id){
        Member member = memberService.findMemberById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }*/

    @PostMapping("/addMember")
    public String addMember(@ModelAttribute Member member) {
        memberService.addMember(member);
        return "redirect:/members";
    }

    @PostMapping("/updateMember")
    public String updateMember(@ModelAttribute Member member) {
        memberService.updateMember(member);
        return "redirect:/members";
    }

    @Transactional
    @PostMapping("/deleteMember")
    public String deleteMember(@ModelAttribute Member member) {
        memberService.deleteMember(member.getId());
        return "redirect:/members";
    }
}
