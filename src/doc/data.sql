???prompt PL/SQL Developer import file
prompt Created on 2021年3月8日 by Administrator
set feedback off
set define off
prompt Creating API_ROLE...
create table API_ROLE
(
  id       VARCHAR2(64),
  name     VARCHAR2(300),
  crt_time DATE,
  crt_user VARCHAR2(255),
  crt_name VARCHAR2(255),
  crt_host VARCHAR2(255),
  upd_time DATE,
  upd_user VARCHAR2(255),
  upd_name VARCHAR2(255),
  upd_host VARCHAR2(255),
  attr1    VARCHAR2(255),
  attr2    VARCHAR2(255),
  attr3    VARCHAR2(255),
  attr4    VARCHAR2(255)
)
tablespace WORKHUB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating API_USER...
create table API_USER
(
  id       VARCHAR2(64),
  name     VARCHAR2(300),
  pwd      VARCHAR2(3000),
  crt_time DATE,
  crt_user VARCHAR2(255),
  crt_name VARCHAR2(255),
  crt_host VARCHAR2(255),
  upd_time DATE,
  upd_user VARCHAR2(255),
  upd_name VARCHAR2(255),
  upd_host VARCHAR2(255),
  attr1    VARCHAR2(255),
  attr2    VARCHAR2(255),
  attr3    VARCHAR2(255),
  attr4    VARCHAR2(255),
  token    VARCHAR2(255)
)
tablespace WORKHUB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating API_USER_ROLE...
create table API_USER_ROLE
(
  id       VARCHAR2(64),
  user_id  VARCHAR2(64),
  role_id  VARCHAR2(64),
  crt_time DATE,
  crt_user VARCHAR2(255),
  crt_name VARCHAR2(255),
  crt_host VARCHAR2(255),
  upd_time DATE,
  upd_user VARCHAR2(255),
  upd_name VARCHAR2(255),
  upd_host VARCHAR2(255),
  attr1    VARCHAR2(255),
  attr2    VARCHAR2(255),
  attr3    VARCHAR2(255),
  attr4    VARCHAR2(255)
)
tablespace WORKHUB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for API_ROLE...
alter table API_ROLE disable all triggers;
prompt Disabling triggers for API_USER...
alter table API_USER disable all triggers;
prompt Disabling triggers for API_USER_ROLE...
alter table API_USER_ROLE disable all triggers;
prompt Loading API_ROLE...
insert into API_ROLE (id, name, crt_time, crt_user, crt_name, crt_host, upd_time, upd_user, upd_name, upd_host, attr1, attr2, attr3, attr4)
values ('1', 'ROLE_ADMIN', null, null, null, null, null, null, null, null, null, null, null, null);
commit;
prompt 1 records loaded
prompt Loading API_USER...
insert into API_USER (id, name, pwd, crt_time, crt_user, crt_name, crt_host, upd_time, upd_user, upd_name, upd_host, attr1, attr2, attr3, attr4, token)
values ('nybDt7bs', 'admin', '$2a$10$xRjdXS7yRzggWYhmIHVi2uhdkK8eYQJ97ZDxpgSe2WduamAb6i4z2', to_date('04-03-2021 09:44:58', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, to_date('04-03-2021 15:21:44', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b20iLCJjcmVhdGVkIjoxNjE0ODQyNTA0MjY2LCJleHAiOjE2MTQ4NDQzMDR9.j3kwidH_XMNum1H75v4nZ8uEtxdDv_D8LLlRkFq-glmYuK0vQUkxxZZOdCHtYoD9iER9VItIetNehXxbOuLd_g');
insert into API_USER (id, name, pwd, crt_time, crt_user, crt_name, crt_host, upd_time, upd_user, upd_name, upd_host, attr1, attr2, attr3, attr4, token)
values ('nybDt7bT', 'tom', '$2a$10$xRjdXS7yRzggWYhmIHVi2uhdkK8eYQJ97ZDxpgSe2WduamAb6i4z2', to_date('04-03-2021 09:44:58', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, to_date('04-03-2021 15:21:44', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b20iLCJjcmVhdGVkIjoxNjE0ODQyNTA0MjY2LCJleHAiOjE2MTQ4NDQzMDR9.j3kwidH_XMNum1H75v4nZ8uEtxdDv_D8LLlRkFq-glmYuK0vQUkxxZZOdCHtYoD9iER9VItIetNehXxbOuLd_g');
insert into API_USER (id, name, pwd, crt_time, crt_user, crt_name, crt_host, upd_time, upd_user, upd_name, upd_host, attr1, attr2, attr3, attr4, token)
values ('2cfplkcX', 'jerry', '$2a$10$pfpgLlW7zoIm0gJcbRjITuOkKUBW7yiKrWG7qYA//J6DJcqeBJf1O', to_date('05-03-2021 16:10:48', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, to_date('05-03-2021 16:10:48', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, null);
commit;
prompt 3 records loaded
prompt Loading API_USER_ROLE...
insert into API_USER_ROLE (id, user_id, role_id, crt_time, crt_user, crt_name, crt_host, upd_time, upd_user, upd_name, upd_host, attr1, attr2, attr3, attr4)
values ('1', 'nybDt7bT', '1', null, null, null, null, null, null, null, null, null, null, null, null);
commit;
prompt 1 records loaded
prompt Enabling triggers for API_ROLE...
alter table API_ROLE enable all triggers;
prompt Enabling triggers for API_USER...
alter table API_USER enable all triggers;
prompt Enabling triggers for API_USER_ROLE...
alter table API_USER_ROLE enable all triggers;
set feedback on
set define on
prompt Done.
