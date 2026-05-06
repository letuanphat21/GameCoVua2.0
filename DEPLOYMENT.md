# TÀI LIỆU TRIỂN KHAI PHẦN MỀM (IEEE 1063)  
## Game Cờ Vua 2.0

**Mã tài liệu:** GCV-DEPLOY-001  
**Phiên bản:** 1.0  
**Ngày phát hành:** 2026-05-06  
**Tình trạng:** Chính thức  
**Phạm vi áp dụng:** Công khai

---

## 1. GIỚI THIỆU

### 1.1 Mục đích  
Tài liệu này hướng dẫn chi tiết cài đặt, cấu hình, sử dụng và bảo trì phần mềm **Game Cờ Vua 2.0** (Chess Game 2.0). Đối tượng sử dụng là người dùng cơ bản, quản trị viên và nhân viên hỗ trợ kỹ thuật.

### 1.2 Phạm vi  
Bao gồm:
- Yêu cầu hệ thống và điều kiện tiên quyết
- Quy trình cài đặt
- Cấu hình và thiết lập
- Hướng dẫn vận hành
- Xử lý sự cố và hỗ trợ
- Bảo trì và gỡ cài đặt
Không bao gồm
- Phát triển và biên dịch mã nguồn
- Chi tiết thiết kế kiến trúc
- Kỹ thuật tối ưu hiệu năng

### 1.3 Cấu trúc tài liệu
- Giới thiệu (phần này)
- Tổng quan phần mềm
- Yêu cầu hệ thống
- Công việc trước khi cài đặt
- Quy trình cài đặt
- Hướng dẫn vận hành
- Hướng dẫn xử lý sự cố
- Quy trình gỡ cài đặt

### 1.4 Đối tượng sử dụng  
- Người dùng cơ bản  
- Quản trị viên CNTT  
- Lập trình viên và người phát triển dự án

### 1.5 Quy ước và thuật ngữ
| Thuật ngữ         | Định nghĩa                                        |
|-------------------|---------------------------------------------------|
| Ứng dụng          | Sản phẩm phần mềm Game Cờ Vua 2.0                 |
| Cài đặt           | Quá trình thiết lập ứng dụng trên hệ thống        |
| Triển khai        | Phân phối và cài đặt ứng dụng                     |
| Runtime           | Phần mềm cần thiết để chạy ứng dụng               |
| Tệp thực thi      | Tệp nhị phân (.exe) chạy ứng dụng                 |
| Người dùng        | Cá nhân vận hành ứng dụng                         |
| AI                | Trí tuệ nhân tạo - đối thủ máy tính               |
| App-Image         | Gói ứng dụng tự chứa (không phụ thuộc bên ngoài)  |
| JRE               | Môi trường chạy Java                              |
| Minimax           | huật toán lý thuyết trò chơi dùng để ra quyết định|
| PGN               | Định dạng ghi ván cờ                              |

---

## 2. TỔNG QUAN PHẦN MỀM

### 2.1 Giới thiệu sản phẩm  
Game Cờ Vua 2.0 là một ứng dụng cờ vua đa nền tảng được phát triển bằng Java, cung cấp giao diện tương tác để chơi cờ. Ứng dụng hỗ trợ nhiều chế độ chơi và sử dụng trí tuệ nhân tạo cho gameplay với máy.

### 2.2 Phiên bản  
- Tên sản phẩm: Game Cờ Vua 2.0
- Phiên bản: 2.0.0  
- Ngày phát hành: 2026-05-06

### 2.3 Tính năng chính
| Tính năng             | Mô tả                                      | Trạng thái      |
|----------------------|---------------------------------------------|-----------------|
| Người vs Người       | 2 người trên 1 máy                          | Hoàn thiện      |
| Người vs Máy (AI)    | Đối đầu AI Minimax                          | Hoàn thiện      |
| Thuật toán Minimax   | AI ra quyết định nâng cao                   | Hoàn thiện      |
| Giao diện đồ họa đẹp | Giao diện Swing, hiển thị bàn cờ tương tác  | Hoàn thiện      |
| Lịch sử nước đi      | Theo dõi nước đi                            | Hoàn thiện      |     
| Multiplayer online   | Chơi mạng                                   | Đang phát triển |

---

## 3. YÊU CẦU HỆ THỐNG

