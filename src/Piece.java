public class Piece {
    private String name;
    private int posX, posY;
    private int dir;
    private int[][] possibleMoves;
    private boolean fwLimit = false, orthLimit = false, diagLimit = false;


    public Piece(String name, int dir, int posX, int posY){
        this.name = name;
        this.dir = dir;
        this.posX = posX;
        this.posY = posY;

        int left[];
        int center[];
        int right[];
        if(name.equals("Gyoku")){
            left = new int[]{-1, 0, 1};
            center = new int[]{-1, 1};
            right = new int[]{-1, 0, 1};

            possibleMoves = new int[][]{left, center, right};
        } else if(name.equals("Kin")){
            left = new int[]{0, 1};
            center = new int[]{-1, 1};
            right = new int[]{0, 1};

            possibleMoves = new int[][]{left, center, right};
        } else if(name.equals("Gin")){
            left = new int[]{-1, 1};
            center = new int[]{1};
            right = new int[]{-1, 1};

            possibleMoves = new int[][]{left, center, right};
        } else if(name.equals("Kei-Ma")){
            left = new int[]{2};
            center = new int[]{};
            right = new int[]{2};

            possibleMoves = new int[][]{left, center, right};
        } else if(name.equals("Kyosha")){
            left = new int[]{};
            center = new int[]{1};
            right = new int[]{};

            possibleMoves = new int[][]{left, center, right};
            fwLimit = true;
        } else if(name.equals("Kaku")){
            left = new int[]{-1, 1};
            center = new int[]{};
            right = new int[]{-1, 1};

            possibleMoves = new int[][]{left, center, right};
            diagLimit = true;
        } else if(name.equals("Hisha")){
            left = new int[]{0};
            center = new int[]{-1, 1};
            right = new int[]{0};

            possibleMoves = new int[][]{left, center, right};
            orthLimit = true;
        } else if(name.equals("Fuhyo")){
            left = new int[]{};
            center = new int[]{1};
            right = new int[]{};

            possibleMoves = new int[][]{left, center, right};
        }
    }

    public int[][] getPossibleMoves(){
        return possibleMoves;
    }

    public String getName(){
        return name;
    }

    public int getDir() {
        return dir;
    }

    public int getPosX(){
        return posX;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public int getPosY(){
        return posY;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public boolean isDiagLimit(){
        return diagLimit;
    }

    public boolean isFwLimit(){
        return fwLimit;
    }

    public boolean isOrthLimit(){
        return orthLimit;
    }

    public String toString(){
        return name;
    }
}
