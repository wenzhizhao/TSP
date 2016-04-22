package Christofides;

import java.util.ArrayList;
import java.util.HashMap;

public class Hungarian{
	//Step1 
	//Input: ElementMatrix matrix
	//Output: ElementMatrix matrix
	public ElementMatrix step1 (ElementMatrix matrix){
		//reduce value of row
		for(Element[] row : matrix.elementMatrix){
			int min = matrix.miniValueOfRow(row);
//			System.out.println("minimum number of row is "+min);
			for(int i=0 ; i< matrix.elementMatrix.length;i++){
				row[i].value -= min;
			}
		}
		matrix.print();
		
		//reduce value of col
		for(int col=0;col< matrix.elementMatrix.length;col++){
			int min = matrix.miniValueOfCol(col);
//			System.out.println("minimum number of col is "+min);
			for (int i=0 ; i< matrix.elementMatrix.length;i++){
				matrix.elementMatrix[i][col].value -= min;
			}
		}
		matrix.print();
		return matrix;
	}
	
	
	//step 2
	public ElementMatrix step2(ElementMatrix matrix){
		HashMap<Integer,Integer> zeroCountsRow = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> zeroCountsCol = new HashMap<Integer,Integer>();
		
		HashMap<Integer,Integer> findSingleZeroRow = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> findSingleZeroCol = new HashMap<Integer,Integer>();
		for(int i=0;i<matrix.elementMatrix.length;i++){
			for(int j=0;j<matrix.elementMatrix.length;j++){
				matrix.elementMatrix[i][j].flag=0;
			}
		}

		for(int row=0;row<matrix.elementMatrix.length;row++){
			for(int col=0;col<matrix.elementMatrix.length;col++){
				if(matrix.elementMatrix[row][col].value==0){
					if(!zeroCountsRow.containsKey(row)){
						zeroCountsRow.put(row, 1);
					}
					else{
						zeroCountsRow.put(row, zeroCountsRow.get(row)+1);
					}
					if(!zeroCountsCol.containsKey(col)){
						zeroCountsCol.put(col, 1);
					}
					else{
						zeroCountsCol.put(col, zeroCountsCol.get(col)+1);
					}
				}
			}
		}
		
		for(Integer key : zeroCountsRow.keySet()){
			int value =zeroCountsRow.get(key);
			if(value==1){
				findSingleZeroRow.put(key, value);
			}
		}
		for(Integer key: zeroCountsCol.keySet()){
			int value = zeroCountsCol.get(key);
			if(value==1){
				findSingleZeroCol.put(key, value);
			}
		}
		System.out.println(findSingleZeroRow.keySet()+" "+findSingleZeroRow.values());
		System.out.println(zeroCountsRow.keySet()+" "+zeroCountsRow.values());
		return step2a(zeroCountsRow, zeroCountsCol, findSingleZeroRow, findSingleZeroCol,matrix);

	}
	public ElementMatrix step2a(HashMap<Integer,Integer> zeroCountsRow, HashMap<Integer,Integer> zeroCountsCol, HashMap<Integer,Integer> findSingleZeroRow, HashMap<Integer,Integer> findSingleZeroCol,ElementMatrix matrix){
		int countAllRow=0;
		int countAllCol=0;
		int minRow=Integer.MAX_VALUE;
		int minCol=Integer.MAX_VALUE;
		int countAll=0;
		for(Integer value : zeroCountsRow.values()){
			countAllRow+=value;
			if(value<minRow){
				minRow=value;
			}
		}
		for(Integer value : zeroCountsCol.values()){
			countAllCol+=value;
			if(value<minCol){
				minCol=value;
			}
		}
		countAll=countAllRow+countAllCol;

			//find the row that has only one 0 and 画圈 然后在画圈的列打叉
			for(int row=0;row<matrix.elementMatrix.length;row++){
				if(findSingleZeroRow.containsKey(row) && findSingleZeroRow.get(row)==1){
					//画圈（行单独0）
					for(int i=0;i<matrix.elementMatrix.length;i++){
						if(matrix.elementMatrix[row][i].value==0&&matrix.elementMatrix[row][i].flag==0){
							matrix.elementMatrix[row][i].flag=1;
							for(int l=0;l<matrix.elementMatrix.length;l++){
								for(int j=0;j<matrix.elementMatrix.length;j++){
									System.out.print(matrix.elementMatrix[l][j].flag+" ");
								}
								System.out.println("");
							}

							
							zeroCountsRow.put(row, zeroCountsRow.get(row)-1);
							zeroCountsCol.put(i, zeroCountsCol.get(i)-1);
							System.out.println(zeroCountsRow.keySet()+" "+zeroCountsRow.values());
							System.out.println(zeroCountsCol.keySet()+" "+zeroCountsCol.values());

							findSingleZeroRow.put(row,findSingleZeroRow.get(row)-1);
							if(findSingleZeroRow.get(row)==0){
								findSingleZeroRow.remove(row); 
							}
							if(findSingleZeroCol.containsKey(i) && findSingleZeroCol.get(i)==1){
								findSingleZeroCol.remove(i);
							}
							if(zeroCountsRow.get(row)==0){
								zeroCountsRow.remove(row);
							}
							if(zeroCountsCol.get(i)==0){
								zeroCountsCol.remove(i);
							}
							System.out.println(findSingleZeroRow.keySet()+" "+findSingleZeroRow.values());

							//画圈元素所在列其他0打叉
							for(int j=0;j<matrix.elementMatrix.length;j++){
								if(matrix.elementMatrix[j][i].value==0 && j!=row &&matrix.elementMatrix[j][i].flag==0){
									matrix.elementMatrix[j][i].flag=-1;

									for(int m=0;m<matrix.elementMatrix.length;m++){
										for(int n=0;n<matrix.elementMatrix.length;n++){
											System.out.print(matrix.elementMatrix[m][n].flag+" ");
										}
										System.out.println("");
									}


									zeroCountsRow.put(j, zeroCountsRow.get(j)-1);
									zeroCountsCol.put(i, zeroCountsCol.get(i)-1);
									if(findSingleZeroRow.containsKey(j) && findSingleZeroRow.get(j)==1){
										findSingleZeroRow.remove(j);
									}
									if(findSingleZeroCol.containsKey(i) && findSingleZeroCol.get(i)==1){
										findSingleZeroCol.remove(i);
									}
									if(zeroCountsRow.get(j)==0){
										zeroCountsRow.remove(j);
									}
									if(zeroCountsCol.get(i)==0){
										zeroCountsCol.remove(i);
									}
									System.out.println(zeroCountsRow.keySet()+" "+zeroCountsRow.values());
									System.out.println(zeroCountsCol.keySet()+" "+zeroCountsCol.values());
									System.out.println(findSingleZeroRow.keySet()+" "+findSingleZeroRow.values());

								}
							}
							break;
						}
					}

				}
			}
				for(int col=0;col<matrix.elementMatrix.length;col++){
					if(findSingleZeroCol.containsKey(col) && findSingleZeroCol.get(col)==1){
						//画圈（列单独0）
						for(int i=0;i<matrix.elementMatrix.length;i++){
							if(matrix.elementMatrix[i][col].value==0 && matrix.elementMatrix[i][col].flag==0){
								matrix.elementMatrix[i][col].flag=1;
								for(int l=0;l<matrix.elementMatrix.length;l++){
									for(int j=0;j<matrix.elementMatrix.length;j++){
										System.out.print(matrix.elementMatrix[l][j].flag+" ");
									}
									System.out.println("");
								}
								zeroCountsCol.put(col, zeroCountsCol.get(col)-1);
								zeroCountsRow.put(i, zeroCountsRow.get(i)-1);
								System.out.println(zeroCountsRow.keySet()+" "+zeroCountsRow.values());
								System.out.println(zeroCountsCol.keySet()+" "+zeroCountsCol.values());
								findSingleZeroCol.put(col, findSingleZeroCol.get(col)-1);
								if(findSingleZeroCol.get(col)==0){
									findSingleZeroCol.remove(col);
								}
								if(findSingleZeroRow.containsKey(i) && findSingleZeroRow.get(i)==1){
									findSingleZeroRow.remove(i);
								}
								if(zeroCountsRow.get(i)==0){
									zeroCountsRow.remove(i);
								}
								if(zeroCountsCol.get(col)==0){
									zeroCountsCol.remove(col);
								}
								System.out.println(findSingleZeroCol.keySet()+" "+findSingleZeroCol.values());

								//画圈元素所在行其他0打叉
								for(int j=0;j<matrix.elementMatrix.length;j++){
									if(matrix.elementMatrix[i][j].value==0 && j!=col && matrix.elementMatrix[i][j].flag==0){
										matrix.elementMatrix[i][j].flag=-1;
										for(int m=0;m<matrix.elementMatrix.length;m++){
											for(int n=0;n<matrix.elementMatrix.length;n++){
												System.out.print(matrix.elementMatrix[m][n].flag+" ");
											}
											System.out.println("");
										}
										zeroCountsCol.put(j, zeroCountsCol.get(j)-1);
										zeroCountsRow.put(i, zeroCountsRow.get(i)-1);
										if(findSingleZeroCol.containsKey(j) && findSingleZeroCol.get(j)==1){
											findSingleZeroCol.remove(j);
										}
										if(findSingleZeroRow.containsKey(i) && findSingleZeroRow.get(i)==1){
											findSingleZeroRow.remove(i);
										}
										if(zeroCountsRow.get(i)==0){
											zeroCountsRow.remove(i);
										}
										if(zeroCountsCol.get(j)==0){
											zeroCountsCol.remove(j);
										}
										System.out.println(zeroCountsRow.keySet()+" "+zeroCountsRow.values());
										System.out.println(zeroCountsCol.keySet()+" "+zeroCountsCol.values());
										System.out.println(findSingleZeroCol.keySet()+" "+findSingleZeroCol.values());

									}
								}
								break;
							}
						}
						//mark 单独列
						//matrix.colMark[col]=1;
					}
				}
				for(Integer key : zeroCountsRow.keySet()){
					int value =zeroCountsRow.get(key);
					if(value==1){
						findSingleZeroRow.put(key, value);
					}
				}
				for(Integer key: zeroCountsCol.keySet()){
					int value = zeroCountsCol.get(key);
					if(value==1){
						findSingleZeroCol.put(key, value);
					}
				}
				if(countAll>0){
					if(minRow==1||minCol==1){
						return step2a(zeroCountsRow, zeroCountsCol, findSingleZeroRow, findSingleZeroCol,matrix);
					}
					else{
						return step2b(zeroCountsRow, zeroCountsCol, matrix);
					}
				}
				else {
					return step3(matrix);
//					return matrix;
				}
	}
	
	
	public ElementMatrix step2b(HashMap<Integer,Integer> zeroCountsRow, HashMap<Integer,Integer> zeroCountsCol, ElementMatrix matrix){
		//find if there are still uncovered 0s;
		HashMap<Integer,Integer> findSingleZeroRow = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> findSingleZeroCol = new HashMap<Integer,Integer>();
		for(Integer key : zeroCountsRow.keySet()){
			int value =zeroCountsRow.get(key);
			if(value==1){
				findSingleZeroRow.put(key, value);
			}
		}
		for(Integer key: zeroCountsCol.keySet()){
			int value = zeroCountsCol.get(key);
			if(value==1){
				findSingleZeroCol.put(key, value);
			}
		}

		int countAllRow=0;
		int countAllCol=0;
		int minRow=Integer.MAX_VALUE;
		int minCol=Integer.MAX_VALUE;
		int countAll=0;
		for(Integer value : zeroCountsRow.values()){
			countAllRow+=value;
			if(value<minRow){
				minRow=value;
			}
		}
		for(Integer value : zeroCountsCol.values()){
			countAllCol+=value;
			if(value<minCol){
				minCol=value;
			}
		}
		countAll=countAllRow+countAllCol;

		int minRowValue = Integer.MAX_VALUE;
		int minRowKey = 0;
		for(Integer i : zeroCountsRow.keySet()){
			if(zeroCountsRow.get(i)>=2 && zeroCountsRow.get(i)<=minRowValue){
				minRowValue=zeroCountsRow.get(i);
				minRowKey = i;
			}
		}
		System.out.println("minRowKey "+minRowKey);
		int minColValue = Integer.MAX_VALUE;
		int minColKey = 0;
		for(int i=0;i<matrix.elementMatrix.length;i++){
			System.out.println("minRowKey"+minRowKey+"zeroCountsCol.get(i)"+zeroCountsCol.get(i)+"col"+i);
			if(zeroCountsCol.containsKey(i)){
			if(matrix.elementMatrix[minRowKey][i].value==0 && zeroCountsCol.get(i)<=minColValue){
				minColValue = zeroCountsCol.get(i);
				minColKey = i;
			}
		}
		}
		if(matrix.elementMatrix[minRowKey][minColKey].flag==0){
			matrix.elementMatrix[minRowKey][minColKey].flag=1;
			zeroCountsRow.put(minRowKey, zeroCountsRow.get(minRowKey)-1);
			zeroCountsCol.put(minColKey, zeroCountsCol.get(minColKey)-1);
			for(int m=0;m<matrix.elementMatrix.length;m++){
				for(int n=0;n<matrix.elementMatrix.length;n++){
					System.out.print(matrix.elementMatrix[m][n].flag+" ");
				}
				System.out.println("");
			}
			if(zeroCountsRow.get(minRowKey)==0){
				zeroCountsRow.remove(minRowKey);
			}
			if(zeroCountsCol.get(minColKey)==0){
				zeroCountsCol.remove(minColKey);
			}

		}

		for(int i = 0;i<matrix.elementMatrix.length;i++){
			if(matrix.elementMatrix[minRowKey][i].value==0 && i!=minColKey && matrix.elementMatrix[minRowKey][i].flag==0){
				matrix.elementMatrix[minRowKey][i].flag=-1;
				zeroCountsRow.put(minRowKey, zeroCountsRow.get(minRowKey)-1);
				zeroCountsCol.put(i, zeroCountsCol.get(i)-1);
				for(int m=0;m<matrix.elementMatrix.length;m++){
					for(int n=0;n<matrix.elementMatrix.length;n++){
						System.out.print(matrix.elementMatrix[m][n].flag+" ");
					}
					System.out.println("");
				}
				if(zeroCountsRow.get(minRowKey)==0){
					zeroCountsRow.remove(minRowKey);
				}
				if(zeroCountsCol.get(i)==0){
					zeroCountsCol.remove(i);
				}

			}
		}
		for(int i = 0;i<matrix.elementMatrix.length;i++){
			if(matrix.elementMatrix[i][minColKey].value==0 && i!=minRowKey && matrix.elementMatrix[i][minColKey].flag==0){
				matrix.elementMatrix[i][minColKey].flag=-1;
				zeroCountsRow.put(i, zeroCountsRow.get(i)-1);
				zeroCountsCol.put(minColKey, zeroCountsCol.get(minColKey)-1);
				for(int m=0;m<matrix.elementMatrix.length;m++){
					for(int n=0;n<matrix.elementMatrix.length;n++){
						System.out.print(matrix.elementMatrix[m][n].flag+" ");
					}
					System.out.println("");
				}
				if(zeroCountsRow.get(i)==0){
					zeroCountsRow.remove(i);
				}
				if(zeroCountsCol.get(minColKey)==0){
					zeroCountsCol.remove(minColKey);
				}
			}
		}
		
		 countAllRow=0;
		 countAllCol=0;
		 minRow=Integer.MAX_VALUE;
		minCol=Integer.MAX_VALUE;
		countAll=0;
		for(Integer value : zeroCountsRow.values()){
			countAllRow+=value;
			if(value<minRow){
				minRow=value;
			}
		}
		for(Integer value : zeroCountsCol.values()){
			countAllCol+=value;
			if(value<minCol){
				minCol=value;
			}
		}
		countAll=countAllRow+countAllCol;
		
		
		System.out.println("minRow "+minRow+" minCol "+minCol);
		if(countAll>0){
			if(minRow>=2 && minCol>=2){
				for(Integer value:zeroCountsRow.values()){
					System.out.print(value+" ");
				}
				System.out.println("\n");
				for(Integer value:zeroCountsCol.values()){
					System.out.print(value+" ");
				}
				System.out.println("\n");
				return step2b(zeroCountsRow, zeroCountsCol, matrix);
			}
			else{
				return step2a(zeroCountsRow, zeroCountsCol, findSingleZeroRow, findSingleZeroCol,matrix);
			}
		}
		else{
			return step3(matrix);
//			return matrix;
		}
	}	
	
