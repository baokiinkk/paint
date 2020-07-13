# Đồ Án Kỹ Thuật Đồ Họa
#### Giảng Viên: Dương Thanh Thảo
![Authors](https://github.com/baokiinkk/paint/blob/master/Authors.png)
### Thực Hiện:
- **Hồ Minh Quốc Bảo** 
- **Lê Khắc Duy** 
- **Nguyễn Đức Khải**
- **Trần Nguyễn Chí Nhân** 
- **Phạm Nhật Quan**

### Nội Dung
#### I. Yêu cầu
- **Hệ tọa độ:**
    - Cho phép người dùng chọn hệ tọa độ 2D, 3D và vẽ hệ tọa độ lên màn hình, mỗi đơn vị tương ứng 5 pixel.
- **Vẽ trong hệ tọa độ 2D:**
    - Tự động (hoặc cho người dùng chọn)  vẽ ít nhất 2 vật cơ bản.
    - Mỗi vật được tạo bởi ít nhất 2 hình cơ sở khác nhau. 
    - Có ít nhất 4 hình cơ sở được vẽ trong sản phẩm. Thực hiện các phép biến đổi (tịnh tiến, quay, đối xứng, thu phóng) trên các hình để tạo hiệu ứng chuyển động.
    - Hiển thị tọa độ của các hình cơ sở trên hệ tọa độ người dùng.
- **Vẽ trong hệ tọa độ 3D:**
    - Cho phép người dùng chọn hình ảnh cơ bản cần vẽ: hình cầu, hình hộp chữ nhật, hình hộp vuông, hình trụ,… trong hệ tọa độ 3D 
    - Dùng thuật toán Cavalier hoặc Cabinet để vẽ các đối tượng trong hệ tọa độ 3D dựa vào các thông số người dùng nhập vào. 
    - Ví dụ: Hình cầu (tọa độ tâm, bán kính), hình hộp chữ nhật (tọa độ đỉnh dưới bên trái, chiều dài, chiều rộng, chiều cao), hình trụ (chiều cao, tâm đáy, bán kính đáy).

#### II. Một số chức năng bổ sung
Nhóm quyết định thực hiện **Đồ Án** giống một chương trình vẽ và lấy ứng dụng **Paint** trên **Window** để làm mẫu. Do đó, **Đồ Án** có một số chức năng không có trong yêu cầu.

- Chức năng cơ bản:
  - Undo/Redo các tác vụ.
  - Lưu/Mở ảnh.
  - Chuyển đổi giữa vẽ 2D và 3D, hiển thị lưới tọa độ, tọa độ của hình.
  - Vẽ các hình cơ bản 2D/3D, bằng thao tác kéo thả. Riêng với 3D có thêm mục Custom để nhập các thông số của hình.
  - Chọn màu cho các nét vẽ.
  - Tô màu, xóa (cục tẩy), xóa tất cả.
  - ...
- Chức năng bổ sung:
  - Đối xứng hình qua điểm, gốc tọa độ, trục tọa độ.
  - Vẽ nhiều hình đối xứng qua 1 điểm.
  - Xoay, di chuyển hình vừa vẽ.
  - Cài đặt ẩn/hiện lưới pixel, chọn màu cho lưới pixel.
  - Chạy hình chuyển động, hình này được tạo từ các hình cơ sở kết hợp với các phép biến đổi hình. Gồm có: Chuyển động Pháo Hoa, Chuyển động Xe Chạy.
  - Load ảnh động (Đang phát triển) dùng để đọc và in ra màn hình 1 loạt các hình ảnh giống một bộ phim. Hiện tại chức năng này sẽ đọc các frame hình có sẵn trong folder đồ án. Dự kiến sẽ phát triển để đọc 1 file Gif tự chọn.

#### III. Ảnh Demo
![2D](https://github.com/baokiinkk/paint/blob/master/2D.png)

![3D](https://github.com/baokiinkk/paint/blob/master/3D.png)

![Impossible Triangle](https://github.com/baokiinkk/paint/blob/master/ImpossibleTri.png)

![Flower](https://github.com/baokiinkk/paint/blob/master/Flower.png)



 






