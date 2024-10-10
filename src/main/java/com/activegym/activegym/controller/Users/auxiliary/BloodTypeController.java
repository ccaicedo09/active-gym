package com.activegym.activegym.controller.Users.auxiliary;


import com.activegym.activegym.model.Users.auxiliary.BloodType;
import com.activegym.activegym.service.Users.auxiliary.BloodTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin // TEMPORAL FEATURE
@AllArgsConstructor
@RestController
@RequestMapping("/bloodtype")
public class BloodTypeController {

    private final BloodTypeService bloodTypeService;

    @GetMapping
    public Iterable<BloodType> list() {
        return bloodTypeService.findAll();
    }

//    @GetMapping("/{id}")
//    public BloodType get(@PathVariable("id") Integer id) {
//        return bloodTypeService.findById(id);
//    }
}
