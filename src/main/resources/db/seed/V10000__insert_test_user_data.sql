-- Users
INSERT INTO users (sur_name, name, middle_name, rnokpp, sex, illegal_actions, created_date, updated_date, individual_entrepreneur)
VALUES
    ('Іванов', 'Іван', 'Іванович', '1234567890', 'MALE', 'Протиправна діяльність №199', '2023-01-01 10:00:00', '2023-01-01 10:00:00', 'UNKNOWN'),
    ('Шевченко', 'Тарас', 'Григорович', '7890654321', 'MALE', 'Протиправна діяльність №200', '2023-01-02 10:00:00', '2023-01-02 10:00:00', 'UNKNOWN'),
    ('Шевченко', 'Григорій', 'Іванович', '1112225555', 'MALE', NULL, '2023-01-03 10:00:00', '2023-01-03 10:00:00', 'UNKNOWN'),
    ('Шевченко', 'Катирина', 'Якимівна', '3334448888', 'FEMALE', NULL, '2023-01-04 10:00:00', '2023-01-04 10:00:00', 'UNKNOWN'),
    ('Шевченко', 'Марта', 'Іванівна', '5556660945', 'FEMALE', NULL, '2023-01-05 10:00:00', '2023-01-05 10:00:00', 'UNKNOWN'),
    ('Шевченко', 'Зінаїда', 'Олеговна', '5223360630', 'FEMALE', NULL, '2023-01-05 10:00:00', '2023-01-05 10:00:00', 'UNKNOWN'),
    ('Шевченко', 'Аргентіна', 'Енакентієвна', '5523360645', 'FEMALE', NULL, '2023-01-05 10:00:00', '2023-01-05 10:00:00', 'UNKNOWN');

INSERT INTO user_siblings(user_id, sibling_id)
VALUES
    (2, 7),
    (2, 1);

INSERT INTO users_children(user_id, children_id)
VALUES
    (2, 6),
    (2, 1);

INSERT INTO users_parents(user_id, parents_id)
VALUES
    (2, 3),
    (2, 4);

