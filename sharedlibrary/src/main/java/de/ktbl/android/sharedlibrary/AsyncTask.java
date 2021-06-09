package de.ktbl.android.sharedlibrary;

import androidx.arch.core.util.Function;

import org.jetbrains.annotations.NotNull;


/**
 * Simple implementation of the android.os.AsyncTask class providing a Lambda interface.
 *
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 */
public class AsyncTask<Params, Progress, Result> extends android.os.AsyncTask<Params, Progress,
        Result> {

    private final Function<Params[], Result> doInBackgroundLambda;

    /**
     * @param doInBackgroundLambda Lambda {@code Function<I, O>}
     */
    public AsyncTask(@NotNull Function<Params[], Result> doInBackgroundLambda) {
        this.doInBackgroundLambda = doInBackgroundLambda;
    }

    @SafeVarargs
    @Override
    protected final Result doInBackground(Params... params) {
        return this.doInBackgroundLambda.apply(params);
    }
}
