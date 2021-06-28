package chess.cli;

import chess.util.TextManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controls view and output of help commands
 */
public class Help {

	private static List<String> logo = new ArrayList<>();
	private static String[] header = { "Command EN", "Command DE", "Description" };
	private static List<String[]> rows = new ArrayList<>();
	private static Map<Integer, Integer> columnLengths = new HashMap<>();

	/**
	 * Enumeration of available cli commands
	 */
	public enum CliCommand {
		HELP_EN(TextManager.get("cli.helpCommandEn")), HELP_DE(TextManager.get("cli.helpCommandDe")),
		ENGLISH_EN(TextManager.get("cli.enCommandEn")), ENGLISH_DE(TextManager.get("cli.enCommandDe")),
		GERMAN_EN(TextManager.get("cli.deCommandEn")), GERMAN_DE(TextManager.get("cli.deCommandDe")),
		MENU_EN(TextManager.get("cli.menuCommandEn")), MENU_DE(TextManager.get("cli.menuCommandDe")),
		QUIT_EN(TextManager.get("cli.quitCommandEn")), QUIT_DE(TextManager.get("cli.quitCommandDe")),
		BEATEN_EN(TextManager.get("cli.beatenCommandEn")), BEATEN_DE(TextManager.get("cli.beatenCommandDe")),
		RESTART_EN(TextManager.get("cli.restartCommandEn")), RESTART_DE(TextManager.get("cli.restartCommandDe")),
		RESIGN_EN(TextManager.get("cli.resignCommandEn")), RESIGN_DE(TextManager.get("cli.resignCommandDe")),
		SAVE_EN(TextManager.get("cli.saveCommandEn")), SAVE_DE(TextManager.get("cli.saveCommandDe")),
		CANCEL_EN(TextManager.get("cli.load.cancelEn")), CANCEL_DE(TextManager.get("cli.load.cancelDe"));

		public final String raw;

		CliCommand(String command) {
			this.raw = command;
		}
	}

	/**
	 * Prints the help page logo
	 */
	private static void printLogo() {
		logo.add("██╗  ██╗███████╗██╗     ██████╗ ");
		logo.add("██║  ██║██╔════╝██║     ██╔══██╗");
		logo.add("███████║█████╗  ██║     ██████╔╝");
		logo.add("██╔══██║██╔══╝  ██║     ██╔═══╝ ");
		logo.add("██║  ██║███████╗███████╗██║     ");
		logo.add("╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ");
		System.out.println();
		logo.forEach(row -> System.out.println(row));
		System.out.println();
	}

	/**
	 * Adds a new field (row) to the help page
	 * 
	 * @param commandEnKey   the command in English
	 * @param commandDeKey   the command in German
	 * @param descriptionKey TextManager key for the command description
	 */
	private static void addHelpField(String commandEn, String commandDe, String descriptionKey) {
		rows.add(new String[] { commandEn, commandDe, TextManager.get(descriptionKey) });
	}

	/**
	 * Adds all available commands to the help page
	 */
	private static void addHelp() {

		rows.add(header);
		addHelpField(CliCommand.HELP_EN.raw, CliCommand.HELP_DE.raw, "cli.help.help");
		addHelpField(CliCommand.ENGLISH_EN.raw, CliCommand.ENGLISH_DE.raw, "cli.help.english");
		addHelpField(CliCommand.GERMAN_EN.raw, CliCommand.GERMAN_DE.raw, "cli.help.german");
		addHelpField(CliCommand.MENU_EN.raw, CliCommand.MENU_DE.raw, "cli.help.menu");
		addHelpField(CliCommand.QUIT_EN.raw, CliCommand.QUIT_DE.raw, "cli.help.quit");

		addHelpField(CliCommand.BEATEN_EN.raw, CliCommand.BEATEN_DE.raw, "cli.help.beaten");
		addHelpField(CliCommand.RESIGN_EN.raw, CliCommand.RESIGN_DE.raw, "cli.help.resign");
		addHelpField(CliCommand.RESTART_EN.raw, CliCommand.RESTART_DE.raw, "cli.help.restart");
		addHelpField(CliCommand.SAVE_EN.raw, CliCommand.SAVE_DE.raw, "cli.help.save");
	}

