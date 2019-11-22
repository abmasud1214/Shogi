import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CapturedGUI extends BoardGUI {
    private Board board;
    private StackPane[][] rectList;

    public CapturedGUI(Board board){
        final int width = 45;
        final int height = 45;
        this.board = board;
        rectList = new StackPane[2][9];

        for(int i = 0; i < 2; i++){
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
    }
}
