-- Users
INSERT INTO users (id, name, midl_name, sur_name, rnokpp, sex, birthday, created_date, updated_date)
VALUES
    (1, 'John', 'John', 'Doe', '1234567890', 'MALE', '1999-01-01 10:00:00', '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
    (2, 'Jane', 'Jane', 'Smith', '654321', 'MALE', '1996-01-01 10:00:00',  '2023-01-02 10:00:00', '2023-01-02 10:00:00'),
    (3, 'Alice', 'Alice', 'Johnson', '111222', 'MALE',  '1970-01-01 10:00:00', '2023-01-03 10:00:00', '2023-01-03 10:00:00'),
    (4, 'Bob', 'Bob', 'Brown', '333444', 'MALE', '1960-01-01 10:00:00', '2023-01-04 10:00:00', '2023-01-04 10:00:00'),
    (5, 'Charlie', 'Charlie', 'Davis', '555666', 'MALE',  '1974-01-01 10:00:00', '2023-01-05 10:00:00', '2023-01-05 10:00:00');

-- Juridical Persons
INSERT INTO juridical_persons (id, erdpo, type_activity, regisrtation_date, user_id)
VALUES
    (1, '12345678', 'Consulting', '2023-01-01 10:00:00', 1),
    (2, '87654321', 'IT Services', '2023-01-02 10:00:00', 2);

-- Passports
INSERT INTO passports (id, passport_number, issue_date, valid_until, validity, authority, user_id)
VALUES
    (1, 'A12345678', '2020-11-15', '2030-01-01', TRUE, 'Mariupol, Prospekt Heroyev 11', 1),
    (2, 'A98765435', '2000-12-11', '2030-01-01', TRUE, 'Mariupol, Prospekt Heroyev 11', 1),
    (3, 'A74562345', '1999-06-04', '2030-01-01', TRUE, 'Mariupol, Prospekt Heroyev 11', 1),
    (4, '752345435', '2024-03-30', '2031-01-01', TRUE, 'Mariupol, Prospekt Heroyev 11', 2),
    (5, 'C11122233', '2022-01-01', '2032-01-01', TRUE, 'Gov Authority 3', 3);

-- Driver Licenses
INSERT INTO driver_licenses (id, issue_date, valid_until, validity, authority, license_number, user_id)
VALUES
    (1, '2019-01-01', '2029-01-01', TRUE, 'DMV Authority 1', 'DL123456', 1),
    (2, '2018-01-01', '2028-01-01', TRUE, 'DMV Authority 2', 'DL654321', 4);

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
INSERT INTO foreign_passports (id, passport_number, issue_date, valid_until, validity, authority, user_id)
VALUES
    (1, 'FA123456', '2020-01-01', '2029-01-01', TRUE, 'Kiev, Heroyev street, home number 4', 1),
    (2, 'FA242333', '1999-01-01', '2029-01-01', TRUE, 'Kiev, Heroyev street, home number 4', 1),
    (3, 'FA543775', '2011-01-01', '2029-01-01', TRUE, 'Kiev, Heroyev street, home number 4', 1),
    (4, 'FA657834', '2039-01-01', '2029-01-01', TRUE, 'Kiev, Heroyev street, home number 4', 1),
    (5, 'FB654321', '2020-01-01', '2030-01-01', TRUE, 'Kiev, Heroyev street, home number 4', 5);

-- Addresses
INSERT INTO addresses (id, city, street, home_number, flat_number, created_date, updated_date, juridical_person_id)
VALUES
    (1, 'City A', 'Street 1', '10', '101', '2023-01-01 10:00:00', '2023-01-01 10:00:00', 1),
    (2, 'City B', 'Street 2', '20', '202', '2023-01-02 10:00:00', '2023-01-02 10:00:00', 2);

-- Address-User Relationships
INSERT INTO addresses_users (users_id, addresses_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2);

-- Cars
INSERT INTO cars (id, plate_number, color, model, car_type, vin_Code, issue_date, juridical_person_id, owner_Id)
VALUES
    (1, 'ABC123', 'Red', 'Model X', 'SEDAN', 'VIN12345678901234', '2020-05-01', 1, 1),
    (2, 'ABC321', 'Yellow', 'BMW X7', 'SUV', 'VIN12347652901234', '2024-11-09', 1, 2),
    (3, 'ABC543', 'Pink', 'Mitsubishi Pajero', 'SUV', 'VIN12309456901234', '2019-06-19', 2, 3),
    (4, 'XYZ789', 'Blue', 'Model Y', 'SUV', 'VIN65432109876543', '2021-01-01', 2, 4);

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
    (1, '1234567890', 'IMEI1233457790', 1, 1),
    (2, '3436546456', 'IMEI7565567890', 1, 1),
    (3, '5345365745', 'IMEI1256577890', 1, 1),
    (4, '0987654321', 'IMEI0987654321', 2, 2);

-- Cars-Users Relationships
INSERT INTO cars_drivers (drivers_id, cars_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 2);
