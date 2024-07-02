CREATE TABLE users (
    id                    BIGINT PRIMARY KEY,
    name                  VARCHAR(50) NOT NULL,
    middle_name           VARCHAR(50) NOT NULL,
    sur_name              VARCHAR(50) NOT NULL,
    rnokpp                VARCHAR(50) UNIQUE,
    sex                   VARCHAR(10),
    illegal_actions       VARCHAR(500),
    created_date          TIMESTAMP NOT NULL,
    updated_date          TIMESTAMP NOT NULL
);

CREATE TABLE juridical_persons(
    id                    BIGINT PRIMARY KEY,
    erdpo                 VARCHAR(8),
    type_activity         VARCHAR(200),
    regisrtation_date     TIMESTAMP NOT NULL,
    user_id               BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE passports(
    id                    BIGINT PRIMARY KEY,
    passport_number       VARCHAR(9) NOT NULL,
    issue_date            TIMESTAMP NOT NULL,
    valid_until           TIMESTAMP NOT NULL,
    is_Valid              BOOLEAN,
    is_Unlimited          BOOLEAN,
    authority             VARCHAR(200) NOT NULL,
    user_id               BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE driver_licenses(
    id                    BIGINT PRIMARY KEY,
    issue_date            TIMESTAMP NOT NULL,
    valid_until           TIMESTAMP NOT NULL,
    is_Valid              BOOLEAN,
    is_Unlimited          BOOLEAN,
    authority             VARCHAR(200) NOT NULL,
    license_number        VARCHAR(9) NOT NULL,
    user_id               BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE driver_license_categories(
    id                    BIGINT PRIMARY KEY,
    category_type         VARCHAR(255) NOT NULL,
    driver_license_id     BIGINT NOT NULL REFERENCES driver_licenses(id) ON DELETE CASCADE
);

CREATE TABLE foreign_passports(
    id                    BIGINT PRIMARY KEY,
    passport_number       VARCHAR(8) NOT NULL,
    issue_date            TIMESTAMP NOT NULL,
    valid_until           TIMESTAMP NOT NULL,
    is_Valid              BOOLEAN,
    is_Unlimited          BOOLEAN,
    authority             VARCHAR(200) NOT NULL,
    user_id               BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE addresses(
    id                    BIGINT PRIMARY KEY,
    region                VARCHAR(50),
    city                  VARCHAR(50) NOT NULL,
    street                VARCHAR(50),
    home_number           VARCHAR(50),
    flat_number           VARCHAR(50),
    created_date          TIMESTAMP NOT NULL,
    updated_date          TIMESTAMP NOT NULL,
    juridical_person_id   BIGINT UNIQUE REFERENCES juridical_persons(id)
);

CREATE TABLE addresses_users (
    users_id              BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    addresses_id          BIGINT NOT NULL REFERENCES addresses(id) ON UPDATE CASCADE,
    PRIMARY KEY           (users_id, addresses_id)
);

CREATE TABLE cars(
    id                    BIGINT PRIMARY KEY,
    plate_number          VARCHAR(20) NOT NULL,
    color                 VARCHAR(50) NOT NULL,
    model                 VARCHAR(50) NOT NULL,
    car_type              VARCHAR(100) NOT NULL,
    vin_Code              VARCHAR(17) NOT NULL,
    issue_date            TIMESTAMP NOT NULL,
    juridical_person_id   BIGINT REFERENCES juridical_persons(id),
    owner_Id              BIGINT REFERENCES users(id)
);

CREATE TABLE emails(
    id                    BIGINT PRIMARY KEY,
    email_address         VARCHAR(100) NOT NULL,
    user_id               BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    created_date          TIMESTAMP NOT NULL,
    updated_date          TIMESTAMP NOT NULL,
    juridical_person_id   BIGINT UNIQUE REFERENCES juridical_persons(id)
);

CREATE TABLE photos(
    id                    BIGINT PRIMARY KEY,
    uuid                  VARCHAR(36)  NOT NULL,
    file_name             VARCHAR(255) NOT NULL,
    created_date          TIMESTAMP NOT NULL,
    updated_date          TIMESTAMP NOT NULL,
    user_id               BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE phones(
    id                    BIGINT PRIMARY KEY,
    number                VARCHAR(10)  NOT NULL,
    imei                  VARCHAR(20) NOT NULL,
    user_id               BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    juridical_person_id   BIGINT REFERENCES juridical_persons(id)
);

CREATE TABLE cars_drivers(
    drivers_id            BIGINT NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    cars_id               BIGINT NOT NULL REFERENCES cars(id) ON UPDATE CASCADE,
    PRIMARY KEY           (drivers_id, cars_id)
);

CREATE TABLE participants(
    id                    BIGINT PRIMARY KEY,
    name                  VARCHAR(100) NOT NULL,
    role                  VARCHAR(5) NOT NULL
);

CREATE TABLE families (
    id                    BIGINT PRIMARY KEY,
    family_status         VARCHAR(10),
    husband_id            BIGINT,
    wife_id               BIGINT,
    father_id             BIGINT,
    mother_id             BIGINT,
    brother_id            BIGINT,
    sister_id             BIGINT,
    CONSTRAINT fk_husband FOREIGN KEY (husband_id) REFERENCES users(id),
    CONSTRAINT fk_wife    FOREIGN KEY (wife_id) REFERENCES users(id),
    CONSTRAINT fk_father  FOREIGN KEY (father_id) REFERENCES users(id),
    CONSTRAINT fk_mother  FOREIGN KEY (mother_id) REFERENCES users(id),
    CONSTRAINT fk_brother FOREIGN KEY (brother_id) REFERENCES users(id),
    CONSTRAINT fk_sister  FOREIGN KEY (sister_id) REFERENCES users(id)
);

CREATE TABLE family_children (
    family_id             BIGINT,
    child_id              BIGINT,
    CONSTRAINT fk_family  FOREIGN KEY (family_id) REFERENCES families(id),
    CONSTRAINT fk_child   FOREIGN KEY (child_id) REFERENCES users(id)
);

CREATE TABLE users_families(
    families_id           BIGINT,
    user_id               BIGINT,
    CONSTRAINT fk_family  FOREIGN KEY (families_id) REFERENCES families(id),
    CONSTRAINT fk_child   FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE birth_certificates (
    id                    BIGINT PRIMARY KEY,
    user_id               BIGINT,
    day                   VARCHAR(2),
    month                 VARCHAR(2),
    year                  VARCHAR(4),
    number                VARCHAR(20),
    issue_date            TIMESTAMP NOT NULL,
    valid_until           TIMESTAMP NOT NULL,
    is_Valid              BOOLEAN,
    is_Unlimited          BOOLEAN,
    authority             VARCHAR(200) NOT NULL,
    address_id            BIGINT,
    CONSTRAINT fk_user    FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses(id)
);



