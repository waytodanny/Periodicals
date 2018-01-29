
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
