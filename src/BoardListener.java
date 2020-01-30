import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class BoardListener extends HBox {
    private Board board;
    private BoardGUI boardGUI;
    private CapturedGUI capturedGUI1;
    private CapturedGUI capturedGUI2;
    private boolean promote;

    final int width = 45;
    final int height = 45;

    private Piece selectedPiece;

    public BoardListener() {
        board = new Board();
        boardGUI = new BoardGUI(board);
        capturedGUI1 = new CapturedGUI(board, boardGUI, 1);
        capturedGUI2 = new CapturedGUI(board, boardGUI, 2);
        Button promoteBtn = new Button("Promote");

        promote = false;

        this.getChildren().addAll(capturedGUI1, boardGUI, capturedGUI2, promoteBtn);

        this.setOnMouseClicked(e -> {
            double xPos = e.getX();
            double yPos = e.getY();

            if (0 < xPos && xPos < capturedGUI1.getWidth()) {
                handleCaptured1(xPos, yPos);
            } else if (capturedGUI1.getWidth() < xPos && xPos < boardGUI.getWidth() + capturedGUI1.getWidth()) {
                handleBoard(xPos - capturedGUI1.getWidth(), yPos);
            } else {
                handleCaptured2(xPos - capturedGUI1.getWidth() - boardGUI.getWidth(), yPos);
            }
        });

        promoteBtn.setOnAction(e -> {
            if (selectedPiece != null) {
                promote = !promote;
                boardGUI.drawLegal(selectedPiece, promote);
            }
        });
    }

    private void handleCaptured1(double posX, double posY) {
        int col = (int) (posX / width);
        int row = (int) (posY / height);

        if (selectedPiece == null) {
            int l = row * 2 + col;
            selectedPiece = board.getCaptured(l, 1);
            if (selectedPiece != null && selectedPiece.getDir() != board.getTurn()) {
                selectedPiece = null;
            }
            if (selectedPiece != null) {
                capturedGUI1.drawRed(row, col);
                boardGUI.drawLegal(selectedPiece, false);
            }
        } else {
            clearRed();
            boardGUI.clearLegal();
            selectedPiece = null;
        }
    }

    private void handleBoard(double posX, double posY) {
        int col = (int) (posX / width);
        int row = (int) (posY / height);

        if (selectedPiece == null) {
            selectedPiece = board.getPiece(row, col);
            if (selectedPiece != null && selectedPiece.getDir() != board.getTurn()) {
                selectedPiece = null;
            }
            if (selectedPiece != null) {
                boardGUI.drawRed(row, col);
                boardGUI.drawLegal(selectedPiece, false);
            }
        } else {
            boolean[][] legalMoves = board.legalMoves(selectedPiece);
            boardGUI.clearLegal();
            clearRed();
            if (legalMoves[row][col]) {
                if (((row == 4 + (2 * selectedPiece.getDir()))
                        || (row == 4 + (3 * selectedPiece.getDir())
                        || (row == 4 + (4 * selectedPiece.getDir())))
                        || (selectedPiece.getPosY() == 4 + (2 * selectedPiece.getDir()))
                        || (selectedPiece.getPosY() == 4 + (3 * selectedPiece.getDir()))
                        || (selectedPiece.getPosY() == 4 + (4 * selectedPiece.getDir()))) && promote) {
                    board.movePiece(selectedPiece, row, col, true);
                } else {
                    board.movePiece(selectedPiece, row, col, false);
                }
                boardGUI.drawPieces();
                capturedGUI1.drawPieces();
                capturedGUI2.drawPieces();
            }
            selectedPiece = null;
        }
    }

    private void handleCaptured2(double posX, double posY) {
        int col = (int) (posX / width);
        int row = (int) (posY / height);

        if (selectedPiece == null) {
            int l = row * 2 + col;
            selectedPiece = board.getCaptured(l, 2);
            if (selectedPiece != null && selectedPiece.getDir() != board.getTurn()) {
                selectedPiece = null;
            }
            if (selectedPiece != null) {
                capturedGUI2.drawRed(row, col);
                boardGUI.drawLegal(selectedPiece, false);
            }
        } else {
            clearRed();
            boardGUI.clearLegal();
            selectedPiece = null;
        }
    }


    private void clearRed() {
        int[] pos = board.getPosition(selectedPiece);
        if (pos[0] == 0) {
            boardGUI.clearColor(pos[1], pos[2]);
        } else if (pos[0] == 1) {
            int row = pos[1] / 2;
            int col = pos[1] % 2;
            capturedGUI1.clearColor(row, col);
        } else if (pos[0] == 2) {
            int row = pos[1] / 2;
            int col = pos[1] % 2;
            capturedGUI2.clearColor(row, col);
        }
    }
}
