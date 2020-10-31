package BinarySearchTree;

public class TreeNode<D>{
    D data; //저장할 데이터의 참조
    TreeNode left; //왼쪽 자식 노드에 대한 참조
    TreeNode right; //오른쪽 자식 노드에 대한 참조
    
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