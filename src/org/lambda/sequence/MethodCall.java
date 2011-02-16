package org.lambda.sequence;

import org.lambda.sequence.gui.element.CallDrawable;

public class MethodCall
{
	private String shortName;
	private String longName;
	private String clazz;
	private String type;
	private String hash;
	private int thread;
	private boolean constructor;
	private String parent;
	private boolean in;
	private boolean closed = false;
	private String time;
	
	public MethodCall(String shortName, String longName, String clazz,
			String type, String hash, int thread, boolean constructor,
			String parent, boolean in, String time)
	{
		this.shortName = shortName;
		this.longName = longName;
		this.clazz = clazz;
		this.type = type;
		this.hash = hash;
		this.thread = thread;
		this.constructor = constructor;
		this.parent = parent;
		this.in = in;
		this.time = time;
	}
	
	public String toString()
	{
		String ret = "";
		ret += ""+shortName+" "+longName+" "+clazz+" "+type+" "+hash+" "+thread+" "+constructor+" "+parent+" "+in+" ";
		return ret;
	}
	/**
	 * @return the shortName
	 */
	public String getShortName()
	{
		return shortName;
	}
	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}
	/**
	 * @return the longName
	 */
	public String getLongName()
	{
		return longName;
	}
	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName)
	{
		this.longName = longName;
	}
	/**
	 * @return the clazz
	 */
	public String getClazz()
	{
		return clazz;
	}
	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(String clazz)
	{
		this.clazz = clazz;
	}
	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	/**
	 * @return the hash
	 */
	public String getHash()
	{
		return hash;
	}
	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}
	/**
	 * @return the thread
	 */
	public int getThread()
	{
		return thread;
	}
	/**
	 * @param thread the thread to set
	 */
	public void setThread(int thread)
	{
		this.thread = thread;
	}
	/**
	 * @return the constructor
	 */
	public boolean isConstructor()
	{
		return constructor;
	}
	/**
	 * @param constructor the constructor to set
	 */
	public void setConstructor(boolean constructor)
	{
		this.constructor = constructor;
	}
	/**
	 * @return the parent
	 */
	public String getParent()
	{
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent)
	{
		this.parent = parent;
	}
	/**
	 * @return the in
	 */
	public boolean isIn()
	{
		return in;
	}
	/**
	 * @param in the in to set
	 */
	public void setIn(boolean in)
	{
		this.in = in;
	}
	
	public boolean isClosing(MethodCall mc)
	{
		boolean result = true;
		result &= ((mc.in && !in) || (!mc.in && in));
		result &= mc.hash.equals(hash);
		result &= mc.longName.equals(longName);
		result &= mc.type.equals(type);
		return result;
	}
	/**
	 * @return the closed
	 */
	public boolean isClosed()
	{
		return closed;
	}
	/**
	 * @param closed the closed to set
	 */
	public void setClosed(boolean closed)
	{
		this.closed = closed;
	}

	/**
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time)
	{
		this.time = time;
	}
	
	
}
