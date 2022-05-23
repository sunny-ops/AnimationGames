import java.awt.Color;
import java.util.Arrays;



public class AI {
   private TileColor[][] tileColors;
   private int size;

    AI(TileColor[][] tileColors) {
        this.tileColors = tileColors;
        this.size = tileColors.length;
    }

    public int[] getBestTile(TileColor color) {
        int bestXPos = 0, bestYPos = 0;
        int size = tileColors.length;
        int maxFlip = 0, flip;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if(tileColors[x][y] != null) {continue;}
                int[] dx = new int[]{0,0,1,-1,1,1,-1,-1};
                int[] dy = new int[]{1,-1,0,0,-1,1,-1,1};

                flip = 0;
                for(int idx = 0; idx < dx.length; idx++) {
                    int i = x + dx[idx];
                    int j = y + dy[idx];
                    while(i >= 0 && i < size && j >= 0 && j < size && getColor(i, j) != null && getColor(i, j) != color) {
                        i += dx[idx];
                        j += dy[idx];
                    }
                    
                    if (getColor(i, j) == color && (i != x + dx[idx] || j != y + dy[idx])) {
                        if(Math.abs(dx[idx]+dy[idx])== 1) {flip += Math.abs(i-x) + Math.abs(j-y) - 1;}
                        else {flip += Math.abs(i-x) - 1;}
                    }
                    
                }
                
                // System.out.println("flip" +","+ flip);
                if(flip > maxFlip) {
                    maxFlip = flip;
                    bestXPos = x;
                    bestYPos = y;
                }
                
            }
        }
        
        return new int[]{bestXPos, bestYPos};
        
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
        for(int i = 0; i < tileColors.length; i++) {
            for(int j = 0; j < tileColors.length; j++) {
                if(tileColors[i][j] == tileColor) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public TileColor getColor(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {return null;}
        return tileColors[x][y];
    }


}
