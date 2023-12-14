-- 개인
insert into personnel(name, date_of_birth, phone, landline, email, address, profile_image, department_type, department_id, small_group_id)
values('test1', '1997-08-26', '010-0000-0001', '032-000-0001', 'test1Email@gmail.com', '인천광역시 서구 석남동', '', 'JOSHUA', 4, 1);
insert into personnel(name, date_of_birth, phone, landline, email, address, profile_image, department_type, department_id, small_group_id)
values('test2', '1997-08-26', '010-0000-0002', '032-000-0002', 'test2Email@gmail.com', '인천광역시 서구 석남동', '', 'JOSHUA', 4, 2);
insert into personnel(name, date_of_birth, phone, landline, email, address, profile_image, department_type, department_id, small_group_id)
values('test3', '1997-08-26', '010-0000-0003', '032-000-0003', 'test3Email@gmail.com', '인천광역시 서구 석남동', '', 'JOSHUA', 4, 2);

-- 부서
insert into department(name, enrollment) values('kindergarten', 11);
insert into department(name, enrollment) values('holykids', 12);
insert into department(name, enrollment) values('paulcommunity', 13);
insert into department(name, enrollment) values('joshua', 14);

-- 출석기록
insert into attendance(attendance_date, attendance_check, department_id, personnel_id) values('2023-12-10', 'ATTENDANCE', 4, 1);
insert into attendance(attendance_date, attendance_check, department_id, personnel_id) values('2023-12-10', 'ABSENT', 4, 2);
insert into attendance(attendance_date, attendance_check, department_id, personnel_id) values('2023-12-10', 'ATTENDANCE', 4, 3);
insert into attendance(attendance_date, attendance_check, department_id, personnel_id) values('2023-12-10', 'ATTENDANCE', 3, 2);
insert into attendance(attendance_date, attendance_check, department_id, personnel_id) values('2023-12-10', 'ATTENDANCE', 1, 1);
insert into attendance(attendance_date, attendance_check, department_id, personnel_id) values('2023-12-10', 'ATTENDANCE', 2, 1);

-- 소그룹
insert into small_group(name, leader, department_id) values('소그룹 이름1', '리더1', 4);
insert into small_group(name, leader, department_id) values('소그룹 이름2', '리더2', 4);
insert into small_group(name, leader, department_id) values('소그룹 이름3', '리더3', 4);
insert into small_group(name, leader, department_id) values('소그룹 이름4', '리더4', 4);