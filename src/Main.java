import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        /*Board board = new Board();
        BoardGUI boardGUI = new BoardGUI(board);
        CapturedGUI cGUI1 = new CapturedGUI(board, boardGUI, 1);
        CapturedGUI cGUI2 = new CapturedGUI(board, boardGUI, 2);
        HBox hBox = new HBox();
        hBox.setOnMouseClicked(e -> {cGUI1.drawPieces(); cGUI2.drawPieces();});
        hBox.getChildren().addAll(cGUI1, boardGUI, cGUI2);*/
        BoardListener boardListener = new BoardListener();
        Scene scene = new Scene(boardListener);

        stage.setScene(scene);
        stage.setTitle("Shogi");
        stage.show();
    }
}
