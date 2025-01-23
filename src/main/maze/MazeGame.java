package maze;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;


/**
 * A MazeGame Class containing various methods
 * for initiating a map with preset parameters.
 * The MazeGame takes input from a player,
 * with an objective of finding the "Goal".
 *
 * @author Austin Hardin
 * @version v1 22Jan25
 */
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

    /**
     * 2 arg constructor for  new Maze game.
     *
     * @param mazeFile  the maze file
     * @param playerInput the player input
     * @throws FileNotFoundException the file not found exception
     */

    public MazeGame(String mazeFile, Scanner playerInput)
            throws FileNotFoundException
    {
        this.playerInput = playerInput;
        loadMaze(mazeFile);
    }


    /**
     * 1 arg for Maze game.
     *
     * @param mazeFile the maze file
     * @throws FileNotFoundException the file not found exception
     */
    public MazeGame(String mazeFile) throws FileNotFoundException
    {
        this(mazeFile, new Scanner(System.in));

    }


    /**
     * Play game method.
     * This method calls the prompt for player input.
     * While the player has yet to reach the goal,
     * the loop continues.
     * When playerAtGoal() is true it satisfies the gameOver boolean
     * resulting in a DUB.
     */

    public void playGame()
    {
        boolean gameOver;

        do
        {
            prompt();
            String move = playerInput.next();
            gameOver = makeMove(move);
        }
        while (!gameOver);

        if (playerAtGoal())
        {
            System.out.println("You Won!");
        }
        else
        {
            System.out.println("Goodbye!");
        }



    }

    /**
     * Print maze/board
     * Print loop to build a board.
     */
    public void printMaze()
    {
        System.out.print("*");
        for (int col = 0; col < WIDTH; col++)
        {
            System.out.print("-");
        }
        System.out.println("*");

        for (int row = 0; row < HEIGHT; row++)
        {
            System.out.print("|");
            for (int col = 0; col < WIDTH; col++)
            {
                if (player[ROW] == row && player[COL] == col)
                {
                    System.out.print("@");
                }
                else if (start[ROW] == row && start[COL] == col)
                {
                    System.out.print("S");
                }
                else if (goal[ROW] == row && goal[COL] == col)
                {
                    System.out.print("G");
                }
                else if (visited[row][col])
                {
                    System.out.print(".");
                }
                else if (blocked[row][col])
                {
                    System.out.print("X");
                }
                else
                {
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }

        System.out.print("*");
        for (int col = 0; col < WIDTH; col++)
        {
            System.out.print("-");
        }
        System.out.println("*");

    }

    /**
     * Gets player row.
     *
     * @return the player row
     */
    public int getPlayerRow()
    {
        return player[ROW];
    }

    /**
     * Gets player column.
     *
     * @return the player col
     */
    public int getPlayerCol()
    {
        return player[COL];
    }

    /**
     * Gets start row.
     *
     * @return the start row
     */
    public int getStartRow()
    {
        return start[ROW];
    }

    /**
     * Gets start column.
     *
     * @return the start col
     */
    public int getStartCol()
    {
        return start[COL];
    }

    /**
     * Get blocked boolean [ ] [ ].
     *
     * @return the boolean [ ] [ ]
     */
    public boolean[][] getBlocked()
    {
        return copyTwoDimBoolArray(this.blocked);
    }

    /**
     * Get visited boolean [ ] [ ].
     *
     * @return the boolean [ ] [ ]
     */
    public boolean[][] getVisited()
    {
        return copyTwoDimBoolArray(this.visited);
    }

    /**
     * Gets player input.
     *
     * @return the player input
     */
    public Scanner getPlayerInput()
    {
        return playerInput;
    }

    /**
     * Gets goal row.
     *
     * @return the goal row
     */
    public int getGoalRow()
    {
        return goal[ROW];
    }

    /**
     * Gets goal col.
     *
     * @return the goal col
     */
    public int getGoalCol()
    {
        return goal[COL];
    }

    /**
     * Sets player row.
     *
     * @param row the row
     */
    public void setPlayerRow(int row)
    {
        if (row >= 0 && row < HEIGHT)
        {
            player[ROW] = row;
        }
    }

    /**
     * Sets player col.
     *
     * @param col the col
     */
    public void setPlayerCol(int col)
    {
        if (col >= 0 && col < WIDTH)
        {
            player[COL] = col;
        }
    }

    /**
     * Sets goal row.
     *
     * @param row the row
     */
    public void setGoalRow(int row)
    {
        if (row >= 0 && row < HEIGHT)
        {
            goal[ROW] = row;
        }
    }

    /**
     * Sets goal col.
     *
     * @param col the col
     */
    public void setGoalCol(int col)
    {
        if (col >= 0 && col < WIDTH)
        {
            goal[COL] = col;
        }
    }

    /**
     * Sets start row.
     *
     * @param row the row
     */
    public void setStartRow(int row)
    {
        if (row >= 0 && row < HEIGHT)
        {
            start[0] = row;
        }
    }

    /**
     * Sets start col.
     *
     * @param col the col
     */
    public void setStartCol(int col)
    {
        if (col >= 0 && col < WIDTH)
        {
            start[1] = col;
        }
    }

    /**
     * Sets blocked.
     *
     * @param blocked the blocked
     */
    public void setBlocked(boolean[][] blocked)
    {
        this.blocked = copyTwoDimBoolArray(blocked);
    }

    /**
     * Sets visited.
     *
     * @param visited the visited
     */
    public void setVisited(boolean[][] visited)
    {
        this.visited = copyTwoDimBoolArray(visited);
    }

    /**
     * Sets player input.
     *
     * @param playerInput the player input
     */
    public void setPlayerInput(Scanner playerInput)
    {
        this.playerInput = playerInput;
    }

    /**
     * Deep copy for our set/get.
     *
     * @param arrayToCopy array to copy from
     * @return copy of array
     */

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

    /**
     * PrintMaze() called.
     * Prints message for user input.
     */
    private void prompt()
    {
        printMaze();

        System.out.print("Enter your move (up, down, left, right, "
                + "or q to quit): ");
    }

    /**
     * Boolean check for win.
     * @return  boolean
     */
    private boolean playerAtGoal()
    {
        return player[ROW] == goal[ROW] && player[COL] == goal[COL];

    }

    /**
     * Boolean condition to check move validity.
     * @param row blocked or not
     * @param col blocked or not
     * @return boolean
     */
    private boolean valid(int row, int col)
    {
        return row >= 0 && row < HEIGHT
                && col >= 0 && col < WIDTH
                && !blocked[row][col];

    }

    /**
     * Checks user visited positions. Log of sorts.
     * @param row row in Maze
     * @param col col in Maze
     */
    private void visit(int row, int col)
    {
        this.visited[row][col] = true;
    }

    /**
     * Loads and reads (eyes) mazeFile.
     *
     * @param mazeFile the mazeFile
     * @throws FileNotFoundException in case not found
     */

    private void loadMaze(String mazeFile) throws FileNotFoundException
    {
        this.blocked = new boolean[HEIGHT][WIDTH];
        this.visited = new boolean[HEIGHT][WIDTH];
        this.start = new int[2];
        this.goal = new int[2];
        this.player = new int[2];

        File file = new File(mazeFile);
        Scanner eyes = new Scanner(file);

        for (int row = 0; row < blocked.length; row++)
        {
            for (int col = 0; col < blocked[ROW].length; col++)
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
        eyes.close();
    }

    /**
     * Boolean for player moves.
     * @param move move
     * @return playerAtGoal()
     */
    private boolean makeMove(String move)
    {
        char direction = move.toLowerCase().charAt(0);

        switch (direction)
        {
            case 'q':
                return true;

            case 'l':
                if (valid(player[ROW], player[COL] - 1))
                {
                    player[COL]--;
                    visit(player[ROW], player[COL]);
                }
                break;

            case 'r':
                if (valid(player[ROW], player[COL] + 1))
                {
                    player[COL]++;
                    visit(player[ROW], player[COL]);
                }
                break;
            case 'u':
                if (valid(player[ROW] - 1, player[COL]))
                {
                    player[ROW]--;
                    visit(player[ROW], player[COL]);
                }
                break;
            case 'd':
                if (valid(player[ROW] + 1, player[COL]))
                {
                    player[ROW]++;
                    visit(player[ROW], player[COL]);
                }
                break;

            default:
                System.out.print("Uh oh... -_-");
                break;

        }

        return playerAtGoal();
    }

}



