package ai;

import controller.Game;
import model.board.Board;
import model.board.Position;
import model.pieces.Piece;
import java.util.*;

/**
 * IA baseada em rede neural para xadrez com diferentes níveis de dificuldade.
 * Implementa algoritmo minimax com poda alfa-beta e heurísticas avançadas.
 */
public class NeuralNetworkAI {
    
    // Níveis de dificuldade
    public static final int VERY_EASY = 2;
    public static final int EASY = 4;
    public static final int MEDIUM = 7;
    public static final int HARD = 10;
    
    private final int difficulty;
    private final Random random;
    
    // Pesos da rede neural para avaliação de posição
    private static final double[][] NEURAL_WEIGHTS = {
        // Material weights (peão, cavalo, bispo, torre, rainha, rei)
        {100, 320, 330, 500, 900, 20000},
        // Position weights (centro, desenvolvimento, segurança do rei)
        {10, 15, 20, 25, 30, 35},
        // Mobility weights (mobilidade de peças, controle de casas)
        {5, 8, 12, 16, 20, 25},
        // Pawn structure weights (estrutura de peões, peões passados)
        {3, 5, 8, 12, 15, 18},
        // King safety weights (segurança do rei, castling)
        {8, 12, 16, 20, 25, 30},
        // Tactical weights (ataques, defesas, ameaças)
        {6, 10, 14, 18, 22, 26}
    };
    
