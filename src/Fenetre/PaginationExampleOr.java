package Fenetre;

import java.awt.*;

import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PaginationExampleOr implements Printable{
	
	 int[] pageBreaks;  
	 ArrayList<String> textLines = new ArrayList<>();
	 private int nbr = 0;
	 private int nbrc = 0;
	 String image;
	 int tmp = 0;
	
	 public PaginationExampleOr(ArrayList<String> textLines, int nbrc, String image) {
		    pageBreaks = null;
		    this.textLines = null;
	        this.textLines = textLines;
	        this.nbrc = nbrc;
	        this.image = image;
	    }
   
	 
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
    	
   	

        Font fnt = new Font("Arial", Font.PLAIN, 10);
        FontMetrics metrics = g.getFontMetrics(fnt);
        int lineHeight = metrics.getHeight();

        int supLign = nbrc - (nbrc / 3 ) -2;
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
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        
        int n = 0;
        int y = 0; 
       
		int x2 = 140 ;
		int nbr2 = 14 + nbrc;
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
       
        int end   = (pageIndex == pageBreaks.length)
                         ? textLines.size() : pageBreaks[pageIndex];
                        
                        // Font font = new Font("Serif", Font.PLAIN, 10);
         
        for (int line=start; line<end; line++) {    
        	if (pageIndex == 0) {
        		 try {
        				final BufferedImage image1 = ImageIO.read(new File(image));
        				
        				g.drawImage(image1, 30, 0,100,30, null);
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		 
        	}
        	
            if(line < 6) {
				
				if(line == 4) {
					y += lineHeight;
					Font fnt1 = new Font("Serif",Font.BOLD,11); 
					g.setFont(fnt1);
					g.drawString(textLines.get(line), 30, y);
					
				}
				else {
					y += lineHeight;
					g.setFont(fnt);
					
					g.drawString(textLines.get(line), 30, y);
				}
			}
			else if(line == 8) {
				 y += lineHeight;
				 Font fnt1 = new Font("Serif",Font.BOLD,14); 
				 g.setFont(fnt1);
				 g.drawString(textLines.get(line), 60, y);
			}
			
			
			else {
				y += lineHeight;
				g.setFont(fnt);
			    g.drawString(textLines.get(line), 0, y);
			    
			}
			
        }
        
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
        
    }



  
}