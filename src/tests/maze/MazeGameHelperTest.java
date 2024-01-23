package maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import labtests.util.TestUtilities;
import static maze.MazeTestUtils.HEIGHT;
import static maze.MazeTestUtils.WIDTH;
import static maze.MazeTestUtils.ROW;
import static maze.MazeTestUtils.COL;
import static maze.MazeTestUtils.getOpenSpot;

public class MazeGameHelperTest
{
    private MazeGenerator mg;
    private MazeGame game;

    @Before
    public void beforeEach()
    {
        mg = new MazeGenerator();
        mg.setup(MazeTestUtils.RANDOM_MAZE_FILE);
        game = MazeTestUtils.genRandomGame(mg);
    }

    @Test
    public void testMakeMove()
    {
        int numTrials = 40;
        Method makeMove = getMethod("makeMove");
        for (int i = 0; i < numTrials; i++)
        {
            try
            {
                game = MazeTestUtils.genRandomGame(mg);
                String direction = getRandomDirection();
                int originalCol = game.getPlayerCol();
                int originalRow = game.getPlayerRow();
                boolean result = (boolean) makeMove.invoke(game, (Object) direction);
                MazeTestUtils.validateMove(game, originalRow, originalCol, direction);
                if (direction.toLowerCase().charAt(0) == 'q')
                {
                    assertTrue(String.format("Passing %s to makeMove "
                        + "should have returned true", direction), result);
                }
                else if (game.getPlayerCol() == game.getGoalCol()
                    && game.getPlayerRow() == game.getGoalRow())
                {
                    assertTrue(String.format("Passing %s to makeMove "
                        + "should have returned true, as it moved the player "
                        + "onto the goal", direction), result);
                }
                else
                {
                    assertFalse(String.format("Passing %s to makeMove "
                        + "should have returned false, as it did not move the "
                        + "player onto the goal aaand did not quit the game", direction), result);
                }
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
            
        }
    }

    @Test
    public void testLoadMaze()
    {
        int numTrials = 5;
        for (int i = 0; i < numTrials; i++)
        {
            String filename = "src/data/random.txt";
            Method loadMaze = getMethod("loadMaze");
            MazeGenerator mazeGen = new MazeGenerator();
            mazeGen.setup(filename);
            mazeGen.generateRandomMazeFile();
            try
            {
                Field gameBlocked = getField("blocked");
                gameBlocked.set(game, (Object) null);
                Field gameVisited = getField("visited");
                gameVisited.set(game, (Object) null);
                Field gamePlayer = getField("player");
                gamePlayer.set(game, (Object) null);
                Field gameGoal = getField("goal");
                gameGoal.set(game, (Object) null);
                Field gameStart = getField("start");
                gameStart.set(game, (Object) null);
                int[] start = mazeGen.getStart();
                int[] goal = mazeGen.getGoal();
                boolean[][] blocked = mazeGen.getBlocked();
                boolean[][] visited = new boolean[HEIGHT][WIDTH];
                try
                {
                    loadMaze.invoke(game, (Object) filename);
                    String preamble = "After calling loadMaze";
                    int[] startSpot = (int[]) gameStart.get(game);
                    int[] goalSpot = (int[]) gameGoal.get(game);
                    int[] playerSpot = (int[]) gamePlayer.get(game);
                    MazeTestUtils.validateSpot(start[ROW], start[COL], startSpot[ROW], startSpot[COL], preamble, "start");
                    MazeTestUtils.validateSpot(goal[ROW], goal[COL], goalSpot[ROW], goalSpot[COL], preamble, "goal");
                    MazeTestUtils.validateSpot(start[ROW], start[COL], playerSpot[ROW], playerSpot[COL], preamble, "player");
                    MazeTestUtils.validateArrays(blocked, (boolean[][]) gameBlocked.get(game), preamble, "blocked");
                    MazeTestUtils.validateArrays(visited, (boolean[][]) gameVisited.get(game), preamble, "visited");
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                {
                    fail("Failed to call loadMaze."
                        + "Did you declare the method correctly?");
                }
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                fail("Failed to access a required field.");
            }
        }
    }

    @Test
    public void testVisit()
    {
        Method visit = getMethod("visit");
        boolean[][] a = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[i].length; j++)
            {
                a[i][j] = Math.random() < .5 ? true : false;
            }
        }
        game.setVisited(a);
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[i].length; j++)
            {
                try
                {
                    visit.invoke(game, (Object) i, (Object) j);
                    assertTrue(String.format("After visiting row %d, col %d, "
                            + " visited[%d][%d] was false", i, j, i, j),
                        game.getVisited()[i][j]);
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                {
                    fail("Failed to call visit."
                        + "Did you declare the method correctly?");
                }
            }
        }
    }

    @Test
    public void testValid()
    {
        Method valid = getMethod("valid");
        String errorMessage = "Valid returned %s when passed row: %d and col: %d";
        try {
            // in bounds, not blocked
            boolean[][] blocked = new boolean[HEIGHT][WIDTH];
            int row = (int) (Math.random() * HEIGHT);
            int col = (int) (Math.random() * WIDTH);
            game.setBlocked(blocked);
            assertTrue(String.format(errorMessage, "false", row, col),
                (boolean) valid.invoke(game, (Object) row, (Object) col));
            // in bounds, blocked
            blocked[row][col] = true;
            game.setBlocked(blocked);
            assertFalse(String.format(errorMessage, "true", row, col),
                (boolean) valid.invoke(game, (Object) row, (Object) col));
            // row too low
            assertFalse(String.format(errorMessage, "true", row, col),
                (boolean) valid.invoke(game, (Object) (row - HEIGHT), (Object) col));
            // row too high
            assertFalse(String.format(errorMessage, "true", row, col),
                (boolean) valid.invoke(game, (Object) (row + HEIGHT), (Object) col));
            // col too low
            assertFalse(String.format(errorMessage, "true", row, col),
                (boolean) valid.invoke(game, (Object) row, (Object) (col - WIDTH)));
            // col too high
            assertFalse(String.format(errorMessage, "true", row, col),
                (boolean) valid.invoke(game, (Object) row, (Object) (col + WIDTH)));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail("Failed to call valid."
                + "Did you declare the method correctly?");
        }
    }

    @Test
    public void testPlayerAtGoal()
    {
        String errorMessage = "playerAtGoal returned %s when:\n"
                    + "player row = %d\nplayer col = %d\n"
                    + "goal row = %d\ngoal col = %d\n";
        Method playerAtGoal = getMethod("playerAtGoal");
        int[] spot = getOpenSpot(game);
        game.setPlayerRow(spot[0]);
        game.setPlayerCol(spot[1]);
        game.setGoalRow(spot[0]);
        game.setGoalCol(spot[1]);
        try {
            // rows and columns equal
            assertTrue(String.format(errorMessage, "false",
                game.getPlayerRow(), game.getPlayerCol(), game.getGoalRow(), game.getGoalCol()),
                (boolean) playerAtGoal.invoke(game));
            // rows different, columns equal
            int[] newSpot;
            do {
                newSpot = getOpenSpot(game);
            } while (newSpot[0] == spot[0] && newSpot[1] == spot[1]);
            game.setGoalRow(newSpot[0]);
            game.setGoalCol(newSpot[1]);
            assertFalse(String.format(errorMessage, "true", spot[0],
                spot[1],newSpot[0], newSpot[1]),
                (boolean) playerAtGoal.invoke(game));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            fail("Failed to call playerAtGoal."
                + "Did you declare the method correctly?");
        }
    }

    @Test
    public void testPrompt()
    {
        Method prompt = getMethod("prompt");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        try
        {
            prompt.invoke(game);
            String output = TestUtilities.getOutput(baos);
            String correct = MazeTestUtils.foo(game) + "Enter your move (up, down, left, right, or q to quit): ";
            assertEquals("incorrect output from prompt", correct, output);
            System.setOut(oldOut);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            System.setOut(oldOut);
            fail("Failed to call prompt."
                + "Did you declare the method correctly?");
        }
    }

    @Test
    public void testCopyTwoDimBoolArray()
    {
        boolean[][] a = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[i].length; j++)
            {
                a[i][j] = Math.random() < .5 ? true : false;
            }
        }
        
        Method copyTwoDimBoolArray = getMethod("copyTwoDimBoolArray");
        try
        {
            boolean[][] copy = (boolean[][]) copyTwoDimBoolArray.invoke(game, (Object) a);
            assertEquals("copyTwoDimBoolArray produced an array of a different length",
                a.length, copy.length);
            for (int i = 0; i < a.length; i++)
            {
                assertEquals("copyTwoDimBoolArray produced a subarray of a different length",
                    a[i].length, copy[i].length);
                for (int j = 0; j < a.length; j++)
                {
                    assertEquals("copyTwoDimBoolArray did not correctly copy all values",
                        a[i][j], copy[i][j]);
                }
            }
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            fail("Failed to call copyTwoDimBoolArray."
                + "Did you declare the method correctly?");
        }
        
    }

    private Method getMethod(String methodName)
    {
        Method[] methods = MazeGame.class.getDeclaredMethods();
        for (Method m : methods)
        {
            if (m.getName().equals(methodName))
            {
                m.setAccessible(true);
                return m;
            }
        }
        fail("Could not find method: " + methodName);
        return null;
    }

    private Field getField(String fieldName)
    {
        Field[] fields = MazeGame.class.getDeclaredFields();
        for (Field f : fields)
        {
            if (f.getName().equals(fieldName))
            {
                f.setAccessible(true);
                return f;
            }
        }
        fail("Could not find field: " + fieldName);
        return null;
    }

    private String getRandomDirection()
    {
        String direction = "";
        int directionType = (int) (Math.random() * 6);
        switch (directionType)
        {
            case 0:
                direction += Math.random() < .5 ? "u" : "U";
                break;
            case 1:
                direction += Math.random() < .5 ? "d" : "D";
                break;
            case 2:
                direction += Math.random() < .5 ? "r" : "R";
                break;
            case 3:
                direction += Math.random() < .5 ? "l" : "L";
                break;
            case 4:
                direction += Math.random() < .5 ? "q" : "Q";
                break;
            case 5:
                direction += (char)((int) (Math.random() * 126 + 32));
                break;
        }
        int numExtraChars = (int) (Math.random() * 20);
        for (int i = 0; i < numExtraChars; i++)
        {
            direction += (char)((int) (Math.random() * 126 + 32));
        }
        return direction;
    }
}
