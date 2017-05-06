package cs401k142109a2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlphaBetaPlayer {
    public static final int turn_player = 1;
    public static final int turn_computer = 2;  
    
    int turn;
    
    double bestValue;
    
    List<Integer> possibleMoves;
    double[] values;

    public AlphaBetaPlayer() {
        possibleMoves = new ArrayList();
        turn = turn_computer;
    }
   
    int makeMove(Board board){
        
        int depth = 7;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        
        possibleMoves = board.getPossibleMoves();
        values = new double[possibleMoves.size()];
        
        int move = minimax(board, depth, turn, alpha, beta);
        
        
        return move;
    }
    
    int minimax(Board board, int depth, int turn, double alpha, double beta){
        int move = -1;
                
        Random rand = new Random(possibleMoves.size());
        double score = Double.NEGATIVE_INFINITY;
        
        int counter = 0;
        
        while(possibleMoves.size() > 0){
          
          int index = rand.nextInt(possibleMoves.size());
          int m = possibleMoves.get(index);
          possibleMoves.remove(index);
          board.makeMove(turn, m);
          values[m] = alphaBeta(board, changeTurn(turn), alpha, beta, depth);
            System.out.println("move: "+ m + " score: "+ values[m]);
          board.undoMove(turn, m);
        
        }
        double max = Double.NEGATIVE_INFINITY;
        for(int v = 0; v<values.length;v++){
            if(values[v] > max){
                max = values[v];
                move = v;
            }
        }
        
        
        return move;
    }
    
    double alphaBeta(Board board, int turn, double alpha, double beta, int depth){
        if(depth==0 || board.getPossibleMoves().size() == 0 || board.isGameOver() != -1){
            return evaluate(turn, board);
        }
        
        double bestValue = 0;
        if(turn == turn_computer){
            bestValue = alpha;
            for(int n: board.getPossibleMoves()){
                board.makeMove(turn, n);
                double v = alphaBeta(board, changeTurn(turn),alpha, beta, depth-1);
                board.undoMove(turn, n);
                bestValue = Math.max(bestValue,v);
                if (bestValue > alpha)
                    alpha = bestValue; //you have found a better move
                if (alpha >= beta)
                    return alpha;
            }
        }else{
            bestValue = beta;
            for(int n : board.getPossibleMoves()){
                board.makeMove(turn, n);
                double m = alphaBeta(board, changeTurn(turn),alpha,beta,depth-1);
                board.undoMove(turn, n);
                bestValue = Math.min(bestValue, m);
                if (bestValue < beta)
                    beta = bestValue; ///you have found a better move
                if (alpha >= beta)
                    return beta;
            }
        }
        return bestValue;
    }
    
/*    int makeMove(Board board){
      int move = -1;
      int depth = 7;
      
      possibleMoves = board.getPossibleMoves();
      Random rand = new Random();
      
      double a = Double.NEGATIVE_INFINITY; // alpha
      double b = Double.POSITIVE_INFINITY; // beta
      
      double bestValue = a;
      
      while(possibleMoves.size() > 0){
          
          int index = rand.nextInt(possibleMoves.size());
          int m = possibleMoves.get(index);
          possibleMoves.remove(index);
          
          double score = 0;
          
          board.makeMove(turn, m);
          score = alphabeta(turn, board, a, b, depth);
          board.undoMove(turn, m);
          
          System.out.println("move " + m + " Score: " + score);
          
          if (score > bestValue) {
            bestValue = score;
            move = m;
          }
      }
    
      return move;  
    }
    
    double alphabeta(int turn, Board node, double alpha, double beta, int depth){
      
        if(depth==0 || node.isGameOver() != -1 || node.getPossibleMoves().size() == 0){
            return evaluate(turn, node);
        }
        turn = changeTurn(turn);
     
        Random random = new Random();
        List<Integer> movelist = new ArrayList();
        if(turn == turn_computer){
            double score = alpha;
            movelist = node.getPossibleMoves();
            while(movelist.size()>0){
                int index = random.nextInt(movelist.size());
                int m = movelist.get(index);
                movelist.remove(index);
                node.makeMove(turn, m);
                double v = alphabeta(turn, node, alpha, beta,depth-1);
                score = Math.max(score, v);
                System.out.println("Score: " + score + " turn: " + turn + " \n" + node);
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
                double v = alphabeta(turn, node, alpha, beta,depth-1);
                score = Math.min(score, v);
                System.out.println("Score: " + score + " turn: " + turn + " \n" + node);
                node.undoMove(turn, m);
               if (score < beta)
                    beta = score; //you have found a better move
                if (alpha >= beta)
                    return beta; //cutoff
            }
            return beta;
        }
    }
  */
    
    double evaluate(int turn, Board board){
        List<String> mega = new ArrayList();
        mega.addAll(board.rows);
        mega.addAll(board.cols);
        mega.addAll(board.diagonals);
        
        turn = changeTurn(turn);
        double result = 0;

        if (turn == turn_player) {
            result = heuristicPlayer(mega);
        } else {
            result = heuristicComputer(mega);
        }
        
        //result += Math.random()/1000;
        return result;
    }
    
    public double heuristicPlayer(List<String> mega){
        double result = 0;
        for (String s : mega) {
                if (s.contains("1111")) {
                    result = 1000;
                    return result;
                }
                else if(s.contains("2222")) {
                    result = -1000;
                    return result;
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
        
        return result;

    }
    
    public double heuristicComputer(List<String> mega){
       double result = 0;
            for (String s : mega) {
                if (s.contains("2222")) {
                    result = 1000;
                    return result;
                }
                else if(s.contains("1111")) {
                    result = -1000;
                    return result;
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
            return result;
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
