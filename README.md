#ToDo
# ToDoList-NDuggan-SDET-JUNE2021

The aim of this project was to test the developers knowledge in the following modules:
Programming and software development
Software design
Testing
Systems integration and build
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 
See deployment for notes on how to deploy the project on a live system.

### Prerequisites

The following software is neccessary in order to use 
* Java 1.8 https://www.java.com/en/download
* MySql https://dev.mysql.com/downloads/installer
* Eclipse https://www.eclipse.org/downloads/packages/release/2021-06/r/eclipse-ide-java-
* Visual studio code https://code.visualstudio.com/Download
**other IDE's can be used**

### Installing

* First make sure your environement is set up with all software that is neccassery, links to prerequisites has been proveded above.
* Then you should clone the project to a local repository or fork it to a remote repositiory.
* In order to clone to a local repository create a folder on your local machine. 
* Open up a git bash cmd prompt - right click inside folder select git bash here
* Type in command - git init
* Type in command - git clone https://github.com/NDuggan-dev/ToDoList-NDuggan-SDET-JUNE2021.git

## Running the tests

To run tests on the application open the application on your command prompt and use command - mvn clean

### Unit Tests 

This application has unit tests which have a coverage above 80%. If you wish to run these tests open your applcation on command prompt and use command - mvn clean;

### Deployment

In order to deploy this application 
* Open up the src of the application in your command prompt
* Run command mvn package
* Go to directory target using command cd target (works if git bash terminal)
* Use command java -jar ims-0.0.1-jar-with-dependencies.jar

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Niall Duggan** - *Initial work* - [NDuggan-dev] https://github.com/NDuggan-dev/

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

* For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* Huge thanks to my team mates Simon White and Megan Crouch.
Books 
* Testing Java Microservices: Using Arquillian, Hoverfly, Assertj, Junit, Selenium, and Mockito 1 by Soto Bueno, Alex, Porter, Jason, Gumbrecht (Autoren), Andy (ISBN: 9781617292897)
* UML for Java¿ Programmers (Robert C. Martin) (ISB: 978-0131428485)



