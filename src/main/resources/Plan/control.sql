-- Active: 1766830996450@@127.0.0.1@3306@board
use board;

select * 
from member
where member.member_id = 'user21';

insert into member (member_id, password, name, email, role)
values ('admin',
        '$2a$10$3taxLS1sG.GIwo0yZlzbBOj8PX75ZKpGYPolEVCA41aFt/fI3viEi',
        '관리자',
        'admin@exampl.com',
        'ADMIN');



-- 모든 테스트 계정의 비밀번호는 '1234' 입니다.
-- BCrypt Hash for '1234': $2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW

INSERT INTO member (member_id, password, name, email, phone, role, status)
VALUES
('user1',  '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자1',  'user1@test.com',  '010-1111-0001', 'USER', 'ACTIVE'),
('user2',  '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자2',  'user2@test.com',  '010-1111-0002', 'USER', 'ACTIVE'),
('user3',  '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자3',  'user3@test.com',  '010-1111-0003', 'USER', 'ACTIVE'),
('user4',  '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자4',  'user4@test.com',  '010-1111-0004', 'USER', 'ACTIVE'),
('user5',  '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자5',  'user5@test.com',  '010-1111-0005', 'USER', 'ACTIVE'),
('user6',  '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자6',  'user6@test.com',  '010-1111-0006', 'USER', 'ACTIVE'),
('user7',  '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자7',  'user7@test.com',  '010-1111-0007', 'USER', 'ACTIVE'),
('user8',  '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자8',  'user8@test.com',  '010-1111-0008', 'USER', 'ACTIVE'),
('user9',  '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자9',  'user9@test.com',  '010-1111-0009', 'USER', 'ACTIVE'),
('user10', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자10', 'user10@test.com', '010-1111-0010', 'USER', 'ACTIVE'),
('user11', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자11', 'user11@test.com', '010-1111-0011', 'USER', 'ACTIVE'),
('user12', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자12', 'user12@test.com', '010-1111-0012', 'USER', 'ACTIVE'),
('user13', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자13', 'user13@test.com', '010-1111-0013', 'USER', 'ACTIVE'),
('user14', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자14', 'user14@test.com', '010-1111-0014', 'USER', 'ACTIVE'),
('user15', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자15', 'user15@test.com', '010-1111-0015', 'USER', 'ACTIVE'),
('user16', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자16', 'user16@test.com', '010-1111-0016', 'USER', 'ACTIVE'),
('user17', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자17', 'user17@test.com', '010-1111-0017', 'USER', 'ACTIVE'),
('user18', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자18', 'user18@test.com', '010-1111-0018', 'USER', 'ACTIVE'),
('user19', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자19', 'user19@test.com', '010-1111-0019', 'USER', 'ACTIVE'),
('user20', '$2a$10$tUUfk1E0jGr90ntHxl/wE.lzrVvAQfxJ7nRo3RYXi2XqJtlv2h.UW', '사용자20', 'user20@test.com', '010-1111-0020', 'USER', 'ACTIVE');


UPDATE member
SET password = '$2a$10$cvocQM8Yb059t0CM3uJ6XOJmofdMRCG3XyYCR.hiJPhSDN9CpzykS'
WHERE member_id LIKE 'user%';

