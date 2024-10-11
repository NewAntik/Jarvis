-- Users
INSERT INTO users (id, sur_name, name, middle_name, rnokpp, sex, illegal_actions, created_date, updated_date, is_individual_entrepreneur)
VALUES
    (1, 'Іванов', 'Іван', 'Іванович', '1234567890', 'MALE', 'Протиправна діяльність №199', '2023-01-01 10:00:00', '2023-01-01 10:00:00', false),
    (2, 'Шевченко', 'Тарас', 'Григорович', '7890654321', 'MALE', 'Протиправна діяльність №200', '2023-01-02 10:00:00', '2023-01-02 10:00:00', true),
    (3, 'Шевченко', 'Григорій', 'Іванович', '1112225555', 'MALE', NULL, '2023-01-03 10:00:00', '2023-01-03 10:00:00', false),
    (4, 'Шевченко', 'Катирина', 'Якимівна', '3334448888', 'FEMALE', NULL, '2023-01-04 10:00:00', '2023-01-04 10:00:00', false),
    (5, 'Шевченко', 'Марта', 'Іванівна', '5556660945', 'FEMALE', NULL, '2023-01-05 10:00:00', '2023-01-05 10:00:00', false),
    (6, 'Шевченко', 'Зінаїда', 'Олеговна', '5223360630', 'FEMALE', NULL, '2023-01-05 10:00:00', '2023-01-05 10:00:00', false),
    (7, 'Шевченко', 'Аргентіна', 'Енакентієвна', '5523360645', 'FEMALE', NULL, '2023-01-05 10:00:00', '2023-01-05 10:00:00', false);

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
INSERT INTO juridical_persons (id, erdpo, type_activity, regisrtation_date)
VALUES
    (1, '12345678', 'Consulting', '2023-01-01 10:00:00'),
    (2, '87654321', 'IT Services', '2023-01-02 10:00:00');

-- Users Juridical Persons
INSERT INTO juridical_persons_users (users_id, juridical_persons_id)
VALUES
    (2, 1),
    (2, 2);

-- Passports
INSERT INTO passports (id, passport_number, issue_date, valid_until, is_valid, is_Unlimited, authority, user_id)
VALUES
    (1, '123456789', '2020-11-15', '2030-01-01', TRUE, TRUE, 'Mariupol, Prospekt Heroyev 11', 1),
    (2, 'СЕ123456', '2000-12-11', '2030-01-01', TRUE, TRUE, 'Mariupol, Prospekt Heroyev 11', 2),
    (3, 'A74562345', '1999-06-04', '2030-01-01', TRUE, TRUE, 'Mariupol, Prospekt Heroyev 11', 3),
    (4, '752345435', '2024-03-30', '2031-01-01', TRUE, TRUE, 'Mariupol, Prospekt Heroyev 11', 4),
    (5, 'C11122233', '2022-01-01', '2032-01-01', TRUE, TRUE, 'Gov Authority 3', 5);

-- Driver Licenses
INSERT INTO driver_licenses (id, issue_date, valid_until, is_valid, is_Unlimited, authority, license_number, user_id)
VALUES
    (1, '2019-01-01', '2029-01-01', TRUE, FALSE, 'DMV Authority 1', 'DL123456', 1),
    (2, '2018-01-01', '2028-01-01', TRUE, FALSE, 'DMV Authority 2', 'DL654321', 2);

-- Driver License Categories
INSERT INTO driver_license_categories (id, category_type, driver_license_id)
VALUES
    (1, 'A', 1),
    (2, 'A1', 1),
    (3, 'B', 1),
    (4, 'B1', 1),
    (5, 'C', 1),
    (6, 'C1', 1),
    (7, 'BE', 1),
    (8, 'CE', 1),
    (9, 'C1', 1),
    (10, 'C1E', 1),
    (11, 'D', 1),
    (12, 'D1', 1),
    (13, 'B', 2);

-- Foreign Passports
INSERT INTO foreign_passports (id, passport_number, issue_date, valid_until, is_valid, is_Unlimited, authority, user_id)
VALUES
    (1, 'FG123456', '2020-01-01', '2029-01-01', TRUE, FALSE, 'Kiev, Heroyev street, home number 4', 1),
    (2, 'FA242333', '1999-01-01', '2029-01-01', TRUE, FALSE, 'Kiev, Heroyev street, home number 4', 2),
    (3, 'FA543775', '2011-01-01', '2029-01-01', TRUE, FALSE, 'Kiev, Heroyev street, home number 4', 3),
    (4, 'FA657834', '2039-01-01', '2029-01-01', TRUE, FALSE, 'Kiev, Heroyev street, home number 4', 4),
    (5, 'FB654321', '2020-01-01', '2030-01-01', TRUE, FALSE, 'Kiev, Heroyev street, home number 4', 5);

