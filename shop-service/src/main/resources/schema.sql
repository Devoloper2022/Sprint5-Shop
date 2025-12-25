create table if not exists items
(
    id bigserial primary key,
    title varchar(256) not null,
    description text,
    imgname varchar,
    price integer not null
);


create table if not exists  positions
(
    id bigserial primary key,
    order_id bigserial ,
    item_id bigserial ,
    quantity integer not null default 0,
    status boolean default false
);
CREATE TABLE users
(
    id       BIGSERIAL NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255)
);

create table if not exists orders
(
    id bigserial primary key,
    status boolean not null,
    total_position integer not null,
    total_price integer not null,
    total_quantity integer not null,
    user_id BIGSERIAL NOT NULL
);

insert into orders(status,total_position,total_price,total_quantity)
values (false,0,0,0);

insert into items(title, description, imgname, price)
values ('Sonik', 'asdfasdfasdfasdfasdfasdf ', '1.jpeg',30);

insert into items(title, description, imgname, price)
values ('Java ', 'asdfasdfasdfasdfasdfasdf', '2.jpeg',12);

insert into items(title, description, imgname, price)
values ('Killer', 'asdfasdfasdfasdfasdfasdf','3.jpeg',23);

insert into items(title, description, imgname, price)
values ('ggvp', 'asdfasdfasdfasdfasdfasdf', '4.jpeg',12)
;
insert into items(title, description, imgname, price)
values ('sss,', 'asdfasdfasdfasdfasdfasdf', '5.jpeg',1)