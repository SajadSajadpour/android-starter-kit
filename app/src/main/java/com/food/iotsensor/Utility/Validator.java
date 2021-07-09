package com.food.iotsensor.Utility;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.util.regex.Pattern;

/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public final class Validator
{
    public final class Regex
    {
        public static final String ALPHABET = "\\p{Alpha}";
        public static final String INTEGER = "^[0-9]*$";
        public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        public static final String SYMBOL = "[#?!@$%^&*-]";
        
        public static final String CONTAIN_ALPHABET = "^(?=.*[a-zA-Z])$";
        
        public static final String CONTAIN_CAPITAL_LETTER = "[A-Z]";
        public static final String CONTAIN_SMALL_LETTER = "[a-z]";
        public static final String CONTAIN_NUMBER = "[0-9]";
//        public static final String CONTAIN_SYMBOL = "[#?!@$%^&*-]";
        public static final String CONTAIN_SYMBOL = "[#@$%-]";
    }
    
    public static boolean isEmpty(@Nullable final String string)
    {
        return string == null || string.equalsIgnoreCase("");
    }
    
    public static boolean isValidEmail(final String email)
    {
        return !isEmpty(email) && containPattern(email, Regex.EMAIL);
    }
    
    public static boolean isValidPin(final String pin)
    {
        return !isEmpty(pin) && pin.length() >= 6;
    }
    
    public static boolean isValidFourPin(final String pin)
    {
        return !isEmpty(pin) && pin.length() >= 4;
    }
    
    public static boolean isValidPostcode(final String postcode)
    {
        return postcode != null && !postcode.equalsIgnoreCase("") && postcode.length() >= 5 && isValidInteger(postcode);
    }
    
    public static boolean isValidInteger(final String text)
    {
        return text != null && !text.equalsIgnoreCase("") && containPattern(text, Regex.INTEGER);
    }
    
    public static boolean isValidContact(final String text)
    {
        return text != null && !text.equalsIgnoreCase("") && text.length() >= 9 && isValidInteger(text);
    }
    
    public static boolean isValidUrl(final String url)
    {
        return (url != null && !url.equalsIgnoreCase(""))
                && (URLUtil.isValidUrl(url) || (url.startsWith("www.") || url.startsWith("http://") || url.startsWith("https://")));
    }
    
    
    // Contain
    public static boolean isContainAlphabet(final String target)
    {
        return !isEmpty(target) && hasPattern(target, Regex.ALPHABET);
    }
    
    public static boolean isContainCapitalAlphabet(final String target)
    {
        return !isEmpty(target) && hasPattern(target, Regex.CONTAIN_CAPITAL_LETTER);
    }
    
    public static boolean isContainSmallAlphabet(final String target)
    {
        return !isEmpty(target) && hasPattern(target, Regex.CONTAIN_SMALL_LETTER);
    }
    
    public static boolean isContainNumber(final String target)
    {
        return !isEmpty(target) && hasPattern(target, Regex.CONTAIN_NUMBER);
    }
    
    public static boolean isContainSymbol(final String target)
    {
        return !isEmpty(target) && hasPattern(target, Regex.CONTAIN_SYMBOL);
    }
    
    
    
    @Deprecated
    public static boolean containPattern(@NonNull final String target, @NonNull final String regex)
    {
        return Pattern.compile(regex).matcher(target).matches();
    }
    
    public static boolean isPattern(@NonNull final String target, @NonNull final String regex)
    {
        return Pattern.compile(regex).matcher(target).matches();
    }
    
    public static boolean hasPattern(@NonNull final String target, @NonNull final String regex)
    {
        return Pattern.compile(regex).matcher(target).find();
    }
    
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable(final Context context)
    {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        
        return connectivityManager != null
                && connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
