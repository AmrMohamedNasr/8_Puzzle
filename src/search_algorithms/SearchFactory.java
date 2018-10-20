package search_algorithms;

public interface SearchFactory {
	public SearchAlgorithm createAlgorithm(int search_index, int heuristic_index);
	public String[] getSupportedSearchMethodes();
	public String[] getSupportedHeuristicFunctions();
	public boolean isInformativeMethod(int search_index);
}
