package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color cor, ChessMatch chessMatch){
        super(board, cor);
        this.chessMatch = chessMatch;
    }

    @Override
	public String toString() {
		return "P";
	}


    @Override
    public boolean[][] movimentosPossiveis(){
        boolean [][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];

        Position p = new Position(0, 0);

        if(getCor() == Color.WHITE){
            p.setValues(position.getLinha() - 1 , position.getColuna());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValues(position.getLinha() - 2 , position.getColuna());
            Position p2 = new Position(position.getLinha() -1 , position.getColuna());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getContarMov() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValues(position.getLinha() - 1 , position.getColuna() - 1);
            if(getBoard().positionExists(p) && existePecaInimiga(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValues(position.getLinha() - 1 , position.getColuna() + 1);
            if(getBoard().positionExists(p) && existePecaInimiga(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Movimento enPassant branco

            if(position.getLinha() == 3){
                Position left = new Position(position.getLinha(), position.getColuna() - 1);
                if(getBoard().positionExists(left) && existePecaInimiga(left) && getBoard().peca(left) == chessMatch.getEnPassantVulnerable()){
                    mat[left.getLinha() - 1][left.getColuna()] = true;
                }
                Position right = new Position(position.getLinha(), position.getColuna() + 1);
                if(getBoard().positionExists(right) && existePecaInimiga(right) && getBoard().peca(right) == chessMatch.getEnPassantVulnerable()){
                    mat[right.getLinha() - 1][right.getColuna()] = true;
                }
            }




        }

        else{

            p.setValues(position.getLinha() + 1 , position.getColuna());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValues(position.getLinha() + 2 , position.getColuna());
            Position p2 = new Position(position.getLinha() + 1 , position.getColuna());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getContarMov() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValues(position.getLinha() + 1 , position.getColuna() - 1);
            if(getBoard().positionExists(p) && existePecaInimiga(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValues(position.getLinha() + 1 , position.getColuna() + 1);
            if(getBoard().positionExists(p) && existePecaInimiga(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Movimento enPassant preto

            if(position.getLinha() == 4){
                Position left = new Position(position.getLinha(), position.getColuna() - 1);
                if(getBoard().positionExists(left) && existePecaInimiga(left) && getBoard().peca(left) == chessMatch.getEnPassantVulnerable()){
                    mat[left.getLinha() + 1][left.getColuna()] = true;
                }
                Position right = new Position(position.getLinha(), position.getColuna() + 1);
                if(getBoard().positionExists(right) && existePecaInimiga(right) && getBoard().peca(right) == chessMatch.getEnPassantVulnerable()){
                    mat[right.getLinha() + 1][right.getColuna()] = true;
                }
            }


        }
        return mat;
    }
    
}
