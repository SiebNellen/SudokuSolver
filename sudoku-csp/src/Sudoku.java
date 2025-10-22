import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Sudoku {
    private Field[][] board;

    Sudoku(String filename) {
        this.board = readsudoku(filename);
    }

    @Override
    public String toString() {
        String output = "╔═══════╦═══════╦═══════╗\n";
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                output += "╠═══════╬═══════╬═══════╣\n";
            }
            output += "║ ";
            for (int j = 0; j < 9; j++) {
                if (j == 3 || j == 6) {
                    output += "║ ";
                }
                output += board[i][j] + " ";
            }

            output += "║\n";
        }
        output += "╚═══════╩═══════╩═══════╝\n";
        return output;
    }

    /**
     * Reads sudoku from file
     *
     * @param filename
     * @return 2d int array of the sudoku
     */
    public static Field[][] readsudoku(String filename) {
        assert filename != null && filename != "" : "Invalid filename";
        String line = "";
        Field[][] grid = new Field[9][9];
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            Scanner scanner = new Scanner(inputStream);
            for (int i = 0; i < 9; i++) {
                if (scanner.hasNext()) {
                    line = scanner.nextLine();
                    for (int j = 0; j < 9; j++) {
                        int numValue = Character.getNumericValue(line.charAt(j));
                        if (numValue == 0) {
                            grid[i][j] = new Field();
                        } else if (numValue != -1) {
                            grid[i][j] = new Field(numValue);
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("error opening file: " + filename);
        }
        addNeighbours(grid);
        return grid;
    }

    /**
     * Adds a list of neighbours to each field, i.e., arcs to be satisfied
     *
     * @param grid
     */
    private static void addNeighbours(Field[][] grid) {
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
                grid[x][y].setNeighbours(new ArrayList<>());
                int [] topLeft = getBox(x,y);
                // same box
                for (int i = topLeft[0]; i < topLeft[0]+3; i++){
                    for (int j = topLeft[1]; j < topLeft[1] + 3; j++){
                            if (x != i && y != j && !grid[x][y].getNeighbours().contains(grid[i][j])) {
                                grid[x][y].getNeighbours().add(grid[i][j]);
                            }
                    }
                }
                for (int z = 0; z < 9; z++){
                    // horizontal
                    if (y != z && !grid[x][y].getNeighbours().contains(grid[x][z])){
                        grid[x][y].getNeighbours().add(grid[x][z]);
                    }
                    // vertical
                    if (x != z && !grid[x][y].getNeighbours().contains(grid[z][y])) {
                        grid[x][y].getNeighbours().add(grid[z][y]);
                    }
                }
            }
        }
    }

    /**
     * Generates fileformat output
     */
    public String toFileString() {
        String output = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                output += board[i][j].getValue();
            }
            output += "\n";
        }
        return output;
    }

    public Field[][] getBoard() {
        return board;
    }

    public static int[] getBox(int x, int y){
        int returnX = 0;
        int returnY = 0;
        switch (x) {
            case 3, 4, 5 -> returnX = 3;
            case 6, 7, 8 -> returnX = 6;
        }
        switch (y) {
            case 3, 4, 5 -> returnY = 3;
            case 6, 7, 8 -> returnY = 6;
        }
        return new int[]{returnX, returnY};
    }
}
