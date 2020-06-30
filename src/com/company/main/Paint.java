package com.company.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.company.Animation2D.Firework;
import com.company.xuli.xuliduongve.*;
import com.company.xuli.xuliduongve.Rectangle;
import com.company.xuli.xuliduongve.Cube;

import static com.company.Button.*;

public class Paint extends JFrame implements ActionListener {
    // ================================== CÁC BIẾN ĐƠN ==========================
    private static int Width = 277;     // độ rộng bảng vẽ
    private static int Height = 172;    // độ cao
    //=================================== Các biến ánh xạ với UI ================
    private JButton lineButton;             // vẽ đường thẳng
    private JButton clearButton;            // xóa sạch
    private JButton pencilButton;           // đè là vẽ
    private JButton undoButton;             // Undo
    private JButton rectangleButton;        // vẽ hình chữ nhật
    private JButton redoButton;             // Redo
    private JButton paintButton;            // tô màu, thay thế vùng pixel được chọn thành màu
    private JButton selectButton;               // hiển thị màu đang chọn
    private JButton ellipseButton;          // vẽ elipse
    private JButton rotateButton;           // xoay
    private JButton Import;                 // mở hình từ bên ngoài
    private JButton Export;                 // lưu hình
    private JButton settingButton;          // mở setting
    private JButton eraseButton;            // xóa hết
    private JButton cubeButton;             // vẽ đường gấp khúc
    private JButton colorButton;            // chọn màu
    private JButton moveButton;             // vẽ hình tròn
    private JButton globularButton;         // vẽ hình cầu
    private JButton animationButton;        // nút mở hoạt cảnh
    private JButton setCenter;
    private JPanel styleCBPanel;
    private JButton symOXButton;
    private JButton symOYButton;
    private JPanel sizeCBPanel;
    private JButton zoomButton;
    private JPanel toolPanel;
    private JButton button2;
    private JPanel Panel2D;
    private JPanel Coord3D;
    private JLabel xCoord2D;
    private JComboBox styleComboBox;            // chọn loại nét vẽ
    private JComboBox sizeComboBox;
    private JLabel yCoord2D;
    private JButton animalButton;
    private JPanel settingPanel;
    private JPanel filePanel;
    private int spacing = 1;           // khoảng cách giữa 2 pixels
    private int rectSize = 4;          // tổng của kích thước pixel và spacing
    private boolean playingAnimation = false;
    private Timer timer;
    private int sizeLine = 1;          // kích thước nét vẽ
    private Color chooseColor = Color.BLACK;    // màu hiện tại đang chọn
    private com.company.Button choose = PENCIL; // nút vừa chọn
    private boolean currentBoardState = true;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show2DAxis = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show3DAxis = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show2DCoord = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show3DCoord = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private int OX = spacing + Width / 2 * rectSize + (rectSize - spacing) / 2;     // vị trí trục OX
    private int OY = spacing + Height / 2 * rectSize + (rectSize - spacing) / 2;    // vị trí trục OY
    private JPanel Coord2D;
    private JPanel Panel3D;
    private JButton button4;
    private JPanel AnimationPanel;


    // ================================== CÁC LOẠI BẢNG ==========================
    private boolean[][] nextDrawing = new boolean[Width][Height];
    private Color[][] nextPoint = new Color[Width][Height];
    private Color[][] drawingBoard = new Color[Width][Height];

    // ================================== CÁC LOẠI PANEL ==========================
    private JPanel activity_main;
    private JButton symetryPointButton;
    private JPanel mainArea;
    private JPanel drawArea;        // khu vực vẽ

    //========================= biến chứa chế độ đường thẳng đang chọn ==============
    private lineMode chooseLineMode = lineMode.DEFAULT;

    //========================= danh sách các loại nét vẽ ===========================
    private lineMode[] lineModeArr = {lineMode.DEFAULT, lineMode.DASH, lineMode.DOT, lineMode.DASHDOT, lineMode.DASHDOTDOT, lineMode.ARROW};


    // ======================== CÁC BIẾN LIÊN QUAN TỚI CHUỘT ========================
    // khi kéo thả, startXY là điểm đầu tiên click chuột vào, mouseXY là điểm hiện tại
    private Point2D startXY;
    private Point2D mouseXY;
    private Point2D test;
    private boolean firstClick = false; // biến xác định click chuột đầu trong 2 lần click


    // hàm custom cho các thành phần trong form

