# Lab 1.1 - Dựng khung repository mẫu

## 1. Tổng quan và mục tiêu

Lab này hướng dẫn thiết lập cấu trúc GitHub repository chuyên nghiệp, sẵn sàng cho quy trình phát triển có AI hỗ trợ. Repository có cấu trúc rõ ràng giúp GitHub Copilot và các AI agent hiểu đúng ngữ cảnh, đồng thời giúp thành viên biết chính xác nơi viết issue, pull request, tài liệu, mã nguồn và kiểm thử.

### Đối tượng

- Lập trình viên phần mềm
- Tech Lead
- Kỹ sư DevOps

### Điều kiện tiên quyết

- Có một GitHub repository rỗng hoặc repository hiện tại được phép bổ sung cấu trúc
- Đã bật GitHub Copilot
- Đã cài Git
- Với repository này: JDK 21 và Maven 3.9 trở lên

### Definition of Done

Một thành viên mới có thể tạo Issue hoặc Pull Request mà không cần hỏi: “Tôi phải viết ở đâu và theo mẫu nào?”

## 2. Phân tích yêu cầu

| Nhóm yêu cầu | Kết quả cần có |
| --- | --- |
| Cấu trúc thư mục | `.github`, `docs`, `src`, `tests`, `scripts` và các tệp hướng dẫn |
| README | Mục tiêu dự án, công nghệ, điều kiện chạy, quy tắc governance |
| CONTRIBUTING | Quy tắc nhánh, PR, commit và kiểm soát đầu ra AI |
| Issue template | Mẫu Feature Request, Bug Report và Spike Investigation |
| PR template | Liên kết đặc tả, kế hoạch kiểm thử, khai báo AI và checklist bảo mật |
| Git workflow | Nhánh riêng, commit có quy ước, push và mở PR |
| Xác minh | Template tự động xuất hiện khi tạo Issue/PR và cấu trúc dễ tìm |

### Điều chỉnh cho repository hiện tại

Repository hiện tại là dự án Java/Maven, đã có:

- `src/main/java/` cho mã nguồn chính
- `src/test/java/` cho unit test
- `pom.xml` để quản lý build và dependency

Vì vậy, không nên chuyển hoặc đổi tên `src/test/` thành `tests/`. Thư mục `tests/unit` và `tests/integration` trong layout tổng quát chỉ nên tạo nếu nhóm thực sự cần test ngoài quy ước Maven, chẳng hạn test black-box hoặc test phục vụ script. Unit test Java tiếp tục đặt trong `src/test/java/`.

## 3. Cấu trúc thư mục mục tiêu

```text
repo/
├── .github/
│   ├── ISSUE_TEMPLATE/
│   │   ├── feature_request.md
│   │   ├── bug_report.md
│   │   └── spike_investigation.md
│   ├── PULL_REQUEST_TEMPLATE.md
│   └── workflows/
├── docs/
│   ├── domain-model.md
│   ├── api-spec.md
│   └── coding-rules.md
├── src/
│   ├── main/java/
│   └── test/java/
├── scripts/
├── README.md
├── CONTRIBUTING.md
└── pom.xml
```

Các thư mục rỗng như `scripts/`, `.github/workflows/` hoặc `docs/` cần có tệp placeholder nếu Git phải theo dõi chúng. Git không theo dõi thư mục rỗng.

## 4. Các bước thực hiện trên Windows PowerShell

Chạy từ thư mục gốc repository:

```powershell
New-Item -ItemType Directory -Force `
  .github/ISSUE_TEMPLATE, .github/workflows, docs, scripts | Out-Null

New-Item -ItemType File -Force `
  .github/PULL_REQUEST_TEMPLATE.md, `
  docs/domain-model.md, docs/api-spec.md, docs/coding-rules.md | Out-Null
```

Nếu cần áp dụng layout tổng quát đầy đủ, tạo thêm các thư mục sau. Với dự án Maven hiện tại, đây là thư mục bổ sung, không thay thế `src/test/`:

```powershell
New-Item -ItemType Directory -Force tests/unit, tests/integration | Out-Null
```

## 5. Nội dung README.md cần có

README là điểm vào của repository và nên bao gồm:

1. **Mục đích dự án:** mô tả đây là nền tảng hoặc Core Service nào.
2. **Công nghệ và điều kiện tiên quyết:** ngôn ngữ, runtime, framework, database và công cụ.
3. **Cách bắt đầu:** clone repository, cài dependency, build và chạy.
4. **Governance:** mọi feature phải bắt đầu từ Issue; code do AI tạo phải qua test và review của con người.

Với repository này, lệnh xác minh chính là:

```powershell
mvn clean test
mvn clean package
```

Không đưa đồng thời các lệnh của Python, Node.js, .NET và Java vào phần “cách chạy” nếu dự án chỉ dùng Java. Các ví dụ đa stack nên được ghi là lựa chọn tham khảo, tránh khiến người mới chạy sai lệnh.

## 6. Nội dung CONTRIBUTING.md cần có

### Quy ước tên nhánh

- Feature: `feature/WO-<issue_id>-<mo-ta-ngan>`
- Bug fix: `fix/WO-<issue_id>-<mo-ta-ngan>`
- Nghiên cứu: `spike/WO-<issue_id>-<mo-ta-ngan>`

### Quy tắc Pull Request

1. Không push trực tiếp vào `main` hoặc `develop`.
2. Mỗi PR phải liên kết với một GitHub Issue đang hoạt động.
3. CI phải pass đầy đủ: test, linter và secret scanner nếu repository có cấu hình.
4. Cần ít nhất một Tech Lead hoặc peer reviewer là con người phê duyệt.

### Chính sách đầu ra AI

- Mọi code do Copilot hoặc công cụ AI đề xuất đều là **không đáng tin cậy cho đến khi được con người xác minh**.
- Kiểm tra package import, API nội bộ, model miền và logic nghiệp vụ; không mặc định rằng nội dung AI sinh ra là tồn tại hoặc đúng.
- Không đưa production key, thông tin đăng nhập database, dữ liệu khách hàng hoặc bí mật độc quyền vào prompt hay repository.

## 7. Các Issue Template

Tạo ba tệp trong `.github/ISSUE_TEMPLATE/`. Mỗi tệp bắt đầu bằng YAML front matter.

### Feature Request

```markdown
---
name: Feature Request
about: Đề xuất tính năng mới hoặc cải tiến kiến trúc
title: "[FEATURE] "
labels: enhancement
assignees: ""
---

