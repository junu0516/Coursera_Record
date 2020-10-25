package Trie;

import java.util.*;

public class TrieNode {
	private HashMap<Character,TrieNode> children;
	private String text;
	private boolean isLastChar;
	
	public TrieNode() {
		children = new HashMap<Character,TrieNode>();
		text = "";
		isLastChar = false;
	}
	
	public TrieNode(String text) {
		this();
		this.text = text;
	}
	
	public TrieNode getChild(char c){
		return children.get(c);
	}
	
	public boolean isLastChar() {
		return isLastChar;
	}
	
	public void setIsLastChar(boolean b) {
		isLastChar = b;
	}
	
	public String getText() {
		return this.text;
	}
	
	//���� ������ ����
	public TrieNode insert(char c) {
		if(children.containsKey(c)) {
			//���� ���� ��忡 �̹� c�� ���ԵǾ� ���� ��쿡�� �ƹ��͵� ��ȯ���� ����
			return null;
		}
		else {
			//c�� ���� ��쿡�� ���� �ؽ�Ʈ�� c�� ���� ���ο� ���ڿ��� text�� ������ ���ο� ��带 ����
			TrieNode next = new TrieNode(text+c);
			//������ next ��带 ���� children �ڽ� ��忡 ���� �� ��ȯ
			children.put(c, next);
			return next;
		}
	}
	//���� ����� ���� ������ ���� ���  key ����Ʈ
	public Set<Character> getCharKeys(){
		return children.keySet();
	}
}
	