package com.activegym.activegym.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activegym.activegym.Entities.Member;
import com.activegym.activegym.Repositories.MemberRepository;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;


    @GetMapping // GET /members
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(member);
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        Optional<Member> member = memberRepository.findById(id);

        if (member.isPresent()) {
            Member updatedMember = member.get();
            updatedMember.setFirstName(memberDetails.getFirstName());
            updatedMember.setLastName(memberDetails.getLastName());
            updatedMember.setEmail(memberDetails.getEmail());
            updatedMember.setPassword(memberDetails.getPassword());
            updatedMember.setDateOfBirth(memberDetails.getDateOfBirth());
            updatedMember.setAge(memberDetails.getAge());
            updatedMember.setHeight(memberDetails.getHeight());
            updatedMember.setWeight(memberDetails.getWeight());
            return ResponseEntity.ok(memberRepository.save(updatedMember));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Member> deleteMember(@PathVariable Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
