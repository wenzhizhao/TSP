package Christofides;

import java.util.*;

public class oddDegree{
	public ArrayList<Vertex> findOddDegree(ArrayList<Edge> edges){
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		Map<Vertex, Integer> count = new HashMap<Vertex, Integer>();
		for(Edge e: edges){
			if(!count.containsKey(e.start)){
				count.put(e.start, 1);
			}
			else{
				count.remove(e.start);
			} 
				
			if(!count.containsKey(e.end)){
				count.put(e.end, 1);
			}
			else
				count.remove(e.end);
		}
		for(Map.Entry<Vertex, Integer> entry : count.entrySet()){
			if(entry.getValue()%2 != 0){
				result.add(entry.getKey());
				System.out.println("odd degree vetice:"+entry.getKey().key);
			}
		}
		return result;
	}

	
}