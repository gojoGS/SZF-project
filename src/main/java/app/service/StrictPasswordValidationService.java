package app.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;

@AllArgsConstructor
public class StrictPasswordValidationService implements PasswordValidationService {
    private int minimumPasswordLength;

    @Override
    public boolean isValidPassword(String password) {
        if (password.length() < minimumPasswordLength) {
            return false;
        }

        return StringUtils.isAlphanumeric(password);
    }
}

