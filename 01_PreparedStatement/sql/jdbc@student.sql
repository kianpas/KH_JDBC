--====================================
-- 관리자 계정
--===================================
--student rPwj
create user student
identified by student
default tablespace users;

grant connect, resource to student;

--====================================
-- STUDENT 계정
--====================================
create table member(
    member_id varchar2(20),
    password varchar2(20) not null,
    member_name varchar2(100) not null,
    gender char(1),
    age number,
    email varchar2(200),
    phone char(11) not null,
    adress varchar2(1000),
    hobby varchar2(100), --농구,음악감상,영화
    enroll_date date default sysdate,
    constraint pk_member_id primary key(member_id),
    constraint ck_member_gender check(gender in ('M', 'F'))
);

insert into member
values('leess', '1234', '이순신', 'M', 45, 'leess@naver.com', '01012121212',
    '전남 여수', '목공예', default
    );
    --널값 있는 것으로
insert into member
values('ygsgs', '1234', '유관순', 'F', null, null, '01031313131',
    null, null, default
    );    
    
select * from member;
commit;

desc member;