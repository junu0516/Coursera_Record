package BinarySearchTree;

public class TreeNode<D>{
    D data; //������ �������� ����
    TreeNode left; //���� �ڽ� ��忡 ���� ����
    TreeNode right; //������ �ڽ� ��忡 ���� ����
    
    public TreeNode(){
    	this.left = null;
        this.right = null;
    }
    
    public TreeNode(D data){
    	this.data = data;
        this.left = null;
        this.right = null;
    }

	public D getData(){
    	return this.data;
    }
}