	/**
	 * Prepares the string format for aligning all the fields on the help page
	 * 
	 * @return the string format
	 */
	private static String getFormatString() {
		final StringBuilder formatString = new StringBuilder("");
		columnLengths.forEach((index, row) -> formatString.append(" %-").append(row).append("s "));
		formatString.append("\n");
		return formatString.toString();
	}

	/**
	 * Prints the header for the help 'table'
	 */
	private static void printHeader() {
		System.out.printf(getFormatString(), (Object[]) header);
		columnLengths.forEach((index, row) -> {
			for (int i = 0; i < row; i++) {
				System.out.print("-");
			}
		});
		System.out.println();
	}

	/**
	 * Calculates appropriate Length of each column by looking at width of data in
	 * each column.
	 * 
	 * Map columnLengths is <column_number, column_length>
	 */
	private static void calculateColumnLength() {
		rows.forEach(row -> {
			for (int i = 0; i < row.length; i++) {
				columnLengths.putIfAbsent(i, 0);
				if (columnLengths.get(i) < row[i].length()) {
					columnLengths.put(i, row[i].length() + ((i == 1) ? 10 : 5));
				}
			}
		});
		rows.remove(header);
	}

	/**
	 * Puts everything together and outputs the finished help page
	 */
	public static void print() {

		if (rows.isEmpty()) {
			addHelp();
		}
		calculateColumnLength();
		rows.remove(header);
		ConsoleColors.greenBoldColor();
		printLogo();
		System.out.println(TextManager.get("cli.help.title"));
		rows.forEach(row -> {
			if (rows.indexOf(row) == 0) {
				ConsoleColors.greenBoldColor();
				System.out.println("\n" + TextManager.get("cli.help.fromAnywhere") + "\n");
				ConsoleColors.greenColor();
				printHeader();
			}
			if (rows.indexOf(row) == 5) {
				ConsoleColors.greenBoldColor();
				System.out.println("\n" + TextManager.get("cli.help.fromGame") + "\n");
				ConsoleColors.greenColor();
				printHeader();
			}
			ConsoleColors.greenColor();
			System.out.printf(getFormatString(), (Object[]) row);
		});

		System.out.println("\n" + TextManager.get("cli.help.back"));
		Cli.scan.nextLine();
		ConsoleColors.resetColor();
	}

	/**
	 * Detects if entered input is one of the standard commands
	 * 
	 * @param command string to check
	 * @return command code
	 */
	public static int detectStandardCommands(String command) {

		String regexSep = "$|^";

		if (command.matches("^" + CliCommand.HELP_EN.raw + regexSep + CliCommand.HELP_DE.raw + "$")) {
			return 101;
		}
		if (command.matches("^" + CliCommand.ENGLISH_EN.raw + regexSep + CliCommand.ENGLISH_DE.raw + "$")) {
			return 102;
		}
		if (command.matches("^" + CliCommand.GERMAN_EN.raw + regexSep + CliCommand.GERMAN_DE.raw + "$")) {
			return 103;
		}
		if (command.matches("^" + CliCommand.MENU_EN.raw + regexSep + CliCommand.MENU_DE.raw + "$")) {
			return 104;
		}
		if (command.matches("^" + CliCommand.QUIT_EN.raw + regexSep + CliCommand.QUIT_DE.raw + "$")) {
			return 105;
		}

		return 0;
	}

	/**
	 * Detects if entered input is one of the game commands
	 * 
	 * @param command string to check
	 * @return command code
	 */
	public static int detectGameCommands(String command) {
		String regexSep = "$|^";

		if (command.matches("^" + CliCommand.BEATEN_EN.raw + regexSep + CliCommand.BEATEN_DE.raw + "$")) {
			return 106;
		}
		if (command.matches("^" + CliCommand.RESTART_EN.raw + regexSep + CliCommand.RESTART_DE.raw + "$")) {
			return 107;
		}
		if (command.matches("^" + CliCommand.RESIGN_EN.raw + regexSep + CliCommand.RESIGN_DE.raw + "$")) {
			return 108;
		}
		if (command.matches("^" + CliCommand.SAVE_EN.raw + regexSep + CliCommand.SAVE_DE.raw + "$")) {
			return 109;
		}

		int standardCode = detectStandardCommands(command);

		if (standardCode != 0) {
			Cli.handleStandardCommands(standardCode);
			return -1;
		}

		return 0;
	}

}
