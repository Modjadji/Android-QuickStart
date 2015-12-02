package modjadji.org.quickstart.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public final class AmountFormatter {
    private static AmountFormatter sInstance = null;

    private DecimalFormat mCurrencyFormatter;

    private AmountFormatter(Locale locale) {
        mCurrencyFormatter = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
    }

    public static AmountFormatter getInstance() {
        if (sInstance == null) {
            sInstance = new AmountFormatter(Locale.getDefault());
        }
        return sInstance;
    }

    public String formatAmount(double amount) {
        return mCurrencyFormatter.format(amount);
    }
}
