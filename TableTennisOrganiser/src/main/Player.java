package main;

import java.io.Serializable;

/**
 * This class contains the data structure for each player.
 * @author Aaron
 */
public class Player implements Serializable{
    
    private static final long serialVersionUID = 8604642400555460348L;
    private String name;
    
    public Player(String name)  {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }
}
