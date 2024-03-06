CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    midl_name VARCHAR(50) NOT NULL,
    sur_name VARCHAR(50) NOT NULL,
    rnokpp VARCHAR(50),
    sex VARCHAR(10)
);

CREATE TABLE juridical_persons(
    id SERIAL PRIMARY KEY,
    erdpo VARCHAR(8),
    type_activity VARCHAR(200),
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE passports(
    id SERIAL PRIMARY KEY,
    passport_number VARCHAR(9) NOT NULL,
    issue_date DATE NOT NULL,
    valid_until DATE NOT NULL,
    validity BOOLEAN NOT NULL,
    authority VARCHAR(200) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE driver_licenses(
    id SERIAL PRIMARY KEY,
    issue_date DATE NOT NULL,
    valid_until DATE NOT NULL,
    validity BOOLEAN NOT NULL,
    authority VARCHAR(200) NOT NULL,
    license_number VARCHAR(9) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE driver_license_categories(
    id SERIAL PRIMARY KEY,
    category_type VARCHAR(255) NOT NULL,
    driver_license_id BIGINT NOT NULL UNIQUE REFERENCES driver_licenses(id) ON DELETE CASCADE,
);

CREATE TABLE foreign_passports(
    id SERIAL PRIMARY KEY,
    passport_number VARCHAR(8) NOT NULL,
    issue_date DATE NOT NULL,
    valid_until DATE NOT NULL,
    validity BOOLEAN NOT NULL,
    authority VARCHAR(200) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE addresses(
    id SERIAL PRIMARY KEY,
    city VARCHAR(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    home_number VARCHAR(50) NOT NULL,
    flat_number VARCHAR(50) NOT NULL,
    juridical_person_id BIGINT NOT NULL UNIQUE REFERENCES juridical_persons(id)
);

CREATE TABLE users_addresses (
    user_id BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    address_id BIGINT NOT NULL REFERENCES addresses(id) ON UPDATE CASCADE,
    PRIMARY KEY (user_id, address_id)
);

