package org.example.javafx;

import  java.util.Scanner;
import com.zaddle.util.Board;
public class Main {
    public static void main(String[] args) {
        int[] color = {1, 2};
        Board.initBoard();
        Board.getBoard();
        Scanner scanner = new Scanner(System.in);
        while (true){
            for (int i = 0; i < 2; i++){
                System.out.println("请输入" + (color[i] == 1 ? "黑棋" : "白棋") + "的落子坐标：");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                if (Board.putChess(x, y, color[i] == 1 ? "BLACK" : "WHITE")){
                    Board.getBoard();
                    if (Board.judgeWin(x, y) != 0){
                        System.out.println((color[i] == 1 ? "黑棋" : "白棋") + "获胜！");
                        return;
                    }
                }
                else{
                    System.out.println("落子失败，请重新输入！");
                    i--;
                }
            }
        }
    }
}


