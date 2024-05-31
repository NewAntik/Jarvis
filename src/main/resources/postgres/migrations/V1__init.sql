CREATE TABLE users (
    id                  BIGINT PRIMARY KEY,
    name                VARCHAR(50) NOT NULL,
    midl_name           VARCHAR(50) NOT NULL,
    sur_name            VARCHAR(50) NOT NULL,
    rnokpp              VARCHAR(50),
    sex                 VARCHAR(10),
    birthday            TIMESTAMP,
    created_date        TIMESTAMP NOT NULL,
    updated_date        TIMESTAMP NOT NULL
);

CREATE TABLE juridical_persons(
    id                  BIGINT PRIMARY KEY,
    erdpo               VARCHAR(8),
    type_activity       VARCHAR(200),
    regisrtation_date   TIMESTAMP NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE passports(
    id                  BIGINT PRIMARY KEY,
    passport_number     VARCHAR(9) NOT NULL,
    issue_date          TIMESTAMP NOT NULL,
    valid_until         TIMESTAMP NOT NULL,
    validity            BOOLEAN NOT NULL,
    authority           VARCHAR(200) NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE driver_licenses(
    id                  BIGINT PRIMARY KEY,
    issue_date          TIMESTAMP NOT NULL,
    valid_until         TIMESTAMP NOT NULL,
    validity            BOOLEAN NOT NULL,
    authority           VARCHAR(200) NOT NULL,
    license_number      VARCHAR(9) NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE driver_license_categories(
    id                  BIGINT PRIMARY KEY,
    category_type       VARCHAR(255) NOT NULL,
    driver_license_id   BIGINT NOT NULL REFERENCES driver_licenses(id) ON DELETE CASCADE
);

CREATE TABLE foreign_passports(
    id                  BIGINT PRIMARY KEY,
    passport_number     VARCHAR(8) NOT NULL,
    issue_date          TIMESTAMP NOT NULL,
    valid_until         TIMESTAMP NOT NULL,
    validity            BOOLEAN NOT NULL,
    authority           VARCHAR(200) NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE addresses(
    id                  BIGINT PRIMARY KEY,
    city                VARCHAR(50) NOT NULL,
    street              VARCHAR(50),
    home_number         VARCHAR(50),
    flat_number         VARCHAR(50),
    created_date        TIMESTAMP NOT NULL,
    updated_date        TIMESTAMP NOT NULL,
    juridical_person_id BIGINT UNIQUE REFERENCES juridical_persons(id)
);

CREATE TABLE addresses_users (
    users_id            BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    addresses_id        BIGINT NOT NULL REFERENCES addresses(id) ON UPDATE CASCADE,
    PRIMARY KEY         (users_id, addresses_id)
);

CREATE TABLE cars(
    id                  BIGINT PRIMARY KEY,
    plate_number        VARCHAR(20) NOT NULL,
    color               VARCHAR(50) NOT NULL,
    model               VARCHAR(50) NOT NULL,
    car_type            VARCHAR(100) NOT NULL,
    vin_Code            VARCHAR(17) NOT NULL,
    issue_date          TIMESTAMP NOT NULL,
    juridical_person_id BIGINT REFERENCES juridical_persons(id),
    owner_Id             BIGINT REFERENCES users(id)
);

CREATE TABLE emails(
    id                  BIGINT PRIMARY KEY,
    email_address       VARCHAR(100) NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    created_date        TIMESTAMP NOT NULL,
    updated_date        TIMESTAMP NOT NULL,
    juridical_person_id BIGINT UNIQUE REFERENCES juridical_persons(id)
);

CREATE TABLE photos(
    id                  BIGINT PRIMARY KEY,
    uuid                VARCHAR(36)  NOT NULL,
    file_name           VARCHAR(255) NOT NULL,
    created_date        TIMESTAMP NOT NULL,
    updated_date        TIMESTAMP NOT NULL,
    user_id             BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE phones(
    id                  BIGINT PRIMARY KEY,
    number        VARCHAR(10)  NOT NULL,
    imei                VARCHAR(20) NOT NULL,
    user_id             BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    juridical_person_id BIGINT REFERENCES juridical_persons(id)
);

CREATE TABLE cars_drivers(
    drivers_id          BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    cars_id             BIGINT NOT NULL REFERENCES cars(id) ON UPDATE CASCADE,
    PRIMARY KEY         (drivers_id, cars_id)
);

CREATE TABLE participants(
    id                  BIGINT PRIMARY KEY,
    name                VARCHAR(100) NOT NULL,
    role                VARCHAR(5) NOT NULL
);



