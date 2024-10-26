package com.activegym.activegym.util;

import com.activegym.activegym.dto.UserDTO;
import com.activegym.activegym.exceptions.BloodRhNotFoundException;
import com.activegym.activegym.exceptions.BloodTypeNotFoundException;
import com.activegym.activegym.exceptions.EpsNotFoundException;
import com.activegym.activegym.exceptions.GenderNotFoundException;
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

/**
 * Service class for managing auxiliary fields related to users.
 * This class contains the business logic for setting auxiliary fields such as EPS, Blood Type, Blood Rh, and Gender.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Service
@RequiredArgsConstructor
public class AuxiliarFields {

    private final EpsRepository epsRepository;
    private final BloodTypeRepository bloodTypeRepository;
    private final BloodRhRepository bloodRhRepository;
    private final GenderRepository genderRepository;

    /**
     * Casts and sets the auxiliary fields for a user based on the provided UserDTO.
     *
     * @param userDTO the data transfer object containing user information (strings).
     * @param user the user entity to set the auxiliary fields for.
     * @throws EpsNotFoundException if the EPS is not found in the database.
     * @throws BloodTypeNotFoundException if the Blood Type is not found in the database.
     * @throws BloodRhNotFoundException if the Blood Rh is not found in the database
     * @throws GenderNotFoundException if the Gender is not found in the database.
     */
    public void castUserAuxiliarFields(UserDTO userDTO, User user) {
        Eps eps = epsRepository.findByEpsName(userDTO.getEpsName())
                .orElseThrow(() -> new EpsNotFoundException("EPS no encontrado"));
        BloodType bloodType = bloodTypeRepository.findByBloodTypeName(userDTO.getBloodTypeName())
                .orElseThrow(() -> new BloodTypeNotFoundException("Tipo de sangre no encontrado"));
        BloodRh bloodRh = bloodRhRepository.findByBloodRh(userDTO.getBloodRhName())
                .orElseThrow(() -> new BloodRhNotFoundException("Factor Rh no encontrado"));
        Gender gender = genderRepository.findByGenderName(userDTO.getGenderName())
                .orElseThrow(() -> new GenderNotFoundException("Género no encontrado"));

        user.setEps(eps);
        user.setBloodType(bloodType);
        user.setBloodRh(bloodRh);
        user.setGender(gender);
    }

// Uncomment and use this method if needed for admin auxiliary fields.
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
