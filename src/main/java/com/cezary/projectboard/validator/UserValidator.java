package com.cezary.projectboard.validator;

import com.cezary.projectboard.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    /**
     * In this method we defaine which class we want to validate
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        if (user.getPassword().length() < 6) {
            errors.rejectValue("password", "Length", "Pasword must be at least 6 characters");
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Match", "Passwords must match");
        }
    }
}
