import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javalib.impworld.*;
import javalib.worldimages.*;

class GamePiece {
	// in logical coordinates, with the origin
	// at the top-left corner of the screen
	int row;
	int col;
	// whether this GamePiece is connected to the
	// adjacent left, right, top, or bottom pieces
	boolean left;
	boolean right;
	boolean top;
	boolean bottom;
	// whether the power station is on this piece
	boolean powerStation;
	boolean isLit;
	int pieceSize;
	ArrayList<GamePiece> neighbors;
	

	// default constructor
	GamePiece(){
	  this.clearPiece();
	  this.neighbors = new ArrayList<GamePiece>();
	}
	
	// convenience constructor for making a gamepiece that can't exist in the game
	GamePiece(int row){
		this.row = row;
	}
	
	// constructor
	GamePiece(int row, int col, boolean left, 
			boolean right, boolean top, boolean bottom, boolean powerStation, int pieceSize){
		this.row = row;
		this.col = col;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.powerStation = powerStation;
		this.pieceSize = pieceSize;
	}


	// rotates a game piece clockwise
	void rotateGamePiece() {
		boolean tempLeft = this.left;
		boolean tempRight = this.right;
		boolean tempTop = this.top;
		boolean tempBottom = this.bottom;
		this.left = tempBottom;
		this.top = tempLeft;
		this.right = tempTop;
		this.bottom = tempRight;
	}

	
	WorldImage drawGamePiece(int distance, int pathDiameter) {
  	Color lineColor = Color.LIGHT_GRAY;
  	if(this.powerStation) {
  	  this.isLit = true;
  	}
  	if(this.isLit) {
  		lineColor = Color.yellow; //Utils.getColor(distance, pathDiameter);
  	}
  	
  	// Makes the base cell
  	WorldImage base = 
  	    new RectangleImage(this.pieceSize, this.pieceSize, OutlineMode.SOLID, Color.DARK_GRAY);
  	WorldImage outline = 
  	    new RectangleImage(this.pieceSize, this.pieceSize, OutlineMode.OUTLINE, Color.black);
  	WorldImage newBase = new OverlayImage(outline, base);
  	
  	//Component of the cell representing the wiring
  	WorldImage line = new RectangleImage(
  	    (this.pieceSize / 5), 
  	    (this.pieceSize / 2), 
  	    OutlineMode.SOLID, 
  	    lineColor).movePinhole(0, (this.pieceSize / 6));
  	
  	// Makes the cell with the appropriate branches
  	WorldImage finalCell = newBase;
  	WorldImage tempCell = newBase;
  	if(this.top) {
  	  tempCell = new OverlayImage(line, newBase);
  	}
  	finalCell = tempCell; //to avoid any issues with self referencing in variable assignment
  	
    if(this.left) {
      WorldImage bottom = new RotateImage(line, 270);
      tempCell = new OverlayImage(bottom, finalCell);
    }
    finalCell = tempCell;
  	
    if(this.bottom) {
      WorldImage bottom = new RotateImage(line, 180);
      tempCell = new OverlayImage(bottom, finalCell);
    }
    finalCell = tempCell;
    
    if(this.right) {
      WorldImage bottom = new RotateImage(line, 90);
      tempCell = new OverlayImage(bottom, finalCell);
    }
    finalCell = tempCell;
    
    //Image representing the powerstation
    WorldImage powerStar = new StarImage(
        (this.pieceSize * 0.5), 
        OutlineMode.SOLID, 
        Color.MAGENTA);
    
    //If the current cell contains the powerstation, draw it on
  	WorldImage finalPiece;
  	if(this.powerStation) {
  	  finalPiece = new OverlayImage(powerStar, finalCell);
  	}
  	else {
  	  finalPiece = finalCell;
  	}
  	return finalPiece;
  }

	// checks if this gamePiece is connected to the given one
	boolean isConnected(GamePiece that) {
		boolean connected = false;
		if(this.neighbors.contains(that)) {
			if(that.row > this.row) {
				if(this.bottom && that.top) {
					connected = true;
				}
			}
			if(that.row < this.row) {
				if(this.top && that.bottom) {
					connected = true;
				}
			}
			if(that.col > this.col) {
				if(this.right && that.left) {
					connected = true;
				}
			}
			if(that.col < this.col) {
				if(this.left && that.right) {
					connected = true;
				}
			}
		}
		return connected;
	}
	

	
	// used for testers 
	void clearPiece() {
	  this.top = false;
	  this.bottom = false;
	  this.right = false;
	  this.left = false;
	  this.powerStation = false;
    this.isLit = false;
	}
	
}
