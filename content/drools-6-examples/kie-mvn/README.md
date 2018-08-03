What's this
-----------

This demo show how Maven based BRMS 6 how to load remove BRMS server repository dynamically.

Procesures
----------

* Install BRMS 6 in remote server, make sure a user with admin role exist, refer to [1](https://access.redhat.com/site/documentation/en-US/Red_Hat_JBoss_BRMS/6.0/html/Installation_Guide/Creating_the_users.html) for details, this user need to set up the Maven setting.xml.

* Create HelloWorld Demo in BRMS 6 server, the creation steps refer to [2](https://access.redhat.com/site/documentation/en-US/Red_Hat_JBoss_BRMS/6.0/html/Getting_Started_Guide/chap-Hello_World_rule_example.html), note that kmodule details like below:
~~~
			<groupId>org.brms</groupId>
			<artifactId>HelloWorld</artifactId>
			<version>1.0</version>
~~~

* update the setting.xml to match yours, in my test, 
  1) the brms 6 server run on 10.66.218.46(start the server via ./standalone.sh -b 10.66.218.46 -bmanagement=10.66.218.46)
  2) the user created in step 1 are admin/password1!

* execute the below mvn build compile code in kie-mvn project
~~~
mvn -s settings.xml compile
~~~

If the commands execute success, then maven load remote dependency success.

Link
----

http://blog.athico.com/2013/12/deployment-with-drools-60.html
