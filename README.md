# bicyclepolicy
Spring Boot Project, PINS Tech Bicycle policy premium calculation

To correctly run this applicaion you need to provide groovy BaseScript.groovy which represents how you can calculate insurance premium for different type of make, model and age.
Template how it should looks like is in resources/templates.


# Improvement points:
- Validation before startup application if BaseScript contains required methods
- Validation before startup application if BaseScript data contains required values in getAgeFactorData line without Make and Model
- Unit tests for services
- Functional tests
- Use Maven Failsafe Plugin to run integration tests in maven phase
