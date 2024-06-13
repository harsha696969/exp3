package cd_programs;

import java.util.*;

public class LeftRecursionElimination 
{
   
    public static boolean eliminateLeftRecursion(Map<String, List<String>> grammar) 
    {
    	boolean hasLeftRecursion = false;
    	
        for (String nonTerminal : new ArrayList<>(grammar.keySet())) 
        {
            List<String> productions = grammar.get(nonTerminal);
            List<String> newProductions = new ArrayList<>();
            List<String> recursiveProductions = new ArrayList<>();
           
            // Separate recursive and non-recursive productions
            for (String production : productions) 
            {
                if (production.startsWith(nonTerminal)) 
                    recursiveProductions.add(production.substring(nonTerminal.length()).trim());
                else
                    newProductions.add(production);
            }
           
            
            if (!recursiveProductions.isEmpty()) 
            {
            	hasLeftRecursion = true;
                String newNonTerminal = nonTerminal + "'";
               
                // Add new productions for non-terminal
                for (String production : new ArrayList<>(recursiveProductions)) 
                {
                    newProductions.add(production + " " + newNonTerminal);
                    recursiveProductions.remove(0);
                    recursiveProductions.add(production + " " + newNonTerminal);
                }
               
                // Add epsilon production
                newProductions.add("$");
               
                // Update grammar
                grammar.put(nonTerminal, newProductions);
                grammar.put(newNonTerminal, recursiveProductions);
            }
        }
        return hasLeftRecursion;
    }

    public static void main(String[] args) 
    {
        // Dynamic input
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> grammar = new LinkedHashMap<>();
       
        System.out.println("Enter grammar productions (press Enter after each non-terminal production, and type 'done' to finish):");
        String input = sc.nextLine();
        while (!input.equals("done")) 
        {
            String[] parts = input.split("->");
            String nonTerminal = parts[0].trim();
            String[] productions = parts[1].trim().split("\\|");
            grammar.put(nonTerminal, Arrays.asList(productions));
            input = sc.nextLine();
        }

        // Eliminate left recursion
        boolean hasLeftRecursion = eliminateLeftRecursion(grammar);

        // Display the modified grammar
        if(hasLeftRecursion)
        {
        for (String nonTerminal : grammar.keySet()) 
            System.out.println(nonTerminal + " -> " + String.join(" | ", grammar.get(nonTerminal)));
        }
        else
        {
        	System.out.println("The given grammar does not contain left recursion");
        }
    }
}