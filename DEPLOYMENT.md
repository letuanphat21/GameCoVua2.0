# 📋 Hướng dẫn Deployment - Game Cờ Vua 2.0

## Mục lục
1. [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
2. [Cài đặt ứng dụng](#cài-đặt-ứng-dụng)
3. [Chạy ứng dụng](#chạy-ứng-dụng)
4. [Gỡ cài đặt](#gỡ-cài-đặt)
5. [Khắc phục sự cố](#khắc-phục-sự-cố)
6. [Thông tin kỹ thuật](#thông-tin-kỹ-thuật)

---

## 🖥️ Yêu cầu hệ thống

### Yêu cầu tối thiểu
- **Hệ điều hành**: Windows 7 trở lên (Windows 10/11 khuyến nghị)
- **Bộ nhớ RAM**: 512 MB
- **Dung lượng ổ đĩa**: 300 MB (bao gồm Java Runtime)
- **Phân giải màn hình**: 1024 x 768 trở lên

### Yêu cầu được khuyến nghị
- **Hệ điều hành**: Windows 10/11 (64-bit)
- **Bộ nhớ RAM**: 1 GB trở lên
- **CPU**: Intel/AMD 2GHz hoặc cao hơn
- **Phân giải màn hình**: 1920 x 1080 trở lên

### Không cần cài đặt
- ✅ Java JDK/JRE (đã tích hợp sẵn)
- ✅ Thư viện bổ sung
- ✅ Các công cụ lập trình

---

## 📥 Cài đặt ứng dụng

### Phương pháp 1: Cài đặt từ App-Image (Đơn giản nhất)

**Bước 1:** Tải file cài đặt
- Tải `GameCoVua.zip` từ [Releases](https://github.com/letuanphat21/GameCoVua2.0/releases)
- Giải nén vào thư mục bất kỳ, ví dụ: `C:\Games\GameCoVua\`

**Bước 2:** Chạy ứng dụng
- Vào thư mục `GameCoVua/bin/`
- Tìm file `GameCoVua.exe`
- Double-click để chạy

**Bước 3:** Tạo shortcut (tùy chọn)
- Chuột phải vào `GameCoVua.exe`
- Chọn "Send to" → "Desktop (create shortcut)"
- Bây giờ bạn có shortcut trên Desktop

### Phương pháp 2: Chạy trực tiếp từ file JAR

**Yêu cầu:** Có Java 21 trở lên cài sẵn trên máy

**Bước 1:** Tải file JAR
- Tải `GameCoVua.jar` từ [Releases](https://github.com/letuanphat21/GameCoVua2.0/releases)
- Lưu vào thư mục bất kỳ

**Bước 2:** Chạy ứng dụng
- **Cách 1:** Double-click vào `GameCoVua.jar`
- **Cách 2:** Mở Command Prompt, chạy:
```bash
cd "đường_dẫn_tới_thư_mục_chứa_jar"
java -jar GameCoVua.jar
```

---

## ▶️ Chạy ứng dụng

### Khởi động đầu tiên
1. Tìm và double-click `GameCoVua.exe` (hoặc shortcut trên Desktop)
2. Chờ ứng dụng tải (lần đầu tiên có thể mất 5-10 giây)
3. Cửa sổ Game Cờ Vua sẽ hiện ra

### Giao diện chính
```
┌─────────────────────────────────────┐
│         Game Cờ Vua 2.0             │
├─────────────────────────────────────┤
│                                     │
│    [Home]                           │
│    - Người vs Người                 │
│    - Người vs AI                    │
│    - Online (Đang phát triển)        │
│                                     │
└─────────────────────────────────────┘
```

### Các chế độ chơi

#### 1. Người vs Người
- Hai người chơi trên cùng một máy tính
- Chuyển lượt nhau sau mỗi nước đi
- Hỗ trợ hoàn tác nước đi

#### 2. Người vs AI
- Chơi với máy tính sử dụng thuật toán Minimax
- Có độ khó có thể điều chỉnh
- Máy tính sẽ tự động tính toán nước đi

#### 3. Online (Đang phát triển)
- Chơi với người chơi khác qua mạng
- Tính năng này sẽ sớm được cập nhật

---

## 🗑️ Gỡ cài đặt

### Gỡ cài đặt App-Image
1. Mở File Explorer
2. Vào thư mục chứa `GameCoVua/`
3. Xóa toàn bộ thư mục
4. Xóa shortcut trên Desktop (nếu có)

**Ghi chú:** Quá trình gỡ cài đặt không để lại file dư thừa trên hệ thống

### Gỡ cài đặt cấu hình (tùy chọn)
Nếu bạn muốn xóa toàn bộ dữ liệu trò chơi (điểm số, cài đặt):
1. Vào: `C:\Users\[YourUsername]\AppData\Local\GameCoVua\`
2. Xóa thư mục này

---

## 🔧 Khắc phục sự cố

### Vấn đề 1: Ứng dụng không khởi động
**Nguyên nhân có thể:** 
- Java Runtime bị hỏng
- Thư mục không có quyền truy cập

**Giải pháp:**
1. Kiểm tra quyền truy cập thư mục
2. Chạy Command Prompt (Admin):
```bash
cd "đường_dẫn_đến_GameCoVua"
java -jar GameCoVua.jar
```
3. Xem thông báo lỗi chi tiết

### Vấn đề 2: Lỗi "Java not found"
**Nguyên nhân:** Java Runtime không được cài đặt

**Giải pháp:**
- Tải Java 21 JRE từ: https://www.oracle.com/java/technologies/downloads/#java21
- Chọn "Windows x64 Installer"
- Cài đặt bình thường
- Khởi động lại máy tính

### Vấn đề 3: Ứng dụng chạy chậm
**Nguyên nhân có thể:**
- RAM không đủ
- CPU đang chạy các chương trình khác

**Giải pháp:**
1. Đóng các ứng dụng không cần thiết
2. Tăng RAM (nếu có thể)
3. Chạy từ ổ SSD thay vì HDD

### Vấn đề 4: Màn hình bị vỡ hoặc không hiển thị đúng
**Nguyên nhân có thể:** Phân giải màn hình không hỗ trợ

**Giải pháp:**
1. Thay đổi phân giải màn hình thành 1024x768 hoặc cao hơn
2. Cập nhật driver card đồ họa

### Vấn đề 5: Lỗi khi chơi với AI
**Nguyên nhân có thể:**
- Memory bị hết khi tính toán nước đi
- Độ sâu tìm kiếm quá lớn

**Giải pháp:**
1. Khởi động lại ứng dụng
2. Giảm độ khó của AI (nếu có tùy chọn)

---

## ℹ️ Thông tin kỹ thuật

### Thông số kỹ thuật ứng dụng
| Thông số | Chi tiết |
|---------|---------|
| **Tên ứng dụng** | Game Cờ Vua 2.0 |
| **Phiên bản** | 2.0 |
| **Ngôn ngữ lập trình** | Java 21 |
| **Framework GUI** | Java Swing |
| **Thuật toán AI** | Minimax |
| **Hệ điều hành hỗ trợ** | Windows 7+ |
| **Kích thước file** | ~300 MB (bao gồm JRE) |
| **License** | Open Source |

### Cấu trúc dự án
```
GameCoVua2.0/
├── DoAn/
│   ├── src/
│   │   └── covua/
│   │       ├── Board.java              # Quản lý bàn cờ
│   │       ├── ChessGame.java          # Logic trò chơi
│   │       ├── Position.java           # Vị trí trên bàn cờ
│   │       ├── PieceColor.java         # Màu quân cờ
│   │       ├── chess/                  # Các loại quân cờ
│   │       │   ├── Piece.java          # Lớp cha
│   │       │   ├── Pawn.java           # Tốt
│   │       │   ├── Rook.java           # Xe
│   │       │   ├── Knight.java         # Mã
│   │       │   ├── Bishop.java         # Tượng
│   │       │   ├── Queen.java          # Hậu
│   │       │   └── King.java           # Vua
│   │       ├── ai/                     # AI engine
│   │       │   ├── Minimax.java        # Thuật toán Minimax
│   │       │   └── Node.java           # Cây trò chơi
│   │       └── view/                   # Giao diện GUI
│   │           ├── MainFrame.java      # Cửa sổ chính
│   │           ├── ChessGameGUI.java   # Bàn cờ GUI
│   │           ├── Home.java           # Màn hình chính
│   │           └── ChessSquareComponent.java # Ô cờ
│   ├── .classpath
│   └── .project
├── DEPLOYMENT.md                        # Hướng dẫn này
├── README.md                            # Giới thiệu dự án
└── BR.md                               # Yêu cầu kinh doanh
```

### Các lớp chính

#### Board.java
- Quản lý trạng thái bàn cờ (8x8)
- Khởi tạo quân cờ ở vị trí ban đầu
- Cung cấp phương thức di chuyển quân cờ

#### ChessGame.java
- Kiểm tra nước đi hợp lệ
- Phát hiện check, checkmate, stalemate
- Quản lý lượt chơi

#### Minimax.java
- Thuật toán AI để tính toán nước đi tốt nhất
- Sử dụng cây trò chơi (game tree)
- Đánh giá vị trí dựa trên heuristics

#### ChessGameGUI.java
- Hiển thị bàn cờ trên màn hình
- Xử lý sự kiện click chuột
- Cập nhật giao diện sau mỗi nước đi

### Yêu cầu hệ thống (Lập trình viên)

Nếu bạn muốn biên dịch từ source code:
- **JDK 21+** (từ Oracle)
- **Eclipse IDE** hoặc **IntelliJ IDEA**
- **Maven** (tùy chọn)

**Compile từ Command Line:**
```bash
cd DoAn
javac -d bin src/covua/**/*.java
jar cfe GameCoVua.jar covua.view.MainFrame -C bin .
```

---

## 📝 Nhật ký thay đổi

### Phiên bản 2.0 (Hiện tại)
- ✅ Chế độ Người vs Người
- ✅ Chế độ Người vs AI (Minimax)
- ✅ Giao diện GUI đẹp
- ✅ Hỗ trợ hoàn tác nước đi
- 🔄 Chế độ Online (đang phát triển)

### Phiên bản 1.0
- ✅ Logic cơ bản của trò chơi cờ vua
- ✅ Kiểm tra nước đi hợp lệ

---

## 📞 Hỗ trợ

### Báo cáo lỗi
Nếu bạn gặp vấn đề, vui lòng:
1. Vào [Issues](https://github.com/letuanphat21/GameCoVua2.0/issues)
2. Click "New Issue"
3. Mô tả chi tiết:
   - Hệ điều hành và phiên bản
   - Mô tả lỗi xảy ra
   - Screenshot (nếu có)
   - Các bước để lặp lại lỗi

### Đóng góp
Chúng tôi chào đón các đóng góp! Vui lòng:
1. Fork repository
2. Tạo branch mới (`git checkout -b feature/feature-name`)
3. Commit thay đổi (`git commit -m 'Add feature'`)
4. Push lên branch (`git push origin feature/feature-name`)
5. Tạo Pull Request

---

## 📄 License

Dự án này được cấp phép dưới [MIT License](LICENSE)

---

## 👨‍💻 Tác giả

**Lê Tuấn Phát** - [@letuanphat21](https://github.com/letuanphat21)

---

## ⭐ Cảm ơn

Cảm ơn bạn đã sử dụng Game Cờ Vua 2.0!

Nếu bạn thích dự án này, vui lòng:
- ⭐ Star repository
- 🔗 Share với bạn bè
- 💬 Để lại feedback

---

**Cập nhật lần cuối:** 2026-05-06

**Phiên bản tài liệu:** 1.0
