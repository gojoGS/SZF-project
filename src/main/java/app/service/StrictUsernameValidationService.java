package app.service;

import org.apache.commons.lang.StringUtils;

public class StrictUsernameValidationService implements UsernameValidationService {

    @Override
    public boolean isValidUsername(String username) {
        if (username.length() == 0) {
            return false;
        }

        return StringUtils.isAlphanumeric(username);
    }
}
