package qa.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class SortedIntMap {
	public static Map<String,Integer> sort(Map<String,Integer> map){
		if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();  
        List<Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());  
        Collections.sort(entryList, new MapIntegerComparator());  
        Iterator<Entry<String, Integer>> iter = entryList.iterator();  
        Entry<String, Integer> tmpEntry = null;  
        while (iter.hasNext()) {  
            tmpEntry = iter.next();  
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
        }  
        return sortedMap;  
	}
}
 //从大到小排序
class MapIntegerComparator implements Comparator<Map.Entry<String, Integer>> {  

    @Override
   public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
	  return o1.getValue().compareTo(o2.getValue())*(-1); 
    }  
}  
