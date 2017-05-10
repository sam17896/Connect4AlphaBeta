package cs401k142109a2;

import java.util.ArrayList;
import java.util.List;

public class AlphaBetaPlayer {
    int turn_player = 1;
    int turn_computer = 2;  
    
    int turn;
    int counter = 0;
    double bestValue;
    
    List<Integer> possibleMoves;
    double[] values;

    public AlphaBetaPlayer() {
        possibleMoves = new ArrayList();
        turn = turn_computer;
    }
   
    int makeMove(Board board){
        
        int depth = 4;
        
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        
        possibleMoves = board.getPossibleMoves();
        values = new double[6];
        
        int move = minimax(board, depth, turn, alpha, beta);
        
        
        return move;
    }
    
    int minimax(Board board, int depth, int turn, double alpha, double beta){
        int move = -1;
        
         // Computer is our maximizing agent , Player is minimizing agent
        
        double score = alpha;
        
        
        for (int m : possibleMoves){
          board.makeMove(turn, m);
          values[m]  = alphaBeta(board, turn, alpha, beta, depth);
          board.undoMove(turn, m);
          System.out.println("move: "+ m + " score: "+ values[m]);
          if(values[m] > score){
              score = values[m];
              move = m;
          }
        }
        
        return move;
    }
    
    double alphaBeta(Board board, int turn, double alpha, double beta, int depth){
        
        if(depth==0 || board.getPossibleMoves().isEmpty() || board.isGameOver() != -1){
            counter++;
            double value = evaluate(turn, board);
            return value;
        }
        
        double bestValue = 0;
        
        if(turn == turn_computer){
            bestValue = alpha;
            for(int n: board.getPossibleMoves()){
                board.makeMove(turn, n);
                bestValue = alphaBeta(board, changeTurn(turn),alpha, beta, depth-1);
                board.undoMove(turn, n);
                if (bestValue > alpha)
                    alpha = bestValue;
                if (alpha >= beta)
                    return alpha;
            }
            return alpha;
        }
        else{
            bestValue = beta;
            for(int n : board.getPossibleMoves()){
                board.makeMove(turn, n);
                bestValue = alphaBeta(board, changeTurn(turn),alpha,beta,depth-1);
                board.undoMove(turn, n);
                if (bestValue < beta)
                    beta = bestValue;
                if (alpha >= beta)
                    return beta;
            }
            return beta;
        }
    }
    
    double evaluate(int turn, Board board){
        
        List<String> all = new ArrayList();
        
        all.addAll(board.Srow);
        all.addAll(board.Scol);
        all.addAll(board.Sdiagonals);
    
        double result = 0;

        if (turn == turn_player) {
            result = heuristicPlayer(all);
        } else {
            result = heuristicComputer(all);
        }
        
        return result;
    }
    
    public double heuristicPlayer(List<String> all){
        double result = 0;
        for (String s : all) {
                if (s.contains("1111")) {
                    result = 100;
                    return result;
                }
                else if(s.contains("2222")) {
                    result = -100;
                    return result;
                }
                
                if (s.contains("01110")) {
                    result += 15;
                }
                else if(s.contains("0111") || s.contains("1110")) {
                    result += 10;
                }
                else if(s.contains("1011") || s.contains ("1101")) {
                    result += 9;
                }
                else if (s.contains("1100") || s.contains("0011")) {
                    result += 1;
                }
                else if (s.contains("0110")) {
                    result += 1;
                }
                if (s.contains("2111") || s.contains("1112")) {
                    result -= 5;
                }
                else if (s.contains("1211") || s.contains("1121")) {
                    result -= 4;
                }
                if(s.contains("02220")) {
                    result -= 20;
                }
                else if(s.contains("2220") || s.contains("0222")) {
                    result -= 12;
                }
                else if(s.contains("2022") || s.contains ("2202")) {
                    result -= 11;
                }
                else if (s.contains("2200") || s.contains("0022")) {
                    result -= 3;
                }
                else if (s.contains("0220")) {
                    result -= 3;
                }
            }
        
        return result;

    }
    
    public double heuristicComputer(List<String> mega){
       double result = 0;
            for (String s : mega) {
                if (s.contains("2222")) {
                    result = 100;
                    return result;
                }
                else if(s.contains("1111")) {
                    result = -100;
                    return result;
                }
                if (s.contains("02220")) {
                    result += 15;
                }
                else if(s.contains("0222") || s.contains("2220")) {
                    result += 10;
                }
                else if(s.contains("2022") || s.contains ("2202")) {
                    result += 9;
                }
                else if (s.contains("220") || s.contains("022")) {
                    result += 1;
                }
                if (s.contains("1222") || s.contains("2111")) {
                    result -= 5;
                }
                else if (s.contains("2122") || s.contains("2212")) {
                    result -= 4;
                }
                if (s.contains("01110")) {
                    result -= 20;
                }
                else if(s.contains("1110") || s.contains("0111")) {
                    result -= 12;
                }
                else if(s.contains("1011") || s.contains ("1101")) {
                    result -= 11;
                }
                else if (s.contains("110") || s.contains("011")) {
                    result -= 3;
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
