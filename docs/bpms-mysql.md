### Create DataBase

~~~
CREATE DATABASE bpms;
create user 'bpms_user'@'localhost' identified by 'bpms_pass';
grant all on bpms.* to bpms_user@'localhost';
FLUSH PRIVILEGES;
~~~
