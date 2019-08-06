package qa.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class SortedDoubleMap {
	public static Map<String,Double> sort(Map<String,Double> map){
		if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();  
        List<Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>(map.entrySet());  
        Collections.sort(entryList, new MapDoubleComparator());  
        Iterator<Entry<String, Double>> iter = entryList.iterator();  
        Entry<String, Double> tmpEntry = null;  
        while (iter.hasNext()) {  
            tmpEntry = iter.next();  
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
        }  
        return sortedMap;  
	}
}
 //从大到小排序
class MapDoubleComparator implements Comparator<Map.Entry<String, Double>> {  

    @Override
   public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
	  return o1.getValue().compareTo(o2.getValue())*(-1); 
    }  
}  
