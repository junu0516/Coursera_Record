package BinarySearchTree;

public class BinarySearchTree {
	
	public class TreeNode{
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
        	root = new TreeNode(data); //루트 노드가 없는 경우에는 루트노드부터 초기화            
        }
        else{
        	currNode = root; //일단 루트 노드부터 시작
          
          	while(true){
           		if(currNode.getData()>data){
            		currNode = currNode.left; //추가할 데이터가 더 작으면 왼쪽으로 이동
                	if(currNode ==null){
                    currNode = new TreeNode(data); //왼쪽 자손 노드가 없으면 그 위치에 새로운 노드 추가
                    break;
                	}
            	}
                else{
                	currNode = currNode.right; //추가할 데이터가 더 크면 오른쪽으로 이동
            		if(currNode == null){
                    currNode = new TreeNode(data); //오른쪽 자손 노드가 없으면 그 위치에 새로운 노드 추가
                    break;
                	}
            	}              
            }
        }
    }
	
    public D search(D data){
    	currNode = root; //루트노드에서 시작
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
        
        //삭제할 위치 검색
        while(currNode.getData() != data){
        	parentNode = currNode; //먼저 부모노드를 설정하고 탐색 시작
            if(currNode == null){
            	return false;
            }
            else if(currNode.getData()>data){
            	currNode = currNode.left; //왼쪽으로 이동
                isLeft = true;
            }
            else if(currNode.getData()<data){
            	currNode = currNode.right; //오른쪽으로 이동
                isLeft = false;
            }
        }
        
        //자식 노드가 없는 경우
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
        //자식 노드가 하나인 경우
        else if(currNode.left==null){
        	parentNode.right = currNode.right; //삭제할 노드의 부모노드와 자식노드를 연결
        }
        else if(currNode.right==null){
        	parentNode.left = currNode.left; //삭제할 노드의 부모노드와 자식노드를 연결
        }
        //자식 노드가 둘인 경우
        else{
        	//서브 트리에서 최댓값 탐색
            parentNode = currNode;
            TreeNode maxNode = parentNode.left;
            isLeft = true;
            while(maxNode.right != null){
                parentNode = maxNode;
                maxNode = maxNode.right;
                isLeft = false;
            }
            currNode.setData(maxNode.getData()); //서브 트리 노드 중 최댓값을 가지는 노드의 데이터를 옮김
            if(isLeft){
            	parentNode.left = maxNode.left; //노드 삭제
            }
            else{
            	parentNode.right = maxNode.left; //노드 삭제
            }
        }
        return true;
    }
}
