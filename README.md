RollerCoaster
=============

Prerequisites
-------------

JAR files need to be installed to your local Maven repository. 

Install AWS SDK for Java on GAE:
mvn install:install-file -Dfile=aws-sdk-for-java-on-gae-1.1.7.1.jar -DgroupId=com.amazonaws -DartifactId=aws-sdk-for-java-on-gae -Dversion=1.1.7.1 -Dpackaging=jar

Install Scribe with OAuth 2.0 support:
mvn install:install-file -Dfile=scribe-1.3.2-patched.jar -DgroupId=org.scribe -DartifactId=scribe -Dversion=1.3.2-patched -Dpackaging=jar
