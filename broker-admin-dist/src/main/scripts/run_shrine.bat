@ECHO OFF

java -Daktin.broker.password=CHANGEME -cp lib\* org.aktin.broker.admin.standalone.HttpServer 8080