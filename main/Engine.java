package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.objects.Direction;
import main.objects.MapData;

public class Engine{
	public static final int TileSize = 32;
	
	private static MapData map = null;
	private static int x, y;
	
	public static void drawSpeechbubble(Rectangle rect, String text){
		
	}
	
	/** @param direction The direction to move 
	 * @param speed The speed to move*/
	public static void move(Direction direction, double speed){
		
	}
	
	public static void loadMap(String path){
		map = new MapData("maps/"+path);
		
	}
	
	public static BufferedImage render(){
		BufferedImage screen = new BufferedImage(MapData.MaxWidthTiles*TileSize,
				MapData.MaxHeightTiles*TileSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = screen.createGraphics();
		map.render(x, y, g2);
		g2.setColor(Color.RED);
		g2.fillRect(400-8, 304-8, 16, 16);
		map.postRender(x, y, g2);
		return screen;
	}
	
	public static void test(){
		x = 1;
		y = 1;
		loadMap("TestMap.map");
	}
}
