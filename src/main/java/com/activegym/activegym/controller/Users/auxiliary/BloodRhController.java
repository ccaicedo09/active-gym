package com.activegym.activegym.controller.Users.auxiliary;


import com.activegym.activegym.model.Users.auxiliary.BloodRh;
import com.activegym.activegym.service.Users.auxiliary.BloodRhService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Blood Rh Controller", description = "Controller for fetching auxiliary data about Users")
public class BloodRhController {

    private final BloodRhService bloodRhService;

    @GetMapping
    @Operation(summary = "List all blood Rh types", description = "This endpoint returns all blood Rh types available in the system.")
    @ApiResponse(responseCode = "200", description = "List of blood Rh types")
    public Iterable<BloodRh> list() {
        return bloodRhService.findAll();
    }

//    @GetMapping("/{id}")
//    public BloodRh get(@PathVariable("id") Integer id) {
//        return bloodRhService.findById(id);
//    }
}
