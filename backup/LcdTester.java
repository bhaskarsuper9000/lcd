class LcdTester {
	
	public static void main(String args[]) {
		Lcd lcd = new Lcd(6,20);
		
		for(int i=0; i<lcd.getX(); i++) {
			for(int j=0; j<lcd.getY(); j++)
				System.out.print( (lcd.check(i,j) == true? "1":"0") + " " );
			System.out.println();
		}
	}
}
