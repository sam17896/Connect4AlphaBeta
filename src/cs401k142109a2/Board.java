package cs401k142109a2;
import java.util.ArrayList;
import java.util.List;

class Board {
    public static final int ROWS = 4;
    public static final int COLS = 6;
    
    private int[][] board;
    
    protected int[] indices;

    protected List<String> rows;
    protected List<String> cols;
    protected List<String> diagonals;
    
    String[][] diagonalPosition;

    public Board() {
    
        board = new int[ROWS][COLS];
        diagonalPosition =  new String[ROWS][COLS];
        indices = new int[6];
        

        rows = new ArrayList();
        cols = new ArrayList();
        diagonals = new ArrayList();
                
        setRCD();
    }
    
    public void undoMove(int turn, int column) {
        
        // turn = 1 for player
        // turn = 2 for computer
        
        // column = move taken by player
        
        int row = indices[column];
        if (row == 0 || row > ROWS) {
            System.out.println("\nError: Can't undo this move\n");
        }
        indices[column]--;
        board[row-1][column] = 0;
        updateRCD(0, row-1, column);
 
    }

    public int[][] getBoardArray() {
        return board;
    }
    
    public int pieceAt(int row, int col) {
        return board[row][col];
    }

    
    // to print the board
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

    public void makeMove(int turn, int column) {
        
        // turn = 1 for player
        // turn = 2 for computer
        
        // column = move taken by player
       
        int row = indices[column];
        if (row != ROWS) {
            board[row][column] = turn;
            indices[turn]++;
                
            updateRCD(turn, row, column);
            return;
        }
        
        System.out.println("\nError: Wrong Move\n");
    }
    private void updateRCD(int piece, int row, int col) {
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
                String d = diagonals.get(pos);
                d = d.substring(0,posX) +  newPiece + d.substring(posX+1); 
                diagonals.set(pos, d);
            }
        }
        
    }
    

    //return: 0 for no win, 1 for player 1 win, 2 for player 2 win
    public int isGameOver() {
    
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
            if (indices[col] != ROWS) {
                result.add(col);
            }
        }
        return result;
    }

    protected void setRCD() {
        
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

}

