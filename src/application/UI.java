package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// Codigo obtido no: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() { 
	 System.out.print("\033[H\033[2J"); 
	 System.out.flush();
	}
	
	public static ChessPosition readChessPosition(Scanner sc) {
		
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new ChessPosition(coluna, linha);
		}
		catch(RuntimeException e){
			throw new InputMismatchException("Erro ao ler ChessPosition. Valores validos são de a1 até h8 ");
			
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturadas) {
		printBoard(chessMatch.getPecas());
		System.out.println();
		printPecasCapturadas(capturadas);
		System.out.println("Turn: " + chessMatch.getTurn());
		if(!chessMatch.getCheckMate()){
			System.out.println("Aguardado jogador: " + chessMatch.getCurrentPlayer());
			if(chessMatch.getCheck()){
				System.out.println("CHEQUE!");
			}
		}
		else{
			System.out.println("CHECKMATE!");
			System.out.println("Vencedor: " + chessMatch.getCurrentPlayer());
		}
	}
	
	
	public static void printBoard(ChessPiece[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printBoard(ChessPiece[][] pecas, boolean [][] possibleMoves) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPeca(ChessPiece peca, boolean background) {
		if (background) {
			System.out.print(ANSI_GREEN_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} 
		else {
			if (peca.getCor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_RED + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	
	private static void printPecasCapturadas (List<ChessPiece> capturadas) {
		
		List<ChessPiece> white = capturadas.stream().filter(x -> x.getCor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> black = capturadas.stream().filter(x -> x.getCor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("Peças capturadas:");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.println(ANSI_RESET);
		System.out.print("Pretass: ");
		System.out.print(ANSI_RED);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.println(ANSI_RESET);	
	}

}
