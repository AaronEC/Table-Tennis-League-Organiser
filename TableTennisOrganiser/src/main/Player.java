package main;

import java.io.Serializable;

/**
 * Not yet implemented
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
