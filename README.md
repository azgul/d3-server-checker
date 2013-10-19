### What is the purpose of this?

It's a very simple tool which identifies whether or not the server you are on in D3 is a laggy server. 

Workaround for the [lag issue at hand](http://eu.battle.net/d3/en/forum/topic/8324862587) on the EU D3 servers.

### Requirements

1. A Windows PC with Diablo 3 installed
2. [Java SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)


### Usage
1. Download [d3-server-checker.jar](https://github.com/azgul/d3-server-checker/raw/master/d3-server-checker.jar) or *git clone* the repository.
2. Run [d3-server-checker.jar](https://github.com/azgul/d3-server-checker/raw/master/d3-server-checker.jar) or compile and run **ServerChecker.java** with `javac *.java` and `java ServerChecker`
3. Sit back, and enjoy the application working it's magic, now playing lag free.

### What does it do?

* [Not in game](https://www.dropbox.com/s/jf8sml7sctrw39y/Screenshot%202013-10-19%2018.39.32.png)
* [Good server](https://www.dropbox.com/s/axzmfy868oy04lz/Screenshot%202013-10-19%2018.40.31.png)
* [Bad server](https://www.dropbox.com/s/578vr9784co7ny0/Screenshot%202013-10-19%2018.39.54.png)
* [Unknown server](https://www.dropbox.com/s/h1e9nnbvbagajoe/Screenshot%202013-10-19%2018.39.44.png)


### Troubleshooting

* You may want to read [this](http://docs.oracle.com/javase/tutorial/essential/environment/paths.html) to understand path variables and how to execute java/javac.
* Your system variables should look more or less like this:
* [Custom system variable (JAVA_BIN)](https://www.dropbox.com/s/mt0qbo9kga22r5l/Screenshot%202013-10-18%2015.21.55.png)
* [Path variable modified](https://www.dropbox.com/s/11vukjocjdbt1qa/Screenshot%202013-10-18%2015.22.06.png)
* Careful not to delete the current path variables, only add new ones!

### Credits
Credit where credit is due. This is based on the work of the awesome people who created [a huge list of bad and good 
servers](http://goo.gl/noQAVc)
