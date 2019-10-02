# Teaching-HEIGVD-AMT-Example-Notes

### Branch: step-001-initial-structure
 - The project structure has been created, by copying the "quotes servlet" example.
 - Dependency on javaee-api has been bumped from 7.0 to 8.0.  

### Branch: step-002-model
 - Removed most of the files from the original skelethon
 - Added Lombok dependency
 - Bumped JUnit to v5
 - Created two model entities: user and note

### Branch: step-003-datastore
 - Refactored model classes to use builder pattern and immutable objects (lombok)
 - Introduced an in-memory datastore, for Users and Notes
 - 100% test coverage on model and datastore packages

