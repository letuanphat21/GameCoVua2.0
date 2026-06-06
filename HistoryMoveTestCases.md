# BẢNG MÔ TẢ KỊCH BẢN KIỂM THỬ (TEST CASES) - LỊCH SỬ NƯỚC ĐI

## Tổng quan về chức năng Lịch sử nước đi

Chức năng lịch sử nước đi trong trò chơi cờ vua được xử lý chủ yếu trong lớp `covua.ChessGame`, thông qua danh sách `historyMoves`. Mỗi khi người chơi hoặc AI thực hiện một nước đi hợp lệ, hệ thống sẽ ghi lại thông tin nước đi theo dạng ký hiệu bàn cờ, ví dụ `e2->e4`.

Các hành vi chính được kiểm thử:

1. **Khởi tạo lịch sử:** Khi tạo ván cờ mới, danh sách lịch sử phải rỗng.
2. **Ghi nước đi hợp lệ:** Khi một nước đi hợp lệ được thực hiện bằng `makeMove`, lịch sử phải thêm đúng một dòng.
3. **Không ghi nước đi sai:** Nước đi không hợp lệ không được thêm vào lịch sử.
4. **Ghi chú thích đặc biệt:** Khi ăn quân hoặc nhập thành, lịch sử phải ghi thêm chú thích tương ứng.
5. **Ghi nhiều nước theo thứ tự:** Khi có nhiều nước đi hợp lệ liên tiếp, lịch sử phải lưu đúng số lượng và đúng thứ tự phát sinh.
6. **Sao chép lịch sử:** Constructor sao chép `new ChessGame(game)` phải tạo bản sao lịch sử độc lập với game gốc.
7. **Chế độ AI:** Khi bật AI mode, lịch sử phải phân biệt nước của người chơi bằng nhãn `Player` và nước của máy bằng nhãn `AI`.

Dưới đây là **8 kịch bản kiểm thử chi tiết (Test Cases)** được xây dựng dựa trên file `DoAn/src/testcase/HistoryMoveTest.java`.

---

## Kết quả thực thi kiểm thử (Test Execution Results)

> **Trạng thái tổng quát:** Tất cả 8 test cases đều chạy thành công và đạt kết quả mong đợi.

| Tổng số Test Cases | Số lượng Đạt (Pass) | Số lượng Lỗi (Fail) | Tỷ lệ thành công |
| :---: | :---: | :---: | :---: |
| 8 | 8 | 0 | 100% |

---

## Danh sách chi tiết các Test Cases

### 1. Test Case TC-HM-001: Ghi lại một nước đi hợp lệ

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-HM-001` |
| **Tên Test Case** | Kiểm tra lịch sử ban đầu rỗng và ghi lại một nước đi hợp lệ |
| **Chức năng** | Lịch sử nước đi (`ChessGame.getHistoryMoves`, `ChessGame.makeMove`) |
| **Tiền điều kiện** | Khởi tạo một ván cờ mới bằng `new ChessGame()`. |
| **Dữ liệu đầu vào (Input Data)** | - **Nước đi:** Tốt Trắng từ `e2` đến `e4`.<br>- **Source:** `Position(6, 4)`.<br>- **Destination:** `Position(4, 4)`. |
| **Các bước thực hiện** | 1. Kiểm tra `game.getHistoryMoves().isEmpty()`.<br>2. Gọi `game.makeMove(new Position(6, 4), new Position(4, 4))`.<br>3. Kiểm tra số dòng lịch sử và nội dung dòng đầu tiên. |
| **Kết quả mong đợi (Expected Output)** | - Lịch sử ban đầu rỗng.<br>- `makeMove` trả về `true`.<br>- Lịch sử có đúng 1 dòng.<br>- Dòng lịch sử có chứa `White` và `e2->e4`. |
| **Kết quả thực tế (Actual Output)** | - `makeMove` trả về `true`.<br>- Lịch sử có 1 dòng và ghi đúng tọa độ `e2->e4`. |
| **Trạng thái** | Đạt (Pass) |

---

### 2. Test Case TC-HM-002: Không ghi nước đi không hợp lệ

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-HM-002` |
| **Tên Test Case** | Kiểm tra nước đi không hợp lệ không được ghi vào lịch sử |
| **Chức năng** | Lịch sử nước đi và kiểm tra hợp lệ nước đi (`ChessGame.makeMove`) |
| **Tiền điều kiện** | Ván cờ ở trạng thái mặc định ban đầu. |
| **Dữ liệu đầu vào (Input Data)** | - **Nước đi sai:** Tốt Trắng từ `e2` đến `e5`.<br>- **Source:** `Position(6, 4)`.<br>- **Destination:** `Position(3, 4)`. |
| **Các bước thực hiện** | 1. Gọi `game.makeMove(new Position(6, 4), new Position(3, 4))`.<br>2. Kiểm tra kết quả trả về.<br>3. Kiểm tra danh sách lịch sử. |
| **Kết quả mong đợi (Expected Output)** | - `makeMove` trả về `false` vì Tốt không được đi thẳng 3 ô.<br>- Lịch sử vẫn rỗng. |
| **Kết quả thực tế (Actual Output)** | - `makeMove` trả về `false`.<br>- Không có dòng nào được thêm vào lịch sử. |
| **Trạng thái** | Đạt (Pass) |

