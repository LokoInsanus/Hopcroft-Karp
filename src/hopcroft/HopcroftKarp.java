package hopcroft;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class HopcroftKarp {
	private static final int NIL = 0;
	private static final int INF = Integer.MAX_VALUE;

	private final Map<Integer, List<Integer>> adj;
	private final Map<Integer, Integer> pairU;
	private final Map<Integer, Integer> pairV;
	private final Map<Integer, Integer> dist;

	public HopcroftKarp(Map<Integer, List<Integer>> adj) {
		this.adj = adj;
		this.pairU = new HashMap<>();
		this.pairV = new HashMap<>();
		this.dist = new HashMap<>();
	}

	public boolean bfs() {
		Queue<Integer> queue = new LinkedList<>();
		for (Integer u : adj.keySet()) {
			if (pairU.getOrDefault(u, NIL) == NIL) {
				dist.put(u, 0);
				queue.add(u);
			} else {
				dist.put(u, INF);
			}
		}
		dist.put(NIL, INF);

		while (!queue.isEmpty()) {
			int u = queue.poll();
			if (dist.get(u) < dist.get(NIL)) {
				for (int v : adj.get(u)) {
					if (dist.get(pairV.getOrDefault(v, NIL)) == INF) {
						dist.put(pairV.getOrDefault(v, NIL), dist.get(u) + 1);
						queue.add(pairV.getOrDefault(v, NIL));
					}
				}
			}
		}
		return dist.get(NIL) != INF;
	}

	public boolean dfs(int u) {
		if (u != NIL) {
			for (int v : adj.get(u)) {
				if (dist.get(pairV.getOrDefault(v, NIL)) == dist.get(u) + 1 && dfs(pairV.getOrDefault(v, NIL))) {
					pairV.put(v, u);
					pairU.put(u, v);
					return true;
				}
			}
			dist.put(u, INF);
			return false;
		}
		return true;
	}

	public int hopcroftKarp() {
		int matching = 0;
		while (bfs()) {
			for (Integer u : adj.keySet()) {
				if (pairU.getOrDefault(u, NIL) == NIL && dfs(u)) {
					matching++;
				}
			}
		}
		return matching;
	}
}
