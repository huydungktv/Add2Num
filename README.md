# Add2Num API

REST API dùng Java 21 và Spring Boot để cộng hai số nguyên không âm rất lớn được biểu diễn dưới dạng chuỗi. API không sử dụng `BigInteger`, vì vậy không bị giới hạn bởi phạm vi của các kiểu số nguyên Java.
## Yêu cầu

- JDK 21
- Maven 3.9+
## Cài đặt và chạy

Build và chạy test:
```powershell
mvn clean verify
```
Đặt API key cho phiên PowerShell hiện tại:

```powershell
$env:ADD2NUM_API_KEY = "1234567890"
Khởi động API:

```powershell
mvn spring-boot:run
API mặc định chạy tại `http://localhost:8080`.

> Nếu cấu hình API key trong Windows User Environment, hãy mở terminal mới hoặc khởi động lại VS Code trước khi chạy ứng dụng. Kiểm tra bằng `$env:ADD2NUM_API_KEY`.
## Authentication

Endpoint API yêu cầu API key qua HTTP Bearer Authorization:
```http
Authorization: Bearer <api-key>
```
API key được đọc từ biến môi trường `ADD2NUM_API_KEY`. Không commit API key vào source code hoặc truyền API key qua query string.
## API endpoint

### Cộng hai số
```http
POST /api/v1/additions
Content-Type: application/json
Authorization: Bearer <api-key>
```
Request body:

```json
{
	"firstNumber": "999999999999999999999999999999",
	"secondNumber": "1"
}
```
Response `200 OK`:

```json
{
	"result": "1000000000000000000000000000000"
}
```
### Gọi bằng PowerShell

```powershell
$headers = @{
	Authorization = "Bearer 1234567890"
}

$body = @{
	firstNumber = "999999999999999999999999999999"
	secondNumber = "1"
} | ConvertTo-Json

Invoke-RestMethod `
	-Method Post `
	-Uri "http://localhost:8080/api/v1/additions" `
	-Headers $headers `
	-ContentType "application/json" `
	-Body $body
```
### Gọi bằng cURL

```bash
curl -X POST "http://localhost:8080/api/v1/additions" \
	-H "Authorization: Bearer 1234567890" \
	-H "Content-Type: application/json" \
	-d '{"firstNumber":"999999999999999999999999999999","secondNumber":"1"}'
```
## Swagger và OpenAPI

Sau khi ứng dụng khởi động:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- OpenAPI YAML: `http://localhost:8080/v3/api-docs.yaml`
- Health check: `http://localhost:8080/actuator/health`
Trong Swagger UI:

1. Chọn **Authorize**.
2. Nhập API key, ví dụ `1234567890`.
3. Chọn **Authorize** rồi đóng hộp thoại.
4. Thực hiện request trong endpoint `POST /api/v1/additions`.
Swagger tự gửi header:

```http
Authorization: Bearer 1234567890
```
Chỉ nhập giá trị API key trong hộp thoại Swagger, không nhập thêm tiền tố `Bearer` nếu giao diện đã tự thêm tiền tố này.
## Validation và giới hạn

- `firstNumber` và `secondNumber` là bắt buộc.
- Mỗi input phải có ít nhất một ký tự.
- Chỉ chấp nhận các ký tự từ `0` đến `9`.
- Không hỗ trợ số âm.
- Mặc định mỗi số tối đa `100000` ký tự.
- Có thể thay đổi giới hạn bằng biến môi trường `MAX_NUMBER_LENGTH`.
Ví dụ:

```powershell
$env:MAX_NUMBER_LENGTH = "200000"
```
## Response lỗi

API trả lỗi theo format Problem Details:

```json
{
	"type": "https://api.add2num.example/problems/unauthorized",
	"title": "Unauthorized",
	"status": 401,
	"detail": "Missing or invalid API key"
}
```
Các trường hợp phổ biến:

| HTTP status | Ý nghĩa |
| --- | --- |
| `400 Bad Request` | Request body hoặc số không hợp lệ |
| `401 Unauthorized` | Thiếu hoặc sai API key |
| `404 Not Found` | Endpoint không tồn tại |
| `500 Internal Server Error` | Lỗi ngoài dự kiến |
## Cấu hình

Cấu hình mặc định nằm trong `src/main/resources/application.yml`:

| Biến môi trường | Mặc định | Mục đích |
| --- | --- | --- |
| `SERVER_PORT` | `8080` | Port HTTP của API |
| `ADD2NUM_API_KEY` | Rỗng | API key dùng cho Bearer authentication |
| `MAX_NUMBER_LENGTH` | `100000` | Độ dài tối đa của mỗi số |
Trong production, bắt buộc cấu hình `ADD2NUM_API_KEY` qua secret manager hoặc environment của nền tảng triển khai. Không đặt secret trực tiếp trong `application.yml`.
## Đóng gói và chạy JAR

```powershell
mvn clean package
$env:ADD2NUM_API_KEY = "1234567890"
java -jar target\big-number-adder-0.0.1.jar
```
## Kiểm thử

Chạy toàn bộ unit test và integration test:

```powershell
mvn clean verify
```
Test bao gồm:

- Thuật toán cộng số rất lớn.
- Request có Bearer API key hợp lệ.
- Request thiếu hoặc sai API key.
- Input không hợp lệ.
- Input vượt giới hạn độ dài.
- OpenAPI document.
## Thuật toán lõi

Thuật toán xử lý hai chuỗi từ phải sang trái, cộng từng chữ số cùng số nhớ, sau đó đảo ngược kết quả. Lớp xử lý lõi vẫn được giữ độc lập phía sau service để bảo toàn logic cộng số hiện tại.
# Add2Num API

REST API Java 21 cài đặt phép cộng hai số nguyên rất lớn được biểu diễn dưới dạng chuỗi.

## Yêu cầu

- JDK 21
- Maven 3.9+

## Build, test và chạy API

```powershell
mvn clean test
mvn clean package

$env:ADD2NUM_API_KEY = "local-development-key"
mvn spring-boot:run
```

API chạy tại `http://localhost:8080`.

## Authentication

API sử dụng API key giai đoạn 1 qua Bearer Authorization. Không commit key vào source code.

```http
Authorization: Bearer <api-key>
```

## Sử dụng API

```powershell
$headers = @{
		Authorization = "Bearer local-development-key"
		"Content-Type" = "application/json"
}

$body = '{"firstNumber":"999999999999999999999999999999","secondNumber":"1"}'

Invoke-RestMethod -Method Post `
		-Uri "http://localhost:8080/api/v1/additions" `
		-Headers $headers `
		-Body $body
```

Response:

```json
{
	"result": "1000000000000000000000000000000"
}
```

## Swagger và OpenAPI

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Health check: `http://localhost:8080/actuator/health`

Trong Swagger UI, chọn **Authorize** và nhập API key. Swagger sẽ gửi header `Authorization: Bearer <api-key>`.

## Validation và lỗi

- Input phải chứa ít nhất một chữ số.
- Chỉ hỗ trợ chữ số từ `0` đến `9`.
- Mặc định mỗi số tối đa `100000` ký tự; cấu hình qua `MAX_NUMBER_LENGTH`.
- Request thiếu hoặc sai API key trả `401 Unauthorized`.
- Input không hợp lệ trả `400 Bad Request`.

## Sử dụng thư viện lõi

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
