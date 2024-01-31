package com.nailasahidah.pendaftaran.utils;

import com.nailasahidah.pendaftaran.domain.UserPrincipal;
import com.nailasahidah.pendaftaran.dto.UserDTO;
import org.springframework.security.core.Authentication;

public class UserUtils {
    public static UserDTO getAuthenticatedUser(Authentication authentication) {
        return ((UserDTO) authentication.getPrincipal());
    }
    public static UserDTO getLoggedInUser(Authentication authentication) {
        return ((UserPrincipal) authentication.getPrincipal()).getUser();
    }

}
