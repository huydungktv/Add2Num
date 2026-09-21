# HƯỚNG DẪN THỰC HÀNH - UNIT 1.2

## Lab 1.2 - Hạ tầng cộng tác thực hành

## Tổng quan và mục tiêu

Chuyển từ việc dựng khung repository sang một quy trình tiếp nhận công việc có thể vận hành và lặp lại. Trong lab này, bạn sẽ soạn các template Issue và PR chặt chẽ với checklist governance dành cho AI, áp dụng chính sách loại trừ nội dung của GitHub Copilot, phân rã đầy đủ ticket `WO-201` theo các ranh giới UI/Data/API, sử dụng Copilot để phác thảo tài liệu domain và mở một Pull Request chỉ chứa tài liệu.

### Đối tượng

- Lập trình viên phần mềm
- Technical Lead
- Kỹ sư Developer Experience (DevEx)

### Điều kiện tiên quyết

Đã hoàn thành Lab 1.1 (hoặc đã khởi tạo sample repository) và đã bật GitHub Copilot.

### Definition of Done

Một Pull Request được mở, chỉ chứa tài liệu đặc tả và template, đồng thời đáp ứng Definition of Ready (DoR).

# Phần 1: Lý thuyết và khái niệm cốt lõi

| Khái niệm / Nguyên tắc | Quy tắc thực hành cho AI SDLC |
| --- | --- |
| Từ cấu trúc đến quy trình | Template hoạt động như policy có thể thực thi: nếu một mục không có trong checklist, AI và con người sẽ bỏ qua mục đó. |
| Phân rã yêu cầu | Với mỗi feature, phải tách rõ UI / Data / API. Không để Copilot tự tạo thêm tầng thứ tư hoặc cơ sở dữ liệu bổ sung. |
| Thiết kế Issue và PR Template | Bắt buộc khai báo phạm vi / ngoài phạm vi, tiêu chí có thể kiểm thử theo Gherkin-lite, ràng buộc tech stack cho AI, khai báo prompt và kiểm tra rủi ro OWASP. |
| Cấu hình Copilot | Áp dụng loại trừ nội dung (`.copilotignore`), workspace trust và kỷ luật sinh code dựa trên comment. |
| Definition of Ready (DoR) | Một ticket CHỈ được xem là Ready khi đã nêu rõ phân tách UI/Data/API, tiêu chí có thể kiểm thử, non-goals, bảo mật và các file mục tiêu. |

# Phần 2: Bài tập thực hành

## Bước 1: Khởi tạo / xác minh sample repository

Đảm bảo workspace cục bộ sạch và đang ở một branch riêng trước khi cấu hình hạ tầng cộng tác.

```bash
# Xác minh trạng thái branch và mức độ sẵn sàng của workspace
git checkout main
git pull origin main
git checkout -b feature/WO-201-infrastructure-setup
```

## Bước 2: Soạn Issue và PR Template với checklist AI

Cập nhật `.github/ISSUE_TEMPLATE/feature_request.md` để bắt buộc phân rã yêu cầu và nêu các ràng buộc AI.

```markdown
---
name: AI-Native Feature Request
about: Template chuẩn bắt buộc phân tách UI/Data/API và các ràng buộc AI
title: '[FEAT]: '
labels: 'enhancement, pending-dor'
---

### 1. Mục tiêu nghiệp vụ và phạm vi

- **Mô tả vấn đề:**
- **Trong phạm vi:**
- **Ngoài phạm vi (Non-goals):**

### 2. Phân rã kiến trúc

- **Tầng UI:** (Màn hình, trường nhập, quy tắc validation, trạng thái lỗi)

- **Tầng Data:** (Entity, thay đổi schema, ràng buộc, phân loại PII)

- **Tầng API:** (REST endpoint, HTTP verb, đặc tả payload, status code, phân quyền)

### 3. Ràng buộc thực thi Copilot AI

- **Tech stack mục tiêu:** Python 3.11 / FastAPI / Pydantic v2 / PostgreSQL

- **Phạm vi thư mục được phép:** `src/modules/work_orders/`, `tests/unit/`

- **Rào chắn dependency:** KHÔNG thêm dependency bên thứ ba nếu chưa được review kiến trúc.

### 4. Tiêu chí nghiệm thu (Gherkin-lite)

- [ ] **Given** người dùng đã xác thực, **When** gửi payload WO hợp lệ, **Then** trả về HTTP 201 Created.

- [ ] **Given** dữ liệu PII không hợp lệ, **When** gửi request, **Then** trả về HTTP 422 kèm lỗi validation theo từng trường.

### 5. Checklist Definition of Ready (DoR)

- [ ] Đã định nghĩa chặt chẽ phân tách UI/Data/API.

- [ ] Đã khai báo rõ non-goals.

- [ ] Đã nêu phân loại PII và bảo mật.
```

