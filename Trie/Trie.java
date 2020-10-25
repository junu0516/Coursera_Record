package Trie;

import java.util.*;

public class Trie {
	private TrieNode root;
	// ���⼭�� size�� Trie ���� �� �ܾ��� ����
	private int size;

	public Trie() {
		this.root = new TrieNode();
	}
	
	public boolean addWord(String word) {
		word = word.toLowerCase();
		//���� ��带 �Ѹ� ���� ����
		TrieNode currNode = root;
		for(char c : word.toCharArray()) {
			if(currNode.getCharKeys().contains(c)) {
				currNode = currNode.getChild(c);
			}
			else {
				currNode = currNode.insert(c);
			}
		}
		if(currNode.isLastChar()==false) {
			currNode.setIsLastChar(true);
			size++;
			return true;
		}
		return false;
	}

	
	public boolean isWord(String word) {
		TrieNode currNode = root;
		word = word.toLowerCase();
		for(char c : word.toCharArray()) {
			if(currNode.getChild(c)==null) {
				return false;
			}
			currNode = currNode.getChild(c);
		}
		return currNode.isLastChar();
	}

	public static void main(String[] args) {
		Trie test = new Trie();
	}
}