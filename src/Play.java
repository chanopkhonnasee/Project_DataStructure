import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Play extends JPanel implements ActionListener{

	public static Timer loop;
	public static Sound sd;

	Graphics2D g2d;
	int cntImage=0;
	Image img1 = Toolkit.getDefaultToolkit().getImage(guiTest.imageShow[cntImage]);
	Image img2 = Toolkit.getDefaultToolkit().getImage("./image/W.png");
	int timecnt=0;
	
	public Play(){
		loop = new Timer(1, this);//ปรับเวลาเร่ง
		loop.start();
		sd = new Sound(new File(guiTest.gall.getMusic().toString()));
		sd.play();

	}
	public void paint(Graphics g){
		g2d=(Graphics2D)g;
		g2d.drawImage(img2, 0, 0,1240,680,null);
		g2d.drawImage(img1, 0, 0,1240,680,null);
		g2d.finalize();

	}
	public void actionPerformed(ActionEvent e) {
		if(!sd.run()){
			sd.stop();
			loop.stop();
			guiTest.windows.dispose();
		}
		timecnt++;
		if(timecnt%1000==0){//ปรับการนับต่อ1หน่วย
			cntImage=cntImage+1;
		 	
			if(guiTest.imageShow[cntImage]==null){
				cntImage=0;
			}
			//g2d.drawImage(img2, 0, 0,1240,680,null);
			img1 = Toolkit.getDefaultToolkit().getImage(guiTest.imageShow[cntImage]);
			//g2d.drawImage(img1, 0, 0,1240,680,null);
		}

		repaint();
	}
	




}
