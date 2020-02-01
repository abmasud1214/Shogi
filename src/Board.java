import javafx.scene.control.IndexRange;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private Piece[][] board;
    private Piece[] capturedOne;
    private Piece[] capturedTwo;
    private int turn = 1;

    private Piece kingOne;
    private Piece kingTwo;

    private boolean kingOneInCheck;
    private boolean kingTwoInCheck;
    private ArrayList<Piece> king1CheckPieces;
    private ArrayList<Piece> king2CheckPieces;
    private ArrayList<Piece> teamOnePieces;
    private ArrayList<Piece> teamTwoPieces;


    public Board() {
        board = new Piece[9][9];
        capturedOne = new Piece[40];
        capturedTwo = new Piece[40];

        king1CheckPieces = new ArrayList<>();
        king2CheckPieces = new ArrayList<>();

        populateSide(1);
        populateSide(-1);
        updatePieceLists();
    }

    private void populateSide(int dir) {
        int base = 0;
        if (dir == -1) {
            base = 8;
        }
        Piece piece = new Piece("Gyoku", dir, 4, base);
        board[base][4] = piece;
        if(dir == 1){
            kingOne = piece;
        } else {
            kingTwo = piece;
        }
        piece = new Piece("Kin", dir, 3, base);
        board[base][3] = piece;
        piece = new Piece("Kin", dir, 5, base);
        board[base][5] = piece;
        piece = new Piece("Gin", dir, 2, base);
        board[base][2] = piece;
        piece = new Piece("Gin", dir, 6, base);
        board[base][6] = piece;
        piece = new Piece("Kei-Ma", dir, 1, base);
        board[base][1] = piece;
        piece = new Piece("Kei-Ma", dir, 7, base);
        board[base][7] = piece;
        piece = new Piece("Kyosha", dir, 0, base);
        board[base][0] = piece;
        piece = new Piece("Kyosha", dir, 8, base);
        board[base][8] = piece;
        piece = new Piece("Kaku", dir, (board.length - 1 - (base + 1 * dir)), (base + 1 * dir));
        board[base + 1 * dir][board.length - 1 - (base + 1 * dir)] = piece;
        piece = new Piece("Hisha", dir, (base + 1 * dir), (base + 1 * dir));
        board[base + 1 * dir][base + 1 * dir] = piece;

        for (int i = 0; i < board.length; i++) {
            Piece fuhyo = new Piece("Fuhyo", dir, i, (base + 2 * dir));
            board[base + 2 * dir][i] = fuhyo;
        }
    }

    private void updatePieceLists(){
        teamOnePieces = new ArrayList<>();
        teamTwoPieces = new ArrayList<>();
        for (Piece[] piecerow: board) {
            for(Piece piece: piecerow){
                if(piece != null && piece.getDir() == 1){
                    teamOnePieces.add(piece);
                } else if (piece != null && piece.getDir() == -1){
                    teamTwoPieces.add(piece);
                }
            }
        }
    }

    public Piece getPiece(int y, int x) {
        try {
            return board[y][x];
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int[] getPosition(Piece piece) {
        if(piece.getPosX() != -1 && piece.getPosY() != -1){
            int[] pos = new int[]{0, piece.getPosY(), piece.getPosX()};
            return pos;
        }

        for (int i = 0; i < capturedOne.length; i++) {
            if (capturedOne[i] == piece) {
                int[] pos = new int[]{1, i};
                return pos;
            }
        }

        for (int i = 0; i < capturedTwo.length; i++) {
            if (capturedTwo[i] == piece) {
                int[] pos = new int[]{2, i};
                return pos;
            }
        }

        return null;
    }

    public Piece getCaptured(int position, int team) {
        if (team == 1) {
            if (capturedOne[position] != null) {
                return capturedOne[position];
            } else {
                return null;
            }
        } else {
            if (capturedTwo[position] != null) {
                return capturedTwo[position];
            } else {
                return null;
            }
        }
    }

    public int getTurn() {
        return turn;
    }

    public void movePiece(Piece piece, int newY, int newX, boolean promote) {
        try {
            int x = piece.getPosX();
            int y = piece.getPosY();
            if (x == -1 && y == -1) {
                if (piece.getDir() == 1) {
                    removeFromCaptured(piece, capturedOne);
                } else {
                    removeFromCaptured(piece, capturedTwo);
                }
            } else {
                board[y][x] = null;
            }
            if (board[newY][newX] != null) {
                killPiece(board[newY][newX]);
            }
            board[newY][newX] = piece;
            piece.setPosX(newX);
            piece.setPosY(newY);
            updatePieceLists();

            //promote pawn, lance, knight
            if(newY == 4 + 4*piece.getDir() && (piece.getName() == "Fuhyo" ||
                    piece.getName() == "Kei-Ma" || piece.getName() == "Kyosha")){
                piece.promote();
            } else if (newY == 4 + 3*piece.getDir() && (piece.getName() == "Kei-Ma")){
                piece.promote();
            }

            //regular promote
            if(promote){
                piece.promote();
            }

            updateCheckLists(1);
            updateCheckLists(-1);

            turn *= -1;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void updateCheckLists(int dir){
        ArrayList<Piece> checkList;
        ArrayList<Piece> pieceList;
        Piece king;
        if(dir == 1){
            checkList = king2CheckPieces;
            king = kingTwo;
            pieceList = teamOnePieces;
        } else {
            checkList = king1CheckPieces;
            king = kingOne;
            pieceList = teamTwoPieces;
        }

        boolean[][] pieceMoves;
        for(Piece piece : pieceList){
            pieceMoves = legalMoves(piece);
            if(piece.getDir() == 1){
                if(king.getPosY() != -1 && pieceMoves[king.getPosY()][king.getPosX()]){
                    if(dir == 1){
                        kingTwoInCheck = true;
                    } else {
                        kingOneInCheck = true;
                    }
                    checkList.add(piece);
                } else {
                    checkList.remove(piece);
                }

            }
        }
        if(checkList.isEmpty()){
            if(dir == 1){
                kingTwoInCheck = false;
            } else {
                kingOneInCheck = false;
            }
        } else {
            boolean checkmate = checkmate(dir * -1);
            if(checkmate) System.out.println("CHECKMATE");
        }
    }

    private void removeFromCaptured(Piece piece, Piece[] captured) {
        for (int i = 0; i < captured.length; i++) {
            if (captured[i] == piece) {
                captured[i] = null;
                break;
            }
        }
    }

    private void killPiece(Piece piece) {
        if (piece.getDir() == 1) {
            int i = 0;
            while (capturedTwo[i] != null) i++;
            capturedTwo[i] = new Piece(piece.getOriginalName(), -1, -1, -1);
            king2CheckPieces.remove(piece);
            if(king2CheckPieces.isEmpty()) kingTwoInCheck = false;
        } else {
            int i = 0;
            while (capturedOne[i] != null) i++;
            capturedOne[i] = new Piece(piece.getOriginalName(), 1, -1, -1);
            king1CheckPieces.remove(piece);
            if(king1CheckPieces.isEmpty()) kingOneInCheck = false;
        }
        int i = 5;
    }

    public boolean[][] legalMoves(Piece piece) {
        boolean[][] lMoves = new boolean[9][9];
        boolean nullPiece = (piece == null);
        if (nullPiece) {
            boolean[] arr = new boolean[9];
            Arrays.fill(arr, false);
            Arrays.fill(lMoves, arr);
            return lMoves;
        }
        if (piece.getPosX() == -1 && piece.getPosY() == -1) {
            for (int i = 0; i < lMoves.length; i++) {
                for (int j = 0; j < lMoves[i].length; j++) {
                    lMoves[i][j] = true;
                    if (piece.getName().equals("Fuhyo")
                            && fuhyoInFile(j, piece.getDir())) {
                        lMoves[i][j] = false;
                    }
                    if ((piece.getName().equals("Fuhyo")
                            || piece.getName().equals("Kyosha")
                            || piece.getName().equals("Kei-Ma"))
                            && (i == 4 + 4 * (piece.getDir()))) {
                        lMoves[i][j] = false;
                    }
                    if (piece.getName().equals("Kei-Ma")
                            && (i == 4 + 3 * (piece.getDir()))) {
                        lMoves[i][j] = false;
                    }
                    if (board[i][j] != null) {
                        lMoves[i][j] = false;
                    }
                }
            }
        } else {
            int[][] possibleMoves = piece.getPossibleMoves();
            for (int i = 0; i < possibleMoves.length; i++) {
                for (int j = 0; j < possibleMoves[i].length; j++) {
                    int col = (i - 1) + piece.getPosX();
                    int row = (possibleMoves[i][j]) * piece.getDir() + piece.getPosY();
                    if (col >= 0 && col < lMoves.length
                            && row >= 0 && row < lMoves[0].length
                            && (board[row][col] == null || (board[row][col] != null
                            && board[row][col].getDir() != piece.getDir()))) {
                        lMoves[row][col] = true;
                    } else {
                        try {
                            lMoves[row][col] = false;
                        } catch (IndexOutOfBoundsException e) {
                        }
                    }
                }
            }


            if (piece.isDiagLimit()) {
                legalMovesHelper(lMoves, piece, 1, 1);
                legalMovesHelper(lMoves, piece, 1, -1);
                legalMovesHelper(lMoves, piece, -1, 1);
                legalMovesHelper(lMoves, piece, -1, -1);
            }
            if (piece.isOrthLimit()) {
                legalMovesHelper(lMoves, piece, 0, 1);
                legalMovesHelper(lMoves, piece, 0, -1);
                legalMovesHelper(lMoves, piece, -1, 0);
                legalMovesHelper(lMoves, piece, 1, 0);
            }
            if (piece.isFwLimit()) {
                legalMovesHelper(lMoves, piece, 0, 1);
            }
        }

        if(piece.getDir() == 1 && kingOneInCheck){
            lMoves = trimCheck(lMoves, piece);
        } else if (piece.getDir() == -1 && kingTwoInCheck){
            lMoves = trimCheck(lMoves, piece);
        }

        return lMoves;
    }

    private void legalMovesHelper(boolean[][] lMoves, Piece piece, int a, int b) {
        for (int i = 1; i < 10; i++) {
            int col = a * i + piece.getPosX();
            int row = b * i * piece.getDir() + piece.getPosY();
            if (col >= 0 && col < lMoves.length
                    && row >= 0 && row < lMoves[0].length
                    && (board[row][col] == null || (board[row][col] != null
                    && board[row][col].getDir() != piece.getDir()))) {
                lMoves[row][col] = true;
            }
            if (col >= 0 && row >= 0
                    && col < 9 && row < 9
                    && board[row][col] != null) {
                break;
            }
        }
    }

    private boolean[][] trimCheck(boolean[][] initialLMoves, Piece piece){
        boolean[][] trimmedMoves = new boolean[9][9];
        //check pieces is all the pieces putting king in check
        ArrayList<Piece> checkPieces;
        Piece king;
        if(piece.getDir() == 1){
            checkPieces = king1CheckPieces;
            king = kingOne;
        } else {
            checkPieces = king2CheckPieces;
            king = kingTwo;
        }

        //initial positions
        int oldX = piece.getPosX(), oldY = piece.getPosY();
        Piece killedPiece;

        for(int i = 0; i < initialLMoves.length; i++){
            for(int j = 0; j < initialLMoves[i].length; j++){
                trimmedMoves[i][j] = initialLMoves[i][j];
                if(initialLMoves[i][j]){
                    boolean checkSpace = false;

                    if(oldX != -1 || oldY != -1){
                        board[oldX][oldY] = null;
                    }
                    killedPiece = null;
                    if(board[i][j] != null){
                        killedPiece = board[i][j];
                        killedPiece.setPosX(-1);
                        killedPiece.setPosY(-1);
                    }
                    board[i][j] = piece;
                    piece.setPosX(j);
                    piece.setPosY(i);

                    for(Piece p : checkPieces){
                        boolean[][] attackMoves = legalMoves(p);
                        //make sure king pos is never -1,-1 for this
                        if(attackMoves[king.getPosY()][king.getPosX()]){
                            checkSpace = true;
                        }
                    }

                    if(oldX != -1 || oldY != -1) {
                        board[oldY][oldX] = piece;
                    }
                    piece.setPosY(oldY);
                    piece.setPosX(oldX);

                    board[i][j] = null;

                    if(killedPiece != null) {
                        board[i][j] = killedPiece;
                        killedPiece.setPosY(i);
                        killedPiece.setPosX(j);
                    }

                    if(checkSpace){
                        trimmedMoves[i][j] = false;
                    }
                }
            }
        }

        return trimmedMoves;
    }

    private boolean fuhyoInFile(int col, int team) {
        for (int row = 0; row < board.length; row++) {
            if (board[row][col] != null && board[row][col].getDir() == team
                    && board[row][col].getName().equals("Fuhyo")) {
                return true;
            }
        }
        return false;
    }

    private boolean[][] allMoves(int team){
        boolean[][] possibleMoves = new boolean[9][9];
        boolean[][] lMoves;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                possibleMoves[i][j] = false;
                Piece p = board[i][j];
                if(p != null && p.getDir() == team){
                    lMoves = legalMoves(p);
                    for(int y = 0; y < possibleMoves.length; y++){
                        for(int x = 0; x < possibleMoves.length; x++){
                            possibleMoves[y][x] = possibleMoves[y][x] || lMoves[y][x];
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }

    private boolean checkmate(int team){
        boolean[][] possibleMoves = allMoves(team);
        boolean availableMove = false;
        for(int i = 0; i < possibleMoves.length; i++){
            for(int j = 0; j < possibleMoves[0].length; j++){
                availableMove = availableMove || possibleMoves[i][j];
            }
        }

        return !availableMove;
    }

    public String toString() {
        String str = "";
        for (Piece[] pieces : board) {
            for (Piece piece : pieces) {
                if (piece != null) {
                    str += piece.toString() + " ";
                } else {
                    str += "null ";
                }
            }
            str += '\n';
        }
        return str;
    }
}
