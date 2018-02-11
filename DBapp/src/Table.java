import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;


public class Table implements Serializable  {
	//should implement serializable 
	//should have an attribute arraylist of pages , a hashtable for type , a string for name 
    
	///should have an insert method called by DbApp .. it called an insert method in Page
	
	//should implement serializable 
	//should have an attribute arraylist of pages , a hashtable for type , a string for name 
    ///should have an insert method called by DbApp .. it called an insert method in Page
	
	String strClusteringKeyColumn; //table primary key
	String strTableName; //table name
	private static final long serialVersionUID = 1L;
	
	Hashtable<String,String> htblColNameType; // hashtable of the attributes and their types.. to put inserted in a metadata file
	public Table(String strTableName,
			String strClusteringKeyColumn, 
			Hashtable<String,String> htblColNameType){ //to do the exception 
		this.strTableName=strTableName;
		this.strClusteringKeyColumn=strClusteringKeyColumn;
		this.htblColNameType=htblColNameType;
		
		try {
			MakeMeta(htblColNameType);
			
			System.out.println("Created");
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void MakeMeta ( Hashtable<String,String> htblColNameTypehtblColNameType)throws DBAppException{
		
	 
		ObjectOutputStream ObjectOutputStream;
		try {
			ObjectOutputStream = new ObjectOutputStream(new FileOutputStream(
			new File(this.strTableName + ".ser"))); //intially writing the table as a big fat serializable thing .. make this our first page XD ..  when we merge i'll be put it in the array of pages
			ObjectOutputStream.writeObject(this);   /// instead of .strtablename it will be 0.ser
			ObjectOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*createIndex(strTableName, strKeyColName);
		try {
			FileWriter writer = new FileWriter("metadata.csv");

			Set<Entry<String, String>> set1 = htblColNameType.entrySet();
			Iterator<Entry<String, String>> iterator1 = set1.iterator();
			while (iterator1.hasNext()) {
				Entry<String, String> en = iterator1.next();
				writer.append(strTableName);
				writer.append(en.getKey());
				writer.append(en.getValue());
				if (strKeyColName.equals(en.getKey())) {
					writer.append("True");
				} else {
					writer.append("False");
				}
				writer.append("False");
				if (htblColNameRefs.contains(en.getKey())) {
					writer.append(htblColNameRefs.get(en.getKey()));
				} else {
					writer.append("null");
				}
				writer.append("\n");
			}

			// generate whatever data you want

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
	}*/
}}
