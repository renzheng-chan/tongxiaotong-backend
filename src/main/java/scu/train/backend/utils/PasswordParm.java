package scu.train.backend.utils;

import org.springframework.stereotype.Component;

@Component
public class PasswordParm {

    public String OldPassword;

    public String NewPassword;

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }
}
