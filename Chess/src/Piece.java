
public class Piece {
   String type;
   int locationX, locationY, piecePosition;
   String color;
   int ID;
   boolean alive;
   
   public Piece(String type, int locationX, int locationY, String color, int piecePosition, int ID, boolean alive)
   {
	   this.type=type;
	   this.locationX=locationX;
	   this.locationY=locationY;
	   this.color=color;
	   this.piecePosition=piecePosition;
	   this.ID= ID;
	   this.alive=alive;
   }
   
   public Piece()
   {
   }
   
}
