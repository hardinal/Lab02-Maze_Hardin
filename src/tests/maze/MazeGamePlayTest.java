package maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static labtests.util.TestUtilities.getOutput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the play method of the MazeGame class.
 * 
 * @author Willow Sapphire
 * @version 04/20/2023
 */
public class MazeGamePlayTest
{
    /**
     * Message that should be printed if the user quits.
     */
    public static final String FAIL_MESSAGE = "Goodbye!\n";

    /**
     * Message that should be printed if the user wins.
     */
    public static final String WIN_MESSAGE = "You Won!\n";

    /**
     * Generator for random mazes.
     */
    private static MazeGenerator mg;
    
     /**
     * MazeGame object used by all tests.
     */
    private static MazeGame game;

    /**
     * Used to catch output printed to System.out.
     */
    private static ByteArrayOutputStream baos;

    /**
     * Stores the original System.out.
     */
    private static PrintStream oldOut;

    /**
     * Stores the original System.in.
     */
    private static InputStream oldIn;

    /**
     * Sets up the testing environment before each test.
     */
    @Before
    public void beforeEach()
    {
        mg = new MazeGenerator();
        mg.setup(MazeTestUtils.RANDOM_MAZE_FILE);
        oldOut = System.out;
        oldIn = System.in;
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        baos.reset();
    }

    /**
     * Cleans up the testing environment after each test.
     */
    @After
    public void afterEach()
    {
        System.setOut(oldOut);
        System.setIn(oldIn);
    }

    /**
     * Tests moving up, down, left, and right.
     */
    @Test
    public void testSimpleMoves()
    {
        // game objects created in testMove
        int numTrials = 20;
        testMove("up", numTrials);
        testMove("down", numTrials);
        testMove("left", numTrials);
        testMove("right", numTrials);
    }

    @Test
    public void testWin()
    {
        int numTrials = 50;
        for (int i = 0; i < numTrials; i++)
        {
            game = MazeTestUtils.genRandomGame(mg);
            String winningMoves = mg.getWinningInput();
            game.setPlayerInput(new Scanner(new ByteArrayInputStream(winningMoves.getBytes())));
            try
            {
                game.playGame();
                String output = getOutput(baos);
                assertEquals(WIN_MESSAGE, output.substring(
                    output.length() - WIN_MESSAGE.length(), output.length()));
                boolean[][] cv = mg.getCorrectVisited();
                boolean[][] v = game.getVisited();
                for (int row = 0; row < cv.length; row++)
                {
                    for (int col = 0; col < cv[row].length; col++)
                    {
                        assertEquals(String.format("After traversing the maze, "
                            + "the cell at [%d, %d] should%s be "
                            + "marked as visited",
                            row, col, !cv[row][col] ? " not" : ""),
                            cv[row][col], v[row][col]);
                    }
                }
            }
            catch (NoSuchElementException e)
            {
                fail("Game did not end when the player "
                    + "should have reached the goal");
            }
        }
    }

    @Test
    public void testLose()
    {
        int numTrials = 50;
        for (int i = 0; i < numTrials; i++)
        {
            game = MazeTestUtils.genRandomGame(mg);
            String losingMoves = mg.getLosingInput();
            game.setPlayerInput(new Scanner(new ByteArrayInputStream(losingMoves.getBytes())));
            try
            {
                game.playGame();
                String output = getOutput(baos);
                assertEquals(FAIL_MESSAGE, output.substring(
                    output.length() - FAIL_MESSAGE.length(), output.length()));
                baos = new ByteArrayOutputStream();
                System.setOut(new PrintStream(baos));
                game.printMaze();
            }
            catch (NoSuchElementException e)
            {
                fail("Game did not end when the player "
                    + "entered quit\n");
            }
        }
    }
    
    /**
     * Tests moving in a given direction.
     * 
     * @param direction "up", "down", "left", or "right"
     * @param numTrials the number of times to test the move
     */
    private void testMove(String direction, int numTrials)
    {
        for (int i = 0; i < numTrials; i++)
        {
            System.setIn(new ByteArrayInputStream((direction + "\nq\n").getBytes()));
            game = MazeTestUtils.genRandomGame(mg);
            int originalCol = game.getPlayerCol();
            int originalRow = game.getPlayerRow();
            try
            {
                game.playGame();
            }
            catch (NoSuchElementException e)
            {
                fail("Game did not end when the player "
                    + "entered quit\n");
            }
            MazeTestUtils.validateMove(game, originalRow, originalCol, direction);
        }
    }
}
