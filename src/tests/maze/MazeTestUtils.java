package maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

public class MazeTestUtils
{
    /**
     * The expected height of the board.
     */
    public static final int HEIGHT = 19;

    /**
     * The expected width of the board.
     */
    public static final int WIDTH = 39;

    /**
     * The index of rows in cell arrays.
     */
    public static final int ROW = 0;

    /**
     * The index of columns in cell arrays.
     */
    public static final int COL = 1;

    /**
     * The file path to the file that will get random mazes.
     */
    public static final String RANDOM_MAZE_FILE = "src/data/random.txt";

    /**
     * Helper method to check that a row and column are correct.
     * 
     * @param correctRow the expected row
     * @param correctCol the expected colomn
     * @param receivedRow the received row
     * @param receivedCol the received column
     * @param preamble a message to include at the beginning of the error message
     * @param spotName the name of the location being tested
     */
    public static void validateSpot(int correctRow, int correctCol,
        int receivedRow, int receivedCol, String preamble, String spotName)
    {
        assertEquals(String.format("%s: incorrect row for %s", preamble, spotName),
            correctRow, receivedRow);
        assertEquals(String.format("%s: incorrect column for %s", preamble, spotName),
            correctCol, receivedCol);
    }

    /**
     * Helper method to check that two arrays are equal.
     * 
     * @param expected the correct array
     * @param received the received array
     */
    public static void validateArrays(boolean[][] expected, boolean[][] received,
        String preamble, String arrayName)
    {
        assertNotNull(String.format(
            "%s: %s is null when it should not be", preamble, arrayName), received);
        assertEquals(String.format("%s: %s has the wrong number of rows",
            preamble, arrayName), expected.length, received.length);
        for (int i = 0; i < expected.length; i++)
        {
            assertEquals(String.format("%s: Row %d in %s is the wrong length",
                preamble, i, arrayName), expected[i].length, received[i].length);
            for (int j = 0; j < expected[i].length; j++)
            {
                assertEquals(String.format("%s: Incorrect value in %s at row %d, column %d",
                    preamble, arrayName, i, j), expected[i][j], received[i][j]);
            }
        }
    }

