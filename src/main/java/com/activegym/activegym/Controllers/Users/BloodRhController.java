package com.activegym.activegym.Controllers.Users;


import com.activegym.activegym.Entities.Users.BloodRh;
import com.activegym.activegym.Services.Users.BloodRhService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin // TEMPORAL FEATURE
@AllArgsConstructor
@RestController
@RequestMapping("/bloodrh")
public class BloodRhController {

    private final BloodRhService bloodRhService;

    @GetMapping
    public Iterable<BloodRh> list() {
        return bloodRhService.findAll();
    }

    @GetMapping("/{id}")
    public BloodRh get(@PathVariable("id") Integer id) {
        return bloodRhService.findById(id);
    }
}
