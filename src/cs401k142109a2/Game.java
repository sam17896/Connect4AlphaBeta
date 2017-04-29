package cs401k142109a2;

import java.util.Random;
import java.util.Scanner;

public class Game {
    
     public static final int turn_you = 1;
     public static final int turn_computer = 2;
     
     public int turn;
     
     int gameOver;
     AlphaBetaPlayer player;
    
    Game(){
        turn = turn_you;
        gameOver = -1;
        player = new AlphaBetaPlayer();
        play();
    }
    
    public void play(){
        Board board = new Board();
        System.out.println(board);
        int move;
        //board.printDiagonalPosition();
        Random rand = new Random();
        while(gameOver == -1) {
            System.out.println("\n*****Turn: " + turn + "*****");
            //System.out.println(board.getPossibleMoves());
            //System.out.println("");
            if(turn == turn_you){
                System.out.print("Make a move: ");
                Scanner scanner = new Scanner(System.in);
                move = scanner.nextInt();
            } else{
                move = player.makeMove(board);
                // Computer Move
//            System.out.println("Move: " + move);
            }
            
            board.makeMove(turn, move);
            System.out.println(board);

            //System.out.println("Rows: " + board.getRowsAsStr());
            System.out.println("Rows: " + board.rows);
            //System.out.println("Cols: "+ board.getColsAsStr());
            System.out.println("Cols: "+ board.cols);
            //System.out.println("Diags: "+board.getDiagsAsStr());
            System.out.println("Diags: "+board.diagonals);

            gameOver = board.isGameOver();

            if (turn == turn_you) {
                turn = turn_computer;
            }else{
                turn = turn_you;
            }
   
    }
             System.out.println("Player " + gameOver
                + (gameOver == 1 ? " (X)" : " (O)") + " wins!");
    }
}
