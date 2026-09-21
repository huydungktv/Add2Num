# Đặc tả kỹ thuật: Domain Work Order (WO-201)

## 1. Mục tiêu và phạm vi

Work Order cho phép quản lý vận hành tạo một yêu cầu công việc cho kỹ thuật viên dịch vụ hiện trường.

### Trong phạm vi

- Luồng tạo work order cơ bản.
- Validation phía client và server.
- Khởi tạo trạng thái `DRAFT`.
- REST contract cho thao tác tạo.

### Ngoài phạm vi

- Thuật toán điều phối.
- Đồng bộ với vendor bên thứ ba.
- Cập nhật hoặc xóa work order.

## 2. Phân rã UI

| Tên trường | Kiểu | Bắt buộc | Quy tắc / Ràng buộc |
| :--- | :--- | :--- | :--- |
| `title` | String | Có | Tối thiểu 5, tối đa 255 ký tự |
| `description` | String | Không | Tối đa 2.000 ký tự |
| `priority` | Enum | Có | Mặc định `MED`; nhận `LOW`, `MED`, `HIGH`, `CRITICAL` |
| `customer_id` | UUID | Có | Phải tham chiếu tới customer đang hoạt động |

Validation lỗi phải hiển thị theo từng trường và không đưa dữ liệu PII nhạy cảm vào log.

## 3. Schema dữ liệu PostgreSQL

```sql
CREATE TABLE work_orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority VARCHAR(20) NOT NULL DEFAULT 'MED',
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    customer_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_priority CHECK
        (priority IN ('LOW', 'MED', 'HIGH', 'CRITICAL'))
);
```

`customer_id` được phân loại là PII nội bộ và chỉ được xuất hiện trong audit log đã sanitize. Khóa và credential database không được lưu trong tài liệu hoặc source code.

## 4. REST API contract

### Tạo Work Order

**Method:** `POST`  
**Path:** `/api/v1/work-orders`  
**Header:** `Authorization: Bearer <token>`  
**Scope yêu cầu:** `workorders:write`

#### Request body

```json
{
  "title": "HVAC Repair Unit 4",
  "description": "System reporting error code E-42",
  "priority": "HIGH",
  "customer_id": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"
}
```

#### Response `201 Created`

```json
{
  "id": "c9bf9e57-1685-4c89-bafb-ff5af830be8a",
  "status": "DRAFT",
  "created_at": "2026-08-30T10:00:00Z"
}
```

#### Status code

- `201 Created`: tạo thành công.
- `400 Bad Request`: request sai cấu trúc hoặc giá trị không hợp lệ ở mức cú pháp.
- `422 Unprocessable Entity`: validation nghiệp vụ thất bại, ví dụ thiếu `title`.
- `401 Unauthorized`: thiếu hoặc sai Bearer Token.
- `403 Forbidden`: token không có scope `workorders:write`.

## 5. Tiêu chí nghiệm thu

- [ ] Given dữ liệu work order hợp lệ, when gửi lên API, then lưu record với trạng thái `DRAFT` và trả về HTTP 201.
- [ ] Given thiếu `title`, when gửi request, then trả về HTTP 422 với chi tiết `title field required`.
- [ ] Given token thiếu scope, when gửi request, then trả về HTTP 403.
- [ ] Không có credential hoặc dữ liệu PII chưa sanitize trong log.
