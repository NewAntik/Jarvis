CREATE TABLE users (
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR(50) NOT NULL,
    midl_name           VARCHAR(50) NOT NULL,
    sur_name            VARCHAR(50) NOT NULL,
    rnokpp              VARCHAR(50),
    sex                 VARCHAR(10),
    created_date        DATE NOT NULL,
    updated_date        DATE NOT NULL
);

CREATE TABLE juridical_persons(
    id SERIAL           PRIMARY KEY,
    erdpo               VARCHAR(8),
    type_activity       VARCHAR(200),
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE passports(
    id                  SERIAL PRIMARY KEY,
    passport_number     VARCHAR(9) NOT NULL,
    issue_date          DATE NOT NULL,
    valid_until         DATE NOT NULL,
    validity            BOOLEAN NOT NULL,
    authority           VARCHAR(200) NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE driver_licenses(
    id                  SERIAL PRIMARY KEY,
    issue_date          DATE NOT NULL,
    valid_until         DATE NOT NULL,
    validity            BOOLEAN NOT NULL,
    authority           VARCHAR(200) NOT NULL,
    license_number      VARCHAR(9) NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE driver_license_categories(
    id                  SERIAL PRIMARY KEY,
    category_type       VARCHAR(255) NOT NULL,
    driver_license_id   BIGINT NOT NULL UNIQUE REFERENCES driver_licenses(id) ON DELETE CASCADE,
);

CREATE TABLE foreign_passports(
    id                  SERIAL PRIMARY KEY,
    passport_number     VARCHAR(8) NOT NULL,
    issue_date          DATE NOT NULL,
    valid_until         DATE NOT NULL,
    validity            BOOLEAN NOT NULL,
    authority           VARCHAR(200) NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE addresses(
    id                  SERIAL PRIMARY KEY,
    city                VARCHAR(50) NOT NULL,
    street              VARCHAR(50),
    home_number         VARCHAR(50),
    flat_number         VARCHAR(50),
    created_date        DATE NOT NULL,
    updated_date        DATE NOT NULL,
    juridical_person_id BIGINT UNIQUE REFERENCES juridical_persons(id)
);

CREATE TABLE users_addresses (
    user_id             BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    address_id          BIGINT NOT NULL REFERENCES addresses(id) ON UPDATE CASCADE,
    PRIMARY KEY         (user_id, address_id)
);

CREATE TABLE cars(
    id SERIAL           PRIMARY KEY,
    plate_number        VARCHAR(20) NOT NULL,
    color               VARCHAR(50) NOT NULL,
    model               VARCHAR(50) NOT NULL,
    car_type            VARCHAR(100) NOT NULL,
    vin_Code            VARCHAR(17) NOT NULL,
    juridical_person_id BIGINT UNIQUE REFERENCES juridical_persons(id)
);

CREATE TABLE emails(
    id SERIAL           PRIMARY KEY,
    email_address       VARCHAR(100) NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    created_date        DATE NOT NULL,
    updated_date        DATE NOT NULL,
    juridical_person_id BIGINT UNIQUE REFERENCES juridical_persons(id)
);

CREATE TABLE photos(
    id                  SERIAL,
    uuid                VARCHAR(36)  NOT NULL,
    file_name           VARCHAR(255) NOT NULL,
    created_date        DATE NOT NULL,
    updated_date        DATE NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE phones(
    id SERIAL           PRIMARY KEY,
    phone_number        VARCHAR(12)  NOT NULL,
    imei                VARCHAR(20) NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    juridical_person_id BIGINT UNIQUE REFERENCES juridical_persons(id)
);

