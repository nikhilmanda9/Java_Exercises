

//Design 1
public class HashTable {
	// insert is O(N)
	// binary search is O(logN)

	Node[] hashMap; // stores a hashMap array of keyword nodes
	int tableSize; // stores the size of the hash table

	public HashTable(int numKeywords) {

		tableSize = numKeywords; // initializes the number of all keywords to the size of the hash table

		hashMap = new Node[tableSize];
	}

	// inserts a keyword node into the hash table
	public void insert(String keyword, FileData fd) 
	{
		Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);

		int key = hashFunction_1(keyword); // receives the key to be inserted into the array
		int index = key % tableSize; // stores the location of the key

		if (hashMap[index] == null) //the keyword doesn't exist
		{
			hashMap[index] = new Node(keyword); //insert the new keyword
			hashMap[index].record = recordToAdd; //update the record for the new keyword
		} 
		else if (keyword.compareTo(hashMap[index].keyword) == 0) //the keyword already exists
		{
			hashMap[index].update(recordToAdd); //just update the record for the keyword
		} 
		else 
		{
			// collision occurs between two distinct keywords here
			int j = 0;
			int nextIndex = (key + hashFunction_2(j)) % tableSize;
			while (hashMap[nextIndex] != null && keyword.compareTo(hashMap[nextIndex].keyword) != 0) 
			{
				nextIndex = (key + hashFunction_2(++j)) % tableSize;
			}

			// insert the keyword in the new location
			if (hashMap[nextIndex] == null && nextIndex < tableSize) 
			{
				hashMap[nextIndex] = new Node(keyword);
				hashMap[nextIndex].record = recordToAdd;
			} 
			else if (keyword.compareTo(hashMap[nextIndex].keyword) == 0) 
			{
				hashMap[nextIndex].update(recordToAdd);
			}

		}

	}

	// Retrieves all the records for a specific keyword node
	public Record get_records(String keyword) 
	{
		int key = hashFunction_1(keyword);
		int index = key % tableSize;

		try {
			if (hashMap[index] == null) 
			{
				return null; // the keyword node doesn't exist
			}
			if (keyword.compareTo(hashMap[index].keyword) == 0) 
			{
				return hashMap[index].record; // return the records for the specific keyword node
			}

			int j = 0;
			int nextIndex = (key + hashFunction_2(j)) % tableSize;

			//use quadratic probing to probe a number of times until an index is available for another keyword
			while (hashMap[nextIndex] != null && keyword.compareTo(hashMap[nextIndex].keyword) != 0) 
			{
				nextIndex = (key + hashFunction_2(j++)) % tableSize; //update the index if a collision occurs between two distinct keywords
			}
			if (keyword.compareTo(hashMap[nextIndex].keyword) == 0) 
			{
				return hashMap[nextIndex].record;
			}

		} catch (NullPointerException e) {
			// do nothing
		}

		return null;
	}

	//this hash function converts the string keyword to an integer
	private int hashFunction_1(String keyword) {
		int key = 0;
		for (int i = 0; i < keyword.length(); i++) 
		{
			//allocates space in the hash table by accumulating the ascii characters of each letter in the keyword
			key += (int) keyword.charAt(i) * (int) Math.pow(tableSize, i);
		}

		return Math.abs(key); //returns a positive integer for the key
	}

	//this hash function supports quadratic probing to handle collisions between two distinct keywords
	private int hashFunction_2(int step) {
		return (int) Math.pow(step, 2);
	}

	public void print() 
	{
		for (int i = 0; i < tableSize; i++) {
			try {
				boolean isDoubleDigit = (i / 10 == 0 && i / 100 == 0) ? false : true;
				if (isDoubleDigit) {
					System.out.printf("[%d]: %s\n", i, hashMap[i].keyword);
				} else {
					System.out.printf("[%d]: %s\n", i, hashMap[i].keyword);
				}
				Record rec = hashMap[i].record;
				while (rec != null) {
					System.out.printf("\t-->%s\n", rec.title);
					rec = rec.next;
				}
			} catch (NullPointerException e) {
				// do nothing
			}
		}
	}

	private class Node {

		String keyword;
		Record record;

		private Node(String k) {
			// TODO Initialize a new Node with keyword k.
			keyword = k;
			record = null;
		}

		private void update(Record r) {
			// TODO Adds the Record r to the linked list of records. You do not have to
			// check if the record is already in the list.
			// HINT: Add the Record r to the front of your linked list.
			if (record == null) {
				record = r;
			} else {
				r.next = record;
				record = r;
			}

		}

	}

}

