
Insert into roles(id, name)
Values
('798fe811-feb1-11e7-a30f-7b1a8bfa155b','user'),
('89179577-feb1-11e7-a9e6-e35467cb8f58', 'admin');

Insert into users(id, login, pass, email, role_id)
values
('1f940bd3-f7a5-11e7-93e6-a30f6152aa28', 'user', 'pass', 'email@gmail.com', '89179577-feb1-11e7-a9e6-e35467cb8f58'),
('34fa41df-f7a5-11e7-adbd-41982924721f', 'a', 'a', 'waytowill@gmail.com', '798fe811-feb1-11e7-a30f-7b1a8bfa155b');

Insert into genres(id, name)
values
('c1fae08d-feb1-11e7-8e6b-313d4bf1847c','comics'),
('cf8c14b2-feb1-11e7-ba96-313a67f5f27a','newspaper');

Insert into publishers(id, name)
values
('fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0','Marvel'),
('02e0cc80-feb2-11e7-9fc8-b3d2dbb83666','Dc');

Insert into periodicals(id, name, description, subscr_cost, issues_per_year, is_limited, genre_id, publisher_id)
values
('426f8ce0-feb2-11e7-bc2e-bd4c345931c8','gambit', '', 12.50, 24, 1, 'c1fae08d-feb1-11e7-8e6b-313d4bf1847c', 'fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0'),
('46b26700-feb2-11e7-b445-f3b95d28ec2b','wolverine','', 10.50, 24, 1, 'c1fae08d-feb1-11e7-8e6b-313d4bf1847c', 'fc13ecc9-feb1-11e7-8a29-d5a2d48a37f0');

insert into periodical_issues(id, issue_no, name, publishing_date, periodical_id)
values
('e77c82ed-feb2-11e7-985b-a3ac4d073c34', 1, 'green goblin invasion', now(), '426f8ce0-feb2-11e7-bc2e-bd4c345931c8'),
('e77c82ee-feb2-11e7-985b-b940b84b0669', 2, 'rhino is in town', now(), '426f8ce0-feb2-11e7-bc2e-bd4c345931c8'),

('e77c82ef-feb2-11e7-985b-a9559739b619', 1, 'green goblin invasion2', now(), '46b26700-feb2-11e7-b445-f3b95d28ec2b'),
('e77c82f0-feb2-11e7-985b-5bfcc7ebcf40', 2, 'rhino is in town2', now(),'46b26700-feb2-11e7-b445-f3b95d28ec2b');

Insert into subscriptions(user_id, periodical_id)
Values
('1f940bd3-f7a5-11e7-93e6-a30f6152aa28','426f8ce0-feb2-11e7-bc2e-bd4c345931c8'),
('1f940bd3-f7a5-11e7-93e6-a30f6152aa28','46b26700-feb2-11e7-b445-f3b95d28ec2b');