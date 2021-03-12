CREATE TABLE users
(
    id              UUID  NOT NULL
        CONSTRAINT pk_users PRIMARY KEY,
    name            VARCHAR(255),
    login           VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    creation_moment timestamp NOT NULL
);

CREATE UNIQUE INDEX u_users_login ON users (login);
CREATE INDEX i_users ON users(name);