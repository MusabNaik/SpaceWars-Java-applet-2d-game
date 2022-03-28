import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;


public class bullet
{

	int pox;
	int poy;
	int y1=600;
	int y2=575;
	int dy=2;
	int bHit=0;
	
	URL url ;
	Image bImg ;//= getImage(url, "images/alienblaster.png");;;
	
	public int gety2()
	{
		return y1;
	}
	
	public void setpox(int x)
	{
		this.pox=x;
	}
	
	public void setpoy(int y)
	{
		this.poy=y;
	}
	
	public bullet()
	{
		
	}
	
	public bullet(int x,int y,URL url,Image i)
	{
		this.pox=x;
		this.poy=y;
		this.url=url;
		this.bImg = i; //getImage(url, "images/alienblaster.png");
	}
		
	public void paint(Graphics g)
	{
		g.setColor(Color.GREEN);
		//g.drawLine(this.pox,y1,this.pox,y2);
		g.drawImage(bImg,this.pox-9, y2,20,30, null);
		//g.drawLine(pox, y1, pox, y2);
	}
	
	public void update(EnemySS es)
	{
	
		{
			//pox=(int) (ap.getWidth()*.5);
			//poy= ap.getHeight()-75;
		
			//y1=poy;
			//y2=poy-25;
			
			y1-=dy;
			y2-=dy;
			
			if(((es.getY()+50) >y2) && ((es.getX() <pox) && (es.getX() +50 >pox) ))
			{
				es.setHit(1);
				this.bHit=1;
			}
			
		}
	}
	
}
