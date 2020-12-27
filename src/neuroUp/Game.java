package neuroUp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	
	//Tamanho da janela do jogo
	private final int WIDTH = 160;
	private final int HEIGHT = 90;
	private final int SCALE = 5;
	
	private BufferedImage image;
	
	private static Player ball;
	private static Legends legend;
	
	private int tamanhoDoPlayer;
	
	public Game() {
		
		//Tamanho da tela
		this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		initFrame();
		
		//Ler as teclas do teclado
		this.addKeyListener(this);
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		//Incializar objetos
		tamanhoDoPlayer = 40;
		ball = new Player((WIDTH*SCALE/2)-(tamanhoDoPlayer/2), (HEIGHT*SCALE/2)-(tamanhoDoPlayer/2), tamanhoDoPlayer, tamanhoDoPlayer);
		legend = new Legends((WIDTH*SCALE/2)-(tamanhoDoPlayer/2), (HEIGHT*SCALE-10)-(tamanhoDoPlayer/2));
		
	}
	
	public void initFrame() {
		frame = new JFrame("Game NeuroUP");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void update() {
		ball.update();
		legend.setLegendOption(ball.getLegendNumber());
		legend.update();
		
	
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		//Cor de fundo e tamanho que ocupa	
		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g.setColor(Color.darkGray);
		g.fillRect(0, (HEIGHT*SCALE/2)+(tamanhoDoPlayer/2), WIDTH*SCALE, 5);
		
		ball.render(g);
		legend.render(g);
		
		bs.show();
	}
	
	//Método de runnable, corre o programa como thread, linhas de código paralelas
	@Override
	public void run() {
		//Foca na tela do jogo de primeira
		requestFocus();
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+= 1000;
			}
		}
		
		stop();
		
	}

	//Métodos de keylistener, para receber informações do teclado
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			ball.setUp(true);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			ball.setUp(false);
		}
		
	}
	
	

}