    private void createUIComponents() {
        drawArea = new Board(nextDrawing, nextPoint, drawingBoard, Width, Height, spacing, rectSize);
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        styleComboBox = new JComboBox<lineMode>(lineModeArr);
        sizeComboBox = new JComboBox<lineMode>(lineModeArr);
        mouseXY = new Point2D(-1, -1);
        startXY = new Point2D(-1, -1);
        timer = new Timer(0, null);
    }

    // hàm chính
    public void run() {
        this.setContentPane(activity_main);// liên kết với màn hình form
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close
        this.setTitle("Paint V1.0"); // đặt tiêu đề
        this.setSize(1279, 748); // kích thước cửa sổ
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("/com/company/Icons/Color.png");
        this.setIconImage(icon.getImage());
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
        cubeButton.addActionListener(this);
        ellipseButton.addActionListener(this);
        rotateButton.addActionListener(this);
        //circleButton.addActionListener(this);
        ellipseButton.addActionListener(this);
        settingButton.addActionListener(this);
        eraseButton.addActionListener(this);
        animationButton.addActionListener(this);
        Import.addActionListener(this);
        Export.addActionListener(this);
        globularButton.addActionListener(this);
        animalButton.addActionListener(this);
        setCenter.addActionListener(this);
        moveButton.addActionListener(this);
        button2.addActionListener(this);
        selectButton.addActionListener(this);
        //settingPanel.setSize(70, 30);
//        styleComboBox.setUI(new BasicComboBoxUI() {
//            @Override
//            protected JButton createArrowButton() {
//                //Color bg = styleComboBox.getBackground();
//                Color bg = styleComboBox.getBackground();
//                JButton b = super.createArrowButton();
//                styleComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));
//                b.setBackground(bg);
//                b.setBorder(BorderFactory.createLineBorder(bg));
//                return b;
//            }
//        });
//
//        sizeComboBox.setUI(new BasicComboBoxUI() {
//            @Override
//            protected JButton createArrowButton() {
//                //Color bg = styleComboBox.getBackground();
//                Color bg = sizeComboBox.getBackground();
//                JButton b = super.createArrowButton();
//                sizeComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));
//                b.setBackground(bg);
//                b.setBorder(BorderFactory.createLineBorder(bg));
//                return b;
//            }
//        });

        // chọn nét vẽ
        styleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chooseLineMode = lineModeArr[styleComboBox.getSelectedIndex()];
            }
        });
        sizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chooseLineMode = lineModeArr[sizeComboBox.getSelectedIndex()];
            }
        });
        //sizeSlider.addChangeListener((ChangeListener) this);
        Panel2D.setVisible(currentBoardState);
        Panel3D.setVisible(!currentBoardState);
        MyFunction.clearArr(drawingBoard);
    }


    // nhấn nút
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = null;

        // câu if dưới dùng để tránh trường hợp đang show animation nhưng bấm nút khác (undo, pencil,...)
        if (!playingAnimation) {
            // nếu hiện tại không có animation nào đang chạy thì tiến hành get trạng thái nút
            source = actionEvent.getSource();
        } else {
            // nếu có animation đang chạy thì chỉ nhận nút Animation
            if (animationButton.equals(actionEvent.getSource())) {
                source = actionEvent.getSource();
            }
        }

        if (redoButton.equals(source)) {
            Board.redo();
            undoButton.setEnabled(Board.ableUndo());
            redoButton.setEnabled(Board.ableRedo());
            repaint();
        } else if (clearButton.equals(source)) {//choose = Button.CLEAR;
            MyFunction.clearArr(drawingBoard);
            MyFunction.clearArr(nextPoint);
            repaint();
        } else if (lineButton.equals(source)) {
            startXY.set(-1, -1);
            choose = LINE;
        }
        else if(selectButton.equals(source)){
            startXY.set(-1, -1);
            choose = SELECT;
        }
        else if (rectangleButton.equals(source)) {
            startXY.set(-1, -1);
            choose = RECTANGLE;
        } else if (cubeButton.equals(source)) {
            startXY.set(-1, -1);
            choose = CUBE;
        } else if (globularButton.equals(source)) {
            startXY.set(-1, -1);
            choose = GLOBULAR;
        } else if (eraseButton.equals(source)) {
            startXY.set(-1, -1);
            choose = ERASE;
        } else if (pencilButton.equals(source)) {
            startXY.set(-1, -1);
            choose = PENCIL;
        }
        else if (animalButton.equals(source)){
            startXY.set(-1, -1);
            choose = ANIMAL;
        } else if (colorButton.equals(source)) {
            Color c = JColorChooser.showDialog(this, "Choose Color", chooseColor);
            if (c != null)
                chooseColor = c;
            colorButton.setBackground(chooseColor);
            colorButton.setForeground(chooseColor);
        } else if (undoButton.equals(source)) {
            Board.undo();
            undoButton.setEnabled(Board.ableUndo());
            redoButton.setEnabled(Board.ableRedo());
            repaint();
        } else if (paintButton.equals(source)) {
            choose = PAINT;
            MyFunction.storePointColor(drawingBoard, nextPoint);
            MyFunction.clearArr(nextDrawing);
        } else if (ellipseButton.equals(source)) {
            startXY.set(-1, -1);
            choose = ELLIPSE;
        } else if (rotateButton.equals(source)) {
            startXY.set(-1, -1);
            choose = ROTATE;
        } else if (setCenter.equals(source)) {
            choose = SETCENTER;
        } else if (moveButton.equals(source)) {
            startXY.set(-1, -1);
            choose = MOVE;
            System.out.println("Move Button");
        } else if (button2.equals(source)) {
            choose = PLAY;
            //choose = ANIMATION;
            if (!playingAnimation) {
                playingAnimation = true;
                int timerDelay = 30;
                final int[] angle = {0};
                Point2D a = Board.now.center;
                Board.previousDo();
                final Point2D[] b = {new Point2D(6, 0)};
                timer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        //while(true)
                        {
                            MyFunction.clearArr(nextDrawing);
                            MyFunction.clearArr(nextPoint);
                            b[0] = b[0].rotatePoint(a, 0.1);
                            Board.rotateNow(a, b[0]);
                            repaint();
                            try {
                                TimeUnit.MILLISECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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


        } else if (settingButton.equals(source)) {
            Setting dialog = new Setting(currentBoardState);
            Setting.setGridColor(Board.getGridColor());
            dialog.setChecker(show2DAxis, show2DCoord, show3DAxis, show3DCoord);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            Board.setGridColor(Setting.getGridColor());
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
            Panel2D.setVisible(currentBoardState);
            Panel3D.setVisible(!currentBoardState);
            if (currentBoardState) {
                ((Board) drawArea).setShowAxis(show2DAxis);
                ((Board) drawArea).setShowCoord(show2DCoord);
            } else {
                ((Board) drawArea).setShowAxis(show3DAxis);
                ((Board) drawArea).setShowCoord(show3DCoord);
            }

            repaint();
        } else if (Import.equals(source)) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "PNG Images", "png");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //System.out.println("You chose to open this file: " +
                //chooser.getSelectedFile().getName());
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
                        Error dialog = new Error();
                        dialog.pack();
                        dialog.setLocationRelativeTo(this);
                        dialog.setVisible(true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        } else if (Export.equals(source)) {
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
        } else if (animationButton.equals(source)) {
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
                            FW.initFirework(100 + rd.nextInt(Height-100), rd.nextInt(50), lineModeArr[rd.nextInt(lineModeArr.length)], 1 + rd.nextInt(20)/10);
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


    // thư viện click
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
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case ANIMAL: // vẽ đường thẳng
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextDrawing);
                        MyFunction.clearArr(nextPoint);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            Animal chon = new Animal(nextDrawing, nextPoint, chooseColor);
                            System.out.println(startXY.X + "--" + startXY.Y);
                            chon.create(startXY,mouseXY,chooseLineMode);
                            chon.draw();
                        }
                        repaint();
                        break;
                    }
                    case RECTANGLE: // vẽ hinh cn
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case SELECT: // vẽ hinh cn
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        //Board.previousDo();
                        break;
                    }
                    case CUBE: // vẽ hinh hộp chữ nhật
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case GLOBULAR: // vẽ hinh hộp chữ nhật
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
                    case SETCENTER: {
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        Board.now.center = new Point2D(startXY);
                        break;
                    }
                    case MOVE: {
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
                    case ANIMAL: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case RECTANGLE: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case SELECT: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case CUBE: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case GLOBULAR: {
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
                    case MOVE: {
                        Board.applyMove(startXY, mouseXY);
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




    // di chuột
    public class Move implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {      // nắm kéo thả chuột
            if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                switch (choose) {
                    case PENCIL: {
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);

                        // nếu vị trí bắt đầu không phải là (-1,-1) thì vẽ đường thẳng từ điểm đó tới vị trí hiện tại của con trỏ chuột
                        if (startXY.X != -1 && startXY.Y != -1) {
                            new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(startXY, mouseXY, lineMode.DEFAULT);
                        }
                        startXY.set(mouseXY); // sau khi vẽ hàm trên thì điểm bắt đầu được set lại
                        drawArea.repaint();
                        break;
                    }
                    case LINE: // vẽ đường thẳng
                    {
                        // tương tự với pencil, khác ở chỗ điểm bắt đầu không đổi
                        MyFunction.clearArr(nextDrawing); // phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1)
                            new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(startXY, mouseXY, chooseLineMode);
                        repaint();
                        break;
                    }

                    case RECTANGLE: // vẽ đường thẳng
                    {
                        MyFunction.clearArr(nextDrawing);// phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            Rectangle rec = new Rectangle(nextDrawing, nextPoint, chooseColor); // khởi tạo class HCN
                            if (mouseEvent.isShiftDown()) { // nếu có sự kiện phím shift
                                rec.setSquare(startXY, mouseXY, chooseLineMode); // hàm set hình vuông
                            } else {
                                rec.setRectangle(startXY, mouseXY, chooseLineMode); //  hàm set HCN
                            }
                            rec.draw(); // cho vẽ hình

                            Board.setNowHinhHoc(rec); // vẽ xong sẽ được đưa vào chế độ được chọn, để xoay zoom...
                        }
                        repaint();
                        break;
                    }
                    case SELECT:
                    {
                        MyFunction.clearArr(nextDrawing);// phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            Point2D startSelect = new Point2D();
                            Point2D endSelect = new Point2D();
                            //A
                            if (startXY.X > mouseXY.X && startXY.Y > mouseXY.Y){
                                startSelect.set(mouseXY.X, mouseXY.Y);
                                endSelect.set(startXY.X, startXY.Y);
                            }
                            //B
                            if (startXY.X < mouseXY.X && startXY.Y > mouseXY.Y){
                                startSelect.set(startXY.X, mouseXY.Y);
                                endSelect.set(mouseXY.X, startXY.Y);
                            }

                            //D
                            if (startXY.X > mouseXY.X && startXY.Y < mouseXY.Y){
                                startSelect.set(mouseXY.X, startXY.Y);
                                endSelect.set(startXY.X, mouseXY.Y);
                            }

                            //C
                            if (startXY.X < mouseXY.X && startXY.Y < mouseXY.Y){
                                startSelect.set(startXY);
                                endSelect.set(mouseXY);
                            }
                            Board.select(startSelect, endSelect);
                            HinhHoc select = new HinhHoc(nextDrawing, nextPoint, chooseColor); // khởi tạo class HCN
                                select.SelectPoint(startSelect, endSelect,drawingBoard); //  hàm set HCN
                            //System.out.println(startSelect.X + " " + startSelect.Y + "--" + endSelect.X + " " + endSelect.Y);
                            select.tag = SELECT;
                            //System.out.println("aaaaaaaaaaaa");
                            Board.setNowHinhHoc(select); // vẽ xong sẽ được đưa vào chế độ được chọn, để xoay zoom...
                        }
                        repaint();
                        break;
                    }
                    case CUBE: // vẽ hình hộp chữ nhật
                    {
                        MyFunction.clearArr(nextDrawing);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            Cube hbh = new Cube(nextDrawing, nextPoint, chooseColor);
                            Cube hbh2 = new Cube(nextDrawing, nextPoint, chooseColor);
                            Line li = new Line(nextDrawing, nextPoint, chooseColor);
                            if (mouseEvent.isShiftDown()) {
                                hbh.setCube(startXY, mouseXY, chooseLineMode);
                            } else {
                                hbh.setCube(startXY, mouseXY, chooseLineMode);
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
                    case ELLIPSE: {
                        MyFunction.clearArr(nextDrawing);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
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
                    case GLOBULAR: {
                        MyFunction.clearArr(nextDrawing);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            new Circle(nextDrawing, nextPoint, chooseColor).drawingCircle(startXY, mouseXY, chooseLineMode);
                            Point2D temp = mouseXY;
                            temp.Y = startXY.Y + Math.abs(mouseXY.X - startXY.X) / 3;
                            new Ellipse(nextDrawing, nextPoint, chooseColor).drawEllipse(startXY, temp, chooseLineMode);
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
                    case MOVE: {
                        MyFunction.clearArr(nextDrawing);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            //System.out.println(Board.now.tag);
                            Board.moveNow(startXY, mouseXY);
                            //System.out.println("Move");
                            repaint();
                        }
                        break;
                    }
                }

            }

        }

        // di chuột
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            xCoord2D.setText("X: " + (mouseEvent.getX() / rectSize - OX / rectSize));
            yCoord2D.setText("Y: " + (-(mouseEvent.getY() / rectSize - OY / rectSize)));
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
}