	//Step3
	public ElementMatrix step3 (ElementMatrix matrix){
		System.out.println("stop\n");
		matrix = step3a(matrix);
		matrix = step3d(matrix);
		matrix = step3e(matrix);
		matrix.print();
		return matrix;
	}
	
	//(1)对没有◎的行打√号；
	public ElementMatrix step3a (ElementMatrix matrix){
		System.out.println("step3a rowMark");
		for(int i=0;i<matrix.elementMatrix.length;i++){
			boolean rowCheck = true;
			for(int j=0; j<matrix.elementMatrix.length;j++){
				 rowCheck = rowCheck && matrix.elementMatrix[i][j].flag != 1;
				}
			if (rowCheck){
				matrix.rowMark[i] =1;
			}
		}
		for(int rowMark: matrix.rowMark){
			System.out.print(rowMark+" ");
		}
		System.out.println();
		return matrix;
	}
	//(2)对已打√号的行中所有含Ø元素的列打√号；
	public ElementMatrix step3b (ElementMatrix matrix){
		System.out.println("step3b colMark");
		for(int i=0;i<matrix.rowMark.length;i++){
			if (matrix.rowMark[i] == 1){
				for (int j=0;j<matrix.rowMark.length;j++){
					if (matrix.elementMatrix[i][j].flag ==-1){
						matrix.colMark[j]=1;
//						break;
					}
				}
			}
		}
		for(int colMark: matrix.colMark){
			System.out.print(colMark+" ");
		}
		System.out.println();
		return matrix;
	}
	
