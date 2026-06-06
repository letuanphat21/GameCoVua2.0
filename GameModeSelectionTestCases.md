# BẢNG MÔ TẢ KỊCH BẢN KIỂM THỬ (TEST CASES) - CHỌN CHẾ ĐỘ CHƠI

## Tổng quan về chức năng Chọn chế độ chơi

Chức năng chọn chế độ chơi trong trò chơi cờ vua được xử lý chủ yếu trong lớp `MainFrame` và `ChessGameGUI`, cho phép người dùng lựa chọn giữa hai chế độ:
1. **Human - Human**: Hai người chơi (isAi = false)
2. **Human - AI**: Người chơi vs Máy tính (isAi = true)

Các hành vi chính được kiểm thử:

1. **Khởi tạo chế độ Human-Human:** Khi người dùng chọn chế độ này, ChessGame khởi tạo với isAi = false, whiteTurn = true, và bàn cờ đầy đủ quân.
2. **Khởi tạo chế độ Human-AI:** Khi người dùng chọn chế độ này, ChessGame khởi tạo với isAi = true, sẵn sàng cho AI.
3. **Vị trí ban đầu chính xác:** Tất cả các quân phải được đặt đúng vị trí theo quy tắc cờ vua tiêu chuẩn.
4. **Độc lập giữa các instance:** Hai instance ChessGame cho hai chế độ phải hoạt động độc lập, không ảnh hưởng lẫn nhau.
5. **Reset trạng thái:** Khi reset game, chỉ game được reset bị ảnh hưởng, game khác vẫn giữ nguyên trạng thái.

Dưới đây là **5 kịch bản kiểm thử chi tiết (Test Cases)** được xây dựng dựa trên file `DoAn/src/testcase/GameModeSelectionTest.java`.

---

## Kết quả thực thi kiểm thử (Test Execution Results)

> **Trạng thái tổng quát:** Tất cả 5 test cases đều chạy thành công và đạt kết quả mong đợi.

| Tổng số Test Cases | Số lượng Đạt (Pass) | Số lượng Lỗi (Fail) | Tỷ lệ thành công |
| :---: | :---: | :---: | :---: |
| 5 | 5 | 0 | 100% |

---

## Danh sách chi tiết các Test Cases

### 1. Test Case TC-GM-001: Kiểm tra chế độ Human-Human khởi tạo chính xác

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-GM-001` |
| **Tên Test Case** | Kiểm tra chế độ Human-Human được khởi tạo đúng khi lựa chọn |
| **Chức năng** | Khởi tạo chế độ chơi (ChessGame initialization - Human-Human) |
| **Tiền điều kiện** | Ứng dụng vừa khởi động, chưa chọn chế độ chơi nào. |
| **Dữ liệu đầu vào (Input Data)** | - Người dùng chọn nút "Human - Human".<br>- MainFrame tạo ChessGame với isAi = false. |
| **Các bước thực hiện** | 1. Kiểm tra humanVsHumanGame được khởi tạo thành công (not null).<br>2. Kiểm tra Board được tạo (not null).<br>3. Kiểm tra whiteTurn = true (lượt Trắng đi trước).<br>4. Kiểm tra danh sách historyMoves rỗng.<br>5. Kiểm tra isAi = false (chế độ AI không bật).<br>6. Kiểm tra số lượng quân = 32 (vị trí ban đầu). |
| **Kết quả mong đợi (Expected Output)** | - Game khởi tạo thành công.<br>- whiteTurn = true.<br>- historyMoves.isEmpty() = true.<br>- isAi = false.<br>- Board có đúng 32 quân. |
| **Kết quả thực tế (Actual Output)** | - ChessGame khởi tạo thành công với toàn bộ thông số như kỳ vọng. |
| **Trạng thái** | Đạt (Pass) |

---

### 2. Test Case TC-GM-002: Kiểm tra chế độ Human-AI khởi tạo chính xác

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-GM-002` |
| **Tên Test Case** | Kiểm tra chế độ Human-AI được khởi tạo đúng khi lựa chọn |
| **Chức năng** | Khởi tạo chế độ chơi (ChessGame initialization - Human-AI) |
| **Tiền điều kiện** | Ứng dụng vừa khởi động, chưa chọn chế độ chơi nào. |
| **Dữ liệu đầu vào (Input Data)** | - Người dùng chọn nút "Human - AI".<br>- MainFrame tạo ChessGame với isAi = true.<br>- Gọi setAiMode(true). |
| **Các bước thực hiện** | 1. Kiểm tra humanVsAiGame được khởi tạo thành công (not null).<br>2. Kiểm tra isAi = true (chế độ AI bật).<br>3. Kiểm tra whiteTurn = true (lượt Trắng đi trước).<br>4. Kiểm tra danh sách historyMoves rỗng.<br>5. Kiểm tra số lượng quân = 32 (vị trí ban đầu). |
| **Kết quả mong đợi (Expected Output)** | - Game khởi tạo thành công với chế độ AI bật.<br>- isAi = true.<br>- whiteTurn = true.<br>- historyMoves.isEmpty() = true.<br>- Board có đúng 32 quân. |
| **Kết quả thực tế (Actual Output)** | - ChessGame khởi tạo thành công với toàn bộ thông số phù hợp chế độ AI. |
| **Trạng thái** | Đạt (Pass) |

