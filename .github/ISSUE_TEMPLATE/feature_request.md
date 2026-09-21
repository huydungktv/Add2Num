---
name: AI-Native Feature Request
about: Template chuẩn bắt buộc phân tách UI/Data/API và các ràng buộc AI
title: "[FEAT]: "
labels: "enhancement, pending-dor"
assignees: ""
---

## 1. Mục tiêu nghiệp vụ và phạm vi

- **Mô tả vấn đề:**
- **Trong phạm vi:**
- **Ngoài phạm vi (Non-goals):**

## 2. Phân rã kiến trúc

- **Tầng UI:** Màn hình, trường nhập, quy tắc validation và trạng thái lỗi.
- **Tầng Data:** Entity, thay đổi schema, ràng buộc và phân loại PII.
- **Tầng API:** REST endpoint, HTTP verb, payload, status code và phân quyền.

## 3. Ràng buộc thực thi Copilot AI

- **Tech stack mục tiêu:** Java 21 / Maven / JUnit 5 / PostgreSQL nếu áp dụng.
- **Phạm vi thư mục được phép:** `src/main/java/`, `src/test/java/`, `docs/`.
- **Rào chắn dependency:** Không thêm dependency bên thứ ba nếu chưa được review kiến trúc.

## 4. Tiêu chí nghiệm thu (Gherkin-lite)

- [ ] **Given** dữ liệu hợp lệ, **When** thực hiện thao tác, **Then** nhận được kết quả mong đợi.
- [ ] **Given** dữ liệu không hợp lệ, **When** thực hiện thao tác, **Then** nhận được lỗi validation rõ ràng.

## 5. Checklist Definition of Ready (DoR)

- [ ] Đã định nghĩa chặt chẽ phân tách UI/Data/API.
- [ ] Đã khai báo rõ non-goals.
- [ ] Đã nêu phân loại PII và yêu cầu bảo mật.
- [ ] Đã chỉ rõ file/thư mục mục tiêu.
- [ ] Đã xác định cách kiểm thử và tiêu chí nghiệm thu.
