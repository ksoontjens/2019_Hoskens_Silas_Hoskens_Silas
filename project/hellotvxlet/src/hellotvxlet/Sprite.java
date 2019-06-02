/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;

import java.awt.Image;
import java.awt.MediaTracker;

import org.havi.ui.HIcon;
import org.havi.ui.HVisible;

/**
 *
 * @author Laptop
 */
public class Sprite extends HIcon {
  Image img;
  boolean weg=false;
    public Sprite()
    {
        super();
    }
    public void init(int x, int y, String filename)
    {
        img=this.getToolkit().getImage(filename);
        MediaTracker mt=new MediaTracker(this);
        mt.addImage(img, 1);
        try {
            mt.waitForAll();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.setBounds(x, y,img.getWidth(this), img.getHeight(this));
        this.setGraphicContent(img, HVisible.NORMAL_STATE);
        this.setBordersEnabled(false);
    }
    public void setXY(int x, int y)
    {
          this.setBounds(x, y,img.getWidth(this), img.getHeight(this));
          this.repaint();
    }
}
