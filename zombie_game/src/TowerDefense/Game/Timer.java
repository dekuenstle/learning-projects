package TowerDefense.Game;

import me.lasergans.game.Node;

public class Timer extends Node
{
	private double i;
	private double interval;
	private boolean reset;
	private double duration;
	private boolean start;
	
	public boolean trigger;
	
	public Timer(double interval, double duration, boolean reset)
	{
		this.interval = interval;
		this.reset = reset;
		this.duration = duration;
	}
	public void update(double dt)
	{
		i += dt;
		
		//if (start)
		{
			if (reset)
			{
				if (i < interval && i > duration)
				{
					trigger = false;
				}
				if (i > interval)
				{
					trigger = true;
					i -= interval;
				}
			}
			else
			{
				if (i > interval)
				{
					trigger = true;
				}
			}
		}
	}
	public void reset()
	{
		trigger = false;
		i = 0;
	}
	public void start()
	{
		start = true;
	}
	
	public void setInterval(double interval)
		{ this.interval = interval; }
	
	public double getInterval()
		{ return interval; }

}
