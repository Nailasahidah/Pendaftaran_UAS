package com.nailasahidah.pendaftaran.service;

import com.nailasahidah.pendaftaran.domain.User;
import com.nailasahidah.pendaftaran.dto.UserDTO;
import com.nailasahidah.pendaftaran.form.UpdateForm;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDTO createUser (User user);
    UserDTO getUserByEmail(String email);
    UserDTO getUserByPhone(String phone);
    void sendVerificationCode(UserDTO user);
    UserDTO verifyCode(String email, String code);
    void updatePassword(Long userId, String password, String confirmPassword);
    void resetPassword(String email);
    UserDTO verifyPasswordKey(String key);
    void renewPassword(String key, String password, String confirmPassword);
    UserDTO verifyAccountKey(String key);
    UserDTO updateUserDetails(UpdateForm user);
    UserDTO getUserById(Long userId);
    void updatePassword(Long id, String currentPassword, String newPassword, String confirmNewPassword);
    void updateUserRole(Long userId, String roleName);
    void updateAccountSettings(Long UserId, Boolean enabled, Boolean notLocked);
    UserDTO toggleMfa(String email);
    void updateImage(UserDTO user, MultipartFile image);
}
