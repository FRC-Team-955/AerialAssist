package Core;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author raiderbot-4
 */
public class Test 
{
	private int i = 1;
	private Timer testTimer = new Timer();
	private boolean running = true;
	private boolean starting = true;
	private Drive drive;
	private Catapult cat;
	private Pickup pickup;
	
	public Test(Drive drive, Catapult cat, Pickup pickup)
	{
		this.drive = drive;
		this.cat = cat;
		this.pickup =  pickup;
	}
			
	/**
	 * Runs the test routine
	 */
	public void run()
	{
		if(starting)
		{
			testTimer.start();
			starting = false;
		}
		
		if(testTimer.get() > .5 && running)
		{
			switch(i) 
			{
				case 1:
					testTimer.reset();
					drive.setMotorLeft1(1);
					break;
				case 2: 
					testTimer.reset();
					drive.setMotorLeft1(0);
					drive.setMotorLeft2(1);
					break;
				case 3: 
					testTimer.reset();
					drive.setMotorLeft2(0);
					drive.setMotorLeft3(1);
					break;
				case 4: 
					testTimer.reset();
					drive.setMotorLeft3(0);
					drive.setMotorRight1(1);
					break;
				case 5: 
					testTimer.reset();
					drive.setMotorRight1(0);
					drive.setMotorRight2(1);
					break;
				case 6: 
					testTimer.reset();
					drive.setMotorRight2(0);
					drive.setMotorRight3(1);
					break;
				case 7: 
					testTimer.reset();
					drive.setMotorRight3(0);
					cat.testCatapult(1);
					break;
				case 8: 
					testTimer.reset();
					cat.testCatapult(0);
					pickup.testPickup(1);
					break;
				case 9: 
					testTimer.reset();
					pickup.testPickup(0);
					running = false;
					i = 1;
					break;
			}
			i+=1;
		}
	}
	
	/**
	 * Resets everything back to original 
	 */
	public void reset()
	{
		starting = true;
		running = true;
		i = 1;
	}
}