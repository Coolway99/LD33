package main.objects;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import main.Engine;
import main.ResourceHelper;

public class Script{
	private final HashMap<Point, String> tileList;
	private final boolean hasList;

	public Script(String path){
		this(ResourceHelper.getResourceAsStream("scripts/"+path));
	}

	public Script(InputStream file){
		boolean hasList = false;
		HashMap<Point, String> tileList = new HashMap<>();
		try(BufferedReader in = new BufferedReader(new InputStreamReader(file))){
			String line = in.readLine();
			while(line != null){
				if(line.startsWith("@")){
					hasList = true;
					String[] temp = line.replaceFirst(";", "\u0001").split("\u0001");
					String[] temp2 = temp[0].replaceFirst("\\@", "").split(",");
					Point point = new Point(Integer.parseInt(temp2[0].trim()), Integer.parseInt(temp2[1].trim()));
					tileList.put(point, temp[1]);
				} else {
					String[] temp = line.replaceFirst(":", "\u0001").split("\u0001");
					parse(temp[0], temp[1]);
				}
				line = in.readLine();
			}
		} catch(IOException e){
			throw new RuntimeException(e);
		} finally {
			if(!hasList){
				this.hasList = false;
				this.tileList = null;
			} else {
				this.hasList = true;
				this.tileList = tileList;
			}
		}
	}
	
	private static void parse(String command, String args){
		switch(command){
			case "debug":
				System.out.println(args);
				break;
			case "loadMap":
				Engine.loadMap(args);
				break;
			
			default:
		}
	}
	
	public void checkPoint(int x, int y){
		this.checkPoint(new Point(x, y));
	}
	
	public void checkPoint(Point point){
		if(!this.hasList){return;}
		String command = tileList.get(point);
		if(command != null){
			String[] temp = command.replaceFirst(":", "\u0001").split("\u0001");
			parse(temp[0], temp[1]);
		}
	}
}