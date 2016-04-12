FROM java:8
VOLUME /tmp
ADD testapps-0.0.1.jar demoapp.jar

#this line is to get update the datetime stamp on the jar
RUN bash -c 'touch /demoapp.jar'

#ENTRYPOINT ["java","-jar", "-Dserver.port=80", "-Dspring.datasource.url=jdbc:oracle:thin:@//$oracle_db_ip", "/demoapp.jar"]
ENTRYPOINT java -jar -Dserver.port=80 -Dspring.datasource.url=jdbc:oracle:thin:@//$oracle_db_ip /demoapp.jar

EXPOSE 80