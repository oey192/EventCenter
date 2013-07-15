package com.andoutay.eventcenter;

import java.util.LinkedList;
import java.util.Queue;

public class ECQueue
{
	private Queue<ECEvent> queue;
	
	ECQueue()
	{
		queue = new LinkedList<ECEvent>();
	}
	
	public void enQueue(ECEvent evt)
	{
		queue.add(evt);
	}
	
	public void delete(ECEvent evt)
	{
		queue.remove(evt);
	}
	
	public ECEvent deQueue()
	{
		return queue.poll();
	}
	
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	public Queue<ECEvent> getQueue()
	{
		return queue;
	}
}
