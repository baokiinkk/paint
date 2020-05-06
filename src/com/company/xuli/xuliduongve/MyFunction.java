package com.company.xuli.xuliduongve;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import javafx.util.Pair;

public class MyFunction {
    // duyệt 4 vị trí kề cạnh 1 điểm
    public static int[] XX = {-1, 0, 0, 1};
    public static int[] YY = {0, -1, 1, 0};

    // code xử lí với pixel
    static void setPoint(Color[][] board, int cordX, int cordY, Color color) {
        board[cordX][cordY] = color;
    }

    // bỏ chọn pixel đó
    static void clearPoint(Color[][] board, int cordX, int cordY) {
        board[cordX][cordY] = Color.WHITE;
    }


    public static boolean chooseMode(int point, lineMode MODE) {
        switch (MODE) {
            case DEFAULT:
            case ARROW: {
                return true;
            }
            case DASH: {
                return (point % 5 != 0);
            }
            case DOT: {
                return (point % 2 != 0);
            }
            case DASHDOT: {
                return (point % 6 != 4 && point % 6 != 0);
            }
            case DASHDOTDOT: {
                return (point % 9 != 5 && point % 9 != 7 && point % 9 != 0);
            }
        }
        return false;
    }


    // gán toàn bộ mảng thảnh màu trắng
    public static void clearArr(Color[][] board) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] = Color.WHITE;
    }

    // gán toàn bộ mảng thành false
    public static void clearArr(boolean[][] board)
    {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] = false;
    }

    // kiểm tra vị trí X, Y có nằm trong mảng hay k, tránh Runtime
    public static boolean isSafe(Color[][] board, int cordX, int cordY)
    {
        return (cordX >= 0 && cordX < board.length && cordY >= 0 && cordY < board[0].length);
    }

    // copy mảng màu Source sang mảng Des
    public static void storePointColor(Color[][] sourceColor, Color[][] desColor)
    {
        for (int i = 0; i < sourceColor.length; i++)
            for (int j = 0; j < sourceColor[0].length; j++)
            {
                    desColor[i][j] = new Color(sourceColor[i][j].getRGB());
            }
    }

    // trộn mảng source vào mảng des, với điều kiện chỉ những pixel nào được đánh dấu ở mảng set mới được ghi đè
    public static void mergePointColor(Color[][] sourceColor, boolean[][] sourceSet, Color[][] desColor)
    {
        for (int i = 0; i < sourceColor.length; i++)
            for (int j = 0; j < sourceColor[0].length; j++)
            {
                if (sourceSet[i][j]) {
                    desColor[i][j] = new Color(sourceColor[i][j].getRGB());
                }
            }
    }

    // dùng loang để tô màu vùng có màu [x][y] bằng màu được chọn
    public static void paintColor(Color[][] sourceColor, boolean[][] sourceSet, int cordX, int cordY, Color color)
    {
        // dùng hàng đợi để khử đệ quy loang
        Queue <Pair<Integer, Integer>> myQ = new LinkedList<>();

        // oldColor là màu của vùng được tô, những pixel nào nằm kệ có màu oldColor sẽ được thay bằng color
        Color oldColor = sourceColor[cordX][cordY];

        // chỉ khi nào màu mới và màu cũ khác nhau thì mới loang
        if (!oldColor.equals(color))
        {
            sourceSet[cordX][cordY] = true;         // đánh dấu ô màu cũ, để sau này có thể merge được
            sourceColor[cordX][cordY] = color;      // tô ô màu cũ = màu mới

            // thêm ô màu vào hàng đợi để loang sang các ô khác
            myQ.add(new Pair<Integer, Integer>(cordX, cordY));
            // biến vị trí phụ để tính vị trí các ô sắp loang
            int tmpX = -1;
            int tmpY = -1;
            while(myQ.size() > 0)
            {
                // lấy 1 ô ra khỏi hàng đợi, duyệt và tính 4 vị trí kề nó
                Pair<Integer, Integer> tmpCord = myQ.poll();
                for (int move = 0; move < 4; move++)
                {

                    tmpX = tmpCord.getKey() + XX[move];
                    tmpY = tmpCord.getValue() + YY[move];

                    // nếu vị trí kề tiếp theo an toàn và có màu cũ -> tô màu nó sang màu mới và bỏ vào hàng đợi để tí loang 4 ô cạnh nó
                    if (isSafe(sourceColor, tmpX, tmpY) && sourceColor[tmpX][tmpY].equals(oldColor))
                    {
                        //System.out.println("Added");
                        sourceSet[tmpX][tmpY] = true;
                        sourceColor[tmpX][tmpY] = color;
                        myQ.add(new Pair<>(tmpX, tmpY));
                    }
                }
            }
        }
    }

}
