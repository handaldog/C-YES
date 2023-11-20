FROM openjdk:11-jdk

ARG JAR_FILE=./build/libs/*.jar

COPY $JAR_FILE app.jar

# 서울 시간대로 설정
ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-Duser.timezone=$TZ", "-jar", "/app.jar"]
