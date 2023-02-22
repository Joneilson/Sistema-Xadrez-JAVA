package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		ChessMatch chessMatch = new ChessMatch();
		
		List<ChessPiece> capturadas = new ArrayList<>();
		
		while(!chessMatch.getCheckMate()) {
			try {
			
				UI.clearScreen();
				UI.printMatch(chessMatch, capturadas);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean [][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPecas(), possibleMoves);
				
				System.out.println();
				System.out.print("Target : ");
				ChessPosition target = UI.readChessPosition(sc);
				System.out.println();
				
				ChessPiece pecaCapturada = chessMatch.executarMovimentoXadrez(source, target);
				
				if(pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
				
			
			}
			
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, capturadas);
	} 

}
