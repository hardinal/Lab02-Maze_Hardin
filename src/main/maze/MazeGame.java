///@author Austin Hardin
///@version V1 22Jan25

package maze;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;


public class MazeGame
{
    public static final int HEIGHT = 19;
    public static final int WIDTH = 39;
    private static final int COL = 1;
    private static final int ROW = 0;
    private Scanner playerInput;
    private boolean[][] blocked;
    private boolean[][] visited;
    private int[] player;
    private int[] goal;
    private int[] start;

    public MazeGame(String mazeFile, Scanner playerInput) throws FileNotFoundException
    {
        this.playerInput = playerInput;
        loadMaze(mazeFile);
    }



    public MazeGame(String mazeFile) throws FileNotFoundException
    {
        this(mazeFile, new Scanner(System.in));

    }


    public void playGame()
    {

    }

    public void printMaze()
    {

    }

    public int getPlayerRow()
    {
        return this.player[ROW];
    }

    public int getPlayerCol()
    {
        return this.player[COL];
    }

    public int getStartRow()
    {
        return this.start[ROW];
    }

    public int getStartCol()
    {
        return this.start[COL];
    }

    public boolean[][] getBlocked()
    {
        return copyTwoDimBoolArray(this.blocked);
    }

    public boolean[][] getVisited()
    {
        return copyTwoDimBoolArray(this.visited);
    }

    public Scanner getPlayerInput()
    {
        return playerInput;
    }

    public int getGoalRow()
    {
        return this.goal[ROW];
    }

    public int getGoalCol()
    {
        return this.goal[COL];
    }

    public void setPlayerRow(int row)
    {
        this.player[ROW] = row;
    }

    public void setPlayerCol(int col)
    {
        this.player[COL] = col;
    }

    public void setGoalRow(int row)
    {
        this.goal[ROW] = row;
    }

    public void setGoalCol(int col)
    {
        this.goal[COL] = col;
    }

    public void setStartRow(int row)
    {
        this.start[0] = row;
    }

    public void setStartCol(int col)
    {
        this.start[1] = col;
    }

    public void setBlocked(boolean[][] blocked)
    {
        this.blocked = copyTwoDimBoolArray(blocked);
    }

    public void setVisited(boolean[][] visited)
    {
        this.visited = copyTwoDimBoolArray(visited);
    }

    public void setPlayerInput(Scanner playerInput)
    {
        this.playerInput = playerInput;
    }

    private boolean[][] copyTwoDimBoolArray(boolean[][] arrayToCopy)
    {
        boolean[][] copy =
                new boolean[arrayToCopy.length][arrayToCopy[0].length];

        for (int i = 0; i < arrayToCopy.length; i++)
        {
            copy[i] = Arrays.copyOf(arrayToCopy[i], arrayToCopy[i].length);
        }

        return copy;
    }

    private void prompt()
    {
        printMaze();

        System.out.print("Enter your move (up, down, left, right, "
                + "or q to quit): ");
    }

    private boolean playerAtGoal()
    {
        return this.goal[ROW] == this.getPlayerRow()
            && this.goal[COL] == this.getPlayerCol();

    }

    private boolean valid(int row, int col)
    {
        return this.blocked[row][col];

    }

    private void visit(int row, int col)
    {
        getVisited()[row][col] = true;
    }

    private void loadMaze(String mazeFile) throws FileNotFoundException
    {
        boolean[][] blocked;
        boolean[][] visited;
        int[] player;
        int[] goal;
        int[] start;

        File file = new File(mazeFile);
        Scanner eyes = new Scanner(file);

        for (int row = 0; row < blocked.length; row++)
        {
            for (int col = 0; col < blocked[row].length; col++)
            {
                String nextChar = eyes.next();

                switch (nextChar)
                {
                    case "1":
                        blocked[row][col] = true;
                        break;
                        case "0":
                            blocked[row][col] = false;
                            break;
                            case "S":
                                blocked[row][col] = false;
                                start[ROW] = row;
                                start[COL] = col;
                                player[ROW] = row;
                                player[COL] = col;
                                break;
                                case "G":
                                    blocked[row][col] = false;
                                    goal[ROW] = row;
                                    goal[COL] = col;
                                    break;
                                    default:
                                        System.out.print("Uh oh... -_-");



                }
            }







    }

    private boolean makeMove(String move)
    {
        return false;
    }

}
