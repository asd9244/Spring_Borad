use board;

select *
from member;

insert into member (member_id, password, name, email, role)
values ('admin',
        '$2a$10$3taxLS1sG.GIwo0yZlzbBOj8PX75ZKpGYPolEVCA41aFt/fI3viEi',
        '관리자',
        'admin@exampl.com',
        'ADMIN');

