package com.company.xuli.xuliduongve;

import com.company.Button;

import java.awt.*;

public class MuiTen extends HinhHoc {
    private Point2D A;
    private Point2D B;
    private Point2D C;
    private Point2D D;
    private Point2D Left;
    private Point2D Right;
    private Point2D Top;
    private lineMode MODE;
    Point2D st, en;

    // khởi tạo hình chữ nhật
    public MuiTen(boolean[][] nextDrawing, Color[][] nextPoint, Color chooseColor) {
        super(nextDrawing, nextPoint, chooseColor);
        tag = Button.RECTANGLE;
        A = new Point2D();
        B = new Point2D();
        C = new Point2D();
        D = new Point2D();
        st = new Point2D();
        en = new Point2D();
        Left = new Point2D();
        Right = new Point2D();
        Top = new Point2D();
    }

    // set 4 điểm hình chữ nhật
    public void setMuiTen(Point2D Mid, float rand) {
        st.set(Mid.X-rand,Mid.Y-rand*3);
        en.set(Mid.X+rand,Mid.Y+rand*3);
        A.set(st.X, st.Y);              // (start) A-----------------B
        B.set(en.X, st.Y);                //         |                 |
        C.set(en.X, en.Y);                  //         |                 |
        D.set(st.X, en.Y);                //         D-----------------C  (end)
        System.out.println(en.X + "-" + en.Y);
        Left.set(st.X-(en.X-st.X)/2,st.Y);
        Right.set(en.X+(en.X-st.X)/2,st.Y);
        Top.set(Mid.X,Mid.Y-rand*6);
    }

    // set đầy đủ thông hình chữ nhật, gồm loại nét vẽ, các đỉnh, và tâm HCN

    // xoay và vẽ hình hiện tại 1 góc alpha
    // đây là xoay nháp, tức là chỉ xoay hình ảo để người dùng canh góc xoay, khi nhả chuột sẽ áp dụng vào hình gốc

    // khi xoay nháp sẽ có góc alpha lệch so với góc ban đầu, khi xoay xong, hàm này sẽ + góc vừa xoay vào góc hiện tại
    // ví dụ xoay hình gốc 1 góc 20 độ, thì sau khi xoay nháp xong, góc xoay gốc sẽ được cộng thêm 20 độ

    // vẽ hình chữ nhật dựa vào 4 điểm
    public void draw() {
        super.MidpointLine(Left,Right,lineMode.DEFAULT);
        super.MidpointLine(Left,Top,lineMode.DEFAULT);
        super.MidpointLine(Right,Top,lineMode.DEFAULT);
        super.MidpointLine(A, B, lineMode.DEFAULT);
        super.MidpointLine(B, C, lineMode.DEFAULT);
        super.MidpointLine(C, D, lineMode.DEFAULT);
        super.MidpointLine(D, A, lineMode.DEFAULT);

        for(int i=st.X+1;i<en.X;i++){
            for(int j = st.Y+1;j<en.Y;j++){
                if (MyFunction.isSafe(nextPoint, i, j)) {
                    nextDrawing[i][j] = true;
                    nextPoint[i][j] = chooseColor;
                }
            }
        }
        MyFunction.paintColor(nextPoint, nextDrawing, new Point2D(Top.X,Top.Y+1), chooseColor);

    }
}
