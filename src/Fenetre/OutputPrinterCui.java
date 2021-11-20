package Fenetre;

import java.awt.print.PrinterException;

import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.IOException;
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
import java.util.Scanner;

public class OutputPrinterCui implements Printable {
	    
		private String printData;
	    private String line;
	    private static Font fnt = new Font("Arial",Font.PLAIN,11); 
	    private int nbr = 0;
	    private int nbrc = 0;
	    private CashControl cash;
	  
	    public OutputPrinterCui(String printDataIn, int nbrc) {
	        this.printData = printDataIn;
	        this.nbrc = nbrc;
	   
	    }
	    
	   

		public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException  {      
	        	
	    	Graphics2D g2d = (Graphics2D) g;
	    	int x = (int) pf.getImageableX();
	    	int y = (int) pf.getImageableY();
	        g2d.translate(x, y);
	        FontMetrics metrics = g.getFontMetrics(fnt);
	        int lineHeight = metrics.getHeight();
	    		    // pageIndex 0 to 4 corresponds to page numbers 1 to 5. 
	    		    if (pageIndex > 0) return Printable.NO_SUCH_PAGE;    
	    		         
	    		    g.setColor(Color.black);      
	    		    BufferedReader br = new BufferedReader(new StringReader(printData));
	                // Just a safety net in case no margin was added.
	    		int n = 0;
	    		int x2 = 100 ;
	    		int nbr2 = 14 + nbrc;
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
	                nbr = 0;
	    		    return Printable.PAGE_EXISTS;    
	    		  }  
	    		
	}

