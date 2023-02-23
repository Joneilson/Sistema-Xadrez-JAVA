package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
//import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
//import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board tabuleiro;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promocao;
	
	private List<Piece> pecasNoTabuleiro = new ArrayList<>();
	private List<Piece> pecasCapturadas = new ArrayList<>();
	
	public ChessMatch() {
		
		tabuleiro = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		comecoPartida();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck(){
		return check;
	}

	public boolean getCheckMate(){
		return checkMate;
	}

	public ChessPiece getEnPassantVulnerable(){
		return enPassantVulnerable;
	}

	public ChessPiece getPromocao(){
		return promocao;
	}
	
	
	public ChessPiece[][] getPecas(){
		ChessPiece[][] mat = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (ChessPiece) tabuleiro.peca(i, j);
			}
		}
		
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return tabuleiro.peca(position).movimentosPossiveis();
	}
	
	
	public ChessPiece executarMovimentoXadrez(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece pecaCapturada = makeMove(source, target);
		
		if(testeCheck(currentPlayer)){
			desfazerMovimento(source, target, pecaCapturada);
			throw new ChessException("Você não pode se colocar em check!");
		}

		ChessPiece pecaMovida = (ChessPiece)tabuleiro.peca(target);

		// Movimento Promoção
		promocao = null;
		if(pecaMovida instanceof Pawn){
			if(pecaMovida.getCor() == Color.WHITE && target.getLinha() == 0 || pecaMovida.getCor() == Color.BLACK && target.getLinha() == 7 ){
				promocao = (ChessPiece)tabuleiro.peca(target);
				promocao = subsPecaPromovida("Q");
			}
		}


		check = (testeCheck(oponente(currentPlayer))) ? true : false;
		
		if(testeCheckMate(oponente(currentPlayer))){
			checkMate = true;
		}
		else{
			nextTurn();
		}
		
		// Movimento enPassant

		if(pecaMovida instanceof Pawn && (target.getLinha() == source.getLinha() - 2 || (target.getLinha() == source.getLinha() + 2 ))){
			enPassantVulnerable = pecaMovida;
		}
		else{
			enPassantVulnerable = null;
		}

		return (ChessPiece)pecaCapturada;
		
	}
	
	private void validateSourcePosition(Position position) {
		if(!tabuleiro.thereIsAPiece(position)) {
			throw new ChessException("Não existe peça na posição de origem");
		}
		if(currentPlayer != ((ChessPiece)tabuleiro.peca(position)).getCor()) {
			throw new ChessException("A peça escolhida não é sua");
		}
		
		if(!tabuleiro.peca(position).existeMovimentoPossivel()) {
			throw new ChessException("Não existem movimentos possiveis para a peça escolhida.");
		}
		
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!tabuleiro.peca(source).movimentoPossivel(target)) {
			throw new ChessException("A peça escolhida não pode se mover para a casa de destino");
		}
	}

	public ChessPiece subsPecaPromovida(String type){
		if(promocao == null){
			throw new IllegalStateException("Não existe peça para ser promovida!");
		}
		if(!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")){
			throw new InvalidParameterException("Tipo invalido para promoção!");
		}

		Position pos = promocao.getChessPosition().toPosition();
		Piece p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);

		ChessPiece novaPeca = novaPeca(type, promocao.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);

		return novaPeca;
	}

	private ChessPiece novaPeca(String type, Color cor){
		if(type.equals("B")) return new Bishop(tabuleiro, cor);
		if(type.equals("C")) return new Knight(tabuleiro, cor);
		if(type.equals("Q")) return new Queen(tabuleiro, cor);
		return new Rook(tabuleiro, cor);
	}	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)tabuleiro.removePeca(source);
		p.incContMov();
		Piece pecaCapturada = tabuleiro.removePeca(target);
		tabuleiro.colocarPeca(p, target);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		//Roque Pequeno
		if(p instanceof King && target.getColuna() == source.getColuna() + 2){
			Position sourceT = new Position(source.getLinha(), source.getColuna() + 3);
			Position targetT = new Position(source.getLinha(), source.getColuna() + 1);
			ChessPiece rook = (ChessPiece)tabuleiro.removePeca(sourceT);
			tabuleiro.colocarPeca(rook, targetT);
			rook.incContMov();
		}

		//Roque Grande
		if(p instanceof King && target.getColuna() == source.getColuna() - 2){
			Position sourceT = new Position(source.getLinha(), source.getColuna() - 4);
			Position targetT = new Position(source.getLinha(), source.getColuna() - 1);
			ChessPiece rook = (ChessPiece)tabuleiro.removePeca(sourceT);
			tabuleiro.colocarPeca(rook, targetT);
			rook.incContMov();
		}

		// EnPassant

		if(p instanceof Pawn){
			if(source.getColuna() != target.getColuna() && pecaCapturada == null){
				Position pawnPosition;
				if(p.getCor() == Color.WHITE){
					pawnPosition = new Position(target.getLinha() + 1, target.getColuna());
				}
				else{
					pawnPosition = new Position(target.getLinha() - 1, target.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(pawnPosition);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);

			}
		}
		
		return pecaCapturada;
	}
	

	private void desfazerMovimento(Position source, Position target, Piece pecaCapturada){
		ChessPiece p = (ChessPiece)tabuleiro.removePeca(target);
		p.decContMov();
		tabuleiro.colocarPeca(p, source);

		if(pecaCapturada != null){
			tabuleiro.colocarPeca(pecaCapturada, target);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		//Roque Pequeno
		if(p instanceof King && target.getColuna() == source.getColuna() + 2){
			Position sourceT = new Position(source.getLinha(), source.getColuna() + 3);
			Position targetT = new Position(source.getLinha(), source.getColuna() + 1);
			ChessPiece rook = (ChessPiece)tabuleiro.removePeca(targetT);
			tabuleiro.colocarPeca(rook, sourceT);
			rook.decContMov();
		}

		//Roque Grande
		if(p instanceof King && target.getColuna() == source.getColuna() - 2){
			Position sourceT = new Position(source.getLinha(), source.getColuna() - 4);
			Position targetT = new Position(source.getLinha(), source.getColuna() - 1);
			ChessPiece rook = (ChessPiece)tabuleiro.removePeca(targetT);
			tabuleiro.colocarPeca(rook, sourceT);
			rook.decContMov();
		}

		// EnPassant

		if(p instanceof Pawn){
			if(source.getColuna() != target.getColuna() && pecaCapturada == enPassantVulnerable){
				ChessPiece pawn = (ChessPiece)tabuleiro.removePeca(target);
				Position pawnPosition;
				if(p.getCor() == Color.WHITE){
					pawnPosition = new Position(3, target.getColuna());
				}
				else{
					pawnPosition = new Position(4, target.getColuna());
				}

				tabuleiro.colocarPeca(pawn, pawnPosition);
			}
		}

	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color oponente(Color cor){
		return (cor == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}


	private ChessPiece king(Color cor){
		List<Piece> listPA = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
		for (Piece p: listPA){
			if(p instanceof King){
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("Não tem nenhum rei " + cor + " no tabuleiro" );

	}


	private boolean testeCheck(Color cor){
		Position posicaoRei = king(cor).getChessPosition().toPosition();
		List<Piece> pecasRivais = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Piece p : pecasRivais){
			boolean [] [] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
				return true;
			}
		}
		return false;
	}


	private boolean testeCheckMate(Color cor){

		if(!testeCheck(cor)){
			return false;
		}
		List<Piece> listCM = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
		for(Piece p : listCM){
			boolean [][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++){
				for(int j = 0; j < tabuleiro.getColunas(); j++){
					if(mat[i][j]){
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece pecaCapturada = makeMove(source, target);
						boolean testCheck = testeCheck(cor);
						desfazerMovimento(source, target, pecaCapturada);
						if(!testCheck){
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	
	private void colocarNovaPeca(char coluna, int linha, ChessPiece peca) {
		tabuleiro.colocarPeca(peca, new ChessPosition(coluna, linha).toPosition());
		pecasNoTabuleiro.add(peca);
		
	}
	
	
	private void comecoPartida() {
		
		colocarNovaPeca('a', 1, new Rook(tabuleiro, Color.WHITE));
		colocarNovaPeca('b', 1, new Knight(tabuleiro, Color.WHITE));
		colocarNovaPeca('c', 1, new Bishop(tabuleiro, Color.WHITE));
		colocarNovaPeca('d', 1, new Queen(tabuleiro, Color.WHITE));
		colocarNovaPeca('e', 1, new King(tabuleiro, Color.WHITE, this));
		colocarNovaPeca('f', 1, new Bishop(tabuleiro, Color.WHITE));
		colocarNovaPeca('g', 1, new Knight(tabuleiro, Color.WHITE));
		colocarNovaPeca('h', 1, new Rook(tabuleiro, Color.WHITE));
		
		colocarNovaPeca('a', 2, new Pawn(tabuleiro, Color.WHITE, this));
		colocarNovaPeca('b', 2, new Pawn(tabuleiro, Color.WHITE, this));
		colocarNovaPeca('c', 2, new Pawn(tabuleiro, Color.WHITE, this));
		colocarNovaPeca('d', 2, new Pawn(tabuleiro, Color.WHITE, this));
		colocarNovaPeca('e', 2, new Pawn(tabuleiro, Color.WHITE, this));
		colocarNovaPeca('f', 2, new Pawn(tabuleiro, Color.WHITE, this));
		colocarNovaPeca('g', 2, new Pawn(tabuleiro, Color.WHITE, this));
		colocarNovaPeca('h', 2, new Pawn(tabuleiro, Color.WHITE, this));

		
		
		colocarNovaPeca('a', 8, new Rook(tabuleiro, Color.BLACK));
		colocarNovaPeca('b', 8, new Knight(tabuleiro, Color.BLACK));
		colocarNovaPeca('d', 8, new Queen(tabuleiro, Color.BLACK));
		colocarNovaPeca('c', 8, new Bishop(tabuleiro, Color.BLACK));
		colocarNovaPeca('e', 8, new King(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('f', 8, new Bishop(tabuleiro, Color.BLACK));
		colocarNovaPeca('g', 8, new Knight(tabuleiro, Color.BLACK));
		colocarNovaPeca('h', 8, new Rook(tabuleiro, Color.BLACK));
		
		colocarNovaPeca('a', 7, new Pawn(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('b', 7, new Pawn(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('c', 7, new Pawn(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('d', 7, new Pawn(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('e', 7, new Pawn(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('f', 7, new Pawn(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('g', 7, new Pawn(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('h', 7, new Pawn(tabuleiro, Color.BLACK, this));
		
	
	}
}
