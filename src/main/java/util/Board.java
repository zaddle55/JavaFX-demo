package util;

public class Board {
    // 通过name和id来创建一个棋盘
    public String name;
    public int id;
    public Board(String name, int id){
        this.name = name;
        this.id = id;
    }
    // 设置黑棋为1，白棋为2，空为0
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    public  static final int EMPTY = 0;
    public  static final int INROW = 5;
    // 设置棋盘
    public static  int[][] board = new int[15][15];
    // 初始化棋盘
    public static void initBoard(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                board[i][j] = 0;
            }
        }
    }
    public static void getBoard(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    // 判断并进行落子
    public static boolean putChess(int x, int y, String color){
        if (x >=0 && x < board[0].length && y >= 0 && y < board.length && board[x][y] == EMPTY){
            if (color.equals("BLACK")){
                board[x][y] = BLACK;
            }
            else if (color.equals("WHITE")){
                board[x][y] = WHITE;
            }
            return true;
        }
        else{
            return false;
        }
    }
    public static String getChess(int x, int y) throws Exception{
        if (board[x][y] == BLACK){
            return "BLACK";
        }
        else if (board[x][y] == WHITE){
            return "WHITE";
        }
        else{
            return "EMPTY";
        }
    }
}