---

### 3. Test Case TC-GM-003: Kiểm tra vị trí ban đầu của bàn cờ

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-GM-003` |
| **Tên Test Case** | Kiểm tra Board được khởi tạo chính xác ở vị trí mặc định |
| **Chức năng** | Khởi tạo vị trí quân ban đầu (Board initialization) |
| **Tiền điều kiện** | Cả hai chế độ Human-Human và Human-AI đã khởi tạo thành công. |
| **Dữ liệu đầu vào (Input Data)** | - Bàn cờ 8x8 được tạo mới với cấu hình tiêu chuẩn cờ vua. |
| **Các bước thực hiện** | 1. Kiểm tra Vua Trắng ở e1 (Position(7, 4)).<br>2. Kiểm tra Vua Đen ở e8 (Position(0, 4)).<br>3. Kiểm tra các Tốt Trắng ở hàng 2 (row 6, 8 quân).<br>4. Kiểm tra các Tốt Đen ở hàng 7 (row 1, 8 quân).<br>5. Kiểm tra các hàng 3, 4, 5, 6 (row 2-5) rỗng.<br>6. Kiểm tra cấu hình của hai chế độ giống nhau. |
| **Kết quả mong đợi (Expected Output)** | - Vua Trắng ở (7, 4).<br>- Vua Đen ở (0, 4).<br>- 8 Tốt Trắng ở row 6.<br>- 8 Tốt Đen ở row 1.<br>- Các hàng giữa (2-5) đều rỗng.<br>- Hai chế độ có cấu hình giống nhau. |
| **Kết quả thực tế (Actual Output)** | - Tất cả vị trí quân đều chính xác theo tiêu chuẩn cờ vua. |
| **Trạng thái** | Đạt (Pass) |

---

### 4. Test Case TC-GM-004: Kiểm tra hoán đổi giữa hai chế độ chơi

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-GM-004` |
| **Tên Test Case** | Kiểm tra hoán đổi giữa hai chế độ chơi khi quay lại Home Screen |
| **Chức năng** | Chuyển đổi giữa chế độ (Mode switching & Reset) |
| **Tiền điều kiện** | Đang ở chế độ Human-Human, sau đó người dùng quay lại Home Screen. |
| **Dữ liệu đầu vào (Input Data)** | - Thực hiện nước đi e2 → e4 trong Human-Human.<br>- Reset lại Game.<br>- Chọn chế độ Human-AI. |
| **Các bước thực hiện** | 1. Thực hiện nước đi e2 → e4: humanVsHumanGame.makeMove(Position(6,4), Position(4,4)).<br>2. Kiểm tra historyMoves.size() = 1 (1 dòng lịch sử).<br>3. Kiểm tra humanVsAiGame vẫn trống (0 dòng lịch sử).<br>4. Gọi humanVsHumanGame.resetGame().<br>5. Kiểm tra humanVsHumanGame quay về ban đầu (historyMoves rỗng, whiteTurn = true).<br>6. Kiểm tra humanVsAiGame sẵn sàng để chơi (lịch sử rỗng, whiteTurn = true). |
| **Kết quả mong đợi (Expected Output)** | - humanVsHumanGame có 1 dòng lịch sử sau nước đi.<br>- humanVsAiGame không bị ảnh hưởng (lịch sử vẫn trống).<br>- Sau reset, humanVsHumanGame quay về ban đầu.<br>- humanVsAiGame vẫn sẵn sàng với trạng thái ban đầu. |
| **Kết quả thực tế (Actual Output)** | - Hai instance game hoạt động độc lập, reset chỉ ảnh hưởng game được reset. |
| **Trạng thái** | Đạt (Pass) |

