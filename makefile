all : build

build :
	mvn package

run :
	rm output.txt
	java -jar target/boardem-1.jar server > output.txt &
