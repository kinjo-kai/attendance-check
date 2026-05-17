package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CheckResult;

public class SummaryService {

	public Map<String, int[]> summarize(List<CheckResult> results){
		
		Map<String, int[]> map = new HashMap<>();
		
		for(CheckResult r: results) {
			
			String id = r.getEmployeeId();
			
			if(!map.containsKey(id)) {
				map.put(id, new int[3]);
			}
			
			int[] data = map.get(id);
			
			//出勤日数
			data[0]++;
			
			//遅刻回数
			if("遅刻".equals(r.getStatus())) {
				data[1]++;
			}
			
			//エラー回数
			if("エラー".equals(r.getStatus())) {
				data[2]++;
			}
		}
		
		return map;
	}	
}
