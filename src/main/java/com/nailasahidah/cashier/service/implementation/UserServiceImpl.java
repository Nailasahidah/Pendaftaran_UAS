package com.nailasahidah.cashier.service.implementation;

import com.nailasahidah.cashier.domain.Role;
import com.nailasahidah.cashier.domain.User;
import com.nailasahidah.cashier.dto.UserDTO;
import com.nailasahidah.cashier.form.UpdateForm;
import com.nailasahidah.cashier.repository.RoleRepository;
import com.nailasahidah.cashier.repository.UserRepository;
import com.nailasahidah.cashier.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

import static com.nailasahidah.cashier.dtomapper.UserDTOMapper.fromUser;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;
    private final RoleRepository<Role> roleRoleRepository;

    @Override
    public UserDTO createUser(User user) {
        validateFullName(user.getFullName());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        return mapToUserDTO(userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return mapToUserDTO(userRepository.getUserByEmail(email));
    }

    @Override
    public UserDTO getUserByPhone(String phone) {
        return mapToUserDTO(userRepository.getUserByPhone(phone));
    }

    @Override
    public void sendVerificationCode(UserDTO user) {
        userRepository.sendVerificationCode(user);
    }

    @Override
    public UserDTO verifyCode(String email, String code) {
        return mapToUserDTO(userRepository.verifyCode(email, code));
    }

    @Override
    public void updatePassword(Long userId, String password, String confirmPassword) {
        userRepository.renewPassword(userId, password, confirmPassword);
    }

    @Override
    public void resetPassword(String email) {
        userRepository.resetPassword(email);
    }

    @Override
    public UserDTO verifyPasswordKey(String key) {
        return mapToUserDTO(userRepository.verifyPasswordKey(key));
    }

    @Override
    public void renewPassword(String key, String password, String confirmPassword) {
        userRepository.renewPassword(key, password, confirmPassword);
    }

    @Override
    public UserDTO verifyAccountKey(String key) {
        return mapToUserDTO(userRepository.verifyAccountKey(key));
    }

    @Override
    public UserDTO updateUserDetails(UpdateForm user) {
        return mapToUserDTO(userRepository.updateUserDetails(user));
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return mapToUserDTO(userRepository.get(userId));
    }

    @Override
    public void updatePassword(Long id, String currentPassword, String newPassword, String confirmNewPassword) {
        userRepository.updatePassword(id, currentPassword, newPassword, confirmNewPassword);
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {
        roleRoleRepository.updateUserRole(userId, roleName);
    }

    @Override
    public void updateAccountSettings(Long userId, Boolean enabled, Boolean notLocked) {
        userRepository.updateAccountSettings(userId, enabled, notLocked);
    }

    @Override
    public UserDTO toggleMfa(String email) {
        return mapToUserDTO(userRepository.toggleMfa(email));
    }

    @Override
    public void updateImage(UserDTO user, MultipartFile image) {
        userRepository.updateImage(user, image);
    }


    private UserDTO mapToUserDTO(User user) {
        return fromUser(user, roleRoleRepository.getRoleByUserId(user.getId()));
    }
    private void validatePassword(String password) {
        if (password.length() < 5 || !containsLetterAndDigit(password)) {
            throw new IllegalArgumentException("Invalid password. Password must be at least 5 characters long and contain at least one letter and one digit.");
        }
    }
    private void validateEmail(String email) {
        if (!isValidEmailFormat(email)) {
            throw new IllegalArgumentException("Invalid email address.");
        }
    }
    private void validateFullName(String fullName) {
        if (fullName.length() < 3) {
            throw new IllegalArgumentException("Invalid full name. Full name must be at least 3 characters long.");
        }
    }
    public void validatePhone(String phone) {
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone number. Phone must contain only digits and '+' sign.");
        }
    }

    private boolean containsLetterAndDigit(String password) {
        return password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*");
    }

    private boolean isValidEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[+0-9]+$";
        return Pattern.matches(phoneRegex, phone);
    }
}
