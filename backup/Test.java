import javax.swing.*;
import java.awt.*;

class TestFrame
{
	public static void main(String args[])
	{
		JFrame jf = new JFrame("Panel");

		
		jf.add(new JPanel() { 
								ImageIcon led = new ImageIcon(/*/media/0472A04018C963D8/Java/LED/*/"LED.gif");
								Image l = led.getImage();
								protected void paintComponent(Graphics g) {
									g.drawImage(l,0,0,null);
									g.drawImage(l,100,0,120,20,20,0,40,20,null);
									g.drawImage(l,200,0,220,20,0,0,20,20,null);
								}
		
		});
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jf.setSize(500,200);
		//jf.setResizable(false);
		//jf.setUndecorated(true);
		//jf.setBackground(new Color(240,240,240,100));		//got to tweek this later to support per-pixel transparency
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);	
	}
}
