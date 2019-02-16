package com.sungjun.springrecipesbatch.custom;

import com.sungjun.springrecipesbatch.user.UserRegistration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserRegistrationServiceItemWriter implements ItemWriter<UserRegistration> {

    private final UserRegistrationService userRegistrationService;

    @Override
    public void write(List<? extends UserRegistration> items) throws Exception {
        items.forEach(this::write);
    }

    private void write(UserRegistration userRegistration){
        UserRegistration registeredUserRegistration = userRegistrationService.registerUser(userRegistration);
        log.debug("Registered: {}", registeredUserRegistration);
    }
}
