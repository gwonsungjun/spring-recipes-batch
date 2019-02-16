package com.sungjun.springrecipesbatch.custom;

import com.sungjun.springrecipesbatch.user.UserRegistration;

import java.util.Collection;
import java.util.Date;

public interface UserRegistrationService {

    Collection<UserRegistration> getOutstandingUserRegistrationBatchForDate(int quantity, Date date);
    UserRegistration registerUser(UserRegistration userRegistrationRegistration);
}
