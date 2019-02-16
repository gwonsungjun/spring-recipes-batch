package com.sungjun.springrecipesbatch.custom;

import com.sungjun.springrecipesbatch.user.UserRegistration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserRegistrationServiceItemWriter implements ItemWriter<UserRegistration> {

    private final UserRegistrationService userRegistrationService;
    private final RetryTemplate retryTemplate;

    @Override
    public void write(List<? extends UserRegistration> items) throws Exception {
        for (final UserRegistration userRegistration : items) {
            UserRegistration registeredUserRegistration = retryTemplate.execute(
                    (RetryCallback<UserRegistration, Exception>) context -> userRegistrationService.registerUser(userRegistration));
            log.debug("Registered: {}", registeredUserRegistration);
        }
    }
}
