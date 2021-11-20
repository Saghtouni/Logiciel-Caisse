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

public class outprinttest implements Printable {
	    
		private String printData;
		
	  
	    public outprinttest(String printDataIn, int nbrc) {
	        this.printData = printDataIn;
	       // this.nbrc = nbrc;
	   
	    }
	    
	   

		public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException  {
			return pageIndex;      
	        	
		}
	    		
	}

