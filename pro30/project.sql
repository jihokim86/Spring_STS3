create table news(
 newsNo number(5),
 newsTitle varchar2(100),
 newsContent varchar2(2000),
 imageFileName varchar2(100), 
 name varchar2(10),
 newsWriteDate date
 );
 
 drop table news;
-- drop sequence product_seq; 
 
 SELECT
     *
 FROM news;
 
select Max(newsNo)+1 from news;
 
create sequence new_seq start with 1 increment by 1;
DROP SEQUENCE new_seq;
--select last_number from user_sequences where sequence_name='product_seq';

--alter sequence product_seq INCREMENT by -27;
 select *from news 
where newsNo=1;



insert into news values(1,'1 제목입력 테스트','1 뉴스내용을 입력하세요','',sysdate);
delete from news 
where newsNo=2;

commit;