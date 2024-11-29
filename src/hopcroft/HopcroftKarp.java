package hopcroft;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class HopcroftKarp {
	private static final int NULO = 0;
	private static final int INFINITO = Integer.MAX_VALUE;

	private final Map<Integer, List<Integer>> adjacentes;
	private final Map<Integer, Integer> parU;
	private final Map<Integer, Integer> parV;
	private final Map<Integer, Integer> distancia;

	public HopcroftKarp(Map<Integer, List<Integer>> adjacentes) {
		this.adjacentes = adjacentes;
		this.parU = new HashMap<>();
		this.parV = new HashMap<>();
		this.distancia = new HashMap<>();
	}

	public boolean buscaEmLargura() {
		Queue<Integer> fila = new LinkedList<>();
		for (Integer u : adjacentes.keySet()) {
			if (parU.getOrDefault(u, NULO) == NULO) {
				distancia.put(u, 0);
				fila.add(u);
			} else {
				distancia.put(u, INFINITO);
			}
		}
		distancia.put(NULO, INFINITO);

		while (!fila.isEmpty()) {
			int u = fila.poll();
			if (distancia.get(u) < distancia.get(NULO)) {
				for (int v : adjacentes.get(u)) {
					if (distancia.get(parV.getOrDefault(v, NULO)) == INFINITO) {
						distancia.put(parV.getOrDefault(v, NULO), distancia.get(u) + 1);
						fila.add(parV.getOrDefault(v, NULO));
					}
				}
			}
		}
		return distancia.get(NULO) != INFINITO;
	}

	public boolean buscaEmProfundidade(int u) {
		if (u != NULO) {
			for (int v : adjacentes.get(u)) {
				if (distancia.get(parV.getOrDefault(v, NULO)) == distancia.get(u) + 1 && buscaEmProfundidade(parV.getOrDefault(v, NULO))) {
					parV.put(v, u);
					parU.put(u, v);
					return true;
				}
			}
			distancia.put(u, INFINITO);
			return false;
		}
		return true;
	}

	public int hopcroftKarp() {
		int emparelhamento = 0;
		while (buscaEmLargura()) {
			for (Integer u : adjacentes.keySet()) {
				if (parU.getOrDefault(u, NULO) == NULO && buscaEmProfundidade(u)) {
					emparelhamento++;
				}
			}
		}
		return emparelhamento;
	}
}