Tiếp theo, cập nhật `.github/PULL_REQUEST_TEMPLATE.md` để bổ sung khai báo prompt, các bước xác minh và checklist bảo mật.

```markdown
## 1. Truy xuất nguồn gốc

- **Issue liên kết:** Closes #WO-201

- **Đường dẫn đặc tả:** `docs/work-order-decomposition.md`

## 2. Khai báo sử dụng AI và nguồn gốc nội dung

- [ ] **Công cụ AI đã sử dụng:** GitHub Copilot Chat / Inline Autocomplete

- [ ] **Prompt / ngữ cảnh đã cung cấp:** Đã sử dụng file đặc tả
  `docs/work-order-decomposition.md` làm ngữ cảnh.

- [ ] **Tóm tắt file đã chỉnh sửa:**

  - File được sinh:
  `docs/work-order-decomposition.md`

  - File viết thủ công:
  `.github/copilot-ignore`

## 3. Xác minh và bằng chứng

- [ ] Đã thêm / cập nhật unit test.

- [ ] Linter và static analysis pass, không có warning.

- [ ] Đã hoàn tất review đặc tả theo hướng dẫn domain.

## 4. Checklist rủi ro và bảo mật

- [ ] Quét secret: Không có credential hoặc API key hardcode trong code hay log prompt.

- [ ] Kiểm tra OWASP: Đã sanitize input parameter và xác minh kiểm tra authorization.

- [ ] Kế hoạch rollback: PR chỉ chứa tài liệu, không ảnh hưởng runtime.
```

## Bước 3: Tạo Issue WO-201 (Tạo Work Order)

Mô phỏng quy trình tiếp nhận backlog trong thực tế bằng cách tạo Issue **WO-201** với đầy đủ phân rã UI/Data/API theo Definition of Ready (DoR).

### Tiêu đề

`[FEAT]: WO-201 Create Work Order Domain Capabilities`

### 1. Mục tiêu nghiệp vụ và phạm vi

- **Vấn đề:** Các quản lý vận hành cần tạo work order cho kỹ thuật viên dịch vụ hiện trường.

- **Trong phạm vi:** Luồng tạo CRUD cơ bản, validation phía client và khởi tạo trạng thái (`DRAFT`).

- **Ngoài phạm vi:** Thuật toán điều phối và đồng bộ với vendor bên thứ ba.

### 2. Phân rã kiến trúc

- **Tầng UI:**

  - Trường biểu mẫu: `title` (text, bắt buộc), `description` (text), `priority` (enum: LOW, MED, HIGH), `customer_id` (UUID).

  - Validation: Title tối thiểu 5 ký tự; priority mặc định là `MED`.

- **Tầng Data:**

  - Bảng: `work_orders` (`id` UUID PRIMARY KEY, `title` VARCHAR(255), `priority` VARCHAR(20), `status` VARCHAR(20), `customer_id` UUID, `created_at` TIMESTAMPTZ).

  - Phân loại PII: Customer ID được xem là PII nội bộ; cần ghi log audit đã sanitize.

- **Tầng API:**

  - Endpoint: `POST /api/v1/work-orders`

  - Xác thực: OAuth2 Bearer Token (`scope: workorders:write`)

  - Status code: `201 Created`, `400 Bad Request`, `422 Unprocessable Entity`

### 3. Tiêu chí nghiệm thu

- [ ] Given dữ liệu work order hợp lệ, when gửi lên API, then lưu record với trạng thái `DRAFT` và trả về HTTP 201.

