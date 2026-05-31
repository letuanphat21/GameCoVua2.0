# BẢNG TẢ KỊCH BẢN KIỂM THỬ (TEST CASES) - DI CHUYỂN QUÂN VUA (KING)

## 📌 Tổng quan về Luật di chuyển của Quân Vua (King)
Quân Vua trong trò chơi cờ vua (được định nghĩa trong lớp `covua.chess.King`) có các quy tắc di chuyển chính sau:
1. **Di chuyển thông thường:** Vua có thể di chuyển đến bất kỳ ô trống nào xung quanh nó (ngang, dọc, chéo) với khoảng cách tối đa là **1 ô**, miễn là ô đó không bị chặn bởi quân đồng minh.
2. **Ăn quân:** Vua có thể ăn quân của đối phương (ngoại trừ quân Vua đối phương) nếu quân đó nằm trong tầm di chuyển 1 ô của nó.
3. **Nhập thành (Castling):** Vua di chuyển 2 ô về phía Xe cùng màu trên cùng hàng xuất phát, với điều kiện các ô trung gian giữa chúng phải trống và Xe ở đúng vị trí.

Dưới đây là **5 kịch bản kiểm thử chi tiết (Test Cases)** kèm theo kết quả thực tế thu được từ mã nguồn hiện tại của dự án.

---

### 📊 KẾT QUẢ THỰC THI KIỂM THỬ (TEST EXECUTION RESULTS)
> **Trạng thái tổng quát:** Tất cả 5 test cases đều chạy thành công và đạt kết quả mong đợi.

| Tổng số Test Cases | Số lượng Đạt (Pass) | Số lượng Lỗi (Fail) | Tỷ lệ thành công |
| :---: | :---: | :---: | :---: |
| 5 | 5 | 0 | 100% |

---

### 📋 DANH SÁCH CHI TIẾT CÁC TEST CASES

#### 1. Test Case TC-001: Vua di chuyển 1 ô hợp lệ đến ô trống (Normal Move)
| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-001` |
| **Tên Test Case** | Kiểm thử di chuyển thông thường của Vua 1 ô tới ô trống |
| **Chức năng** | Di chuyển quân Vua (King.isValidMove) |
| **Tiền điều kiện** | Vua Trắng ở vị trí xuất phát hoặc vị trí bất kỳ, xung quanh không có quân cản. |
| **Dữ liệu đầu vào (Input Data)** | - **Trạng thái Bàn cờ:** Trống, ngoại trừ quân Vua Trắng.<br>- **Quân cờ kiểm thử:** Vua Trắng (White King)<br>- **Vị trí hiện tại (Source):** `e4` (Hàng 4, Cột 4 - `Position(4, 4)`)<br>- **Vị trí đích (Destination):** `e5` (Hàng 3, Cột 4 - `Position(3, 4)`) - đi lên 1 ô |
| **Các bước thực hiện** | 1. Khởi tạo bàn cờ với Vua Trắng ở vị trí `e4`.<br>2. Gọi phương thức `king.isValidMove(new Position(3, 4), board)`.<br>3. Kiểm tra kết quả trả về. |
| **Kết quả mong đợi (Expected Output)** | - Kết quả trả về: `true`<br>- Quân Vua có thể di chuyển hợp lệ sang ô `e5`. |
| **Kết quả thực tế (Actual Output)** | - Kết quả trả về: `true`<br>- Khớp hoàn toàn với kết quả mong đợi. |
| **Trạng thái** | ✅ Đạt (Pass) |

---

#### 2. Test Case TC-002: Vua di chuyển sai luật - Di chuyển quá 1 ô (Invalid Distance Move)
| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-002` |
| **Tên Test Case** | Kiểm thử Vua di chuyển quá 1 ô (không hợp lệ) |
| **Chức năng** | Di chuyển quân Vua (King.isValidMove) |
| **Tiền điều kiện** | Vua Trắng ở vị trí trung tâm. |
| **Dữ liệu đầu vào (Input Data)** | - **Trạng thái Bàn cờ:** Trống, ngoại trừ quân Vua Trắng.<br>- **Quân cờ kiểm thử:** Vua Trắng (White King)<br>- **Vị trí hiện tại (Source):** `e4` (Hàng 4, Cột 4 - `Position(4, 4)`)<br>- **Vị trí đích (Destination):** `e6` (Hàng 2, Cột 4 - `Position(2, 4)`) - đi lên 2 ô |
| **Các bước thực hiện** | 1. Khởi tạo bàn cờ với Vua Trắng ở vị trí `e4`.<br>2. Gọi phương thức `king.isValidMove(new Position(2, 4), board)`.<br>3. Kiểm tra kết quả trả về. |
| **Kết quả mong đợi (Expected Output)** | - Kết quả trả về: `false`<br>- Quân Vua không được phép di chuyển và phải giữ nguyên vị trí cũ. |
| **Kết quả thực tế (Actual Output)** | - Kết quả trả về: `false`<br>- Khớp hoàn toàn với kết quả mong đợi. |
| **Trạng thái** | ✅ Đạt (Pass) |

---

