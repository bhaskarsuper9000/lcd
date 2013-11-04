import javax.swing.*;
import java.awt.Color;

class Frame
{
	public static void main(String args[])
	{
		JFrame jf = new JFrame("Panel");
		LcdPanel p = new LcdPanel();
		
		jf.add(p);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jf.setSize(p.getWidth(),p.getHeight());
		jf.setResizable(false);
		jf.setUndecorated(true);
		//jf.setBackground(new Color(26,0,0));		//got to tweek this later to support per-pixel transparency
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
}
