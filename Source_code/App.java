/*------------Space Wars-------------
 * Created By Musab Naik
 * Project Started->3/08/2013 9:37 PM
 */

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class App extends Applet implements Runnable, KeyListener
{
	int x[]={125,105,145};
	int pox;
	int poy;
	int y[]={175,205,205};
	int y1=175;
	int y2=150;
	int dy=10;
	int dpox=10;
	int rX;		 //random x of EnemyShip
	int rY;		 //random y of EnemyShip
	int reSD=500;//re-spawn Distance
	int nES=7;   //no.of Enemy Ship
	int l=5;	 //no.of lives
	int score=0; //score
	int AppStart=0;
	ArrayList <Integer> keysDown;
	ArrayList <bullet> Bullets;
	ArrayList <EnemySS> esArr;
	int sp=0;
	int stp=0;
	int i;
	Image shp,bg,bg_END,idG,bImg,esi,imx;
	URL url;
	Graphics dG;
	
	bullet b;
	EnemySS es; //EnemyShip class object
	
	
	Random rd =new Random();
	
	int delX[]={150,300};
	
	public void init()
	{
		setSize(500,700);
		pox=(int) (getWidth()*.5);
		poy= getHeight()-75;
		x[0]=pox;
		x[1]=pox-20;
		x[2]=pox+20;
		y[0]=poy;
		y[1]=y[2]=poy+30;
		y1=poy;
		y2=poy-25;
		addKeyListener(this);
		
		keysDown = new ArrayList<Integer>();
		Bullets = new ArrayList<bullet>();
		esArr = new ArrayList<EnemySS>();
		
		url= getDocumentBase();
		shp = getImage(url, "images/alienblaster.png");
		bg = getImage(url, "images/bg.png");
		bg_END = getImage(url, "images/bg_END.png");
		bImg = getImage(url, "images/bulImg.png");
		esi = getImage(url,"Images/EnemyShip.png");
		imx = getImage(url,"Images/Explosion.png");
		
		
		
		for(int del=0;del<nES;del++)
		{
			rX=rd.nextInt(450);
			rY=rd.nextInt(reSD)*-1;
			es =new EnemySS(url,esi,imx,rX,rY);
			esArr.add(es);
		}
	}
	
	public void start()
	{
		b =new bullet();
		Thread th= new Thread(this);
		th.start();
	}
	
	public void stop()
	{
		Thread t= new Thread(this);
		t.destroy();
	}
	
	public void paint(Graphics g)
	{
		x[0]=pox;
		x[1]=pox-20;
		x[2]=pox+20;
		y[0]=poy;
		y[1]=y[2]=poy+30;
		
		g.setColor(Color.GREEN);
		g.drawImage(bg, 0, 0,getWidth(),getHeight(), this);
		g.drawImage(shp, x[0]-30, y[0],60,60, this); //MainShip
		//g.fillPolygon(x,y,3);

		//---------------------------------------------------------
		g.setColor(Color.RED);
		g.setFont(new Font("Serif", Font.BOLD,16));
		g.drawString("Score:"+Integer.toString(score), 25, 25);
		g.drawString("Lives:"+Integer.toString(l), 25, 45);
		//---------------------------------------------------------

				
		es.paint(g); //EnemyShip
		
		for(int del=0;del<esArr.size();del++)
		{
			es =esArr.get(del);
			es.paint(g);
		}
		
		//g.drawLine(pox, y1, pox, y2);
		for(i=0;i<Bullets.size();i++)
		{
		if(sp==1 )
		{	
			b=Bullets.get(i);
			if( b.gety2() >0 && b.bHit==0)
			{
			 b.paint(g);
			 stp = 0;
			}
			else
			{
				//sp=0;
				//b =new bullet();
				Bullets.remove(0);
			}
		}
		}
		
		if(l<=0)
		{
			g.setFont(new Font("Serif", Font.BOLD,35));
			g.drawString("GAME OVER", 150, 340);
			g.drawString("Score:"+Integer.toString(score), 190, 390);
			
		}
	}
	
	public void update(Graphics g) //Double Buffer
	{
		if(idG == null)
		{
			idG = createImage(this.getSize().width, this.getSize().height);
			dG = idG.getGraphics();
		}
		
		dG.setColor(getBackground());
		dG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		dG.setColor(getForeground());
		paint(dG);
		
		g.drawImage(idG, 0, 0, this);
				
	}
		
	public void run() 
	{
		while(true)
		{//System.out.println(l);
		 System.out.println(score);
			if(l<=0)
			{
				stop();
			}
				
			for(i=0;i<Bullets.size();i++)
			{
				b=Bullets.get(i);
				for(int j=0;j<=esArr.size();j++)
				{
					try
					{
						es=esArr.get(j);
					}
					catch(Exception e)
					{}
					b.update(es);
				}
			}
			
			for(int j=0;j<=esArr.size();j++)
			{
				try
				{
					es=esArr.get(j);
				}
				catch(Exception e)
				{}
				es.update(this);
				if(es.getHit()==1)
				{
					score+=10;
					try
					{
						esArr.remove(j);
						rX=rd.nextInt(450);
						rY=rd.nextInt(reSD)*-1;
						
						esArr.add(new EnemySS(url,esi,imx,rX,rY));
					}
					catch(Exception e)
					{}
				}
			}
			
			repaint();
			
			try
			{
				Thread.sleep(17);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	public void moveL()
	{
		if((pox-20)!=0)
		{
			pox -=dpox;
		}
		
	}
	
	public void moveR()
	{
		if((pox+20)<getWidth())
		{
			pox +=dpox;
		}
	}
	
	public void shoot()
	{
		sp=1;
		Bullets.add(new bullet(x[0],y[0],url,bImg));
		//System.out.println(Bullets.size());
		
		//b.setpox(x[0]);
		//b.setpoy(y[0]);
		
		//b.update(this);
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(!keysDown.contains(e.getKeyCode()))
			keysDown.add(new Integer (e.getKeyCode()));
		
		if(keysDown.contains(KeyEvent.VK_RIGHT))
		{
			moveR();
		}
		
		if(keysDown.contains(KeyEvent.VK_LEFT))
		{
			moveL();
		}
		
		if(keysDown.contains(KeyEvent.VK_SPACE))
		{
			shoot();
		}
		
		if(keysDown.contains(KeyEvent.VK_A))
		{
			System.out.println("A pressed");
			AppStart=1;
		}
		
		/*
		if(keysDown.contains(KeyEvent.VK_V))
		{
			count();
		}
		*/
	}

	/*
	public void count()
	{
		//System.out.println(esArr.size());
		System.out.println(l);
	}
	*/
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		keysDown.remove(new Integer (e.getKeyCode()));
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
	
	
}