	//(3)再对打有√号的列中含◎ 元素的行打√号；
	public ElementMatrix step3c (ElementMatrix matrix){
		System.out.println("step3c rowMark");
		for (int i=0;i<matrix.colMark.length;i++){
			if(matrix.colMark[i] == 1){
				for(int j=0;j<matrix.colMark.length;j++){
					if (matrix.elementMatrix[j][i].flag == 1){
						matrix.rowMark[j] =1;
					}
				}
			}
		}
		for(int rowMark: matrix.rowMark){
			System.out.print(rowMark+" ");
		}
		System.out.println();
		return matrix;
	}
	
	//	(4)重复(2)，(3)直到得不出新的打√号的行、列为止；
	public ElementMatrix step3d (ElementMatrix matrix){
		System.out.println("step3d working");
		ArrayList<Integer[]> zero = matrix.findZero();
		for(int i=0;i<zero.size();i++){
			for(Integer position: zero.get(i)){
				System.out.print(position+ " ");
			}
			System.out.println();
		}
		while(zero.size()>0){
//			for(int i=0;i<zero.size();i++){
//				System.out.println(zero.get(i));
//			}

			matrix=step3b(matrix);
			matrix=step3c(matrix);
			System.out.println("zero.size "+zero.size());
			int deleteIndex=zero.size()-1;
			while(deleteIndex>=0){
				if(matrix.rowMark[zero.get(deleteIndex)[0]] ==0 || matrix.colMark[zero.get(deleteIndex)[1]] ==1){
					zero.remove(deleteIndex);
				}
				deleteIndex--;
//				for(int i=0;i<zero.size();i++){
//					for(Integer position: zero.get(i)){
//						System.out.print(position+ " ");
//					}
//					System.out.println();
//				}
			}
			System.out.println("zero.size delete "+zero.size());
//			for(int i=0;i<zero.size();i++){
//				for(Integer position: zero.get(i)){
//					System.out.print(position+ " ");
//				}
//				System.out.println();
//			}
		}
		return matrix;
		
	}
	//(5)对没有打√号的行画横线，有打√号的列画纵线，这就得到覆盖所有0元素的最少直线数 l 。l 等于m，
	public  ElementMatrix step3e (ElementMatrix matrix){
		System.out.println("step3e working");
		int totalRowMark = 0;
		int totalColMark = 0;
		for (int i=0; i<matrix.rowMark.length;i++){
			totalRowMark+=matrix.rowMark[i];
			totalColMark+=matrix.colMark[i];
		}
		// the mark line right equals to the m
		System.out.println("line num: "+(totalColMark+matrix.rowMark.length-totalRowMark));
		if ((totalColMark+matrix.rowMark.length-totalRowMark) == matrix.rowMark.length){ 
			return matrix;
		}
		// the mark line less than m;
		else{
			return step4(matrix);
		}

	}
	

