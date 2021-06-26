package chess.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SavingManager {

	public static String getDefaultFileName() {
		LocalDateTime myDateObj = LocalDateTime.now();
		String formattedDate = myDateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss"));
		return "MELT-CHESS_Saving_" + formattedDate + ".melt";
	}

	public static List<String> findSavings() {

		List<String> result = new ArrayList<>();
		String fileExt = ".melt";
		File[] listRoots = File.listRoots();
		for (int i = 0; i < listRoots.length; i++) {
			// System.out.println(listRoots[i]);

			// try (Stream<Path> walk = Files.walk(listRoots[i].toPath())) {
			// result = walk.filter(p -> !Files.isDirectory(p))
			// // this is a path, not string,
			// // this only test if path end with a certain path
			// // .filter(p -> p.endsWith(fileExtension))
			// // convert path to string first
			// .filter(Files::isReadable).filter(Files::isRegularFile).map(p ->
			// p.toString().toLowerCase())
			// .filter(f -> f.endsWith(fileExt)).collect(Collectors.toList());
			try (Stream<Path> pathStream = Files.find(listRoots[i].toPath(), Integer.MAX_VALUE,
					(p, basicFileAttributes) -> p.getFileName().toString().equalsIgnoreCase("*.melt"))) {
				// result = pathStream.collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return result;

	}

	public static void saveGame(Saving saving, String pathname) {
		File file = new File(pathname);
		saveGame(saving, file);
	}

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

	public static Saving loadGame(String pathname) {
		File file = new File(pathname);
		return loadGame(file);
	}

}
