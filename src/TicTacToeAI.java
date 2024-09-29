import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToeAI extends JFrame {

    private static final int SIZE = 3;
    private JButton[][] buttons = new JButton[SIZE][SIZE];
    private char playerSymbol;
    private char aiSymbol;
    private char currentPlayer;
    private static final char EMPTY = ' ';
    private int[][] winningCombo = null;

    public TicTacToeAI(char playerSymbol){
       
        this.playerSymbol = playerSymbol;
        this.aiSymbol = (playerSymbol == 'X') ? 'O' : 'X';
        this.currentPlayer = 'X'; // X always starts first

        setTitle("Tic-Tac-Toe");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE,SIZE));

        initializeBoard();

        if(aiSymbol == 'X'){
            aiMove(); // ai makes the first move
        }

        setVisible(true);
    }

    private void initializeBoard(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Ariel", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(new ButtonClickListener(i,j));
                add(buttons[i][j]);
            }
        }
    }

   
    private class ButtonClickListener implements ActionListener{
        private int row, col;

        public ButtonClickListener(int row, int col){
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            if(buttons[row][col].getText().equals("")){
                setButtonSymbol(row,col,currentPlayer);

                if(checkWin()){
                    winningCombo = getWinningCombo();
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!" );
                    resetBoard();
                }else if(isBoardFull()){
                    JOptionPane.showMessageDialog(null,"It's a tie!");
                    resetBoard();
                }else{
                    currentPlayer = (currentPlayer == playerSymbol) ?  aiSymbol : playerSymbol;
                    if (currentPlayer == aiSymbol){
                        aiMove();
                    }
                }

            }
        }
    }

    private void setButtonSymbol(int row, int col, char symbol){
        buttons[row][col].setText(String.valueOf(symbol));

        if(symbol == 'X'){
            buttons[row][col].setForeground(Color.BLUE);
        }else if(symbol == 'O'){
            buttons[row][col].setForeground(Color.RED);
        }
    }

    private int minimax(char[][]board, boolean isMaximizing){
        if(checkBoardWin(board, aiSymbol)) return 1;
        if(checkBoardWin(board, playerSymbol)) return -1;
        if (isBoardFull()) return 0;

        if(isMaximizing){
            int bestScore = Integer.MIN_VALUE;
            for(int i = 0;i < SIZE; i++){
                for(int j = 0; j < SIZE; j++){
                    if(board[i][j] == EMPTY){
                        board[i][j] = aiSymbol;
                        int score = minimax(board,false);
                        board[i][j] = EMPTY;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
       }
       else{
          int bestScore = Integer.MAX_VALUE;
          for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(board[i][j] == EMPTY){
                    board[i][j] = playerSymbol;
                    int score = minimax(board, true);
                    board[i][j] = EMPTY;
                    bestScore = Math.min(score, bestScore);
                }
            }
          }
          
          return bestScore;
        }
    }

    private void aiMove(){
        
        ArrayList<int[]> availableSpots = new ArrayList<>();

        // collect available spots
        for(int i = 0;i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(buttons[i][j].getText().equals("")){
                    availableSpots.add(new int[] {i,j});
                }
            }
        }

        // AI is 'X' and it is the first move, random spot selected
        if(currentPlayer == 'X' && availableSpots.size() == SIZE * SIZE){
            Random rand = new Random();
            int[] move = availableSpots.get(rand.nextInt(availableSpots.size()));
            int row = move[0];
            int col = move[1];

            setButtonSymbol(row,col,aiSymbol);
        }
        else{
            int bestScore = Integer.MIN_VALUE;
            int moveRow = -1, moveCol = -1;
            
            char[][] board = getBoardState();

            for(int i = 0; i < SIZE; i++){
                for(int j = 0; j < SIZE; j++){
                    if(board[i][j] == EMPTY){
                        board[i][j] = aiSymbol;
                        int score = minimax(board,false);
                        board[i][j] = EMPTY;

                        if(score > bestScore){
                            bestScore = score;
                            moveRow = i;
                            moveCol = j;
                        }
                    }
                }
            }
            
            if(moveRow != -1 && moveCol != -1){
                setButtonSymbol(moveRow,moveCol,aiSymbol);
            }
        }

        if(checkWin()){
            winningCombo = getWinningCombo();
            JOptionPane.showMessageDialog(null,"Player " + aiSymbol + " (AI) wins!");
            resetBoard();
        }else if (isBoardFull()){
            JOptionPane.showMessageDialog(null,"It's a tie!");
            resetBoard();
        }else{
            currentPlayer = playerSymbol;
        }
    }

    private char[][] getBoardState(){
        char[][] board = new char[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                String text = buttons[i][j].getText();
                if(text.equals("")){
                    board[i][j] = EMPTY;
                }else{
                    board[i][j] = text.charAt(0);
                }
            }
        }
        return board;
    }

    private boolean checkWin(){
        for(int i = 0; i < SIZE; i++){
            if(buttons[i][0].getText().equals(buttons[i][1].getText()) &&
               buttons[i][1].getText().equals(buttons[i][2].getText()) &&
               !buttons[i][0].getText().equals("")){
                winningCombo = new int[][] {{i,0},{i,2}};
               return true;
            }
            if(buttons[0][i].getText().equals(buttons[1][i].getText()) &&
               buttons[1][i].getText().equals(buttons[2][i].getText()) &&
               !buttons[0][i].getText().equals("")){
                winningCombo = new int[][] {{0,i},{2,i}};
               return true;
            }
        }
        if(buttons[0][0].getText().equals(buttons[1][1].getText()) &&
               buttons[1][1].getText().equals(buttons[2][2].getText()) &&
               !buttons[0][0].getText().equals("")){
                winningCombo = new int[][] {{0,0},{2,2}};
               return true;
        }
        if(buttons[0][2].getText().equals(buttons[1][1].getText()) &&
               buttons[1][1].getText().equals(buttons[2][0].getText()) &&
               !buttons[0][2].getText().equals("")){
                winningCombo = new int[][] {{0,2},{2,0}};
               return true;
        }

        return false; // No winner
    }

    private int[][] getWinningCombo(){
        return winningCombo;
    }

    private boolean checkBoardWin(char[][] board, char symbol){

        for(int i = 0; i < SIZE; i++){
            if(board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol){
                return true;
            }
            if(board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol){
                return true;
            }
        }
        if(board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol){
            return true;
        }
        if(board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol){
            return true;
        }

        return false;
    }


    private boolean isBoardFull(){
        for(int i = 0;i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(buttons[i][j].getText().equals("")){
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard(){
        winningCombo = null;
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.WHITE);
            }
        }
        // set currentPlayer to 'X', since 'X' always goes first
        currentPlayer = 'X';

        // If the player is 'O', the AI should make the first move as 'X'
        if(playerSymbol == 'O'){
            aiMove(); // AI automatically makes the first move
        }
    }

    public static void main(String[] args) throws Exception {
            new TicTacToeAI('X');
    }
}