### 3.1 Yêu cầu tối thiểu
| Thành phần        | Yêu cầu tối thiểu     |
|-------------------|----------------------|
| Hệ điều hành      | Windows 7 trở lên    |
| Vi xử lý          | Pentium IV 2.0 GHz   |
| Bộ nhớ RAM        | 512 MB               |
| Dung lượng ổ đĩa  | 300 MB               |
| Độ phân giải      | 1024×768             |

### 3.2 Yêu cầu khuyến nghị
| Thành phần        | Khuyến nghị          |
|-------------------|----------------------|
| Hệ điều hành      | Windows 10/11 (64bit)|
| Vi xử lý          | Intel Core i5 2.5GHz |
| RAM               | 1-2 GB               |
| Ổ đĩa SSD         | 500 MB               |
| Độ phân giải      | 1920×1080            |

### 3.3 Phần mềm bổ trợ
| Thành phần      | Phiên bản | Bắt buộc | Ghi chú                   |
|-----------------|-----------|----------|---------------------------|
| Java Runtime    | 21+       | Có thể   | Đã kèm trong app-image    |

---

## 4. CHUẨN BỊ TRƯỚC CÀI ĐẶT

- Đảm bảo quyền admin trên máy tính.
- Có ít nhất 300MB trống.
- Khuyến khích tạo điểm khôi phục hệ thống với Windows.
- Ngắt các phần mềm diệt virus hoặc cho phép game chạy.

---

## 5. QUY TRÌNH CÀI ĐẶT

### 5.1 Cài đặt từ App-Image (đề xuất)
1. **Tải file:**  
   Tải về `GameCoVua-2.0-App-Image.zip` từ [Releases](https://github.com/letuanphat21/GameCoVua2.0/releases).
2. **Giải nén:**  
   Giải nén vào thư mục, ví dụ `C:\Program Files\GameCoVua\`.
3. **Tạo shortcut (tùy chọn):**  
   Chuột phải `GameCoVua.exe` > Send to > Desktop.
4. **Hoàn tất:**  
   Kiểm tra thư mục có file `GameCoVua.exe`. Có thể chạy ngay.

### 5.2 Cài đặt từ JAR (Yêu cầu Java)
1. **Yêu cầu:**  
   Máy tính đã cài **Java 21**
2. **Tải file:**  
   `GameCoVua-2.0.jar`
3. **Chạy:**  
   - Mở CMD, chạy: `java -jar GameCoVua.jar`
   - Hoặc double-click vào file JAR

---

## 6. HƯỚNG DẪN SỬ DỤNG

### 6.1 Cách chạy ứng dụng  
- App-Image: Double-click `GameCoVua.exe`
- JAR: Double-click `GameCoVua.jar` hoặc `java -jar GameCoVua.jar`
- Tạo shortcut ra desktop nếu muốn

### 6.2 Giao diện chính  
<img width="1042" height="829" alt="image" src="https://github.com/user-attachments/assets/7992739c-fc53-43ad-aa57-c179f99bfddc" />
Chọn chế độ chơi, gồm 2 chế độ:
- Human - Human: Chế độ 2 người chơi
- Human - AI: Chế độ chơi 1 người chơi với máy tính (AI)

### 6.3 Giao diện khi vào trò chơi
<img width="1038" height="857" alt="image" src="https://github.com/user-attachments/assets/26fb0703-f604-44bd-8049-c189060c3410" />
Gồm bàn cờ và tất cả các quân cờ ở 2 bên:
- Sử dụng chuột chọn vào quân cờ của bản thân để bắt đầu di chuyển
- Hệ thống sẽ hiện thị nước đi khả dụng
- Người chơi lựa chọn nước đi
Bên phải có lịch sự quá trình các nước đi được cập nhật theo từng nước đi
Có menu góc trái trên cùng để đầu hàng hoặc tạo trận mới

---

## 7. XỬ LÝ SỰ CỐ
### 7.1 Lỗi không chạy:
- Kiểm tra quyền
- Chạy admin
- Cài lại
### 7.2 Lỗi Java:
- Cài Java 21
### 7.3 Lag:
- Tắt app nền
- Tăng RAM JVM

---

## 8.Gỡ cài đặt
### 8.1 App-Image:
- Xoá folder
### 8.2 JAR:
- Xoá file .jar
