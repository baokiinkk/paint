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
import com.company.xuli.xuliduongve.*;
import com.company.xuli.xuliduongve.Cube;
import com.company.xuli.xuliduongve.Rectangle;

import static com.company.Button.*;

public class Paint extends JFrame implements ActionListener {
    // ================================== CÁC BIẾN ĐƠN ==========================
    public static int Width = 282;     // độ rộng bảng vẽ
    public static int Height = 181;    // độ cao
    //=================================== Các biến ánh xạ với UI ================
    private JButton lineButton;             // vẽ đường thẳng
    private JButton clearButton;            // xóa sạch
    private JButton pencilButton;           // đè là vẽ
    private JButton undoButton;             // Undo
    private JButton rectangleButton;        // vẽ hình chữ nhật
    private JButton coneButton;
    private JButton cylinderButton;
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
    private JButton sphereButton;         // vẽ hình cầu
    private JButton animationButton;        // nút mở hoạt cảnh
    private JButton setCenter;
    private JPanel styleCBPanel;
    private JButton symOXButton;
    private JButton symOYButton;
    private JPanel sizeCBPanel;
    private JButton rotateSymButton;
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
    public static int spacing = 1;           // khoảng cách giữa 2 pixels
    public static int rectSize = 4;           // tổng của kích thước pixel và spacing
    private boolean playingAnimation = false;
    private Timer timer;
    private int time = 0;
    public static Color[][] nextPoint = new Color[Width][Height];
    private Color chooseColor = Color.BLACK;    // màu hiện tại đang chọn
    private com.company.Button choose = PENCIL; // nút vừa chọn
    private boolean currentBoardState = true;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show2DAxis = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show3DAxis = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show2DCoord = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    private boolean show3DCoord = false;   // biến chỉ trạng thái bảng vẽ, true là 2D, false là 3D
    public static int OX = spacing + Width / 2 * rectSize + (rectSize - spacing) / 2;     // vị trí trục OX
    public static int OY = spacing + Height / 2 * rectSize + (rectSize - spacing) / 2;    // vị trí trục OY
    private Point2D startSelect;
    private Point2D endSelect;
    private JPanel Coord2D;
    private JPanel Panel3D;
    private JButton driveCarButton;
    private JPanel AnimationPanel;


    // ================================== CÁC LOẠI BẢNG ==========================
    private boolean[][] nextDrawing = new boolean[Width][Height];
    private boolean showGrid = true;          // hiện lưới
    private Color[][] drawingBoard = new Color[Width][Height];
    private String[][] Coord = new String[Width][Height];
    private String[][] nextCoord = new String[Width][Height];

    // ================================== CÁC LOẠI PANEL ==========================
    private JPanel activity_main;
    private JButton symetryPointButton;
    private JPanel mainArea;
    private JPanel drawArea;        // khu vực vẽ
    private JPanel X;
    private JPanel Y;
    private JPanel Z;
    private JButton customButton;
    private JButton pyramidButton;
    private JButton tetrahedronButton;
    private JButton triangleButton;
    private JButton parallelogramButton;


    //========================= biến chứa chế độ đường thẳng đang chọn ==============
    private lineMode chooseLineMode = lineMode.DEFAULT;
    private sideMode chooseSideMode = sideMode.DEFAULT;

    //========================= danh sách các loại nét vẽ ===========================
    private lineMode[] lineModeArr = {lineMode.DEFAULT, lineMode.DASH, lineMode.DOT, lineMode.DASHDOT, lineMode.DASHDOTDOT, lineMode.ARROW};
    private sideMode[] sideModeArr = {sideMode.DEFAULT, sideMode.DYAD, sideMode.TRIAD, sideMode.TETRAD, sideMode.PENTAD,
                                      sideMode.HEXAD, sideMode.HEPTAD, sideMode.OCTAD, sideMode.NONAD, sideMode.DECAD};


    // ======================== CÁC BIẾN LIÊN QUAN TỚI CHUỘT ========================
    // khi kéo thả, startXY là điểm đầu tiên click chuột vào, mouseXY là điểm hiện tại
    private Point2D startXY;
    private Point2D mouseXY;
    private Point2D pointXY;
    private Point2D test;
    private boolean firstClick = false; // biến xác định click chuột đầu trong 2 lần click


