# Jenkins Pipeline for Maven Java Application

This repository contains a Jenkins pipeline configuration that automates the build and testing process for a Java application using Maven.

## Pipeline Overview

* **Environment Variables**

  * `APP_PORT=9090` – the application runs on port 9090
  * `JOB_NAME_VAR` – stores the Jenkins job name

* **Stages**

  1. **Build**
     Uses Maven to build the project, skipping unit tests:

     ```bash
     mvn clean package -DskipTests
     ```
  2. **Integration Test (Parallel)**
     Runs two tasks in parallel:

     * **Running Application**
       Launches the `contact.war` file from the `target` directory.
       The stage is limited to **60 seconds** with a `try/catch` block to handle timeout gracefully.
     * **Running Test**
       Waits 30 seconds for the application to start and runs only the `RestIT` integration test:

       ```bash
       mvn -Dtest=RestIT test
       ```

## Result

* The application is built and packaged successfully.
* Integration tests are executed in parallel with the application run.
* Timeouts are handled without failing the pipeline, ensuring successful completion.
