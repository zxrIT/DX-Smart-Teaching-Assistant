create database dx_smart_training_talents default char set 'utf8mb4';

create table training_talents
(
    training_talent_id            varchar(255) primary key comment '人才培养方案唯一ID',
    educational_system            int comment '学制'             default 0                  not null,
    years_schooling_min           int comment '修业最少年限'     default 3                  not null,
    years_schooling_max           int comment '修业最多年限'     default 6                  not null,
    graduation_credit             double comment '毕业学分'                                 not null,
    object_enrollment             varchar(64) comment '招生对象' default '全日制高中毕业生' not null,
    professional_orientation      text comment '专业定位'                                   not null,
    objectives_training           text comment '培养目标'                                   not null,
    objectives_training_target_id varchar(255) comment '毕业5年预期ID'                      not null,
    graduation_requirements_id    varchar(255) comment '毕业要求ID'                         not null
) default char set 'utf8mb4' comment '人才培养方案表';

create table objectives_training_target
(
    id             varchar(255) comment '毕业5年预期ID' primary key,
    expectations_1 text comment '预期1' not null,
    expectations_2 text comment '预期2' not null,
    expectations_3 text comment '预期3' not null,
    expectations_4 text comment '预期4' not null,
    expectations_5 text comment '预期5' not null
) default char set 'utf8mb4' comment '毕业5年预期表';

create database dx_smart_user default char set 'utf8mb4';

create table role
(
    role_id  int primary key auto_increment comment '权限ID',
    `read`   boolean comment '可读',
    `write`  boolean comment '可写',
    `update` boolean comment '可改',
    `delete` boolean comment '可删'
) default char set 'utf8mb4' comment '权限表';

create table user
(
    user_id  varchar(255) primary key comment '用户唯一标识',
    username varchar(255) comment '用户名' not null,
    account  varchar(16) comment '账号'    not null,
    password varchar(255) comment '密码'   not null,
    role     int comment '权限'            not null
) default char set 'utf8mb4' comment '用户表';