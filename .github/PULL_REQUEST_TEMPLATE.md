## 1. Truy xuất nguồn gốc

- **Issue liên kết:** Closes #WO-201
- **Đường dẫn đặc tả:** `docs/work-order-decomposition.md`

## 2. Khai báo sử dụng AI và nguồn gốc nội dung

- [ ] **Công cụ AI đã sử dụng:** GitHub Copilot Chat / Inline Autocomplete
- [ ] **Prompt / ngữ cảnh đã cung cấp:** Đã sử dụng file đặc tả làm ngữ cảnh.
- [ ] **Tóm tắt file đã chỉnh sửa:**
  - File được sinh: `docs/work-order-decomposition.md`
  - File viết thủ công: `.copilotignore`, `.github/`

## 3. Xác minh và bằng chứng

- [ ] Unit test đã được thêm / cập nhật nếu thay đổi runtime.
- [ ] Linter và static analysis pass, không có warning.
- [ ] Đã hoàn tất review đặc tả theo hướng dẫn domain.
- [ ] Đã xác nhận PR không chứa thay đổi ứng dụng ngoài phạm vi.

## 4. Checklist rủi ro và bảo mật

- [ ] Quét secret: Không có credential hoặc API key hardcode trong code hay log prompt.
- [ ] Kiểm tra OWASP: Đã sanitize input parameter và xác minh authorization.
- [ ] Kế hoạch rollback: PR chỉ chứa tài liệu/cấu hình, không ảnh hưởng runtime.

## 5. Trước khi merge

- [ ] Branch đã cập nhật với `main`/`core`.
- [ ] Tài liệu đã được cập nhật.
- [ ] Reviewer là con người đã phê duyệt.
