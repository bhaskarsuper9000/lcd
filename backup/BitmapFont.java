//A very simple class that stores the
//digital charater bitmaps

public class BitmapFont {
	
	//To handle scrolling
	int sx,	//start x
		sy,	//start y
		w, 	//x bound
		h,	//y bound
		cx,	//current x
		cy;	//current y
	
	//Font metrics
	int charWidth,
		charHeight,
		intraSpace,	//spacing between 2 chars
		interSpace;	//spacing between 2 words
	
	int str[], sIndex;	//the preprocessed string and its string index
		
	
	boolean isIntraSpace;
	byte chars[][];
	
	//The constructor initializes the engine to the default values
	BitmapFont(String s, int width, int height) {

		charWidth = 5;
		charHeight = 6;

		sx = 0;
		sy = 0;
		w = s.length() * charWidth;
		h = height * charHeight;
		cx = 0;
		cy = 0;
		intraSpace = 1;
		interSpace = 3;

		sIndex = 0;
		isIntraSpace = false;
		
		byte temp[][] = 
		{
			{(byte)0x70, (byte)0x88, (byte)0xf8, (byte)0x88, (byte)0x88, (byte)0x88}, //A
			{(byte)0xf0, (byte)0x88, (byte)0xf0, (byte)0x88, (byte)0x88, (byte)0xf0}, //B
			{(byte)0xf0, (byte)0x88, (byte)0x80, (byte)0x80, (byte)0x88, (byte)0xf0}, //C
			{(byte)0xf0, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0xf0}, //D
			{(byte)0xf8, (byte)0x80, (byte)0xf8, (byte)0x80, (byte)0x80, (byte)0xf8}, //E
		
			{(byte)0xf8, (byte)0x80, (byte)0xf8, (byte)0x80, (byte)0x80, (byte)0x80}, //F
			{(byte)0xf0, (byte)0x88, (byte)0x80, (byte)0xb8, (byte)0x88, (byte)0xf0}, //G
			{(byte)0x88, (byte)0x88, (byte)0xf8, (byte)0x88, (byte)0x88, (byte)0x88}, //H
			{(byte)0xf8, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0xf8}, //I
			{(byte)0xf8, (byte)0x20, (byte)0x20, (byte)0xa0, (byte)0xa0, (byte)0x60}, //J
		
			{(byte)0x90, (byte)0xa0, (byte)0xc0, (byte)0xa0, (byte)0x90, (byte)0x88}, //K
			{(byte)0x80, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0xf8}, //L
			{(byte)0x88, (byte)0xd8, (byte)0xa8, (byte)0x88, (byte)0x88, (byte)0x88}, //M
			{(byte)0x88, (byte)0xc8, (byte)0xa8, (byte)0x98, (byte)0x88, (byte)0x88}, //N
			{(byte)0x70, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70}, //O
		
			{(byte)0xf0, (byte)0x88, (byte)0xf0, (byte)0x80, (byte)0x80, (byte)0x80}, //P
			{(byte)0x70, (byte)0x88, (byte)0x88, (byte)0xa8, (byte)0x98, (byte)0x78}, //Q
			{(byte)0xf0, (byte)0x88, (byte)0xf0, (byte)0x88, (byte)0x88, (byte)0x88}, //R
			{(byte)0x78, (byte)0x80, (byte)0x70, (byte)0x08, (byte)0x88, (byte)0x70}, //S
			{(byte)0xf8, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20}, //T
		
			{(byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x70}, //U
			{(byte)0x88, (byte)0x88, (byte)0x88, (byte)0x88, (byte)0x50, (byte)0x20}, //V
			{(byte)0x88, (byte)0x88, (byte)0x88, (byte)0xa8, (byte)0xa8, (byte)0xd8}, //W
			{(byte)0x88, (byte)0x50, (byte)0x20, (byte)0x50, (byte)0x88, (byte)0x88}, //X
			{(byte)0x88, (byte)0x88, (byte)0x50, (byte)0x20, (byte)0x20, (byte)0x20}, //Y
		
			{(byte)0xf8, (byte)0x10, (byte)0x20, (byte)0x40, (byte)0x80, (byte)0xf8},  //Z
			{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},  //' ' (space)
			{(byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x00, (byte)0x20},  //'!' (exclaimation)
			{(byte)0x00, (byte)0x08, (byte)0x00, (byte)0x00, (byte)0x08, (byte)0x00},  //':' (colon)
			{(byte)0x80, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x40, (byte)0x80},  //')' (right brace)
			{(byte)0x40, (byte)0xa0, (byte)0x40, (byte)0xaf, (byte)0x90, (byte)0x6f}  //'&' (amphersand)
			

		};
		chars = temp;
		
		preprocessString(s);
	}
	
	public void preprocessString(String s) {
		str = new int[s.length()];
		s = s.toUpperCase();
		char temp[] = s.toCharArray();
		for(int i=0; i<s.length(); i++) {
			if(s.charAt(i) == ' ') {
				//this should be removed as soon as all the 
				//characters, at least ascii are implemented
				str[i] = 26;
			}
			else if(s.charAt(i) == '!') {
				//this should be removed as soon as all the 
				//characters, at least ascii are implemented
				str[i] = 27;
			}
			else if(s.charAt(i) == ':') {
				//this should be removed as soon as all the 
				//characters, at least ascii are implemented
				str[i] = 28;
			}
			else if(s.charAt(i) == ')') {
				//this should be removed as soon as all the 
				//characters, at least ascii are implemented
				str[i] = 29;
			}
			else if(s.charAt(i) == '&') {
				//this should be removed as soon as all the 
				//characters, at least ascii are implemented
				str[i] = 30;
			}
			else
				str[i] = (int)((s.charAt(i)) - 65);
		}
	}
	
	public void reset() {
		sx = 0;
		sy = 0;
		cx = 0;
		cy = 0;
		intraSpace = 1;
		interSpace = 3;
		
		sIndex = 0;
		isIntraSpace = false;
	}
	
	public boolean[] getNextCol() {
		//byte temp[] = new byte[h/8 + 1];	//every byte is 8 bits :P; 
		boolean temp[] = new boolean[charHeight];
		if(isIntraSpace) {
			isIntraSpace = false;
		} else {
			
			for(int i=0; i<charHeight; i++) {
				//temp[i/8] = temp[i/8] | ( chars[str[sIndex]][i] & 1<<(8-cx%8) ) ;	//should work
				temp [i] = ( chars[str[sIndex]][i] & (1<<(7-cx%charWidth)) ) == 0 ? false:true;
				//System.out.println("i="+i + "|temp[i]=" + temp[i]+"|sIndex="+
				//sIndex +"|str[sIndex]=" + str[sIndex] + "|chars[...][i]="+chars[str[sIndex]][i]+"|cx="+cx);
			}
			cx++;
			if(cx%charWidth == 0) {
				sIndex++;
				isIntraSpace = true;
			}
			if(cx == w) {
				cx=0;	//will produce the effect of scrolling
				sIndex = 0;
			}
		}
		return temp;
	}
	/*
	public int getPrevRow(){
	}
	
	public int getNextCol(){
	}
	
	public int getPrevCol(){
	}*/
}