    // hàm custom cho các thành phần trong form

    private void createUIComponents() {
        drawArea = new Board(nextDrawing, nextPoint, drawingBoard, Coord, nextCoord);
        drawArea.addMouseMotionListener(new Move());
        drawArea.addMouseListener(new Click());
        styleComboBox = new JComboBox<lineMode>(lineModeArr);
        sizeComboBox = new JComboBox<sideMode>(sideModeArr);
        mouseXY = new Point2D(-1, -1);
        startXY = new Point2D(-1, -1);
        pointXY = new Point2D(Width/2,Height/2);
        startSelect = new Point2D();
        endSelect = new Point2D();
        timer = new Timer(0, null);
    }

    // hàm chính
    public void run() {
        this.setContentPane(activity_main);// liên kết với màn hình form
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close
        this.setTitle("Paint V1.0"); // đặt tiêu đề
//        this.setSize(1279, 748); // kích thước cửa sổ
        this.setSize(1299, 780); // kích thước cửa sổ
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
        sphereButton.addActionListener(this);
        animalButton.addActionListener(this);
        setCenter.addActionListener(this);
        moveButton.addActionListener(this);
        button2.addActionListener(this);
        selectButton.addActionListener(this);
        driveCarButton.addActionListener(this);
        symOXButton.addActionListener(this);
        symOYButton.addActionListener(this);
        symetryPointButton.addActionListener(this);
        rotateSymButton.addActionListener(this);
        cylinderButton.addActionListener(this);
        coneButton.addActionListener(this);
        customButton.addActionListener(this);
        parallelogramButton.addActionListener(this);
        triangleButton.addActionListener(this);
        tetrahedronButton.addActionListener(this);
        pyramidButton.addActionListener(this);
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
                chooseSideMode = sideModeArr[sizeComboBox.getSelectedIndex()];
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
            if (choose.equals(ANIMATION) && animationButton.equals(actionEvent.getSource())) {
                source = actionEvent.getSource();
            }
            if (choose.equals(CARANIM) && driveCarButton.equals(actionEvent.getSource())) {
                source = actionEvent.getSource();
            }
            if (choose.equals(PLAY) && button2.equals(actionEvent.getSource())) {
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
            MyFunction.clearArr(Coord);
            MyFunction.clearArr(nextPoint);
            MyFunction.clearArr(nextCoord);
            pointXY.set(Width / 2, Height / 2);
            repaint();
        } else if (lineButton.equals(source)) {
            startXY.set(-1, -1);
            choose = LINE;
        } else if (selectButton.equals(source)) {
            startXY.set(-1, -1);
            choose = SELECT;
        } else if (rectangleButton.equals(source)) {
            startXY.set(-1, -1);
            choose = RECTANGLE;
        } else if (cubeButton.equals(source)) {
            startXY.set(-1, -1);
            choose = CUBE;
        } else if (sphereButton.equals(source)) {
            startXY.set(-1, -1);
            choose = SPHERE;
        } else if (coneButton.equals(source)) {
            startXY.set(-1, -1);
            choose = CONE;
        } else if (cylinderButton.equals(source)) {
            startXY.set(-1, -1);
            choose = CYLINDER;
        } else if (parallelogramButton.equals(source)) {
            startXY.set(-1, -1);
            choose = PARALLELOGRAM;
        } else if (triangleButton.equals(source)) {
            startXY.set(-1, -1);
            choose = TRIANGLE;
        } else if (tetrahedronButton.equals(source)) {
            startXY.set(-1, -1);
            choose = TETRAHEDRON;
        } else if (pyramidButton.equals(source)) {
            startXY.set(-1, -1);
            choose = PYRAMID;
        } else if (eraseButton.equals(source)) {
            startXY.set(-1, -1);
            choose = ERASE;
        } else if (pencilButton.equals(source)) {
            startXY.set(-1, -1);
            choose = PENCIL;
        } else if (animalButton.equals(source)) {
            startXY.set(-1, -1);
            choose = ANIMAL;
        } else if (driveCarButton.equals(source)) {
            choose = CARANIM;
            if (!playingAnimation) {
                playingAnimation = true;
                MyFunction.clearArr(nextPoint);
                Board.setGridColor(Color.BLACK);
                DriveCarAnim carAnim = new DriveCarAnim(nextDrawing, nextPoint, chooseColor);
                Board.applyNow();
                MyFunction.clearArr(nextDrawing);
                repaint();
                time = 0;
                Random rd = new Random();
                Board.setGridColor(Color.BLACK);
                java.util.List<Rectangle> listFW = new ArrayList<>();
                java.util.List<Tree> listTreeLeft = new ArrayList<>();
                java.util.List<Tree> listTreeRight = new ArrayList<>();
                int pos = 64, len = 3, spc = 3, spd = 1;
                Rectangle base = new Rectangle(nextDrawing, nextPoint, Color.WHITE);
                base.setRectangle(new Point2D(138, pos), new Point2D(139, pos + len), lineMode.DEFAULT);

                Tree leftCenterBase = new Tree(nextDrawing, nextPoint, Color.BLACK, new Point2D(89, 60), new Point2D(91, 67));
                //Tree leftSizeBase = new Tree(nextDrawing, nextPoint, Color.BLACK, new Point2D(), new Point2D());
                Tree rightCenterBase = new Tree(nextDrawing, nextPoint, Color.BLACK, new Point2D(188, 61), new Point2D(193, 68));
                //Tree rightSizeBase = new Tree(nextDrawing, nextPoint, Color.BLACK, new Point2D(), new Point2D());
                //listFW.add(base);
                Sun sun = new Sun(nextDrawing, nextPoint, Color.YELLOW);
                //sun.setSun(new Point2D(268, 5), new Point2D(255, 5));
                sun.setSun(new Point2D(268, 5), new Point2D(250, 5));
                timer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        MyFunction.clearArr(nextDrawing);
                        MyFunction.clearArr(nextPoint);
                        repaint();
                        if (time % 75 == 0) {
                            Rectangle now = new Rectangle(nextDrawing, nextPoint, Color.WHITE);
                            now.setRectangle(new Point2D(base.start.X, base.start.Y),
                                    new Point2D(base.end.X, base.end.Y), lineMode.DEFAULT);
                            listFW.add(now);

                            Tree leftNow = new Tree(nextDrawing, nextPoint, Color.BLACK,
                                    new Point2D(leftCenterBase.center), new Point2D(leftCenterBase.end));
                            listTreeLeft.add(leftNow);
                            Tree rightNow = new Tree(nextDrawing, nextPoint, Color.BLACK,
                                    new Point2D(rightCenterBase.center), new Point2D(rightCenterBase.end));
                            listTreeRight.add(rightNow);
                        }
                        for (int i = 0; i < listFW.size(); i++) {
                            Rectangle now = listFW.get(i);
                            now.draw();
                            Point2D str = now.start;
                            Point2D en = now.end;
                            str.set(str.X, str.Y + 1);
                            en.set(en.X, en.Y + 2);
                            now.setRectangle(str, en, lineMode.DEFAULT);
                            listFW.set(i, now);
                        }

                        for (int i = 0; i < listTreeLeft.size(); i++) {
                            //System.out.println(i);
                            Tree left = listTreeLeft.get(i);
                            Tree right = listTreeRight.get(i);
                            left.tanCayColor = new Color(0, 100 + left.center.Y, 50);
                            left.draw();
                            right.tanCayColor = new Color(0, 100 + left.center.Y, 50);
                            right.draw();
                            Point2D str = left.center;
                            Point2D en = left.end;
                            str.Y += 1;
                            str.X = MyFunction.leftCenter(str.Y) - 5;
                            en.Y += 1 + time % 2;
                            en.X = MyFunction.leftSize(en.Y);
                            listTreeLeft.set(i, new Tree(nextDrawing, nextPoint, Color.BLACK, str, en));

                            str = right.center;
                            en = right.end;
                            str.Y += 1;
                            str.X = MyFunction.rightCenter(str.Y) + 5;
                            en.Y += 1 + time % 2;
                            en.X = MyFunction.rightSize(en.Y);
                            listTreeRight.set(i, new Tree(nextDrawing, nextPoint, Color.BLACK, str, en));
                        }
                        sun.draw(time % 360);
                        //repaint();
                        //System.out.println(listTreeLeft.get(0).center.X);
                        if (listTreeLeft.get(0).center.Y > 150) {
                            listTreeLeft.remove(0);
                            listTreeRight.remove(0);
                        }
                        time += 1;
                        if (time % 450 == 0) {
                            time = 0;
                            listFW.remove(0);
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(0);
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
                MyFunction.clearArr(drawingBoard);
                MyFunction.clearArr(nextDrawing);
                MyFunction.clearArr(nextPoint);
                repaint();
            }
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
            //System.out.println("Move Button");
        } else if (button2.equals(source)) {
            choose = PLAY;
            time = 1;
            //choose = ANIMATION;
            if (!playingAnimation) {
                playingAnimation = true;
                int timerDelay = 30;
                timer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent actionEvent) {
                        //while(true)
                        {
                            if (time == 640) {
                                timer.stop();
                                timer.removeActionListener(timer.getActionListeners()[0]);
                                playingAnimation = false;
                            }
                            BufferedImage myNewPNGFile = null;
                            try {
                                //System.out.println("error");
                                String str = "";
                                str += String.valueOf(time);
                                //System.out.println(str);
                                str = "src/com/company/Stick/stick (" + str + ").png";
                                //System.out.println(str);
                                myNewPNGFile = ImageIO.read(new File(str));
                                for (int i = 0; i < drawingBoard.length; i++) {
                                    for (int j = 0; j < drawingBoard[0].length; j++) {
                                        Color c = new Color(myNewPNGFile.getRGB(i * 3, j * 3), true);
                                        drawingBoard[i][j] = c;
                                    }
                                }
                                time++;
                                //System.out.println(stt[0]);
                                repaint();
                            } catch (IOException e) {
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
                MyFunction.clearArr(drawingBoard);
                MyFunction.clearArr(nextPoint);
                repaint();
            }


        } else if (customButton.equals(source)) {
            Input3D dialog = new Input3D(Width, Height, choose);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            if (dialog.isOK()) {
                choose = dialog.get3DShape();
                switch (choose) {
                    case CUBE: {
                        Cube cube = new Cube(nextDrawing, nextPoint, chooseColor);
                        cube.setCube(dialog.getStart3D(), dialog.getEnd3D());
                        cube.draw3D();

                        cube.saveCoord(nextCoord);
                        repaint();
                        break;
                    }
                    case SPHERE: {
                        MyFunction.clearArr(nextDrawing);
                        Sphere Sp = new Sphere(nextDrawing, nextPoint, chooseColor);
                        Sp.setSphere3D(dialog.getStart3D(), dialog.getEnd3D(), chooseLineMode);
                        Sp.draw();
                        Sp.saveCoord(nextCoord);
                        repaint();
                        break;
                    }
                    case CONE: {
                        MyFunction.clearArr(nextDrawing);
                        Cone Con = new Cone(nextDrawing, nextPoint, chooseColor);
                        Con.setCone3D(dialog.getStart3D(), dialog.getEnd3D(), chooseLineMode);
                        Con.draw();
                        Con.saveCoord(nextCoord);
                        repaint();
                        break;
                    }
                    case CYLINDER: {
                        MyFunction.clearArr(nextDrawing);
                        Cylinder Cy = new Cylinder(nextDrawing, nextPoint, chooseColor);
                        Cy.setCylinder3D(dialog.getStart3D(), dialog.getEnd3D(), chooseLineMode);
                        Cy.draw();
                        Cy.saveCoord(nextCoord);
                        repaint();
                        break;
                    }
                    case PYRAMID: {
                        MyFunction.clearArr(nextDrawing);
                        Pyramid Pyr = new Pyramid(nextDrawing, nextPoint, chooseColor);
                        Pyr.setPyramid3D(dialog.getStart3D(), dialog.getEnd3D(), chooseLineMode);
                        Pyr.draw();
                        Pyr.saveCoord(nextCoord);
                        repaint();
                        break;
                    }
                    case TETRAHEDRON: {
                        MyFunction.clearArr(nextDrawing);
                        Tetrahedron Tet = new Tetrahedron(nextDrawing, nextPoint, chooseColor);
                        Tet.setTetrahedron3D(dialog.getStart3D(), dialog.getEnd3D(), chooseLineMode);
                        Tet.draw();
                        Tet.saveCoord(nextCoord);
                        repaint();
                        break;
                    }
                }
                Board.applyNow();
            }
        } else if (settingButton.equals(source)) {
            Setting dialog = new Setting(currentBoardState);
            Setting.setGridColor(Board.getGridColor());
            dialog.setChecker(show2DAxis, show2DCoord, show3DAxis, show3DCoord, showGrid);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            Board.setGridColor(Setting.getGridColor());
            if (dialog.getOK()) {
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
                    MyFunction.clearArr(Coord);
                    MyFunction.clearArr(nextCoord);
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
                if (showGrid != dialog.getShowGrid()) {
                    //System.out.println(showGrid);
                    showGrid = dialog.getShowGrid();
                    if (showGrid) {
                        spacing = 1;
                        //rectSize -= 1;
                    } else {
                        //rectSize += 1;
                        spacing = 0;
                    }
                    ((Board) drawArea).setSpacing(spacing);
                    //((Board)drawArea).setRectSize(rectSize);
                }

                rotateButton.setEnabled(currentBoardState);
                setCenter.setEnabled(currentBoardState);
                rotateSymButton.setEnabled(currentBoardState);
                symetryPointButton.setEnabled(currentBoardState);
                symOXButton.setEnabled(currentBoardState);
                symOYButton.setEnabled(currentBoardState);
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
                BufferedImage myNewPNGFile = null;
                try {
                    myNewPNGFile = ImageIO.read(new File("src/com/company/Icons/New Project.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < drawingBoard.length; i++) {
                    for (int j = 0; j < drawingBoard[0].length; j++) {
                        Color c = new Color(myNewPNGFile.getRGB(i * 3, j * 3), true);
                        drawingBoard[i][j] = c;
                    }
                }
                java.util.List<Firework> listFW = new ArrayList<>();
                Random rd = new Random();
                Board.setGridColor(chooseColor);
                int timerDelay = 20;
                timer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent actionEvent) {
                        MyFunction.clearArr(nextDrawing);
                        MyFunction.clearArr(nextPoint);
                        if (rd.nextInt() % 3 == 0) {
                            Firework FW = new Firework(nextDrawing, nextPoint, new Color(rd.nextFloat(), rd.nextFloat(), rd.nextFloat()));
                            FW.initFirework(100 + rd.nextInt(Height - 100), rd.nextInt(50), lineModeArr[rd.nextInt(lineModeArr.length)], 1 + rd.nextInt(20) / 10);
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
                        //System.out.println("list" + listFW.size());
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
                MyFunction.clearArr(drawingBoard);
                MyFunction.clearArr(nextDrawing);
                MyFunction.clearArr(nextPoint);
                repaint();
            }
            //break;
        } else if (symOYButton.equals(source)) {
            startXY.set(-1, -1);
            choose = VETICALSYMETRY;
        } else if (symOXButton.equals(source)) {
            startXY.set(-1, -1);
            choose = HONRIZONTALSYMETRY;
        } else if (symetryPointButton.equals((source))) {
            startXY.set(-1, -1);
            choose = POINTSYMETRY;
        } else if (rotateSymButton.equals(source)) {
            startXY.set(-1, -1);
            choose = ROTATESYMETRY;
        }
        undoButton.setEnabled(Board.ableUndo());
        redoButton.setEnabled(Board.ableRedo());

    }

    // thư viện click
    public class Click implements MouseListener {

        // click chuột vào trạng thái nào
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            switch (choose) {
//                case LINE: // vẽ đường thẳng
//                {
//                    // nothing
//                    break;
//                }
                case HONRIZONTALSYMETRY:{

                    MyFunction.clearArr(nextDrawing);
                    Board.SymOXNow();
                    Board.applyNow();
                    repaint();
                    break;
                }
                case VETICALSYMETRY:{
                    MyFunction.clearArr(nextDrawing);
                    Board.SymOYNow();
                    Board.applyNow();
                    repaint();
                    break;
                }
                case POINTSYMETRY:{
                    MyFunction.clearArr(nextDrawing);
                    pointXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                    nextDrawing[pointXY.X][pointXY.Y] = true;
                    nextPoint[pointXY.X][pointXY.Y] = chooseColor;
                    Board.SymPointNow(pointXY);
                    Board.applyNow();
                    repaint();
                    pointXY.set(-1,-1);
                    break;
                }
                case SETCENTER: {
                    MyFunction.clearArr(nextDrawing);
                    pointXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                    nextDrawing[pointXY.X][pointXY.Y] = true;
                    nextPoint[pointXY.X][pointXY.Y] = chooseColor;
                    Board.now.center = new Point2D(pointXY);
//                    Board.applyNow();
                    repaint();
                    break;
                }
                case ROTATESYMETRY:
                {
                    MyFunction.clearArr(nextDrawing);
                    pointXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                    nextDrawing[pointXY.X][pointXY.Y] = true;
                    nextPoint[pointXY.X][pointXY.Y] = chooseColor;
                    Board.applyNow();
                    repaint();
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
                            //System.out.println(startXY.X + "--" + startXY.Y);
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
                    case PARALLELOGRAM: // vẽ hinh cn
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case TRIANGLE: // vẽ hinh cn
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case TETRAHEDRON: // vẽ hinh cn
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case PYRAMID: // vẽ hinh cn
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
                    case SPHERE:
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case CONE:
                    {
                        // lấy tọa độ điểm bắt đầu
                        startXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        MyFunction.clearArr(nextPoint);
                        break;
                    }
                    case CYLINDER:
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
                        //System.out.println("Pressed");
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
                    case PARALLELOGRAM: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case CUBE: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case SPHERE: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case CONE: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case CYLINDER: {
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
                    case TRIANGLE: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case TETRAHEDRON: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case PYRAMID: {
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case ERASE: {
                        Board.applyNow();
                        break;
                    }
                    case ROTATE: {
                        if (Board.now != null) {
                            MyFunction.clearArr(nextCoord);
                            Board.applyRotate(startXY, mouseXY);
                            Board.now.saveCoord(nextCoord);
                        }
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case MOVE: {
                        if (Board.now != null) {
                            MyFunction.clearArr(nextCoord);
                            Board.applyMove(startXY, mouseXY);
                            Board.now.saveCoord(nextCoord);
                        }
                        Board.applyNow();
                        repaint();
                        break;
                    }
                    case SELECT: {
                        Select select = new Select(nextDrawing, nextPoint, chooseColor); // khởi tạo class HCN
                        select.SelectPoint(startSelect, endSelect, drawingBoard); //  hàm set HCN
                        //System.out.println(startSelect.X + " " + startSelect.Y + "--" + endSelect.X + " " + endSelect.Y);
                        select.tag = SELECT;
                        //System.out.println("aaaaaaaaaaaa");
                        Board.setNowHinhHoc(select); // vẽ xong sẽ được đưa vào chế độ được chọn, để xoay zoom...
                        //Board.applyNow();
                        //Board.nextDo();
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
                xCoord2D.setText("X: " + (mouseEvent.getX() / rectSize - OX / rectSize));
                yCoord2D.setText("Y: " + (-(mouseEvent.getY() / rectSize - OY / rectSize)));
                switch (choose) {
                    case PENCIL: {
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        // nếu vị trí bắt đầu không phải là (-1,-1) thì vẽ đường thẳng từ điểm đó tới vị trí hiện tại của con trỏ chuột
                        if (startXY.X != -1 && startXY.Y != -1) {
//                            new Line(nextDrawing, nextPoint, chooseColor).MidpointLine(startXY, mouseXY, lineMode.DEFAULT);
                            Line line = new Line(nextDrawing, nextPoint, chooseColor);
                            line.setLine(startXY, mouseXY, pointXY, lineMode.DEFAULT, chooseSideMode);
                            line.draw();
                        }
                        startXY.set(mouseXY); // sau khi vẽ hàm trên thì điểm bắt đầu được set lại
                        drawArea.repaint();
                        break;
                    }
                    case LINE: // vẽ đường thẳng
                    {
                        // tương tự với pencil, khác ở chỗ điểm bắt đầu không đổi
                        MyFunction.clearArr(nextDrawing); // phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        MyFunction.clearArr(nextCoord);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            Line line = new Line(nextDrawing, nextPoint, chooseColor);
                            line.setLine(startXY,mouseXY,pointXY, chooseLineMode, chooseSideMode);
                            line.draw();
                            line.saveCoord(nextCoord);
                        }
                        repaint();
                        break;
                    }

                    case RECTANGLE:
                    {
                        MyFunction.clearArr(nextDrawing);// phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        MyFunction.clearArr(nextPoint);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            Rectangle rec = new Rectangle(nextDrawing, nextPoint, chooseColor); // khởi tạo class HCN
                            if (mouseEvent.isShiftDown()) { // nếu có sự kiện phím shift
                                rec.setSquare(startXY, mouseXY, chooseLineMode); // hàm set hình vuông
                            } else {
                                rec.setRectangle(startXY, mouseXY, chooseLineMode); //  hàm set HCN
                            }
                            rec.draw(pointXY, chooseSideMode); // cho vẽ hình
                            MyFunction.clearArr(nextCoord);
                            rec.saveCoord(nextCoord);
                            Board.setNowHinhHoc(rec); // vẽ xong sẽ được đưa vào chế độ được chọn, để xoay zoom...
                        }
                        repaint();
                        break;
                    }
                    case PARALLELOGRAM:
                    {

                        MyFunction.clearArr(nextDrawing);// phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        MyFunction.clearArr(nextPoint);
                        MyFunction.clearArr(nextCoord);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        Parallelogram par = new Parallelogram(nextDrawing, nextPoint, chooseColor); // khởi tạo class HCN
                        if (startXY.X != -1 && startXY.Y != -1) {
                            if(startXY.X == mouseXY.X)
                                mouseXY.X++;
                            if(startXY.Y == mouseXY.Y)
                                mouseXY.Y++;
                            par.setParallelogram(startXY, mouseXY, chooseLineMode); //  hàm set HC
                            par.saveCoord(nextCoord);
                            par.draw(pointXY, chooseSideMode); // cho vẽ hình
                            Board.setNowHinhHoc(par); // vẽ xong sẽ được đưa vào chế độ được chọn, để xoay zoom...
                        }
                        repaint();
                        break;
                    }
                    case TRIANGLE:
                    {

                        MyFunction.clearArr(nextDrawing);// phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        MyFunction.clearArr(nextPoint);
                        MyFunction.clearArr(nextCoord);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        Triangle tri = new Triangle(nextDrawing, nextPoint, chooseColor); // khởi tạo class HCN
                        if (startXY.X != -1 && startXY.Y != -1) {
                            if(startXY.X == mouseXY.X)
                                mouseXY.X++;
                            if(startXY.Y == mouseXY.Y)
                                mouseXY.Y++;
                            if (mouseEvent.isShiftDown()) { // nếu có sự kiện phím shift
                                tri.setTri(startXY, mouseXY, chooseLineMode); // hàm set hình vuông
                            } else {
                                tri.setTriangle(startXY, mouseXY, chooseLineMode, chooseSideMode, pointXY); //  hàm set HCN
                            }
                            tri.saveCoord(nextCoord);
                            tri.draw(); // cho vẽ hình
                            Board.setNowHinhHoc(tri); // vẽ xong sẽ được đưa vào chế độ được chọn, để xoay zoom...
                        }
                        repaint();
                        break;
                    }
                    case TETRAHEDRON:
                    {

                        MyFunction.clearArr(nextDrawing);// phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        MyFunction.clearArr(nextPoint);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        Tetrahedron tet = new Tetrahedron(nextDrawing, nextPoint, chooseColor); // khởi tạo class HCN
                        if (startXY.X != -1 && startXY.Y != -1) {
                            if(startXY.X == mouseXY.X)
                                mouseXY.X++;
                            if(startXY.Y == mouseXY.Y)
                                mouseXY.Y++;
                            tet.setTetrahedron(startXY, mouseXY, chooseLineMode); //  hàm set HC
                            tet.draw(); // cho vẽ hình
                            Board.setNowHinhHoc(tet); // vẽ xong sẽ được đưa vào chế độ được chọn, để xoay zoom...
                        }
                        repaint();
                        break;
                    }
                    case PYRAMID:
                    {

                        MyFunction.clearArr(nextDrawing);// phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        MyFunction.clearArr(nextPoint);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        Pyramid py = new Pyramid(nextDrawing, nextPoint, chooseColor); // khởi tạo class HCN
                        if (startXY.X != -1 && startXY.Y != -1) {
                            py.setPyramid(startXY, mouseXY, chooseLineMode); //  hàm set HC
                            py.draw(); // cho vẽ hình
                            Board.setNowHinhHoc(py); // vẽ xong sẽ được đưa vào chế độ được chọn, để xoay zoom...
                        }
                        repaint();
                        break;
                    }
                    case SELECT:
                    {
                        MyFunction.clearArr(nextDrawing);// phải luôn xóa các nét vẽ và cho vẽ lại khi có sự thay đổi
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
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
                        MyFunction.clearArr(nextPoint);
                        MyFunction.clearArr(nextCoord);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        Ellipse elp = new Ellipse(nextDrawing, nextPoint, chooseColor);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            if(startXY.X == mouseXY.X)
                                mouseXY.X++;
                            if(startXY.Y == mouseXY.Y)
                                mouseXY.Y++;
                            if (mouseEvent.isShiftDown()) {
                                elp.setCircle(startXY, mouseXY, chooseLineMode);
                            } else {
                                elp.setElip(startXY, mouseXY, chooseLineMode);
                            }
                            elp.saveCoord(nextCoord);
                            elp.draw(pointXY, chooseSideMode);
                            Board.setNowHinhHoc(elp);
                        }
                        repaint();
                        break;
                    }
                    case SPHERE: {
                        MyFunction.clearArr(nextDrawing);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        Sphere Glo = new Sphere(nextDrawing, nextPoint, chooseColor);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            Glo.setSphere(startXY, mouseXY, chooseLineMode);
                            Point2D temp = mouseXY;
                            temp.Y = startXY.Y + Math.abs(mouseXY.X - startXY.X) / 2;
                            Glo.setSphere(startXY, temp, chooseLineMode);
                            Glo.draw();
                            Board.setNowHinhHoc(Glo);
                        }
                        repaint();
                        break;
                    }
                    case CONE: {
                        MyFunction.clearArr(nextDrawing);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        Cone con = new Cone(nextDrawing, nextPoint, chooseColor);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            con.setCone(startXY, mouseXY, chooseLineMode);
                            con.draw();
                            Board.setNowHinhHoc(con);
                        }
                        repaint();
                        break;
                    }
                    case CYLINDER: {
                        MyFunction.clearArr(nextDrawing);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        Cylinder Cir = new Cylinder(nextDrawing, nextPoint, chooseColor);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            Cir.setCylinder(startXY, mouseXY, chooseLineMode);
                            Cir.draw();
                            Board.setNowHinhHoc(Cir);
                        }
                        repaint();
                        break;
                    }
                    case ROTATE: {
                        MyFunction.clearArr(nextDrawing);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            //System.out.println(Board.now.tag);
                            if (Board.now != null) {
                                MyFunction.clearArr(nextCoord);
                                Board.rotateNow(startXY, mouseXY);
                                Board.now.saveCoord(nextCoord);
                            }
                            repaint();
                        }
                        break;
                    }
                    case MOVE: {
                        MyFunction.clearArr(nextDrawing);
                        mouseXY.set(mouseEvent.getX() / rectSize, mouseEvent.getY() / rectSize);
                        if (startXY.X != -1 && startXY.Y != -1) {
                            //System.out.println(Board.now.tag);
                            if (Board.now != null) {
                                MyFunction.clearArr(nextCoord);
                                Board.moveNow(startXY, mouseXY);
                                Board.now.saveCoord(nextCoord);
                            }
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
