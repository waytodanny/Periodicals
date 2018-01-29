
Insert into genres(id, name)
values
('c1fae08d-feb1-11e7-8e6b-313d4bf1847c','comics');

Insert into publishers(id, name)
values
('fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0','Marvel');

Insert into periodicals(id, name, description, subscr_cost, issues_per_year, is_limited, genre_id, publisher_id)
values
('426f8ce0-feb2-11e7-bc2e-bd4c345931c8','Ultimate spider man1', 'best spider-man story you have ever read', 12.50, 24, 1, 'c1fae08d-feb1-11e7-8e6b-313d4bf1847c', 'fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0'),
('46b26700-feb2-11e7-b445-f3b95d28ec2b','Ultimate spider man2','second best spider-man story you have ever read', 10.50, 24, 1, 'c1fae08d-feb1-11e7-8e6b-313d4bf1847c', 'fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0');
