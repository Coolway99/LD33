package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.objects.Direction;

public class InputHandler implements KeyListener{
	
	public Direction direction = Direction.Down;
	public boolean keyDown = false;
	public boolean enter = false;

	@Override
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				this.keyDown = true;
				this.direction = Direction.Up;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				this.keyDown = true;
				this.direction = Direction.Right;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				this.keyDown = true;
				this.direction = Direction.Down;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				this.keyDown = true;
				this.direction = Direction.Left;
				break;
			case KeyEvent.VK_ENTER:
				this.enter = true;
				break;
			default:
		}
	}

	@Override
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				this.keyDown = false;
				break;
			case KeyEvent.VK_ENTER:
				this.enter = false;
				break;
			default:
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0){ /*Nah*/ }

}
