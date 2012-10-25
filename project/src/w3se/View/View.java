package w3se.View;

import java.util.Observable;
import java.util.Observer;

abstract public class View implements Observer
{

	@Override
	abstract public void update(Observable o, Object arg);
	
}
