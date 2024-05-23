-- Users
INSERT INTO users (id, name, midl_name, sur_name, rnokpp, sex, created_date, updated_date, driver_type)
VALUES
    (1, 'John', 'John', 'Doe', '123456', 'Ч', '2023-01-01 10:00:00', '2023-01-01 10:00:00', 'OWNER'),
    (2, 'Jane', 'Jane', 'Smith', '654321', 'Ч', '2023-01-02 10:00:00', '2023-01-02 10:00:00', 'DRIVER'),
    (3, 'Alice', 'Alice', 'Johnson', '111222', 'Ч', '2023-01-03 10:00:00', '2023-01-03 10:00:00', NULL),
    (4, 'Bob', 'Bob', 'Brown', '333444', 'Ж', '2023-01-04 10:00:00', '2023-01-04 10:00:00', 'DRIVER'),
    (5, 'Charlie', 'Charlie', 'Davis', '555666', 'Ж', '2023-01-05 10:00:00', '2023-01-05 10:00:00', 'OWNER');

-- Juridical Persons
INSERT INTO juridical_persons (id, erdpo, type_activity, regisrtation_date, user_id)
VALUES
    (1, '12345678', 'Consulting', '2023-01-01 10:00:00', 1),
    (2, '87654321', 'IT Services', '2023-01-02 10:00:00', 2);

-- Passports
INSERT INTO passports (id, passport_number, issue_date, valid_until, validity, authority, user_id)
VALUES
    (1, 'A12345678', '2020-01-01', '2030-01-01', TRUE, 'Gov Authority 1', 1),
    (2, 'B87654321', '2021-01-01', '2031-01-01', TRUE, 'Gov Authority 2', 2),
    (3, 'C11122233', '2022-01-01', '2032-01-01', TRUE, 'Gov Authority 3', 3);

-- Driver Licenses
INSERT INTO driver_licenses (id, issue_date, valid_until, validity, authority, license_number, user_id)
VALUES
    (1, '2019-01-01', '2029-01-01', TRUE, 'DMV Authority 1', 'DL123456', 1),
    (2, '2018-01-01', '2028-01-01', TRUE, 'DMV Authority 2', 'DL654321', 4);

-- Driver License Categories
INSERT INTO driver_license_categories (id, category_type, driver_license_id)
VALUES
    (1, 'A', 1),
    (2, 'B', 2);

-- Foreign Passports
INSERT INTO foreign_passports (id, passport_number, issue_date, valid_until, validity, authority, user_id)
VALUES
    (1, 'FA123456', '2019-01-01', '2029-01-01', TRUE, 'Gov Authority 4', 1),
    (2, 'FB654321', '2020-01-01', '2030-01-01', TRUE, 'Gov Authority 5', 5);

-- Addresses
INSERT INTO addresses (id, city, street, home_number, flat_number, created_date, updated_date, juridical_person_id)
VALUES
    (1, 'City A', 'Street 1', '10', '101', '2023-01-01 10:00:00', '2023-01-01 10:00:00', 1),
    (2, 'City B', 'Street 2', '20', '202', '2023-01-02 10:00:00', '2023-01-02 10:00:00', 2);

-- Address-User Relationships
INSERT INTO addresses_users (users_id, addresses_id)
VALUES
    (1, 1),
    (2, 2);

-- Cars
INSERT INTO cars (id, plate_number, color, model, car_type, vin_Code, issue_date, juridical_person_id)
VALUES
    (1, 'ABC123', 'Red', 'Model X', 'SEDAN', 'VIN12345678901234', '2020-01-01', 1),
    (2, 'XYZ789', 'Blue', 'Model Y', 'SUV', 'VIN65432109876543', '2021-01-01', 2);

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
    (1, '1234567890', 'IMEI1234567890', 1, 1),
    (2, '0987654321', 'IMEI0987654321', 2, 2);

-- Cars-Users Relationships
INSERT INTO cars_drivers (drivers_id, cars_id)
VALUES
    (1, 1),
    (2, 2);
