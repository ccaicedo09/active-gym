package com.activegym.activegym.controller.Users;

import com.activegym.activegym.dto.ResponseStatusMessage;
import com.activegym.activegym.dto.users.UserAccessDTO;
import com.activegym.activegym.dto.users.UserAccessResponseDTO;
import com.activegym.activegym.service.Users.AccessControlService;
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
public class AccessController {
    private final AccessControlService accessControlService;
    private final ResponseStatusMessage responseStatusMessage;

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @PostMapping
    public ResponseEntity<ResponseStatusMessage> access(@RequestBody UserAccessDTO userAccessDTO) {
        String document = userAccessDTO.getDocument();
        responseStatusMessage.setMessage(accessControlService.access(document));
        return ResponseEntity.status(HttpStatus.OK).body(responseStatusMessage);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping
    public ResponseEntity<Page<UserAccessResponseDTO>> getAccessLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(accessControlService.getAccessLogs(page, size));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ASESOR')")
    @GetMapping("/{document}")
    public ResponseEntity<Page<UserAccessResponseDTO>> getAccessLogsByDocument(
            @PathVariable("document") String document,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(accessControlService.getAccessLogsByDocument(document, page, size));
    }
}
