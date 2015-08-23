package main.objects;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import main.Engine;
import main.Main;
import main.ResourceHelper;

public class Script{
	private static final HashMap<String, String> varList = new HashMap<>();
	private final HashMap<Point, String> tileList;
	private final boolean hasList;

	public Script(String path){
		this(ResourceHelper.getResourceAsStream("scripts/"+path));
	}

	@SuppressWarnings("hiding")
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
					parse(temp[0], temp[1], this);
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
	
	private static void parse(String command, String args, Script script){
		switch(command){
			case "debug":
				System.out.println(args);
				break;
			case "loadMap":{
				String[] temp = args.split("\\*\\,\\:");
				Engine.loadMap(temp[0], Integer.parseInt(temp[1].trim()),
						Integer.parseInt(temp[2].trim()));
				break;
			}
			case "setPos":{
				String[] temp = args.split("\\*\\,\\:");
				Engine.setLocation(Integer.parseInt(temp[0].trim()),
						Integer.parseInt(temp[1].trim()), Boolean.parseBoolean(temp[2].trim()));
				break;
			}
			case "dialog":
			case "displayText":
				Engine.drawSpeechbubble(args);
				break;
			case "repaint":
				Main.frame.repaint();
				break;
			case "setFacing":
				switch(args.toLowerCase()){
					case "up":
						Main.keyHandler.direction = Direction.Up;
						break;
					case "right":
						Main.keyHandler.direction = Direction.Right;
						break;
					case "down":
						Main.keyHandler.direction = Direction.Down;
						break;
					case "left":
						Main.keyHandler.direction = Direction.Left;
						break;
					default:
				}
				break;
			case "addEvent":{
				String[] temp = args.split("\"");
				String[] temp2 = temp[0].split("\\*\\,\\:");
				script.tileList.put(new Point(Integer.parseInt(temp2[0].trim()),
						Integer.parseInt(temp2[1].trim())), temp[1]);
				break;
			}
			case "removeEvent":{
				String[] temp = args.split("\\*\\,\\:");
				script.tileList.remove(new Point(Integer.parseInt(temp[0].trim()),
						Integer.parseInt(temp[1].trim())));
				break;
			}
			case "setVar":{
				String[] temp = args.split("\\*\\,\\:");
				varList.put(temp[0], temp[1]);
				break;
			}
			case "clearVar":
				varList.remove(args);
				break;
			default:
		}
	}
	
	public void checkPoint(int x, int y){
		this.checkPoint(new Point(x, y));
	}
	
	public void checkPoint(Point point){
		if(!this.hasList){return;}
		String string = this.tileList.get(point);
		if(string != null){
			String[] commands = string.split("\\:\\,\\*");
			for(String command : commands){
				String[] temp = command.replaceFirst(":", "\u0001").split("\u0001");
				parse(temp[0], temp[1], this);
			}
		}
	}
}