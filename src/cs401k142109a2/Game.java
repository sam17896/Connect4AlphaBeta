package cs401k142109a2;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    
     public static final int turn_you = 1;
     public static final int turn_computer = 2;
     
     public int turn;
     
     int gameOver;
     boolean gameDraw;
     AlphaBetaPlayer player;
    
    Game(){
        turn = turn_you;
        gameOver = -1;
        gameDraw = false;
        player = new AlphaBetaPlayer();
        play();
    }
    
    public void play(){
        Board board = new Board();
        System.out.println(board);
        int move;
      
        Random rand = new Random();
        while(gameOver == -1 && !gameDraw) {
            if(turn == turn_you){
                System.out.println("\n*****Player's Turn*****\n");
            }else{
                System.out.println("\n*****Computer's Turn*****\n");
            }
        
            if(turn == turn_you){
                System.out.print("Make a move: ");
                Scanner scanner = new Scanner(System.in);
                move = scanner.nextInt();
            }
            else{
                move = player.makeMove(board);
                System.out.println("\nComputer makes move at column " + move);
            }
            
            board.makeMove(turn, move);
            System.out.println(board);
            
            
            //This should be commented out after final testing 
            System.out.println("Rows: " + board.rows);
            System.out.println("Cols: "+ board.cols);
            System.out.println("Diags: "+board.diagonals);

            gameOver = board.isGameOver();

            turn = changeTurn(turn);
            
            List<Integer> availableMoves = board.getPossibleMoves();
            System.out.println(availableMoves.size());
  
            if (availableMoves.size()==0){
                gameDraw = true;
            }
   
    }
        if(gameOver != -1){
            if (gameOver == 1){
                System.out.println("\nPlayer (X) wins\n");
            }else if (gameOver==2){
                System.out.println("\nPlayer (O) wins\n");
                
            }
        } else if (gameDraw){
            System.out.println("\nGame Draw\n");
        }
            
    }

    private int changeTurn(int t) {
        if (t == turn_you) {
            t = turn_computer;
        }else{
            t = turn_you;
        }
        return t;
    }
}
