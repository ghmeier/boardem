#Makefile for my dropwizard experiment

all : build

#Uses maven to package the app
build : 
	mvn package

run : build
	java -jar target/test-0.0.1-SNAPSHOT.jar server test.yml

clean : 
	rm target/*.jar