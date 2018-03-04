import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

public class DenseIndex {
	String ColumnName;

	LinkedList<Entity> Densetuples; // Gonna be create in page 200 elements also
									// of entities
	File file;

	public DenseIndex(String ColumnName) {

		this.ColumnName = ColumnName;

		this.Densetuples = new LinkedList<Entity>();

		// Tuples will contain entities

	}

	public void InsertIntoDense(Entity New, boolean isString) {
		Entity Entity = null;

		if (isString) { // ///////////Remember to add currentLine
			String Value = (String) New.Value;
			if (!Densetuples.isEmpty()) {
				for (int i = 0; i < Densetuples.size(); i++) {
					Entity = Densetuples.get(i);

					Object CurrentKey = Entity.Value;
					String Current = (String) CurrentKey;

					if (Current.compareTo(Value) > 0 && i == 0) {

						Densetuples.addFirst(New);

						break;
					}

					if (Current.compareTo(Value) >= 0) { // Changed to Or equal
						Densetuples.add(i, New);

						break;
					}
					if (Current.compareTo(Value) < 0
							&& i == Densetuples.size() - 1) {

						Densetuples.addLast(New);

						break;
					}

					/*
					 * else if(HashCurrentValue.compareTo(KeyValue)==0){ throw
					 * new DBAppException(); ///print already exists
					 * 
					 * }
					 */

				}

			} else {
				Densetuples.add(0, Entity);

			}

		}

		else {
			// /case int
			int Value = (int) New.Value;

			if (!Densetuples.isEmpty()) {
				// System.out.println("Here");
				// System.out.println(htblColNameVale); //What empties tuples ?

				for (int i = 0; i < Densetuples.size(); i++) {
					Entity = Densetuples.get(i);
					int currentValue = (int) Entity.Value;
					if (currentValue > Value && i == Densetuples.size() - 1) {
						Densetuples.addFirst(Entity);

						break;
					} else if (currentValue < Value && i == 0) {

						Densetuples.addLast(Entity);
						break;
					}
					if (currentValue <= Value) {
						Densetuples.add(i, Entity);
						break;

					}

				}
			}

			else {
				Densetuples.add(0, Entity);
			}
		}

	}

	public void loadDense(int Index, String columnName) {
		this.file = new File(columnName + "Dense" + Index + ".csv");
		boolean exists = file.exists();

		try {
			FileWriter writer = new FileWriter(columnName + Index + ".csv");

			for (int i = 0; i < Densetuples.size(); i++) {

				writer.append("\n");
				System.out.println(Densetuples.get(i));
				// generate whatever data you want

			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
