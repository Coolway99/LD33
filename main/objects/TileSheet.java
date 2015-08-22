package main.objects;

import java.awt.image.BufferedImage;

public class TileSheet{
	private final BufferedImage image;
	private final int tileSize;
	//The amount of tiles wide said image is (what it's bounds are) max 16
	private final int tileWidth;
	//The amount of tiles tall the said image is (what it's bounds are) max 16
	private final int tileHeight;
	
	public TileSheet(BufferedImage image, int tileSize){
		this(image, tileSize, Math.floorDiv(image.getWidth(), tileSize),
				Math.floorDiv(image.getHeight(), tileSize));
	}
	
	public TileSheet(BufferedImage image, int tileSize, int tileWidth, int tileHeight){
		this.image = image;
		this.tileSize = tileSize;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	public BufferedImage getTile(int x, int y){
		if((x >= this.tileWidth || y >= this.tileHeight) || (x < 0 || y < 0)){
			throw new IndexOutOfBoundsException("Bounds are ("+this.tileWidth+", "+this.tileHeight
					+") given location was ("+(x)+", "+(y)+"), location must be less than bounds"
							+" but greater than or equal to 0");
		}
		return this.image.getSubimage(x*this.tileSize, y*this.tileSize, this.tileSize, this.tileSize);
	}
	
	public int getTileSize(){ return this.tileSize; }
	public int getTileHeight(){ return this.tileHeight; }
	public int getTileWidth(){ return this.tileWidth; }
}
