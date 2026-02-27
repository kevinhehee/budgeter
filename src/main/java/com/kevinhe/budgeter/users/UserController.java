package com.kevinhe.budgeter.users;


import com.kevinhe.budgeter.budgets.Budget;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    public static class CreateUserRequest {
        @NotBlank
        private String displayName;

        @NotBlank
        private String email;

        public String getDisplayName() {
            return this.displayName;
        }

        public String getEmail() {
            return this.email;
        }
    }

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/budgets")
    public List<Budget> getBudgets(@PathVariable UUID id) {
        return userService.getBudgets(id);
    }


}
