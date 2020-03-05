/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/2/26 14:29:02                           */
/*==============================================================*/


SET foreign_key_checks = 0;


drop table if exists _security_function;

drop table if exists _security_function_resource_pattern;

drop table if exists _security_global_resource_pattern;

drop table if exists _security_role;

drop table if exists _security_role_function;

drop table if exists _security_role_user;

/*==============================================================*/
/* Table: _security_function                                    */
/*==============================================================*/
create table _security_function
(
   id                   bigint not null comment 'ID',
   parent_id            bigint not null comment '父ID',
   name                 varchar(31) not null comment '名称',
   note                 varchar(255) comment '备注',
   primary key (id),
   unique key AK_Key_2 (name)
);

/*==============================================================*/
/* Table: _security_function_resource_pattern                   */
/*==============================================================*/
create table _security_function_resource_pattern
(
   id                   bigint not null comment 'ID',
   function_id          bigint comment '所属功能ID',
   pattern              varchar(255) not null comment '资源规则',
   note                 varchar(255) comment '备注',
   primary key (id)
);

/*==============================================================*/
/* Table: _security_global_resource_pattern                     */
/*==============================================================*/
create table _security_global_resource_pattern
(
   id                   bigint not null comment 'ID',
   pattern              varchar(255) not null comment '资源规则',
   authority            varchar(31) not null default 'permitAll' comment '权限(permitAll, ...)',
   note                 varchar(255) comment '备注',
   primary key (id)
);

/*==============================================================*/
/* Table: _security_role                                        */
/*==============================================================*/
create table _security_role
(
   id                   bigint not null comment 'ID',
   name                 varchar(31) not null comment '角色名称',
   note                 varchar(255) comment '备注',
   primary key (id),
   unique key AK_Key_2 (name)
);

/*==============================================================*/
/* Table: _security_role_function                               */
/*==============================================================*/
create table _security_role_function
(
   id                   bigint not null auto_increment comment 'ID',
   role_id              bigint not null comment '角色ID',
   function_id          bigint not null comment '功能ID',
   primary key (id),
   unique key AK_Key_2 (role_id, function_id)
);

/*==============================================================*/
/* Table: _security_role_user                                   */
/*==============================================================*/
create table _security_role_user
(
   id                   bigint not null auto_increment comment 'ID',
   role_id              bigint not null comment '角色ID',
   user_id              bigint not null comment '用户ID',
   primary key (id),
   unique key AK_Key_2 (role_id, user_id)
);

alter table _security_function_resource_pattern add constraint FK_Reference_4 foreign key (function_id)
      references _security_function (id) on delete set null on update restrict;

alter table _security_role_function add constraint FK_Reference_2 foreign key (role_id)
      references _security_role (id) on delete cascade on update restrict;

alter table _security_role_function add constraint FK_Reference_3 foreign key (function_id)
      references _security_function (id) on delete cascade on update restrict;

alter table _security_role_user add constraint FK_Reference_1 foreign key (role_id)
      references _security_role (id) on delete cascade on update restrict;

      
ALTER TABLE `_security_function_resource_pattern` DROP FOREIGN KEY `_security_function_resource_pattern_ibfk_1`;

ALTER TABLE `_security_function_resource_pattern` ADD CONSTRAINT `_security_function_resource_pattern_ibfk_1` FOREIGN KEY (`function_id`) REFERENCES `_security_function` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;



SET foreign_key_checks = 1;
