package com.activegym.activegym.service.Users;

import com.activegym.activegym.dto.ResponseStatusMessage;
import com.activegym.activegym.dto.users.UserAccessResponseDTO;
import com.activegym.activegym.exceptions.UserNotFoundException;
import com.activegym.activegym.model.Users.AccessLog;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.repository.Memberships.MembershipRepository;
import com.activegym.activegym.repository.Users.AccessLogRepository;
import com.activegym.activegym.repository.Users.UserRepository;
import com.activegym.activegym.util.FormatDateTime;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AccessControlService {
    private final UserRepository userRepository;
    private final AccessLogRepository accessLogRepository;
    private final MembershipRepository membershipRepository;
    private final FormatDateTime formatDateTime;
    private static final List<String> teamRoles = List.of("ADMINISTRADOR", "ASESOR", "ENTRENADOR", "PERSONAL DE ASEO");

    public String access(String document) {
        User user = userRepository.findByDocument(document)
            .orElseThrow(() -> new UserNotFoundException(""));

        AccessLog accessLog = new AccessLog();

        boolean successStatus;

        boolean isTeamMember = user.getRoles().stream()
            .anyMatch(role -> teamRoles.contains(role.getRoleName()));

        if (isTeamMember) {
            successStatus = true;
        } else {
            successStatus = membershipRepository.existsActiveMembership(user.getId());
        }
        accessLog.setAccessDateTime(LocalDateTime.now());
        accessLog.setSuccess(successStatus);
        accessLog.setUserId(user);
        accessLogRepository.save(accessLog);

        return successStatus ? "INGRESO EXITOSO" : "INGRESO DENEGADO";
    }

    public Page<UserAccessResponseDTO> getAccessLogs(int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

        return accessLogRepository.findAll(pageable)
                .map(accessLog -> new UserAccessResponseDTO(
                        accessLog.getId(),
                        formatDateTime.formatDateTime(accessLog.getAccessDateTime()),
                        accessLog.getSuccess(),
                        accessLog.getUserId().getDocument()
                ));
    }

    public Page<UserAccessResponseDTO> getAccessLogsByDocument(String document, int page, int size) {

        User user = userRepository.findByDocument(document)
            .orElseThrow(() -> new UserNotFoundException(""));

        PageRequest pageable = PageRequest.of(page, size);

        return accessLogRepository.findAllByUserId(user, pageable)
                .map(accessLog -> new UserAccessResponseDTO(
                        accessLog.getId(),
                        formatDateTime.formatDateTime(accessLog.getAccessDateTime()),
                        accessLog.getSuccess(),
                        accessLog.getUserId().getDocument()
                ));
    }
}
