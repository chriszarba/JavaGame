import java.awt.Color;
import java.util.*;
//import java.util.Arrays;
import javalib.impworld.*;
import javalib.worldimages.*;
import tester.*;

class ExamplesLightEmAll { 
  LightEmAll l1 = new LightEmAll(3, 3);
  LightEmAll l3 = new LightEmAll(2, 2);
  LightEmAll l4 = new LightEmAll(3, 3);

  // tests that the mouse clicks register on the board
  boolean testOnMouseClicked(Tester t) {
    for (GamePiece i : l1.nodes) {
      i.clearPiece();
    }

    l1.board.get(2).get(2).top = true;
    l1.onMouseClicked(new Posn(101, 101));
    l1.board.get(1).get(2).right = true;
    l1.board.get(1).get(2).left = true;
    l1.onMouseClicked(new Posn(101, 51));
    l1.onMouseClicked(new Posn(800, -1)); 
    // the above line tests that the code runs but doesn't do anything for a click not on the board


    return t.checkExpect(l1.board.get(2).get(2).top, false)
        && t.checkExpect(l1.board.get(2).get(2).right, true)
        && t.checkExpect(l1.board.get(1).get(2).right, false)
        && t.checkExpect(l1.board.get(1).get(2).bottom, true)
        && t.checkExpect(l1.board.get(1).get(2).top, true);
  }

  // tests the connectivity of two gamepieces
  boolean testIsConnected(Tester t) {
    for (GamePiece i : l1.nodes) {
      i.clearPiece();
    }
    l1.board.get(2).get(2).top = true;
    l1.board.get(2).get(2).isLit = true;
    l1.board.get(1).get(2).bottom = true;
    l1.board.get(0).get(0).right = true;
    l1.board.get(0).get(1).left = true;
    l1.board.get(0).get(1).isLit = true;

    return t.checkExpect(l1.board.get(2).get(2).isConnected(l1.board.get(1).get(2)), true)
        && t.checkExpect(l1.board.get(1).get(2).isLit, true)
        && t.checkExpect(l1.board.get(0).get(0).isConnected(l1.board.get(0).get(1)), true)
        && t.checkExpect(l1.board.get(0).get(1).isLit, true)
        && t.checkExpect(l1.board.get(0).get(1).isConnected(l1.board.get(2).get(2)), false)
        && t.checkExpect(l1.board.get(0).get(1).isConnected(l1.board.get(0).get(2)), false);
  }

  //tests rotating a clicked gamepiece
  boolean testRotateGamePiece(Tester t) {
    for (GamePiece i : l1.nodes) {
      i.clearPiece();
    }
    l1.board.get(0).get(0).right = true;
    l1.board.get(0).get(1).left = true;
    l1.board.get(1).get(2).right = true;
    l1.board.get(1).get(2).left = true;
    l1.board.get(2).get(2).top = true;
    l1.board.get(2).get(2).left = false;
    l1.board.get(2).get(1).bottom = true;

    for (GamePiece i : l1.nodes) {
      i.rotateGamePiece();
    }
    GamePiece dummy = new GamePiece(); 
    dummy.rotateGamePiece();

    return t.checkExpect(l1.board.get(2).get(2).top, false)
        && t.checkExpect(l1.board.get(2).get(2).right, true)
        && t.checkExpect(l1.board.get(1).get(2).right, false)
        && t.checkExpect(l1.board.get(1).get(2).bottom, true)
        && t.checkExpect(l1.board.get(1).get(2).top, true)
        && t.checkExpect(l1.board.get(0).get(0).right, false)
        && t.checkExpect(l1.board.get(0).get(0).bottom, true)
        && t.checkExpect(l1.board.get(2).get(1).left, true)
        && t.checkExpect(dummy.top, false)
        && t.checkExpect(dummy.bottom, false)
        && t.checkExpect(dummy.left, false)
        && t.checkExpect(dummy.right, false);

  }

  //tests drawing an individual gamepiece
  boolean testDrawGamePiece(Tester t) {
    for (GamePiece i : l1.nodes) {
      i.clearPiece();
    }

    l1.board.get(0).get(0).powerStation = true;
    l1.board.get(0).get(0).right = true;
    l1.board.get(0).get(1).top = true;
    l1.board.get(0).get(2).top = true;
    l1.board.get(0).get(2).isLit = true;
    //l1.bigBang(l1.width, l1.height, 0.1);

    WorldImage base1 = 
        new RectangleImage(50, 50, OutlineMode.SOLID, Color.DARK_GRAY);
    WorldImage outline1 = 
        new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.BLACK);
    WorldImage newBase1 = new OverlayImage(outline1, base1);
    WorldImage line1 = new RectangleImage(
        (50 / 5), 
        (50 / 2), 
        OutlineMode.SOLID, 
        Color.LIGHT_GRAY).movePinhole(0, (50 / 6));

