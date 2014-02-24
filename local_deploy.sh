#Script I wrote to deploy my Java code to a local tomcat installation in my home directory
cp -R classes ~/tomcat/apache-tomcat-7.0.52/webapps/ROOT/WEB-INF/
cp web.xml ~/tomcat/apache-tomcat-7.0.52/webapps/ROOT/WEB-INF/
~/./tomcat/stop
~/./tomcat/start
