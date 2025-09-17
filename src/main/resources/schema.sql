
create table if not exists item
(
    id      bigserial PRIMARY KEY,
    title   varchar(256) not null,
    description text ,
    imgName   varchar,
    price   integer not null
);


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
