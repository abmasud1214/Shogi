import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CapturedGUI extends Pane {
    private Board board;
    private StackPane[][] rectList;
    private BoardGUI boardGUI;
    int capturedType;

    public CapturedGUI(Board board, BoardGUI boardGUI, int capturedType){
        final int width = 45;
        final int height = 45;
        this.board = board;
        this.capturedType = capturedType;
        this.boardGUI = boardGUI;

        rectList = new StackPane[9][2];

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 2; j++){
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

        this.setOnMouseClicked(e -> {
            double posX = e.getX();
            double posY = e.getY();

            int col = (int) (posX / width);
            int row = (int) (posY / height);
            boolean x = true;

            if(col < 0 || row < 0 || col >= 2 || row >= 2){
                x = false;
            }

            if(x) {
                int l = row*rectList[0].length + col;
                this.boardGUI.movePieces(row, col, board.getCaptured(l, capturedType));
                drawPieces();
            }
        });
    }

    public void drawPieces(){
        Text t;
        for(int i = 0; i < rectList.length; i++){
            for(int j = 0; j < rectList[i].length; j++){
                int x = i*rectList[i].length + j;
                Piece piece = board.getCaptured(x, capturedType);
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
