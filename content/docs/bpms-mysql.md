### Create DataBase

~~~
CREATE DATABASE bpms;
create user 'bpms_user'@'localhost' identified by 'bpms_pass';
grant all on bpms.* to bpms_user@'localhost';
FLUSH PRIVILEGES;
~~~

### Start a Process & Task

~~~
//Insert
insert into ProcessInstanceInfo (lastModificationDate, lastReadDate, processId, processInstanceByteArray, startDate, state, OPTLOCK)
insert into VariableInstanceLog (log_date, externalId, oldValue, processId, processInstanceId, value, variableId, variableInstanceId)
insert into ProcessInstanceLog (duration, end_date, externalId, user_identity, outcome, parentProcessInstanceId, processId, processInstanceId, processName, processVersion, start_date, status) 
insert into NodeInstanceLog (connection, log_date, externalId, nodeId, nodeInstanceId, nodeName, nodeType, processId, processInstanceId, type, workItemId)

insert into WorkItemInfo (creationDate, name, processInstanceId, state, OPTLOCK, workItemByteArray)
insert into Content (content)
insert into Task (archived, allowedToDelegate, formName, taskInitiator_id, priority, subTaskStrategy, activationTime, actualOwner_id, createdBy_id, createdOn, deploymentId, documentAccessType, documentContentId, documentType, expirationTime, faultAccessType, faultContentId, faultName, faultType, outputAccessType, outputContentId, outputType, parentId, previousStatus, processId, processInstanceId, processSessionId, skipable, status, workItemId, taskType, OPTLOCK)
insert into I18NText (language, shortText, text)
insert into PeopleAssignments_BAs(task_id, entity_id)
insert into PeopleAssignments_PotOwners (task_id, entity_id)

//Select
select InstanceId, lastModificationDate, lastReadDate, processId, startDate, state, OPTLOCK from ProcessInstanceInfo; 
select id, log_date, oldValue, processId, processInstanceId, variableId, variableInstanceId, value from VariableInstanceLog;
select id, duration, start_date, end_date, processId, processInstanceId, processName, status from ProcessInstanceLog;
select id, connection, log_date, nodeId, nodeInstanceId, nodeName, nodeType, processId, processInstanceId from NodeInstanceLog;

select workItemId, creationDate, name, processInstanceId, state, OPTLOCK from WorkItemInfo;
select id from Content;
select id, createdOn, deploymentId, processInstanceId, processId, status, OPTLOCK, createdBy_id from Task;
select id, language, shortText, text, Task_Subjects_Id, Task_Names_Id, Task_Descriptions_Id from I18NText;
select task_id, entity_id from PeopleAssignments_BAs;
select DTYPE, id from OrganizationalEntity;
select task_id, entity_id from PeopleAssignments_PotOwners;
~~~
