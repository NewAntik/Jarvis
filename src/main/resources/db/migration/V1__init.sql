CREATE TABLE users (
    id                                                BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name                                              VARCHAR(50),
    middle_name                                       VARCHAR(50),
    sur_name                                          VARCHAR(50),
    rnokpp                                            VARCHAR(50) UNIQUE,
    sex                                               VARCHAR(10),
    illegal_actions                                   VARCHAR(500),
    individual_entrepreneur                           VARCHAR(7) DEFAULT 'UNKNOWN',
    created_date                                      TIMESTAMP NOT NULL,
    updated_date                                      TIMESTAMP NOT NULL
);

CREATE TABLE user_siblings (
    user_id                                           BIGINT REFERENCES users(id),
    sibling_id                                        BIGINT REFERENCES users(id),
    PRIMARY KEY                                       (user_id, sibling_id),
    CONSTRAINT no_self_sibling                        CHECK (user_id != sibling_id)
);

CREATE TABLE users_children(
    user_id                                           BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    children_id                                       BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE
);

CREATE TABLE users_parents(
    user_id                                           BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    parents_id                                        BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE
);

CREATE TABLE juridical_persons(
    id                                                BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    erdpo                                             VARCHAR(8),
    type_activity                                     VARCHAR(200),
    regisrtation_date                                 TIMESTAMP,
    created_date                                      TIMESTAMP NOT NULL,
    updated_date                                      TIMESTAMP NOT NULL
);

CREATE TABLE juridical_persons_users(
    users_id                                          BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    juridical_persons_id                              BIGINT NOT NULL REFERENCES juridical_persons(id) ON UPDATE CASCADE
);

CREATE TABLE passports(
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    passport_number          VARCHAR(9),
    issue_date               TIMESTAMP,
    valid_until              TIMESTAMP,
    valid                    VARCHAR(7) DEFAULT 'UNKNOWN',
    unlimited                VARCHAR(7) DEFAULT 'UNKNOWN',
    authority                VARCHAR(200),
    user_id                  BIGINT NOT NULL REFERENCES users(id),
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL
);

CREATE TABLE driver_licenses(
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    issue_date               TIMESTAMP,
    valid_until              TIMESTAMP,
    valid                    VARCHAR(7) DEFAULT 'UNKNOWN',
    unlimited                VARCHAR(7) DEFAULT 'UNKNOWN',
    authority                VARCHAR(200),
    license_number           VARCHAR(9),
    user_id                  BIGINT NOT NULL UNIQUE REFERENCES users(id),
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL
);

CREATE TABLE driver_license_categories(
    id                       BIGINT PRIMARY KEY,
    category_type            VARCHAR(255) NOT NULL,
    driver_license_id        BIGINT NOT NULL REFERENCES driver_licenses(id),
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL
);

CREATE TABLE foreign_passports(
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    passport_number          VARCHAR(8),
    issue_date               TIMESTAMP,
    valid_until              TIMESTAMP,
    valid                    VARCHAR(7) DEFAULT 'UNKNOWN',
    unlimited                VARCHAR(7) DEFAULT 'UNKNOWN',
    authority                VARCHAR(200),
    user_id                  BIGINT NOT NULL REFERENCES users(id),
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL
);

CREATE TABLE addresses(
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    region                   VARCHAR(50),
    city                     VARCHAR(50),
    street                   VARCHAR(50),
    home_number              VARCHAR(50),
    flat_number              VARCHAR(50),
    district                 VARCHAR(50),
    corpus                   VARCHAR(50),
    other                    VARCHAR(50),
    other_num                VARCHAR(10),
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL
);

CREATE TABLE addresses_users (
    users_id                 BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    addresses_id             BIGINT NOT NULL REFERENCES addresses(id) ON UPDATE CASCADE
);

CREATE TABLE individual_entrepreneur_addresses (
    individual_entrepreneur_id BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    addresses_id             BIGINT NOT NULL REFERENCES addresses(id) ON UPDATE CASCADE
);

CREATE TABLE addresses_juridical_persons (
    juridical_persons_id     BIGINT NOT NULL REFERENCES juridical_persons(id) ON UPDATE CASCADE,
    jur_addresses_id         BIGINT NOT NULL REFERENCES addresses(id) ON UPDATE CASCADE
);

CREATE TABLE cars(
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    plate_number             VARCHAR(8),
    color                    VARCHAR(50),
    model                    VARCHAR(50),
    car_type                 VARCHAR(100),
    vin_Code                 VARCHAR(17),
    issue_date               TIMESTAMP,
    juridical_person_id      BIGINT REFERENCES juridical_persons(id),
    owner_Id                 BIGINT REFERENCES users(id),
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL
);

CREATE TABLE emails(
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    email_address            VARCHAR(100) NOT NULL,
    user_id                  BIGINT REFERENCES users(id),
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL,
    juridical_person_id      BIGINT UNIQUE REFERENCES juridical_persons(id)
);

CREATE TABLE photos(
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    uuid                     VARCHAR(36)  NOT NULL,
    file_name                VARCHAR(255) NOT NULL,
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL,
    user_id                  BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE phones(
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    number                   VARCHAR(10)  NOT NULL,
    imei                     VARCHAR(20),
    user_id                  BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    juridical_person_id      BIGINT REFERENCES juridical_persons(id),
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL
);

CREATE TABLE cars_drivers (
    drivers_id               BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    cars_id                  BIGINT NOT NULL REFERENCES cars(id) ON UPDATE CASCADE
);

CREATE TABLE participants (
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    telegram_id                   BIGINT UNIQUE NOT NULL,
    role                     VARCHAR(5) NOT NULL,
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL
);

CREATE TABLE birth_certificates (
    id                       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id                  BIGINT,
    day                      VARCHAR(2),
    month                    VARCHAR(2),
    year                     VARCHAR(4),
    number                   VARCHAR(20),
    issue_date               TIMESTAMP,
    valid_until              TIMESTAMP,
    valid                    VARCHAR(7) DEFAULT 'UNKNOWN',
    unlimited                VARCHAR(7) DEFAULT 'UNKNOWN',
    authority                VARCHAR(200),
    address_id               BIGINT,
    CONSTRAINT fk_user       FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_address    FOREIGN KEY (address_id) REFERENCES addresses(id),
    created_date             TIMESTAMP NOT NULL,
    updated_date             TIMESTAMP NOT NULL
);

-- Indexes
CREATE INDEX idx_users_rnokpp ON users (rnokpp);

CREATE INDEX idx_phones_number ON phones (number);

CREATE INDEX idx_participants_telegram_id ON participants (telegram_id);
