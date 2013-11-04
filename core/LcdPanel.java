import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

class LcdPanel extends JPanel {

	Lcd lcd;
	Image l,r,t,b,tl,tr,bl,br,led;
	BufferedImage bi;	//Used for double-buffering
	Graphics2D goff;	//offscreen g2d of bi
	
	public static final int SPRITE_WIDTH = 20;	//not used yet
	public static final int SPRITE_HEIGHT= 20;	//not used yet
	
	//Timer time;
	
	public LcdPanel()
	{
		lcd = new Lcd(6,48);	//use default red LED
		
		setFocusable(true);
		
		ImageIcon t = new ImageIcon("/media/0472A04018C963D8/Java/LED/top_new.png");
		ImageIcon l = new ImageIcon("/media/0472A04018C963D8/Java/LED/left_new.png");
		ImageIcon r = new ImageIcon("/media/0472A04018C963D8/Java/LED/right_new.png");
		ImageIcon b = new ImageIcon("/media/0472A04018C963D8/Java/LED/bottom_new.png");
		ImageIcon tl = new ImageIcon("/media/0472A04018C963D8/Java/LED/tl_new.png");
		ImageIcon tr = new ImageIcon("/media/0472A04018C963D8/Java/LED/tr_new.png");
		ImageIcon bl = new ImageIcon("/media/0472A04018C963D8/Java/LED/bl_new.png");
		ImageIcon br = new ImageIcon("/media/0472A04018C963D8/Java/LED/br_new.png");

		this.t = t.getImage();
		this.l = l.getImage();
		this.r = r.getImage();
		this.b = b.getImage();
		this.tl = tl.getImage();		
		this.tr = tr.getImage();
		this.bl = bl.getImage();
		this.br = br.getImage();
		this.led = lcd.getLedImage();


		//System.out.println("X = "+getWidth() + "Y = "+getHeight());
		bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		goff = bi.createGraphics();

		initBoard();
		//Decomment for testing paintComponent(Graphics g)
		//goff.setColor(Color.CYAN);
		//goff.fillRect(0,0,getWidth(),getHeight());
		
		lcd.setGraphics( goff );
		lcd.setNotifier( this );
		
		//Thread animator = new Thread(this);
		//animator.start();
	
	}

	public int getWidth() {
		return 20 * ( lcd.getCols() + 2);
	}
	
	public int getHeight() {
		return 20 * ( lcd.getRows() + 2);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage( bi ,0,0,null);
		//System.out.println("paint called");
		/*
		if( ( p.x - 525) % 2400 == 0 )
			p.nx = 0;
		if( (p.x - 1725) % 2400 == 0 ) 
			p.nx2 = 0;
		g2d.drawImage(img, 685-p.nx2, 0, null);
		if( p.getX() > 525 )
			g2d.drawImage(img, 685-p.nx, 0, null);
		
		g2d.drawImage( p.getImage(), p.left, v, null );
		
		ArrayList bullets = Player.getBullets();
		for(int w = 0; w < bullets.size(); w++)
		{
			Bullet m = (Bullet)bullets.get(w);
			if(m.visible)
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), null);
		}
		
		if(p.x > 400)
			if(en1.isAlive == true)
				g2d.drawImage(en1.getImage(),en1.getX(),en1.getY(),null);
		
		if(p.x > 500)
			if(en2.isAlive == true)
				g2d.drawImage(en2.getImage(),en2.getX(),en2.getY(),null);		
		
*/
	}
	
	public void initBoard() {
		int i = 1, j = 1;
	
		//draw top left image
		goff.drawImage(tl,0,0,null);
		//draw top images
		for( i=1; i<lcd.getCols()+1; i++) {
			goff.drawImage(t,20*i,0,null);
		}
		//draw top right image
		goff.drawImage(tr,20*i,0,null);


		//draw the intermediate rows
		for( i=1; i<lcd.getRows()+1; i++) {
			//draw a left image at the begining of every row
			goff.drawImage(l,0,20*i,null);
			//loop for drawing individual LEDs
			for( j=1; j<lcd.getCols()+1; j++) {
				goff.drawImage(led,20*j,20*i,20*j+20,20*i+20,0,0,20,20,null);
				
				if( j == 4 )
					goff.drawImage(led,20*j,20*i,20*j+20,20*i+20,20,0,40,20,null);
			}
			//draw a right image at the end of every row
			goff.drawImage(r,20*j,20*i,null);
		}

		//draw bottom left image
		goff.drawImage(bl,0,20*i,null);
		//draw bottom images
		for( j=1; j<lcd.getCols()+1; j++)
			goff.drawImage(b,20*j,20*i,null);
		//draw bottom right image
		goff.drawImage(br,20*j,20*i,null);
		

	}
	
	public void requestRepaint() {
		repaint();
	}

}
