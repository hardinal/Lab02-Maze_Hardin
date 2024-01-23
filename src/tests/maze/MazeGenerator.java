package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import static maze.MazeTestUtils.ROW;
import static maze.MazeTestUtils.COL;

/**
 * Creates random mazes and writes them to files.
 * 
 * @author Willow Sapphire
 * @version 04/27/2023
 */
public class MazeGenerator
{
    /**
     * The height of the maze.
     */
    private int height;

    /**
     * The width of the maze.
     */
    private int width;

    /**
     * The filename to which to write generated mazes.
     */
    private String filename;

    /**
     * Stores the moves that will win the game.
     */
    private String winningInput;

    /**
     * The maze itself.
     */
    private String[][] maze;

    /**
     * What the blocked array should be for this maze
     */
    private boolean[][] blocked;

    /**
     * What cells should be visited when solving the maze
     */
    private boolean[][] correctVisited;

    /**
     * The [row][col] of the starting location
     */
    private int[] start;

    /**
     * The [row][col] of the goal location
     */
    private int[] goal;

    /**
     * Creates a new MazeGenerator.
     * 
     * @param filename the file to which the random maze(s) should be written
     * @param height the height of the mazes to generate
     * @param width the width of the mazes to generate
     */
    public void setup(String filename)
    {
        this.height = MazeTestUtils.HEIGHT;
        this.width = MazeTestUtils.WIDTH;
        this.filename = filename;
        File mazeFile = new File(filename);
        if (!mazeFile.exists())
        {
            try {
                mazeFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Could not create file at: " + filename);
                e.printStackTrace();
            }
        }
    }

    /**
     * Generates a random maze specified by the fields of this object.
     * 
     * @return true if the file was created. false if it failed.
     */
    public boolean generateRandomMazeFile()
    {
        blocked = new boolean[height][width];
        correctVisited = new boolean[height][width];
        winningInput = "";
        maze = new String[height][width];
        start = getUnusedCell();
        maze[start[ROW]][start[COL]] = "S";
        do {
            goal = getUnusedCell();
        } while (goal[ROW] == start[ROW] || goal[COL] == start[COL]);
        maze[goal[ROW]][goal[COL]] = "G";
        createPathToGoal();
        fillEmptyWithOnes();
        try {
            printMazeToFile();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file: " + filename);
            return false;
        }
    }

    /**
     * Gets the string of moves to win the maze.
     * Should only be run after generating a maze.
     * 
     * @return the string of winning moves as they would be input by a player.
     */
    public String getWinningInput()
    {
        return winningInput;
    }

    /**
     * Gets a string of moves that does not win.
     * The last move will be quit.
     * 
     * @return the string of losing moves
     */
    public String getLosingInput()
    {
        String losingMoves = "";
        String[] moves = winningInput.split("\n");
        switch (moves[0])
        {
            case "up":
                losingMoves = "down\n";
                break;
            case "down":
                losingMoves = "up\n";
                break;
            case "right":
                losingMoves = "left\n";
                break;
            case "left":
                losingMoves = "right\n";
        }
        int numMoves = (int) (Math.random() * moves.length);
        for (int i = 1; i < numMoves - 1; i++)
        {
            switch ((int) (Math.random() * 4))
            {
                case 0:
                    losingMoves += "up\n";
                    break;
                case 1:
                    losingMoves += "down\n";
                    break;
                case 2:
                    losingMoves += "left\n";
                    break;
                case 3:
                    losingMoves += "right\n";
                    break;
            }
        }
        losingMoves += "quit\n";
        return losingMoves;
    }

    /**
     * Getter for the maze's width.
     * 
     * @return width
     */
    public int getWidth()
    {
        return this.width;
    }

    /**
     * Getter for the maze's height.
     * 
     * @return height
     */
    public int getHeight()
    {
        return this.height;
    }

    /**
     * Getter for the filename to which mazes will be written.
     * 
     * @return filename
     */
    public String getFilename()
    {
        return this.filename;
    }

    /**
     * Setter for the maze's width.
     * 
     * @param width the new maze width
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * Setter for the maze's height.
     * 
     * @param height the new maze height
     */
    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * Setter for the filename to which to write mazes.
     * 
     * @param filename the new filename
     */
    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    /**
     * Getter for the correct visited result.
     * 
     * @return correctVisited.
     */
    public boolean[][] getCorrectVisited()
    {
        return this.correctVisited;
    }

    /**
     * Getter for the start location.
     * 
     * @return start.
     */
    public int[] getStart()
    {
        return start;
    }

    /**
     * Getter for the goal location.
     * 
     * @return goaal.
     */
    public int[] getGoal()
    {
        return goal;
    }

    /**
     * Getter for the correct blocked array.
     * 
     * @return blocked.
     */
    public boolean[][] getBlocked()
    {
        return blocked;
    }

