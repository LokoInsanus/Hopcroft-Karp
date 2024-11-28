package hopcroft;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {
		String filePath = "";
		File directory = new File("src/data/");
		File[] files = directory.listFiles();
		File retorno = new File("src/hopcroft/retorno.txt");
		FileWriter writer = new FileWriter(retorno);
		Map<Integer, Map<Integer, Float>> graph = new HashMap<>();
		Map<Integer, List<Integer>> bipartiteGraph = new HashMap<>();

		for (File f : files) {
			if (f.getName().endsWith(".tsp")) {
				filePath = "src/data/" + f.getName();
				System.out.print(f.getName().substring(0, f.getName().lastIndexOf(".")) + ": ");
				writer.write(f.getName().substring(0, f.getName().lastIndexOf(".")) + ": ");
			}
			graph = TSPParser.parseTSP(filePath);
			bipartiteGraph = new HashMap<>();
			for (int u : graph.keySet()) {
				bipartiteGraph.put(u, new ArrayList<>(graph.get(u).keySet()));
			}
			HopcroftKarp hopcroftKarp = new HopcroftKarp(bipartiteGraph);
			int maxMatching = hopcroftKarp.hopcroftKarp();
			System.out.println(maxMatching);
			writer.write(maxMatching + "\n");
		}
		writer.close();
		//Marcelo
	}
}