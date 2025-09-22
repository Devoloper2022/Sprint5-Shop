
create table if not exists item
(
    id      bigserial PRIMARY KEY,
    title   varchar(256) not null,
    description text ,
    imgName   varchar,
    price   integer not null
);



create table if not exists orders
(
    id      bigserial PRIMARY KEY,
    status   BOOLEAN not null,
    total_position integer not null ,
    total_price   integer not null,
    total_quantity   integer not null
);

insert into orders(status,total_position,total_price,total_quantity)
values (false,0,0,0);


insert into item(title, description, imgName, price)
values ('Sonik', 'asdfasdfasdfasdfasdfasdf ',  '1.jpeg',30);
insert into item(title, description, imgName, price)
values ('Java ', 'asdfasdfasdfasdfasdfasdf', '2.jpeg',12);
insert into item(title, description, imgName, price)
values ('Killer', 'asdfasdfasdfasdfasdfasdf','3.jpeg', 23);
insert into item(title, description, imgName, price)
values ('ggvp', 'asdfasdfasdfasdfasdfasdf', '4.jpeg',12);
insert into item(title, description, imgName, price)
values ('sss,', 'asdfasdfasdfasdfasdfasdf', '5.jpeg',1);
