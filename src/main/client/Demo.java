package client;

import java.io.FileNotFoundException;

import maze.MazeGame;

/**
 * Demo used to manually test the maze game.
 * 
 * @author Willow Sapphire
 * @version 04/06/2023
 */
public class Demo
{
	/**
     * By default, plays the maze game.
     * You can edit it to test specific methods.
     * 
     * Runs the demo.
     * @param args command line arguments, unused
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        String mapFile = "src/data/easy.txt";
        MazeGame game = new MazeGame(mapFile);
        game.playGame();
    }
}