    /**
     * Creates a path of zeros from the start to the goal.
     * The path will follow a straight line to either the goal row or column,
     * then follow a straight line to the goal.
     */
    private void createPathToGoal()
    {
        int[] playerLoc = {start[ROW], start[COL]};
        boolean rowFirst = Math.random() < .5;
        moveToGoal(playerLoc, rowFirst);
        moveToGoal(playerLoc, !rowFirst);
    }

    /**
     * Moves the player in a straight line to the column containing the goal.
     * Puts zeros in every spot inbetween.
     * 
     * @param playerLoc the current location of the player
     * @param moveToRow true to move to the goal row, false to move to the goal column.
     */
    private void moveToGoal(int[] playerLoc, boolean moveToRow)
    {
        int index = moveToRow ? ROW : COL;
        while (playerLoc[index] != goal[index])
        {
            if ( goal[index] > playerLoc[index])
            {
                winningInput += moveToRow ? "down\n" : "right\n";
                playerLoc[index]++;
                correctVisited[playerLoc[ROW]][playerLoc[COL]] = true;
            }
            else
            {
                winningInput += moveToRow ? "up\n" : "left\n";
                playerLoc[index]--;
                correctVisited[playerLoc[ROW]][playerLoc[COL]] = true;
            }
            // do not overwrite the goal when it is reached.
            if (maze[playerLoc[ROW]][playerLoc[COL]] != "G")
            {
                maze[playerLoc[ROW]][playerLoc[COL]] = "0";
            }
        }
    }

    /**
     * Prints the current maze to the current filename.
     * 
     * @throws FileNotFoundException if the file does not exit
     */
    private void printMazeToFile() throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(filename);
        for (String[] row : maze)
        {
            for (String cell : row)
            {
                pw.print(cell + " ");
            }
            pw.println();
        }
        pw.close();
    }

    /**
     * Gets a location in the maze array that has no value yet.
     * 
     * @return an array of the indices to the maze array such that
     *      maze[returnVal[ROW]][returnVal[COL]] is an empty spot in the array.
     *      Returns null if the array is full.
     */
    private int[] getUnusedCell()
    {
        if (isFull()) return null;
        int[] cell = {getValidRow(), getValidCol()};
        while (maze[cell[ROW]][cell[COL]] != null)
        {
            int emptyColInRow = getRandomEmptySpotInRow(cell[ROW]);
            int emptyRowInCol = getRandomEmptySpotInCol(cell[COL]);
            if (emptyColInRow != -1)
            {
                cell[COL] = emptyColInRow;
            }
            else if (emptyRowInCol != -1)
            {
                cell[ROW] = emptyRowInCol;
            }
            else
            {
                cell[ROW] = getValidRow();
                cell[COL] = getValidCol();
            }
        }
        return cell;
    }

    /**
     * Gets a random row within the bounds of the board.
     * 
     * @return the random row
     */
    private int getValidRow()
    {
        return (int) (Math.random() * height);
    }

    /**
     * Gets a random column within the bounds of the board.
     * 
     * @return the random column
     */
    private int getValidCol()
    {
        return (int) (Math.random() * width);
    }

    /**
     * Gets a random empty spot in a given row.
     * 
     * @param row the row to search
     * 
     * @return the empty spot or -1 if the row is full
     */
    private int getRandomEmptySpotInRow(int row)
    {
        // I just didn't want to use an ArrayList or deal with
        // an incomplete array, so I keep track of spots in a string
        String spots = "";
        for (int i = 0; i < maze[row].length; i++)
        {
            if (maze[row][i] == null)
            {
                spots += i + ",";
            }
        }
        String[] spotArray = spots.split(",");
        return spotArray.length > 0 ?
            Integer.parseInt(spotArray[(int)(Math.random() * spotArray.length)]) : -1;
    }

    /**
     * Gets a random empty spot in a given column.
     * 
     * @param row the column to search
     * 
     * @return the empty spot or -1 if the xolumn is full
     */
    private int getRandomEmptySpotInCol(int col)
    {
        // I just didn't want to use an ArrayList or deal with
        // an incomplete array, so I keep track of spots in a string
        String spots = "";
        for (int i = 0; i < maze.length; i++)
        {
            if (maze[i][col] == null)
            {
                spots += i + ",";
            }
        }
        String[] spotArray = spots.split(",");
        if (spotArray.length > 0)
        {
            return Integer.parseInt(spotArray[(int)(Math.random() * spotArray.length)]);
        }
        else
        {
            return -1;
        }
    }

    /**
     * Checks if there are any empty spots in the maze.
     * 
     * @return true if the maze is full, false otherwise
     */
    private boolean isFull()
    {
        for (String[] row : maze)
        {
            for (String val : row)
            {
                if (val == null)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Fills every empty spot in maze with a "1".
     */
    private void fillEmptyWithOnes()
    {
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[i].length; j++)
            {
                if (maze[i][j] == null)
                {
                    blocked[i][j] = true;
                    maze[i][j] = "1";
                }
            }
        }
    }
}
