create table USERS(
user_id int identity primary key,
user_phone varchar(13) not null,
user_fname varchar(15) not null,
user_lname varchar(15) not null,
user_address varchar(50) null
)

create table ROLES(
role_id int identity primary key,
role_type varchar(20) not null
)

create table PERMISSIONS(
permission_id int identity primary key,
permission_type varchar(50) not null
)

create table USER_ROLE(
user_id int not null,
role_id int not null,
CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id),
CONSTRAINT fk_user FOREIGN KEY (user_id) references USERS(user_id),
CONSTRAINT fk_role FOREIGN KEY (role_id) references ROLES(role_id)
)

create table ROLE_PERMISSION(
role_id int not null,
permission_id int not null,
CONSTRAINT pk_role_permission PRIMARY KEY (permission_id, role_id),
CONSTRAINT fk_permission FOREIGN KEY (permission_id) references PERMISSIONS(permission_id),
CONSTRAINT fk_role_per FOREIGN KEY (role_id) references ROLES(role_id)
)