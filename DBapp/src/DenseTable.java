import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;


public class DenseTable {
	String ColumnName; //The Column we   cluster with 
	
	
	ArrayList<DenseIndex> denses;
	Table table;
	
	
	Hashtable<String,String> htblColNameType;
	public DenseTable(Table table,String ColumnName){
		this.table=table;
		this.ColumnName=ColumnName;
		this.denses=new ArrayList<DenseIndex>();
		Initialize();
		

	}
	
		
	
	public void Initialize(){
		ArrayList<Page> Pages=this.table.Pages;
		
		for(int i=0;i<Pages.size();i++){
			Page p=Pages.get(i);
			LinkedList<Hashtable<String,Object>> Pagetuples=p.tuples;
			DenseIndex Dense=new DenseIndex(ColumnName);
			denses.add(Dense);
			for(int j=0;j<Pagetuples.size();j++){
				if(Pagetuples.get(i).containsKey(ColumnName)){
					Object key= Pagetuples.get(i).get(ColumnName); //Get Value we want to Cluster On
					InsertIntoDenseTable(key,i,j);
					
				}
				   
					
				//Dense.InsertIntoDense(t.get(i));
				
				
				
			
			
			
		}}
	}
	
	public void LoadAll(){
		for(int i=0;i<denses.size();i++){
			denses.get(i).loadDense(i, ColumnName);
		}
	}
	public void InsertIntoDenseTable(Object keyValue,int PageNum,int EntityNum){
		boolean IsString=false;
		if(keyValue instanceof String){
			String primary=(String)keyValue;
		
				
				for(int i=0;i<denses.size();i++){  ///this is disgusting .. im ashamed of u romy
					DenseIndex d=denses.get(i);
					LinkedList<Entity> Densetuples=d.Densetuples;
				 if(!Densetuples.isEmpty()){
					Entity first=Densetuples.getFirst();
					Entity Last=Densetuples.getLast();
					String firstValue= (String)first.Value;
					String SecondValue= (String)Last.Value;
					int Upper=primary.compareTo(firstValue);
					
					
					int Lower=primary.compareTo(SecondValue);
			
						Entity Entity=new Entity(keyValue, PageNum,EntityNum);
						d.InsertIntoDense(Entity,!IsString);
						
                        
						//p.loadPage(i,this.strTableName);
						
						LoadAll();
						break;
				}
					
				 
				
				 
				
				 else{
						Entity Entity=new Entity(keyValue, PageNum,EntityNum);

					 d.InsertIntoDense(Entity, !IsString); 
					
						LoadAll();
						break;
				 }
					
				}
			}
				
		
		else{
			//case numerical
			
			
			int key1=(int)keyValue;
			
			for(int i=0;i<denses.size();i++){  ///this is disgusting .. im ashamed of u romy
				DenseIndex d=denses.get(i);
				LinkedList<Entity> tuples=d.Densetuples;
				
			
			 if(!tuples.isEmpty()){
				
				Entity first=tuples.getFirst();
				
				
				Entity Last=tuples.getLast();
				int firstValue= (int)first.Value;
				//System.out.println(firstValue+"first"); //it prints our value
				int SecondValue= (int)Last.Value;
				//System.out.println(SecondValue+"last");
				
                
                	
                 /*if(key1>firstValue){
                	 //p.insertIntoPage(htblColNameVale, key1,IsString);
                	 tuples.addLast(htblColNameVale);
                	
                 }
                 
                 else if(key1<SecondValue){
                	 
                	 tuples.addFirst(htblColNameVale);
                 }*/
                	 
                
                
                 
              
                	
                if((key1>firstValue&& key1<SecondValue)||(key1<SecondValue)||(key1>firstValue)){ //missing case
				Entity Entity=new Entity(keyValue, PageNum,EntityNum);


				 d.InsertIntoDense(Entity,IsString);
				
				
				
					LoadAll();
					break;
				}
			
		
			

		}
			 
			 else{
			  Entity Entity=new Entity(keyValue, PageNum,EntityNum);

				 d.InsertIntoDense(Entity,IsString);
				
				 
				 
				 
				
				
				
				 LoadAll();
				 break;
			 }
	}
			
		
		
  }
		
	
	
	
	}
	
	

}
