package damose;

import java.awt.*;


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