package util;

import java.util.*;

public class AirportLookup {

    Map<String, Integer> orderMap;
    List<String[]> lookup;

    public AirportLookup(Map<String, Integer> map, List<String[]> csv) {
        this.orderMap = map;
        this.lookup = csv;
    }

    public List<String[]> getLookup() {return lookup;}

    public Map<String, Integer> getMap() {return orderMap;}
}
