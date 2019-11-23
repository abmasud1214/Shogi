import javafx.scene.control.IndexRange;

import java.util.Arrays;

public class Board {
    private Piece[][] board;
    private Piece[] capturedOne;
    private Piece[] capturedTwo;

    public Board(){
        board = new Piece[9][9];
        capturedOne = new Piece[40];
        capturedTwo = new Piece[40];

        populateSide(1);
        populateSide(-1);
    }

    private void populateSide(int dir){
        int base = 0;
        if(dir == -1){
            base = 8;
        }
        Piece piece = new Piece("Gyoku", dir, 4, base);
        board[base][4] = piece;
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
        piece = new Piece("Kaku", dir, (board.length - 1 - (base + 1*dir)), (base + 1*dir));
        board[base + 1*dir][board.length - 1 - (base + 1*dir)] = piece;
        piece = new Piece("Hisha", dir, (base + 1*dir), (base + 1*dir));
        board[base + 1*dir][base + 1*dir] = piece;

        for(int i = 0; i < board.length; i++){
            Piece fuhyo = new Piece("Fuhyo", dir, i, (base + 2*dir));
            board[base + 2*dir][i] = fuhyo;
        }
    }

    public Piece getPiece(int x, int y){
        try{
            return board[x][y];
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }

    public Piece getCaptured(int x, int y){
        if(y == 1){
            if(capturedOne[x] != null){
                return capturedOne[x];
            } else {
                return null;
            }
        } else {
            if(capturedTwo[x] != null){
                return capturedTwo[x];
            } else {
                return null;
            }
        }
    }

    public void movePiece(Piece piece, int newY, int newX){
        try{
            int x = piece.getPosX();
            int y = piece.getPosY();
            if(x == -1 && y == -1){
                if(piece.getDir() == 1){
                    removeFromCaptured(piece, capturedOne);
                } else {
                    removeFromCaptured(piece, capturedTwo);
                }
            } else {
                board[y][x] = null;
            }
            if(board[newY][newX] != null) {
                killPiece(board[newY][newX]);
            }
            board[newY][newX] = piece;
            piece.setPosX(newX);
            piece.setPosY(newY);
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    private void removeFromCaptured(Piece piece, Piece[] captured) {
        for(int i = 0; i < captured.length; i++){
            if(captured[i] == piece){
                captured[i] = null;
                break;
            }
        }
    }

    private void killPiece(Piece piece){
        if(piece.getDir() == 1){
            int i = 0;
            while(capturedTwo[i] != null) i++;
            capturedTwo[i] = new Piece(piece.getName(), -1, -1, -1);
        } else{
            int i = 0;
            while(capturedOne[i] != null) i++;
            capturedOne[i] = new Piece(piece.getName(), 1, -1, -1);
        }
        int i = 5;
    }

    public boolean[][] legalMoves(Piece piece){
        boolean[][] lMoves = new boolean[9][9];
        if(piece == null){
            boolean[] arr = new boolean[9];
            Arrays.fill(arr, false);
            Arrays.fill(lMoves, arr);
            return lMoves;
        }
        if(piece.getPosX() == -1 && piece.getPosY() == -1){
            for(int i = 0; i < lMoves.length; i++){
                for(int j = 0; j < lMoves[i].length; j++){
                    lMoves[i][j] = true;
                    if(piece.getName().equals("Fuhyo")
                            && fuhyoInFile(j)){
                        lMoves[i][j] = false;
                    }
                    if(piece.getName().equals("Fuhyo")
                            || piece.getName().equals("Kyosha")
                            || piece.getName().equals("Kei-Ma")
                            && (i == 4 + 4*(piece.getDir()))){
                        lMoves[i][j] = false;
                    }
                    if(piece.getName().equals("Kei-Ma")
                            && (i == 4 + 3*(piece.getDir()))){
                        lMoves[i][j] = false;
                    }
                    if(board[i][j] != null){
                        lMoves[i][j] = false;
                    }
                }
            }
            return lMoves;
        } else {
            int[][] possibleMoves = piece.getPossibleMoves();
            for (int i = 0; i < possibleMoves.length; i++) {
                for (int j = 0; j < possibleMoves[i].length; j++) {
                    int col = (i - 1) + piece.getPosX();
                    int row = (possibleMoves[i][j])*piece.getDir() + piece.getPosY();
                    if (col >= 0 && col < lMoves.length
                            && row >= 0 && row < lMoves[0].length
                            && (board[row][col] == null || (board[row][col] != null
                            && board[row][col].getDir() != piece.getDir()))) {
                        lMoves[row][col] = true;
                    } else {
                        try {
                            lMoves[row][col] = false;
                        } catch(IndexOutOfBoundsException e){}
                    }
                }
            }


            if(piece.isDiagLimit()){
                legalMovesHelper(lMoves, piece, 1, 1);
                legalMovesHelper(lMoves, piece, 1, -1);
                legalMovesHelper(lMoves, piece, -1, 1);
                legalMovesHelper(lMoves, piece, -1, -1);
            }
            if(piece.isOrthLimit()){
                legalMovesHelper(lMoves, piece, 0, 1);
                legalMovesHelper(lMoves, piece, 0, -1);
                legalMovesHelper(lMoves, piece, -1, 0);
                legalMovesHelper(lMoves, piece, 1, 0);
            }
            if(piece.isFwLimit()){
                legalMovesHelper(lMoves, piece, 0, 1);
            }
        }

        return lMoves;
    }

    private void legalMovesHelper(boolean[][] lMoves, Piece piece, int a, int b){
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

    private boolean fuhyoInFile(int col){
        for(int row = 0; row < board.length; row++){
            if(board[row][col] != null && board[row][col].getName().equals("Fuhyo")){
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String str = "";
        for (Piece[] pieces: board) {
            for (Piece piece: pieces){
                if(piece != null){
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
