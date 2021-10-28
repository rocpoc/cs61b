package cube;

import java.util.Observable;

/** Models an instance of the Cube puzzle: a cube with color on some sides
 *  sitting on a cell of a square grid, some of whose cells are colored.
 *  Any object may register to observe this model, using the (inherited)
 *  addObserver method.  The model notifies observers whenever it is modified.
 *  @author P. N. Hilfinger
 */

class CubeModel extends Observable {
    /** Side variable. */
    private int _side;
    /** Move counter variable. */
    private int movecounter;
    /** Row variable. */
    private int _row;
    /** Column variable. */
    private int _col;
    /** 2D Boolean Array for Grid. */
    private boolean[][] gridpainted;
    /** Face values. */
    private boolean[] facebook;

    /** Initialize the Cube. */
    CubeModel() {
        movecounter = 0;
        _row = 0;
        _col = 0;
        _side = 4;
        facebook = new boolean[6];
        gridpainted = new boolean[_side][_side];
        initialize(_side, _row, _col, gridpainted, facebook);
    }

    /** A copy of CUBE. */
    CubeModel(CubeModel cube) {
        initialize(cube);
    }

    /** Initialize puzzle of size SIDExSIDE with the cube initially at
     *  ROW0 and COL0, with square r, c painted iff PAINTED[r][c], and
     *  with face k painted iff FACEPAINTED[k] (see isPaintedFace).
     *  Assumes that
     *    * SIDE > 2.
     *    * PAINTED is SIDExSIDE.
     *    * 0 <= ROW0, COL0 < SIDE.
     *    * FACEPAINTED has length 6.
     */
    void initialize(int side, int row0, int col0, boolean[][] painted,
                    boolean[] facePainted) {
        this._side = side;
        this._row = row0;
        this._col = col0;

        for (int i = 0; i < facePainted.length; i++) {
            this.facebook[i] = facePainted[i];
        }
        for (int i = 0; i < painted.length; i++) {
            for (int j = 0; j < painted.length; j++) {
                this.gridpainted[i][j] = painted[i][j];
            }
        }
        setChanged();
        notifyObservers();
    }

    /** Initialize puzzle of size SIDExSIDE with the cube initially at
     *  ROW0 and COL0, with square r, c painted iff PAINTED[r][c].
     *  The cube is initially blank.
     *  Assumes that
     *    * SIDE > 2.
     *    * PAINTED is SIDExSIDE.
     *    * 0 <= ROW0, COL0 < SIDE.
     */
    void initialize(int side, int row0, int col0, boolean[][] painted) {
        initialize(side, row0, col0, painted, new boolean[6]);
    }

    /** Initialize puzzle to be a copy of CUBE. */
    void initialize(CubeModel cube) {
        movecounter = cube.moves();
        _side = cube.side();
        _row = cube.cubeRow();
        _col = cube.cubeCol();
        gridpainted = cube.gridpainted.clone();
        facebook = cube.facebook.clone();
        initialize(_side, _row, _col, gridpainted, facebook);
        setChanged();
        notifyObservers();
    }

    /** Move the cube to (ROW, COL), if that position is on the board and
     *  vertically or horizontally adjacent to the current cube position.
     *  Transfers colors as specified by the rules.
     *  Throws IllegalArgumentException if preconditions are not met.
     */
    void move(int row, int col) {
        boolean temp0 = isPaintedFace(0);
        boolean temp1 = isPaintedFace(1);
        boolean temp2 = isPaintedFace(2);
        boolean temp3 = isPaintedFace(3);
        boolean temp4 = isPaintedFace(4);
        boolean temp5 = isPaintedFace(5);

        if ((Math.abs(this._row - row) == 1 && Math.abs(this._col - col) > 0)
                || (Math.abs(this._col - col) == 1
                && Math.abs(this._row - row) > 0)) {
            throw new IllegalArgumentException("Object outta bounds!");
        } else if ((Math.abs(this._row - row) == 1
                && Math.abs(this._col - col) == 1)
                || (Math.abs(this._row - row) > 1
                || Math.abs(this._col - col) > 1)) {
            throw new IllegalArgumentException("can't do diagonals.");
        } else if (row >= 0 && row < this.side()
                && col >= 0 && col < this.side()) {

            movecounter++;

            if (row == this._row - 1 && col == this._col) {
                downflip(row, col);

            } else if (row == this._row + 1 && col == this._col) {
                upflip(row, col);

            } else if (row == this._row && col == this._col - 1) {
                leftflip(row, col);

            } else if (row == this._row && col == this._col + 1) {
                rightflip(row, col);
            }

            this._row = row;
            this._col = col;
            allFacesPainted();
        } else {
            throw new IllegalArgumentException("Off da board.");
        }
        setChanged();
        notifyObservers();
    }

