# rest-assured-java

## Project structure

```
.github/                                              # GitHub workflow configurations
logs/                                                 # Log4j log files
reports/                                              # Extent HTML report files
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── restassured/
│   │               ├── Category.java
│   │               ├── HttpMethod.java
│   │               ├── constant/                     # Project related constants including Application and Reporter constants
│   │               │   ├── ApplicationConstant.java
│   │               │   ├── AuthenticationConstant.java
│   │               │   ├── CommonConstant.java
│   │               │   └── ReporterConstant.java
│   │               ├── model/                        # Model classes which represents the request JSON payload
│   │               │   ├── AuthenticationRequest.java
│   │               │   ├── BookingDates.java
│   │               │   ├── BookingRequest.java
│   │               ├── helper/                        # Helper classes which support methods
│   │               │   ├── Helper.java
│   │               ├── service/
│   │               │   ├── ExtentReportService.java  # Extent report setup services
│   │               │   └── app/                      # Application service classes
│   │               │       ├── AuthenticationService.java
│   │               │       └── BookingService.java
│   │               └── util/                         # Utility methods
│   │                   ├── AnnotationReader.java
│   │                   └── FileReader.java
│   │                   └── JsonFormatter.java
│   │                   └── Log4jFilter.java
│   │                   └── RestClient.java
│   │                   └── Listener.java
│   └── resources/                                    # Resource files for logging and reporting
│       ├── log4j2.xml
│       └── reporter.properties
├── test/
│   ├── java/
│   │   └── com/
│   │       └── restassured/
│   │               └── test/
│   │                   └── authenticationtest/
│   │                       ├── AuthTest.java                 # Authentication tests
│   │                   └── bookingtest/
│   │                       ├── CreateBookingTest.java        # API test for creating a booking
│   │                       ├── DeleteBookingTest.java        # API test for deleting a booking
│   │                       ├── GetBookingInfoTest.java       # API test for fetching all bookings
│   │                       └── UpdateBookingTest.java        # API test for updating a booking
│   │                   ├── constant/                     # Test constants
│   │                   │   └── TestCategory.java
│   │                   └── BaseTest.java                 # Base test class setup
│   └── resources/
│       └── env/                                          # Environment configurations
│           └── dev.properties
│       └── smoke-suite.xml                               # TestNG smoke test suite configuration
target/                                                   # Compiled code and build artifacts are stored here
.gitignore                                                # Specifies files and directories to be ignored by Git
pom.xml                                                   # Maven configuration file for dependencies and build settings
README.md                                                 # Repository overview and instructions (This file)
```

## How to run tests
1. Using IntelliJ IDEA
    * Create testNG runner
    * Select test kind `Suite` and path to direct `smoke-suite.xml`
    * Enter environment variables: `env:dev`
    * Click on `Apply` and click on `Run`

2. Using Command Line
    * To run the smoke test suite against the UAT environment
      `mvn clean test -Pdev -Psmoke-test`

**Note**: By default, if no Maven profiles are selected, the tests will be executed on the `dev` environment.