    WorldImage litLine = new RectangleImage(
        (50 / 5), 
        (50 / 2), 
        OutlineMode.SOLID, 
        Color.YELLOW).movePinhole(0, (50 / 6));

    WorldImage powerStar1 = new StarImage(
        (50 * 0.5), 
        OutlineMode.SOLID, 
        Color.MAGENTA);

    WorldImage right = new RotateImage(line1, 90);
    WorldImage litRight = new RotateImage(litLine, 90);
    WorldImage num0_1 = new OverlayImage(litRight, newBase1);
    WorldImage num0 = new OverlayImage(powerStar1, num0_1);
    WorldImage num1 = new OverlayImage(line1, newBase1);
    WorldImage num2 = new OverlayImage(litLine, newBase1);

    return t.checkExpect(l1.board.get(0).get(0).drawGamePiece(20, 1), num0) &&
        t.checkExpect(l1.board.get(0).get(1).drawGamePiece(20, 1), num1) &&
        t.checkExpect(l1.board.get(0).get(2).drawGamePiece(20, 1), num2) &&
        t.checkExpect(l1.board.get(1).get(2).drawGamePiece(20, 1), newBase1);
  }

  // tests initializing the list of nodes
  boolean testInitializeNodes(Tester t) { 
    // test that for any board all of the nodes will be put into this.nodes
    l1.initializeNodes(); // 3 x 3 board => 1 x 9 array
    LightEmAll l2 = new LightEmAll(10, 10);
    l2.initializeNodes();
    return t.checkExpect(l1.board.size() * 3, l1.nodes.size())
        && t.checkExpect(l2.board.size() * 10, l2.nodes.size())
        && t.checkExpect(l2.nodes.get(60) , l2.board.get(6).get(0))
        && t.checkExpect(l2.nodes.get(0) , l2.board.get(0).get(0));
  }

  // tests finding the distance between a given gamepiece and the powerstation
  boolean testGetDistanceHelper() {
    for (GamePiece i : l1.nodes) {
      i.clearPiece();
    }
    l1.board.get(0).get(0).powerStation = true;
    for (int i = 0; i < l1.board.size(); i++) {
      for (int j = 0; j < l1.board.size(); j++) {
        if (l1.getDistance(l1.board.get(i).get(j)) != i + j) {
          System.out.println("testGetDistance failed. \n");
          return false;
        }
      }
    }
    System.out.println("testGetDistance passed. \n");
    return true;
  }

  //tests the function getDistance
  boolean testGetDistance(Tester t) {
    LightEmAll l2 = new LightEmAll(10, 10);
    l2.board.get(5).get(0).powerStation = true;
    l2.powerCol = 0;
    l2.powerRow = 5;
    return t.checkExpect(this.testGetDistanceHelper(), true)
        && t.checkExpect(l2.getDistance(l2.nodes.get(58)), 8)
        && t.checkExpect(l2.getDistance(l2.nodes.get(2)) , 7)
        && t.checkExpect(l2.getDistance(l2.nodes.get(73)), 5);
  }

  // tests drawing the entire gameboard
  boolean testDrawBoard(Tester t) {
    WorldImage base2 = 
        new RectangleImage(50, 50, OutlineMode.SOLID, Color.DARK_GRAY);
    WorldImage outline2 = 
        new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.BLACK);
    WorldImage newBase2 = new OverlayImage(outline2, base2);

    WorldScene sceneL3 = new WorldScene(l3.width, l3.height);
    WorldScene testSceneL3 = new WorldScene(l3.width, l3.height);

    testSceneL3.placeImageXY(newBase2, l3.pieceSize / 2, l3.pieceSize / 2);
    testSceneL3.placeImageXY(newBase2, l3.pieceSize + l3.pieceSize / 2, l3.pieceSize / 2);
    testSceneL3.placeImageXY(newBase2, l3.pieceSize / 2, l3.pieceSize + l3.pieceSize / 2);
    testSceneL3.placeImageXY(newBase2, l3.pieceSize + l3.pieceSize / 2, 
        l3.pieceSize + l3.pieceSize / 2);

    //l3.bigBang(l3.width, l3.height, 0.1);
    //-------------------------------------------------------------------

    WorldScene sceneL4 = new WorldScene(l4.width, l4.height);
    WorldScene testSceneL4 = new WorldScene(l4.width, l4.height);

    WorldImage base1 = 
        new RectangleImage(50, 50, OutlineMode.SOLID, Color.DARK_GRAY);
    WorldImage outline1 = 
        new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.BLACK);
    WorldImage newBase1 = new OverlayImage(outline1, base1);
    WorldImage line1 = new RectangleImage(
        (50 / 5), 
        (50 / 2), 
        OutlineMode.SOLID, 
        Color.LIGHT_GRAY).movePinhole(0, (50 / 6));

    WorldImage litLine = new RectangleImage(
        (50 / 5), 
        (50 / 2), 
        OutlineMode.SOLID, 
        Color.YELLOW).movePinhole(0, (50 / 6));

    WorldImage powerStar1 = new StarImage(
        (50 * 0.5), 
        OutlineMode.SOLID, 
        Color.MAGENTA);

    WorldImage right = new RotateImage(line1, 90);
    WorldImage litRight = new RotateImage(litLine, 90);
    WorldImage num0_1 = new OverlayImage(litRight, newBase1);
    WorldImage num0 = new OverlayImage(powerStar1, num0_1);
    WorldImage num1 = new OverlayImage(line1, newBase1);
    WorldImage num2 = new OverlayImage(litLine, newBase1);

    l4.board.get(0).get(0).powerStation = true;
    l4.board.get(0).get(0).right = true;
    l4.board.get(1).get(1).top = true;
    l4.board.get(0).get(2).top = true;
    l4.board.get(0).get(2).isLit = true;

    //l4.bigBang(l4.width, l4.height, 0.1);

    testSceneL4.placeImageXY(num0    , l4.pieceSize * 0 + l4.pieceSize / 2, 
        l4.pieceSize * 0 + l4.pieceSize / 2);
    testSceneL4.placeImageXY(newBase1, l4.pieceSize * 1 + l4.pieceSize / 2, 
        l4.pieceSize * 0 + l4.pieceSize / 2);
    testSceneL4.placeImageXY(num2    , l4.pieceSize * 2 + l4.pieceSize / 2, 
        l4.pieceSize * 0 + l4.pieceSize / 2);
    testSceneL4.placeImageXY(newBase1, l4.pieceSize * 0 + l4.pieceSize / 2,
        l4.pieceSize * 1 + l4.pieceSize / 2);
    testSceneL4.placeImageXY(num1    , l4.pieceSize * 1 + l4.pieceSize / 2,
        l4.pieceSize * 1 + l4.pieceSize / 2);
    testSceneL4.placeImageXY(newBase1, l4.pieceSize * 2 + l4.pieceSize / 2, 
        l4.pieceSize * 1 + l4.pieceSize / 2);
    testSceneL4.placeImageXY(newBase1, l4.pieceSize * 0 + l4.pieceSize / 2, 
        l4.pieceSize * 2 + l4.pieceSize / 2);
    testSceneL4.placeImageXY(newBase1, l4.pieceSize * 1 + l4.pieceSize / 2, 
        l4.pieceSize * 2 + l4.pieceSize / 2);
    testSceneL4.placeImageXY(newBase1, l4.pieceSize * 2 + l4.pieceSize / 2, 
        l4.pieceSize * 2 + l4.pieceSize / 2);

    return t.checkExpect(l3.drawBoard(sceneL3), testSceneL3) && 
        t.checkExpect(l4.drawBoard(sceneL4), testSceneL4);
  }

  // tests adding the manual board for part 1
  boolean testManualBoard(Tester t) {
    LightEmAll l5 = new LightEmAll(9, 9);
    for (GamePiece i : l5.nodes) {
      i.clearPiece();
    }
    for (ArrayList<GamePiece> i : l5.board) {
      for (GamePiece j : i) {
        j.bottom = true;
        j.top = true;
      }
    }
    ArrayList<GamePiece> temp = l5.board.get(4);
    for (GamePiece i : temp) {
      i.right = true;
      i.left = true;
    }
    l5.board.get(4).get(4).powerStation = true;
    l5.powerCol = 4;
    l5.powerRow = 4;
    l5.board.get(4).get(0).left = false;
    l5.board.get(4).get(8).right = false;
    l5.bigBang(l5.width, l5.height, 0.1);
    return true;
  }
}