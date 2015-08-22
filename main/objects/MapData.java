package main.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Engine;
import main.ResourceHelper;

public class MapData{
	//Higher to overdraw a bit for smooth movement, tiles are 22 x 18.75 that will fit on an 800x600 screen
	public static final int MaxWidthTiles = 25;
	public static final int MaxHeightTiles = 19;
	public static final int HalfMaxWidthTiles = (MaxWidthTiles-1)/2;
	public static final int HalfMaxHeightTiles = (MaxHeightTiles-1)/2;

	private final int width, height;
	private final TileSheet tileSheet;
	private final Script script;
	private final ByteData map;

	public MapData(InputStream map){
		try(BufferedReader in = new BufferedReader(new InputStreamReader((map)))){
			String[] temp = in.readLine().split(",");
			this.width = Integer.parseInt(temp[0].trim());
			this.height = Integer.parseInt(temp[1].trim());
			this.tileSheet = new TileSheet(ImageIO.read(ResourceHelper.getResource("tilemaps/"+in.readLine())),
					Engine.TileSize);
			this.script = new Script(in.readLine());
			this.map = new ByteData(this.width, this.height, in);
		} catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public MapData(String path){
		this(ResourceHelper.getResourceAsStream(path));
	}

	/**
	 * NOTE: Should not render the player, rather just the map beneath
	 * @param x the player's X coord
	 * @param y the player's Y coord
	 * @param g2 the graphics to draw with
	 */
	public void render(int playerX, int playerY, Graphics2D g2){
		g2.setColor(Color.BLACK);
		for(int y = 0; y < MaxHeightTiles; y++){
			for(int x = 0; x < MaxWidthTiles; x++){
				final int x2 = playerX+x-HalfMaxWidthTiles, y2 = playerY+y-HalfMaxHeightTiles;
				if(this.inBounds(x2, y2)){
					g2.drawImage(this.getTile(x2, y2), x*32, y*32, null);
				} else {
					g2.drawRect(x, y, Engine.TileSize, Engine.TileSize);
				}
			}
		}
	}
	
	public void update(int playerX, int playerY){
		this.script.checkPoint(playerX, playerY);
	}
	
	public BufferedImage getTile(int x, int y){
		int bite = this.map.bytes[y][x];
		return this.tileSheet.getTile(bite&15, bite>>4);
	}
	
	public boolean inBounds(int x, int y){
		return -1 < x && x < this.width && -1 < y && y < this.height;
	}

	public boolean canMoveTo(int x, int y){
		return this.map.flags[y][x];
	}

	/**
	 * Optional to override for more complex maps to add effects and stuffs
	 * @see #render(int, int, Graphics2D)
	 */
	@SuppressWarnings("static-method")
	public void postRender(int x, int y, Graphics2D g2){ return; }

	private static final class ByteData{
		private final byte[][] bytes;
		private final boolean[][] flags;

		public ByteData(int width, int height, BufferedReader in){
			this.bytes = new byte[height][width];
			this.flags = new boolean[height][width];
			try{
				for(int y = 0; y < height; y++){
					String[] line = in.readLine().split(",");
					for(int x = 0; x < line.length; x++){
						int temp = Integer.parseInt(line[x].trim(), 16);
						this.flags[y][x] = temp >= 0; //true if can move, false if wall
						this.bytes[y][x] = (byte) Math.abs(temp);
					}
				}
			}catch(IOException e){
				throw new RuntimeException(e);
			}
		}
	}
}
