//package Task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class Page implements Serializable {

    //should implement serializable ///
    int No;
    public LinkedList<Hashtable<String,Object>> tuples;
    File file;
    int currentLine = 0;//the line to add data too
     String strClusteringKeyColumn = "";
     
     //BrinIndex Brin;
    //int ColumnNumber;

    //constructor 
    public Page(int pageNo,File file, String strClusteringKeyColumn) {
        this.No = pageNo;
        this.tuples = new LinkedList<>();// 200 max no of tuples
        this.file = file;
        this.strClusteringKeyColumn = strClusteringKeyColumn;
        
        //this.ColumnNumber=ColumnNumber;
}
    //check for page if full or not 
    //if full it calls WritePage 
    
	public boolean check(){
       return this.currentLine>199;
    }
	public boolean IsEmpty(){
		return(this.tuples.isEmpty());
		
	}
    // insert method for pages 
    @SuppressWarnings("unused")
	public void insertIntoPage(Hashtable<String, Object> htblColNameVale, String KeyValue,int clusterKeyPrimary,Boolean isString) throws DBAppException{
    	Hashtable<String,Object> hashtable =null;
    	//System.out.println(clusterKeyPrimary);
    	Set<Entry<String, Object>> FirstTuple =htblColNameVale.entrySet();
		
		
		Iterator <Entry<String, Object>> Iterator= FirstTuple.iterator();
		Object HashValue=null;
		
		while (Iterator.hasNext()) {
			Entry<String, Object> en = Iterator.next();
		
		
			if (strClusteringKeyColumn.equals((String)en.getKey())) {
				//System.out.println((String)en.getKey());
				 HashValue=en.getValue();/// got the value of the primary key of the wanted to insert value
				 //System.out.println(HashValue);
				
			}
		}
		if(isString){ /////////////Remember to add currentLine
		
			if(!tuples.isEmpty()){
				for(int i = 0;i<tuples.size();i++){
	    			hashtable= tuples.get(i);
	    			Set<Entry<String,Object>> SecondTuple = hashtable.entrySet();
	    			Iterator <Entry<String, Object>> Iterator2= SecondTuple.iterator();
	    			
	    			//int ToIntHashCurrentValue=0;
	    			String HashCurrentValue="";
	    			while (Iterator2.hasNext()) {
	    				Entry<String, Object> en1 = Iterator2.next();
	    			
	    				
	    				if (strClusteringKeyColumn.equals(en1.getKey())) {
	    					 HashCurrentValue=(String)en1.getValue(); 
	    				  //ToIntHashCurrentValue=Integer.parseInt(HashCurrentValue);
	    				    
	    				}
	    				
	    			}
	    			
	    			
	    	 
	    	   
	    	    
	    	    if(HashCurrentValue.compareTo(KeyValue)>0 && i==0){
             	   tuples.addFirst(htblColNameVale);
             	   currentLine++;
             	   break;}
	    	 
	    	
	    	   if(HashCurrentValue.compareTo(KeyValue)>0){
	   				tuples.add(i,htblColNameVale);
	   				currentLine++;
	   		         break;}
	    	    if(HashCurrentValue.compareTo(KeyValue)<0 && i==tuples.size()-1){
   	    	
          	   tuples.addLast(htblColNameVale); 
          	   currentLine++;
          	   break;}
	    	
	    	
	    			
	    	else if(HashCurrentValue.compareTo(KeyValue)==0){
	    				throw new DBAppException("sorry there exists another entity with the same culstering key "); ///print already exists
	    				
	    	}
	    		
	    		
	    			
	    	}
				
				
			}
			else{
				tuples.add(0,htblColNameVale);
				currentLine++;
			}

			
		}
		
		
		
		else{
			///case int 
		 if(!tuples.isEmpty()){
			 //System.out.println("Here");
			 //System.out.println(htblColNameVale); //What empties tuples ? 
			
			for(int i = 0;i<tuples.size();i++){
    			hashtable= tuples.get(i);
    			Set<Entry<String,Object>> SecondTuple = hashtable.entrySet();
    			Iterator <Entry<String, Object>> Iterator2= SecondTuple.iterator();
    			
    			int ToIntHashCurrentValue=0;
    			while (Iterator2.hasNext()) {
    				Entry<String, Object> en1 = Iterator2.next();
    			
    				
    				if (strClusteringKeyColumn.equals(en1.getKey())) {
    				    ToIntHashCurrentValue = (int)en1.getValue();
    				}
    				
    			}
				
    	  if(ToIntHashCurrentValue>clusterKeyPrimary && i==tuples.size()-1){
                	   tuples.addFirst(htblColNameVale);
                	   currentLine++;
                	   break;}
    	  else  if(ToIntHashCurrentValue<clusterKeyPrimary && i==0){
    	    	
            	   tuples.addLast(htblColNameVale); 
            	   currentLine++;
            	   break;}
    	      if(ToIntHashCurrentValue<clusterKeyPrimary){
   				tuples.add(i,htblColNameVale);
   				currentLine++;
   		         break;
   				
   			}
               else if(ToIntHashCurrentValue==clusterKeyPrimary){
    				throw new DBAppException("sorry there exists another entity with the same culstering key "); ///print already exists
    				
    			}
    			
    		//Hello
    			
    		}}
		 
		 
		 else{
			tuples.add(0,htblColNameVale);
			currentLine++;
			//htblColNameVale.clear();
			//Only enters with the first insertion
		 }
		}
    		
        	
        	   ObjectOutputStream ObjectOutputStream;
       		try {
       			ObjectOutputStream = new ObjectOutputStream(
       					new FileOutputStream(
       							this.file));
       			//intially writing the page as a big fat serializable thing .. make this our page XD 
       			ObjectOutputStream.writeObject(this); // page already made
       			ObjectOutputStream.close();
       			
       		} catch (FileNotFoundException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		} catch (IOException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		}
       		
        
        }
    
    
    @SuppressWarnings("unused")
	public void loadPage(int Index,String tablename){
    	this.file = new File(tablename+Index+".csv");
    	boolean exists = file.exists();
      
		try {
			FileWriter writer = new FileWriter(tablename+Index+".csv");
			
           for(int i=0;i<tuples.size();i++){
        	   
           
			Set<Entry<String, Object>> FirstTuple = tuples.get(i).entrySet();
			
			
			Iterator <Entry<String, Object>> Iterator= FirstTuple.iterator();
			
			while (Iterator.hasNext()) {
				Entry<String, Object> en = Iterator.next();
				writer.append(en.getKey()+en.getValue()+",");
				
				
				
			}
			writer.append("\n");
            //System.out.println(tuples.get(i));
			// generate whatever data you want 
			
           }
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    
      }
   /* else{
    	
    	try
    	{
    	    String filename= tablename+Index+".csv";
    	    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
    	    for(int i=0;i<tuples.size();i++){
         	   
    	           
    			Set<Entry<String, Object>> FirstTuple = tuples.get(i).entrySet();
    			
    			
    			Iterator <Entry<String, Object>> Iterator= FirstTuple.iterator();
    			
    			while (Iterator.hasNext()) {
    				Entry<String, Object> en = Iterator.next();
    				fw.append(en.getKey()+en.getValue()+",");
    				
    				
    			}
    			fw.append("\n");

    			// generate whatever data you want 
                System.out.println(tuples.get(i));

    			
               }
    	    fw.flush();
			fw.close();
    	}
    	catch(IOException ioe)
    	{
    	    System.err.println("IOException: " + ioe.getMessage());
    	}
    }*/


    
    
    
    
    	
    

 public Page(int pageNo,File file, String strClusteringKeyColumn,LinkedList<Hashtable<String,Object>> tuples){
	 this.tuples=tuples;
	 this.file=file;
	 this.No=pageNo;
	 this.strClusteringKeyColumn=strClusteringKeyColumn;
 }
 
 
 
 
 /////////////////////////////////////////////////update page///////////////////////////////////////////////////////////////////////////////////////////

 
 @SuppressWarnings("unused")
public void updateIntoPage(Hashtable<String, Object> htblColNameVale, String KeyValue,int clusterKeyPrimary,Boolean isString) throws DBAppException{
 	Hashtable<String,Object> hashtable =null;
 	//System.out.println(clusterKeyPrimary);
 	Set<Entry<String, Object>> FirstTuple =htblColNameVale.entrySet();
		
		
		Iterator <Entry<String, Object>> Iterator= FirstTuple.iterator();
		Object HashValue=null;
		
		while (Iterator.hasNext()) {
			Entry<String, Object> en = Iterator.next();
		
		
			if (strClusteringKeyColumn.equals((String)en.getKey())) {
				//System.out.println((String)en.getKey());
				 HashValue=en.getValue();/// got the value of the primary key of the wanted to insert value
				 //System.out.println(HashValue);
				
			}
		}
		if(isString){ /////////////Remember to add currentLine
		
			if(!tuples.isEmpty()){
				for(int i = 0;i<tuples.size();i++){
	    			hashtable= tuples.get(i);
	    			Set<Entry<String,Object>> SecondTuple = hashtable.entrySet();
	    			Iterator <Entry<String, Object>> Iterator2= SecondTuple.iterator();
	    			
	    			//int ToIntHashCurrentValue=0;
	    			String HashCurrentValue="";
	    			while (Iterator2.hasNext()) {
	    				Entry<String, Object> en1 = Iterator2.next();
	    			
	    				
	    				if (strClusteringKeyColumn.equals(en1.getKey())) {
	    					 HashCurrentValue=(String)en1.getValue(); 
	    				  //ToIntHashCurrentValue=Integer.parseInt(HashCurrentValue);
	    				    
	    				}
	    				
	    			}
	    			
	    			
	    	 
	    	   
	    	    
	    	    if(HashCurrentValue.compareTo(KeyValue)==0 && i==0){
	    	    	tuples.removeFirst();
          	   tuples.addFirst(htblColNameVale);
          	   
          	   break;}
	    	      if(HashCurrentValue.compareTo(KeyValue)==0 && i==tuples.size()-1){
	    	tuples.removeLast();
       	   tuples.addLast(htblColNameVale); 
       	   
       	   break;}
	    	
	    	    if(HashCurrentValue.compareTo(KeyValue)==0){
	    		   tuples.remove(i);
	   				tuples.add(i,htblColNameVale);
	   				
	   		         break;}
	    	  
	    		
	    		
	    			
	    	}
				
				
			}
			else{
				throw new DBAppException("sorry this table is empty insert first");
				
			}

			
		}
		
		
		
		else{
			///case int 
		 if(!tuples.isEmpty()){
			 //System.out.println("Here");
			 //System.out.println(htblColNameVale); //What empties tuples ? 
			
			for(int i = 0;i<tuples.size();i++){
 			hashtable= tuples.get(i);
 			Set<Entry<String,Object>> SecondTuple = hashtable.entrySet();
 			Iterator <Entry<String, Object>> Iterator2= SecondTuple.iterator();
 			
 			int ToIntHashCurrentValue=0;
 			while (Iterator2.hasNext()) {
 				Entry<String, Object> en1 = Iterator2.next();
 			
 				
 				if (strClusteringKeyColumn.equals(en1.getKey())) {
 				    ToIntHashCurrentValue = (int)en1.getValue();
 				}
 				
 			}
				
 	  if(ToIntHashCurrentValue==clusterKeyPrimary && i==tuples.size()-1){
 		  			tuples.removeLast();
             	   tuples.addLast(htblColNameVale);
             	   
             	   break;}
 	  else  if(ToIntHashCurrentValue==clusterKeyPrimary && i==0){
 	    	tuples.removeFirst();
         	   tuples.addFirst(htblColNameVale); 
         	  
         	   break;}
 	      if(ToIntHashCurrentValue==clusterKeyPrimary){
 	    	  tuples.remove(i);
				tuples.add(i,htblColNameVale);
				
		         break;
				
			}
            
 			
 		//Hello
 			
 		}}
		 
		 
		 else{
			throw new DBAppException("sorry this table is empty insert first");
		 }
		}
 		
     	
     	   ObjectOutputStream ObjectOutputStream;
    		try {
    			ObjectOutputStream = new ObjectOutputStream(
    					new FileOutputStream(
    							this.file));
    			//intially writing the page as a big fat serializable thing .. make this our page XD 
    			ObjectOutputStream.writeObject(this); // page already made
    			ObjectOutputStream.close();
    			
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
     
     }
 
 ////////////////////////////////////////////////////////////////DeleteFromPage/////////////////////////////////////////////////////////////////////////
 
 	@SuppressWarnings("unused")
	public void DeleteFromPage(Hashtable<String, Object> htblColNameVale, String KeyValue,int clusterKeyPrimary,Boolean isString) throws DBAppException{
 		Hashtable<String,Object> hashtable =null;
 	 	//System.out.println(clusterKeyPrimary);
 	 	Set<Entry<String, Object>> FirstTuple =htblColNameVale.entrySet();
 			
 			
 			Iterator <Entry<String, Object>> Iterator= FirstTuple.iterator();
 			Object HashValue=null;
 			
 			while (Iterator.hasNext()) {
 				Entry<String, Object> en = Iterator.next();
 			
 			
 				if (strClusteringKeyColumn.equals((String)en.getKey())) {
 					//System.out.println((String)en.getKey());
 					 HashValue=en.getValue();/// got the value of the primary key of the wanted to insert value
 					 //System.out.println(HashValue);
 					
 				}
 			}
 			if(isString){ /////////////Remember to add currentLine
 			
 				if(!tuples.isEmpty()){
 					//System.out.println("Delete from page ");
 					for(int i = 0;i<tuples.size();i++){
 		    			hashtable= tuples.get(i);
 		    			Set<Entry<String,Object>> SecondTuple = hashtable.entrySet();
 		    			Iterator <Entry<String, Object>> Iterator2= SecondTuple.iterator();
 		    			
 		    			//int ToIntHashCurrentValue=0;
 		    			String HashCurrentValue="";
 		    			while (Iterator2.hasNext()) {
 		    				Entry<String, Object> en1 = Iterator2.next();
 		    			
 		    				
 		    				if (strClusteringKeyColumn.equals(en1.getKey())) {
 		    					 HashCurrentValue=(String)en1.getValue(); 
 		    				  //ToIntHashCurrentValue=Integer.parseInt(HashCurrentValue);
 		    					
 		    				    
 		    				}
 		    				
 		    			}
 		    			
 		    			
 		    	  
 		    		
 		    		
 		    	    
 		    	    if(HashCurrentValue.compareTo(KeyValue)==0 && i==0){
 		    	    	
 		    	    	tuples.removeFirst();
 		    	    	
 		    	    	currentLine--;
 	          	   //tuples.addFirst(htblColNameVale);
 	          	   
 	          	   break;}
 		    	      if(HashCurrentValue.compareTo(KeyValue)==0 && i==tuples.size()-1){
 		    	    		
 		    	tuples.removeLast();
 		    	
 		    	currentLine--;
 	       	   //tuples.addLast(htblColNameVale); 
 	       	   
 	       	   break;}
 		    	
 		    	    if(HashCurrentValue.compareTo(KeyValue)==0){
 		    		   tuples.remove(i);
 		    		  //System.out.println("DELETED mIDDLE");
 		    		  currentLine--;
 		   				//tuples.add(i,htblColNameVale);
 		   				
 		   		         break;}
 		    	  
 		    		
 		    		
 		    			
 		    	}
 					
 					
 				}
 				

 				
 			}
 			
 			
 			
 			else{
 				///case int 
 			 if(!tuples.isEmpty()){
 				 //System.out.println("Here");
 				 //System.out.println(htblColNameVale); //What empties tuples ? 
 				
 				for(int i = 0;i<tuples.size();i++){
 	 			hashtable= tuples.get(i);
 	 			Set<Entry<String,Object>> SecondTuple = hashtable.entrySet();
 	 			Iterator <Entry<String, Object>> Iterator2= SecondTuple.iterator();
 	 			
 	 			int ToIntHashCurrentValue=0;
 	 			while (Iterator2.hasNext()) {
 	 				Entry<String, Object> en1 = Iterator2.next();
 	 			
 	 				
 	 				if (strClusteringKeyColumn.equals(en1.getKey())) {
 	 				    ToIntHashCurrentValue = (int)en1.getValue();
 	 				}
 	 				
 	 			}
 					
 	 	  if(ToIntHashCurrentValue==clusterKeyPrimary && i==tuples.size()-1){
 	 		  			tuples.removeLast();
 	 		  		    currentLine--;
 	             	   //tuples.addLast(htblColNameVale);
 	             	   
 	             	   break;}
 	 	  else  if(ToIntHashCurrentValue==clusterKeyPrimary && i==0){
 	 		   System.out.println("Yeaaa");
 	 	    	tuples.removeFirst();
 	 	    	currentLine--;
 	         	//tuples.addFirst(htblColNameVale); 
 	         	  
 	         	   break;}
 	 	      if(ToIntHashCurrentValue==clusterKeyPrimary){
 	 	    	System.out.println("Yeaaa");
 	 	    	  tuples.remove(i);
 	 	    	  currentLine--;
 					//tuples.add(i,htblColNameVale);
 					
 			         break;
 					
 				}
 	            
 	 			
 	 		//Hello
 	 			
 	 		}}
 			 
 			 
 			 else{
 				throw new DBAppException("sorry this table is empty insert first");
 			 }
 			}
 	 		
 	     	
 	     	   ObjectOutputStream ObjectOutputStream;
 	    		try {
 	    			ObjectOutputStream = new ObjectOutputStream(
 	    					new FileOutputStream(
 	    							this.file));
 	    			//intially writing the page as a big fat serializable thing .. make this our page XD 
 	    			ObjectOutputStream.writeObject(this); // page already made
 	    			ObjectOutputStream.close();
 	    			
 	    		} catch (FileNotFoundException e) {
 	    			// TODO Auto-generated catch block
 	    			e.printStackTrace();
 	    		} catch (IOException e) {
 	    			// TODO Auto-generated catch block
 	    			e.printStackTrace();
 	    		}
 	    		
 	}
 
 
 

 
 
  public void InsertIntoDense(){
	  
	  
  }
  public void DeleteFromDense(){
	  
	  
  }
  
 public void UpdateFromDense(){
	  
	  
  }
}
    
