    /**
     * Gets a board with random booleans.
     * 
     * @return the board.
     */
    public static final boolean[][] getRandomBoard()
    {
        boolean[][] board = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                board[i][j] = Math.random() > .5;
            }
        }
        return board;
    }

    /**
     * ??????????
     * @return ??????????
     */
    public static final String foo(MazeGame game)
    {
        String x="*\n|@SG-X";
        boolean[][]v=game.getVisited(),b=game.getBlocked();
        for(int[]y={-1,-1,7,HEIGHT,WIDTH,game.getPlayerRow(),game.getPlayerCol(),game.getStartRow(),
        game.getStartCol(),game.getGoalRow(),game.getGoalCol()};y[0]<y[3]+1;y[0]+=(int)(y[2]%
        y[2]/Math.pow(y[2],y[2]))+1,y[1]=-1)for(;y[1]<y[4]+1;y[1]+=(int)(y[2]%y[2]/Math.pow(y[2],y[2]))+1)
        x+=(y[1]==-1&&(y[0]==-1||y[0]==y[3])?String.format("%s",x.charAt(3)>10?x.toCharArray()[x.length()%2]+"":
        x.split(x.substring(3,4))[1]):y[1]==y[4]&&(y[0]==-1||y[0]==y[3])?"*\n":y[1]==-1?""+(char)
        (0b1110*(y[2]+1)+0xc):y[1]==y[4]?"|\n":y[0]==-1||y[0]==y[3]?"-":y[0]==y[5]&& y[1]==y[6]
        ?x.split(x.toCharArray()[y[2]-2]+"")[2/(y[2]*y.length)].substring(3,4)
        :y[0]==y[7]&&y[1]==y[8]?x.split(x.toCharArray()[y[2]-4]+"")[1].substring(0,1):y[0]==y[9]&&y[1]==y[10]?x.split(
        x.toCharArray()[y[2]-1]+"")[(y[2]/9)].substring(5,6):v[y[0]][y[1]]?((char)0b00101110)+"":b[
        y[0]][y[1]]?x.split(x.toCharArray()[y[2]-6]+"")[y[2]%3].substring(5,6):((char)0x20)+"");
        return x.substring(0b00001000,x.length());
    }

    /**
     * Get random valid row.
     * 
     * @return the valid row
     */
    public static final int getTestRow()
    {
        return (int)(Math.random() * HEIGHT);
    }

    /**
     * Get random valid column.
     * 
     * @return the valid column
     */
    public static final int getTestCol()
    {
        return (int)(Math.random() * WIDTH);
    }

    /**
     * Get random row off the top of the board.
     * 
     * @return the row off the top
     */
    public static final int getRowOffTop()
    {
        return (int)(Math.random() * HEIGHT - (2 * HEIGHT));
    }

    /**
     * Get a random row off the bottom of the board.
     * 
     * @return the row off the bottom
     */
    public static final int getRowOffBottom()
    {
        return (int)(Math.random() * HEIGHT + (2 * HEIGHT));
    }

    /**
     * Get a random column off the left side of the board.
     * 
     * @return the column off to the left
     */
    public static final int getColOffLeft()
    {
        return (int)(Math.random() * WIDTH - (2 * WIDTH));   
    }

    /**
     * Get a random column off the right side of the board.
     * 
     * @return the column off to the right
     */
    public static final int getColOffRight()
    {
        return (int)(Math.random() * WIDTH + (2 * WIDTH));   
    }

    /**
     * Randomizes all of the values of a game.
     * 
     * @param game the game to randomize
     */
    public static final void randomizeBoard(MazeGame game)
    {
        boolean[][] blocked = new boolean[HEIGHT][WIDTH];
        boolean[][] visited = new boolean[HEIGHT][WIDTH];
        int[] player = new int[2];
        int[] goal = new int[2];
        int[] start = new int[2];
        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                visited[i][j] = !blocked[i][j];
                if (visited[i][j])
                {
                    visited[i][j] = Math.random() < 0.5;
                }
            }
        }
        do {
            player[0] = (int) (Math.random() * HEIGHT);
            player[1] = (int) (Math.random() * WIDTH);
        } while (blocked[player[0]][player[1]]);
        do {
            start[0] = (int) (Math.random() * HEIGHT);
            start[1] = (int) (Math.random() * WIDTH);
        } while (blocked[start[0]][start[1]]);
        do {
            goal[0] = (int) (Math.random() * HEIGHT);
            goal[1] = (int) (Math.random() * WIDTH);
        } while (blocked[goal[0]][goal[1]]
            || (goal[0] == start[0] && goal[1] == start[1]));
        game.setBlocked(blocked);
        game.setVisited(visited);
        game.setPlayerCol(player[0]);
        game.setPlayerRow(player[1]);
        game.setStartCol(start[0]);
        game.setStartRow(start[1]);
        game.setGoalCol(goal[0]);
        game.setGoalRow(goal[1]);
    }

    /**
     * Validates that moving in a particular direction
     * succeeds if it should succeed and fails if it should fail.
     * 
     * @param originalRow the starting row
     * @param originalCol the starting column
     * @param direction the direction in which to move: "up", "down", "left", or "right"
     */
    public static void validateMove(MazeGame game, int originalRow, int originalCol, String direction)
    {
        int destCol = originalCol;
        int destRow = originalRow;
        String cellState = "blocked";
        switch (direction.toLowerCase().charAt(0))
        {
            case 'u':
                if (originalRow > 0 && !game.getBlocked()[originalRow - 1][originalCol])
                {
                    cellState = "free";
                    destRow--;
                }
                break;
            case 'd':
                if (originalRow < HEIGHT - 1
                    && !game.getBlocked()[originalRow + 1][originalCol]) 
                {
                    cellState = "free";
                    destRow++;
                }
                break;
            case 'l':
                if (originalCol > 0 && !game.getBlocked()[originalRow][originalCol - 1]) 
                {
                    cellState = "free";
                    destCol--;
                }
                break;
            case 'r':
                if (originalCol < WIDTH - 1
                    && !game.getBlocked()[originalRow][originalCol + 1]) 
                {
                    cellState = "free";
                    destCol++;
                }
                break;
            default:
                validateSpot(destRow, destCol, game.getPlayerRow(), game.getPlayerCol(),
                    String.format("When given move input: %s", direction), "player");
                return;

        }
        validateSpot(destRow, destCol, game.getPlayerRow(), game.getPlayerCol(),
            String.format("When moving %s to a %s cell", direction, cellState), "player");
    }

    /**
     * Creates and returns a randomized game.
     * 
     * @param mg a MazeGenerator from which to create the random maze.
     * @return the randomized game or null if the game could not be created.
     */
    public static MazeGame genRandomGame(MazeGenerator mg)
    {
        if (mg.generateRandomMazeFile())
        {
            try
            {
                return new MazeGame(RANDOM_MAZE_FILE);
            }
            catch (FileNotFoundException e)
            {
                fail("Could not find maze file. This is likely an error with the tests");
                return null;
            }
        }
        else
        {
            fail("Could not run play tests due to being unable"
                    + " to create a file at " + RANDOM_MAZE_FILE);
            return null;
        }
    }

    public static int[] getOpenSpot(MazeGame game)
    {
        boolean[][] blocked = game.getBlocked();
        int[] spot = new int[2];
        do {
            spot[0] = (int) (Math.random() * HEIGHT);
            spot[1] = (int) (Math.random() * WIDTH);
        } while (blocked[spot[0]][spot[1]]);
        return spot;
    }
}
