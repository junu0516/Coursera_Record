package BreadthFirstSearch;

import java.util.*;

public class Structure {
	private int num; //����� ����
	private LinkedList<Integer> adj[]; //��������Ʈ
	
	public Structure(int num) {
		this.num = num;
		this.adj = new LinkedList[num]; //������ num�� ��������Ʈ �迭
		//���� ����Ʈ �迭�� �ʱ�ȭ
		for(int i=0;i<num;++i) {
			adj[i] = new LinkedList();
		}
	}
	
	//����� ����
	//num -> num2 ������� ����Ǵ� ��
	public void connect(int num, int num2) {
		adj[num].add(num2);
	}
	
	//bfs�� ����
	//�Ű����� s�� ���� ��忡 �ش��ϴ� ����
	//s�� �������� Ž���� �����ϸ鼭, Ž���� ������ ���
	public void bfs(int s) {
		// ����� �湮 ���θ� �Ǵ�
		boolean visited[] = new boolean[num];
		//bfs ������ ���� Queue
		LinkedList<Integer> queue = new LinkedList<Integer>();
		
		//���� ���� ���, �� ���� ��带 �湮�� ������ ó���ϰ� queue�� �߰�
		visited[s] = true;
		queue.add(s);
		System.out.println(queue);
		//queue�� �� ������ Ž�� ������ �ݺ�
		while(queue.size()!=0) {
			//�湮�� ���� ť���� ����
			s = queue.poll(); //.poll() : Retrieves and remove the head of the list
			System.out.println(s+"��/�� queue���� ����");
			System.out.println(queue);
			//������ ������ queue�� �߰�
			Iterator<Integer> iterator = adj[s].listIterator();
			while(iterator.hasNext()) {
				int node = iterator.next();
				//�ش� ��尡 �湮���� ���� ���¸�, �湮���� ǥ���� �� queue�� �߰�
				if(!visited[node]) {
					visited[node] = true;
					queue.add(node);
					System.out.println(node+" queue�� �߰�");
					System.out.println(queue);
				}
			}
		}
	}
	
	//bfs�� ������ ����غ���(tester)
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Structure s = new Structure(4);
		
		s.connect(0, 1);
		s.connect(0, 2);
		s.connect(1, 2);
		s.connect(2,0);
		s.connect(2, 3);
		s.connect(3, 3);
		//0���� �����Ͽ� �׷����� Ž��
		s.bfs(0);
	}
}
