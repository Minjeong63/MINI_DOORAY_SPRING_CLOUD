package com.nhnacademy.minidoorayaccount.entity;

import com.nhnacademy.minidoorayaccount.entity.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotNull
    @Size(max = 20)
    private String userId;

    @NotNull
    @Size(max = 100)
    @Setter
    private String password;

    @NotNull
    @Size(max = 50)
    @Setter
    private String email;

    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    UserStatus status;

    public Account(String userId, String password, String email, UserStatus status) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.status = status;
    }
}
