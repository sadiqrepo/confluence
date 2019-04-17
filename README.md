# Confluence cloud automation

Enabling test automation in Java

Confluence(framework) is built on top of TestNG and Selenium to provide a set of capabilities that get you up and running with WebDriver in a short time that can be used for testing Confluence Cloud product.


## Prerequisites

1. Make sure the following software is installed on your machine before you start using this framework:

** Java JDK 1.8
** Maven 3.2.x
** Intellij IDEA (IDE)


2. Can be executed on Mac or Linux(Cent OS)





## Usage

1. Check out the code from the repository
2. Run the runtest.xml file

The above will execute the tests on Chrome and Firefox browser serially. Once executed, Pass/Fail screenshots will be under 'screenshots' folder.
Logs will be recorded under logs folder and reports will be found under 'test-output/html' folder.


Data is fed via 'resources/testdata/blankpage.csv' containing title and description for the page creation. 
CSV paths are provided through 'resources/testdata.properties' file
All configuration required for the framework is passed through 'resources/config.properties'


