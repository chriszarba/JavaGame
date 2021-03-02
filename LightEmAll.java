import java.util.ArrayList;
import javalib.impworld.*;
import javalib.worldimages.Posn;

class LightEmAll extends World {
  // a list of columns of GamePieces,
  // i.e., represents the board in column-major order
  ArrayList<ArrayList<GamePiece>> board;
  // a list of all nodes
  ArrayList<GamePiece> nodes;
  // a list of edges of the minimum spanning tree
  ArrayList<Edge> mst;
  // the width and height of the board
  int width;
  int height;
  // the current location of the power station,
  // as well as its effective radius
  int powerRow;
  int powerCol;
  int radius;
  int pathDiameter;
  int pieceSize = 50;
  int rows;
  int columns;


  LightEmAll(int rows, int columns){
    this.rows = rows;
    this.columns = columns;
    this.width = this.columns * this.pieceSize;
    this.height = this.rows * this.pieceSize;
    this.board = new ArrayList<ArrayList<GamePiece>>();
    this.makeBoard();
    this.initializeNodes();
    this.initializeAllNeighbors();
    this.pathDiameter = this.height/2;
  }

  // placeholder for when we actually write this function
  public WorldScene makeScene() {
    return this.drawBoard(new WorldScene(this.width, this.height));
  }

  // this makes an empty board of the specified size
  // and initializes the pieces with row and column
  void makeBoard(){
    for(int i = 0; i < this.rows; i++) {
      board.add(new ArrayList<GamePiece>());
      for(int j = 0; j < this.columns; j++) {
        GamePiece tempPiece = new GamePiece();
        tempPiece.pieceSize = this.pieceSize;
        tempPiece.col = j;
        tempPiece.row = i;
        board.get(i).add(tempPiece);
      }
    }
  }


  // add all pieces in this.board to this.nodes
  void initializeNodes(){
    this.nodes = new ArrayList<GamePiece>();
    for(ArrayList<GamePiece> i : this.board) {
      for(GamePiece j : i) {
        this.nodes.add(j);
      }
    }
  }

  // gets the gamepiece that was clicked and rotates it
  // is it really this simple
  // want to add more tests for this to make sure
  public void onMouseClicked(Posn p) {
    int x = p.x;
    int y = p.y;
    if((x > 0) && (x < this.width) && (y > 0) && (y < this.height)) {
      int j = y/this.pieceSize;
      int i = x/this.pieceSize;
      this.board.get(j).get(i).rotateGamePiece();
    }
  }

  // this is inefficient, would like a better way
  public void onTick() {
    this.lightWires();
  }

  // handle lighting the wires
  void lightWires() {
    for(GamePiece i : this.nodes) {
      i.isLit = (this.pathDiameter/2 + 1 > this.getDistance(i)) 
          && this.dfsPath(i, this.board.get(this.powerRow).get(this.powerCol));
    }
  }

  //moves the powerstation when the player hits a directional key
  public void onKeyEvent(String k) {
    moveStation(k, this.board.get(this.powerRow).get(this.powerCol));
  }

  // a helper for onKeyEvent that alters the cell that holds the powerstation
  void moveStation(String direction, GamePiece current) {
    if (direction.equals("up") && (this.powerRow > 0)) {
      GamePiece up = this.board.get(this.powerRow - 1).get(this.powerCol);

      if(current.top && up.bottom) {
        GamePiece next = this.board.get(this.powerRow - 1).get(this.powerCol);
        next.powerStation = true;
        current.powerStation = false;
        this.powerRow = this.powerRow - 1;
      }
    }
    else if (direction.equals("down") && (this.powerRow < this.rows - 1)) {
      GamePiece down  = this.board.get(this.powerRow + 1).get(this.powerCol + 0);

      if (current.bottom && down.top) {
        GamePiece next = this.board.get(this.powerRow + 1).get(this.powerCol);
        next.powerStation = true;
        current.powerStation = false;
        this.powerRow = this.powerRow + 1;
      }

    }
    else if (direction.equals("left") && (this.powerCol > 0)) {
      GamePiece left  = this.board.get(this.powerRow - 0).get(this.powerCol - 1);

      if (current.left && left.right) {
        GamePiece next = this.board.get(this.powerRow).get(this.powerCol - 1);
        next.powerStation = true;
        current.powerStation = false;
        this.powerCol = this.powerCol - 1;
      }

    }
    else if (direction.equals("right") && (this.powerCol < this.columns - 1)) {
      GamePiece right = this.board.get(this.powerRow + 0).get(this.powerCol + 1);

      if (current.right && right.left) {
        GamePiece next = this.board.get(this.powerRow).get(this.powerCol + 1);
        next.powerStation = true;
        current.powerStation = false;
        this.powerCol = this.powerCol + 1;
      }

    }
  }

  void initializeNeighbors(GamePiece g) {
    if(g.row > 0) {
      this.board.get(g.row).get(g.col).neighbors.add(this.board.get(g.row - 1).get(g.col));
    }
    if(g.row < this.rows - 1) { 
      this.board.get(g.row).get(g.col).neighbors.add(this.board.get(g.row + 1).get(g.col));
    }
    if(g.col > 0) {
      this.board.get(g.row).get(g.col).neighbors.add(this.board.get(g.row).get(g.col - 1));
    }
    if(g.col < this.columns - 1) {
      this.board.get(g.row).get(g.col).neighbors.add(this.board.get(g.row).get(g.col + 1));
    }
  }

  void initializeAllNeighbors() {
    for(GamePiece i: this.nodes) {
      this.initializeNeighbors(i);
    }
  }

  int getDistance(GamePiece g) {
    return (Math.abs(this.powerRow - g.row) +  Math.abs(this.powerCol - g.col)) * this.pieceSize;
  }

  WorldScene drawBoard(WorldScene w) {
    for(GamePiece i : this.nodes) {
      w.placeImageXY(i.drawGamePiece(this.getDistance(i), this.pathDiameter),
          this.pieceSize * i.col + this.pieceSize/2, this.pieceSize * i.row + this.pieceSize/2);
    }
    return w;
  }

  //helper for search returning if two GamePieces are connected
  boolean searchHelpPath(GamePiece from, GamePiece to, ICollection<GamePiece> worklist) {
    Deque<GamePiece> alreadySeen = new Deque<GamePiece>();

    // Initialize the worklist with the from GamePiece
    worklist.add(from);
    // As long as the worklist isn't empty...
    while (!worklist.isEmpty()) {
      GamePiece next = worklist.remove();
      if (next.equals(to)) {
        return true; // Success!
      }
      else if (alreadySeen.contains(next)) {
        // do nothing: we've already seen this one
      }
      else {
        // add all the neighbors of next to the worklist for further processing
        for (GamePiece g : next.neighbors) {
          if(next.isConnected(g) && g.isLit) {
            worklist.add(g);
          }
        }
        // add next to alreadySeen, since we're done with it
        alreadySeen.addAtHead(next);
      }
    }
    // We haven't found the to vertex, and there are no more to try
    return false;
  }

  // dfs returns if two GamePieces are connected
  boolean dfsPath(GamePiece from, GamePiece to) {
    return searchHelpPath(from, to, new Stack<GamePiece>());
  }
  
  
  
  

}