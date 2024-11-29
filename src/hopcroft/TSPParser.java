package hopcroft;

import java.io.*;
import java.util.*;

public class TSPParser {
	private TSPParser() {}
	
	public static Map<Integer, Map<Integer, Float>> parseTSP(String filePath) throws IOException {
		BufferedReader leitor = new BufferedReader(new FileReader(filePath));
		Map<Integer, Map<Integer, Float>> grafo = new HashMap<>();
		String linha;
		boolean secaoDePesoDeArestas = false;
		int cidade = 1;
		String[] pesos;

		while ((linha = leitor.readLine()) != null) {
			linha = linha.trim();
			if (linha.equals("EDGE_WEIGHT_SECTION") || linha.equals("NODE_COORD_SECTION") || linha.equals("DISPLAY_DATA_SECTION")) {
				secaoDePesoDeArestas = true;
				continue;
			}
			if (secaoDePesoDeArestas) {
				if (linha.equals("EOF"))
					break;

				pesos = linha.split("\\s+");
				for (String peso : pesos) {
					if (!grafo.containsKey(cidade)) {
						grafo.put(cidade, new HashMap<>());
					}
					int vizinho = grafo.get(cidade).size() + cidade + 1;
					if (vizinho <= grafo.size() + 58) {
						grafo.get(cidade).put(vizinho, Float.parseFloat(peso));
					}
				}
				cidade++;
			}
		}
		leitor.close();
		return grafo;
	}
}
