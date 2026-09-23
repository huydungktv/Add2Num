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
