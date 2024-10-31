FROM amazoncorretto:17

ENV TZ=Asia/Seoul

WORKDIR /app

COPY /home/ubuntu/config/.env /app/.env

COPY build/libs/moa.jar /app/moa.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/moa.jar"]