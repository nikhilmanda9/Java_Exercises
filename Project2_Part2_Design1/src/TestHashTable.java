import java.io.*;

//Design 1
public class TestHashTable {
	BufferedReader reader;
	HashTable table;
	int numKeywords;

	public static void main(String[] args) {

		TestHashTable T = new TestHashTable("datafile.txt");

		// be sure to test for non-existent keywords
		System.out.println("Hash Table of Keywords: ");
		T.table.print();
		System.out.println();

		Record rec = T.table.get_records("learning");
		if (rec == null) {
			System.out.println("Record not found");
		} else {
			System.out.printf("Records for %s :\n", "learning");
			rec.print();
		}
		
		System.out.println();
		rec = T.table.get_records("matching");
		if (rec == null) {
			System.out.println("Record not found");
		} else {
			System.out.printf("Records for %s :\n", "matching");
			rec.print();
		}
		
		//T.table.print();

	}

	/*
	 * Returns the next data record (a whole record object) in the data input file.
	 * Returns null if there is not such record. Hence a null indicates end of file
	 * or some error Error message will be displayed on the screen. DO NOT CHANGE
	 * THIS FUNCTION!
	 */
	public FileData readNextRecord() {
		if (reader == null) {
			System.out.println("Error: You must open the file first.");
			return null;
		} else {
			FileData readData;
			try {
				String data = reader.readLine();
				if (data == null)
					return null;
				int readNo = Integer.parseInt(data);
				readData = new FileData(readNo, reader.readLine(), reader.readLine(),
						Integer.parseInt(reader.readLine()));
				for (int i = 0; i < readData.keywords.length; i++) {
					readData.addKeyword(reader.readLine());
				}
				String space = reader.readLine();
				if ((space != null) && (!space.trim().equals(""))) {
					System.out.println("Error in file format");
					return null;
				}
			} catch (NumberFormatException e) {
				System.out.println("Error Number Expected! ");
				return null;
			} catch (Exception e) {
				System.out.println("Fatal Error: " + e);
				return null;
			}
			return readData;
		}
	}

	public TestHashTable(String filename) {
		try {

			this.reader = new BufferedReader(new FileReader(filename));

			/* READS DATAFILE.TXT INTO DATASTRUCTURE */
			FileData fd;
			while ((fd = this.readNextRecord()) != null) {
				for (int i = 0; i < fd.keywords.length; i++) {

					numKeywords++;
				}
			}

			table = new HashTable(numKeywords);

			this.reader = new BufferedReader(new FileReader(filename));
			while ((fd = this.readNextRecord()) != null) {
				for (int i = 0; i < fd.keywords.length; i++) {
					table.insert(fd.keywords[i], fd);

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
