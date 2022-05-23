import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class GameState {
    private int size;
    private TileColor currTileColor;
    private TileColor[][] tileColors;
    private int totalTiles = 4;

    GameState(int size) {
        this.size = size;
        this.tileColors = new TileColor[size][size];
        tileColors[size/2-1][size/2-1] = TileColor.WHITE;
        tileColors[size/2-1][size/2] = TileColor.BLACK;
        tileColors[size/2][size/2-1] = TileColor.BLACK;
        tileColors[size/2][size/2] = TileColor.WHITE;
        currTileColor = TileColor.BLACK;
    }
    
    public TileColor getColor(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {return null;}
        return tileColors[x][y];
    }

    public int getCount(Color color) {
        int cnt = 0;
        TileColor tileColor = null;
        if(color == Color.BLACK) {
            tileColor = TileColor.BLACK; 
        }
        if(color == Color.WHITE) {
            tileColor = TileColor.WHITE;
        }
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                if(tileColors[i][j] == tileColor) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public TileColor[][] getTileColors() {
        return tileColors;
    }

    public TileColor getWinner() {
        if(getCount(Color.BLACK) > getCount(Color.WHITE)){return TileColor.BLACK;}
        else if(getCount(Color.BLACK) < getCount(Color.WHITE)){return TileColor.WHITE;}
        else {return null;}
    }

    public TileColor whosTurn() {
        return currTileColor;
    }

    public boolean isValidPos(int x, int y, TileColor color) {
        int i, j;
        int[] dx = new int[]{0,0,1,-1,1,1,-1,-1};
        int[] dy = new int[]{1,-1,0,0,-1,1,-1,1};

        // int[] dx = new int[]{0};
        // int[] dy = new int[]{1};

        for(int idx = 0; idx < dx.length; idx++) {
            i = x + dx[idx];
            j = y + dy[idx];
            while(i >= 0 && i < size && j >= 0 && j < size && getColor(i, j) != null && getColor(i, j) != color) {
                i += dx[idx];
                j += dy[idx];
            }
            // System.out.println(i);
            // System.out.println(j);
            if (getColor(i, j) == color && (i != x + dx[idx] || j != y + dy[idx])) {return true;}
        }
        return false;
    }

    public void makeTile(int x, int y, TileColor color) {
        int i, j;
        int[] dx = new int[]{0,0,1,-1,1,1,-1,-1};
        int[] dy = new int[]{1,-1,0,0,-1,1,-1,1};


        for(int idx = 0; idx < dx.length; idx++) {
            i = x + dx[idx];
            j = y + dy[idx];
            while(i >= 0 && i < size && j >= 0 && j < size && getColor(i, j) != null && getColor(i, j) != color) {
                i += dx[idx];
                j += dy[idx];
            }
            
            if (getColor(i, j) == color && (i != x + dx[idx] || j != y + dy[idx])) {
                int ii = x, jj = y;
                if (dx[idx] >= 0 && dy[idx] >= 0) {
                    for(; ii <= i & jj <= j; ii += dx[idx], jj += dy[idx]) { tileColors[ii][jj] = color;}
                }
                if (dx[idx] >= 0 && dy[idx] < 0) {
                    for(; ii <= i & jj >= j; ii += dx[idx], jj += dy[idx]) { tileColors[ii][jj] = color;}
                }
                if (dx[idx] < 0 && dy[idx] >= 0) {
                    for(; ii >= i & jj <= j; ii += dx[idx], jj += dy[idx]) { tileColors[ii][jj] = color;}
                }
                if (dx[idx] < 0 && dy[idx] < 0) {
                    for(; ii >= i & jj >= j; ii += dx[idx], jj += dy[idx]) { tileColors[ii][jj] = color;}
                }
                
            }
        }
        totalTiles ++;

        
        checkWhosTurn(color);
        if(currTileColor == TileColor.BLACK) {System.out.println("BLACK");}
        else if(currTileColor == TileColor.WHITE) {System.out.println("WHITE");}
        else {System.out.println("End the Game");}
        
        
        // AI computer's turn
        if(whosTurn() == TileColor.WHITE) {
            AI ai = new AI(Arrays.copyOf(tileColors, tileColors.length));
            // System.out.println(Arrays.deepToString(tileColors));
            int[] bestPos = ai.getBestTile(TileColor.WHITE);
            int bestXPos = bestPos[0];
            int bestYPos = bestPos[1];
            // try {
            //     Thread.sleep(1000);
            // } catch (InterruptedException e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            // }
            makeTile(bestXPos, bestYPos, whosTurn());
        }

    }

    public void checkWhosTurn(TileColor color) {
        TileColor potentialColor = null;

        // Try to change turn;
        if(color == TileColor.BLACK) {potentialColor = TileColor.WHITE;}
        if(color == TileColor.WHITE) {potentialColor = TileColor.BLACK;}

        // Check whether the changed color is valid
        if(ifValidTiles(potentialColor)) {return;}


        // Try to change turn
        if(potentialColor == TileColor.BLACK) { potentialColor = TileColor.WHITE;}
        else if(potentialColor == TileColor.WHITE) { potentialColor = TileColor.BLACK;}

        // Check whether the changed color is valid
        if(ifValidTiles(potentialColor)) {return;}
        currTileColor = null;
    }

    public boolean ifValidTiles(TileColor color) {
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (tileColors[i][j] == null && isValidPos(i, j, color)) {
                    // System.out.println("Found it!");
                    // System.out.println(i +" " + j);;
                    currTileColor = color; // no need to change, return it
                    return true;
                }
            }
        }
        return false;
    }

    public int getSize() {
        return this.size;
    }

    public boolean gameFinish() {
        if (totalTiles == size * size || currTileColor == null) {return true;}
        else {return false;}
    } 
}