### 1. Vấn đề và giá trị người dùng
Mô tả nhu cầu hoặc vấn đề cần giải quyết.

### 2. Phạm vi và yêu cầu kỹ thuật
- [ ] Yêu cầu 1
- [ ] Yêu cầu 2

### 3. Ngoài phạm vi
Nêu rõ những nội dung không thực hiện trong issue này.

### 4. Tiêu chí nghiệm thu
- [ ] Given X, when Y, then Z.
- [ ] Độ bao phủ unit test >= 80%.
```

### Bug Report

```markdown
---
name: Bug Report
about: Báo cáo lỗi hoặc hành vi ngoài dự kiến
title: "[BUG] "
labels: bug
assignees: ""
---

### 1. Mô tả lỗi

### 2. Các bước tái hiện
1. Đi tới `...`
2. Thực hiện `...`
3. Quan sát lỗi

### 3. Hành vi mong đợi và thực tế
- **Mong đợi:** Hệ thống trả về HTTP 200.
- **Thực tế:** Hệ thống trả về HTTP 500 hoặc lỗi tương ứng.

### 4. Ghi chú hoặc liên kết RCA
```

### Spike Investigation

```markdown
---
name: Spike Investigation
about: Nghiên cứu có giới hạn thời gian hoặc đánh giá tính khả thi
title: "[SPIKE] "
labels: spike
assignees: ""
---

### 1. Mục tiêu
Câu hỏi kỹ thuật hoặc kiến trúc cần trả lời là gì?

### 2. Giới hạn thời gian
- [ ] Tối đa 4 giờ
- [ ] Tối đa 8 giờ

### 3. Sản phẩm bàn giao
- [ ] ADR hoặc bản tóm tắt trong `/docs`
```

## 8. Pull Request Template

Tệp `.github/PULL_REQUEST_TEMPLATE.md` phải yêu cầu người mở PR cung cấp:

- Liên kết Issue và tài liệu đặc tả
- Tóm tắt thay đổi
- Bằng chứng test, gồm log integration test nếu có
- Khai báo có hoặc không sử dụng AI
- Xác nhận con người đã đọc, kiểm tra và test logic AI sinh ra
- Checklist không chứa secret, có input validation và đã cập nhật tài liệu
- Xác nhận branch đã cập nhật với `main` hoặc `develop`

Mẫu checklist tối thiểu:

```markdown
## 1. Liên kết đặc tả
Closes #<Issue_Number> | Spec: [link]

## 2. Kế hoạch kiểm thử
- [ ] Unit test đã được thêm và pass
- [ ] Đã đính kèm log integration test nếu áp dụng

## 3. Khai báo sử dụng AI
- [ ] Không sử dụng AI
- [ ] Có sử dụng AI để hỗ trợ
- [ ] Con người đã đọc, xác minh và test toàn bộ logic AI sinh ra

## 4. Bảo mật và trước khi merge
- [ ] Không có secret, API key hoặc credential hardcode
- [ ] Đã kiểm tra input validation
- [ ] Đã cập nhật tài liệu
```

## 9. Tạo branch và mở Pull Request

```powershell
git switch -c feature/WO-101-scaffold
git add .
git commit -m "feat(scaffold): initialize AI-native repo structure and github templates"
git push -u origin feature/WO-101-scaffold
```

Sau khi push, mở Pull Request trên GitHub. Nội dung PR sẽ tự động lấy từ `.github/PULL_REQUEST_TEMPLATE.md`.

Trước khi commit, kiểm tra thay đổi:

```powershell
git status
git diff --check
```

## 10. Checklist xác minh và Definition of Done

- [ ] Có các thư mục `.github`, `docs`, `src` và `scripts`.
- [ ] Cấu trúc test Maven hiện tại `src/test/java/` vẫn được giữ nguyên.
- [ ] Có ba Issue Template: Feature, Bug và Spike.
- [ ] Khi chọn **New Issue**, các template hiển thị tự động.
- [ ] Có `.github/PULL_REQUEST_TEMPLATE.md`.
- [ ] Khi mở PR từ branch scaffold, template được điền tự động.
- [ ] README mô tả đúng Java/Maven và lệnh build thực tế.
- [ ] CONTRIBUTING quy định branch, PR và human-in-the-loop AI safety.
- [ ] `mvn clean test` chạy thành công.
- [ ] Không có secret hoặc credential trong các tệp mới.
- [ ] Một thành viên mới có thể tạo Issue hoặc PR mà không cần hỏi quy trình.

## 11. Kết luận

Lab hoàn tất khi repository có cấu trúc nhất quán, tài liệu vào dự án rõ ràng, các template GitHub hoạt động tự động và mọi thay đổi đều đi qua Issue, branch, kiểm thử và review của con người. Đây là nền tảng để Copilot hỗ trợ phát triển mà vẫn giữ được khả năng kiểm soát và truy vết của nhóm.
