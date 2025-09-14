
create table if not exists item
(
    id      bigserial PRIMARY KEY,
    title   varchar(256) not null,
    description text ,
    imgName   varchar(256) ,
    price   integer not null
);


insert into item(title, description, imgName, price)
values ('Sonik', 'asdfasdfasdfasdfasdfasdf ',  '1.png',30);
insert into item(title, description, imgName, price)
values ('Java ', 'asdfasdfasdfasdfasdfasdf', '2.png',12);
insert into item(title, description, imgName, price)
values ('Killer', 'asdfasdfasdfasdfasdfasdf','3.png', 23);
insert into item(title, description, imgName, price)
values ('ggvp', 'asdfasdfasdfasdfasdfasdf', '4.png',12);
insert into item(title, description, imgName, price)
values ('sss,', 'asdfasdfasdfasdfasdfasdf', '5.png',1);
