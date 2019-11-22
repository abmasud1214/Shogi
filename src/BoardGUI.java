import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.w3c.dom.css.Rect;

public class BoardGUI extends Pane {
    private final int sizeX = 540;
    private final int sizeY = 540;
    private Board board;
    private StackPane[][] rectList;

    public BoardGUI(){
        final int width = 45;
        final int height = 45;

        board = new Board();
        rectList = new StackPane[9][9];
        Piece selectedPiece[] = new Piece[1];
        boolean[][][] legalMoves = new boolean[1][][];

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

        this.setOnMouseClicked(e -> {
            double posX = e.getX();
            double posY = e.getY();

            int col = (int) (posX / width);
            int row = (int) (posY / height);
            boolean x = true;

            if(col < 0 || row < 0 || col >= 9 || row >= 9){
                x = false;
            }

            if(x) {
                Rectangle r = (Rectangle) rectList[row][col].getChildren().get(0);
                r.setFill(Color.RED);
                Rectangle rect;

                if (selectedPiece[0] == null) {
                    selectedPiece[0] = board.getPiece(row, col);
                    legalMoves[0] = board.legalMoves(selectedPiece[0]);
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (legalMoves[0][i][j]) {
                                rect = (Rectangle) rectList[i][j].getChildren().get(0);
                                rect.setFill(Color.LIGHTBLUE);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            rect = (Rectangle) rectList[i][j].getChildren().get(0);
                            rect.setFill(Color.WHITE);
                            if (legalMoves[0][i][j] && i == row && j == col) {
                                board.movePiece(selectedPiece[0], row, col);
                            }
                        }
                    }
                    selectedPiece[0] = null;
                    drawPieces();
                    System.out.println(board.toString());
                }
            }
        });
    }

    private void drawPieces(){
        Text t;
        for(int i = 0; i < rectList.length; i++){
            for(int j = 0; j < rectList[i].length; j++){
                Piece piece = board.getPiece(i, j);
                Rectangle r = (Rectangle) rectList[i][j].getChildren().get(0);
                rectList[i][j].getChildren().clear();
                rectList[i][j].getChildren().add(r);
                if(piece != null){
                    switch (piece.getName()){
                        case "Gyoku":
                            t = new Text("K");
                            rectList[i][j].getChildren().add(t);
                            break;
                        case "Kin":
                            t = new Text("G");
                            rectList[i][j].getChildren().add(t);
                            break;
                        case "Gin":
                            t = new Text("S");
                            rectList[i][j].getChildren().add(t);
                            break;
                        case "Kei-Ma":
                            t = new Text("Kn");
                            rectList[i][j].getChildren().add(t);
                            break;
                        case "Kyosha":
                            t = new Text("L");
                            rectList[i][j].getChildren().add(t);
                            break;
                        case "Kaku":
                            t = new Text("B");
                            rectList[i][j].getChildren().add(t);
                            break;
                        case "Hisha":
                            t = new Text("R");
                            rectList[i][j].getChildren().add(t);
                            break;
                        case "Fuhyo":
                            t = new Text("P");
                            rectList[i][j].getChildren().add(t);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
