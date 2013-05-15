sermonis
========

Sermonis started as an experimentation using mongoDB, atmosphere and Spring-Data and finished with a working web chat application using these technologies.

The Sermonis application was born. Its goal is to provide users an easy way to deploy a simple web application that let users talk with each other without 
the need to install an application like Skype.

Sermonis supports Markdown and password protected rooms. It‘s not aimed to grow bigger than that for the moment.


### First run

In order to test Sermonis on your local machine all you need is Java, Maven and mongoDB. If you want to test rapidly just type the following command:

Install a mongoDB on your machine, by downloading it here, then open a shell and type the following to run it:

    
    $ cd mongodb/bin
    $ mkdir data
    $ mongod --dbpath data
    

Download Sermonis and run it like this:

    
    $ cd sermonis
    $ mvn compile exec:java -Dexec.mainClass=me.grison.sermonis.SermonisInit jetty:run
    

During the execution of the command you will be asked for an administrator password. This very password will be stored in a file called `sermonis.properties` in an encrypted way (md5 hash of an AES password encrypted with a secret token generated previously).

Here is a sample of `sermonis.properties` file

    #                                          _     
    #    ________  _________ ___  ____  ____  (_)____
    #   / ___/ _ \/ ___/ __ `__ \/ __ \/ __ \/ / ___/
    #  (__  )  __/ /  / / / / / / /_/ / / / / (__  ) 
    # /____/\___/_/  /_/ /_/ /_/\____/_/ /_/_/____/  
    #                                               
    
    # Sermonis property file generated on Mon May 13 10:11:44 CEST 2013
    
    # Sermonis secret token used to encrypt password and browser request information into cookies
    # to secure access to password protected room and admin interface
    secretKey=0b388F1ab18EA5-7426E2-2e2C-8f1781683f-68Da1b1eA2C8-b5BD161Fb2f4140D06
    
    # MD5 hash of the admin password AES encrypted with the above secret token
    adminPassword=E591B207FA96239F368382E45BC55816


and a sample of the execution of the previous mvn command.

    $ mvn compile exec:java -Dexec.mainClass=me.grison.sermonis.SermonisInit jetty:run
    [INFO] Scanning for projects...
    [INFO] ------------------------------------------------------------------------
    [INFO] Building sermonis Maven Webapp 0.0.1-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    	                                          _     
    	    ________  _________ ___  ____  ____  (_)____
    	   / ___/ _ \/ ___/ __ `__ \/ __ \/ __ \/ / ___/
    	  (__  )  __/ /  / / / / / / /_/ / / / / (__  ) 
    	 /____/\___/_/  /_/ /_/ /_/\____/_/ /_/_/____/  
    	                                               
    
    You're about to generate a secret key and admin password for sermonis.
    
    Generating the secret key...
    Please provide an admin password: 
    foobar
    Updating sermonis.properties...
    All done!
    [INFO] 
    [INFO] >>> jetty-maven-plugin:8.1.0.v20120127:run (default-cli) @ sermonis >>>
    [INFO] Configuring Jetty for project: sermonis Maven Webapp
    ...
    [INFO] Started Jetty Server
    [INFO] Starting scanner at interval of 1 seconds.

### Play with it

Just hit http://localhost:9001 and you will be redirected to a newly created room asking you for your username. 
Just share that same URL to someone else so that he can join you, and finally have some chat with your friend.


### About the name

sermonis means (sort-of) discussion in Latin, since a corpus of chat messages is seen as a discussion, that name make sense :-).


## Technical

### Technical Stack

Sermonis was started as an experimentation around multiple technologies, which are listed below:

* Atmosphere
* Spring with Spring-Data, Spring-Data-Mongo and Spring-Atmosphere
* MongoDB
* AngularJS
* Websockets

I‘m using Websockets to provide realtime-like chat within browser, using a library called Atmosphere.
The RT stuff is bound to `.../async/...` URLs and is supported by recent web-browsers. You will note that not every Java application container supports them. If you‘re using Tomcat you will need Tomcat7. Jetty and Glassfish supports them too.

### MongoDB

In Sermonis MongoDB is used only to save chat rooms configuration (password, administrator) and chat messages within them.
Used with Spring-Data and it‘s binding specific to MongoDB it is really super-easy to use.

### AngularJS & Bootstrap

I‘ve used AngularJS to render the messages in the chat rooms, with some jQuery touch where I‘m not enough skilled with Angular.
The skin is using the well-known-already-seen-everywhere Bootstrap CSS framework.

### Cookies

Sermonis use cookies to save user preferences (name, color, markdown, password (encrypted)). 
Cookies are related to one and only one room. This means that a user can have different name, color and password for every room he has access.

### Use cases

Let‘s say the url of your deployed version of Sermonis is http://localhost/sermonis/.

#### Creating a room
Just hit the index page of the application at http://localhost/sermonis/, you will be redirected to a newly created room.

#### Creating a password protected room
Just hit the index page of the application with a query parameter withPassword at http://localhost/sermonis/?withPassword, 
you will be redirected to a page asking you the password with which the room should be protected, along with an administrator password. 
When you click on Create the room will be created and you will be redirected on it directly.

#### Joining a room
Just hit the URL someone shared you.

#### Joining a password protected room
Just hit the URL someone shared you. You will be asked for a password, providing it and clicking Join will redirect you to the chat room. 
Depending on the room configuration, you may be able to see the chat messages history.

#### Choosing your user color
Click on your username, and pick a color from the color picker. The color is stored in a cookie.

#### Messages
To send a message in the room just type it in the input at the bottom of the page and press Enter. 
Sermonis supports Markdown by relying on the PageDown Markdown JS Library.

The IRC `/me` command is supported, and a shortcut for posting images is available with `img:url` instead of the longer markdown syntax `![alt](url)`.


