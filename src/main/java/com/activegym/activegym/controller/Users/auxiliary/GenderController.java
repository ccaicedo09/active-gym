package com.activegym.activegym.controller.Users.auxiliary;


import com.activegym.activegym.model.Users.auxiliary.Gender;
import com.activegym.activegym.service.Users.auxiliary.GenderService;
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
@RequestMapping("/genders")
@RestController
@Tag(name = "Genders Controller", description = "Controller for fetching auxiliary data about Users")
public class GenderController {

    private final GenderService genderService; // Injected by Lombok

    @GetMapping
    @Operation(summary = "List all Genders", description="List all Genders that can be assigned to a user")
    @ApiResponse(responseCode = "200", description ="List of genders")
    public Iterable<Gender> list() {
        return genderService.findAll();
    }

//    @GetMapping("/{id}")
//    public Gender get(@PathVariable("id") Integer id) {
//        return genderService.findById(id);
//    }

}
