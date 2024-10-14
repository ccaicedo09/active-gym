package com.activegym.activegym.controller.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.Eps;
import com.activegym.activegym.service.Users.auxiliary.EpsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@AllArgsConstructor
@RestController
@RequestMapping("/eps")
public class EpsController {

    private final EpsService epsService;

    @GetMapping
    public Iterable<Eps> list() {
        return epsService.findAll();
    }

//    @GetMapping("/{id}")
//    public Eps get(@PathVariable("id") Integer id) {
//        return epsService.findById(id);
//    }
}
