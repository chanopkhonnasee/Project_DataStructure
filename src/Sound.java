

import java.io.*;

import javax.sound.sampled.*;

//ควบคุมการเล่นเสียง
public class Sound {
	private Clip clips;
	private FloatControl gainControl;

	Sound(File file){
		
		try{
			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
												AudioSystem.NOT_SPECIFIED,
												16,2,4,
												AudioSystem.NOT_SPECIFIED,
												true);
			DataLine.Info info=new DataLine.Info(Clip.class, format);
			
			
				
				BufferedInputStream bs =new BufferedInputStream(new FileInputStream(file));
				AudioInputStream soundIn= AudioSystem.getAudioInputStream(bs);
				clips=(Clip)AudioSystem.getLine(info);
				clips.open(soundIn);
				gainControl =(FloatControl) clips.getControl(FloatControl.Type.MASTER_GAIN);//Represents a control for the overall gain on a line.
				
			
			
		}catch (UnsupportedAudioFileException ex){
			System.out.println("Unsupported Audio File");
		}catch(LineUnavailableException ex){
			System.out.println("Line Unvailable");
		}catch(IOException ex){
			System.out.println("IO ERROR"+ex);
		}
	}
	/*
	 * play sound
	 */
		public void play(){
			clips.stop();
			clips.setFramePosition(0);
			clips.start();
		}
		public void loop(){
			clips.stop();
			clips.setFramePosition(0);
			clips.loop(Clip.LOOP_CONTINUOUSLY);
			
		}
		
		
		public void stop(){
			clips.stop();
		}
		
		public boolean run(){
			if(clips.isRunning())
				return true;
			return false;
		}
		
}


