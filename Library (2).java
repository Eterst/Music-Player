import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

/**
 * Clase Biblioteca encargada de contener y administrar la biblioteca de musica, tambien esta encargada de crear la biblioteca explorando el arbol de directorios
 * 
 * @author Rodolfo
 *
 */
public class Library 
{
	/**
	 * Almacena la biblioteca de canciones
	 */
	private Track[] library;
	
	/**
	 * Guarda la posicion que ocupara la proxima pista a agregar a la biblioteca
	 */
	private int nextTrack;
	
	/** 
	 * Si la biblioteca ha sido previamente creada en un archivo, se carga
	 * Si la biblioteca no ha sido previamente creada se debe construir a partir de una ruta indicada por el usuario
	 */
	public Library()
	{
		library = new Track[100];
		nextTrack = 0;
		File saveroot = new File("C:\\Users\\usuario\\Desktop\\playthis");
		File savefile = new File("C:\\Users\\usuario\\Desktop\\playthis\\filename.txt");
		if(!saveroot.exists()){
			try {
				saveroot.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(!savefile.exists()){
			try {
				savefile.createNewFile();
				buildLibrary();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			loadLibrary();
		}
		
		// TODO
	}
	
	/** 
	 * Carga la biblioteca a partir de un archivo de texto previamente creado
	 */
	public void loadLibrary()
	{
		String SaveLib = ReadFile("C:\\Users\\usuario\\Desktop\\playthis\\filename.txt"); //Modificar ruta dejando \\playthis\\filename.txt
		String[] song = SaveLib.split("\n");
		//Under construction
		// TODO
	}
	
	public void buildLibrary()
	{
		// La raiz se obtiene de un explorador de archivos usando la clase correspondiente de la biblioteca swing
		// Documentacion del JFileChooser: https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
		JFileChooser chooser = new JFileChooser();
		
		// Debemos garantizar que el explorador muestre unicamente directorios
		// https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html#setFileSelectionMode(int)
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// El showOpenDialog recibe como parametro un componente de javax.swing, en este caso no nos interesa por que vamos a usar JavaFX por eso ponemos null
		// El showOpenDialog retorna un valor distinto segun el modo en que se cerro el dialogo
		// https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html#showOpenDialog(java.awt.Component)
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			// Si el FileChooser se cerro correctamente, necesitamos el directorio que fue seleccionado para empezar a explorar
			File root = chooser.getSelectedFile();
			
			// Se explora el directorio seleccionado por el usuario de forma recursiva buscando todos los archivos con extension .mp3
			exploreLibrary(root);
		}
	}
	
	/**
	 * Explora de forma recursiva el directorio seleccionado por el usuario
	 * Agrega a la biblioteca unicamente los archivos de tipo .mp3
	 * 
	 * @param root
	 */
	private void exploreLibrary(File root)
	{
		File[] subFiles = root.listFiles();
		
		for(int i = 0; i < subFiles.length; ++i)
		{
			if(subFiles[i].isDirectory())
			{
				exploreLibrary(subFiles[i]);
			}
			else if(subFiles[i].getName().endsWith(".mp3"))
			{
				addToLibrary(subFiles[i]);
			}
		}
	}
	
	/**
	 * Agrega una nueva cancion a la biblioteca a partir del archivo que se encontro
	 * Si la biblioteca esta llena, se agregan 100 espacios nuevos a la biblioteca
	 * @param trackFile
	 */
	private void addToLibrary(String trackString) {
		//Under construction
	}
	private void addToLibrary(File trackFile)
	{
		// Si la biblioteca esta llena, tenemos que agrandarla
		if(nextTrack == library.length)
		{
			// Cada vez que tenemos que agrandar la biblioteca, agregamos 100 espacios mas
			Track[] newLibrary = new Track[library.length + 100];
			for(int i = 0; i < library.length; ++i)
			{
				// Movemos una por una las canciones que estan almacenadas en la biblioteca actual, hacia la nueva biblioteca
				// Se estan pasando las canciones por referencia, eso significa que no se estan copiando (no se esta haciendo memoria nueva para cada cancion independiente)
				newLibrary[i] = library[i];
			}
			// Reemplazamos la biblioteca actual por la nueva biblioteca
			library = newLibrary;
		}
		
		// Se almacena la nueva cancion en la posicion correspondiente y posteriormente se incrementa el indice de la cancion siguiente
		// Notese el uso del ++ y el uso del constructor de canciones que ya hace todo solo
		library[nextTrack++] = new Track(trackFile);
		Track Tcache = new Track(trackFile);
		System.out.println("Here: "+Tcache.getTitle());
		String save = Tcache.getTrackNo()+"|"+Tcache.getArtist()+"|"+Tcache.getAlbum()+"|"+Tcache.getTitle()+"|"+Tcache.getPath();
		WriteFile(save);
	}
	
	/**
	 * Imprime el largo de la biblioteca y todos los titulos de sus canciones
	 */
	// Metodos de crear y leer archivo txt
	public void WriteFile(String message) {
		File file = new File("C:\\Users\\usuario\\Desktop\\playthis\\filename.txt");//Modificar ruta dejando \\playthis\\filename.txt
		try {
			if (!file.exists()) {
				 file.createNewFile();
			}
			FileWriter fw = new FileWriter(file,true);
			PrintWriter bw = new PrintWriter(fw);
			bw.println(message);
			bw.close();
		}catch (Exception e) {
			 e.printStackTrace();
		 }
	}
	public File Filechoose() {
		File cache = null;
		JFileChooser chooser = new JFileChooser();
		  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		  if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
			  cache = chooser.getSelectedFile();
			}
		 return cache;
	}
	public static String ReadFile(String fileroot) {
		//File file = new File("C:\\Users\\usuario\\Desktop\\playthis\\filename.txt");
		File file = new File(fileroot);
		String text = "";
		String cache = "";
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while(true) {
				cache=br.readLine();
				if(cache!= null) {
					text = text+cache+"\n";
				}
				else {
					break;
				}
			}
			br.close();
			fr.close();
		}catch (Exception e) {
			 e.printStackTrace();
		 }
		return text;
	}
	public void PrintFile(File file) {
		String ruta = file.getAbsolutePath();
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
		try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File (ruta);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         while((linea=br.readLine())!=null)
	            System.out.println(linea);
	         
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
	}
	// Fin de metodos de crear y leer archivos
	public void printLibrary()
	{
		System.out.println("Library size: " + library.length);
		System.out.println("Track count: " + nextTrack);
		System.out.println("Wasted slots: " + (library.length - nextTrack));
		for(int i = 0; i < nextTrack; ++i)
		{
			System.out.println("Track No " + (i+1) + " : " + library[i].getTitle());
		}
	}
}
