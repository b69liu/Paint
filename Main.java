// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

/**
 *  Two views with integrated controllers.  Uses java.util.Observ{er, able} instead
 *  of custom IView.
 */

import javax.swing.*;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem; 
import javax.swing.JToggleButton;	
import java.awt.geom.*;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.awt.Color;

public class Main {

	public static void main(String[] args){	
		JFrame frame = new JFrame("Paint");
                frame.setMinimumSize(new Dimension(400, 400));
		
		// create Model and initialize it
		Model model = new Model();
		

                //add winlinstener resize
                frame.addComponentListener(new ComponentAdapter() 
                {  
                         public void componentResized(ComponentEvent evt) {
                             Component c = (Component)evt.getSource();
                             model.incrementCounter();
                        }
                });


		// create View, tell it about model (and controller)
		View view = new View(model);
		// tell Model about View. 
		model.addObserver(view);
		
		// create second view ...
		View2 view2 = new View2(model);
		model.addObserver(view2);
		

		// create down view ...
		Viewd viewd = new Viewd(model);
                viewd.setPreferredSize(new Dimension(400,60));
		model.addObserver(viewd);

                // create menu bar
                JMenuBar mb = new JMenuBar();
                JMenu fl = new JMenu("File");
                JMenuItem nw = new JMenuItem("New");
                JMenuItem sv = new JMenuItem("Save");
                JMenuItem ld = new JMenuItem("Load");
                JMenuItem et = new JMenuItem(new AbstractAction("Exit") {
                                                public void actionPerformed(ActionEvent e) { System.exit(0);   }
});

                nw.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                model.recnum=0;
                                model.index=0;
                                model.incrementCounter();
                
                            }
                });


 //http://blog.csdn.net/yahohi/article/details/6924732
                sv.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                JFileChooser fs = new JFileChooser();
                                fs.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);
                                fs.showSaveDialog(null);
                                File file2 = fs.getSelectedFile();
                                String pa = file2.getAbsolutePath()+"\\paint.save"; 
                                System.out.println("save: "+pa);
                                try{
                                   FileWriter out = new FileWriter(pa,false);
                                   out.write(model.recnum+"\n");                   //recnum
                                   out.write(model.index+ "\n");                   //index
                                   for(int i=0;i<model.recnum;++i){
                                       int len=model.record[i].length;
                                       int wid=model.record[i].wid;
                                       out.write(len+"\n");                        //length of a line
                                       out.write(wid+"\n");                        //thinckness of a line
                                       int r=model.record[i].linecolor.getRed();   //rgb alpha
                                       int g=model.record[i].linecolor.getGreen();
                                       int b=model.record[i].linecolor.getBlue();
                                       int a=model.record[i].linecolor.getAlpha();
                                       out.write(r+"\n");
                                       out.write(g+"\n");
                                       out.write(b+"\n");
                                       out.write(a+"\n");
                                       for(int j=0;j<len;++j){
                                          out.write(model.record[i].points[j].x+"\n");  //x
                                          out.write(model.record[i].points[j].y+"\n");  //y
                                       }//endforj
                                   }//endfori
                                   out.close();
                                }catch(Exception e2){}
                
                            }
                });
//http://zhidao.baidu.com/question/197899163.html?qbl=relate_question_0&word=java%20%B6%C1%C8%A1%CA%FD%D7%D6
                ld.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                JFileChooser fo = new JFileChooser();
                                fo.setFileFilter(new javax.swing.filechooser.FileFilter() {
                                     public boolean accept(File f) {
                                         if(f.getName().endsWith(".save")||f.isDirectory()){
                                                 return true;
                                         }else{
                                                 return false;
                                         }//endif
                                     }
                                     public String getDescription() {
                                         return "Save File(*.save)";
                                     }
                                });
                                fo.showOpenDialog(null);
                                File file = fo.getSelectedFile();
                                int[] nums=null;                    //used to stored shifted integers
                                ArrayList<String> list=new ArrayList<String>();
                                if(file != null){
                                   try{
                                     String path = file.getAbsolutePath();
                                     System.out.println("load: "+path);
                                     FileReader fr =new FileReader(path);
                                     BufferedReader bw = new BufferedReader(fr);
                                     String templine = null;
                                     while((templine=bw.readLine())!=null){  //read each line into list
                                          list.add(templine);
                                     }//endwhile
                                     bw.close();
                                   }catch(Exception e3){}
                                int lsize=list.size();           // # of ints
                                nums= new int[lsize];
                                for(int z=0;z<lsize;++z){        //convert strings(list) to ints(nums)
                                     String temp = (String)list.get(z);
                                     nums[z]=Integer.parseInt(temp);
                                }//endfor
                                int real_recnum=nums[0];
                                int real_index=nums[1];

                                int readindex=2;
                                model.recnum=0;
                                model.index=0;
                                model.sldindex=0;
                                for(int i2=0;i2<real_recnum;++i2){
                                     int llen=nums[readindex];
                                     ++readindex;
                                     int lwid=nums[readindex];
                                     ++readindex;
                                     int lr=nums[readindex];
                                     ++readindex;
                                     int lg=nums[readindex];
                                     ++readindex;
                                     int lb=nums[readindex];
                                     ++readindex;
                                     int la=nums[readindex];
                                     ++readindex;
                                     int x=nums[readindex];
                                     ++readindex;
                                     int y=nums[readindex];
                                     ++readindex;
                                     model.addrec(x,y,'s',new Color(lr,lg,lb,la),lwid);  //virtual press
                                     ++model.index;
                                     ++model.sldindex;
                                   for(int j2=0;j2<llen-1;++j2){
                                     x=nums[readindex];
                                     ++readindex;
                                     y=nums[readindex];
                                     ++readindex;
                                     model.record[model.index-1].addpoint(x,y);   //virtual drag and release
                                   }//endforj2
                                   model.record[model.index-1].process=false;
                               }//endfori2
                               model.sldindex=model.index;
                               model.incrementCounter();
                               }//endif null?

                            }
                });


                fl.add(nw);
                fl.add(sv);
                fl.add(ld);
                fl.add(et);
                mb.add(fl);
                JMenu vw = new JMenu("View");
                vw.add(new JToggleButton(                   //source: http://stackoverflow.com/questions/8065571/change-state-of-toggle-button-from-another-button
                            new AbstractAction("Size"){
                               public void actionPerformed(ActionEvent e) {
                                  AbstractButton bt=(AbstractButton)e.getSource();
                                  if(bt.isSelected()){
                                        bt.setText("Full");
                                       model.freesize=true;
			               model.incrementCounter();
                                  }else{
                                        bt.setText("Fit ");
                                       model.freesize=false;
			               model.incrementCounter();
                                  }//endif

                               }
                            }
                       ));
                mb.add(vw);
                frame.setJMenuBar(mb);



		// let all the views know that they're connected to the model
		model.notifyObservers();
		
		// create the window
		JPanel p = new JPanel();
                p.setMinimumSize(new Dimension(600,500));
                p.setLayout(new BorderLayout(5,5));
		frame.getContentPane().add(p);
		p.add("West",view);

//add scrollbar
                model.scp=new JScrollPane(view2);
                model.scp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                model.scp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                model.scp.setBounds(0, 0, 400, 500);
                view2.setPreferredSize(new Dimension(400, 500));

		p.add("Center",model.scp);
		p.add("South",viewd);
		frame.setPreferredSize(new Dimension(600,500));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
//add thread to viewd
                Thread thread = new Thread(viewd);
                thread.start();
	} 




}