    /** Return the number of squares on a side. */
    int side() {
        return _side;
    }

    /** Return true iff square ROW, COL is painted.
     *  Requires 0 <= ROW, COL < board size. */
    boolean isPaintedSquare(int row, int col) {
        return gridpainted[row][col];
    }

    /** Return current row of cube. */
    int cubeRow() {
        return _row;
    }

    /** Return current column of cube. */
    int cubeCol() {
        return _col;
    }

    /** Return the number of moves made on current puzzle. */
    int moves() {
        return movecounter;
    }

    /** Return true iff face #FACE, 0 <= FACE < 6, of the cube is painted.
     *  Faces are numbered as follows:
     *    0: Vertical in the direction of row 0 (nearest row to player).
     *    1: Vertical in the direction of last row.
     *    2: Vertical in the direction of column 0 (left column).
     *    3: Vertical in the direction of last column.
     *    4: Bottom face.
     *    5: Top face.
     */
    boolean isPaintedFace(int face) {
        if (facebook == null) {
            return false;
        } else {
            return facebook[face];
        }
    }

    /** Return true iff all faces are painted. */
    boolean allFacesPainted() {
        for (boolean value : facebook) {
            if (!value) {
                return false;
            }
        } return true;
    }

    /** Flips Cube downwards, takes in ROWW & COLUMN. */
    void downflip(int roww, int column) {
        boolean temp0 = isPaintedFace(0);
        boolean temp1 = isPaintedFace(1);
        boolean temp2 = isPaintedFace(2);
        boolean temp3 = isPaintedFace(3);
        boolean temp4 = isPaintedFace(4);
        boolean temp5 = isPaintedFace(5);
        if (this.gridpainted[roww][column] != this.facebook[0]) {
            if (!temp0) {
                temp0 = true;
                gridpainted[roww][column] = false;
            } else if (temp0) {
                temp0 = false;
                gridpainted[roww][column] = true;
            }
        }
        facebook[5] = temp1;
        facebook[1] = temp4;
        facebook[4] = temp0;
        facebook[0] = temp5;
    }

    /** Flips Cube up, takes in ROWW & COLUMN. */
    void upflip(int roww, int column) {
        boolean temp0 = isPaintedFace(0);
        boolean temp1 = isPaintedFace(1);
        boolean temp2 = isPaintedFace(2);
        boolean temp3 = isPaintedFace(3);
        boolean temp4 = isPaintedFace(4);
        boolean temp5 = isPaintedFace(5);
        if (this.gridpainted[roww][column] != this.facebook[1]) {
            if (!temp1) {
                temp1 = true;
                gridpainted[roww][column] = false;
            } else if (temp1) {
                temp1 = false;
                gridpainted[roww][column] = true;
            }
        }
        facebook[0] = temp4;
        facebook[5] = temp0;
        facebook[1] = temp5;
        facebook[4] = temp1;
    }
    /** Flips cube to the left, takes in ROWW & COLUMN. */
    void leftflip(int roww, int column) {
        boolean temp0 = isPaintedFace(0);
        boolean temp1 = isPaintedFace(1);
        boolean temp2 = isPaintedFace(2);
        boolean temp3 = isPaintedFace(3);
        boolean temp4 = isPaintedFace(4);
        boolean temp5 = isPaintedFace(5);
        if  (this.gridpainted[roww][column] != this.facebook[2]) {
            if (!temp2) {
                temp2 = true;
                this.gridpainted[roww][column] = false;
            } else if (temp2) {
                temp2 = false;
                this.gridpainted[roww][column] = true;
            }
        }
        facebook[2] = temp5;
        facebook[5] = temp3;
        facebook[3] = temp4;
        facebook[4] = temp2;
    }
    /** Flips cube to the right, takes in ROWW & COLUMN. */
    void rightflip(int roww, int column) {
        boolean temp0 = isPaintedFace(0);
        boolean temp1 = isPaintedFace(1);
        boolean temp2 = isPaintedFace(2);
        boolean temp3 = isPaintedFace(3);
        boolean temp4 = isPaintedFace(4);
        boolean temp5 = isPaintedFace(5);
        if (this.gridpainted[roww][column] != this.facebook[3]) {
            if (!temp3) {
                temp3 = true;
                gridpainted[roww][column] = false;
            } else if (temp3) {
                temp3 = false;
                gridpainted[roww][column] = true;
            }
        }
        facebook[2] = temp4;
        facebook[5] = temp2;
        facebook[3] = temp5;
        facebook[4] = temp3;
    }

}
