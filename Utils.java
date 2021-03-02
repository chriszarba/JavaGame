import java.awt.Color;

class Utils {
	
	
	public static Color getColor(int distance, int pathDiameter) { 
    Color lineColor = Color.LIGHT_GRAY;

    if(distance <= pathDiameter * 0.2) {
      lineColor = Color.RED;
    }
    else if((distance > pathDiameter * 0.2) && (distance <= pathDiameter * 0.4)) {
      lineColor = Color.YELLOW;
    }
    else if((distance > pathDiameter * 0.4) && (distance <= pathDiameter * 0.6)) {
      lineColor = Color.GREEN;
    }
    else if((distance > pathDiameter * 0.6) && (distance <= pathDiameter * 0.8)) {
      lineColor = Color.CYAN;
    }
    else if((distance > pathDiameter * 0.8) && (distance <= pathDiameter)) {
      lineColor = Color.BLUE;
    }

    return lineColor;

 // new Color(R, G, B) to use gradient 
  }


}