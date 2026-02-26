package com.kevinhe.budgeter.auth;

import java.util.UUID;

public interface CurrentUserProvider {
    UUID currentUserId();
}
