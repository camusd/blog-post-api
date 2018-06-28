# Blog Post API

This repository contains my implementation of a small Java Spring Boot microservice. It implements a REST API with two endpoints that could theoretically be used as the backend of a blogging website. It uses Gradle as the build tool, Docker for containerization, Helm for deployment, and Prometheus and Grafana for monitoring.

## Building and Running

The easiest way to build and start the application is to run it with Gradle:

- Install [JRE 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) if it is not installed already. You can check the version of Java installed by running

```bash
java -version
```

- Build and run with Gradle:

```bash
./gradlew build run
```

The application will be accessible at **localhost:8080**. Here are some sample curl commands to test the app out:

```bash
curl -v -H "Content-Type:application/json" -d '{"title":"Test","body":"This is a test"}' localhost:8080/post
```

```bash
curl -v localhost:8080/posts
```

## Running in Docker

- Install [Docker CE](https://docs.docker.com/install/)

- Build and run the docker container with Gradle:

```bash
./gradlew build docker dockerRun
```

The Docker container will also be accessible at **localhost:8080** and can be tested with the same curl commands as above.

## Running in Minikube with Helm

- Install [Minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)

- Install [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)

- Install [Helm](https://docs.helm.sh/using_helm/)

- Start Minikube:

```bash
minikube start
```

- Set kubectl context:

```bash
kubectl config use-context minikube
```

- Initialize Helm:

```bash
helm init
```

- Deploy application to Minikube. From the root of the project:

```bash
helm install charts --name=blogpostapi
```

The application should deployed to the Minikube cluster. Additionally, a Prometheus and a Grafana container will also be deployed and ready within a few minutes. The application can be tested by replacing **localhost:8080** in the previous curl commands with **$(minikube ip):30010**.

To view the status of the deployment run:

```bash
minikube dashboard
```

To access Grafana, first determine the IP address Minikube is accessible at by running:

```bash
minikube ip
```

In a browser, navigate to the Minikube IP on port 30011. The login credentials have been left at their default values.

**Username**: admin

**Password**: strongpassword

On the following page, click on the _Home_ button in the upper left hand corner. There should be two preinstalled Grafana dashboards. One tracks metrics of the Kubernetes cluster and the other tracks the Java application.
