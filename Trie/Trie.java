package Trie;

import java.util.*;

public class Trie {
	private TrieNode root;
	// 여기서의 size는 Trie 구조 내 단어의 개수
	private int size;

	public Trie() {
		this.root = new TrieNode();
	}
	
	public boolean addWord(String word) {
		word = word.toLowerCase();
		//시작 노드를 뿌리 노드로 놓기
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