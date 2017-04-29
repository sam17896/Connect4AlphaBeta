package cs401k142109a2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlphaBetaPlayer {
    public static final int turn_player = 2;
    public static final int turn_computer = 1;  
    
    int turn = turn_computer;
    
    List<Integer> possibleMoves;
    List<Integer> values;

    public AlphaBetaPlayer() {
        possibleMoves = new ArrayList();
        values = new ArrayList();
    }
    
    int makeMove(Board board){
      int move = -1;
      int depth = 7;
      
      possibleMoves = board.getPossibleMoves();
      Random rand = new Random();
      
      double alpha = Double.NEGATIVE_INFINITY;
      double beta = Double.POSITIVE_INFINITY;
      
      double bestValue = alpha;
      
      while(possibleMoves.size() > 0){
          
          int index = rand.nextInt(possibleMoves.size());
          int m = possibleMoves.get(index);
          possibleMoves.remove(index);
          double score;
          board.makeMove(turn, m);
          turn = turn_player;
          score = alphabeta(turn, board,alpha,beta,depth);
          turn = turn_computer;
          board.undoMove(turn, m);
          
          if (score > bestValue) {
            bestValue = score;
            move = m;
          }
      }
    
      return move;  
    }
    
    double alphabeta(int turn, Board node, double alpha, double beta,int depth){
      //  System.out.println("i am here in alphabeta");
        if(depth==0||node.isGameOver() != -1){
            return evaluate(turn, node);
        }
   //     System.out.println("i am here in alphabeta");
        Random random = new Random();
        List<Integer> movelist = new ArrayList();
        if(turn == turn_computer){
            double score = alpha;
            movelist = node.getPossibleMoves();
            while(movelist.size()>0){
                int index = random.nextInt(movelist.size());
                int m = movelist.get(index);
                movelist.remove(index);
                System.out.println("player: " + turn + " evaluating move " +index);
                node.makeMove(turn, m);
                turn = turn_player;
                score = alphabeta(turn, node, alpha, beta,depth-1);
                turn = turn_computer;
                node.undoMove(turn, m);
               if (score > alpha)
                    alpha = score; //you have found a better move
                if (alpha >= beta)
                    return alpha; //cutoff
            }
            return alpha;
        }else {
            double score = beta;
            movelist = node.getPossibleMoves();
            while(movelist.size()>0){
                int index = random.nextInt(movelist.size());
                int m = movelist.get(index);
                movelist.remove(index);
                
                node.makeMove(turn, m);
                turn = turn_player;
                score = alphabeta(turn, node, alpha, beta,depth-1);
                turn = turn_computer;
                node.undoMove(turn, m);
               if (score < beta)
                    beta = score; //you have found a better move
                if (alpha >= beta)
                    return beta; //cutoff
            }
            return beta;
        }
    }
    
    double evaluate(int turn,Board board){
        List<String> mega = new ArrayList();
        mega.addAll(board.rows);
        mega.addAll(board.cols);
        mega.addAll(board.diagonals);

        double result = 0;

        if (turn == turn_player) {
            megasearch:
            for (String s : mega) {
                if (s.contains("1111")) {
                    result = 1000;
                    break megasearch;
                }
                else if(s.contains("2222")) {
                    result = -1000;
                    break megasearch;
                }
                if (s.contains("01110")) {
                    result += 150;
                }
                else if(s.contains("0111") || s.contains("1110")) {
                    result += 100;
                }
                else if(s.contains("1011") || s.contains ("1101")) {
                    result += 90;
                }
                else if (s.contains("1100") || s.contains("0011")) {
                    result += 10;
                }
                else if (s.contains("0110")) {
                    result += 10;
                }
                if (s.contains("2111") || s.contains("1112")) {
                    result -= 50;
                }
                else if (s.contains("1211") || s.contains("1121")) {
                    result -= 45;
                }
                if(s.contains("02220")) {
                    result -= 152;
                }
                else if(s.contains("2220") || s.contains("2220")) {
                    result -= 102;
                }
                else if(s.contains("2022") || s.contains ("2202")) {
                    result -= 92;
                }
                else if (s.contains("2200") || s.contains("0022")) {
                    result -= 10;
                }
                else if (s.contains("0220")) {
                    result -= 10;
                }
            }
        } else {
            megasearch:
            for (String s : mega) {
                if (s.contains("2222")) {
                    result = 1000;
                    break megasearch;
                }
                else if(s.contains("1111")) {
                    result = -1000;
                    break megasearch;
                }
                if (s.contains("02220")) {
                    result += 150;
                }
                else if(s.contains("0222") || s.contains("2220")) {
                    result += 100;
                }
                else if(s.contains("2022") || s.contains ("2202")) {
                    result += 90;
                }
                else if (s.contains("220") || s.contains("022")) {
                    result += 10;
                }
                if (s.contains("1222") || s.contains("2111")) {
                    result -= 50;
                }
                else if (s.contains("2122") || s.contains("2212")) {
                    result -= 45;
                }
                if (s.contains("01110")) {
                    result -= 152;
                }
                else if(s.contains("1110") || s.contains("1110")) {
                    result -= 102;
                }
                else if(s.contains("1011") || s.contains ("1101")) {
                    result -= 92;
                }
                else if (s.contains("110") || s.contains("011")) {
                    result -= 10;
                }
            }

        }
        
        //result += Math.random()/1000;
        return result;
    }
}
