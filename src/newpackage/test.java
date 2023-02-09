/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class test extends JPanel{
        private ImageIcon feild = new ImageIcon(this.getClass().getResource("back2.jpg"));
	private ImageIcon start = new ImageIcon(this.getClass().getResource("play.png"));
        private ImageIcon exit = new ImageIcon(this.getClass().getResource("exit.jpg"));
	public JButton Bstart = new JButton(start);
        public JButton BExit1  = new JButton(exit);
	test(){
            setLayout(null);
            BExit1.setBounds(200,750, 180,71);
            add(BExit1);
            add(Bstart);
            Bstart.setBounds(420,750,180,71);
            add(Bstart);
	}
	public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(feild.getImage(),0,0,800,1000,this);
            g.setColor(Color.orange);
            g.setFont(new Font("Arial Rounded MT Bold",Font.CENTER_BASELINE,70));		
            g.drawString("MONKEY FRUITY",70,300);	
	}
    }
    

