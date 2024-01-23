package maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.Scanner;

import static labtests.util.TestUtilities.getOutput;
import static maze.MazeTestUtils.HEIGHT;
import static maze.MazeTestUtils.WIDTH;
import static maze.MazeTestUtils.ROW;
import static maze.MazeTestUtils.COL;
import static maze.MazeTestUtils.getOpenSpot;

/**
 * Unit tests for the MazeGame class.
 * 
 * @author Willow Sapphire
 * @version 04/17/2023
 */
public class MazeGameGeneralTest
{
    /**
     * Message to use if a row or column is off the board.
     */
    private static final String INVALID_LOCATION_MESSAGE =
        "Location should not change if set beyond the bounds of the board";

    /**
     * Random row off the top of the board.
     */
    private static final int ROW_OFF_TOP = MazeTestUtils.getRowOffTop();

    /**
     * Random row off the bottom of the board.
     */
    private static final int ROW_OFF_BOTTOM = MazeTestUtils.getRowOffBottom();

    /**
     * Random column off the left side of the board.
     */
    private static final int COL_OFF_LEFT = MazeTestUtils.getColOffLeft();

    /**
     * Random column off the right side of the board.
     */
    private static final int COL_OFF_RIGHT = MazeTestUtils.getColOffRight();

    /**
     * Board with random booleans.
     */
    private static final boolean[][] TEST_BOARD = MazeTestUtils.getRandomBoard();
    /**
     * Scanner used to test two-arg constructor
     * and to provide input to the maze program.
     */
    private static Scanner testInput;

    /**
     * MazeGame object used by all tests.
     */
    private static MazeGame game;

    private static MazeGenerator mg;
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
        oldOut = System.out;
        oldIn = System.in;
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        mg = new MazeGenerator();
        mg.setup(MazeTestUtils.RANDOM_MAZE_FILE);
        mg.generateRandomMazeFile();
        try
        {
            game = new MazeGame(mg.getFilename());
        }
        catch (FileNotFoundException e)
        {
            fail("Could not find maze file. This is likely an error with the tests");
        }
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
     * Tests the values of HEIGHT and WIDTH.
     */
    @Test
    public void testConstantValues()
    {
        assertEquals("Your HEIGHT constant does not have the correct value.",
            HEIGHT, MazeGame.HEIGHT);
        assertEquals("Your WIDTH constant does not have the correct value.",
            WIDTH, MazeGame.WIDTH);
    }

    /**
     * Checks that the one argument constructor works properly.
     */
    @Test
    public void testOneArgConstructor() throws FileNotFoundException
    {
        final String preamble = "In the one-arg constructor";
        MazeTestUtils.validateArrays(mg.getBlocked(), game.getBlocked(),
            preamble, "blocked");
        MazeTestUtils.validateArrays(new boolean[HEIGHT][WIDTH], game.getVisited(),
            preamble, "visited");
        assertNotNull("One-arg constructor did not set playerInput",
            game.getPlayerInput());
        MazeTestUtils.validateSpot(mg.getStart()[ROW],
            mg.getStart()[COL],
            game.getPlayerRow(), game.getPlayerCol(), preamble, "player");
        MazeTestUtils.validateSpot(mg.getStart()[ROW],
            mg.getStart()[COL],
            game.getStartRow(), game.getStartCol(), preamble, "start");
        MazeTestUtils.validateSpot(mg.getGoal()[ROW],
            mg.getGoal()[COL],
            game.getGoalRow(), game.getGoalCol(), preamble, "goal");
    }

    /**
     * Checks that the two argument constructor works properly.
     */
    @Test
    public void testTwoArgConstructor() throws FileNotFoundException
    {
        mg.generateRandomMazeFile();
        game = new MazeGame(mg.getFilename(), testInput);
        final String preamble = "In the two-arg constructor";
        MazeTestUtils.validateArrays(mg.getBlocked(), game.getBlocked(),
            preamble, "blocked");
        MazeTestUtils.validateArrays(new boolean[HEIGHT][WIDTH], game.getVisited(),
            preamble, "visited");
        assertEquals("The input scanner is not equal to the scanner provided in the constructor",
            testInput, game.getPlayerInput());
        MazeTestUtils.validateSpot(mg.getStart()[ROW],
            mg.getStart()[COL],
            game.getPlayerRow(), game.getPlayerCol(), preamble, "player");
        MazeTestUtils.validateSpot(mg.getStart()[ROW],
            mg.getStart()[COL],
            game.getStartRow(), game.getStartCol(), preamble, "start");
        MazeTestUtils.validateSpot(mg.getGoal()[ROW],
            mg.getGoal()[COL],
            game.getGoalRow(), game.getGoalCol(), preamble, "goal");
    }

