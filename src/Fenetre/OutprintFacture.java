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

import javafx.collections.ObservableList;

public class OutprintFacture implements Printable {
	    
		private String printData;
	    private String line;
	    private static Font fnt = new Font("Arial",Font.PLAIN,11); 
	    private int nbr = 0;
	    private int nbrc = 0;
	    private CashControl cash;
	    BufferedReader br;
	  
	    public OutprintFacture(String printDataIn, int nbrc) {
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
	    		int nbr2 = 22 + nbrc;
	                try {
	                	
						while ((line = br.readLine()) != null) {
							
							
							if(n < 5) {
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
									System.out.println(line);
									g.drawString(line, 30, y);
								}
							}
							else if(n == 6) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
								g.drawString(line, 60, y);
							}
							else if(n == 7) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
								g.drawString(line, 60, y);
							}
							else if(n == 8) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
								g.drawString(line, 60, y);
							}
							else if(n == 9) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
								g.drawString(line, 60, y);
							}
							else if(n == 10) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
								g.drawString(line, 60, y);
							}
							else if(n == 11) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
								g.drawString(line, 60, y);
							}
							else if(n == 12) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
								g.drawString(line, 60, y);
							}
							else if(n == 14) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
								g.drawString(line, x, y);
							}
							
							else if(n == 15) {
								 y += lineHeight;
								 Font fnt1 = new Font("Arial",Font.BOLD,12); 
								 g.setFont(fnt1);
								 g.drawString(line, 60, y);
							}
							
							else if (n == 17) {
								g.setFont(fnt);
							    g.drawString(line, x, y);
							    System.out.println("TEXT 11:" +line);
							}
							
				
							else if ((n >= 19)  && (n < nbr2) ) {
								nbr++;
								if(((nbr % 3) == 0)) {
									y += lineHeight;
									g.setFont(fnt);
									System.out.println(line);
								    g.drawString(line, x, y);
								    x2 = 100;
								}
								else {
								g.setFont(fnt);
								System.out.println(line);
							    g.drawString(line, x2, y);
							    x2 += 70;
								}
								
								System.out.print("nbr: "+ nbr + " cash :" + nbrc );
							}
							
							else if (n == nbr2 ) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
							    g.drawString(line, 100, y);
							}
							else if (n == nbr2 +6) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
							    g.drawString(line, 60, y);
							}/*
							
							else if (n == nbr2 +6) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
							    g.drawString(line, 60, y);
							}
					
							else if (n == nbr2 +7) {
								y += lineHeight;
								g.setFont(fnt);
								System.out.println(line);
							    g.drawString(line, 30, y);
							}
					*/
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

