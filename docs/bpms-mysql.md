### Create DataBase

~~~
CREATE DATABASE bpms;
create user 'bpms_user'@'localhost' identified by 'bpms_pass';
grant all on bpms.* to bpms_user@'localhost';
FLUSH PRIVILEGES;
~~~

### Start a Process

~~~
select id, duration, start_date, end_date, processId, processInstanceId, processName, status from ProcessInstanceLog;
select id, connection, log_date, nodeId, nodeInstanceId, nodeName, nodeType, processId, processInstanceId from NodeInstanceLog;
select workItemId, creationDate, name, processInstanceId, state, OPTLOCK from WorkItemInfo;
select DTYPE, id from OrganizationalEntity;
~~~
