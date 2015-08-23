package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.objects.Direction;
import main.objects.MapData;
import main.objects.TileSheet;

public class Engine{
	public static final int TileSize = 32;
	private static final InputHandler keys = Main.keyHandler;
	private static MapData map = null;
	private static int x, y;
	private static TileSheet playerSprite;
	//private static int cooldown = 0;
	//private static final int SET_COOLDOWN = 150;
	//private static BufferedImage moving = null;
	private static BufferedImage last;
	private static String speechText;
	private static boolean hasText = false;
	
	public static void init(){
		x = 0;
		y = 0;
		try{
			playerSprite = new TileSheet(ImageIO.read(ResourceHelper.getResource("characters/player.png")), 32);
		} catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public static void drawSpeechbubble(String text){
		speechText = text;
		hasText = true;
	}

	public static void loadMap(String path, int posX, int posY){
		map = new MapData("maps/"+path);
		x = posX;
		y = posY;
	}
	
	public static void setLocation(int posX, int posY, boolean update){
		x = posX;
		y = posY;
		if(update){
			map.update(x, y);
		}
	}

	public static BufferedImage render(Graphics2D g){
		if(hasText){
			Graphics2D g2 = last.createGraphics();
			g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
			g2.setColor(Color.WHITE);
			g2.fillRoundRect(0, 305, 780, 250, 40, 40);
			g2.setColor(Color.BLACK);
			g2.drawRoundRect(5, 310, 770, 240, 40, 40);
			g2.drawString(speechText, 20, 345);
			while(!keys.enter){return last;
			}
			hasText = false;
			keys.enter = false;
		}
		BufferedImage screen = new BufferedImage(MapData.MaxWidthTiles*TileSize,
				MapData.MaxHeightTiles*TileSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = screen.createGraphics();
		map.render(x, y, g2);
		//g2.setColor(Color.RED);
		//g2.fillOval(400-8, 304-8, 16, 16);
		g2.drawImage(playerSprite.getTile(Direction.getNum(keys.direction), 0), 400-16, 304-16, null);
		map.postRender(x, y, g2);
		if(keys.keyDown){
			keys.keyDown = false;
			int newX = x;
			int newY = y;
			switch(keys.direction){
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
		last = screen;
		return screen;
	}

	public static void test(){
		loadMap("TestMap.map", 1, 1);
	}
}
