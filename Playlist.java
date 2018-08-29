public class Playlist {
    public String name;
    
    public Track[] songs = new Track[100];
    
    public int SearchLastIndex(Track[] array) {
    	int i = 0;
    	
    	while(i<array.length) {
    		if(array[i]==null) {
    			return i;
    		}
    		i++;
    	}
    	return -1;
    }
    public void AñadirTrack () {
    	int index = SearchLastIndex(songs);
    	if(index == -1) {
    		for(int i = 0;i<songs.length;i++) {
        		
        		if(i == songs.length)
        		{
        			// Cada vez que tenemos que agrandar la biblioteca, agregamos 100 espacios mas
        			Track[] newsongs = new Track[songs.length + 100];
        			for(int j = 0; j < songs.length; ++j)
        			{
        				newsongs[j] = songs[j];
        			}
        			songs = newsongs;
        		}
        	}
    	}
    	// Aqui va lo que continua el codigo cuando no necesita agrandarlo
    	
    }
    
}