-- Juridical Persons
INSERT INTO juridical_persons (erdpo, type_activity, regisrtation_date, created_date, updated_date)
VALUES
    ('12345678', 'Consulting', '2023-01-01 10:00:00', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('87654321', 'IT Services', '2023-01-02 10:00:00', '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Users Juridical Persons
INSERT INTO juridical_persons_users (users_id, juridical_persons_id)
VALUES
    (2, 1),
    (2, 2);

-- Passports
INSERT INTO passports (passport_number, issue_date, valid_until, valid, unlimited, authority, user_id, created_date, updated_date)
VALUES
    ('123456789', '2020-11-15', '2030-01-01', 'YES', 'YES', 'Mariupol, Prospekt Heroyev 11', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('СЕ123456', '2000-12-11', '2030-01-01', 'YES', 'YES', 'Mariupol, Prospekt Heroyev 11', 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('A74562345', '1999-06-04', '2030-01-01', 'YES', 'YES', 'Mariupol, Prospekt Heroyev 11', 3, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('752345435', '2024-03-30', '2031-01-01', 'YES', 'YES', 'Mariupol, Prospekt Heroyev 11', 4, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('C11122233', '2022-01-01', '2032-01-01', 'YES', 'YES', 'Gov Authority 3', 5, '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Driver Licenses
INSERT INTO driver_licenses (issue_date, valid_until, valid, unlimited, authority, license_number, user_id, created_date, updated_date)
VALUES
    ('2019-01-01', '2029-01-01', 'YES', 'NO', 'DMV Authority 1', 'DL123456', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('2018-01-01', '2028-01-01', 'YES', 'NO', 'DMV Authority 2', 'DL654321', 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Driver License Categories
INSERT INTO driver_license_categories (id, category_type, driver_license_id, created_date, updated_date)
VALUES
    (1, 'A', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (2, 'A1', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (3, 'B', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (4, 'B1', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (5, 'C', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (6, 'C1', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (7, 'BE', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (8, 'CE', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (9, 'C1', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (10, 'C1E', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (11, 'D', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (12, 'D1', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (13, 'B', 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Foreign Passports
INSERT INTO foreign_passports (passport_number, issue_date, valid_until, valid, unlimited, authority, user_id, created_date, updated_date)
VALUES
    ('FG123456', '2020-01-01', '2029-01-01', 'NO', 'NO', 'Kiev, Heroyev street, home number 4', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('FA242333', '1999-01-01', '2029-01-01', 'NO', 'NO', 'Kiev, Heroyev street, home number 4', 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('FA543775', '2011-01-01', '2029-01-01', 'NO', 'NO', 'Kiev, Heroyev street, home number 4', 3, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('FA657834', '2039-01-01', '2029-01-01', 'NO', 'NO', 'Kiev, Heroyev street, home number 4', 4, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('FB654321', '2020-01-01', '2030-01-01', 'NO', 'NO', 'Kiev, Heroyev street, home number 4', 5, '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Addresses
INSERT INTO addresses (region, city, street, home_number, flat_number, created_date, updated_date)
VALUES
    ('Київська', 'Київ', 'Проспект Перемоги', '10', '101', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('Донецька', 'Донецьк', 'Київський проспект імені Зеленского', '20', '202', '2023-01-02 10:00:00', '2023-01-02 10:00:00');

-- Address-User Relationships
INSERT INTO addresses_users (users_id, addresses_id)
VALUES
    (2, 1),
    (1, 2),
    (3, 2);

INSERT INTO addresses_users (users_id, addresses_id)
VALUES
    (2, 1),
    (2, 2);

-- Addresses-JuridicalPersons Relationships
INSERT INTO addresses_juridical_persons (jur_addresses_id, juridical_persons_id)
VALUES
    (1, 1),
    (2, 2);

-- Cars
INSERT INTO cars (plate_number, color, model, car_type, vin_Code, issue_date, juridical_person_id, owner_Id, created_date, updated_date)
VALUES
    ('СЕ1234КЕ', 'Red', 'Model X', 'SEDAN', 'VIN12345678901234', '2020-05-01', 1, 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('СЕ1234КЕ', 'Yellow', 'BMW X7', 'SUV', 'VIN12347652901234', '2024-11-09', 2, 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('ABC543', 'Pink', 'Mitsubishi Pajero', 'SUV', 'VIN12309456901234', '2019-06-19', 2, 3, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('XYZ789', 'Blue', 'Model Y', 'SUV', 'VIN65432109876543', '2021-01-01', 1, 4, '2023-01-01 10:00:00', '2023-01-01 10:00:00');

-- Emails
INSERT INTO emails (email_address, user_id, created_date, updated_date, juridical_person_id)
VALUES
    ('john.doe@example.com', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00', 1),
    ('jane.smith@example.com', 2, '2023-01-02 10:00:00', '2023-01-02 10:00:00', 2);

-- Photos
INSERT INTO photos (uuid, file_name, created_date, updated_date, user_id)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'photo2.jpg', '2023-01-01 10:00:00', '2023-01-01 10:00:00', 1),
    ('550e8400-e29b-41d4-a716-446655440001', 'photo2.jpg', '2023-01-02 10:00:00', '2023-01-02 10:00:00', 2);

-- Phones
INSERT INTO phones (number, imei, user_id, juridical_person_id, created_date, updated_date)
VALUES
    ('0994361290', 'IMEI1233457790', 1, 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('0994361290', 'IMEI0932654321', 2, 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('0994361292', 'IMEI0954654321', 3, 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('0994361293', 'IMEI0987655621', 4, 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('0994361294', 'IMEI0987698321', 5, 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('0994361295', 'IMEI0297654321', 6, 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    ('0994361296', 'IMEI0958654321', 7, 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00');


-- Cars-Users Relationships
INSERT INTO cars_drivers (drivers_id, cars_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);

-- Birth Certificates
INSERT INTO birth_certificates (user_id, day, month, year, number, issue_date, valid_until, valid, unlimited, authority, address_id, created_date, updated_date)
VALUES
    (2, '05', '03', '1814', 'BC123456', '2020-01-01', '2029-01-01', 'YES', 'YES', 'Kiev, Heroyev street', 2, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (1, '09', '02', '1815', 'BC654321', '2020-01-01', '2029-01-01', 'YES', 'YES', 'Kiev, Heroyev street', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (3, '03', '02', '1815', 'BC654321', '2020-01-01', '2029-01-01', 'YES', 'YES', 'Kiev, Heroyev street', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (4, '01', '02', '1820', 'BC654321', '2020-01-01', '2029-01-01', 'YES', 'YES', 'Kiev, Heroyev street', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (5, '29', '02', '1830', 'BC654321', '2020-01-01', '2029-01-01', 'YES', 'YES', 'Kiev, Heroyev street', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (6, '19', '02', '1833', 'BC654321', '2020-01-01', '2029-01-01', 'YES', 'YES', 'Kiev, Heroyev street', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (7, '10', '02', '1828', 'BC654321', '2020-01-01', '2029-01-01', 'YES', 'YES', 'Kiev, Heroyev street', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00');
