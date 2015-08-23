package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.objects.MapData;

public class Main extends JPanel{
	private static final long serialVersionUID = 5368223505868578629L;
	
	public static final JFrame frame = new JFrame("LD33");
	public static final Main panel = new Main();
	public static final InputHandler keyHandler = new InputHandler();
	
	public static void main(String...args){
		frame.setSize(800, 608);
		Engine.init();
		Engine.test();
		Timer timer = new Timer("Update Timer", true);
		timer.scheduleAtFixedRate(new UpdateTask(), 300, 33);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(panel);
		frame.addKeyListener(keyHandler);
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		BufferedImage render = Engine.render();
		g2.drawImage(render, (MapData.MaxWidthTiles*-32+800), (MapData.MaxHeightTiles*-32+600), null);
		super.paintComponents(g);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800, 608);
	}
	
	@Override
	public Dimension getMinimumSize(){
		return this.getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize(){
		return this.getPreferredSize();
	}
}
final class UpdateTask extends TimerTask{
	@Override
	public void run(){
		Main.frame.repaint();
	}
}
