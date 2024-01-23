package maze;

import labtests.util.StructureTest;
import labtests.util.specs.ClassSpec;
import labtests.util.specs.ConstructorSpec;
import labtests.util.specs.FieldSpec;
import labtests.util.specs.MethodSpec;

/**
 * Tests the field and method declarations in MazeGame.
 * 
 * @author Willow Sapphire
 * @version 04/27/2023
 */
public class MazeGameStructureTest extends StructureTest
{
    @Override
    protected String getClassName()
    {
        return "maze.MazeGame";
    }

    @Override
    protected ClassSpec getClassSpec()
    {
        return new ClassSpec(getClassName(), "public", 
            false, false, false);
    }

    @Override
    protected FieldSpec[] getFieldSpecs()
    {
        FieldSpec[] fields = new FieldSpec[10];
        fields[0] = new FieldSpec("HEIGHT", "public", 
            true, true, "int");
        fields[1] = new FieldSpec("WIDTH", "public", 
            true, true, "int");
        fields[2] = new FieldSpec("ROW", "private", 
            true, true, "int");
        fields[3] = new FieldSpec("COL", "private", 
            true, true, "int");
        fields[4] = new FieldSpec("playerInput", "private", 
            false, false, "java.util.Scanner");
        fields[5] = new FieldSpec("blocked", "private", 
            false, false, "[[Z");
        fields[6] = new FieldSpec("visited", "private", 
            false, false, "[[Z");
        fields[7] = new FieldSpec("player", "private", 
            false, false, "[I");
        fields[8] = new FieldSpec("goal", "private", 
            false, false, "[I");
        fields[9] = new FieldSpec("start", "private", 
            false, false, "[I");
        return fields;
    }

    @Override
    protected ConstructorSpec[] getConstructorSpecs()
    {
        ConstructorSpec[] constructors = new ConstructorSpec[2];
        constructors[0] = new ConstructorSpec("maze.MazeGame", 
            "public", new String[]{"java.lang.String"});
        constructors[1] = new ConstructorSpec("maze.MazeGame", 
            "public", new String[]{"java.lang.String", "java.util.Scanner"});
        return constructors;
    }

    @Override
    protected MethodSpec[] getMethodSpecs()
    {
        MethodSpec[] methods = new MethodSpec[27];
        methods[0] = new MethodSpec("playGame", "public", false, 
            false, false, false, new String[]{}, "void");
        methods[1] = new MethodSpec("printMaze", "public", false, 
            false, false, false, new String[]{}, "void");
        methods[2] = new MethodSpec("getPlayerRow", "public", false, 
            false, false, false, new String[]{}, "int");
        methods[3] = new MethodSpec("getPlayerCol", "public", false, 
            false, false, false, new String[]{}, "int");
        methods[4] = new MethodSpec("getStartRow", "public", false, 
            false, false, false, new String[]{}, "int");
        methods[5] = new MethodSpec("getStartCol", "public", false, 
            false, false, false, new String[]{}, "int");
        methods[6] = new MethodSpec("getGoalRow", "public", false, 
            false, false, false, new String[]{}, "int");
        methods[7] = new MethodSpec("getGoalCol", "public", false, 
            false, false, false, new String[]{}, "int");
        methods[8] = new MethodSpec("getBlocked", "public", false, 
            false, false, false, new String[]{}, "[[Z");
        methods[9] = new MethodSpec("getVisited", "public", false, 
            false, false, false, new String[]{}, "[[Z");
        methods[10] = new MethodSpec("getPlayerInput", "public", false, 
            false, false, false, new String[]{}, "java.util.Scanner");
        methods[11] = new MethodSpec("setPlayerRow", "public", false,
            false, false, false, new String[]{"int"}, "void");
        methods[12] = new MethodSpec("setPlayerCol", "public", false,
            false, false, false, new String[]{"int"}, "void");
        methods[13] = new MethodSpec("setStartRow", "public", false,
            false, false, false, new String[]{"int"}, "void");
        methods[14] = new MethodSpec("setStartCol", "public", false,
            false, false, false, new String[]{"int"}, "void");
        methods[15] = new MethodSpec("setGoalRow", "public", false,
            false, false, false, new String[]{"int"}, "void");
        methods[16] = new MethodSpec("setGoalCol", "public", false,
            false, false, false, new String[]{"int"}, "void");
        methods[17] = new MethodSpec("setBlocked", "public", false,
            false, false, false, new String[]{"[[Z"}, "void");
        methods[18] = new MethodSpec("setVisited", "public", false,
            false, false, false, new String[]{"[[Z"}, "void");
        methods[19] = new MethodSpec("setPlayerInput", "public", false,
            false, false, false, new String[]{"java.util.Scanner"}, "void");
        methods[20] = new MethodSpec("loadMaze", "private", false,
            false, false, false, new String[]{"java.lang.String"}, "void");
        methods[21] = new MethodSpec("prompt", "private", false,
            false, false, false, new String[]{}, "void");
        methods[22] = new MethodSpec("playerAtGoal", "private", false,
            false, false, false, new String[]{}, "boolean");
        methods[23] = new MethodSpec("makeMove", "private", false,
            false, false, false, new String[]{"java.lang.String"}, "boolean");
        methods[24] = new MethodSpec("visit", "private", false,
            false, false, false, new String[]{"int", "int"}, "void");
        methods[25] = new MethodSpec("valid", "private", false,
            false, false, false, new String[]{"int", "int"}, "boolean");
        methods[26] = new MethodSpec("copyTwoDimBoolArray", "private", false,
            false, false, false, new String[]{"[[Z"}, "[[Z");
        return methods;
    }
}
