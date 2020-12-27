package neuroUp;

import java.awt.Graphics;

public class Legends {
	
	int x, y, legendOption;

	String str = "Relaxado";
	
	public Legends(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		switch (legendOption) {
		case 1:
			str = "Relaxado";
			break;
		case 2:
			str = "Contraindo";
			break;
		case 3:
			str = "Contraído";
			break;
		case 4:
			str = "Relaxando";
			break;
		default:
			break;
		}
	}
	
	public void render(Graphics g) {
		g.drawString(str, x, y);
	}

	public int getLegendOption() {
		return legendOption;
	}

	public void setLegendOption(int legendOption) {
		this.legendOption = legendOption;
	}
	
}
