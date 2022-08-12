CREATE TABLE IF NOT EXISTS "public"."customer"
(
    "id"         SERIAL PRIMARY KEY,
    "first_name" VARCHAR(20),
    "last_name"  VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS "public"."product"
(
    "id"   SERIAL PRIMARY KEY,
    "name" VARCHAR(20),
    "cost" INTEGER
);

CREATE TABLE IF NOT EXISTS "public"."sale"
(
    "id"          SERIAL PRIMARY KEY,
    "customer_id" INTEGER REFERENCES customer (id),
    "product_id"     INTEGER REFERENCES product (id),
    "date"        DATE
);


insert into customer(firstname, lastname)values ('Oleg', 'Ivanov');
insert into customer(firstname, lastname)values ('Ivan', 'Petrov');
insert into customer(firstname, lastname)values ('Nikolay', 'Aleksandrov');
insert into customer(firstname, lastname)values ('Viktor', 'Miami');
insert into customer(firstname, lastname)values ('Mariya', 'Olegovna');
insert into customer(firstname, lastname) values ('Иванов', 'Иванов');
insert into customer(firstname, lastname) values ('Oleg', 'Иванов');


insert into product(name, cost) values('Milk', 5);
insert into product(name, cost) values('Eggs', 3);
insert into product(name, cost) values('Bread', 7);
insert into product(name, cost) values('Water', 15);
insert into product(name, cost) values('Cheese', 20);
insert into product(name, cost) values('Arbuz', 1500);
insert into product(name, cost) values('Черешня', 4500);
insert into product(name, cost) values('Клубника', 3200);
insert into product(name, cost) values('Дыня', 5000);

insert into sale(customer_id, product_id, date) values(1, 3, '2022-08-19');
insert into sale(customer_id, product_id, date) values(2, 1, '2022-08-19');
insert into sale(customer_id, product_id, date) values(3, 1, '2022-08-20');
insert into sale(customer_id, product_id, date) values(1, 4, '2022-08-20');
insert into sale(customer_id, product_id, date) values(3, 2, '2022-08-21');
insert into sale(customer_id, product_id, date) values(4, 5, '2022-08-22');
insert into sale(customer_id, product_id, date) values(1, 5, '2022-08-22');
insert into sale(customer_id, product_id, date) values(5, 4, '2022-08-23');
insert into sale(customer_id, product_id, date) values(5, 5, '2022-08-23');
insert into sale(customer_id, product_id, date) values(4, 3, '2022-08-24');

insert into sale(customer_id, product_id, date) values(3, 6, '2022-08-24');
insert into sale(customer_id, product_id, date) values(4, 7, '2022-08-24');
insert into sale(customer_id, product_id, date) values(5, 8, '2022-08-24');
insert into sale(customer_id, product_id, date) values(6, 9, '2022-08-24');
insert into sale(customer_id, product_id, date) values(7, 10, '2022-08-24');
insert into sale(customer_id, product_id, date) values(8, 11, '2022-08-24');

