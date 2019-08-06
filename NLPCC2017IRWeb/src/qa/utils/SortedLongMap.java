package qa.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class SortedLongMap {
	public static Map<String,Long> sort(Map<String,Long> map){
		if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();  
        List<Entry<String, Long>> entryList = new ArrayList<Map.Entry<String, Long>>(map.entrySet());  
        Collections.sort(entryList, new MapLongComparator());  
        Iterator<Entry<String, Long>> iter = entryList.iterator();  
        Entry<String, Long> tmpEntry = null;  
        while (iter.hasNext()) {  
            tmpEntry = iter.next();  
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
        }  
        return sortedMap;  
	}
}
 //从大到小排序
class MapLongComparator implements Comparator<Map.Entry<String, Long>> {  

    @Override
   public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
	  return o1.getValue().compareTo(o2.getValue())*(-1); 
    }  
}  
