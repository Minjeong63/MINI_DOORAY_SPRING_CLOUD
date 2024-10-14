package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Account;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    boolean existsByProjectAndMember(Project project, Account account);

    @Query("SELECT pm.project FROM ProjectMember pm WHERE pm.member.accountId = :accountId")
    List<Project> findProjectsByAccountId(Long accountId);

    Optional<ProjectMember> findByProjectAndMember(Project project, Account account);

    void deleteByProject(Project project);
}
