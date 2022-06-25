package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	ImdbDAO dao;
	Graph<Director, DefaultWeightedEdge> grafo;
	List<Director> vertici;
	List<Adiacenza> archi;
	
	public Model()
	{
		dao = new ImdbDAO();
	}
	
	public String creaGrafo(int a)
	{
		grafo = new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		vertici = dao.getDirectorsVERTICI(a);
		Graphs.addAllVertices(grafo, vertici);
		
		archi = dao.getArchi(a);
		for(Adiacenza arco:archi)
		{
			Graphs.addEdgeWithVertices(grafo, arco.getD1(), arco.getD2(), arco.getPeso());
		}
		
		
		return "Grafo creato\n# VERTICI: " + grafo.vertexSet().size() + "\n# ARCHI: " + grafo.edgeSet().size();
	}

	public List<Director> getVertici() {
		return vertici;
	}
	
	public List<Adiacenza> getAdiacenti(Director d)
	{
		List<Adiacenza> adiacenti = new LinkedList<>();
		
		for(Director dir: Graphs.neighborListOf(grafo, d))
		{
			adiacenti.add(new Adiacenza(d, dir, (int) grafo.getEdgeWeight(grafo.getEdge(dir, d))));
		}
		
		Collections.sort(adiacenti);
		return adiacenti;
	}
	
	

}
