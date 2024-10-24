package com.activegym.activegym.controller.Users.auxiliary;


import com.activegym.activegym.model.Users.auxiliary.BloodRh;
import com.activegym.activegym.service.Users.auxiliary.BloodRhService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200, https://activegym.vercel.app/"})
@AllArgsConstructor
@RestController
@RequestMapping("/bloodrh")
@Tag(name = "BloodRh", description = "Controller for fetching auxiliary data about Users")
public class BloodRhController {

    private final BloodRhService bloodRhService;

    @GetMapping
    @Tag(name = "BloodRh", description = "Get all blood Rh")
    public Iterable<BloodRh> list() {
        return bloodRhService.findAll();
    }

//    @GetMapping("/{id}")
//    public BloodRh get(@PathVariable("id") Integer id) {
//        return bloodRhService.findById(id);
//    }
}
