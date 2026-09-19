# Big Number Adder Task 1

Project Java 21 cài đặt phép cộng hai số nguyên rất lớn được biểu diễn dưới dạng chuỗi.

## Yêu cầu

- JDK 21
- Maven 3.9+

## Build và test

```powershell
mvn clean test
mvn clean package
```

## Sử dụng

```java
MyBigNumber myBigNumber = new MyBigNumber();
String result = myBigNumber.sum("1234", "897");
// result = "2131"
```

## Thuật toán

Hàm xử lý hai chuỗi từ phải sang trái, cộng từng chữ số và số nhớ, sau đó đảo ngược kết quả. Không sử dụng `BigInteger`.

## Giả định dữ liệu

- Mỗi đầu vào phải có ít nhất một ký tự.
- Đầu vào chỉ chứa các chữ số từ `0` đến `9`.
- Số âm không được hỗ trợ.
- Đầu vào `null` hoặc không hợp lệ sẽ gây ra `IllegalArgumentException`.
