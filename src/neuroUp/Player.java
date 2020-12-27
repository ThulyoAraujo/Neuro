package neuroUp;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	private int x, y, yInicial, width, height, legendNumber, speed = 3;
	private boolean up;
	
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}
	
	public int getLegendNumber() {
		return legendNumber;
	}

	public void setLegendNumber(int legendNumber) {
		this.legendNumber = legendNumber;
	}

	public Player(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.yInicial = y;
		this.width = width;
		this.height = height;
	}
	
	public void update() {
		if(up) {
			y -= speed;
			legendNumber = 2;
		} else if (y < yInicial){
				y += speed;
				legendNumber = 4;
		}
		
		if(y <= 0) {
			y = 0;
			legendNumber = 3;
		} else if (y >= yInicial) {
			legendNumber = 1;
		}
	}

	public void render(Graphics g) {
		//cor, formato e tamanho
		g.setColor(Color.white);
		g.fillOval(x, (int)y, width, height);
	}

}
