
create role r_connect;
grant create session to r_connect;
grant alter session to r_connect;

create role r_resource;
grant create table to r_resource;
grant create view to r_resource;
grant create sequence to r_resource;
grant create procedure to r_resource;
grant create type to r_resource;
grant create trigger to r_resource;

create role r_catalog;
grant select any dictionary to r_catalog;
grant select_catalog_role to r_catalog;

create user           bluetree
identified by         oracle
default tablespace    users
temporary tablespace  temp
profile               default
quota                 unlimited on users
account               unlock;

grant r_catalog to bluetree;
grant r_resource to bluetree;
grant r_connect to bluetree;


exec dbms_stats.gather_schema_stats ('BLUETREE');



