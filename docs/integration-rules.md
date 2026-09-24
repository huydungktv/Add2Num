# Quy tắc tích hợp Add2Num API

Tài liệu này mô tả cách ứng dụng client tích hợp với API cộng hai số nguyên không âm có độ dài lớn.

## 1. Thông tin kết nối

| Thuộc tính | Giá trị mặc định |
| --- | --- |
| Base URL | `http://localhost:8080` |
| API endpoint chuẩn | `POST /api/v1/additions` |
| Content-Type | `application/json` |
| Xác thực | Bearer API key |

Trong môi trường production, thay Base URL bằng địa chỉ triển khai thực tế và luôn sử dụng HTTPS.

## 2. Xác thực

Mọi request tới endpoint cộng số phải gửi API key trong HTTP header:

```http
Authorization: Bearer <api-key>
```

API key được cấu hình phía server qua biến môi trường `ADD2NUM_API_KEY`. Client không được:

- Đặt API key trong query string hoặc request body.
- Commit API key vào source code.
- Ghi API key vào log hoặc gửi API key qua kết nối HTTP không mã hóa trong production.

Nếu thiếu API key hoặc API key không hợp lệ, API trả về `401 Unauthorized`.

## 3. Gọi endpoint chuẩn

### Request

```http
POST /api/v1/additions HTTP/1.1
Host: localhost:8080
Authorization: Bearer 1234567890
Content-Type: application/json

{
	"firstNumber": "999999999999999999999999999999",
	"secondNumber": "1"
}
```

`firstNumber` và `secondNumber` phải là chuỗi chỉ chứa các chữ số từ `0` đến `9`. Dùng chuỗi thay vì JSON number để không mất độ chính xác với số rất lớn.

### Response thành công

HTTP status: `200 OK`

```json
{
	"result": "1000000000000000000000000000000"
}
```

Trường `result` luôn là chuỗi và có thể dài hơn giới hạn của `long`, `double` hoặc kiểu số JavaScript thông thường.

## 4. Ví dụ tích hợp

### cURL

```bash
curl -X POST "http://localhost:8080/api/v1/additions" \
	-H "Authorization: Bearer 1234567890" \
	-H "Content-Type: application/json" \
	-d '{"firstNumber":"999999999999999999999999999999","secondNumber":"1"}'
```

### PowerShell

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

### JavaScript

```javascript
const response = await fetch("http://localhost:8080/api/v1/additions", {
	method: "POST",
	headers: {
		"Authorization": "Bearer 1234567890",
		"Content-Type": "application/json"
	},
	body: JSON.stringify({
		firstNumber: "999999999999999999999999999999",
		secondNumber: "1"
	})
});

const data = await response.json();
console.log(data.result);
```

Không chuyển `result` sang JavaScript `Number`; hãy giữ giá trị ở dạng string.

## 5. Validation và giới hạn

- Hai trường `firstNumber` và `secondNumber` là bắt buộc.
- Mỗi trường phải có ít nhất một ký tự.
- Chỉ chấp nhận chữ số ASCII từ `0` đến `9`.
- Không hỗ trợ số âm, dấu thập phân hoặc dấu phân cách hàng nghìn.
- Độ dài mặc định của mỗi số tối đa là `100000` ký tự.
- Server có thể thay đổi giới hạn bằng biến môi trường `MAX_NUMBER_LENGTH`.

Ví dụ input không hợp lệ:

```json
{
	"firstNumber": "12a",
	"secondNumber": "3"
}
```

## 6. Response lỗi

Lỗi validation và lỗi request body trả về `400 Bad Request` theo dạng Problem Details:

```json
{
	"type": "https://api.add2num.example/problems/invalid-number",
	"title": "Invalid request",
	"status": 400,
	"detail": "Input must contain digits only"
}
```

Các status code chính:

| Status | Ý nghĩa | Cách xử lý phía client |
| --- | --- | --- |
| `200` | Cộng số thành công | Đọc `result` dưới dạng string |
| `400` | Request hoặc số không hợp lệ | Sửa payload, không retry nguyên request |
| `401` | Thiếu hoặc sai API key | Kiểm tra secret và header Authorization |
| `404` | Sai URL hoặc endpoint | Kiểm tra Base URL và path |
| `500` | Lỗi server ngoài dự kiến | Retry có kiểm soát và ghi nhận correlation/error id nếu có |

Khi nhận lỗi `400`, client nên hiển thị `detail` cho mục đích chẩn đoán nhưng không nên hiển thị API key hoặc thông tin bảo mật trong log.

## 7. Endpoint query parameter tương thích

Ứng dụng hiện cũng cung cấp endpoint:

```http
POST /api/v1/additions/add?firstNumber=12&secondNumber=3
```

Response:

```json
{
	"result": "15"
}
```

Endpoint này chỉ nên dùng cho client cũ hoặc tích hợp đơn giản. Endpoint JSON `POST /api/v1/additions` được khuyến nghị vì không đưa dữ liệu số vào URL và phù hợp hơn với các số rất dài.

## 8. OpenAPI và health check

Sau khi server khởi động:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- OpenAPI YAML: `http://localhost:8080/v3/api-docs.yaml`
- Health check: `http://localhost:8080/actuator/health`

Swagger UI hỗ trợ nút **Authorize**. Nhập API key theo hướng dẫn của giao diện; không thêm `Bearer` nếu Swagger đã tự thêm scheme này.

## 9. Nguyên tắc retry và timeout

- Cấu hình timeout kết nối và timeout đọc phù hợp với client.
- Chỉ retry có giới hạn cho lỗi mạng hoặc `5xx`.
- Không retry tự động với `400`, `401` hoặc `404`.
- Khi retry request `POST`, client nên dùng cùng payload và giới hạn số lần thử để tránh tạo tải không cần thiết.
