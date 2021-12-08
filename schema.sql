
    create table hotel (
       id bigint not null,
        address varchar(255),
        announcement varchar(255),
        biz_region integer,
        credit_bound integer not null,
        description varchar(255),
        hotel_star integer,
        manager_id integer,
        name varchar(255),
        phone_number varchar(255),
        pic varchar(255),
        rate double precision,
        primary key (id)
    ) engine=InnoDB;

    create table room (
       id bigint not null,
        hotel_id bigint,
        room_config_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table room_config (
       id bigint not null,
        breakfast bit not null,
        name varchar(255),
        people_max integer not null,
        price decimal(19,2),
        type integer,
        hotel_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table room_unit (
       date date not null,
        price decimal(19,2),
        room_id bigint not null,
        primary key (date, room_id)
    ) engine=InnoDB;

    alter table room 
       add constraint FKdosq3ww4h9m2osim6o0lugng8 
       foreign key (hotel_id) 
       references hotel (id);

    alter table room 
       add constraint FKe42033dndi8fc1w7nboechukd 
       foreign key (room_config_id) 
       references room_config (id);

    alter table room_config 
       add constraint FKownlnpj22c7v9ebk55yjxv3kc 
       foreign key (hotel_id) 
       references hotel (id);

    alter table room_unit 
       add constraint FKflwgv31qcvk8dx75d70vjbwx9 
       foreign key (room_id) 
       references room (id);
