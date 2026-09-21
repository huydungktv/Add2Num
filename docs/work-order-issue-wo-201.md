# Issue WO-201: Create Work Order Domain Capabilities

## 1. Mục tiêu nghiệp vụ và phạm vi

- **Vấn đề:** Quản lý vận hành cần tạo work order cho kỹ thuật viên dịch vụ hiện trường.
- **Trong phạm vi:** Luồng tạo CRUD cơ bản, validation phía client và khởi tạo trạng thái `DRAFT`.
- **Ngoài phạm vi:** Thuật toán điều phối và đồng bộ với vendor bên thứ ba.

## 2. Phân rã kiến trúc

- **UI:** `title` bắt buộc, `description`, `priority` (`LOW`, `MED`, `HIGH`) và `customer_id`; title tối thiểu 5 ký tự, priority mặc định `MED`.
- **Data:** Bảng `work_orders` với UUID primary key, title, priority, status, customer ID và timestamp tạo.
- **API:** `POST /api/v1/work-orders`, OAuth2 Bearer Token với scope `workorders:write`, trả `201`, `400` hoặc `422`.

## 3. Tiêu chí nghiệm thu

- [ ] Given input hợp lệ, when post API, then lưu status `DRAFT` và trả HTTP 201.
- [ ] Given thiếu title, then trả HTTP 422 với chi tiết `title field required`.

## 4. Definition of Ready

- [x] UI/Data/API đã được phân tách.
- [x] Non-goals đã được khai báo.
- [x] Phân loại PII và yêu cầu bảo mật đã được nêu.
- [x] File đặc tả và phạm vi thư mục đã được xác định.
- [x] Tiêu chí nghiệm thu có thể kiểm thử.
