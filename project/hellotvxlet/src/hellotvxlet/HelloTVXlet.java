package hellotvxlet;

import java.awt.Font;
import java.util.Timer;
import javax.tv.xlet.*;
import org.bluray.ui.event.HRcEvent;
import org.dvb.event.EventManager;
import org.dvb.event.UserEvent;
import org.dvb.event.UserEventListener;
import org.dvb.event.UserEventRepository;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HStaticText;


public class HelloTVXlet implements Xlet, UserEventListener {

  int x=300;
          int ax=10;
  int ay=10;
  int ar=1;
  int kx,ky;
  int gameender;
  int score = 0;
 
        //text
  
     HStaticText text = new HStaticText("Alien DED",0,0,720,576);
    /* HStaticText SB = new HStaticText("Score: " + score,220,275,720,576);*/
     

     
     
  Font grootFont=new Font("Tiresias",Font.PLAIN,72);
         Sprite schip = new Sprite();
                             Sprite kogel = new Sprite();
                             boolean kogelactief=false;
      Sprite[][] alien=new Sprite[5][4];
            HScene scene=HSceneFactory.getInstance().getDefaultHScene();
    public HelloTVXlet() {
        
    }

    public void initXlet(XletContext context) {

      Sprite bgi = new Sprite();
      bgi.init(0,0,"stars.jpg");
      scene.add(bgi);

      kogel.init(-1000,0,"kogel.png");
      scene.add(kogel);
      
      for (int j=0;j<4;j++)
      {
          for (int i=0;i< alien.length;i++)
          {
              alien[i][j]=new  Sprite();
              alien[i][j].init(ax+i*120,ay+90*j,"image.png");
                    scene.add(alien[i][j]);
          }
      }

      schip.init(x,450,"ship.png");
      scene.add(schip);
     UserEventRepository rep=new UserEventRepository("naam");
     rep.addAllArrowKeys();
     rep.addKey(HRcEvent.VK_ENTER);
     EventManager.getInstance().addUserEventListener(this, rep);
        text.setFont(grootFont);
      scene.add(text);
     /*scene.add(SB);*/
     
     
  
      /*text.setBackground(Color.BLACK);
      text.setBackgroundMode(HVisible.BACKGROUND_FILL);
      scene.popToFront(text);*/
      scene.pushToBack(bgi);
      scene.setVisible(true);
         
      scene.validate();
 
      MijnTimerTask mtt=new MijnTimerTask(this);
      Timer t=new Timer();
      t.scheduleAtFixedRate(mtt, 0, 10);
      
      
      text.setVisible(false);
    }
public void run()
{

    
    // alien laten bewegen
    ax+=ar;
    
    if (ax>100) ar=-1;
    if (ax<0) ar=+1;
    
       for (int j=0;j<4;j++)
      {
          for (int i=0;i< alien.length;i++)
          {
           if (alien[i][j].weg==false)   alien[i][j].setLocation(ax+i*120,ay+90*j);
                 
          }
      }
    //kogel laten bewegen
    if (kogelactief)
    {
        ky-=6;
        if (ky<-kogel.getHeight()) kogelactief=false;
        kogel.setLocation(kx, ky);
        kogel.repaint();
    
    // collision detectiong
    // loop alle aliens af: (en kijk of de kogel ermee overlapt
           for (int j=0;j<4;j++)
      {
          for (int i=0;i< alien.length;i++)
          {
              if (alien[i][j].getBounds().intersects(kogel.getBounds()))
              {
                  System.out.println("BANG!!");
                  
                  alien[i][j].setLocation(-2000,0);
                     alien[i][j].weg=true;
                     
                   kogel.setLocation(-1000, ky);
                   kogelactief=false;
                   gameender++;
                   score = gameender*10;

                   System.out.println(score);
                   
              }
                 
          }
      }
    }
    
    
    //gameover screen
    if (gameender == 20){
      text.setVisible(true);
    }
}


    public void startXlet() {
    
    }

    public void pauseXlet() {
     
    }

    public void destroyXlet(boolean unconditional) {
     
    }

    public void userEventReceived(UserEvent arg0) {
        //direction buttons
        //left
       if (arg0.getCode()==HRcEvent.VK_LEFT)
       {
           x-=5;
           schip.setLocation(x, 450);
       }
       //right
        if (arg0.getCode()==HRcEvent.VK_RIGHT)
       {
           x+=5;
           schip.setLocation(x, 450);
       }
       
       //enter press = shoot
              if (arg0.getCode()==HRcEvent.VK_ENTER && !kogelactief)
       {
           kx=x+42;
           ky=450;
           kogelactief=true;
       }
    }
}