- [ ] Given thiếu trường title, trả về HTTP 422 với chi tiết `title field required`.

## Bước 4: Cấu hình loại trừ nội dung Copilot

Cấu hình loại trừ nội dung để bảo đảm credential môi trường nhạy cảm, keystore và dữ liệu dump độc quyền của vendor không được đưa vào context prompt của Copilot.

```bash
# Tạo .copilotignore cục bộ ở thư mục gốc repository
touch .copilotignore

# Thêm các đường dẫn nhạy cảm vào .copilotignore
echo ".env*" >> .copilotignore
echo "secrets/" >> .copilotignore
echo "*.pem" >> .copilotignore
echo "vendor/proprietary/" >> .copilotignore

# Đồng thời áp dụng trong .gitignore
echo ".env" >> .gitignore
echo "bin/" >> .gitignore
echo "node_modules/" >> .gitignore
```

## Bước 5: Dùng Copilot để phác thảo `docs/work-order-decomposition.md` và con người chỉnh sửa

Thực hành sinh nội dung dựa trên comment trong Copilot Chat hoặc inline prompt để tạo đặc tả domain, sau đó bắt buộc con người xác minh.

```markdown
<!--
Hướng dẫn cho Copilot Chat / Prompt:

"Hãy tạo tài liệu thiết kế kỹ thuật dạng Markdown cho
 docs/work-order-decomposition.md dựa trên Issue WO-201. Bao gồm các bảng
 validation UI, PostgreSQL DDL schema và REST API payload contract."
-->

# Đặc tả kỹ thuật: Domain Work Order (WO-201)

## 1. Ma trận validation UI

| Tên trường | Kiểu | Bắt buộc | Quy tắc / Ràng buộc |
| :--- | :--- | :--- | :--- |
| `title` | String | Có | Tối thiểu 5 ký tự, tối đa 255 ký tự |
| `description` | String | Không | Tối đa 2.000 ký tự |
| `priority` | Enum | Có | Mặc định: `MED` (`LOW`, `MED`, `HIGH`, `CRITICAL`) |
| `customer_id` | UUID | Có | Phải tham chiếu tới Customer đang hoạt động |

## 2. Schema dữ liệu (PostgreSQL)

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

## 3. Contract REST API

**Request Header:** `Authorization: Bearer <token>`  

**POST** `/api/v1/work-orders`

**Request Body:**

```json
{
  "title": "HVAC Repair Unit 4",
  "description": "System reporting error code E-42",
  "priority": "HIGH",
  "customer_id": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"
}
```

**Response Body (HTTP 201 Created):**

```json
{
  "id": "c9bf9e57-1685-4c89-bafb-ff5af830be8a",
  "status": "DRAFT",
  "created_at": "2026-08-30T10:00:00Z"
}
```
```

## Bước 6: Mở Pull Request chỉ chứa tài liệu

Commit các template đã cập nhật, cấu hình loại trừ và tài liệu đặc tả. Tạo Pull Request, xác nhận không đưa code runtime nào vào thay đổi.

```bash
# Chỉ stage các file tài liệu và cấu hình
git add .github/ .copilotignore .gitignore docs/work-order-decomposition.md

# Kiểm tra các file đã stage (Đảm bảo KHÔNG stage code ứng dụng trong src/ hoặc bin/)
git status

# Commit với scope có cấu trúc
git commit -m "docs(wo-201): define work order decomposition spec and governance templates"

# Push branch và mở Pull Request
git push -u origin feature/WO-201-infrastructure-setup
```

# Xác minh và Definition of Done

## Tiêu chí Definition of Done (DoD)

1. Issue `WO-201` tồn tại và đáp ứng chặt chẽ cả 5 quy tắc của Definition of Ready (DoR).
2. `.copilotignore` tồn tại ở thư mục gốc repository và loại trừ các mẫu nhạy cảm (`.env*`, secrets, key).
3. `docs/work-order-decomposition.md` tồn tại và chứa các contract UI, SQL và REST API đã được xác minh.
4. Pull Request đã được mở với PR Template mới tự động điền, phần khai báo đã hoàn tất và không chứa bất kỳ code ứng dụng nào (`src/`).
