# Chọn base image Java 17 nhẹ
FROM eclipse-temurin:17-jdk-alpine

# Thư mục tạm
VOLUME /tmp

# Copy file JAR vào container
COPY target/FetchYoDo-1.0.0.jar app.jar

# Lệnh chạy ứng dụng
ENTRYPOINT ["java","-jar","/app.jar"]
