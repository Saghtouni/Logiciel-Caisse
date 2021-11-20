package Fenetre;

import java.awt.print.PrinterException;

import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class OutputPrinter implements Printable {
	    
		private String printData;
	    private String line;
	    private static Font fnt = new Font("Arial",Font.PLAIN,11); 
	    private int nbr = 0;
	    private int nbrc = 0;
	    private CashControl cash;
	    String image;
	    public OutputPrinter(String printDataIn, int nbrc, String image) {
	        this.printData = printDataIn;
	        this.nbrc = nbrc;
	        this.image = image;
	   
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
	    		int x2 = 140 ;
	    		int nbr2 = 14 + nbrc;
	                try {
	                	
						while ((line = br.readLine()) != null) {
							
							
							if(n < 6) {
								if(n == 0) {
									y += lineHeight;
									Font fnt1 = new Font("Arial",Font.BOLD,18); 
									g.setFont(fnt1);
									g.drawString(line, 30, y);
								 
								}
								else if(n == 4) {
									y += lineHeight;
									Font fnt1 = new Font("Arial",Font.BOLD,11); 
									g.setFont(fnt1);
									g.drawString(line, 30, y);
									
								}
								else {
									y += lineHeight;
									g.setFont(fnt);
									g.drawString(line, 30, y);
								}
							}
							else if(n == 8) {
								 y += lineHeight;
								 Font fnt1 = new Font("Arial",Font.BOLD,16); 
								 g.setFont(fnt1);
								 g.drawString(line, 60, y);
							}
							
							else if (n == 11) {
								g.setFont(fnt);
							    g.drawString(line, 140, y);
							}
							
							else if (n == 12) {
								g.setFont(fnt);
							    g.drawString(line, 180, y);
							}
							else if (n == 13) {
								y += lineHeight;
								g.setFont(fnt);
							    g.drawString(line, x, y);
							}
							else if ((n >= 14)  && (n < nbr2) ) {
								nbr++;
								if(((nbr % 3) == 0)) {
									y += lineHeight;
									g.setFont(fnt);
								    g.drawString(line, x, y);
								    x2 = 140;
								}
								else {
								g.setFont(fnt);
							    g.drawString(line, x2, y);
							    x2 += 40;
								}
								
							}
							
							/*else if (n == nbr2 ) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
							    g.drawString(line, 90, y);
							}
							else if (n == nbr2 +5) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
							    g.drawString(line, 30, y);
							}
							
							else if (n == nbr2 +6) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
							    g.drawString(line, 60, y);
							}*/
					
							else {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
							    g.drawString(line, x, y);
							}
							
							n++;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
	               // cash.setNbr(0);
	                System.out.println("NBRR: " +cash.getNbr());
	                nbr = 0;
	               // scanner.close();
	    		    return Printable.PAGE_EXISTS;    
	    		  }  
	}

