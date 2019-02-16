package com.sungjun.springrecipesbatch.custom;

import com.sungjun.springrecipesbatch.user.UserRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Collection;
import java.util.Date;

@RequiredArgsConstructor
public class UserRegistrationItemReader implements ItemReader<UserRegistration> {

    private final UserRegistrationService userRegistrationService;

    @Override
    public UserRegistration read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        final Date today = new Date();
        Collection<UserRegistration> registrations = userRegistrationService.getOutstandingUserRegistrationBatchForDate(1, today);
        return registrations.stream().findFirst().orElse(null);
    }
}
