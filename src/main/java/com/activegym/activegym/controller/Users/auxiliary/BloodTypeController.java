package com.activegym.activegym.controller.Users.auxiliary;


import com.activegym.activegym.model.Users.auxiliary.BloodType;
import com.activegym.activegym.service.Users.auxiliary.BloodTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@AllArgsConstructor
@RestController
@RequestMapping("/bloodtype")
@Tag(name = "BloodType", description = "Controller for fetching auxiliary data about Users")
public class BloodTypeController {

    private final BloodTypeService bloodTypeService;

    @GetMapping
    @Operation(summary = "Get all blood types")
    public Iterable<BloodType> list() {
        return bloodTypeService.findAll();
    }

//    @GetMapping("/{id}")
//    public BloodType get(@PathVariable("id") Integer id) {
//        return bloodTypeService.findById(id);
//    }
}
