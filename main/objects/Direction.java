package main.objects;

public enum Direction{
	Up,
	Right,
	Down,
	Left;
	
	public static int getNum(Direction direction){
		switch(direction){
			case Up: return 0;
			case Right: return 1;
			case Down: return 2;
			case Left: return 3;
			default: return -1;
		}
	}
}
