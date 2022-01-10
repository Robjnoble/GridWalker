package noble;

import java.util.Scanner;
import noble.game.*;

public class Main {
    private static Board board = new Board();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter commands (M, L, R)");

        String commandStr;
        while((commandStr = scanner.next()) != null) {
            Command command = Command.valueOf(commandStr);
            board.moveOnce(command);
            System.out.println("Moved to position: " + board.getCurrentPosition());
        }        
    } 
}