package Observer_Estadisticas;

import java.util.HashMap;

public interface Observer {
	public void update(HashMap<String, Integer> estadisticas);
}
