use board;

select *
from member;

insert into member (member_id, password, name, email, role)
values ('admin',
        '$2a$10$F2E9zE1GbQX2H1IYvM7O5Oj5VaD4yH6gLTQSwpS8GcRkS9E2k1GdS',
        '관리자',
        'admin@exampl.com',
        'ADMIN');

