package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This class tracks interactions between miRNA and genes, allowing queries
 * of either to find matches. 
 * 
 * @author Ross Jones
 * @author Weiss Laboratory, MIT
 * @version Last updated 2015-06-30
 */
public class Mapper {
	
	// --- Fields --- //
	
	private Map<String, Collection<String>> mirMap;
	private Map<String, Collection<String>> geneMap;
	
	
	// --- Public Methods --- //
	
	/**
	 * Constructs a Mapper with mir2gene and gene2mir maps.
	 */
	public Mapper() {
		
		mirMap = scanMapFile("mirMap.txt");
		geneMap = scanMapFile("geneMap.txt");
		
	}
	
	/**
	 * Returns a list of genes or miRNAs that interact with the given query.
	 * 
	 * The query may be a miRNA or a gene in Uniprot format.
	 * 
	 * If no matches are found, a collection with one entry: "Query not found" 
	 * is returned.
	 * 
	 * @param query	miRNA or protein (Uniprot format)
	 * @return		Interacting miRNA or Proteins
	 */
	public Collection<String> find(String query) {
		
		Collection<String> output = new ArrayList<String>();
		
		// Look first in the list of miRs, since there are far fewer than genes
		if (geneMap.containsKey(query))
			output = geneMap.get(query);
		else if (mirMap.containsKey(query))
			output = mirMap.get(query);
		else
			output.add("Query not found");
		
		return output;
	}
	
	/**
	 * Returns a string representation of the maps
	 * 
	 * @return	A string representation of the maps	
	 */
	@Override
	public String toString() {		
		return "mirMap:\n" + mapToString(mirMap) + "\ngeneMap:\n" + mapToString(geneMap);
	}
	
	
	// --- Getters/Setters --- //
	
	public Map<String, Collection<String>> getMirMap() { return mirMap; }
	public Map<String, Collection<String>> getGeneMap() { return geneMap; }

	
	// --- Private Methods --- //
	
	private Map<String, Collection<String>> scanMapFile(String filename) {
		
		Scanner s = null;
		try {
			s = new Scanner(new File(filename));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: " + filename);
			System.exit(2);
		}
		
		// Add file contents to map
		Map<String, Collection<String>> map = new TreeMap<String, Collection<String>>();
		while(s.hasNextLine()) {
			// Each line is structured like so (delim = \t):
			// key	value	value	value	...
			Scanner line = new Scanner(s.nextLine());
			
			// Thus we first get the key from the first string
			String key = null;
			if (line.hasNext())
				key = line.next().toUpperCase();
			
			// Then we get the keys from the remaining strings
			Collection<String> values = new ArrayList<String>();
			while (line.hasNext())
				values.add(line.next().toUpperCase());
			line.close();
			
			// After collecting all from a line, the data can be added
			// to our map
			map.put(key, values);
		}
		
		s.close();
		return map;
	}

	private String mapToString(Map<String, Collection<String>> map) {
		
		String output = "";
		
		for (Entry<String, Collection<String>> entry : map.entrySet()) {
			output += entry.getKey() + ":";
			for (String value : entry.getValue())
				output += "\t" + value;
			output += "\n";
		}
		
		return output;
		
	}

}














