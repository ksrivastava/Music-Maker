import javax.sound.midi.*; 
import java.io.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
  
public class MusicMaker {  
	
	final static int frameHeight = 300;
	final static int frameWidth = 300;
	int speed = 550;
	int beats = 2;
	static JFrame frame = new JFrame("My First Music Video");
	static MyDrawPanel panel;
	static JButton InsButton;
	Sequencer player;
	boolean start = true;
    public static void main(String [] args)  
    { 
    	//frame.setBackground(Color.yellow);
    	new MusicMaker().play();
  
    }  
    
    public void setUpGui() {
    	panel = new MyDrawPanel();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	InsButton = new JButton("Change Instrument");
    	InsButton.addActionListener(new InstrumentChangeListener());
    	frame.getContentPane().add(BorderLayout.SOUTH, InsButton);
    	
    	frame.add(panel);
    	frame.setSize(frameWidth, frameHeight);
    	frame.setVisible(true);
    }
      
    public void play()  
    {  
    	setUpGui();
    	
    		try  
    		{  
    			player = MidiSystem.getSequencer();  
    			player.open();  
        	
    			int[] eventIWant = {127};
    			player.addControllerEventListener(panel, eventIWant);
    			Sequence seq= new Sequence(Sequence.PPQ, 4);  
    			Track track = seq.createTrack(); 
    			int instrument = (int) (Math.random() * 128);
    			track.add(makeEvent(192, 1, instrument, 0, 5));
    			int r = 0;
    			for (int i = 5; i < 1000; i+=4) {

    				r = (int) ((Math.random() * 128));

    				track.add(makeEvent(144, 1, r, 100, i));

    				track.add(makeEvent(176, 1, 127, 0, i));

    				track.add(makeEvent(128, 1, r, 100, i+beats));
    			}

    			player.setSequence(seq);  
    			player.setTempoInBPM(speed);
    			player.start();  
    		}  
    		catch(Exception e)  
    		{  
    			e.printStackTrace();  
    		}  
    		
    	}


	
	public MidiEvent makeEvent(int comd, int chan, int note, int vel, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, note, vel);
			event = new MidiEvent(a, tick);
		}
		
		catch (Exception ex) { }
		
		return event;
	}
	
	class InstrumentChangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			player.stop();
			player.close();
			play();
			
		}
		
	}
	
	class MyDrawPanel extends JPanel implements ControllerEventListener {
		boolean msg = false;
		@Override
		public void controlChange(ShortMessage event) {
			// TODO Auto-generated method stub
			msg = true;
			repaint();
		} 
		
		public void paintComponent(Graphics g) {
			
			if (msg) {
				
				Graphics2D g2d = (Graphics2D) g;
				
				Color startColor = new Color (getRandom(255), getRandom(255), getRandom(255));
				Color endColor = new Color (getRandom(255), getRandom(255), getRandom(255));
				
				GradientPaint gradient = new GradientPaint(70, 70, startColor, 150, 150, endColor);
				g2d.setPaint(gradient);

				int height = getRandom(frameHeight/10) + 20;
				int width = getRandom(frameHeight/10) + 20;
				
				int x = getRandom(frameWidth - 50);
				int y = getRandom(frameHeight - 50);
				
			
				
			//	System.out.println(x + " and " + y);
				
				g2d.fillRect(x, y, width, height);
				
				msg = false;
			
			}
		}
		
		public int getRandom(int num) {
			return (int) (Math.random() * num);
		}
	}

}  
