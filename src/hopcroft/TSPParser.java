package hopcroft;

import java.io.*;
import java.util.*;

public class TSPParser {
	public static Map<Integer, Map<Integer, Float>> parseTSP(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		Map<Integer, Map<Integer, Float>> graph = new HashMap<>();
		String line;
		boolean edgeWeightSection = false;
		int city = 1;
		String[] weights;

		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line.equals("EDGE_WEIGHT_SECTION") || line.equals("NODE_COORD_SECTION")
					|| line.equals("DISPLAY_DATA_SECTION")) {
				edgeWeightSection = true;
				continue;
			}
			if (edgeWeightSection) {
				if (line.equals("EOF"))
					break;

				weights = line.split("\\s+");
				for (String weight : weights) {
					if (!graph.containsKey(city)) {
						graph.put(city, new HashMap<>());
					}
					int neighbor = graph.get(city).size() + city + 1;
					if (neighbor <= graph.size() + 58) {
						graph.get(city).put(neighbor, Float.parseFloat(weight));
					}
				}
				city++;
			}
		}
		reader.close();
		return graph;
	}
}
