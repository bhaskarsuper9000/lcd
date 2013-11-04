import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;

class Lcd implements Runnable{

	boolean bulb[][];
	int r,c;
	Image led;
	Graphics2D g2d;	//Drawing could be sped-up if we have a reference to the offscreen Graphics object
	LcdPanel lcdPanel; //stores the LcdPanel that created this, for callback requestRepaint();
	boolean repaint = false;
	boolean repaintLastCol = false;
	BitmapFont bFont;

	//Default
	public Lcd() {
		r = 20;
		c = 32;
		bulb = new boolean[20][32];
		ImageIcon led = new ImageIcon("LED.gif");
		this.led = led.getImage();
		
		bFont = new BitmapFont ("AAA",c,r);
	}

	//Use a grid of your choice
	public Lcd(int r, int c) {
		this.r = r;
		this.c = c;
		bulb = new boolean[r][c];
		ImageIcon led = new ImageIcon("LED"+(int)(Math.random()*7+1)+".gif");
		this.led = led.getImage();
		
		bFont = new BitmapFont ("HAPPY DIWALI & A PROSPEROUS NEW YEAR!:)",c,r);
	}
	
	//Use a grid and led image of your choice
	public Lcd(int r, int c, String path) {
		this.r = r;
		this.c = c;
		bulb = new boolean[r][c];
		ImageIcon led = new ImageIcon( path );
		if( led == null ) {
			led = new ImageIcon("LED.gif");
		}
		this.led = led.getImage();
		
		bFont = new BitmapFont ("AAA",c,r);
	}


	/*	Sets an LED on.
		The g in the 3 methods set, clear and toggle indicates that
		it makes correponding changes in the Graphics context too
	*/
	public void gSet(int row, int col) {
		if( (row < r) && (col < c) ) {
			if(g2d != null && bulb[row][col] == false) {
				g2d.drawImage(led,20*(col +1),20*(row+1),20*(col+2),20*(row+2),20,0,40,20,null);
				repaint = true;
			}
			bulb [row][col] = true;
		}
	}

	//Switches an LED off
	public void gClear(int row, int col) {
		if( (row < r) && (col < c) ) {
			if(g2d != null && bulb[row][col] == true) {
				g2d.drawImage(led,20*(col +1),20*(row+1),20*(col+2),20*(row+2),0,0,20,20,null);
				repaint = true;
			}
			bulb [row][col] = false;
		}
	}

	//Toggles the status of an LED
	public void gToggle(int row, int col) {
		if( (row < r) && (col < c) ) {
			bulb [row][col] = !bulb [row][col];
			if(g2d != null) {
				g2d.drawImage(led,20*(col +1),20*(row+1),20*(col+2),20*(row+2),(bulb[row][col]?20:0),0,(bulb[row][col]?40:20),20,null);
				repaint = true;
			}
		}
	}
	
	/*	Sets an LED on.
		The c in the 3 methods set, clear and toggle indicates that
		it checks for index before making changes
	*/
	public void cSet(int row, int col) {
		if( (row < r) && (col < c) ) {
			bulb [row][col] = true;
		}
	}

	//Switches an LED off
	public void cClear(int row, int col) {
		if( (row < r) && (col < c) ) {
			bulb [row][col] = false;
		}
	}

	//Toggles the status of an LED without checking
	public void cToggle(int row, int col) {
		if( (row < r) && (col < c) ) {
			bulb [row][col] = !bulb [row][col];
		}
	}
	
	//Sets an LED on. No range check
	public void set(int row, int col) {
		bulb [row][col] = true;
	}

	//Switches an LED off
	public void clear(int row, int col) {
		bulb [row][col] = false;
	}

