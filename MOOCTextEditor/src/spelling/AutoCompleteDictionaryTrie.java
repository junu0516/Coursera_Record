package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    
    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		word = word.toLowerCase();
		TrieNode currNode = root;
		//특정 word의 모든 문자에 대한 for-each 구문 내에서
		for(char c : word.toCharArray()) {
			//만일 현재 노드의 자손이 현재 참조중인 문자 c를 포함하면?
			//해당 메소드는 Children 노드들의 keyset을 반환... 즉 우리가 사용하는 문자열의 모든 문자 리스트와 같은 것
			if(currNode.getValidNextCharacters().contains(c)) {
				//추가 없이 바로 다음 노드로 직행한다.
				currNode = currNode.getChild(c);
			}
			else {
				//그렇지 않으면 추가 후, 현재 노드를 바로 추가한 하위 노드로 변경
				currNode = currNode.insert(c); //그렇지 않을 경우 문자 c를 키로 하는 새로운 노드를 추가
			}
		}
		//만일 마지막 노드가 단어의 끝을 알린다면
		if(!currNode.endsWord()) {
			currNode.setEndsWord(true);
			size++;
			return true;
		}
		return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		s = s.toLowerCase(); //매개변수로 받은 문자열을 소문자로 변경
		TrieNode currNode = root; //현재 참조하는 레벨의 노드는 뿌리 노드부터 시작
		for(char c : s.toCharArray()) {
			if(currNode.getChild(c)==null) {
				return false;
			}
			currNode = currNode.getChild(c);
		}		
		return currNode.endsWord();
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 LinkedList<TrieNode> nodeQueue = new LinkedList<TrieNode>();
    	 LinkedList<String> completions = new LinkedList<String>();
    	 TrieNode node = root;
    	 prefix = prefix.toLowerCase();
    	 for(char c : prefix.toCharArray()) {
    		 node = node.getChild(c);
    		 if(node == null/*다시 말해, 다음 노드가 없는 것으로 해석*/) {
    			 return completions; //이 경우 비어있는 리스트 그대로 반환
    		 }
    	 }
    	 nodeQueue.add(node); //트리 내 prefix 끝지점까지 도달한 노드가 root가 되는 것
    	 
    	 while(!nodeQueue.isEmpty()&&completions.size()<numCompletions) {
    		 node = nodeQueue.remove();
    		 if(node.endsWord()) {
    			 completions.add(node.getText());
    		 }
    		 Set<Character> children = node.getValidNextCharacters();
    		 for(char c : children) {
    			 nodeQueue.add(node.getChild(c));
    		 }
    	 }
         return completions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}