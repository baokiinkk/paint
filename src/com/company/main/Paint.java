package com.company.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.company.xuli.xuliduongve.*;
import com.company.xuli.xuliduongve.MyFunction;
import static com.company.Button.*;

public class Paint extends JFrame implements ActionListener, ItemListener {

    // hàm chính
    public void run() {
        this.setContentPane(activity_main);// liên kết với màn hình form
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close
        this.setTitle("Paint V1.0");
        this.setVisible(true); // set hiện hay k
        this.setSize(1280,800);
        this.setResizable(false);
        pencilButton.addActionListener(this);
        colorButton.addActionListener(this);
        axisCheckBox.addItemListener(this);
        MyFunction.clearArr(drawingBoard);
    }

    // hàm custom cho các thành phần trong form
    private void createUIComponents() {
        drawArea = new Board(nextDrawing,nextPoint,drawingBoard, Width, Height, spacing,rectSize);
        drawArea.addMouseListener(new Click());
        drawArea.addMouseMotionListener(new Move());

        //axisCheckBox.setSelected(true);
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        if (itemEvent.getSource() == axisCheckBox)
        {
            if(axisCheckBox.isSelected())
            {
                ((Board) drawArea).showAxis();
            }
            else
            {
                //drawArea.hideAxis();
                ((Board) drawArea).hideAxis();
            }
            repaint();
        }
    }


    // click chuột
    public  class Click implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            switch (choose)
            {
                case PENCIL: // vẽ điểm
                {
                    xStart = (mouseEvent.getX()-spacing)/rectSize;
                    yStart= (mouseEvent.getY()-spacing)/rectSize;
                    MyFunction.setPoint(drawingBoard, xStart, yStart, chooseColor);
                    repaint();
                    break;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    public class Move implements MouseMotionListener
    {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            coordX = mouseEvent.getX()/rectSize - Width/2;
            coordY = mouseEvent.getY()/rectSize - Height/2;
            showX.setText("X: " + coordX);
            showY.setText("Y: " + coordY*(-1));
            //System.out.println(coordX + " " + coordX);
        }
    }


    // chọn nút
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String nameButton = actionEvent.getActionCommand();
        System.out.println(nameButton);
        switch (nameButton) {
            case "Pencil": {
                xStart = -1;
                yStart = -1;
                choose = PENCIL;
                MyFunction.clearArr(nextPoint);
                System.out.println(choose);
                break;
            }
            case "Color":
            {
                Color c = JColorChooser.showDialog(this,"choose color",Color.RED);
                if(c != null)
                    chooseColor = c;
                //System.out.println(colors);
                colorBox.setBackground(chooseColor);
                colorBox.setForeground(chooseColor);
                break;
            }
        }
    }


    //Color colors = Color.BLACK;
    //
    // size ảnh
    private static int Width = 189;     //225
    private static int Height = 129;    //155

    //tọa độ mouse
    private int mX = -1;
    private int mY = -1;

    //tọa độ đổi qua hệ tọa độ có âm
    //private Point xOy = new Point();
    private int coordX = 0;
    private int coordY = 0;

    //kích thước nét vẽ
    private int sizeLine = 1;
    //kich thuoc pixel va khoang cách giữa các pixel
    private int spacing = 1;
    private int rectSize = 6;

    // trạng thái nút đang chọn
    private com.company.Button choose = MOUSE;

    // biến phụ vị trí trỏ chuột
    private int xStart=-1;
    private int yStart=-1;
    // biến xác định click chuột đầu giữa 2 lần click, (click để chọn)
    private boolean firstClick = false;

    //bảng trạng thái các pixel
    private Color[][] drawingBoard = new Color[Width][Height]; //129x189

    //biến màu đang chọn
    private Color chooseColor = Color.BLACK;

    // chứa các điểm đã set
    private Color[][] nextPoint = new Color[Width][Height]; //150x217
    private boolean[][] nextDrawing = new boolean[Width][Height]; //150x217
    // chứa các điểm dùng để quay lại
    private Color[][] undoPoint = new Color[Width][Height]; //150x217

    // khai báo biến
    private JPanel activity_main;
    private JPanel leftPanel;
    private JButton lineButton;
    private JPanel mainArea;
    private JPanel drawArea;
    private JButton clearButton;            // xóa sạch
    private JButton pencilButton;           // đè là vẽ
    private JButton undoButton;             // xóa thao tác vừa làm
    private JButton rectangleButton;        // vẽ hình chữ nhật
    private JButton colorButton;            // chưa nghĩ ra
    private JButton mouseButton;            // nút vô dụng nhất, không có gì cả
    private JButton paintButton;            // tô màu, thay thế vùng pixel được chọn thành màu
    private JButton colorBox;              // chưa nghĩ ra
    private JButton button1;
    private JButton button2;
    private JLabel showSize;
    private JCheckBox axisCheckBox;
    private JLabel showX;
    private JLabel showY;
}
