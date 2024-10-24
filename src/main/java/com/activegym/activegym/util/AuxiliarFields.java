package com.activegym.activegym.util;

import com.activegym.activegym.dto.AdminDTO;
import com.activegym.activegym.dto.UserDTO;
import com.activegym.activegym.model.Users.auxiliary.BloodRh;
import com.activegym.activegym.model.Users.auxiliary.BloodType;
import com.activegym.activegym.model.Users.auxiliary.Eps;
import com.activegym.activegym.model.Users.auxiliary.Gender;
import com.activegym.activegym.model.Users.User;
import com.activegym.activegym.repository.Users.auxiliary.BloodRhRepository;
import com.activegym.activegym.repository.Users.auxiliary.BloodTypeRepository;
import com.activegym.activegym.repository.Users.auxiliary.EpsRepository;
import com.activegym.activegym.repository.Users.auxiliary.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuxiliarFields {

    private final EpsRepository epsRepository;
    private final BloodTypeRepository bloodTypeRepository;
    private final BloodRhRepository bloodRhRepository;
    private final GenderRepository genderRepository;

    public void castUserAuxiliarFields(UserDTO userDTO, User user) {
        Eps eps = epsRepository.findByEpsName(userDTO.getEpsName())
                .orElseThrow(() -> new RuntimeException("EPS no encontrado"));
        BloodType bloodType = bloodTypeRepository.findByBloodTypeName(userDTO.getBloodTypeName())
                .orElseThrow(() -> new RuntimeException("Tipo de sangre no encontrado"));
        BloodRh bloodRh = bloodRhRepository.findByBloodRh(userDTO.getBloodRhName())
                .orElseThrow(() -> new RuntimeException("Factor Rh no encontrado"));
        Gender gender = genderRepository.findByGenderName(userDTO.getGenderName())
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));

        user.setEps(eps);
        user.setBloodType(bloodType);
        user.setBloodRh(bloodRh);
        user.setGender(gender);
    }

//    public void castAdminAuxiliarFields(AdminDTO adminDTO, User admin) {
//        Eps eps = epsRepository.findByEpsName(adminDTO.getEpsName())
//                .orElseThrow(() -> new RuntimeException("EPS no encontrado"));
//        BloodType bloodType = bloodTypeRepository.findByBloodTypeName(adminDTO.getBloodTypeName())
//                .orElseThrow(() -> new RuntimeException("Tipo de sangre no encontrado"));
//        BloodRh bloodRh = bloodRhRepository.findByBloodRh(adminDTO.getBloodRhName())
//                .orElseThrow(() -> new RuntimeException("Factor Rh no encontrado"));
//        Gender gender = genderRepository.findByGenderName(adminDTO.getGenderName())
//                .orElseThrow(() -> new RuntimeException("Género no encontrado"));
//
//        admin.setEps(eps);
//        admin.setBloodType(bloodType);
//        admin.setBloodRh(bloodRh);
//        admin.setGender(gender);
//    }
}
