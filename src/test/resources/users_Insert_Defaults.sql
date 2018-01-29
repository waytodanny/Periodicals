Insert into roles(id, name)
Values
('798fe811-feb1-11e7-a30f-7b1a8bfa155b','user'),
('89179577-feb1-11e7-a9e6-e35467cb8f58', 'admin');

Insert into users(id, login, pass, email, role_id)
values
('1f940bd3-f7a5-11e7-93e6-a30f6152aa28', 'user', '1A1DC91C907325C69271DDF0C944BC72', 'email@gmail.com', '89179577-feb1-11e7-a9e6-e35467cb8f58'),
('34fa41df-f7a5-11e7-adbd-41982924721f', 'a', 'a', 'waytowill@gmail.com', '798fe811-feb1-11e7-a30f-7b1a8bfa155b'),
('57f2e55d-f7a5-11e7-89c9-8fc8f2dde3f7', 'b', 'b', 'waytojonny@gmail.com', '798fe811-feb1-11e7-a30f-7b1a8bfa155b');