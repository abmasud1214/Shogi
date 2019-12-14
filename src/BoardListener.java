import javafx.scene.layout.HBox;

public class BoardListener extends HBox {
    private Board board;
    private BoardGUI boardGUI;
    private CapturedGUI capturedGUI1;
    private CapturedGUI capturedGUI2;

    final int width = 45;
    final int height = 45;

    private Piece selectedPiece;

    public BoardListener(){
        board = new Board();
        boardGUI = new BoardGUI(board);
        capturedGUI1 = new CapturedGUI(board, boardGUI, 1);
        capturedGUI2 = new CapturedGUI(board, boardGUI, 2);

        System.out.println(board);

        this.getChildren().addAll(capturedGUI1, boardGUI, capturedGUI2);

        this.setOnMouseClicked(e -> {
            double xPos = e.getX();
            double yPos = e.getY();

            if(0 < xPos && xPos < capturedGUI1.getWidth()){
                handleCaptured1(xPos, yPos);
            } else if (capturedGUI1.getWidth() < xPos && xPos < boardGUI.getWidth() + capturedGUI1.getWidth()){
                handleBoard(xPos - capturedGUI1.getWidth(), yPos);
            } else {
                handleCaptured2(xPos - capturedGUI1.getWidth() - boardGUI.getWidth(), yPos);
            }
        });
    }

    private void handleCaptured1(double posX, double posY){
        int col = (int) (posX / width);
        int row = (int) (posY / height);

        if(selectedPiece == null){
            int l = row*2 + col;
            selectedPiece = board.getCaptured(l, 1);
            if(selectedPiece != null) {
                capturedGUI1.drawRed(row, col);
                boardGUI.drawLegal(selectedPiece);
            }
        } else {
            clearRed();
            boardGUI.clearLegal();
            selectedPiece = null;
        }
    }

    private void handleBoard(double posX, double posY){
        int col = (int) (posX / width);
        int row = (int) (posY / height);

        if(selectedPiece == null){
            selectedPiece = board.getPiece(row, col);
            if(selectedPiece != null) {
                boardGUI.drawRed(row, col);
                boardGUI.drawLegal(selectedPiece);
            }
        } else {
            boolean[][] legalMoves = board.legalMoves(selectedPiece);
            boardGUI.clearLegal();
            clearRed();
            if(legalMoves[row][col]){
                board.movePiece(selectedPiece, row, col);
                boardGUI.drawPieces();
                capturedGUI1.drawPieces();
                capturedGUI2.drawPieces();
            }
            selectedPiece = null;
        }
    }

    private void handleCaptured2(double posX, double posY){
        int col = (int) (posX / width);
        int row = (int) (posY / height);

        if(selectedPiece == null){
            int l = row*2 + col;
            selectedPiece = board.getCaptured(l, 2);
            if(selectedPiece != null) {
                capturedGUI2.drawRed(row, col);
                boardGUI.drawLegal(selectedPiece);
            }
        } else {
            clearRed();
            boardGUI.clearLegal();
            selectedPiece = null;
        }
    }


    private void clearRed() {
        int[] pos = board.getPosition(selectedPiece);
        if(pos[0] == 0){
            boardGUI.clearColor(pos[1], pos[2]);
        } else if(pos[0] == 1) {
            int row = pos[1] / 2;
            int col = pos[1] % 2;
            capturedGUI1.clearColor(row, col);
        } else if(pos[0] == 2) {
            int row = pos[1] / 2;
            int col = pos[1] % 2;
            capturedGUI2.clearColor(row, col);
        }
    }
}
