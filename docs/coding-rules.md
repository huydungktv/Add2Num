# Coding Rules

- Dùng Java 21 và giữ package theo cấu trúc Maven hiện tại.
- Unit test đặt trong `src/test/java/`.
- Không dùng `BigInteger` để thay thế thuật toán cốt lõi.
- Tên class, method và biến phải mô tả rõ ý nghĩa.
- Mọi thay đổi hành vi phải có test tương ứng.
- Chạy `mvn clean test` trước khi mở Pull Request.
- Không commit secret, credential hoặc dữ liệu nhạy cảm.
