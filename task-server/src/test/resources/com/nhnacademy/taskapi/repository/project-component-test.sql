insert into account
values (1);

insert into project(project_id, account_id, project_name, project_status)
values (1, 1, 'dooray-active1', 'ACTIVE');

insert into milestone(mile_stone_id, project_id, progress)
values (1, 1, 'UI 설계'),
       (2, 1, 'DB 설계'),
       (3, 1, '테스트');

insert into tag (project_id, tag_name)
values (1, 'DB'),
       (1, 'UI');
       