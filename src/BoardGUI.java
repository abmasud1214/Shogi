import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.w3c.dom.css.Rect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class BoardGUI extends Pane {

    private Board board;
    private StackPane[][] rectList;
    private Piece selectedPiece;
    private boolean[][] legalMoves;

    public BoardGUI(Board board){
        final int width = 45;
        final int height = 45;

        this.board = board;
        rectList = new StackPane[9][9];
        boolean[][] legalMoves = new boolean[9][9];

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                Rectangle rect = new Rectangle(width,  height);
                rect.fillProperty().setValue(Color.WHITE);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(1);
                rect.setX(width * j);
                rect.setY(height * i);

                rectList[i][j] = new StackPane();
                rectList[i][j].getChildren().add(rect);
                rectList[i][j].setLayoutX(width * j);
                rectList[i][j].setLayoutY(height * i);
                this.getChildren().add(rectList[i][j]);
            }
        }

        try {
            drawPieces();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void drawLegal(Piece piece, boolean promote){
        legalMoves = board.legalMoves(piece);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (legalMoves[i][j]) {
                    Rectangle rect = (Rectangle) rectList[i][j].getChildren().get(0);
                    rect.setFill(Color.LIGHTBLUE);
                    if(((i == 4 + (2 * piece.getDir()))
                            || (i == 4 + (3 * piece.getDir())
                            || (i == 4 + (4 * piece.getDir())))
                            || (piece.getPosY() == 4 + (2 * piece.getDir()))
                            || (piece.getPosY() == 4 + (3 * piece.getDir()))
                            || (piece.getPosY() == 4 + (4 * piece.getDir()))) && promote){
                        rect.setFill(Color.LIGHTGREEN);
                    }
                }
            }
        }
    }

    public void drawPieces() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\abmasud\\IdeaProjects\\Shogi\\Shogi\\src\\Resources\\emptypiece.png");
        Image image = new Image(fileInputStream);
        Image image2 = new Image(new FileInputStream("C:\\Users\\abmasud\\IdeaProjects\\Shogi\\Shogi\\src\\Resources\\emptypiece2.png"));
        ImageView imageView;

        Text t;
        for(int i = 0; i < rectList.length; i++){
            for(int j = 0; j < rectList[i].length; j++){
                Piece piece = board.getPiece(i, j);
                ObservableList<Node> nodeList = rectList[i][j].getChildren();
                if(nodeList.size() == 3){
                    nodeList.remove(1);
                    nodeList.remove(1);
                }
                if(piece != null){
                    switch (piece.getName()){
                        case "Gyoku":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("K");
                            nodeList.add(t);
                            break;
                        case "Kin":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("G");
                            nodeList.add(t);
                            break;
                        case "Gin":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("S");
                            nodeList.add(t);
                            break;
                        case "Narigin":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("S*");
                            nodeList.add(t);
                            break;
                        case "Kei-Ma":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("Kn");
                            nodeList.add(t);
                            break;
                        case "Narikei":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("Kn*");
                            nodeList.add(t);
                            break;
                        case "Kyosha":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("L");
                            nodeList.add(t);
                            break;
                        case "Narikyo":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("L*");
                            nodeList.add(t);
                            break;
                        case "Kaku":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("B");
                            nodeList.add(t);
                            break;
                        case "Ryuma":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            t = new Text("B*");
                            nodeList.add(imageView);
                            nodeList.add(t);
                            break;
                        case "Hisha":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("R");
                            nodeList.add(t);
                            break;
                        case "Ryu":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("R*");
                            nodeList.add(t);
                            break;
                        case "Fuhyo":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("P");
                            nodeList.add(t);
                            break;
                        case "Tokin":
                            if(piece.getDir() == 1){
                                imageView = new ImageView(image2);
                            } else {
                                imageView = new ImageView(image);
                            }
                            imageView.setFitWidth(45);
                            imageView.setPreserveRatio(true);
                            nodeList.add(imageView);
                            t = new Text("P*");
                            nodeList.add(t);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void drawRed(int row, int col) {
        Rectangle rect = (Rectangle) rectList[row][col].getChildren().get(0);
        rect.setFill(Color.RED);
    }

    public void clearColor(int row, int col){
        Rectangle rect = (Rectangle) rectList[row][col].getChildren().get(0);
        rect.setFill(Color.WHITE);
    }

    public void clearLegal() {
        for(int i = 0; i < rectList.length; i++){
            for(int j = 0; j < rectList[i].length; j++){
                clearColor(i, j);
            }
        }
    }
}
