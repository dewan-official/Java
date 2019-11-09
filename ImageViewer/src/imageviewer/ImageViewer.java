package imageviewer;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageViewer extends JFrame{
    JLabel pic;
    int x = 0;
    
    //    Image Types
    static final String[] EXTENSIONS = new String[]{
        "jpg", "png", "gif" // and other formats you need
    };
    
    //    Image Directory Selection
    static private File getPath(){
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        f.showSaveDialog(null);
        return f.getSelectedFile();
    }
    
    //Images Path In Array
    File path = getPath();
    ArrayList<String> list = getImages(path);
    
    //    Collecting Only Image Path
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    
    // Collecting all files from given directory   
    public ArrayList<String> getImages(File dir){
        ArrayList<String> list = new ArrayList<String>();
        if (dir.isDirectory()) {
            
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                BufferedImage img = null;

                try {
                    img = ImageIO.read(f);
                    list.add(f.getName());
                }catch (IOException e) {
                    // handle errors here
                }
            }
        }
        return list;
    }
    
    // Showing Image by constractor   
    public ImageViewer(){
        super("Java SlideShow");
        JFrame frame = new JFrame("Frame");
        
        JButton btn2 = new JButton("Previous");
        JButton btn = new JButton("Next");
        
        pic = new JLabel();
        pic.setBounds(300, 0, 600, 700);
        
        btn2.setBounds(100, 325, 100, 50);
        btn.setBounds(1000, 325, 100, 50);
       

        //Call The Function SetImageSize
        SetImageSize(0);
               //set a timer
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                x += 1;
                if(x >= list.size() )
                    x = 0;
                SetImageSize(x);
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                x -= 1;
                if(x == -1 )
                    x = list.size()-1;
                SetImageSize(x);
            }
        });
        
        frame.add(pic);
        frame.add(btn);
        frame.add(btn2);
        frame.setLayout(null);
        frame.setSize(1200, 700);
        frame.getContentPane().setBackground(Color.decode("#bdb67b"));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    //function to resize the image 
    public void SetImageSize(int i){
        ImageIcon icon = new ImageIcon(path.toString().concat("/").concat(list.get(i)));
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(pic.getWidth(), pic.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        pic.setIcon(newImc);
    }
    
    // Main Function/Method   
    public static void main(String[] args){ 
        new ImageViewer();
    }
}