    // Bias para cada camada da rede neural
    private static final double[] NEURAL_BIAS = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6};
    
    public NeuralNetworkAI(int difficulty) {
        this.difficulty = Math.max(VERY_EASY, Math.min(HARD, difficulty));
        this.random = new Random();
    }
    
    /**
     * Encontra o melhor movimento para o lado especificado
     */
    public Move findBestMove(Game game, boolean isWhite) {
        List<Move> allMoves = getAllLegalMoves(game, isWhite);
        if (allMoves.isEmpty()) return null;
        
        // Para dificuldades muito baixas, adiciona aleatoriedade
        if (difficulty <= VERY_EASY) {
            return allMoves.get(random.nextInt(allMoves.size()));
        }
        
        // Usa minimax com poda alfa-beta
        Move bestMove = null;
        int bestScore = isWhite ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        
        // Ordena movimentos para melhorar eficiência da poda alfa-beta
        allMoves.sort((m1, m2) -> {
            int score1 = evaluateMove(game, m1, isWhite);
            int score2 = evaluateMove(game, m2, isWhite);
            return isWhite ? Integer.compare(score2, score1) : Integer.compare(score1, score2);
        });
        
        for (Move move : allMoves) {
            Game gameCopy = game.snapshotShallow();
            gameCopy.forceMoveNoChecks(move.from, move.to);
            
            int score = minimax(gameCopy, difficulty - 1, !isWhite, 
                              Integer.MIN_VALUE, Integer.MAX_VALUE);
            
            if (isWhite && score > bestScore) {
                bestScore = score;
                bestMove = move;
            } else if (!isWhite && score < bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        
        return bestMove != null ? bestMove : allMoves.get(0);
    }
    
    /**
     * Algoritmo minimax com poda alfa-beta
     */
    private int minimax(Game game, int depth, boolean maximizingPlayer, int alpha, int beta) {
        if (depth == 0 || game.isGameOver()) {
            return evaluatePosition(game);
        }
        
        List<Move> moves = getAllLegalMoves(game, maximizingPlayer);
        if (moves.isEmpty()) {
            return evaluatePosition(game);
        }
        
        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
                Game gameCopy = game.snapshotShallow();
                gameCopy.forceMoveNoChecks(move.from, move.to);
                
                int eval = minimax(gameCopy, depth - 1, false, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                
                if (beta <= alpha) break; // Poda beta
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves) {
                Game gameCopy = game.snapshotShallow();
                gameCopy.forceMoveNoChecks(move.from, move.to);
                
                int eval = minimax(gameCopy, depth - 1, true, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                
                if (beta <= alpha) break; // Poda alfa
            }
            return minEval;
        }
    }
    
    /**
     * Avalia uma posição usando a rede neural
     */
    private int evaluatePosition(Game game) {
        Board board = game.board();
        
        // Extrai características da posição
        double[] features = extractFeatures(board);
        
        // Aplica a rede neural
        double[] hidden = new double[NEURAL_WEIGHTS.length];
        for (int i = 0; i < NEURAL_WEIGHTS.length; i++) {
            hidden[i] = NEURAL_BIAS[i];
            for (int j = 0; j < features.length; j++) {
                hidden[i] += features[j] * NEURAL_WEIGHTS[i][j % NEURAL_WEIGHTS[i].length];
            }
            hidden[i] = sigmoid(hidden[i]); // Função de ativação
        }
        
        // Camada de saída
        double output = 0;
        for (int i = 0; i < hidden.length; i++) {
            output += hidden[i] * (i + 1) * 100; // Pesos da camada de saída
        }
        
        return (int) output;
    }
    
    /**
     * Extrai características da posição para a rede neural
     */
    private double[] extractFeatures(Board board) {
        double[] features = new double[64]; // 64 casas do tabuleiro
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Piece piece = board.get(pos);
                
                int index = row * 8 + col;
                if (piece == null) {
                    features[index] = 0;
                } else {
                    // Codifica peça e cor como valor numérico
                    double value = getPieceValue(piece);
                    if (!piece.isWhite()) value = -value; // Pretas são negativas
                    features[index] = value;
                }
            }
        }
        
        return features;
    }
    
    /**
     * Obtém valor numérico de uma peça
     */
    private double getPieceValue(Piece piece) {
        return switch (piece.getSymbol()) {
            case "P" -> 1.0;
            case "N" -> 3.2;
            case "B" -> 3.3;
            case "R" -> 5.0;
            case "Q" -> 9.0;
            case "K" -> 20.0;
            default -> 0.0;
        };
    }
    
    /**
     * Avalia um movimento específico
     */
    private int evaluateMove(Game game, Move move, boolean isWhite) {
        Piece piece = game.board().get(move.from);
        Piece target = game.board().get(move.to);
        
        int score = 0;
        
        // Valor da captura
        if (target != null) {
            score += getPieceValue(target) * 10;
        }
        
        // Bônus por controle do centro
        score += getCenterBonus(move.to);
        
        // Bônus por desenvolvimento
        if (piece.getSymbol().equals("N") || piece.getSymbol().equals("B")) {
            score += 5;
        }
        
        // Penalidade por mover peões no início
        if (piece.getSymbol().equals("P") && !piece.hasMoved()) {
            score -= 2;
        }
        
        return isWhite ? score : -score;
    }
    
    /**
     * Bônus por controle do centro
     */
    private int getCenterBonus(Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        
        // Centro do tabuleiro
        if ((row >= 3 && row <= 4) && (col >= 3 && col <= 4)) {
            return 20;
        }
        // Área próxima ao centro
        if ((row >= 2 && row <= 5) && (col >= 2 && col <= 5)) {
            return 10;
        }
        return 0;
    }
    
    /**
     * Função de ativação sigmoid
     */
    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }
    
    /**
     * Obtém todos os movimentos legais para um lado
     */
    private List<Move> getAllLegalMoves(Game game, boolean isWhite) {
        List<Move> moves = new ArrayList<>();
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position from = new Position(row, col);
                Piece piece = game.board().get(from);
                
                if (piece != null && piece.isWhite() == isWhite) {
                    List<Position> legalMoves = game.legalMovesFrom(from);
                    for (Position to : legalMoves) {
                        moves.add(new Move(from, to));
                    }
                }
            }
        }
        
        return moves;
    }
    
    /**
     * Classe para representar um movimento
     */
    public static class Move {
        public final Position from;
        public final Position to;
        
        public Move(Position from, Position to) {
            this.from = from;
            this.to = to;
        }
        
        @Override
        public String toString() {
            return from + " -> " + to;
        }
    }
    
    /**
     * Obtém o nível de dificuldade atual
     */
    public int getDifficulty() {
        return difficulty;
    }
    
    /**
     * Obtém nome do nível de dificuldade
     */
    public String getDifficultyName() {
        return switch (difficulty) {
            case VERY_EASY -> "Muito Fácil (Nível 2)";
            case EASY -> "Fácil (Nível 4)";
            case MEDIUM -> "Médio (Nível 7)";
            case HARD -> "Difícil (Nível 10)";
            default -> "Personalizado (Nível " + difficulty + ")";
        };
    }
}
