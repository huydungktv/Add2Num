# Hướng dẫn đóng góp

Cảm ơn bạn đã đóng góp cho Big Number Adder. Mọi thay đổi cần có Issue, được kiểm thử và được reviewer là con người xác minh trước khi merge.

## 1. Quy ước nhánh

- Feature: `feature/WO-<issue_id>-<mo-ta-ngan>`
- Bug fix: `fix/WO-<issue_id>-<mo-ta-ngan>`
- Nghiên cứu: `spike/WO-<issue_id>-<mo-ta-ngan>`

Không push trực tiếp vào `main` hoặc `develop`.

## 2. Commit

Dùng Conventional Commits, ví dụ:

```text
feat: add validation for empty input
fix: reject non-numeric values
test: cover carry propagation
docs: update repository guide
```

## 3. Quy tắc Pull Request

1. PR phải liên kết với một GitHub Issue đang hoạt động.
2. Mô tả rõ phạm vi thay đổi và cách kiểm thử.
3. `mvn clean test` phải pass.
4. CI, linter và secret scanner phải pass nếu được cấu hình.
5. Cần ít nhất một Tech Lead hoặc peer reviewer là con người phê duyệt.
6. Cập nhật tài liệu khi thay đổi hành vi hoặc quy trình.

## 4. Chính sách đầu ra AI

- Mọi code do GitHub Copilot hoặc công cụ AI đề xuất đều là **UNTRUSTED** cho đến khi được developer đọc và xác minh.
- Kiểm tra package, API nội bộ, domain model, thuật toán và các trường hợp biên; không mặc định nội dung AI sinh ra là đúng.
- Không đưa production key, database credential, dữ liệu khách hàng hoặc thông tin độc quyền vào prompt hay repository.
- PR phải khai báo việc sử dụng AI và xác nhận human verification.

## 5. Kiểm tra cục bộ

```powershell
mvn clean test
mvn clean package
git diff --check
```
