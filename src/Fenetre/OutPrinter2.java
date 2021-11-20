package Fenetre;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class OutPrinter2 implements Printable {

	private String printData;
    private String line;
    private static Font fnt = new Font("Arial",Font.PLAIN,11); 
  
  
    public OutPrinter2 (String printDataIn) {
        this.printData = printDataIn;
   
    }
    
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException  {      
        	
    	Graphics2D g2d = (Graphics2D) g;
    	int x = (int) pf.getImageableX();
    	int y = (int) pf.getImageableY();
        g2d.translate(x, y);
        FontMetrics metrics = g.getFontMetrics(fnt);
        int lineHeight = metrics.getHeight();
        if (pageIndex > 0) return Printable.NO_SUCH_PAGE;    
        
	    g.setColor(Color.black);      
	    BufferedReader br = new BufferedReader(new StringReader(printData));
        try {
			while ((line = br.readLine()) != null) {
				y += lineHeight;
				g.setFont(fnt);
				g.drawString(line, x, y);
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        
		return Printable.PAGE_EXISTS;    
		}  
}
