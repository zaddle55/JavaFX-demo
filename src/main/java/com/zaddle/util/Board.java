package com.zaddle.util;
/**
* 定义棋盘逻辑，获胜判断等
 @ param name 棋盘名称
 @ param id 棋盘id
 @ param board 棋盘
 @ method initBoard 初始化棋盘
 @ method getBoard 获取棋盘
 @ method putChess 落子
 @ method getChess 获取棋子状态
 @ method judgeWin 判断胜负
 */

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
    // 判断是否有一方胜利, 1为黑棋胜利，2为白棋胜利，0为未分胜负
    public static int judgeWin(int x, int y){
        if (judgeRow(x, y) || judgeCol(x, y) || judgeLeftDiagonal(x, y) || judgeRightDiagonal(x, y)){
            if (board[x][y] == BLACK){
                return BLACK;
            }
            else if (board[x][y] == WHITE){
                return WHITE;
            }
        }
        return EMPTY;
    }
    /*
    *判断当前列内是否有五子相连
    @param x 当前横坐标
    @param y 当前纵坐标
    @return boolean
     */
    public static boolean judgeCol(int x, int y){
        int count = 1;
        int color = board[x][y];
        for (int i = x - 1; i >= 0; i--){
            if (board[i][y] == color){
                count++;
            }
            else{
                break;
            }
        }
        for (int i = x + 1; i < 15; i++){
            if (board[i][y] == color){
                count++;
            }
            else{
                break;
            }
        }
        if (count >= INROW){
            return true;
        }
        else{
            return false;
        }
    }
    /*
    *判断当前行内是否有五子相连
    * @param x 当前横坐标
    * @param y 当前纵坐标
    * @return boolean
     */
    public static boolean judgeRow(int x, int y) {
        int count = 1;
        int color = board[x][y];
        for (int i = y - 1; i >= 0; i--) {
            if (board[x][i] == color) {
                count++;
            } else {
                break;
            }
        }
        for (int i = y + 1; i < 15; i++) {
            if (board[x][i] == color) {
                count++;
            } else {
                break;
            }
        }
        if (count >= INROW) {
            return true;
        } else {
            return false;
        }
    }
        /*
        *判断当前左斜对角线内是否有五子相连
        * @param x 当前横坐标
        * @param y 当前纵坐标
        * @return boolean
         */
        public static  boolean judgeLeftDiagonal(int x, int y) {
            int count = 1;
            int color = board[x][y];
            for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
                if (board[i][j] == color) {
                    count++;
                } else {
                    break;
                }
            }
            for (int i = x + 1, j = y + 1; i < 15 && j < 15; i++, j++) {
                if (board[i][j] == color) {
                    count++;
                } else {
                    break;
                }
            }
            if (count >= INROW) {
                return true;
            } else {
                return false;
            }
        }
        /*
        *判断当前右斜对角线内是否有五子相连
        * @param x 当前横坐标
        * @param y 当前纵坐标
        * @return boolean
         */
    public static  boolean judgeRightDiagonal(int x, int y) {
        int count = 1;
        int color = board[x][y];
        for (int i = x - 1, j = y + 1; i >= 0 && j < 15; i--, j++) {
            if (board[i][j] == color) {
                count++;
            } else {
                break;
            }
        }
        for (int i = x + 1, j = y - 1; i < 15 && j >= 0; i++, j--) {
            if (board[i][j] == color) {
                count++;
            } else {
                break;
            }
        }
        if (count >= INROW) {
            return true;
        } else {
            return false;
        }
    }
}
