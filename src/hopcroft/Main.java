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
		String caminhoDoArquivo = "";
		File diretorio = new File("src/data/");
		File[] arquivos = diretorio.listFiles();
		File arquivoDeRetorno = new File("src/hopcroft/retorno.txt");
		FileWriter escritor = new FileWriter(arquivoDeRetorno);
		Map<Integer, Map<Integer, Float>> grafo;
		Map<Integer, List<Integer>> grafoBipartido;

		for (File arquivo : arquivos) {
			if (arquivo.getName().endsWith(".tsp")) {
				caminhoDoArquivo = "src/data/" + arquivo.getName();
				System.out.print(arquivo.getName().substring(0, arquivo.getName().lastIndexOf(".")) + ": ");
				escritor.write(arquivo.getName().substring(0, arquivo.getName().lastIndexOf(".")) + ": ");
			}
			grafo = TSPParser.parseTSP(caminhoDoArquivo);
			grafoBipartido = new HashMap<>();
			for (int no : grafo.keySet()) {
				grafoBipartido.put(no, new ArrayList<>(grafo.get(no).keySet()));
			}
			HopcroftKarp algoritmoDeHopcroft = new HopcroftKarp(grafoBipartido);
			int emparelhamentoMaximo = algoritmoDeHopcroft.hopcroftKarp();
			System.out.println(emparelhamentoMaximo);
			escritor.write(emparelhamentoMaximo + "\n");
		}
		escritor.close();
	}
}
