# Add2Num - Task 2

Ứng dụng web Spring Boot cho phép cộng hai số rất lớn, sử dụng lại thư viện `big-number-adder-0.0.1.jar` của Task 1, source code thư viện này được lưu tại branch `core`

## Công nghệ

- Java 21
- Spring Boot 3.4.5
- Spring MVC
- Thymeleaf
- Bootstrap 5
- Maven

## Chạy ứng dụng

```powershell
mvn spring-boot:run
```

Mở trình duyệt tại http://localhost:8080.

## Build

```powershell
mvn clean package
java -jar target/add2num-0.0.1.jar
```

## Thư viện Task 1

File `lib/big-number-adder-0.0.1.jar` được dùng làm dependency cục bộ. Kết quả cuối cùng được tính trực tiếp bởi `MyBigNumber.sum(String, String)`. Bảng tiến trình hiển thị các chữ số, số nhớ và tổng của từng vị trí từ phải sang trái.
