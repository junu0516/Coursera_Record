package BinarySearchTree;

public class BinarySearchTree {
	
	public class TreeNode{
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
		
		public void setData(D data) {
			this.data = data;
		}
	}
	D data;
	TreeNode root;
	TreeNode currNode;
	TreeNode parentNode;
    
    public void insertNode(D data){
    	if(root==null){
        	root = new TreeNode(data); //��Ʈ ��尡 ���� ��쿡�� ��Ʈ������ �ʱ�ȭ            
        }
        else{
        	currNode = root; //�ϴ� ��Ʈ ������ ����
          
          	while(true){
           		if(currNode.getData()>data){
            		currNode = currNode.left; //�߰��� �����Ͱ� �� ������ �������� �̵�
                	if(currNode ==null){
                    currNode = new TreeNode(data); //���� �ڼ� ��尡 ������ �� ��ġ�� ���ο� ��� �߰�
                    break;
                	}
            	}
                else{
                	currNode = currNode.right; //�߰��� �����Ͱ� �� ũ�� ���������� �̵�
            		if(currNode == null){
                    currNode = new TreeNode(data); //������ �ڼ� ��尡 ������ �� ��ġ�� ���ο� ��� �߰�
                    break;
                	}
            	}              
            }
        }
    }
	
    public D search(D data){
    	currNode = root; //��Ʈ��忡�� ����
        while(true){
        	if(currNode == null){
            	return null;
            }
            else if(currNode.getData()>data){
            	currNode = currNode.left;
            }
            else if(currNode.getData()<data){
            	currNode = currNode.right;
            }
            else if(currNode.getData() == data){
            	return currNode.getData();
            }
        }

    }
    
    public boolean removeNode(D data){
        currNode = root;
        parentNode = null;
        boolean isLeft = false;
        
        //������ ��ġ �˻�
        while(currNode.getData() != data){
        	parentNode = currNode; //���� �θ��带 �����ϰ� Ž�� ����
            if(currNode == null){
            	return false;
            }
            else if(currNode.getData()>data){
            	currNode = currNode.left; //�������� �̵�
                isLeft = true;
            }
            else if(currNode.getData()<data){
            	currNode = currNode.right; //���������� �̵�
                isLeft = false;
            }
        }
        
        //�ڽ� ��尡 ���� ���
        if(currNode.left==null && currNode.right==null){
        	if(currNode == root){
            	root = null;
            }
            if(isLeft){
            	parentNode.left = null;
            }
            else{
            	parentNode.right = null;
            }
        }
        //�ڽ� ��尡 �ϳ��� ���
        else if(currNode.left==null){
        	parentNode.right = currNode.right; //������ ����� �θ���� �ڽĳ�带 ����
        }
        else if(currNode.right==null){
        	parentNode.left = currNode.left; //������ ����� �θ���� �ڽĳ�带 ����
        }
        //�ڽ� ��尡 ���� ���
        else{
        	//���� Ʈ������ �ִ� Ž��
            parentNode = currNode;
            TreeNode maxNode = parentNode.left;
            isLeft = true;
            while(maxNode.right != null){
                parentNode = maxNode;
                maxNode = maxNode.right;
                isLeft = false;
            }
            currNode.setData(maxNode.getData()); //���� Ʈ�� ��� �� �ִ��� ������ ����� �����͸� �ű�
            if(isLeft){
            	parentNode.left = maxNode.left; //��� ����
            }
            else{
            	parentNode.right = maxNode.left; //��� ����
            }
        }
        return true;
    }
}
