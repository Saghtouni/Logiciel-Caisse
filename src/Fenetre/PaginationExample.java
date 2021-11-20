package Fenetre;

import java.awt.*;

import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.print.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class PaginationExample implements Printable{
	
	int[] pageBreaks;  
	private String printData;
    private String line;
    private static Font fnt = new Font("Arial",Font.PLAIN,11); 
    private int nbr = 0;
    private int nbrc = 0;
    ArrayList<String> textLines;
    String image;
    private CashControl cash;
	
	 public PaginationExample(ArrayList<String> textLines, int nbrc, String image) {
	        this.textLines = textLines;
	        this.nbrc = nbrc;
	        this.image = image;
	    }
   
	 
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
    	
   	

    	Graphics2D g2d = (Graphics2D) g;
    	int x = (int) pf.getImageableX();
    	int y = (int) pf.getImageableY();
        g2d.translate(x, y);
        FontMetrics metrics = g.getFontMetrics(fnt);
        int lineHeight = metrics.getHeight();
        
        if (pageBreaks == null) {
            int linesPerPage = (int)(pf.getImageableHeight()/lineHeight);

            int numBreaks = (textLines.size()-1)/linesPerPage;

            pageBreaks = new int[numBreaks];
            for (int b=0; b<numBreaks; b++) {
                pageBreaks[b] = (b+1)*linesPerPage; 
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

    		    g.setColor(Color.black);      

                // Just a safety net in case no margin was added.
    		int n = 0;
    		int x2 = 140 ;
    		int nbr2 = 14 + nbrc;
    	    int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
    	       
    	    int end   = (pageIndex == pageBreaks.length)
    	                         ? textLines.size() : pageBreaks[pageIndex];
            for (int line = start; line < end; line++) {
					
                	  
                     	if (pageIndex == 0) {
                     		 try {
                     				final BufferedImage image1 = ImageIO.read(new File(image));
                     				
                     				g.drawImage(image1, 30, 0,100,30, null);
                     			} catch (IOException e) {
                     				// TODO Auto-generated catch block
                     				e.printStackTrace();
                     			}
                     		 
                     	
					if(n < 6) {
	
						 if(n == 4) {
							y += lineHeight;
							Font fnt1 = new Font("Arial",Font.BOLD,11); 
							g.setFont(fnt1);
							g.drawString(textLines.get(line), 30, y);
							
						}
						else {
							y += lineHeight;
							g.setFont(fnt);
							
							g.drawString(textLines.get(line), 30, y);
						}
					}
					else if(n == 8) {
						 y += lineHeight;
						 Font fnt1 = new Font("Arial",Font.BOLD,14); 
						 g.setFont(fnt1);
						 g.drawString(textLines.get(line), 60, y);
					}
					
				/*	else if (n == 11) {
						g.setFont(fnt);
					    g.drawString(textLines.get(line), 140, y);
					}
					
					else if (n == 12) {
						g.setFont(fnt);
					    g.drawString(textLines.get(line), 180, y);
					}
					else if (n == 13) {
						y += lineHeight;
						g.setFont(fnt);
					    g.drawString(textLines.get(line), x, y);
					}
					else if ((n >= 14)  && (n < nbr2) ) {
						
						nbr++;
						if(((nbr % 3) == 0)) {
							y += lineHeight;
							g.setFont(fnt);
						    g.drawString(textLines.get(line), 0, y);
						    x2 = 140;
						}
						else {
						g.setFont(fnt);
					    g.drawString(textLines.get(line), x2, y);
					    x2 += 40;
						}
					}
					
					else if (n == nbr2 ) {
						y += lineHeight;
						g.setFont(fnt);
						
					    g.drawString(textLines.get(line), 100, y);
					}
					else if (n == nbr2 +5) {
						y += lineHeight;
						g.setFont(fnt);
					    g.drawString(textLines.get(line), 30, y);
					}
					
					else if (n == nbr2 +6) {
						y += lineHeight;
						g.setFont(fnt);
					    g.drawString(textLines.get(line), 60, y);
					}

					else if (n == nbr2 +7) {
						y += lineHeight;
						g.setFont(fnt);
					    g.drawString(textLines.get(line), 30, y);
					}*/

					else {
						y += lineHeight;
						g.setFont(fnt);
					    g.drawString(textLines.get(line), x, y);
					}
					
					n++;
				} 
                  
    		  }  
            nbr = 0;
		    return Printable.PAGE_EXISTS; 
    }
    }
