package BreadthFirstSearch;

import java.util.*;

public class Structure {
	private int num; //노드의 개수
	private LinkedList<Integer> adj[]; //인접리스트
	
	public Structure(int num) {
		this.num = num;
		this.adj = new LinkedList[num]; //사이즈 num의 인접리스트 배열
		//인접 리스트 배열의 초기화
		for(int i=0;i<num;++i) {
			adj[i] = new LinkedList();
		}
	}
	
	//노드의 연결
	//num -> num2 방식으로 연결되는 것
	public void connect(int num, int num2) {
		adj[num].add(num2);
	}
	
	//bfs의 구현
	//매개변수 s는 시작 노드에 해당하는 정수
	//s를 시작으로 탐색을 진행하면서, 탐색한 노드들을 출력
	public void bfs(int s) {
		// 노드의 방문 여부를 판단
		boolean visited[] = new boolean[num];
		//bfs 구현을 위한 Queue
		LinkedList<Integer> queue = new LinkedList<Integer>();
		
		//먼저 시작 노드, 즉 현재 노드를 방문한 것으로 처리하고 queue에 추가
		visited[s] = true;
		queue.add(s);
		System.out.println(queue);
		//queue가 빌 때까지 탐색 과정을 반복
		while(queue.size()!=0) {
			//방문한 노드는 큐에서 제거
			s = queue.poll(); //.poll() : Retrieves and remove the head of the list
			System.out.println(s+"을/를 queue에서 제거");
			System.out.println(queue);
			//인접한 노드들을 queue에 추가
			Iterator<Integer> iterator = adj[s].listIterator();
			while(iterator.hasNext()) {
				int node = iterator.next();
				//해당 노드가 방문하지 않은 상태면, 방문으로 표시한 후 queue에 추가
				if(!visited[node]) {
					visited[node] = true;
					queue.add(node);
					System.out.println(node+" queue에 추가");
					System.out.println(queue);
				}
			}
		}
	}
	
	//bfs를 실제로 사용해보기(tester)
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Structure s = new Structure(4);
		
		s.connect(0, 1);
		s.connect(0, 2);
		s.connect(1, 2);
		s.connect(2,0);
		s.connect(2, 3);
		s.connect(3, 3);
		//0에서 시작하여 그래프를 탐색
		s.bfs(0);
	}
}
