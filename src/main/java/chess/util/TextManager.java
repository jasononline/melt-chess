package chess.util;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.MessageFormat;
import java.util.*;

/**
 * Replaces a placeholder for text values in GUI elements, depending on the
 * current language in the settings.
 */
public final class TextManager {

	// The current selected Locale
	private static final ObjectProperty<Locale> locale;

	static {
		locale = new SimpleObjectProperty<>(getDefaultLocale());
		locale.addListener((observable, oldLocale, newLocale) -> Locale.setDefault(newLocale));
	}

	/**
	 * Get the supported Locales
	 * 
	 * @return List of Locale objects
	 */
	public static List<Locale> getSupportedLocales() {
		return new ArrayList<>(Arrays.asList(Locale.ENGLISH, Locale.GERMAN));
	}

	/**
	 * Get the default Locale. This is the systems default if contained in the
	 * supported locales, german otherwise
	 * 
	 * @return the default Locale
	 */
	public static Locale getDefaultLocale() {
		Locale sysDefault = Locale.getDefault();
		return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.GERMAN;
	}

	/**
	 * Get the current Locale
	 * 
	 * @return the current Locale
	 */
	public static Locale getLocale() {
		return locale.get();
	}

	/**
	 * Set the current Locale
	 * 
	 * @param locale the new locale
	 */
	public static void setLocale(Locale locale) {
		localeProperty().set(locale);
		Locale.setDefault(locale);
	}

	/**
	 * Get Locale ObjectProperty
	 * 
	 * @return the current Locale ObjectProperty
	 */
	public static ObjectProperty<Locale> localeProperty() {
		return locale;
	}

	/**
	 * Gets the string with the given key from the resource bundle for the current
	 * locale and uses it at first argument to Message.format
	 * 
	 * @param key  message key
	 * @param args optional argumetns for the message
	 * @return localized formatted string
	 */
	public static String get(final String key, final Object... args) {
		ResourceBundle bundle = ResourceBundle.getBundle("chess/gui/lang/lang", getLocale());
		return MessageFormat.format(bundle.getString(key), args);
	}

	/**
	 * Creates a String Binding to a localized String for the given message bundle
	 * key
	 * 
	 * @param key  the message key
	 * @param args optional argumetns for the message
	 * @return String binding
	 */
	public static StringBinding createStringBinding(final String key, Object... args) {
		return Bindings.createStringBinding(() -> get(key, args), locale);
	}

	/**
	 * Edits a bound Button whose value is computed on language change
	 * 
	 * @param button Button that needs to be edited
	 * @param key    ResourceBundle key
	 * @param args   optional argument for message
	 */
	public static void computeText(Button button, final String key, final Object... args) {
		button.textProperty().bind(createStringBinding(key, args));
	}

	/**
	 * Edits a bound Label whose value is computed on language change
	 * 
	 * @param label Label that needs to be edited
	 * @param key   ResourceBundle key
	 * @param args  optional argument for message
	 */
	public static void computeText(Label label, final String key, final Object... args) {
		label.textProperty().bind(createStringBinding(key, args));
	}

}
