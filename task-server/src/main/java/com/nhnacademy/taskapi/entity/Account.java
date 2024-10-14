package com.nhnacademy.taskapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    @Id
    private Long accountId;

    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
    private List<Project> projects;


    public Account(Long accountId) {
        this.accountId = accountId;
    }
}
