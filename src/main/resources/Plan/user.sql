-- Member 테이블 설계
CREATE TABLE `member`
(
    `member_id`   VARCHAR(50)                NOT NULL COMMENT '로그인 ID',
    `password`    VARCHAR(200)               NOT NULL COMMENT '암호화된 비밀번호(BCrypt)',
    `name`        VARCHAR(50)                NOT NULL COMMENT '닉네임 또는 이름',
    `email`       VARCHAR(100)               NOT NULL COMMENT '이메일',
    `phone`       VARCHAR(20) COMMENT '휴대폰 번호(옵션)',
    `role`        ENUM ('USER','ADMIN')      NOT NULL DEFAULT 'USER' COMMENT '권한',
    `status`      ENUM ('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE' COMMENT '계정 상태',
    `join_date`   DATETIME                   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
    `last_login`  DATETIME                            DEFAULT NULL COMMENT '마지막 로그인 시간',
    `update_time` DATETIME                            DEFAULT NULL COMMENT '회원 정보 수정 시간',

    PRIMARY KEY (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='회원 정보 테이블';


-- 관리자 계정 생성.
insert into member (member_id, password, name, email, role)
values ('admin',
        '$2a$10$3taxLS1sG.GIwo0yZlzbBOj8PX75ZKpGYPolEVCA41aFt/fI3viEi', -- 평문 비밀번호: 'admin'
        '관리자',
        'admin@exampl.com',
        'ADMIN');
      
      -- vs code git hub 연동 테스트
      -- vs code git hub 연동 테스트
      