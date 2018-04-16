
import java.util.Vector;
 
public class Fish
{
    public static void main(String[] args)
    {
 
        Exchange ex	 = new Exchange(5);
 
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    ex.produce();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    ex.consume();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
 
        t1.start();
        t2.start();
        
      
     
    }
 
    public static class Exchange
    {

    	private String[] food = {"Mleko","Kakao","Kanapki","Owoce"};
    	 
        Vector<String> v = new Vector<String>();
        int size;
        
    	public Exchange(int s) {
    		size = s;
    	}
    	
 
        public void produce() throws InterruptedException
        {
           
            while (true)
            {
                synchronized (this)
                {
                    while (v.size()==size)  wait();
 
                   System.out.println("Nowy produkt");
                    
                    v.add(food[(int)(Math.random()*(size-1))]);
 
                    notify();
                    Thread.sleep(500);
                }
            }
        }
 
        public void consume() throws InterruptedException
        {
            while (true)
            {
                synchronized (this)
                {
                    while (v.size()==0)  wait();
                    
                     String food = v.remove(v.size()-1);
 
                    System.out.println("Zjedzono " + food);
                    notify();
 
                    Thread.sleep(1000);
                }
            }
        }
    }
}
