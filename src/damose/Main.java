package damose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jxmapviewer.painter.*;
import org.jxmapviewer.*;


public class Main{
    
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
        	public void run() {
        		try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
        		
            }
        });
    }
}