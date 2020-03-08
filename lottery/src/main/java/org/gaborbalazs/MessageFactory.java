package org.gaborbalazs;

import java.text.MessageFormat;
import java.util.Locale;

public class MessageFactory {

    private Locale locale;

    MessageFactory(Locale locale) {
        this.locale = locale;
    }

    public String create(String pattern, Object... arguments) {
        MessageFormat messageFormat = new MessageFormat(pattern, locale);
        return messageFormat.format(arguments);
    }
}
