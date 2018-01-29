
CREATE TABLE IF NOT EXISTS roles (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id CHAR(36) PRIMARY KEY,
    login NVARCHAR(50) NOT NULL UNIQUE,
    pass NVARCHAR(32) NOT NULL,
    email VARCHAR(254) UNIQUE NOT NULL,
    role_id CHAR(36) NOT NULL,
    FOREIGN KEY (role_id)
        REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS publishers (
    id CHAR(36) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS genres (
    id CHAR(36) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS periodicals (
    id CHAR(36) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(1000),
    subscr_cost DECIMAL(5 , 2 ) UNSIGNED NOT NULL,
    issues_per_year SMALLINT UNSIGNED NOT NULL,
    is_limited BIT NOT NULL,
    genre_id CHAR(36) NOT NULL,
    publisher_id CHAR(36) NOT NULL,
    FOREIGN KEY (genre_id)
        REFERENCES genres (id),
    FOREIGN KEY (publisher_id)
        REFERENCES publishers (id)
);

CREATE TABLE IF NOT EXISTS periodical_issues (
    id CHAR(36) PRIMARY KEY,
    issue_no INT UNSIGNED NOT NULL,
    name NVARCHAR(100),
    publishing_date DATE NOT NULL,
    periodical_id CHAR(36) NOT NULL,

    FOREIGN KEY (periodical_id)
        REFERENCES periodicals (id),
	UNIQUE KEY periodical_issue_no_key (issue_no, periodical_id)
);

CREATE TABLE IF NOT EXISTS subscriptions (
    user_id CHAR(36) NOT NULL ,
    periodical_id CHAR(36) NOT NULL,

    PRIMARY KEY (user_id , periodical_id),

    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (periodical_id)
        REFERENCES periodicals (id)
);

CREATE TABLE IF NOT EXISTS payments (
    id CHAR(36) PRIMARY KEY,
    payment_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    payment_sum DECIMAL(10,2) UNSIGNED NOT NULL,
    user_id CHAR(36) NOT NULL,

    FOREIGN KEY (user_id)
        REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS payments_periodicals (
    payment_id CHAR(36) NOT NULL,
    periodical_id CHAR(36) NOT NULL,

    PRIMARY KEY (payment_id , periodical_id),

    FOREIGN KEY (payment_id)
        REFERENCES payments (id),
    FOREIGN KEY (periodical_id)
        REFERENCES periodicals (id)
);


Insert into roles(id, name)
Values
('798fe811-feb1-11e7-a30f-7b1a8bfa155b','user'),
('89179577-feb1-11e7-a9e6-e35467cb8f58', 'admin');

Insert into users(id, login, pass, email, role_id)
values
('1f940bd3-f7a5-11e7-93e6-a30f6152aa28', 'admin', '21232F297A57A5A743894A0E4A801FC3', 'admin@gmail.com', '89179577-feb1-11e7-a9e6-e35467cb8f58');

Insert into genres(id, name)
values
('c1fae08d-feb1-11e7-8e6b-313d4bf1847c','Comics'),
('cf8c14b2-feb1-11e7-ba96-313a67f5f27a','Newspaper'),
('d74ec6d5-feb1-11e7-982f-25e096a99612','Magazine'),
('dd8fe15f-feb1-11e7-a1da-4f1c9ba93af5','Manga');
