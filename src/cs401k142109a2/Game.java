package cs401k142109a2;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    
     int turn_player = 1;
     int turn_computer = 2;
     
     public int turn;
     
     int gameOver;
     boolean gameDraw;
     AlphaBetaPlayer player;
    
    Game(){
        turn = turn_player;
        gameOver = -1;
        gameDraw = false;
        player = new AlphaBetaPlayer();
        play();
    }
    
    public void play(){
        
        Board board = new Board();
        int move;
        
        System.out.println("Board is of size " + board.rows + " " + board.cols + "\n");
        System.out.println("Player is 'X'\n");
        System.out.println("Computer is 'O'\n");
        System.out.println("Empty place '-'\n");
        
        Random rand = new Random();
        
        System.out.println(board);
        
        while(gameOver == -1 && !gameDraw) {
            if(turn == turn_player){
                System.out.println("\n*****Player's Turn*****\n");
            }else{
                System.out.println("\n*****Computer's Turn*****\n");
            }
        
            if(turn == turn_player){
                System.out.println("\nAvailable Moves: \n");
                List<Integer> movelist = board.getPossibleMoves();
                
                for (int i : movelist){
                    System.out.print(i + " ");
                }
                
                System.out.print("\nMake a move: \n");
                
                Scanner scanner = new Scanner(System.in);
                move = scanner.nextInt();
            }
            else{
                
                move = player.makeMove(board);
                System.out.println("\nComputer makes move at column " + move);
                System.out.println("Computer evaluated " + player.counter + " States");
                player.counter = 0;
            }
            
            board.makeMove(turn, move);
            
            
            if(turn == turn_player && board.moveTaken){
                System.out.println(board);
            
            
                //This should be commented out after final testing 
            //     System.out.println("Rows: " + board.Srow);
            //      System.out.println("Cols: "+ board.Scol);
            //     System.out.println("Diags: "+board.Sdiagonals);

                gameOver = board.isGameOver();
                List<Integer> availableMoves = board.getPossibleMoves(); 
                if (availableMoves.size()==0){
                    gameDraw = true;
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
                turn = changeTurn(turn);
                board.moveTaken = false;
            }else if (turn == turn_computer){
                
                System.out.println(board);
            
            
                //This should be commented out after final testing 
            //      System.out.println("Rows: " + board.Srow);
            //      System.out.println("Cols: "+ board.Scol);
            //      System.out.println("Diags: "+board.Sdiagonals);

                gameOver = board.isGameOver();
                List<Integer> availableMoves = board.getPossibleMoves(); 
                if (availableMoves.size()==0){
                    gameDraw = true;
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
                turn = changeTurn(turn);
                board.moveTaken = false;
            }
           
    }
    }

    private int changeTurn(int t) {
        if (t == turn_player) {
            t = turn_computer;
        }else{
            t = turn_player;
        }
        return t;
    }
}
