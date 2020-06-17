package com.company.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.company.Animation2D.Firework;
import com.company.Button;
import com.company.xuli.xuliduongve.*;
import com.company.xuli.xuliduongve.Rectangle;
import com.company.xuli.xuliduongve.HopChuNhat;

import static com.company.Button.*;

public class Paint extends JFrame implements ActionListener {

    // ================================== CÁC BIẾN ĐƠN ==========================
    private static int Width = 272;     // độ rộng bảng vẽ
    private static int Height = 185;    // độ cao

    // click chuột
    public class Click implements MouseListener {

        // click chuột vào trạng thái nào
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            switch (choose) {
                case LINE: // vẽ đường thẳng
                {
                    // nothing
                    break;
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                switch (choose) {
                    case PENCIL: {
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        nextDrawing[mouseXY.X][mouseXY.Y] = true;
                        nextPoint[mouseXY.X][mouseXY.Y] = chooseColor;
                        startXY.set(mouseXY);
                        drawArea.repaint();
                        break;
                    }
                    case LINE: // vẽ đường thẳng
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                        xStart = mouseEvent.getX() / rectSize;
//                        yStart = mouseEvent.getY() / rectSize;
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case RECTANGLE: // vẽ hinh cn
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case ZIGZAG: // vẽ hinh cn
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case ERASE: // vẽ cục gôm
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case PAINT: {
                        System.out.println("Pressed");
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.paintColor(nextPoint, nextDrawing, mouseXY, chooseColor);
                        break;
                    }
                    case CIRCLE: {
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case ELLIPSE: {
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case ROTATE: {
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        Board.previousDo();
                        break;
                    }
                }
            } else {
//                MyFunction.storePointColor(drawingBoard, nextPoint);
//                MyFunction.clearArr(nextDrawing);
//                repaint();
            }

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                switch (choose) {
                    case PENCIL: // vẽ đường thẳng
                    {
                        //nothing
                        startXY.set(-1, -1);
                        Board.applyNow();
                        break;
                    }
                    case LINE: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case RECTANGLE: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case ZIGZAG: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case PAINT: {
                        Board.applyNow();
                        MyFunction.storePointColor(drawingBoard, nextPoint);
                        repaint();
                        break;
                    }
                    case CIRCLE:{
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case ELLIPSE: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case ERASE: {
                        Board.applyNow();
                        break;
                    }
                    case ROTATE: {
                        Board.applyRotate(startXY, mouseXY);
                        Board.applyNow();
                        break;
                    }
                }

                undoButton.setEnabled(Board.ableUndo());
                redoButton.setEnabled(Board.ableRedo());
            }
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }


    }



    // hàm custom cho các thành phần trong form
    private void createUIComponents() {
        drawArea = new Board(nextDrawing, nextPoint, drawingBoard, Width, Height, spacing, rectSize);
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        comboBox1 = new JComboBox<lineMode>(lineModeArr);
        mouseXY = new Point2D(-1, -1);
        startXY = new Point2D(-1, -1);
        timer = new Timer(0, null);
    }

    //biến chứa chế độ đường thẳng đang chọn
    private lineMode chooseLineMode = lineMode.DEFAULT;
    // danh sách các loại nét vẽ
    private lineMode[] lineModeArr = {lineMode.DEFAULT, lineMode.DASH, lineMode.DOT, lineMode.DASHDOT, lineMode.DASHDOTDOT, lineMode.ARROW};
    // =========================== CÁC BIẾN LIÊN QUAN TỚI CHUỘT=================
    // khi kéo thả, startXY là điểm đầu tiên click chuột vào, mouseXY là điểm hiện tại
    private Point2D startXY;
    // biến xác định click chuột đầu trong 2 lần click
    private boolean firstClick = false;
    private Point2D mouseXY;
    private boolean playingAnimation = false;
    private Timer timer;
    private int rectSize = 4;          // tổng của kích thước pixel và spacing
    private int spacing = 1;           // khoảng cách giữa 2 pixel
    private int sizeLine = 1;          // kích thước nét vẽ
    private Color chooseColor = Color.BLACK;    // màu hiện tại đang chọn
    private com.company.Button choose = PENCIL; // nút vừa chọn
    private boolean currentBoardState = true;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show2DAxis = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show3DAxis = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show2DCoord = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show3DCoord = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    // =================================== CÁC LOẠI NÚT ============================
    private JButton animationButton;        // nút mở hoạt cảnh

    // hàm chính
    public void run() {
        this.setContentPane(activity_main);// liên kết với màn hình form
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close
        this.setTitle("Paint V1.0");
        this.setSize(1366, 740);
        this.setResizable(false);
        //this.setMaximumSize(new Dimension(1280, 800));
        this.setLocationRelativeTo(null);
        this.setVisible(true); // set hiện hay k

        clearButton.addActionListener(this);
        lineButton.addActionListener(this);
        pencilButton.addActionListener(this);
        redoButton.addActionListener(this);
        colorButton.addActionListener(this);
        undoButton.addActionListener(this);
        paintButton.addActionListener(this);
        rectangleButton.addActionListener(this);
        zigzagButton.addActionListener(this);
        ellipseButton.addActionListener(this);
        rotateButton.addActionListener(this);
        circleButton.addActionListener(this);
        ellipseButton.addActionListener(this);
        settingButton.addActionListener(this);
        eraseButton.addActionListener(this);
        animationButton.addActionListener(this);
        Import.addActionListener(this);
        Export.addActionListener(this);
//
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chooseLineMode = lineModeArr[comboBox1.getSelectedIndex()];
            }
        });
        //sizeSlider.addChangeListener((ChangeListener) this);
        MyFunction.clearArr(drawingBoard);
        MyFunction.clearArr(undoPoint);
    }

    // click buttonaq
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String nameButton = "";
        if (!playingAnimation) {
            nameButton = actionEvent.getActionCommand();
        } else {
            if (actionEvent.getActionCommand().equals("Animation")) {
                nameButton = "Animation";
            }
        }
        System.out.println(nameButton);
        switch (nameButton) {

            case "Redo": {
                Board.redo();
                undoButton.setEnabled(Board.ableUndo());
                redoButton.setEnabled(Board.ableRedo());
                repaint();
                break;
            }
            case "Clear": {
                //choose = Button.CLEAR;
                MyFunction.clearArr(drawingBoard);
                MyFunction.clearArr(nextPoint);
                repaint();
                break;
            }
            case "Line": {
                startXY.set(-1, -1);
//                xStart = -1;
//                yStart = -1;
                choose = LINE;
                break;
            }
            case "Rectangle": {
                startXY.set(-1, -1);
//                xStart = -1;
//                yStart = -1;
                choose = RECTANGLE;
                break;
            }
            case "Zigzag": {
                startXY.set(-1, -1);
//                xStart = -1;
//                yStart = -1;
                choose = ZIGZAG;
                break;
            }
            case "Erase": {
                startXY.set(-1, -1);
                choose = ERASE;
                break;
            }
            case "Circle": {
                //startXY.set(-1, -1);
                startXY.X = -1;
                startXY.Y = -1;
//                xStart = -1;
//                yStart = -1;
                choose = Button.CIRCLE;
                break;
            }
            case "Pencil": {
                //startXY.set(-1, -1);
                startXY.X = -1;
                startXY.Y = -1;
//                xStart = -1;
//                yStart = -1;
                choose = PENCIL;
                MyFunction.clearArr(nextPoint);
//                System.out.println(choose);
                break;
            }
            case "Color": {
                Color c = JColorChooser.showDialog(this, "Choose Color", chooseColor);
                if (c != null)
                    chooseColor = c;
                //System.out.println(colors);
                colorBox.setBackground(chooseColor);
                colorBox.setForeground(chooseColor);

                break;
            }
            case "Undo": {
                Board.undo();
                undoButton.setEnabled(Board.ableUndo());
                redoButton.setEnabled(Board.ableRedo());
                repaint();
                break;
            }
            case "Paint": {
                choose = PAINT;
                MyFunction.storePointColor(drawingBoard, nextPoint);
                MyFunction.clearArr(nextDrawing);
                break;
            }
            case "Ellipse": {
                startXY.set(-1, -1);
//                xStart = -1;
//                yStart = -1;
                choose = ELLIPSE;
                break;
            }
            case "Rotate": {
                startXY.set(-1, -1);
                choose = ROTATE;
//                sizeLine++;
//                showSize.setText("Size: " + sizeLine);
                break;
            }
            case "Setting": {
                System.out.println("Yes");
                Setting dialog = new Setting(currentBoardState);
                dialog.setChecker(show2DAxis, show2DCoord, show3DAxis, show3DCoord);
                dialog.pack();
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
                show2DAxis = dialog.getShow2DAxis();
                show3DAxis = dialog.getShow3DAxis();
                show2DCoord = dialog.getShow2DCoord();
                show3DCoord = dialog.getShow3DCoord();
                if (currentBoardState != dialog.getState()) {
                    currentBoardState = dialog.getState();
                    ((Board) drawArea).setBoardState(currentBoardState);
                    MyFunction.clearArr(drawingBoard);
                    MyFunction.clearArr(nextPoint);
                    MyFunction.clearArr(nextDrawing);
                }
                if (currentBoardState) {
                    ((Board) drawArea).setShowAxis(show2DAxis);
                    ((Board) drawArea).setShowCoord(show2DCoord);
                } else {
                    ((Board) drawArea).setShowAxis(show3DAxis);
                    ((Board) drawArea).setShowCoord(show3DCoord);
                }

                repaint();
                System.out.println(show2DAxis);
                break;
            }
            case "Imp": {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "PNG Images", "png");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: " +
                            chooser.getSelectedFile().getName());
                    try {
                        Board.applyNow();
                        BufferedImage myNewPNGFile = ImageIO.read(new File(chooser.getSelectedFile().getAbsolutePath()));
                        if (myNewPNGFile.getHeight() == Height * 3 && myNewPNGFile.getWidth() == Width * 3) {
                            for (int i = 0; i < drawingBoard.length; i++) {
                                for (int j = 0; j < drawingBoard[0].length; j++) {
                                    Color c = new Color(myNewPNGFile.getRGB(i * 3, j * 3), true);
                                    drawingBoard[i][j] = c;
                                }
                            }
                            repaint();
                        } else {
                            System.out.println("Wrong!");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                break;
            }
            case "Exp": {

                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "PNG Images", "png");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = chooser.getSelectedFile().getAbsolutePath();
                    if (!filename.toLowerCase().endsWith(".png")) {
                        filename += ".png";
                    }
                    File myNewPNGFile = new File(filename);
                    BufferedImage bufferedImage = new BufferedImage(drawingBoard.length * 3, drawingBoard[0].length * 3,
                            BufferedImage.TYPE_INT_RGB);
                    for (int i = 0; i < drawingBoard.length; i++) {
                        for (int j = 0; j < drawingBoard[0].length; j++) {
                            for (int plusI = 0; plusI < 3; plusI++) {
                                for (int plusJ = 0; plusJ < 3; plusJ++) {
                                    bufferedImage.setRGB(i * 3 + plusI, j * 3 + plusJ, drawingBoard[i][j].getRGB());
                                }
                            }
                        }
                    }
                    try {
                        ImageIO.write(bufferedImage, "PNG", myNewPNGFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "Animation": {
                choose = ANIMATION;
                if (!playingAnimation) {
                    playingAnimation = true;
                    java.util.List<Firework> listFW = new ArrayList<>();
                    Random rd = new Random();
                    Board.setGridColor(chooseColor);
                    int timerDelay = 20;
                    timer.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            MyFunction.clearArr(nextDrawing);
                            MyFunction.clearArr(nextPoint);
                            if (rd.nextInt() % 3 == 0) {
                                Firework FW = new Firework(nextDrawing, nextPoint, new Color(rd.nextFloat(), rd.nextFloat(), rd.nextFloat()));
                                FW.initFirework(rd.nextInt(Height), rd.nextInt(50), lineModeArr[rd.nextInt(lineModeArr.length)]);
                                //FW.initFirework(rd.nextInt(Height), rd.nextInt(Width), Color.BLACK);
                                listFW.add(FW);
                            }
                            for (int u = 0; u < listFW.size(); u++) {
                                Firework now = listFW.get(u);
                                //System.out.println(now.getRadius());
                                if (now.isEnd()) {
                                    listFW.remove(u);
                                } else {
                                    now.drawingState();
                                    now.nextState();
                                }
                            }
                            repaint();
                            System.out.println("list" + listFW.size());
                            try {
                                TimeUnit.MILLISECONDS.sleep(30);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    timer.start();
                } else {
                    timer.stop();
                    timer.removeActionListener(timer.getActionListeners()[0]);
                    playingAnimation = false;
                    Board.setGridColor(new Color(235, 235, 235));
                    MyFunction.clearArr(nextDrawing);
                    MyFunction.clearArr(nextPoint);
                    repaint();
                }
                //break;
            }
        }
    }


    // ================================== CÁC LOẠI BẢNG ==========================
    private boolean[][] nextDrawing = new boolean[Width][Height]; //258x188
    private Color[][] nextPoint = new Color[Width][Height];
    private Color[][] drawingBoard = new Color[Width][Height];
    private Color[][] undoPoint = new Color[Width][Height];

    // ================================== CÁC LOẠI PANEL ==========================
    private JPanel activity_main;
    private JPanel leftPanel;
    private JPanel mainArea;
    private JPanel drawArea;        // khu vực vẽ

    // di chuột
    public class Move implements MouseMotionListener {

        // nắm kéo thả chuột
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            switch (choose) {
                case PENCIL: {
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                    mX = mouseEvent.getX() / rectSize;
//                    mY = mouseEvent.getY() / rectSize;
                    //if (xStart != -1 && yStart != -1)
                    if (startXY.X != -1 && startXY.Y != -1)
                        new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(startXY, mouseXY, lineMode.DEFAULT);
                    startXY.set(mouseXY);
//                    xStart = mX;
//                    yStart = mY;
                    drawArea.repaint();
                    break;
                }
                case LINE: // vẽ đường thẳng
                {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                    mX = mouseEvent.getX() / rectSize;
//                    mY = mouseEvent.getY() / rectSize;
                    if (startXY.X != -1 && startXY.Y != -1)
                        new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(startXY, mouseXY, chooseLineMode);
                    repaint();
                    break;
                }
                case RECTANGLE: // vẽ đường thẳng
                {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                    if (startXY.X != -1 && startXY.Y != -1) {
                        Rectangle rec = new Rectangle(nextDrawing, nextPoint, chooseColor);
                        if (mouseEvent.isShiftDown()) {
                            rec.setSquare(startXY, mouseXY, chooseLineMode);
                        } else {
                            rec.setRectangle(startXY, mouseXY, chooseLineMode);
                        }
                        rec.draw();
                        Board.setNowHinhHoc(rec);
                    }
                    repaint();
                    break;
                }
                case ZIGZAG: // vẽ đường thẳng
                {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                    if (startXY.X != -1 && startXY.Y != -1) {
                        HopChuNhat hbh = new HopChuNhat(nextDrawing, nextPoint, chooseColor);
                        HopChuNhat hbh2 = new HopChuNhat(nextDrawing, nextPoint, chooseColor);
                        Line li = new Line(nextDrawing, nextPoint, chooseColor);
                        if (mouseEvent.isShiftDown()) {
                            hbh.setHopChuNhat(startXY, mouseXY, chooseLineMode);
                        } else {
                            hbh.setHopChuNhat(startXY, mouseXY, chooseLineMode);
                        }
                        hbh.draw();
                        Board.setNowHinhHoc(hbh);
                    }
                    repaint();
                    break;
                }
                case ERASE: // vẽ cục gôm
                {
                    //MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            Point2D diem = new Point2D(mouseXY.X + i, mouseXY.Y + j);
                            if (MyFunction.isSafe(drawingBoard, diem.X, diem.Y)) {
                                nextPoint[diem.X][diem.Y] = Color.WHITE;
                                nextDrawing[diem.X][diem.Y] = true;
                            }
                        }
                    }
                    Board.drawErase(mouseXY);
                    repaint();
                    break;
                }
                case CIRCLE: {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                    mX = mouseEvent.getX() / rectSize;
//                    mY = mouseEvent.getY() / rectSize;
                    if (startXY.X != -1 && startXY.Y != -1) {
                        new Circle(nextDrawing, nextPoint, chooseColor).drawingCircle(startXY, mouseXY, chooseLineMode);
                    }
                    repaint();
                    break;
                }
                case ELLIPSE: {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
//                    mX = mouseEvent.getX() / rectSize;
//                    mY = mouseEvent.getY() / rectSize;
                    if (startXY.X != -1 && startXY.Y != -1) {
                        if (mouseEvent.isShiftDown()) {
                            new Circle(nextDrawing, nextPoint, chooseColor).drawingCircle(startXY, mouseXY, chooseLineMode);
                        } else {
                            new Ellipse(nextDrawing, nextPoint, chooseColor).drawEllipse(startXY, mouseXY, chooseLineMode);
                        }
                    }
                    repaint();
                    break;
                }
                case ROTATE: {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                    if (startXY.X != -1 && startXY.Y != -1) {
                        //System.out.println(Board.now.tag);
                        Board.rotateNow(startXY, mouseXY);
                        repaint();
                    }
                    break;
                }
            }

        }

        // di chuột
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            switch (choose) {
                case LINE: // vẽ đường thẳng
                {
                    // nothing
                    // System.out.println("x: " +mouseEvent.getX()+" - "+"y:"+mouseEvent.getY());
                    break;
                }
                case ERASE: {
                    MyFunction.clearArr(nextDrawing);
                    mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            Point2D diem = new Point2D(mouseXY.X + i, mouseXY.Y + j);
                            if (MyFunction.isSafe(drawingBoard, diem.X, diem.Y)) {
                                nextPoint[diem.X][diem.Y] = Color.WHITE;
                                nextDrawing[diem.X][diem.Y] = true;
                            }
                        }
                    }
                    Board.drawErase(mouseXY);
                    repaint();
                    break;
                }
            }
        }
    }

    private JButton lineButton;             // vẽ đường thẳng
    private JButton clearButton;            // xóa sạch
    private JButton pencilButton;           // đè là vẽ
    private JButton undoButton;             // Undo
    private JButton rectangleButton;        // vẽ hình chữ nhật
    private JButton redoButton;             // Redo
    private JButton paintButton;            // tô màu, thay thế vùng pixel được chọn thành màu
    private JButton colorBox;               // hiển thị màu đang chọn
    private JButton ellipseButton;          // vẽ elipse
    private JButton rotateButton;           // xoay
    private JButton Import;                 // mở hình từ bên ngoài
    private JButton Export;                 // lưu hình
    private JButton settingButton;          // mở setting
    private JButton eraseButton;            // xóa hết
    private JButton zigzagButton;           // vẽ đường gấp khúc
    private JButton colorButton;            // chọn màu
    private JButton circleButton;           // vẽ hình tròn
    private JComboBox comboBox1;            // chọn loại nét vẽ
    private JLabel showSize;                // hiện kích thước nét vẽ
    private JSlider sizeSlider;             // điều chỉnh kích thước nét vẽ
}
