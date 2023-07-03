create table users
(
    id_users       bigserial,
    username varchar(30) not null unique,
    password varchar(80) not null,
    email    varchar(80) unique,
    created_at timestamp,
    updated_at timestamp,
    primary key (id_users)
);

create table roles
(
    id_roles   serial,
    name_roles varchar(50) not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id_roles)
);

create table groups
(
    id_group   serial,
    name_group varchar(50) not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id_group)
);

create table authority
(
    id_authority   serial,
    name_authority varchar(50) not null,
    primary key (id_authority)
);


create table users_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id_users),
    foreign key (role_id) references roles (id_roles)
);

create table users_authority
(
    user_id      bigint not null,
    authority_id int    not null,
    primary key (user_id, authority_id),
    foreign key (user_id) references users (id_users),
    foreign key (authority_id) references authority (id_authority)
);


create table user_groups
(
    user_id bigint not null,
    group_id int    not null,
    primary key (user_id, group_id),
    foreign key (user_id) references users (id_users),
    foreign key (group_id) references groups (id_group)
);



create table tracker
(
    id_tracker       bigserial,
    tracker_name varchar(30) not null unique,
    created_at timestamp,
    updated_at timestamp,
    primary key (id_tracker)
);

insert into tracker (tracker_name)
values ('tracker_gb');

create table status
(
    id_status       bigserial,
    status_name varchar(30) not null unique,
	default_s BOOLEAN not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id_status)
);




create table priority
(
    id_priority       bigserial,
    priority_name varchar(30) not null unique,
	priority_value smallserial  not null unique,
    created_at timestamp,
    updated_at timestamp,
    primary key (id_priority)
);



create table ticket
(
    id_ticket       bigserial,
    name_ticket varchar(120) not null,
    text_ticket varchar(2042) not null,
    tracker_ticket int    not null,
    status_ticket int    not null,
    priority_ticket int    not null,
    worker int not null,
    author int not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id_ticket),
    foreign key (tracker_ticket) references  tracker (id_tracker),
    foreign key (status_ticket) references status (id_status),
    foreign key (priority_ticket) references priority (id_priority),
    foreign key (worker) references users (id_users),
    foreign key (author) references users (id_users)
);

create table comment
(
    id_comment       bigserial,
    author int not null,
    ticket_id bigint not null,
    comment_id bigint not null,
    text_comment varchar(2042) not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id_comment),
    foreign key (ticket_id) references  ticket (id_ticket),
    foreign key (author) references users (id_users)
);


CREATE OR REPLACE FUNCTION updated_at_set_now() RETURNS TRIGGER LANGUAGE plpgsql AS
$$
BEGIN
    IF (TG_OP = 'UPDATE' OR NEW IS DISTINCT FROM OLD) THEN
        NEW.updated_at = transaction_timestamp();
    END IF;
    IF (TG_OP = 'INSERT' ) THEN
        NEW.updated_at = transaction_timestamp();
        NEW.created_at = transaction_timestamp();
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER users_bi_updated_at_set_now BEFORE INSERT ON users FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();
CREATE TRIGGER users_bu_updated_at_set_now BEFORE UPDATE ON users FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();

CREATE TRIGGER roles_bi_updated_at_set_now BEFORE INSERT ON roles FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();
CREATE TRIGGER roles_bu_updated_at_set_now BEFORE UPDATE ON roles FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();

CREATE TRIGGER groups_bi_updated_at_set_now BEFORE INSERT ON groups FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();
CREATE TRIGGER groups_bu_updated_at_set_now BEFORE UPDATE ON groups FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();

CREATE TRIGGER tracker_bi_updated_at_set_now BEFORE INSERT ON tracker FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();
CREATE TRIGGER tracker_bu_updated_at_set_now BEFORE UPDATE ON tracker FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();

CREATE TRIGGER status_bi_updated_at_set_now BEFORE INSERT ON status FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();
CREATE TRIGGER status_bu_updated_at_set_now BEFORE UPDATE ON status FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();

CREATE TRIGGER priority_bi_updated_at_set_now BEFORE INSERT ON priority FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();
CREATE TRIGGER priority_bu_updated_at_set_now BEFORE UPDATE ON priority FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();

CREATE TRIGGER ticket_bi_updated_at_set_now BEFORE INSERT ON ticket FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();
CREATE TRIGGER ticket_bu_updated_at_set_now BEFORE UPDATE ON ticket FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();

CREATE TRIGGER comment_bi_updated_at_set_now BEFORE INSERT ON comment FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();
CREATE TRIGGER comment_bu_updated_at_set_now BEFORE UPDATE ON comment FOR EACH ROW EXECUTE PROCEDURE updated_at_set_now();

insert into roles (name_roles)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into groups (id_group, name_group)
values ('1','Tech_GB');

insert into authority (name_authority)
values ('OP_READ'),
       ('OP_EDIT');

insert into users (username, password, email)
values ('user', '$2a$10$5KpQnigJwmRuya4JAE9Vz.YaO9MZ4QnZ48S8Ng33Ja7J0gtGq3DOS', 'test@yandex.ru');

insert into user_groups(user_id, group_id)
values (1, 1);

insert into users_roles(user_id, role_id)
values (1, 1);

insert into users_authority(user_id, authority_id)
values (1, 1);

insert into status (status_name,default_s)
values ('Решена',FALSE),
       ('В работе',FALSE),
       ('Обратная связь',FALSE),
       ('Открыта',TRUE);


insert into priority (priority_name,priority_value)
values ('Незначительная проблема','100'),
       ('Серьезная проблема','55'),
       ('Критическая проблема','20'),
       ('Общий вопрос','120');