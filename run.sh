#!/bin/bash
mvn -f Project3Server/Project3Server/pom.xml clean compile exec:java &
mvn -f Project3Client/Project3Client/pom.xml clean compile exec:java
