package com.activegym.activegym.controller.Users.auxiliary;

import com.activegym.activegym.model.Users.auxiliary.Eps;
import com.activegym.activegym.service.Users.auxiliary.EpsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200, https://activegym.vercel.app/"})
@AllArgsConstructor
@RestController
@RequestMapping("/eps")
@Tag(name = "Eps Controller", description = "Controller for fetching auxiliary data about Users")
public class EpsController {

    private final EpsService epsService;

    @GetMapping
    @Operation(summary = "List all EPS", description = "List all EPS available in the system")
    @ApiResponse(responseCode = "200", description = "List of EPS")
    public Iterable<Eps> list() {
        return epsService.findAll();
    }

//    @GetMapping("/{id}")
//    public Eps get(@PathVariable("id") Integer id) {
//        return epsService.findById(id);
//    }
}
