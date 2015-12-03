package com.dammi.dammi.search;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by trees on 12/1/15.
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider
{
    public final static String AUTHORITY="com.dammi.dammi.search.MySuggestionProvider";
    public final static int MODE=DATABASE_MODE_QUERIES;

    public MySuggestionProvider()
    {
        setupSuggestions(AUTHORITY, MODE);
    }
}