	//step 4
		public ElementMatrix step4(ElementMatrix matrix){
			System.out.println("\nstep 4 ");
			int restMin=matrix.restMinimumValueOfMatrix();
			System.out.println("restMininum num is "+restMin);
			//然后打√各行都减去这最小元素
			System.out.println("然后打√各行都减去这最小元素");
			for(int i=0;i<matrix.rowMark.length;i++){
				if(matrix.rowMark[i]==0)
					continue;
				else{
					for(int j=0;j<matrix.colMark.length;j++){
							matrix.elementMatrix[i][j].value-=restMin;
					}
				}
			}
			matrix.print();
			
			//打√各列都加上这最小元素
			System.out.println("打√各列都加上这最小元素");
			for(int i=0;i<matrix.colMark.length;i++){
				if(matrix.colMark[i]==1){
					for(int j=0;j<matrix.rowMark.length;j++){
						matrix.elementMatrix[j][i].value+=restMin;
					}
				}
			}
			matrix.print();
			System.out.println("return to step2 ");
			return step2(matrix);
		}
		
		
	//convert the int matrix to element struct matrix, initually all the flags are 0
	public ElementMatrix convertMatrix(int[][] intMatrix){
		Element[][] elementMatrix = new Element[intMatrix.length][intMatrix.length];
		for(int row=0;row<elementMatrix.length;row++){
			for(int col=0;col<elementMatrix.length;col++){
				Element element = new Element(intMatrix[row][col],0);
//				System.out.println(intMatrix[row][col]);
				elementMatrix[row][col] = element;
//				elementMatrix[row][col].value=intMatrix[row][col];
//				System.out.println(elementMatrix[row][col].value);
//				elementMatrix[row][col].flag=0;
			}
		}
		return new ElementMatrix(elementMatrix);
	}	
}
class Element{
	int value;
	int flag;
	Element(int value,int flag){
		this.value=value;
		this.flag=flag;
	}
}

