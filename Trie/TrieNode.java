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
	
	//하위 노드와의 연결
	public TrieNode insert(char c) {
		if(children.containsKey(c)) {
			//만일 하위 노드에 이미 c가 포함되어 있을 경우에는 아무것도 반환하지 않음
			return null;
		}
		else {
			//c가 없는 경우에는 기존 텍스트에 c를 붙인 새로운 문자열을 text로 가지는 새로운 노드를 생성
			TrieNode next = new TrieNode(text+c);
			//생성한 next 노드를 현재 children 자식 노드에 연결 후 반환
			children.put(c, next);
			return next;
		}
	}
	//현재 노드의 하위 노드들이 가진 모든  key 리스트
	public Set<Character> getCharKeys(){
		return children.keySet();
	}
}
	