# Big Number Adder

>Dự án Java 21 minh họa phép cộng hai số nguyên rất lớn được biểu diễn dưới dạng chuỗi, không sử dụng `BigInteger`.

## Mục đích

`MyBigNumber` cộng hai chuỗi chữ số bằng cách duyệt từ phải sang trái, xử lý số nhớ và đảo ngược kết quả. Repository này cũng là mẫu scaffold cho quy trình phát triển có GitHub Copilot hỗ trợ, với Issue Template, Pull Request Template và quy tắc review rõ ràng.

## Công nghệ và điều kiện tiên quyết

- JDK 21
- Maven 3.9+
- JUnit 5
- SLF4J và Logback
- Git và GitHub Copilot (khi làm việc theo quy trình AI-assisted)

## Bắt đầu

```powershell
git clone <repository-url>
Set-Location Add2Num
mvn clean test
mvn clean package
```

## Sử dụng

```java
MyBigNumber myBigNumber = new MyBigNumber();
String result = myBigNumber.sum("1234", "897");
// result = "2131"
```

## Quy tắc dữ liệu

- Mỗi đầu vào phải có ít nhất một ký tự.
- Đầu vào chỉ chứa các chữ số từ `0` đến `9`.
- Số âm không được hỗ trợ.
- Đầu vào `null` hoặc không hợp lệ gây ra `IllegalArgumentException`.

## Cấu trúc repository

- `src/main/java/`: mã nguồn chính.
- `src/test/java/`: unit test theo chuẩn Maven.
- `docs/`: tài liệu miền, API, coding rules và hướng dẫn lab.
- `.github/ISSUE_TEMPLATE/`: mẫu Feature, Bug và Spike.
- `.github/PULL_REQUEST_TEMPLATE.md`: checklist review và khai báo sử dụng AI.
- `scripts/`: script hỗ trợ phát triển hoặc CI.

## Governance

- Mọi feature phải bắt đầu từ một GitHub Issue được phê duyệt.
- Không push trực tiếp vào `main` hoặc `develop`.
- Mọi PR phải pass test và được ít nhất một reviewer là con người phê duyệt.
- Code do AI đề xuất được xem là **chưa đáng tin cậy** cho đến khi được đọc, xác minh và kiểm thử.
- Không đưa secret, credential hoặc dữ liệu khách hàng vào prompt hay repository.

Xem [CONTRIBUTING.md](CONTRIBUTING.md) để biết đầy đủ quy tắc đóng góp và [tài liệu Lab 1.1](docs/lab-1.1-scaffold-repository-vi.md) để biết cách vận hành scaffold.
