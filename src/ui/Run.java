package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import data.Mapper;

/**
 * This program allows one to quickly find genes and miRNAs that interact
 * by querying the gene or miRNA of interest. 
 * 
 * Currently, this is a console-based program and the data is derived from
 * MiRBase Release 21. Gene symbols from MiRBase data were converted to 
 * Uniprot format for their functionality here.
 * 
 * @author Ross Jones
 * @author Weiss Laboratory, MIT
 * @version Last updated 2015-06-30
 */
public class Run {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String query = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Mapper map = new Mapper();
		
		while (!query.toLowerCase().equals("quit")) {
			
			System.out.println("Input a miRNA or protein (Uniprot) name ('quit' to exit)");
			query = br.readLine().toUpperCase();
			
//			System.out.println(response);
			
			// Look for query hits
			Collection<String> output = map.find(query);
			
			for (String entry : output)
				System.out.printf("%s\t", entry);
			System.out.println();
		}
		
	}

}
