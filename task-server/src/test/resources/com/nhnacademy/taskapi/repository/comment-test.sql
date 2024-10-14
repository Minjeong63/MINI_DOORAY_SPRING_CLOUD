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

-- task 생성 - milestone_id 추가
insert into task (project_id, mile_stone_id, task_content)
values (1, 1, 'UI 설계'),
       (1, 2, 'DB 설계'),
       (1, 3, '테스트');

-- 코멘트 데이터 삽입
INSERT INTO comment (comment_content, task_id)
VALUES ('This is a comment for Task 1', 1);

INSERT INTO comment (comment_content, task_id)
VALUES ('Another comment for Task 1', 1);

-- Task 2가 존재하는지 확인한 후 실행
INSERT INTO comment (comment_content, task_id)
VALUES ('This is a comment for Task 2', 2);
