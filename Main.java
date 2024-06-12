import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Main extends JFrame {
    private MinesStorage minesStorage;
    private ImageStorage imageStorage;
    private SoundStorage soundStorage;
    public final int gridSize = 20;
    public final int mineCount = 150;
    public final JButton[][] grid;
    private boolean[][] revealed;
    private boolean[][] flags;
    private int flagsCount = 0;
    private final HashMap<Integer, Color> mineCountToColor;
    private boolean firstTouch = true;
    private final Color startColor = new Color(59, 59, 59);
    private final Color revealedColor = new Color(240, 240, 240);


    public Main() {
        minesStorage = new MinesStorage(gridSize, mineCount);
        imageStorage = new ImageStorage();
        soundStorage = new SoundStorage();
        setTitle("Minesweeper");
        setMinimumSize(new Dimension(45 * gridSize, 45 * gridSize));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(gridSize, gridSize));
        grid = new JButton[gridSize][gridSize];
        revealed = new boolean[gridSize][gridSize];
        flags = new boolean[gridSize][gridSize];
        mineCountToColor = new HashMap<>();
        mineCountToColor.put(1, new Color(255, 39, 0, 255));
        mineCountToColor.put(2, new Color(255, 136, 0));
        mineCountToColor.put(3, new Color(255, 255, 0, 255));
        mineCountToColor.put(4, new Color(0, 255, 0));
        mineCountToColor.put(5, new Color(0, 130, 255));
        mineCountToColor.put(6, new Color(0, 0, 255, 147));
        mineCountToColor.put(7, new Color(176, 0, 255));
        mineCountToColor.put(8, new Color(128, 0, 0));



        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JButton button = new JButton();
                button.setBackground(startColor);
                int finalI = i;
                int finalJ = j;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            toggleFlag(finalI, finalJ);
                            System.out.println();
                        }
                        else {
                            reveal(finalI, finalJ);
                        }
                    }
                });
                grid[i][j] = button;
                add(button);
            }
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu game = new JMenu("game");
        JMenuItem resetGame = new JMenuItem("restart");
        resetGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        game.add(resetGame);
        menuBar.add(game);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    private void restartGame() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                grid[x][y].setBackground(startColor);
                grid[x][y].setText("");
                grid[x][y].setIcon(null);
                grid[x][y].setEnabled(true);
                revealed = new boolean[gridSize][gridSize];
                flags = new boolean[gridSize][gridSize];
                minesStorage.mineLocations = new boolean[gridSize][gridSize];
                firstTouch = true;
                flagsCount = 0;
            }
        }
    }
    private void toggleFlag(int x, int y) {
        if (flags[x][y]) {
            grid[x][y].setIcon(null);
            flags[x][y] = false;
            flagsCount--;
        }
        else if (!flags[x][y] && !revealed[x][y]){
            grid[x][y].setIcon(imageStorage.getFlagImage());
            flags[x][y] = true;
            flagsCount++;
            if (flagsCount == mineCount) {
                if (checkWin()) {
                    JOptionPane.showMessageDialog(this, "win");
                }
            }
        }
    }
    private void reveal(int x, int y) {
        if (firstTouch) {
            minesStorage.generateMines(x, y);
            firstTouch = false;
        }

        if (revealed[x][y] || flags[x][y]) {
            return;
        }
        revealed[x][y] = true;
        grid[x][y].setEnabled(false);

        if (minesStorage.mineLocations[x][y]) {
            soundStorage.getBombSound().start();
            grid[x][y].setText("&");
            lockGrid();
            grid[x][y].setBackground(Color.BLACK);

            new GameOverFrame(imageStorage.getBombImage());

        } else {
            int mines = minesStorage.countMines(x, y);
            if (mines > 0) {
                grid[x][y].setText(String.valueOf(mines));
                grid[x][y].setBackground(mineCountToColor.get(mines));
            } else {
                grid[x][y].setText("");
                grid[x][y].setBackground(revealedColor);
                for (int i = Math.max(0, x - 1); i < Math.min(gridSize, x + 2); i++) {
                    for (int j = Math.max(0, y - 1); j < Math.min(gridSize, y + 2); j++) {
                        reveal(i, j);
                    }
                }
            }
        }
    }
    public void lockGrid() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                revealed[x][y] = true;
                if (minesStorage.mineLocations[x][y]) {
                    grid[x][y].setText("&");
                    grid[x][y].setBackground(Color.CYAN);

                }
            }
        }
    }
    private boolean checkWin() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (flags[x][y] != minesStorage.mineLocations[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new Main();
    }
}
