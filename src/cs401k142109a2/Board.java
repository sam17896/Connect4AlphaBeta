package cs401k142109a2;
import java.util.ArrayList;
import java.util.List;

class Board {
    public static final int ROWS = 4;
    public static final int COLS = 6;
    //board[row][col]
    private int[][] board;
    
    //gives the first empty row in each column.
    //very useful.
    protected int[] indices;

    //rows, columns, and diagonals as strings
    protected List<String> rows;
    protected List<String> cols;
    protected List<String> diagonals;
    
    String[][] diagonalPosition;

    public Board() {
        //board[row][col]
        board = new int[ROWS][COLS];
        diagonalPosition =  new String[ROWS][COLS];
        indices = new int[6];
        

        rows = new ArrayList();
        cols = new ArrayList();
        diagonals = new ArrayList();
                
        refreshRCD();
    }
    
    public void undoMove(int player_piece, int move_position) {
        //System.out.println("before undo\n" + this);
        int row = indices[move_position];
        if (row == 0 || row > ROWS) {
            throw new RuntimeException("Illegal undoMove("
                    +player_piece + "," + move_position + ")");
        }
        indices[move_position]--;
        board[row-1][move_position] = 0;
        refreshRCD2(0, row-1, move_position);
        //System.out.println("after undo\n" + this);
    }
/*
    public Board(Board other) {
        board = new int[ROWS][COLS];
        indices = new int[6];

        rows = new ArrayList();
        cols = new ArrayList();
        diagonals = new ArrayList();

        for (int row = 0; row < ROWS; row++) {
            System.arraycopy(other.board[row], 0, board[row], 0, COLS);
        }
        System.arraycopy(other.indices, 0, indices, 0, 6);

        position_to_down_left_diagonal = new int[ROWS][COLS][2];
        position_to_down_right_diagonal = new int[ROWS][COLS][2];

        refreshRCD();
    }

    public Board invert() {
        Board other = new Board();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == 1) {
                    other.makeMove(2, col);
                }
                else if (board[row][col] == 2) {
                    other.makeMove(1, col);
                }
            }
        }
        return other;
    }
*/
    public int[][] getBoardArray() {
        return board;
    }
    public int pieceAt(int row, int col) {
        return board[row][col];
    }

    @Override
    public String toString() {
        String Board = new String();
        
        for(int row = ROWS-1; row >= 0; row--) {
            for(int col = 0; col < COLS; col++) {
                switch(board[row][col]) {
                    case 1: Board += "X"; break;
                    case 2: Board += "O"; break;
                    default:Board += "-"; break;
                }
                if (col != COLS-1) {
                    Board += " ";
                }
            }
            Board += "\n";
        }
        Board += "0 1 2 3 4 5\n";
        return Board;
    }

    public void makeMove(int player_piece, int move_position) {
        //get the empty row value in indices
        int row = indices[move_position];
        if (row != ROWS) {
            board[row][move_position] = player_piece;
            //System.out.println(player_piece + " player piece");
            indices[move_position]++;
                
            //now, correct the row/col/diagonal storage
            refreshRCD2(player_piece, row, move_position);
            return;
        }
        //if the program has not returned, we have an issue
        throw new RuntimeException("Illegal makeMove(" + player_piece 
                + "," + move_position + ")");
    }
    private void refreshRCD2(int piece, int row, int col) {
        char newPiece = (char)(piece+48);

        String r = rows.get(row);
        r = r.substring(0,col) + newPiece + r.substring(col+1);
        rows.set(row, r);

        String c = cols.get(col);
        c = c.substring(0,row) + newPiece + c.substring(row+1);
        cols.set(col, c);
        
        if(diagonalPosition[row][col] == ""){
            
        }else{
            String arg[] = diagonalPosition[row][col].split(":");
            if (arg.length > 4){
                int q = Integer.parseInt(arg[1]);
                int w = Integer.parseInt(arg[2]);
                
                String e = diagonals.get(q);
                e = e.substring(0,w) +  newPiece + e.substring(w+1); 
                diagonals.set(q, e);
                
                int i = Integer.parseInt(arg[4]);
                int t = Integer.parseInt(arg[5]);
                
                String y = diagonals.get(i);
                y = y.substring(0,t) +  newPiece + y.substring(t+1); 
                diagonals.set(i, y);
                
            }else{
                int pos = Integer.parseInt(arg[1]);
                int posX = Integer.parseInt(arg[2]);
                //  System.out.println(pos + " " + posX + " yahan print ho raha hai andhe");
                String d = diagonals.get(pos);
                d = d.substring(0,posX) +  newPiece + d.substring(posX+1); 
                diagonals.set(pos, d);
            }
        }
        
    }
    

