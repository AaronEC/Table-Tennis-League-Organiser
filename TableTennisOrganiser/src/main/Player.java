package main;

import java.io.Serializable;

/**
 *<h1>Player Data Structure Class</h1>
 * This class contains the data structure for each player. Whilst this class
 * currently only holds one variable, it is created as a class for future
 * expandability. E.G it could be expanded to hold individual player stats etc.
 * @author  Aaron Cardwell 13009941
 * @version 1.0
 * @since 19/12/2020
 * @serial 8604642400555460348L
 */
public class Player implements Serializable{
    
    private static final long serialVersionUID = 8604642400555460348L;
    private String name;
    
    /**
     * Class constructor specifying name of <code>Player</code>
     * @param name 
     */
    public Player(String name)  {
        this.name = name;
    }
    
    /* Getters */
    
    String getName() {
        return this.name;
    }

    /* Setters */
    
    public void setName(String name) {
        this.name = name;
    }

}
