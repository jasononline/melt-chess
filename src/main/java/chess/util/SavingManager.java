package chess.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Loads and saves saving objects
 */
public class SavingManager {

	/**
	 * Creates default filename for saving
	 * 
	 * @return saving name
	 */
	public static String getDefaultFileName() {
		LocalDateTime myDateObj = LocalDateTime.now();
		String formattedDate = myDateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss"));
		return "MELT-CHESS_Saving_" + formattedDate + ".melt";
	}

	/**
	 * Finds saving files in the given path
	 * 
	 * @param path path where to search saving files
	 * @return array of founded saving files
	 */
	public static File[] findSavings(String path) {

		if (!Files.isDirectory(Paths.get(path))) {
			throw new IllegalArgumentException("Path must be a directory!");
		}

		File[] files = new File(path).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".melt");
			}
		});

		return files;
	}

	/**
	 * Saves the game to the gevin path
	 * 
	 * @param saving   saving object to save
	 * @param pathname path where the saving file schould be saved
	 */
	public static void saveGame(Saving saving, String pathname) {
		File file = new File(pathname + File.separator + getDefaultFileName());
		saveGame(saving, file);
	}

	/**
	 * Saves the game to the given file
	 * 
	 * @param saving saving object to save
	 * @param file   file where the saved game should be written
	 */
	public static void saveGame(Saving saving, File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

			objOut.writeObject(saving);

			objOut.close();
			fileOut.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error initializing stream");
			e.printStackTrace();
		}
	}

	/**
	 * Loads game from the given file
	 * 
	 * @param file saving file
	 * @return saving object from the file
	 */
	public static Saving loadGame(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);

			Saving saving = (Saving) objIn.readObject();

			objIn.close();
			fileIn.close();

			return saving;

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error initializing stream");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Loads game from the given path
	 * 
	 * @param pathname path to the savings file
	 * @return saving object from the file
	 */
	public static Saving loadGame(String pathname) {
		File file = new File(pathname);
		return loadGame(file);
	}

}
