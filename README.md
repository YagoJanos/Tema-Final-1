# Guide

## 1- Clone this repository and build the project with the command:

### *./gradlew clean build*

## 2- Enter the project through the terminal and build the docker image:

### *docker build -t rxnetty .*

## 3- Run the container on port 8081:

### *docker run -d -p 8081:8081 rxnetty*

## 4- Access the calculator through the URL http://localhost:8081

## 5- Pass the parameters "numbers" (as many numbers as you want separated by commas) and "operation" (the type of operation) as follows:

### *?numbers=2,3,4.5,9.2&operation=mult*

## Possible operations are "sum", "sub", "mult", "div" and "pow", which are respectively addition, subtraction, multiplication, division and exponentiation.

### *The full URL then looks like this:*

### *http://localhost:8081?numbers=2,3,4.5,9.2&operation=mult*

### In this case, the numbers 2, 3, 4.5 and 9.2 will be multiplied.