    //return: 0 for no win, 1 for player 1 win, 2 for player 2 win
    public int isGameOver() {
        //refreshRCD();
        for (String s : rows) {
            if (s.contains("1111")) {
                return 1;
            }
            else if(s.contains("2222")) {
                return 2;
            }
        }
        for (String s : cols) {
            if (s.contains("1111")) {
                return 1;
            }
            else if(s.contains("2222")) {
                return 2;
            }
        }
        for (String s : diagonals) {
            if (s.contains("1111")) {
                return 1;
            }
            else if(s.contains("2222")) {
                return 2;
            }
        }
        return -1;
    }

    public List<Integer> getPossibleMoves() {
        List<Integer> result = new ArrayList();
        for(int col = 0; col < COLS; col++) {
            //check if any of the columns are not full
            if (indices[col] != ROWS) {
                result.add(col);
            }
        }
        return result;
    }

//    public List<Move> getPossibleMoves(int player) {
//        List<Move> result = new ArrayList();
//        for(int col = 0; col < COLS; col++) {
//            //check if any of the columns are empty
//            boolean isColumnFull = true;
//            searchcol:
//            for(int row = 0; row < ROWS; row++) {
//                if (board[row][col] == 0) {
//                    isColumnFull = false;
//                    break searchcol;
//                }
//            }
//            if (!isColumnFull) {
//                result.add(new Move(player, col));
//            }
//        }
//        return result;
//    }

    /*
     * all of the rows, columns, and diagonals will be
     * encoded in strings.
     * this will then allow for the easy searching of
     * substrings using the string api
     *
     */
    //rows, columns, and diagonals
//    protected List<String> getRCDasStr() {
//        List<String> result = new ArrayList();
//        result.addAll(getRowsAsStr());
//        result.addAll(getColsAsStr());
//        result.addAll(getDiagsAsStr());
//        return result;
//    }
    protected void refreshRCD() {
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                diagonalPosition[i][j] = "";
            }
        }
        rows = getRowsAsStr();
        cols = getColsAsStr();
        diagonals = getDiagsAsStr();
    }
    
    protected List<String> getRowsAsStr() {
        List<String> result = new ArrayList();
        for(int row = 0; row < ROWS; row++) {
            String s = "";
            for (int col = 0; col < COLS; col++) {
                s += board[row][col];
            }
            result.add(s);
        }
        return result;
    }

    protected List<String> getColsAsStr() {
        List<String> result = new ArrayList();
        for (int col = 0; col < COLS; col++) {
            String s = "";
            for(int row = 0; row < ROWS; row++) {
                s += board[row][col];
            }
            result.add(s);
        }
        return result;
    }
    
    protected List<String> getDiagsAsStr()  {
        List<String> result = new ArrayList();
        int count = 0;
        // right diagonals
        int row = 0;
        for(int i=0;i<3;i++){
            int col = i;
            String s = "";
            for (int j=0;j<4;j++){
                s += board[row][col];
                diagonalPosition[row][col] += "1:" + count + ":" + j + ":";
                row++;
                col++;
            }
            count++;
            row = 0;
            result.add(s);
        }
        
        //left diagonals
        row = 3;
        for(int i=0;i<3;i++){
            int col = i;
            String s = "";
            for (int j=0;j<4;j++){
                s += board[row][col];
                diagonalPosition[row][col] += "2:" + count + ":" + j;
                row--;
                col++;
            }
            count++;
            row = 3;
            result.add(s);
        }
        
        return result;
    }
    void printDiagonalPosition(){
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                System.out.println(i+" "+j+" "+diagonalPosition[i][j]);
            }
        }
    }

//    //changes the position (row, column) to a corresponding
//    //down-left diagonal position.
//    public static final int[][][] left_diag_lookup =
//        { { {9, 5}, {8, 5}, {7, 4}, {6, 3}, {-1, 0}, {-1,0}, {-1,0} },
//          { {10, 4},{9, 4}, {8, 4}, {7, 3}, {6, 2}, {-1, 0}, {-1, 0}},
//          { {11,3}, {10,3}, {9, 3}, {8, 3}, {7, 2}, {6, 1}, {-1, 0}},
//          { {-1,0}, {11,2}, {10,2}, {9, 2}, {8, 2}, },
//          {},
//          {}};
}

