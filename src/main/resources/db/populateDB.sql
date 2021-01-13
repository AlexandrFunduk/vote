DELETE
FROM user_roles;
DELETE
FROM dish_price;
DELETE
FROM menu;
DELETE
FROM vote;
DELETE
FROM restaurant;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('User1', 'user1@yandex.ru', '{noop}123456'),
       ('User2', 'user2@yandex.ru', '{noop}654321'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('ADMIN', 100003),
       ('USER', 100003);

INSERT INTO restaurant (name, registered)
VALUES ('restaurant1', '2020-12-01'),
       ('restaurant2', '2020-12-01'),
       ('restaurant3', '2020-12-01');

INSERT INTO menu (day, restaurant_id)
VALUES ('2020-12-01', 100004),
       ('2020-12-01', 100005),
       ('2020-12-01', 100006),
       ('2020-12-02', 100004),
       ('2020-12-02', 100005),
       ('2020-12-02', 100006),
       (curdate(), 100004);

INSERT INTO dish_price(menu_id, dish, price)
VALUES (100007, 'borsch', 10000),
       (100007, 'pie', 20000),
       (100007, 'tea', 5000),
       (100008, 'pizza', 27000),
       (100008, 'pasta', 25000),
       (100008, 'coffee', 11000),
       (100009, 'roast', 30000),
       (100009, 'salad', 27000),
       (100009, 'juice', 8000),
       (100010, 'borsch1', 10100),
       (100010, 'pie1', 20100),
       (100010, 'tea1', 5100),
       (100011, 'pizza1', 27100),
       (100011, 'pasta1', 25100),
       (100011, 'coffee1', 11100),
       (100012, 'roast1', 30100),
       (100012, 'salad1', 27100),
       (100012, 'juice1', 8010),
       (100013, 'roast2', 333300),
       (100013, 'salad2', 200000),
       (100013, 'juice2', 1000);

INSERT INTO vote (date, restaurant_id, user_id)
VALUES ('2020-12-01', 100004, 100000),
       ('2020-12-01', 100005, 100001),
       ('2020-12-01', 100006, 100002),
       ('2020-12-02', 100005, 100000),
       ('2020-12-02', 100005, 100001),
       ('2020-12-02', 100004, 100002),
       (curdate(), 100004, 100001),
       (curdate(), 100004, 100002);
