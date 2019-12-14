import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.w3c.dom.css.Rect;

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

        drawPieces();
    }

    public void drawLegal(Piece piece){
        legalMoves = board.legalMoves(piece);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (legalMoves[i][j]) {
                    Rectangle rect = (Rectangle) rectList[i][j].getChildren().get(0);
                    rect.setFill(Color.LIGHTBLUE);
                }
            }
        }
    }

    public void drawPieces(){
        Text t;
        for(int i = 0; i < rectList.length; i++){
            for(int j = 0; j < rectList[i].length; j++){
                Piece piece = board.getPiece(i, j);
                ObservableList<Node> nodeList = rectList[i][j].getChildren();
                if(nodeList.size() == 2){
                    nodeList.remove(1);
                }
                if(piece != null){
                    switch (piece.getName()){
                        case "Gyoku":
                            t = new Text("K");
                            nodeList.add(t);
                            break;
                        case "Kin":
                            t = new Text("G");
                            nodeList.add(t);
                            break;
                        case "Gin":
                            t = new Text("S");
                            nodeList.add(t);
                            break;
                        case "Kei-Ma":
                            t = new Text("Kn");
                            nodeList.add(t);
                            break;
                        case "Kyosha":
                            t = new Text("L");
                            nodeList.add(t);
                            break;
                        case "Kaku":
                            t = new Text("B");
                            nodeList.add(t);
                            break;
                        case "Hisha":
                            t = new Text("R");
                            nodeList.add(t);
                            break;
                        case "Fuhyo":
                            t = new Text("P");
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
