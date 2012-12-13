package com.wissen.telemedia.gui;

public interface UIViewListener {
	abstract public void setStandby(boolean state);
	abstract public void nextScreen();
	abstract public void reset();
	abstract public void wakeup();
	abstract public Session getSession();
	abstract public void endSession();
	abstract public void notifyActivity();
}
