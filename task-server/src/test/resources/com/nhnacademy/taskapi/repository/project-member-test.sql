insert into account
values (1),
       (2),
       (3);

insert into project(project_id, project_name, project_status)
values (1,'dooray-active1', 'ACTIVE');

insert
into project_member(project_member_id, account_id, project_id)
values (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1);

