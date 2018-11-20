package at.fhv.sportsclub.model.security;

import java.io.Serializable;
import java.util.HashMap;

/*
      Created: 18.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public enum AccessLevel implements Serializable {
    READ, WRITE, SPECIAL, NONE;

    private static final HashMap<String, AccessLevel> accessLevelByName = new HashMap<>();

    static {
        for(AccessLevel l : AccessLevel.values()){
            AccessLevel.accessLevelByName.put(l.toString(), l);
        }
    }

    public static AccessLevel getByStr(String lvl){
        return accessLevelByName.getOrDefault(lvl.toUpperCase(), NONE);
    }
}
