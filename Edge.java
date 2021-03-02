class Edge {
  GamePiece fromNode;
  GamePiece toNode;
  int weight;
  
  Edge(GamePiece from, GamePiece to){
  	this.fromNode = from;
  	this.toNode = to;
  }
  
  Edge(GamePiece from, GamePiece to, int weight){
  	this.fromNode = from;
  	this.toNode = to;
  	this.weight = weight;
  }
  
}