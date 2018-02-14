import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class Page implements Serializable {

    //should implement serializable ///
    int No;
    LinkedList<Hashtable<String,Object>> tuples;
    File file;
    int currentLine = 0;//the line to add data too
     String strClusteringKeyColumn = "";

    //constructor 
    public Page(int pageNo, int columnNumber,File file, String strClusteringKeyColumn) {
        this.No = pageNo;
        this.tuples = new LinkedList();// 200 max no of tuples
        this.file = file;
        this.strClusteringKeyColumn = strClusteringKeyColumn;
}
    //check for page if full or not 
    //if full it calls WritePage 
    @SuppressWarnings("static-access")
	public boolean check(){
       return this.currentLine>=200;
    }
    // insert method for pages 
    public void insertIntoPage(Hashtable<String, Object> htblColNameVale, int clusterKeyPrimary,Boolean isString) throws DBAppException{
    	Hashtable<String,Object> hashtable =null;
    	
    	Set<Entry<String, Object>> FirstTuple =htblColNameVale.entrySet();
		
		
		Iterator <Entry<String, Object>> Iterator= FirstTuple.iterator();
		Object HashValue=null;
		
		while (Iterator.hasNext()) {
			Entry<String, Object> en = Iterator.next();
		
		
			if (strClusteringKeyColumn.equals(en.getKey())) {
				 HashValue=en.getValue(); /// got the value of the primary key of the wanted to insert value
				
			}
		}
		if(isString){
		
				
				for(int i = 0;i<tuples.size();i++){
	    			hashtable= tuples.get(i);
	    			Set<Entry<String,Object>> SecondTuple = hashtable.entrySet();
	    			Iterator <Entry<String, Object>> Iterator2= SecondTuple.iterator();
	    			
	    			int ToIntHashCurrentValue=0;
	    			while (Iterator2.hasNext()) {
	    				Entry<String, Object> en1 = Iterator2.next();
	    			
	    				
	    				if (strClusteringKeyColumn.equals(en1.getKey())) {
	    					String HashCurrentValue=(String)en1.getValue(); 
	    				  ToIntHashCurrentValue=Integer.parseInt(HashCurrentValue);
	    				    
	    				}
	    				
	    			}
	    			if(ToIntHashCurrentValue==clusterKeyPrimary){
	    				throw new DBAppException(); ///print already exists
	    				
	    			}
	    			else if(ToIntHashCurrentValue>clusterKeyPrimary){
	    				tuples.add(i,htblColNameVale);
	    				
	    			}
	    		
	    			
	    		}

			
		}
		
		
		
		else{
			///case int 
		 if(!tuples.isEmpty()){
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
    			if(ToIntHashCurrentValue==clusterKeyPrimary){
    				throw new DBAppException(); ///print already exists
    				
    			}
    			else if(ToIntHashCurrentValue>clusterKeyPrimary){
    				tuples.add(i,htblColNameVale);
    				
    			}
    		
    			
    		}}
		 else{
			tuples.add(0,htblColNameVale);
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
       		
        currentLine++;
        }
    
    	
   }
    



