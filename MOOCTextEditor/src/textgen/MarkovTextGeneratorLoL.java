package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		ArrayList<String> words = new ArrayList<String>();
		Pattern splitter = Pattern.compile("[a-zA-Z]+");		
		Matcher m = splitter.matcher(sourceText);
		while (m.find()) {
			words.add(m.group());
		}
		
		this.starter = words.get(0);
		String prevWord = this.starter;
		
		for(int i=1;i<words.size();i++) {
			String word = words.get(i);
			//���� prevWord�� ����Ʈ ���� ���� �����ϴ� �� Ȯ��
			addNextWord(prevWord,word);
			prevWord = word;
		}
		addNextWord(prevWord, this.starter);
	}
	
	public void addNextWord(String prevWord, String word) {
		//���� �ܾ� ��� ��������
		ListNode prevWordNode = getNode(prevWord);
		if(prevWordNode!=null) {
			//���� �ܾ� ��尡 �����ϸ�, �ص� ��忡 �Ű������� �־��� �ܾ �߰�
			prevWordNode.addNextWord(word);
		}
		else {
			//���� ��� ���ο� ��带 ����� ���� �ܾ �ش� ��忡 �߰�
			ListNode newNode = new ListNode(prevWord);
			//���Ӱ� ������� ��忡 �Ű������� �־��� �ܾ �߰�
			newNode.addNextWord(word);
			//�ϼ��� ��带 ��� ����Ʈ�� �߰�
			wordList.add(newNode);
		}
	}
	//��� �������� 
	public ListNode getNode(String word) {
		for(ListNode node : wordList) {
			//��� ����Ʈ ���� ������ ���鿡 ���ؼ�
			if(node.getWord().equals(word)) {
				//���� ��忡�� ���� ���ڿ��� �Ű������� �־��� ���ڿ��� ������ �ش� ��带 ��ȯ
				return node;
			}
		}
		//��ȯ�� ��尡 ������ null�� ��ȯ
		return null;
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		String currWord = this.starter;
		String output = "";
		output += currWord+" ";
		int count = 1;
		while(count<numWords) {
			ListNode currNode = getNode(currWord);
			if(currNode.getNextWords().size()>0) {
				String randomWord = currNode.getRandomNextWord(rnGenerator);
				output += randomWord+" ";
				currWord = randomWord;
				count++;
			}
			else {
				continue;
			}
		}
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		this.wordList = new LinkedList<ListNode>();
		this.starter = "";
		train(sourceText);
	}	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		this.nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public List<String> getNextWords(){
		return this.nextWords;
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int randomIndex = generator.nextInt(nextWords.size());
		String randomlyPicked = nextWords.get(randomIndex);
	    return randomlyPicked;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
}


