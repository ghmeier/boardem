#BoardEm (Working name)
---

##Local Server Setup
---

- Install Maven - Look it up for your system
- Install corsproxy - `sudo npm install -g corsproxy`
- Make sure ionic is up to date - `npm install -g ionic`

- For the next part you'll need three terminal tabs open.
- - In one: `corsproxy`
- - - This starts a proxy server on localhost:9292
- - In the next cd to boardem-app/boardem and `ionic serve`
- - - This starts the ionic app server on localhost:8100
- - Finally cd to boarem-server and `mvn package`
- - Then cd to ./target and `java -jar boardem-1.jar server`
- - - This starts the dropwizard server

###Now you're set to run things :)