---

### 3. Test Case TC-HM-003: Ghi chú thích khi ăn quân

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-HM-003` |
| **Tên Test Case** | Kiểm tra lịch sử ghi chú thích ăn quân |
| **Chức năng** | Ghi lịch sử nước ăn quân (`formatMoveNotation`, `formatMoveDetails`) |
| **Tiền điều kiện** | Bàn cờ được dọn trống, sau đó đặt Vua Trắng, Vua Đen, Xe Trắng và Hậu Đen vào các vị trí kiểm thử. |
| **Dữ liệu đầu vào (Input Data)** | - **Vua Trắng:** `h1` (`Position(7, 7)`).<br>- **Vua Đen:** `h8` (`Position(0, 7)`).<br>- **Xe Trắng:** `a1` (`Position(7, 0)`).<br>- **Hậu Đen:** `a4` (`Position(4, 0)`).<br>- **Nước đi:** Xe Trắng `a1->a4`. |
| **Các bước thực hiện** | 1. Dọn sạch bàn cờ bằng helper `clearBoard`.<br>2. Đặt các quân cờ cần thiết lên bàn cờ.<br>3. Gọi `game.makeMove(new Position(7, 0), new Position(4, 0))`.<br>4. Kiểm tra nội dung lịch sử. |
| **Kết quả mong đợi (Expected Output)** | - Nước đi trả về `true`.<br>- Lịch sử có đúng 1 dòng.<br>- Dòng lịch sử chứa `a1->a4`.<br>- Dòng lịch sử chứa chú thích `(captures ...)`. |
| **Kết quả thực tế (Actual Output)** | - Xe Trắng ăn được Hậu Đen.<br>- Lịch sử ghi đúng tọa độ và có chú thích `captures`. |
| **Trạng thái** | Đạt (Pass) |

---

### 4. Test Case TC-HM-004: Ghi chú thích nhập thành cánh Vua

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-HM-004` |
| **Tên Test Case** | Kiểm tra lịch sử ghi chú thích nhập thành cánh Vua |
| **Chức năng** | Ghi lịch sử nước nhập thành (`ChessGame.makeMove`, `isCastlingMove`) |
| **Tiền điều kiện** | Bàn cờ được dọn trống. Vua Trắng và Xe Trắng ở vị trí nhập thành hợp lệ; Vua Đen được đặt để đảm bảo trạng thái ván cờ hợp lệ. |
| **Dữ liệu đầu vào (Input Data)** | - **Vua Trắng:** `e1` (`Position(7, 4)`).<br>- **Xe Trắng:** `h1` (`Position(7, 7)`).<br>- **Vua Đen:** `e8` (`Position(0, 4)`).<br>- **Nước đi:** Vua Trắng `e1->g1`. |
| **Các bước thực hiện** | 1. Dọn sạch bàn cờ.<br>2. Đặt Vua Trắng, Xe Trắng và Vua Đen vào vị trí cần thiết.<br>3. Gọi `game.makeMove(new Position(7, 4), new Position(7, 6))`.<br>4. Kiểm tra dòng lịch sử được tạo ra. |
| **Kết quả mong đợi (Expected Output)** | - Nước nhập thành trả về `true`.<br>- Lịch sử có đúng 1 dòng.<br>- Dòng lịch sử chứa `e1->g1`.<br>- Dòng lịch sử chứa `(kingside castling)`. |
| **Kết quả thực tế (Actual Output)** | - Nước nhập thành hợp lệ.<br>- Lịch sử ghi đúng tọa độ và chú thích nhập thành cánh Vua. |
| **Trạng thái** | Đạt (Pass) |

---

