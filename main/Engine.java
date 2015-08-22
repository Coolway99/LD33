package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.objects.MapData;

public class Engine{
	public static final int TileSize = 32;

	private static MapData map = null;
	private static int x, y;
	private static int cooldown = 0;
	private static final int SET_COOLDOWN = 150;
	private static BufferedImage moving = null;
	
	public static void init(){
		
	}

	public static void drawSpeechbubble(Rectangle rect, String text){

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
		g2.fillOval(400-8, 304-8, 16, 16);
		map.postRender(x, y, g2);
		if(Main.keyHandler.keyDown){
			Main.keyHandler.keyDown = false;
			int newX = x;
			int newY = y;
			switch(Main.keyHandler.direction){
				case Up:
					newY--;
					break;
				case Right:
					newX++;
					break;
				case Down:
					newY++;
					break;
				case Left:
					newX--;
					break;
				default:
					//HALT AND CATCH FIRE
			}
			if(map.canMoveTo(newX, newY)){
				x = newX;
				y = newY;
				map.update(x, y);
			}
		}
		return screen;
	}

	public static void test(){
		x = 1;
		y = 1;
		loadMap("TestMap.map");
	}
}
