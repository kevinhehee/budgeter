package com.kevinhe.budgeter.auth;

import com.kevinhe.budgeter.users.User;
import com.kevinhe.budgeter.users.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LocalCurrentUserProvider implements CurrentUserProvider {
    private static final UUID LOCAL_USER_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Override
    public UUID currentUserId() {
        return LOCAL_USER_ID;
    }


}
