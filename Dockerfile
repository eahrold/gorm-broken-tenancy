FROM openjdk:14-alpine
COPY build/libs/tenancy-*-all.jar tenancy.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "tenancy.jar"]