---

### 5. Test Case TC-GM-005: Kiểm tra độc lập giữa các instance game

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-GM-005` |
| **Tên Test Case** | Kiểm tra các instance game độc lập khi một game thay đổi trạng thái |
| **Chức năng** | Độc lập của các instance game (Game Instance Independence) |
| **Tiền điều kiện** | Cả hai chế độ (Human-Human và Human-AI) được khởi tạo. |
| **Dữ liệu đầu vào (Input Data)** | - Thực hiện 2 nước đi liên tiếp trong Human-Human.<br>- Kiểm tra trạng thái của Human-AI sau mỗi nước đi. |
| **Các bước thực hiện** | 1. Nước 1: humanVsHumanGame.makeMove(Position(6,4), Position(4,4)) - White e2→e4.<br>2. Kiểm tra currentPlayerColor của Human-Human = BLACK.<br>3. Kiểm tra currentPlayerColor của Human-AI vẫn = WHITE (không ảnh hưởng).<br>4. Nước 2: humanVsHumanGame.makeMove(Position(1,4), Position(3,4)) - Black e7→e5.<br>5. Kiểm tra historyMoves.size() của Human-Human = 2.<br>6. Kiểm tra historyMoves.size() của Human-AI = 0 (không thay đổi).<br>7. Kiểm tra currentPlayerColor của Human-Human = WHITE.<br>8. Kiểm tra currentPlayerColor của Human-AI = WHITE.<br>9. Kiểm tra số lượng quân trên cả hai bàn cờ bằng nhau. |
| **Kết quả mong đợi (Expected Output)** | - Human-Human: historyMoves = 2, currentColor = WHITE.<br>- Human-AI: historyMoves = 0, currentColor = WHITE.<br>- Hai game hoạt động độc lập, không ảnh hưởng lẫn nhau.<br>- Số lượng quân trên bàn cờ của cả hai game bằng nhau (32 quân). |
| **Kết quả thực tế (Actual Output)** | - Hai game hoạt động hoàn toàn độc lập, thay đổi một game không ảnh hưởng đến game kia. |
| **Trạng thái** | Đạt (Pass) |

---

## Kết luận

Tất cả 5 test cases của chức năng **Chọn chế độ chơi** đều **PASS (100%)**. Hệ thống:
- ✅ Khởi tạo chính xác cả hai chế độ (Human-Human và Human-AI)
- ✅ Đặt các quân ở vị trí ban đầu đúng theo luật cờ vua
- ✅ Duy trì độc lập giữa các instance game
- ✅ Hỗ trợ chuyển đổi giữa các chế độ mà không mất dữ liệu
- ✅ Reset game chỉ ảnh hưởng đến game được reset