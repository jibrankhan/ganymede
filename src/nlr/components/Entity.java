package nlr.components;

import java.util.List;

import org.newdawn.slick.SlickException;

/**
 * An aggregation of components. 
 * Specific components can be accessed via interface search. 
 * @author nicklarooy
 *
 */
public strictfp interface Entity extends Component {

	List<Component> getComponents();
	
	void add(Component component);
	
	void remove(Component component);
	
	<T> boolean is(Class<T> type);
	
	<T> T get(Class<T> type) throws SlickException;
}
