package com.zaddle.util;
/**
* 定义棋盘逻辑，获胜判断等
 @param name 棋盘名称
 @param id 棋盘id
 @param board 棋盘
 @method initBoard 初始化棋盘
 @method getBoard 获取棋盘
 @method putChess 落子
 @method getChess 获取棋子状态
 @method judgeWin 判断胜负
 */
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

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
    public static final int EMPTY = 0;
    public static final int INROW = 5;

    // 设置棋盘
    private int[][] board = new int[15][15];
    // 通过一个二维数组来创建一个棋盘
    public Board(int[][] initBoard){
        copyBoard(initBoard, board);
    }

    // 初始化状态队列
    private Deque status = new Deque();
    // 状态加入队尾
    public void pushStatus(int[][] board){
        int [][] copy = new int[15][15];
        copyBoard(board, copy);
        status.pushRear(copy);
    }
    // 状态出队尾
    public int[][] popStatus(){
        return status.popRear();
    }

    // 获取棋盘
    public int[][] getBoard(){
        return board;
    }
    // 判断并进行落子
    public boolean putChess(int x, int y, int color){
        if (x >=0 && x < board[0].length && y >= 0 && y < board.length && board[x][y] == EMPTY){
            if (color == 1){
                board[x][y] = BLACK;
            }
            else if (color == 2){
                board[x][y] = WHITE;
            }
            return true;
        }
        else{
            return false;
        }
    }

    // 移除指定位置的棋子
    public void removeChess(int x, int y){
        board[x][y] = EMPTY;
    }

    // 获取指定位置的棋子
    public String getChess(int x, int y) throws Exception{
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

    // 判断棋盘是否已满
    public boolean isFull(){
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if (board[i][j] == EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

    // 根据棋面判断是否有一方胜利
    public boolean isWin(int color){
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if (board[i][j] == color){
                    if (this.judgeWin(i, j) != 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // 根据最后一步判断是否有一方胜利, 1为黑棋胜利，2为白棋胜利，0为未分胜负
    public int judgeWin(int x, int y){
        if (this.judgeRow(x, y) || this.judgeCol(x, y) || this.judgeLeftDiagonal(x, y) || this.judgeRightDiagonal(x, y)){
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
    public boolean judgeCol(int x, int y){
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
    public boolean judgeRow(int x, int y) {
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
    public boolean judgeLeftDiagonal(int x, int y) {
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
    public boolean judgeRightDiagonal(int x, int y) {
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

    // 继承LinkedList类，实现双端队列，储存棋盘状态
    class Deque extends LinkedList<int[][]>{
        public void pushRear(int[][] board) {
            addLast(board);
        }
        public int[][] popRear() {
            return removeLast();
        }
        public void pushFront(int[][] board) {
            addFirst(board);
        }
        public int[][] popFront() {
            return removeFirst();
        }
    }

    // 复制棋盘防止引用传递
    public static void copyBoard(int[][] srcboard, int[][] dstBoard) {
        for (int i = 0; i < srcboard.length; i++) {
            for (int j = 0; j < srcboard[i].length; j++) {
                dstBoard[i][j] = srcboard[i][j];
            }
        }
    }

    // 得到当前棋盘的所有合法走法
    public static List<int[]> getValidMoves(int[][] board){
        List<int[]> resList = new ArrayList<int[]>();
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if (board[i][j] == EMPTY && isAround(board, i, j)){
                    resList.add(new int[]{i, j});
                }
            }
        }
        return resList;
    }

    // 搜索指定点位距离为5范围内是否有棋子
    public static boolean isAround(int[][] board, int x, int y){
        for (int i = x - 3; i <= x + 3; i++){
            for (int j = y - 3; j <= y + 3; j++){
                if (i >= 0 && i < 15 && j >= 0 && j < 15 && board[i][j] != EMPTY){
                    return true;
                }
            }
        }
        return false;
    }
}
