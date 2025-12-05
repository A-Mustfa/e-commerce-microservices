-- 1. inserting data into users

INSERT INTO USERS(user_phone, user_fname, user_lname, user_address)
VALUES ('01112345678', 'Ali', 'Hassan', 'Cairo')
,('01098765432', 'Sarah', 'Mahmoud', 'Alexandria')
,('01234567890', 'Omar', 'Khalid', 'Giza')
,('01555512345', 'Fatima', 'Ali', 'Luxor')
,('01011122334', 'Youssef', 'Ibrahim', 'Aswan')
,('01199988877', 'Layla', 'Mohamed', 'Port Said'),
('01277766655', 'Karim', 'Osman', 'Suez'),
('01044455566', 'Nour', 'Saleh', 'Mansoura'),
('01122233344', 'Hana', 'Mustafa', 'Ismailia'),
('01288899900', 'Amir', 'Farouk', 'Tanta'),
('01066677788', 'Zainab', 'Rashid', 'Zagazig'),
('01144455566', 'Khalid', 'Nasser', 'Damietta'),
('01211122233', 'Mona', 'Tarek', 'Beni Suef'),
('01088899911', 'Samir', 'Fathi', 'Minya'),
('01177788899', 'Rana', 'Hussein', 'Qena'),
('01233344455', 'Tariq', 'Zaki', 'Sohag'),
('01022233344', 'Dalia', 'Adel', 'Assiut'),
('01155566677', 'Bassem', 'Hamdi', 'Fayoum'),
('01266677788', 'Salma', 'Wael', 'Hurghada'),
('01077788899', 'Jamal', 'Raafat', 'Sharm El Sheikh'),
('01188899900', 'Nadia', 'Samir', 'Dahab'),
('01244455566', 'Ramy', 'Ashraf', 'Marsa Matrouh'),
('01055566677', 'Soha', 'Emad', 'El Arish'),
('01166677788', 'Walid', 'Kamal', 'Ras Sidr'),
('01255566677', 'Heba', 'Naser', 'Siwa Oasis'),
('01033344455', 'Fadi', 'Bakri', 'El Gouna'),
('01133344455', 'Mai', 'Sherif', 'New Cairo'),
('01222233344', 'Adel', 'Hany', '6th of October'),
('01099988877', 'Lina', 'Gamal', 'Sheikh Zayed'),
('01100011122', 'Hisham', 'Said', 'Nasr City');

--------

-- 2.insert data into roles

insert into ROLES(role_type)
values ('USER'),('ADMIN'),('MANAGER')

-------
-- 3. insert data into permssions

insert into PERMISSIONS(permission_type)
values ('CREATE_USER'),('UPDATE_USER'),('DELETE_USER'),('ADD_TASK'),('REMOVE_TASK')

-------
-- 4. insert data into role_permissoin

insert into ROLE_PERMISSION(role_id,permission_id)
values(1,4),(1,5)

insert into ROLE_PERMISSION(role_id,permission_id)
values(2,1),(2,2),(2,4),(2,5)

insert into ROLE_PERMISSION(role_id,permission_id)
values(3,1),(3,2),(3,3),(3,4),(3,5)

------------
-- 5. insert data into user, role
insert into user_role(user_id,role_id)
values
(1,2),
(2,1),
(3,1),
(4,1),
(5,1),
(6,2),
(7,3),
(8,3),
(9,3),
(10,1),
(11,1),
(12,2),
(13,2),
(14,3),
(15,2),
(16,1),
(17,2),
(18,3),
(19,1),
(20,2),
(21,1),
(22,3),
(23,2),
(24,1),
(25,2),
(26,1),
(27,3),
(28,2),
(29,1),
(30,2),
(31,3);

---

