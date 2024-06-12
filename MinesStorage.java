import java.util.Random;

public class MinesStorage {
    public boolean[][] mineLocations;
    private final Random random = new Random();
    private int gridSize;
    private int mineCount;

    public MinesStorage(int gridSize, int mineCount){
        this.gridSize = gridSize;
        this.mineCount = mineCount;
        mineLocations = new boolean[gridSize][gridSize];
    }
    public void generateMines(int touchX, int touchY) {
        mineLocations = new boolean[gridSize][gridSize];
        int count = 0;
        int x = random.nextInt(gridSize);
        int y = random.nextInt(gridSize);
        while (count < mineCount) {
            if (Math.abs(x- touchX) < 2 && Math.abs(y- touchY) < 2){
                x++;
                if (x == gridSize){
                    x = 0;
                    y++;
                    if (y == gridSize){
                        y = 0;
                    }
                }
                continue;
            }
            if (!mineLocations[x][y] && (x != touchX || y != touchY)) {
                mineLocations[x][y] = true;
                x = random.nextInt(gridSize);
                y = random.nextInt(gridSize);
                count++;
            }
            else {
                x++;
                if (x == gridSize){
                    x = 0;
                    y++;
                    if (y == gridSize){
                        y = 0;
                    }
                }
            }
        }
    }

    public int countMines(int x, int y) {
        int count = 0;
        for (int i = Math.max(0, x - 1); i < Math.min(gridSize, x + 2); i++) {
            for (int j = Math.max(0, y - 1); j < Math.min(gridSize, y + 2); j++) {
                if (mineLocations[i][j]) count++;
            }
        }
        return count;
    }

}
