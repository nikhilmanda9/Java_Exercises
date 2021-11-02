
public class Record{
    int id;
    String title;
    String author;
    Record next;

    Record(int i, String t, String a, Record r){
        this.id = i;
        this.title = t;
        this.author = a;
        this.next = r;
    }
    
    public void print(){
    	Record rec = this;
    	while(rec != null)
    	{
    		System.out.printf("\t-->%s by: %s\n", rec.title, rec.author);
    		rec = rec.next;
    	}
    }
    

}