### 5. Test Case TC-HM-005: Ghi nhiều nước đi hợp lệ theo đúng thứ tự

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-HM-005` |
| **Tên Test Case** | Kiểm tra lịch sử ghi nhiều nước đi hợp lệ theo đúng thứ tự |
| **Chức năng** | Ghi lịch sử nhiều nước đi (`ChessGame.makeMove`, `ChessGame.getHistoryMoves`) |
| **Tiền điều kiện** | Ván cờ ở trạng thái mặc định ban đầu. |
| **Dữ liệu đầu vào (Input Data)** | - **Nước 1:** Tốt Trắng `e2->e4` (`Position(6, 4)` đến `Position(4, 4)`).<br>- **Nước 2:** Tốt Đen `e7->e5` (`Position(1, 4)` đến `Position(3, 4)`). |
| **Các bước thực hiện** | 1. Gọi `game.makeMove(new Position(6, 4), new Position(4, 4))`.<br>2. Gọi `game.makeMove(new Position(1, 4), new Position(3, 4))`.<br>3. Kiểm tra số lượng dòng lịch sử.<br>4. Kiểm tra thứ tự hai dòng lịch sử. |
| **Kết quả mong đợi (Expected Output)** | - Cả hai nước đi đều trả về `true`.<br>- Lịch sử có đúng 2 dòng.<br>- Dòng đầu chứa `e2->e4`.<br>- Dòng thứ hai chứa `e7->e5`. |
| **Kết quả thực tế (Actual Output)** | - Lịch sử ghi đủ 2 nước đi hợp lệ và giữ đúng thứ tự phát sinh. |
| **Trạng thái** | Đạt (Pass) |

---

### 6. Test Case TC-HM-006: resetGame xóa lịch sử và đưa lượt về quân Trắng

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-HM-006` |
| **Tên Test Case** | Kiểm tra `resetGame` xóa lịch sử và đưa lượt về quân Trắng |
| **Chức năng** | Reset ván cờ (`ChessGame.resetGame`) |
| **Tiền điều kiện** | Đã có một nước đi hợp lệ, lượt hiện tại chuyển sang quân Đen. |
| **Dữ liệu đầu vào (Input Data)** | - Thực hiện nước Tốt Trắng `e2->e4`.<br>- Gọi `game.resetGame()`. |
| **Các bước thực hiện** | 1. Gọi `game.makeMove(new Position(6, 4), new Position(4, 4))`.<br>2. Kiểm tra lượt hiện tại là quân Đen.<br>3. Gọi `game.resetGame()`.<br>4. Kiểm tra lịch sử và lượt chơi hiện tại. |
| **Kết quả mong đợi (Expected Output)** | - Lịch sử bị xóa sạch.<br>- Lượt chơi được đưa về quân Trắng (`PieceColor.WHITE`). |
| **Kết quả thực tế (Actual Output)** | - `resetGame` xóa lịch sử và đặt lượt về quân Trắng đúng như mong đợi. |
| **Trạng thái** | Đạt (Pass) |

---

### 7. Test Case TC-HM-007: Copy constructor sao chép lịch sử độc lập

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-HM-007` |
| **Tên Test Case** | Kiểm tra copy constructor sao chép lịch sử độc lập với game gốc |
| **Chức năng** | Sao chép trạng thái game (`ChessGame(ChessGame other)`) |
| **Tiền điều kiện** | Game gốc đã có một dòng lịch sử. |
| **Dữ liệu đầu vào (Input Data)** | - Game gốc thực hiện nước `e2->e4`.<br>- Tạo `ChessGame copiedGame = new ChessGame(game)`.<br>- Game gốc tiếp tục thực hiện thêm nước `e7->e5`. |
| **Các bước thực hiện** | 1. Thực hiện nước đi hợp lệ đầu tiên trên game gốc.<br>2. Tạo game copy từ game gốc.<br>3. Cho game gốc thực hiện thêm một nước đi hợp lệ.<br>4. Kiểm tra lịch sử của cả game gốc và game copy. |
| **Kết quả mong đợi (Expected Output)** | - Game gốc có 2 dòng lịch sử sau nước đi mới.<br>- Game copy vẫn giữ 1 dòng lịch sử tại thời điểm sao chép.<br>- Dòng lịch sử trong game copy chứa `e2->e4`. |
| **Kết quả thực tế (Actual Output)** | - Game copy giữ lịch sử độc lập, không bị ảnh hưởng khi game gốc phát sinh thêm nước đi mới. |
| **Trạng thái** | Đạt (Pass) |

---

### 8. Test Case TC-HM-008: Lịch sử trong chế độ AI ghi nhãn Player và AI

| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-HM-008` |
| **Tên Test Case** | Kiểm tra lịch sử trong chế độ AI phân biệt nước Player và nước AI |
| **Chức năng** | Ghi lịch sử ở chế độ AI (`setAiMode`, `makeMove`, `makeMoveForAI`) |
| **Tiền điều kiện** | Game được bật chế độ AI bằng `game.setAiMode(true)`. |
| **Dữ liệu đầu vào (Input Data)** | - **Nước Player:** Tốt Trắng `e2->e4` (`Position(6, 4)` đến `Position(4, 4)`).<br>- **Nước AI:** Tốt Đen `e7->e5` (`Position(1, 4)` đến `Position(3, 4)`). |
| **Các bước thực hiện** | 1. Bật AI mode bằng `game.setAiMode(true)`.<br>2. Gọi `game.makeMove(...)` cho nước của người chơi.<br>3. Gọi `game.makeMoveForAI(...)` cho nước của máy.<br>4. Kiểm tra số dòng và nội dung lịch sử. |
| **Kết quả mong đợi (Expected Output)** | - Lịch sử có đúng 2 dòng.<br>- Dòng đầu bắt đầu bằng `Player White` và chứa `e2->e4`.<br>- Dòng thứ hai bắt đầu bằng `AI Black` và chứa `e7->e5`. |
| **Kết quả thực tế (Actual Output)** | - Lịch sử ghi đủ 2 dòng và phân biệt đúng nhãn `Player`/`AI`. |
| **Trạng thái** | Đạt (Pass) |
