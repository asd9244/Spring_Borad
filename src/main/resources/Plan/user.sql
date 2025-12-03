-- Member 테이블 설계
member
(
    member_id      VARCHAR(50)  PRIMARY KEY,   -- 로그인 ID
    password       VARCHAR(200) NOT NULL,      -- BCrypt 암호화 PW
    name           VARCHAR(50)  NOT NULL,      -- 사용자 이름 or 닉네임
    role           ENUM('USER','ADMIN') NOT NULL DEFAULT 'USER',
    join_date      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
