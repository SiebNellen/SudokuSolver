import java.util.*;

public class Game {
    private Sudoku sudoku;
    private final int N = 9;

    Game(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    public void showSudoku() {
        System.out.println(sudoku);
    }

    /**
     * Implementation of the AC-3 algorithm
     *
     * @return true if the constraints can be satisfied, else false
     */
    public boolean solve() {
        // TODO: implement AC-3
        Queue<Constraint> queue = new PriorityQueue<>();

        addConstraints(queue);
        while (!queue.isEmpty()){
            Constraint constraint = queue.remove();
            boolean r = constraint.revise();
            if (r && constraint.getA().getDomainSize() == 0){
                return false;
            }
            if (r){
                List<Constraint> neighbours = new ArrayList<>();
                for (Constraint c: queue){
                    if (c.getB() == constraint.getA()){
                        neighbours.add(c);
                    }
                }queue.addAll(neighbours);
            }
        }
        return true;
    }


    public void addConstraints(Queue<Constraint> queue) {
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                Field currentField = sudoku.getBoard()[i][j];
                for (Field f : currentField.getNeighbours()){
                    Constraint c = new Constraint(currentField, f);
                    queue.add(c);
                }

            }
        }
    }

    /**
     * Checks the validity of a sudoku solution
     *
     * @return true if the sudoku solution is correct
     */
    public boolean validSolution() {
        // TODO: implement validSolution function
        boolean[] numbers = new boolean[N + 1];

        for (int i = 0; i < N; i++) {
            Arrays.fill(numbers, false);
            for (int j = 0; j < N; j++) {
                int Z = sudoku.getBoard()[i][j].getValue();
                if (numbers[Z]) {
                    return false;
                }
                numbers[Z] = true;
            }
        }
        for (int i = 0; i < N; i++) {
            Arrays.fill(numbers, false);
            for (int j = 0; j < N; j++) {
                int Z = sudoku.getBoard()[j][i].getValue();
                if (numbers[Z]) {
                    return false;
                }
                numbers[Z] = true;
            }
        }

        for (int i = 0; i < N - 2; i += 3) {
            for (int j = 0; j < N - 2; j += 3) {
                Arrays.fill(numbers, false);
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        int X = i + k;
                        int Y = j + l;
                        int Z = sudoku.getBoard()[X][Y].getValue();
                        if (numbers[Z]) {
                            return false;
                        }
                        numbers[Z] = true;
                    }
                }
            }
        }
        return true;
    }

    public Sudoku getSudoku() {
        return sudoku;
    }
}