-- Addresses
INSERT INTO addresses (id, region, city, street, home_number, flat_number, created_date, updated_date)
VALUES
    (1, 'Київська', 'Київ', 'Проспект Перемоги', '10', '101', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (2, 'Донецька', 'Донецьк', 'Київський проспект імені Зеленского', '20', '202', '2023-01-02 10:00:00', '2023-01-02 10:00:00');

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
INSERT INTO cars (id, plate_number, color, model, car_type, vin_Code, issue_date, juridical_person_id, owner_Id)
VALUES
    (1, 'СЕ1234КЕ', 'Red', 'Model X', 'SEDAN', 'VIN12345678901234', '2020-05-01', 1, 1),
    (2, 'СЕ1234КЕ', 'Yellow', 'BMW X7', 'SUV', 'VIN12347652901234', '2024-11-09', 2, 2),
    (3, 'ABC543', 'Pink', 'Mitsubishi Pajero', 'SUV', 'VIN12309456901234', '2019-06-19', 2, 3),
    (4, 'XYZ789', 'Blue', 'Model Y', 'SUV', 'VIN65432109876543', '2021-01-01', 1, 4);

-- Emails
INSERT INTO emails (id, email_address, user_id, created_date, updated_date, juridical_person_id)
VALUES
    (1, 'john.doe@example.com', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00', 1),
    (2, 'jane.smith@example.com', 2, '2023-01-02 10:00:00', '2023-01-02 10:00:00', 2);

-- Photos
INSERT INTO photos (id, uuid, file_name, created_date, updated_date, user_id)
VALUES
    (1, '550e8400-e29b-41d4-a716-446655440000', 'photo2.jpg', '2023-01-01 10:00:00', '2023-01-01 10:00:00', 1),
    (2, '550e8400-e29b-41d4-a716-446655440001', 'photo2.jpg', '2023-01-02 10:00:00', '2023-01-02 10:00:00', 2);

-- Phones
INSERT INTO phones (id, number, imei, user_id, juridical_person_id)
VALUES
    (1, '0994361290', 'IMEI1233457790', 1, 1),
    (2, '0994361290', 'IMEI0932654321', 2, 2),
    (3, '0994361292', 'IMEI0954654321', 3, 2),
    (4, '0994361293', 'IMEI0987655621', 4, 2),
    (5, '0994361294', 'IMEI0987698321', 5, 2),
    (6, '0994361295', 'IMEI0297654321', 6, 2),
    (7, '0994361296', 'IMEI0958654321', 7, 2);


-- Cars-Users Relationships
INSERT INTO cars_drivers (drivers_id, cars_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);

-- Birth Certificates
INSERT INTO birth_certificates (id, user_id, day, month, year, number, issue_date, valid_until, is_valid, is_Unlimited, authority, address_id)
VALUES
    (1, 2, '05', '03', '1814', 'BC123456', '2020-01-01', '2029-01-01', TRUE, TRUE, 'Kiev, Heroyev street', 2),
    (2, 1, '09', '02', '1815', 'BC654321', '2020-01-01', '2029-01-01', TRUE, TRUE, 'Kiev, Heroyev street', 1),
    (3, 3, '03', '02', '1815', 'BC654321', '2020-01-01', '2029-01-01', TRUE, TRUE, 'Kiev, Heroyev street', 1),
    (4, 4, '01', '02', '1820', 'BC654321', '2020-01-01', '2029-01-01', TRUE, TRUE, 'Kiev, Heroyev street', 1),
    (5, 5, '29', '02', '1830', 'BC654321', '2020-01-01', '2029-01-01', TRUE, TRUE, 'Kiev, Heroyev street', 1),
    (6, 6, '19', '02', '1833', 'BC654321', '2020-01-01', '2029-01-01', TRUE, TRUE, 'Kiev, Heroyev street', 1),
    (7, 7, '10', '02', '1828', 'BC654321', '2020-01-01', '2029-01-01', TRUE, TRUE, 'Kiev, Heroyev street', 1);