	//Toggles the status of an LED
	public void toggle(int row, int col) {
		bulb [row][col] = !bulb [row][col];
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
	
	//delay :P
	public void delayMillis( final int milis ) {
		try {
			Thread.sleep(milis);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//animation thread
	public void run()
	{
		long b4Time, timeDiff, sleep;

		//initialization code here
		/*
		//Some random stuff
		for(int i=0;i<100;i++)
			gToggle((int)(Math.random()*r),(int)(Math.random()*c));
		repaint = true;
		*/
		

		b4Time = System.currentTimeMillis();
		while( true )
		{
			//System.out.println("Clock's tikin");
			/*if(repaint) {
				lcdPanel.requestRepaint(20,20,20*(c-1),20*r);
				repaint = false;
			}
			
			if(repaintLastCol) {
				lcdPanel.requestRepaint(20*c,20,20,20*r);
				repaintLastCol = false;
			}*/

			if(repaint) {
				lcdPanel.requestRepaint();
				repaint = false;
			}

			animate();
			timeDiff = System.currentTimeMillis() - b4Time;
			sleep = 200 - timeDiff;
			//System.out.println(sleep);
			if(sleep > 0)
			{	//sleep = 2;
				try{
					Thread.sleep(sleep);
				}catch(Exception e){}
			}
			b4Time = System.currentTimeMillis();
		}
	}

	/*	The actual code for animation starts here	*/
	public void animate() {
		scrollLeft();
	}

	public void scrollUp() {
		//Copy the image from g2d to g2d, but change the source
		g2d.drawImage(lcdPanel.bi, 20, 20 , 20*(getCols()+1), 20*getRows(), 20, 40, 20*(getCols()+1), 20*(getRows()+1),null);
		repaint = true;	//Is doing this really a good idea, coz the data-structure isn't updated yet
		
		for(int i=1; i<(r-1); i++) {
			System.arraycopy(bulb[i+1],0,bulb[i],0,c);
		}
		
		//updateLastRow();
	}
	
	public void scrollDown() {
		//Involves more overhead than ScrollUp.
		/*g2d.setColor(Color.green);
		g2d.drawLine(20, 40, 20*(getCols()+1), 20*(getRows()+1));
		repaint = true;
		delayMilis(1000);d
		
		g2d.setColor(Color.yellow);
		g2d.drawLine( 20, 20 , 20*(getCols()+1), 20*getRows());
		repaint = true;
		
		delayMilis(1000);*/
		
		BufferedImage bi = new BufferedImage(lcdPanel.getWidth(), lcdPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D goff = bi.createGraphics();		
		goff.drawImage(lcdPanel.bi,0,0,lcdPanel.getWidth(), lcdPanel.getHeight(),null);
		goff.drawImage(lcdPanel.bi, 20*(getCols()+1), 20*(getRows()+1), 20, 40, 20*(getCols()+1), 20*getRows(), 20, 20, null);
		
		//change the references itself :P..bad way, i know
		lcdPanel.bi = bi;
		lcdPanel.goff = goff;
		
		repaint = true;	//Is doing this really a good idea, coz the data-structure isn't updated yet
		
		for(int i=r-2; i>=0; i--) {
			System.arraycopy(bulb[i],0,bulb[i+1],0,c);
		}
		//delayMilis(1000);
		
		//updateFirstRow();
	}
	
	public void scrollLeft() {
		delayMillis(10);	//this prevents the image from changing while drawing is in progress
							//i.e. wait for the image to finish with drawing onscreen. A while loop wouldn't work properly

		//Copy the image from g2d to g2d, but change the source
		g2d.drawImage(lcdPanel.bi, 20, 20 , 20*getCols(), 20*(getRows()+1), 40, 20, 20*(getCols()+1), 20*(getRows()+1),null);
		//repaint = true;	//Is doing this really a good idea, coz the data-structure isn't updated yet
		
		for(int i=0; i<r-1; i++) {
			System.arraycopy(bulb[i],1,bulb[i],0,c-1);
		}

		updateLastCol();
		//delayMillis(10);	//Uncomment this only if performance is very bad
		repaint = true;
	}
	
	public void scrollRight() {
		
		
		BufferedImage bi = new BufferedImage(lcdPanel.getWidth(), lcdPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D goff = bi.createGraphics();		
		goff.drawImage(lcdPanel.bi,0,0,lcdPanel.getWidth(), lcdPanel.getHeight(),null);
		goff.drawImage(lcdPanel.bi, 40, 20,20*(getCols()+1), 20*(getRows()+1), 20, 20,20*getCols(), 20*(getRows()+1), null);
		
		//change the references itself :P..bad way, i know
		lcdPanel.bi = bi;
		lcdPanel.goff = goff;
		
		repaint = true;	//Is doing this really a good idea, coz the data-structure isn't updated yet

		for(int i=1; i<r-1; i++) {
			System.arraycopy(bulb[i],0,bulb[i],1,c-1);
		}
		
		//updateFirstCol();
	}
	
	public void updateLastCol() {
		boolean temp[] = bFont.getNextCol();
		int dx1 = 20*c, dx2 = 20*(c+1);
		int dy1 = 20, dy2 = 40;
		int sx1,sx2;
		for(int i=0; i<r; i++, dy1+=20, dy2+=20) {
			//System.out.println(temp[i]);
			sx1 = (temp[i]?20:0);
			sx2 = sx1+20;
			if(temp[i] ^ bulb[i][c-1]) {
				g2d.drawImage(led,dx1,dy1,dx2,dy2,sx1,0,sx2,20,null);
				//System.out.println(20*c +"|"+ 20*(i+1) +"|"+ 20*(c+1) +"|"+ 20*(i+2)+"|"+(temp[i]?20:0)+"|"+0+"|"+(temp[i]?40:20)+"|"+20);
				bulb[i][c-1] = temp[i];
			}
		}

		/*
		System.out.println("======================================");

		for(int i=0; i<getRows(); i++) {
			for(int j=0; j<getCols(); j++)
				System.out.print( (check(i,j) == true? "1":"0") + " " );
			System.out.println();
		}
		System.out.println("======================================");
		*/
		//delayMillis(500);
		//repaintLastCol = true;
	}
}
