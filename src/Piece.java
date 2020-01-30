public class Piece {
    private String name;
    private String originalName;
    private String promoteName;

    private int posX, posY;
    private int dir;
    private int[][] possibleMoves;
    private boolean fwLimit = false, orthLimit = false, diagLimit = false;


    public Piece(String name, int dir, int posX, int posY){
        this.name = name;
        this.dir = dir;
        this.posX = posX;
        this.posY = posY;
        this.originalName = name;

        int left[];
        int center[];
        int right[];
        if(name.equals("Gyoku")){
            left = new int[]{-1, 0, 1};
            center = new int[]{-1, 1};
            right = new int[]{-1, 0, 1};

            promoteName = "Gyoku";
            possibleMoves = new int[][]{left, center, right};
        } else if(name.equals("Kin")){
            left = new int[]{0, 1};
            center = new int[]{-1, 1};
            right = new int[]{0, 1};

            promoteName = "Kin";
            possibleMoves = new int[][]{left, center, right};
        } else if(name.equals("Gin")){
            left = new int[]{-1, 1};
            center = new int[]{1};
            right = new int[]{-1, 1};

            promoteName = "Narigin";
            possibleMoves = new int[][]{left, center, right};
        } else if(name.equals("Kei-Ma")){
            left = new int[]{2};
            center = new int[]{};
            right = new int[]{2};

            promoteName = "Narikei";
            possibleMoves = new int[][]{left, center, right};
        } else if(name.equals("Kyosha")){
            left = new int[]{};
            center = new int[]{1};
            right = new int[]{};

            promoteName = "Narikyo";
            possibleMoves = new int[][]{left, center, right};
            fwLimit = true;
        } else if(name.equals("Kaku")){
            left = new int[]{-1, 1};
            center = new int[]{};
            right = new int[]{-1, 1};

            promoteName = "Ryuma";
            possibleMoves = new int[][]{left, center, right};
            diagLimit = true;
        } else if(name.equals("Hisha")){
            left = new int[]{0};
            center = new int[]{-1, 1};
            right = new int[]{0};

            promoteName = "Ryu";
            possibleMoves = new int[][]{left, center, right};
            orthLimit = true;
        } else if(name.equals("Fuhyo")){
            left = new int[]{};
            center = new int[]{1};
            right = new int[]{};

            promoteName = "Tokin";
            possibleMoves = new int[][]{left, center, right};
        }
    }

    public void promote(){
        int left[];
        int center[];
        int right[];

        if(name == "Gin" || name == "Kei-Ma" || name == "Kyosha"
                || name == "Fuhyo"){
            left = new int[]{0, 1};
            center = new int[]{-1, 1};
            right = new int[]{0, 1};

            fwLimit = false;
            possibleMoves = new int[][]{left, center, right};
        } else if (name == "Kaku" || name == "Hisha"){
            left = new int[]{-1, 0, 1};
            center = new int[]{-1, 1};
            right = new int[]{-1, 0, 1};

            possibleMoves = new int[][]{left, center, right};
        }
        name = promoteName;
    }

    public String getOriginalName(){
        return originalName;
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