class ElementMatrix{
	Element[][] elementMatrix;
	int[] rowMark;
	int[] colMark;
	ElementMatrix(Element[][] elementMatrix){
		this.elementMatrix=elementMatrix;
		this.rowMark=new int[elementMatrix.length];
		this.colMark=new int[elementMatrix.length];
	}
	
	//print the value of matrix
	public void print(){
		System.out.println("Print the matrix:");
		for(Element[] row: this.elementMatrix){
			for(Element item: row){
				System.out.print(item.value+"  ");
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	
	//find minimum value of row
		public int miniValueOfRow(Element[] array){
				int min = Integer.MAX_VALUE;
				for(Element item : array){
					if (item.value < min) {
						min = item.value;
					}
				}
			return min;
		}
		//find minimum value of col
		public int miniValueOfCol(int col){
			int min = Integer.MAX_VALUE;
			for (int i=0; i< elementMatrix.length;i++){
				if(elementMatrix[i][col].value < min){
					min = elementMatrix[i][col].value;
				}
			}
			return min;
		}
		
		//find rest minimum value of element matrix
		public int restMinimumValueOfMatrix(){
			int min = Integer.MAX_VALUE;
			for(int i=0;i<rowMark.length;i++){
				if(rowMark[i]==0)
					continue;
				else{
				for(int j=0;j<colMark.length;j++){
					if(colMark[j]==0 && elementMatrix[i][j].value<min){
						min=elementMatrix[i][j].value;
					}
				}
				}
			}
			return min;
		}
		
		public ArrayList<Integer[]> findZero(){
			ArrayList<Integer[]> zero = new ArrayList<Integer[]>();
			for(int row=0;row<this.elementMatrix.length;row++){
				for(int col=0;col<this.elementMatrix.length;col++){
					Integer[] temp = {row,col};
					if(this.elementMatrix[row][col].value==0){
						zero.add(temp);
					}

				}
			}
			return zero;
		}
		//output the result of assigned 0s
		public ArrayList<Integer[]> output(){
			ArrayList<Integer[]> result = new ArrayList<Integer[]>();
			for(int i = 0;i<this.elementMatrix.length;i++){
				for(int j = 0; j<this.elementMatrix.length;j++){
					if(this.elementMatrix[i][j].flag == 1){
						Integer[] position = {i,j};
						result.add(position);
					}
				}
			}
			for(Integer[] edge: result){
				System.out.println(edge[0]+" "+edge[1]);
			}
			
			return result;
		}
}