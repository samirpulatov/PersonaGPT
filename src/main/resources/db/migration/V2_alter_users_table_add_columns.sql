ALTER TABLE users
    ADD COLUMN first_name VARCHAR(255) NOT NULL AFTER id,
    ADD COLUMN last_name  VARCHAR(255) NOT NULL AFTER first_name,
    ADD COLUMN email      VARCHAR(255) NOT NULL AFTER username;

-- Уникальность email (username уже UNIQUE в старой миграции)
ALTER TABLE users
    ADD CONSTRAINT uk_users_email UNIQUE (email);