#### 3. Test Case TC-003: Vua ăn quân đối phương hợp lệ (Capture Move)
| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-003` |
| **Tên Test Case** | Kiểm thử Vua ăn quân đối phương nằm trong phạm vi 1 ô |
| **Chức năng** | Di chuyển quân Vua & Bắt quân (King.isValidMove & canCapture) |
| **Tiền điều kiện** | Có quân đối phương nằm ngay cạnh quân Vua. |
| **Dữ liệu đầu vào (Input Data)** | - **Trạng thái Bàn cờ:** Có quân Tốt Đen (Black Pawn) tại `d5`.<br>- **Quân cờ kiểm thử:** Vua Trắng (White King) ở `e4` (`Position(4, 4)`) và Tốt Đen (Black Pawn) ở `d5` (`Position(3, 3)`) - nằm chéo 1 ô.<br>- **Vị trí đích (Destination):** `d5` (Hàng 3, Cột 3 - `Position(3, 3)`) |
| **Các bước thực hiện** | 1. Khởi tạo bàn cờ với Vua Trắng ở `e4` và Tốt Đen ở `d5`.<br>2. Gọi phương thức `king.isValidMove(new Position(3, 3), board)`.<br>3. Kiểm tra kết quả trả về. |
| **Kết quả mong đợi (Expected Output)** | - Kết quả trả về: `true` (vì Tốt Đen khác màu và không phải là King).<br>- Vua di chuyển thành công tới `d5` và quân Tốt Đen bị loại khỏi bàn cờ. |
| **Kết quả thực tế (Actual Output)** | - Kết quả trả về: `true`<br>- Khớp hoàn toàn với kết quả mong đợi. |
| **Trạng thái** | ✅ Đạt (Pass) |

---

#### 4. Test Case TC-004: Vua bị cản bởi quân đồng minh (Friendly Block Move)
| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-004` |
| **Tên Test Case** | Kiểm thử di chuyển quân Vua đè lên quân đồng minh (không hợp lệ) |
| **Chức năng** | Di chuyển quân Vua (King.isValidMove) |
| **Tiền điều kiện** | Có quân cùng màu nằm ngay sát quân Vua. |
| **Dữ liệu đầu vào (Input Data)** | - **Trạng thái Bàn cờ:** Có Tốt Trắng (White Pawn) tại `e5`.<br>- **Quân cờ kiểm thử:** Vua Trắng (White King) ở `e4` (`Position(4, 4)`) và Tốt Trắng (White Pawn) ở `e5` (`Position(3, 4)`) - nằm thẳng phía trên 1 ô.<br>- **Vị trí đích (Destination):** `e5` (Hàng 3, Cột 4 - `Position(3, 4)`) |
| **Các bước thực hiện** | 1. Khởi tạo bàn cờ với Vua Trắng ở `e4` và Tốt Trắng ở `e5`.<br>2. Gọi phương thức `king.isValidMove(new Position(3, 4), board)`.<br>3. Kiểm tra kết quả trả về. |
| **Kết quả mong đợi (Expected Output)** | - Kết quả trả về: `false` (vì ô đích chứa quân cùng màu).<br>- Di chuyển bị chặn lại, Vua giữ nguyên vị trí ở `e4`. |
| **Kết quả thực tế (Actual Output)** | - Kết quả trả về: `false`<br>- Khớp hoàn toàn với kết quả mong đợi. |
| **Trạng thái** | ✅ Đạt (Pass) |

---

#### 5. Test Case TC-005: Nhập thành cánh Vua hợp lệ (Valid King-side Castling)
| Mục | Nội dung chi tiết |
| :--- | :--- |
| **Mã Test Case** | `TC-005` |
| **Tên Test Case** | Kiểm thử tính năng nhập thành cánh Vua (King-side Castling) |
| **Chức năng** | Nhập thành của Vua (Castling Logic trong King.isValidMove) |
| **Tiền điều kiện** | Vua Trắng ở vị trí ban đầu `e1`, Xe Trắng ở vị trí ban đầu `h1`, các ô giữa trống (`f1`, `g1` đều `null`). |
| **Dữ liệu đầu vào (Input Data)** | - **Trạng thái Bàn cờ:** Vua Trắng ở `e1` (`Position(7, 4)`), Xe Trắng ở `h1` (`Position(7, 7)`). Các ô `f1` và `g1` rỗng.<br>- **Vị trí đích di chuyển của Vua:** `g1` (Hàng 7, Cột 6 - `Position(7, 6)`) |
| **Các bước thực hiện** | 1. Khởi tạo bàn cờ với Vua Trắng ở `e1` và Xe Trắng ở `h1`. Đảm bảo các ô `f1` và `g1` bằng `null`.<br>2. Gọi phương thức `king.isValidMove(new Position(7, 6), board)`.<br>3. Kiểm tra kết quả trả về. |
| **Kết quả mong đợi (Expected Output)** | - Kết quả trả về: `true`<br>- Nước đi nhập thành hợp lệ. |
| **Kết quả thực tế (Actual Output)** | - Kết quả trả về: `true`<br>- Khớp hoàn toàn với kết quả mong đợi. |
| **Trạng thái** | ✅ Đạt (Pass) |
