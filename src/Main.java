import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new BoardGUI(), 540, 540);

        stage.setScene(scene);
        stage.setTitle("Shogi");
        stage.show();
    }
}
