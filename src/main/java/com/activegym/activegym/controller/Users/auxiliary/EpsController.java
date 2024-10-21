package com.activegym.activegym.controller.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.Eps;
import com.activegym.activegym.service.Users.auxiliary.EpsService;
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
@RequestMapping("/eps")
@Tag(name = "Eps", description = "Controller for fetching auxiliary data about Users")
public class EpsController {

    private final EpsService epsService;

    @GetMapping
    @Operation(summary = "Get all EPS")
    public Iterable<Eps> list() {
        return epsService.findAll();
    }

//    @GetMapping("/{id}")
//    public Eps get(@PathVariable("id") Integer id) {
//        return epsService.findById(id);
//    }
}
