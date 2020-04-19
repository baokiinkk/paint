package com.company;

import java.awt.Color;

public class myFunction {
    // code xử lí với pixel
    static void setPoint(Color[][] board, int cordX, int cordY, Color color)
    {
        board[cordX][cordY] = color;
    }
    // bỏ chọn pixel đó
    static void clearPoint(Color[][] board, int cordX, int cordY)
    {
        board[cordX][cordY] = Color.WHITE;
    }

    static void clearArr(Color[][] board)
    {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] = Color.WHITE;
    }

    static void clearArr(boolean[][] board)
    {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] = false;
    }


    static boolean isSafe(Color[][] board, int cordX, int cordY)
    {
        return (cordX >= 0 && cordX < board.length && cordY >= 0 && cordY < board[0].length);
    }

    static void mergePointColor(Color[][] sourceColor, boolean[][] sourceSet, Color[][] desColor)
    {
        for (int i = 0; i < sourceColor.length; i++)
            for (int j = 0; j < sourceColor[0].length; j++)
            {
                if (sourceSet[i][j]) {
                    desColor[i][j] = new Color(sourceColor[i][j].getRGB());
                }
            }
    }

}
