package com.activegym.activegym.controller.Users.auxiliary;


import com.activegym.activegym.model.Users.auxiliary.Gender;
import com.activegym.activegym.service.Users.auxiliary.GenderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin // TEMPORAL FEATURE
@AllArgsConstructor
@RequestMapping("/genders")
@RestController
public class GenderController {

    private final GenderService genderService; // Injected by Lombok

    @GetMapping
    public Iterable<Gender> list() {
        return genderService.findAll();
    }

    @GetMapping("/{id}")
    public Gender get(@PathVariable("id") Integer id) {
        return genderService.findById(id);
    }

}
