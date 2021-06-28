package chess.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Contains methods to test the methods of the TextManaget class
 */
public class TextManagerTest {

	/**
	 * Method to test the expected list of supported locales
	 */
	@Test
	public void getSupportedLocales() {
		List<Locale> locales = Arrays.asList(Locale.ENGLISH, Locale.GERMAN);
		assertEquals(locales, TextManager.getSupportedLocales());
	}

	/**
	 * Method to test the expected default locale
	 */
	@Test
	public void getDefaultLocale() {
		Locale defaultLocale = TextManager.getSupportedLocales().contains(Locale.getDefault()) ? Locale.getDefault()
				: Locale.GERMAN;
		assertEquals(defaultLocale, TextManager.getDefaultLocale());
	}

	/**
	 * Method to test the expected string from key
	 */
	@Test
	public void getString() {
		String string = "Schach";
		TextManager.setLocale(Locale.GERMAN);
		assertEquals(string, TextManager.get("menu.title"));
		string = "Chess";
		TextManager.setLocale(Locale.ENGLISH);
		assertEquals(string, TextManager.get("menu.title"));
	}
}
