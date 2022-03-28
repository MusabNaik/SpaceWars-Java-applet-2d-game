import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;


public class EnemySS 
{
	URL url;
	Image img,imx;
	int x,y;
	int dy =1;
	int draw=1;
	int hit=0;//is alive 
	
	public int getHit()//check is alive
	{
		return hit;
	}
	
	public void setHit(int hit)
	{
		this.hit=hit;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public EnemySS()
	{
		
	}
	
	public EnemySS(URL url,Image img, Image imx,int x,int y)
	{
		this.url=url;
		this.img=img;
		this.imx=imx;
		this.x=x;
		this.y=y;
	}
	public void init()
	{
		
	}
	
	public void paint(Graphics g)
	{
		//if(draw == 1)
		{
			if(hit == 0)
			{
				g.drawImage(img, x, y, 50, 50, null);
			}
			//else
			{
				/*
				g.drawImage(imx, x, y, 50, 50, null);
				//draw=0;
				dy =  1;
				*/
				
			}
		}
		
	}
	
	public void update(App ap)
	{int a=1;
		 y +=dy;
		 if(y > ap.getHeight())
		 {	
			 if(a==1)
			 {ap.l--;
			  a=0;
			 }
			 hit=1;
		 }
	}
}
