-- project 생성
insert into project(project_name, project_status)
values ('dooray-active1', 'ACTIVE'),
       ('dooray-active2', 'ACTIVE'),
       ('dooray-active3', 'ACTIVE'),
       ('completed-project1', 'COMPLETE'),
       ('completed-project2', 'COMPLETE'),
       ('completed-project3', 'COMPLETE'),
       ('inactive-project1', 'INACTIVE'),
       ('inactive-project2', 'INACTIVE'),
       ('inactive-project3', 'INACTIVE');

-- milestone 생성
insert into milestone (project_id, progress)
values (1, 'UI 설계'),
       (1, 'DB 설계'),
       (1, '테스트');

-- task 생성
insert into task (project_id, task_content)
values (1, 'UI 설계'),
       (1, 'DB 설계'),
       (1, '테스트');
