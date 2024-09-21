package com.activegym.activegym.Controllers;

import com.activegym.activegym.Entities.Eps;
import com.activegym.activegym.Services.EpsService;
import com.activegym.activegym.Services.GenderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin // TEMPORAL FEATURE
@AllArgsConstructor
@RestController
@RequestMapping("/eps")
public class EpsController {

    private final EpsService epsService;

    @GetMapping
    public Iterable<Eps> list() {
        return epsService.findAll();
    }

    @GetMapping("/{id}")
    public Eps get(@PathVariable("id") Integer id) {
        return epsService.findById(id);
    }
}
