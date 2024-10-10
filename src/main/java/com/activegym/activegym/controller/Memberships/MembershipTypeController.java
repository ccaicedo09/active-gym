//package com.activegym.activegym.controller.Memberships;
//
//
//import com.activegym.activegym.dto.MembershipTypeDTO;
//import com.activegym.activegym.model.Memberships.MembershipType;
//import com.activegym.activegym.service.Memberships.MembershipTypeService;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@CrossOrigin // TEMPORAL FEATURE
//@AllArgsConstructor
//@RequestMapping("api/admin/membership-types")
//@RestController
//public class MembershipTypeController {
//
//    private final MembershipTypeService membershipTypeService;
//
//    @GetMapping
//    public Iterable<MembershipType> list() {
//        return membershipTypeService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public MembershipType get(@PathVariable("id") Long id) {
//        return membershipTypeService.findById(id);
//    }
//
//    @PostMapping
//    public MembershipType create(@RequestBody MembershipTypeDTO membershipTypeDTO) {
//        return membershipTypeService.create(membershipTypeDTO);
//    }
//}
