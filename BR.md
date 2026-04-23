# Giới thiệu tổng quan về đề tài (Business Requirement)  
## Tên đề tài  
**Xây dựng game Cờ Vua trên nền tảng Java Swing (Desktop Application)**

## 1. Bối cảnh & lý do chọn đề tài  
Cờ vua là một trò chơi trí tuệ phổ biến, có luật chơi rõ ràng và mang tính chiến thuật cao. Trong phạm vi môn **Nhập môn Công nghệ Phần mềm**, đề tài xây dựng game cờ vua giúp nhóm:
- Rèn luyện quy trình phát triển phần mềm (phân tích yêu cầu → thiết kế → lập trình → kiểm thử).
- Thực hành lập trình hướng đối tượng với **Java**.
- Làm quen phát triển ứng dụng giao diện desktop bằng **Java Swing**.
- Áp dụng các khái niệm cơ bản như: mô hình dữ liệu, xử lý sự kiện, tách lớp UI/logic, quản lý trạng thái trò chơi.

> Tham khảo repo demo của nhóm: `letuanphat21/GameCoVua2.0` (Java 100%).  

## 2. Mục tiêu hệ thống  
Hệ thống cần cung cấp một ứng dụng chơi cờ vua trên máy tính với giao diện trực quan, cho phép người dùng thực hi���n đầy đủ các thao tác cơ bản khi chơi cờ vua, bao gồm:
- Hiển thị bàn cờ và quân cờ đúng quy chuẩn.
- Cho phép người chơi di chuyển quân theo luật.
- Quản lý lượt đi, trạng thái ván cờ và điều kiện kết thúc.

## 3. Phạm vi (Scope)  
### 3.1. Trong phạm vi (In-scope)  
- Ứng dụng desktop chạy trên môi trường có cài **JDK** (Windows/Linux/macOS).
- Chế độ chơi cơ bản (ưu tiên): **2 người chơi trên cùng một máy (local PvP)**.
- Luật di chuyển chuẩn cho các quân cờ: Vua, Hậu, Xe, Tượng, Mã, Tốt.
- Tính năng hỗ trợ người chơi:
  - Tô sáng ô có thể đi (nếu nhóm triển khai).
  - Thông báo lỗi khi đi sai luật.
  - Hiển thị lượt hiện tại (Trắng/Đen).
  - Thông báo khi **chiếu (check)**, **chiếu hết (checkmate)** hoặc **hòa (draw)** (tùy mức hoàn thiện).

### 3.2. Ngoài phạm vi (Out-of-scope)  
- Chơi online/mạng LAN.
- AI/Bot đánh cờ (nếu không được yêu cầu).
- Hệ thống tài khoản, xếp hạng, lưu cloud.
- Phân tích ván đấu nâng cao như engine gợi ý nước đi.

## 4. Đối tượng sử dụng (Stakeholders / Users)  
- **Người chơi**: học sinh/sinh viên hoặc người dùng phổ thông muốn chơi cờ vua trên máy tính.
- **Nhóm phát triển**: sinh viên thực hiện đồ án môn học (cần code rõ ràng, dễ mở rộng, dễ báo cáo).
- **Giảng viên**: đánh giá theo tiêu chí chức năng, quy trình và chất lượng sản phẩm.

## 5. Nhu cầu nghiệp vụ (Business Needs)  
- Cần một phần mềm cờ vua **dễ sử dụng**, giao diện rõ ràng.
- Đảm bảo luật chơi cơ bản hoạt động đúng để người dùng có trải nghiệm hợp lệ.
- Có khả năng chạy ổn định và dễ cài đặt/thực thi trong môi trường học tập.

## 6. Yêu cầu chức năng mức cao (High-level Functional Requirements)  
**BR-01: Hiển thị bàn cờ và quân cờ**
- Hệ thống hiển thị bàn cờ 8x8 và quân cờ theo 2 màu Trắng/Đen.

**BR-02: Tương tác chọn và di chuyển quân**
- Người dùng chọn quân cờ và thực hiện nước đi bằng chuột.
- Hệ thống chỉ cho phép nước đi hợp lệ theo luật.

**BR-03: Quản lý lượt chơi**
- Hệ thống kiểm soát luân phiên lượt Trắng/Đen.

**BR-04: Xử lý bắt quân**
- Cho phép bắt quân đối phương theo đúng luật.

**BR-05: Xác định trạng thái ván cờ**
- Phát hiện và thông báo các trạng thái: chiếu, chiếu hết, hòa (tùy mức hoàn thiện).

**BR-06: Bắt đầu ván mới / khởi tạo lại**
- Cho ph��p người dùng tạo ván mới để chơi lại.

*(Tùy chọn nếu nhóm có triển khai: undo/redo, lưu ván, đồng hồ thời gian, phong cấp tốt, nhập thành, bắt tốt qua đường.)*

## 7. Yêu cầu phi chức năng (Non-functional Requirements)  
- **NFR-01: Tính đúng đắn**: luật di chuyển và bắt quân phải chính xác ở mức cơ bản.
- **NFR-02: Tính dễ dùng (Usability)**: thao tác đơn giản, thông báo rõ khi đi sai.
- **NFR-03: Hiệu năng**: phản hồi nhanh khi chọn quân và vẽ lại bàn cờ.
- **NFR-04: Khả năng bảo trì**: mã nguồn tổ chức theo lớp, dễ đọc, dễ mở rộng.
- **NFR-05: Tính ổn định**: hạn chế lỗi runtime khi người dùng thao tác liên tục.

## 8. Giá trị mang lại (Business Value)  
- Với người dùng: có công cụ chơi cờ vua gọn nhẹ, dễ dùng.
- Với nhóm: đạt được mục tiêu môn học, thực hành kỹ năng phân tích yêu cầu, thiết kế, lập trình UI và xử lý logic game.
- Tạo nền tảng để mở rộng về sau: AI, lưu ván, chơi online, phân tích nước đi.

## 9. Tiêu chí nghiệm thu (Acceptance Criteria)  
- Ứng dụng chạy được, hiển thị đúng bàn cờ và quân cờ.
- Người chơi di chuyển được quân theo luật cơ bản, không cho phép đi sai.
- Có quản lý lượt đi và bắt quân.
- Có thể bắt đầu ván mới và chơi lại mà không lỗi.
- (Nếu có) thông báo các trạng thái như chiếu/chiếu hết/hòa.

---
**Ghi chú:** Nếu bạn gửi thêm cấu trúc dự án hoặc các chức năng nhóm đã làm trong repo (ví dụ: menu, undo, nhập thành, phong tốt...), mình có thể chỉnh phần Business Requirement sát với demo của nhóm hơn (đúng phạm vi và đúng tiêu chí chấm điểm).
