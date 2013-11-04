import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;

class Lcd implements Runnable{

	boolean bulb[][];
	int r,c;
	Image led;
	Graphics2D g2d;	//Drawing could be sped-up if we have a reference to the offscreen Graphics object
	LcdPanel lcdPanel; //stores the LcdPanel that created this, for callback requestRepaint();
	//DigitalText dfont;

	//Default
	public Lcd() {
		r = 20;
		c = 32;
		bulb = new boolean[20][32];
		ImageIcon led = new ImageIcon("/media/0472A04018C963D8/Java/LED/LED.gif");
		this.led = led.getImage();
	}

	//Use a grid of your choice
	public Lcd(int r, int c) {
		this.r = r;
		this.c = c;
		bulb = new boolean[r][c];
		ImageIcon led = new ImageIcon("/media/0472A04018C963D8/Java/LED/LED.gif");
		this.led = led.getImage();
	}
	
	//Use a grid and led image of your choice
	public Lcd(int r, int c, String path) {
		this.r = r;
		this.c = c;
		bulb = new boolean[r][c];
		ImageIcon led = new ImageIcon( path );
		if( led == null ) {
			led = new ImageIcon("/media/0472A04018C963D8/Java/LED/LED.gif");
		}
		this.led = led.getImage();
	}


	//Sets an LED on
	public void set(int row, int col) {
		if( (row < r) && (col < c) ) {
			if(g2d != null && bulb[row][col] == false) {
				g2d.drawImage(led,20*(col +1),20*(row+1),20*(col+2),20*(row+2),20,0,40,20,null);
				lcdPanel.requestRepaint();
			}
			bulb [row][col] = true;
		}
	}

	//Switches an LED off
	public void clear(int row, int col) {
		if( (row < r) && (col < c) ) {
			bulb [row][col] = false;
		}
	}

	//Toggles the status of an LED
	public void toggle(int row, int col) {
		if( (row < r) && (col < c) ) {
			bulb [row][col] = !bulb [row][col];
			g2d.drawImage(led,20*(col +1),20*(row+1),20*(col+2),20*(row+2),(bulb[row][col]?20:0),0,(bulb[row][col]?40:20),20,null);
			lcdPanel.requestRepaint();
		}
	}

	//Check the status of an LED
	public boolean check(int row, int col) {
		if( (row < r) && (col < c) ) {
			return bulb [row][col];
		}
		return false;
	}
	
	public void writeDigitalText(String abc) {
		
	}
	
	Image getLedImage() {
		return led;
	}
	
	int getRows() {	return r;	}
	int getCols() {	return c;	}
	
	public void setNotifier(LcdPanel lcdPanel) {
		this.lcdPanel = lcdPanel;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void setGraphics(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	//not required
	public void delayMilis( final int milis ) {
		try {
			Thread.sleep(milis);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//this thread is reqd. for animation
	public void run()
	{
		long b4Time, timeDiff, sleep;

		b4Time = System.currentTimeMillis();
		while( true )
		{
			cycle();
			timeDiff = System.currentTimeMillis() - b4Time;
			sleep = 100 - timeDiff;
			if(sleep < 0)
				sleep = 2;
			try{
				Thread.sleep(sleep);
			}catch(Exception e){}
			b4Time = System.currentTimeMillis();

			//System.out.println("Clock's tikin");
			//if(repaint)
			//	lcdPanel.requestRepaint();
		}
	}
	
	public void cycle() {
		toggle((int)(Math.random()*r),(int)(Math.random()*c));
	}
}
