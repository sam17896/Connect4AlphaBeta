package cs401k142109a2;
import java.util.ArrayList;
import java.util.List;

class Board {
    int rows = 4;
    int cols = 6;
    
    boolean moveTaken;
    
    private int[][] board;
    
    protected int[] columnPos;

    protected List<String> Srow;
    protected List<String> Scol;
    protected List<String> Sdiagonals;
    
    String[][] diagonalPosition;

    public Board() {
    
        board = new int[rows][cols];
        diagonalPosition =  new String[rows][cols];
        columnPos = new int[6];
        moveTaken = false;

        Srow = new ArrayList();
        Scol = new ArrayList();
        Sdiagonals = new ArrayList();
                
        initialize();
        
    }
    
    public void undoMove(int turn, int column) {
        
        // turn = 1 for player
        // turn = 2 for computer
        
        // column = move taken by player
        
        int row = columnPos[column];
        if (row == 0 || row > rows) {
            System.out.println("\nError: Can't undo this move\n");
        }else{
            columnPos[column]--;
            board[row-1][column] = 0;
            
            updateState(0, row-1, column);
         }
    }

    
    // to print the board
    @Override
    public String toString() {
        String Board = new String();
        
        for(int row = rows-1; row >= 0; row--) {
            for(int col = 0; col < cols; col++) {
                switch(board[row][col]) {
                    case 1: Board += "X"; break;
                    case 2: Board += "O"; break;
                    default:Board += "-"; break;
                }
                if (col != cols-1) {
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
        if (column > 5){
           
        }else{
            int row = columnPos[column];
            if (row != rows) {
                board[row][column] = turn;
                columnPos[column]++;

                updateState(turn, row, column);
                if(turn == 1){
                    moveTaken = true;
                }
                return;
            }
        }
        
        System.out.println("\nError: Wrong Move\n");
    }
        
    private void updateState(int piece, int row, int col) {
        char newPiece = (char)(piece+48);

        String r = Srow.get(row);
        r = r.substring(0,col) + newPiece + r.substring(col+1);
        Srow.set(row, r);

        String c = Scol.get(col);
        c = c.substring(0,row) + newPiece + c.substring(row+1);
        Scol.set(col, c);
        
        if(diagonalPosition[row][col] == ""){
            
        }else{
            String arg[] = diagonalPosition[row][col].split(":");
            if (arg.length > 4){
                int q = Integer.parseInt(arg[1]);
                int w = Integer.parseInt(arg[2]);
                
                String e = Sdiagonals.get(q);
                e = e.substring(0,w) +  newPiece + e.substring(w+1); 
                Sdiagonals.set(q, e);
                
                int i = Integer.parseInt(arg[4]);
                int t = Integer.parseInt(arg[5]);
                
                String y = Sdiagonals.get(i);
                y = y.substring(0,t) +  newPiece + y.substring(t+1); 
                Sdiagonals.set(i, y);
                
            }else{
                int pos = Integer.parseInt(arg[1]);
                int posX = Integer.parseInt(arg[2]);
                String d = Sdiagonals.get(pos);
                d = d.substring(0,posX) +  newPiece + d.substring(posX+1); 
                Sdiagonals.set(pos, d);
            }
        }
        
    }
    

    //return: -1 for no win, 1 for player 1 win, 2 for computer win
    public int isGameOver() {
    
        for (String s : Srow) {
            if (s.contains("1111")) {
                return 1;
            }
            else if(s.contains("2222")) {
                return 2;
            }
        }
        for (String s : Scol) {
            if (s.contains("1111")) {
                return 1;
            }
            else if(s.contains("2222")) {
                return 2;
            }
        }
        for (String s : Sdiagonals) {
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
        for(int col = 0; col < cols; col++) {
            if (columnPos[col] != rows) {
                result.add(col);
            }
        }
        return result;
    }

    protected void initialize() {
        
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                diagonalPosition[i][j] = "";
            }
        }
        
        Srow = getRowsAsStr();
        Scol = getColsAsStr();
        Sdiagonals = getDiagsAsStr();
    }
    
    protected List<String> getRowsAsStr() {
        List<String> result = new ArrayList();
        for(int row = 0; row < rows; row++) {
            String s = "";
            for (int col = 0; col < cols; col++) {
                s += board[row][col];
            }
            result.add(s);
        }
        return result;
    }

    protected List<String> getColsAsStr() {
        List<String> result = new ArrayList();
        for (int col = 0; col < cols; col++) {
            String s = "";
            for(int row = 0; row < rows; row++) {
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
    
}

