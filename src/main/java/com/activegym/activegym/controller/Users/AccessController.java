package com.activegym.activegym.controller.Users;

import com.activegym.activegym.dto.ResponseStatusMessage;
import com.activegym.activegym.dto.users.UserAccessDTO;
import com.activegym.activegym.dto.users.UserAccessResponseDTO;
import com.activegym.activegym.service.Users.AccessControlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://activegym.vercel.app/"})
@AllArgsConstructor
@RequestMapping("api/access")
@RestController
@Tag(name = "Access Management", description = "Endpoints for managing user access to the gym")
public class AccessController {
    private final AccessControlService accessControlService;
    private final ResponseStatusMessage responseStatusMessage;

    @Operation(
            summary = "Register user access",
            description = "Registers an access attempt for a user using their document number. Requires ADMINISTRADOR or ASESOR authority.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Access successfully registered", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "403", description = "Unauthorized to perform this action"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PostMapping
    public ResponseEntity<ResponseStatusMessage> access(@RequestBody UserAccessDTO userAccessDTO) {
        String document = userAccessDTO.getDocument();
        responseStatusMessage.setMessage(accessControlService.access(document));
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);
    }

    @Operation(
            summary = "Get all access logs",
            description = "Retrieves a paginated list of all user access logs. Requires ADMINISTRADOR authority.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Access logs retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAccessResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Unauthorized to perform this action")
            }
    )
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<Page<UserAccessResponseDTO>> getAccessLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(accessControlService.getAccessLogs(page, size));
    }

    @Operation(
            summary = "Get access logs by document",
            description = "Retrieves a paginated list of all user access logs for a specific user. Requires ADMINISTRADOR or ASESOR authority.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Access logs retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAccessResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Unauthorized to perform this action")
            }
    )
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR')")
    @GetMapping("/{document}")
    public ResponseEntity<Page<UserAccessResponseDTO>> getAccessLogsByDocument(
            @PathVariable("document") String document,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(accessControlService.getAccessLogsByDocument(document, page, size));
    }
}
