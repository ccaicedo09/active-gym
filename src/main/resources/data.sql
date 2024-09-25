CREATE TABLE IF NOT EXISTS genders (
    id SERIAL PRIMARY KEY,
    gender_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS eps (
  id SERIAL PRIMARY KEY,
  eps_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS blood_type (
    id SERIAL PRIMARY KEY,
    blood_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS blood_rh (
    id SERIAL PRIMARY KEY,
    blood_rh VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS membership_status (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL UNIQUE
);

-- Data for gender entity
INSERT INTO genders (gender_name) VALUES ('Masculino') ON CONFLICT (gender_name) DO NOTHING;
INSERT INTO genders (gender_name) VALUES ('Femenino') ON CONFLICT (gender_name) DO NOTHING;
INSERT INTO genders (gender_name) VALUES ('Otro') ON CONFLICT (gender_name) DO NOTHING;

-- Data for Colombia EPS
INSERT INTO eps (eps_name) VALUES ('Salud Total') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ('Sanitas') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ('Nueva EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ('SURA') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ('Coomeva EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ('Famisanar EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ('Compensar EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ('Cafesalud EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ('EPS S.O.S') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Salud Vida EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Medimás EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Capital Salud EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Mutual Ser EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Cruz Blanca EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Ambuq EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Asmet Salud EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Cafam EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Ecoopsos EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Emssanar EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Salud Mía EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Comfandi EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Comfenalco EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Ecopetrol EPS') ON CONFLICT (eps_name) DO NOTHING;
INSERT INTO eps (eps_name) VALUES ( 'Capresoca EPS') ON CONFLICT (eps_name) DO NOTHING;

-- Data for blood types
INSERT INTO blood_type (blood_type_name) VALUES ('A') ON CONFLICT (blood_type_name) DO NOTHING;
INSERT INTO blood_type (blood_type_name) VALUES ('B') ON CONFLICT (blood_type_name) DO NOTHING;
INSERT INTO blood_type (blood_type_name) VALUES ('AB') ON CONFLICT (blood_type_name) DO NOTHING;
INSERT INTO blood_type (blood_type_name) VALUES ('O') ON CONFLICT (blood_type_name) DO NOTHING;

-- Data for blood RH
INSERT INTO blood_rh (blood_rh) VALUES ('+') ON CONFLICT (blood_rh) DO NOTHING;
INSERT INTO blood_rh (blood_rh) VALUES ('-') ON CONFLICT (blood_rh) DO NOTHING;

-- Data for roles
INSERT INTO roles (role_name) VALUES ('ADMINISTRADOR') ON CONFLICT (role_name) DO NOTHING;
INSERT INTO roles (role_name) VALUES ('ASESOR') ON CONFLICT (role_name) DO NOTHING;
INSERT INTO roles (role_name) VALUES ('ENTRENADOR') ON CONFLICT (role_name) DO NOTHING;
INSERT INTO roles (role_name) VALUES ('PERSONAL DE ASEO') ON CONFLICT (role_name) DO NOTHING;
INSERT INTO roles (role_name) VALUES ('MIEMBRO') ON CONFLICT (role_name) DO NOTHING;

-- Data for membership status
INSERT INTO membership_status (description) VALUES ('ACTIVA') ON CONFLICT (description) DO NOTHING;
INSERT INTO membership_status (description) VALUES ('INACTIVA') ON CONFLICT (description) DO NOTHING;
INSERT INTO membership_status (description) VALUES ('CONGELADA') ON CONFLICT (description) DO NOTHING;
