import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class guiTest {
	public static final int WIDTH=1240;
	public static final int HIGHT=680;
	public static JFrame frame,windows;
	public static Gallery gall ;
	JFileChooser fm = new JFileChooser();
	JFileChooser fi = new JFileChooser();
	JTextPane textName;
	public static String[] imageShow = new String[100] ;
	public static MyDoublyLinkedList gallList = new MyDoublyLinkedList();
	public static MyDoublyLinkedList imageBuffer = new MyDoublyLinkedList();
	public static File musicBuffer ;
	JScrollPane scrollPane ;
	JTextArea jtextArea ;
	JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiTest window = new guiTest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public guiTest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.BLACK);
		frame.setBounds(200, 0, 830, 730);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//สร้างปุ่มปิด-พับจอ
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				System.exit(0);
			}
		});
		lblX.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblX.setBounds(806, 13, 12, 12);
		frame.getContentPane().add(lblX);
		JLabel label = new JLabel("-");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setState(Frame.ICONIFIED);
			}
		});
		label.setFont(new Font("Tahoma", Font.BOLD, 26));
		label.setBounds(787, 13, 12, 12);
		frame.getContentPane().add(label);
		frame.setUndecorated(true);//ซ้อนขอบ
		JButton btnSave = new JButton("");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Sound msd;
				msd = new Sound(new File("./sound/mouse_hover.wav"));
				msd.play();
				btnSave.setIcon(new ImageIcon("./image/save_1.jpg"));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSave.setIcon(new ImageIcon("./image/save.jpg"));
			}
		});
		btnSave.setIcon(new ImageIcon("./image/save.jpg"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MyDoublyLinkedList CheckName;
				try {
					while(true){
						FileReader fileReader = new FileReader("./File/NameGalleryList.txt");
						BufferedReader bufferedReader = new BufferedReader(fileReader);
						CheckName = new MyDoublyLinkedList();
						String line =null ;
						while((line = bufferedReader.readLine())!=null){
							CheckName.insert(line);
						}
						bufferedReader.close(); 
						if(!CheckName.isEmpty()&&CheckName.findKey(textName.getText())){
							JOptionPane.showMessageDialog(null, "ชื่อซ้ำ");
							break;
						}
						if(fm.getSelectedFile()==null){
							JOptionPane.showMessageDialog(null, "กรุณาเลือกเพลง");
							break;
						}
						if(fi.getSelectedFile()==null){
							JOptionPane.showMessageDialog(null, "กรุณาเลือกภาพอย่างน้อย1ภาพ");
							break;
						}
						if(textName.getText().equals("")){
							JOptionPane.showMessageDialog(null, "กรุณาใส่ชื่อลิสต์");
							break;
						}



						MyDoublyLinkedList TMPimageBuffer=imageBuffer;
						gall = new Gallery(textName.getText(),TMPimageBuffer,musicBuffer);
						gallList.insert(gall);
						imageShow = new String[100] ;
						String fileName = "./File/"+textName.getText()+".txt";
						String fileNameList = "./File/NameGalleryList.txt";
						BufferedWriter bufferedWriter = null;
						BufferedWriter bufferedWriter2 = null;
						try {
							FileWriter fileWriter = new FileWriter(fileName,true);
							bufferedWriter = new BufferedWriter(fileWriter);
							bufferedWriter.write(musicBuffer.toString());
							bufferedWriter.newLine();
							TMPimageBuffer.findFirst();
							int cntLine=TMPimageBuffer.size;
							while(cntLine!=0){
								bufferedWriter.write(TMPimageBuffer.retrieve().toString());
								bufferedWriter.newLine();
								TMPimageBuffer.findNext();
								cntLine--;
							}

							FileWriter fileWriter2 = new FileWriter(fileNameList,true);
							bufferedWriter2 = new BufferedWriter(fileWriter2);
							bufferedWriter2.write(textName.getText());
							bufferedWriter2.newLine();
						}
						catch(IOException ex) {System.out.println( "Error writing to file '"+ fileName + "'"); }
						finally {bufferedWriter.close();bufferedWriter2.close();}

						textName.setText(null);
						imageBuffer = new MyDoublyLinkedList();
						System.out.println(gall.getName());
						jtextArea.setText(null);
						UpdatTextArea();
						break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		JButton btnPlayLast = new JButton("");
		btnPlayLast.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Sound msd;
				msd = new Sound(new File("./sound/mouse_hover.wav"));
				msd.play();
				btnPlayLast.setIcon(new ImageIcon("./image/watch_1.jpg"));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnPlayLast.setIcon(new ImageIcon("./image/watch.jpg"));
			}
		});
		btnPlayLast.setIcon(new ImageIcon("./image/watch.jpg"));
		btnPlayLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					gall = (Gallery) gallList.retrieve();

					gall.Image.findFirst();
					System.out.println(gall.getName()+"\n"+gall.getMusic().toString()+"\n"+gall.Image.retrieve().toString());
					for(int i=0;i<gall.Image.size();i++){
						imageShow[i] = gall.Image.retrieve().toString();
						gall.Image.findNext();
					}

					createwin();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JButton btnSlcImage = new JButton("");
		btnSlcImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Sound msd;
				msd = new Sound(new File("./sound/mouse_hover.wav"));
				msd.play();
				btnSlcImage.setIcon(new ImageIcon("./image/camera_1.jpg"));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSlcImage.setIcon(new ImageIcon("./image/camera.jpg"));
			}
		});
		btnSlcImage.setIcon(new ImageIcon("./image/camera.jpg"));
		btnSlcImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				try {

					fi.showOpenDialog(null);
					imageBuffer.insert(fi.getSelectedFile());
					lblNewLabel.setText("choose photo"+"["+imageBuffer.size+"]");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		});

		JLabel lblListName_1 = new JLabel(" name");
		lblListName_1.setForeground(Color.DARK_GRAY);
		lblListName_1.setFont(new Font("Egge Sans Bold", Font.PLAIN, 15));
		lblListName_1.setBounds(401, 613, 50, 20);
		frame.getContentPane().add(lblListName_1);

		JTextPane textCurName = new JTextPane();
		textCurName.setFont(new Font("Egge Sans Bold", Font.PLAIN, 13));
		textCurName.setBackground(Color.DARK_GRAY);
		textCurName.setForeground(Color.WHITE);
		textCurName.setBounds(269, 635, 322, 31);
		frame.getContentPane().add(textCurName);

		JLabel lblCreateFile = new JLabel("create file");
		lblCreateFile.setForeground(Color.WHITE);
		lblCreateFile.setFont(new Font("Egge Sans Bold", Font.BOLD, 18));
		lblCreateFile.setBounds(354, 108, 135, 46);
		frame.getContentPane().add(lblCreateFile);

		textName = new JTextPane();
		textName.setForeground(Color.WHITE);
		textName.setFont(new Font("Egge Sans Bold", Font.PLAIN, 15));
		textName.setBackground(Color.DARK_GRAY);
		textName.setBounds(325, 148, 178, 20);
		frame.getContentPane().add(textName);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("./image/board.gif"));
		lblNewLabel_4.setBounds(259, 119, 322, 57);
		frame.getContentPane().add(lblNewLabel_4);

		lblNewLabel = new JLabel("choose photo"+" ["+imageBuffer.size+"]");
		lblNewLabel.setFont(new Font("Egge Sans Bold", Font.PLAIN, 11));
		lblNewLabel.setBounds(68, 329, 135, 14);
		frame.getContentPane().add(lblNewLabel);
		btnSlcImage.setBounds(68, 216, 113, 107);
		frame.getContentPane().add(btnSlcImage);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("./image/pola2.png"));
		lblNewLabel_2.setBounds(61, 204, 127, 150);
		frame.getContentPane().add(lblNewLabel_2);
		btnPlayLast.setBounds(673, 213, 113, 114);
		frame.getContentPane().add(btnPlayLast);
		btnSave.setBounds(476, 214, 115, 107);
		frame.getContentPane().add(btnSave);


		JButton btnSlcMusic = new JButton("");
		btnSlcMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Sound msd;
				msd = new Sound(new File("./sound/mouse_hover.wav"));
				msd.play();
				btnSlcMusic.setIcon(new ImageIcon("./image/song_1.jpg"));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSlcMusic.setIcon(new ImageIcon("./image/song.jpg"));
			}
		});
		btnSlcMusic.setIcon(new ImageIcon("./image/song.jpg"));
		btnSlcMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					//เก็บไฟล์เพลง
					fm.showOpenDialog(null);
					musicBuffer=fm.getSelectedFile();

					//					File file = new File(musicBuffer.toString());
					//					String origPath = file.getPath();
					//					origPath.split(file.getName());
					//					System.out.println(file);
					//					System.out.println(origPath);
					//				    String destPath = origPath.replace(file.getPath(),"");
					//					System.out.println(destPath);
					//			    String destFile = file.getName();
					//			   File newfile =new File(destPath+destFile);
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}
		});

		JLabel lblSave = new JLabel("save");
		lblSave.setFont(new Font("Egge Sans Bold", Font.PLAIN, 11));
		lblSave.setBounds(515, 329, 38, 14);
		frame.getContentPane().add(lblSave);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("./image/pola2.png"));
		label_1.setBounds(470, 202, 127, 150);
		frame.getContentPane().add(label_1);

		JLabel lblRecentlyPlayed = new JLabel("   recently play");
		lblRecentlyPlayed.setFont(new Font("Egge Sans Bold", Font.PLAIN, 11));
		lblRecentlyPlayed.setBounds(673, 329, 113, 14);
		frame.getContentPane().add(lblRecentlyPlayed);

		JLabel lblChooseSong = new JLabel("choose song");
		lblChooseSong.setFont(new Font("Egge Sans Bold", Font.PLAIN, 11));
		lblChooseSong.setBounds(269, 329, 100, 14);
		frame.getContentPane().add(lblChooseSong);
		btnSlcMusic.setBounds(258, 214, 113, 107);
		frame.getContentPane().add(btnSlcMusic);

		JLabel lblTest = new JLabel("Memorable");
		lblTest.setFont(new Font("SweetheartScript-limited", Font.BOLD, 60));
		lblTest.setBounds(295, 26, 436, 82);
		frame.getContentPane().add(lblTest);

		JButton btnPlayCur = new JButton("play");
		btnPlayCur.setForeground(Color.WHITE);
		btnPlayCur.setFont(new Font("Egge Sans Bold", Font.PLAIN, 15));
		btnPlayCur.setBackground(Color.DARK_GRAY);
		btnPlayCur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String line = null;
					String music = null;
					FileReader fileReader = new FileReader("./File/"+textCurName.getText()+".txt");
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					music = bufferedReader.readLine();
					while((line = bufferedReader.readLine()) != null) {
						imageBuffer.insert(line);}
					bufferedReader.close();         
					gall = new Gallery(textCurName.getText(),imageBuffer,new File(music));
					gallList.insert(gall);
					gall.Image.findFirst();
					System.out.println(gall.getName()+"\n"+gall.getMusic().toString()+"\n"+gall.Image.retrieve().toString());
					for(int i=0;i<gall.Image.size();i++){
						imageShow[i] = gall.Image.retrieve().toString();
						gall.Image.findNext();
					}

					createwin();

					textCurName.setText(null);
					imageBuffer = new MyDoublyLinkedList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnPlayCur.setBounds(352, 679, 151, 31);
		frame.getContentPane().add(btnPlayCur);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(283, 387, 298, 180);
		frame.getContentPane().add(scrollPane);
		
				jtextArea = new JTextArea();
				jtextArea.setFont(new Font("Egge Sans Light", Font.PLAIN, 13));
				scrollPane.setViewportView(jtextArea);

		JLabel label_11 = new JLabel("");
		label_11.setIcon(new ImageIcon("./image/pola2.png"));
		label_11.setBounds(251, 202, 127, 150);
		frame.getContentPane().add(label_11);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon("./image/pola2.png"));
		label_2.setBounds(666, 202, 127, 150);
		frame.getContentPane().add(label_2);

		JLabel label_3 = new JLabel("");
		label_3.setBounds(61, 180, 127, 150);
		frame.getContentPane().add(label_3);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("./image/polawi2.png"));
		lblNewLabel_1.setBounds(269, 387, 309, 200);
		frame.getContentPane().add(lblNewLabel_1);
				
				JLabel lblNewLabel_5 = new JLabel("");
				lblNewLabel_5.setIcon(new ImageIcon("./image/polawi2.png"));
				lblNewLabel_5.setBounds(269, 353, 342, 269);
				frame.getContentPane().add(lblNewLabel_5);
				
						JLabel lblNewLabel_3 = new JLabel("");
						lblNewLabel_3.setFont(new Font("Egge Sans Bold", Font.PLAIN, 15));
						lblNewLabel_3.setIcon(new ImageIcon("./image/wa.jpg"));
						lblNewLabel_3.setBounds(0, 0, 830, 730);
						frame.getContentPane().add(lblNewLabel_3);
		UpdatTextArea();
	}
	public void UpdatTextArea(){
		try {
			String line=null;
			String line2[];

			FileReader fileReader = new FileReader("./File/NameGalleryList.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null)
			{
				if (!line.startsWith(">"))
					jtextArea.append(line + "\n");

			}
			bufferedReader.close();
		} catch (IOException e) {e.printStackTrace();}
	}

	public void createwin(){

		windows=new JFrame("Play");
		windows.setSize(WIDTH,HIGHT);
		windows.setResizable(false);
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.setLocationRelativeTo(null);
		windows.getContentPane().add(new Play());
		windows.setUndecorated(true);
		windows.setVisible(true);
	}
}