    /**
     * Tests the getters and setters for the player.
     */
    @Test
    public void testGetSetPlayer()
    {
        int[] spot = getOpenSpot(game);
        game.setPlayerRow(spot[0]);
        game.setPlayerCol(spot[1]);
        MazeTestUtils.validateSpot(spot[0], spot[1], game.getPlayerRow(), game.getPlayerCol(),
            "When using the getters and setters", "player");
        game.setPlayerRow(ROW_OFF_TOP);
        game.setPlayerRow(COL_OFF_RIGHT);
        MazeTestUtils.validateSpot(spot[0], spot[1], game.getPlayerRow(), game.getPlayerCol(),
            INVALID_LOCATION_MESSAGE, "player");
        game.setPlayerRow(ROW_OFF_BOTTOM);
        game.setPlayerRow(COL_OFF_LEFT);
        MazeTestUtils.validateSpot(spot[0], spot[1], game.getPlayerRow(), game.getPlayerCol(),
            INVALID_LOCATION_MESSAGE, "player");
    }

    /**
     * Tests the getters and setters for the goal.
     */
    @Test
    public void testGetSetGoal()
    {
        int[] spot = getOpenSpot(game);
        game.setGoalRow(spot[0]);
        game.setGoalCol(spot[1]);
        MazeTestUtils.validateSpot(spot[0], spot[1], game.getGoalRow(), game.getGoalCol(),
            "When using the getters and setters:", "goal");
        game.setGoalRow(ROW_OFF_TOP);
        game.setGoalRow(COL_OFF_RIGHT);
        MazeTestUtils.validateSpot(spot[0], spot[1], game.getGoalRow(), game.getGoalCol(),
            INVALID_LOCATION_MESSAGE, "goal");
        game.setGoalRow(ROW_OFF_BOTTOM);
        game.setGoalRow(COL_OFF_LEFT);
        MazeTestUtils.validateSpot(spot[0], spot[1], game.getGoalRow(), game.getGoalCol(),
            INVALID_LOCATION_MESSAGE, "goal");
    }

    /**
     * Tests the getters and setters for the start.
     */
    @Test
    public void testGetSetStart()
    {
        int[] spot = getOpenSpot(game);
        game.setStartRow(spot[0]);
        game.setStartCol(spot[1]);
        MazeTestUtils.validateSpot(spot[0], spot[1], game.getStartRow(), game.getStartCol(),
            "When using the getters and setters:", "start");
        game.setStartRow(ROW_OFF_TOP);
        game.setStartRow(COL_OFF_RIGHT);
        MazeTestUtils.validateSpot(spot[0], spot[1], game.getStartRow(), game.getStartCol(),
            INVALID_LOCATION_MESSAGE, "start");
        game.setStartRow(ROW_OFF_BOTTOM);
        game.setStartRow(COL_OFF_LEFT);
        MazeTestUtils.validateSpot(spot[0], spot[1], game.getStartRow(), game.getStartCol(),
            INVALID_LOCATION_MESSAGE, "start");
    }

    /**
     * Tests the getters and setters for the blocked array.
     */
    @Test
    public void testGetSetBlocked()
    {
        game.setBlocked(TEST_BOARD);
        assertFalse("The blocked array setter did not set the array to a copy of the provided array",
            TEST_BOARD == game.getBlocked());
        MazeTestUtils.validateArrays(TEST_BOARD, game.getBlocked(), 
        "When using the getters and setters", "blocked");
    }

    /**
     * Tests the getters and setters for the visited array.
     */
    @Test
    public void testGetSetVisited()
    {
        boolean[][] board = MazeTestUtils.getRandomBoard();
        game.setVisited(board);
        assertFalse("The visited array setter did not set the array to a copy of the provided array",
            board == game.getVisited());
        MazeTestUtils.validateArrays(board, game.getVisited(), 
        "When using the getters and setters", "visited");
    }

    /**
     * Tests the getters and setters for the player input.
     */
    @Test
    public void testGetSetPlayerInput()
    {
        Scanner s = new Scanner("TEST");
        game.setPlayerInput(s);
        assertTrue("The playerInput setter did not set the playerInput",
            s == game.getPlayerInput());
    }
    
    /**
     * Tests the printMaze method.
     */
    @Test
    public void testPrintMaze()
    {
        final int NUM_TESTS = 20;
        for (int i = 0; i < NUM_TESTS; i++)
        {
            MazeTestUtils.randomizeBoard(game);
            game.printMaze();
            assertEquals("Incorrect print output",
                MazeTestUtils.foo(game), getOutput(baos));
        }
    }
}
