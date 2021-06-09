package de.ktbl.android.sharedlibrary.view.viewmodel;

import androidx.lifecycle.ViewModel;

import timber.log.Timber;

/**
 * KTBL specific ViewModel with pre-defined functions for its lifecycle.
 * This can be a help to coordinate work, as well to ease (or complicate,
 * depending on your point of view) the divide responsibilities.
 * <p>
 * This ViewModel has 2 major functionalities:
 * (1) Loading-Data
 * Data has to be loaded from the database or mocked data has to be
 * generated. This is done via the following functions:
 * - {@link #loadData(String)}
 * - {@link #loadDataFromDB()}
 * - {@link #loadMockedData()}
 * (2) Saving-Data
 * Now, since the data has been loaded and the ViewModel is filled, the
 * data can be used within Views (e.g. via Bindings). If data has been
 * changed it can be saved back to the database. This is triggered via:
 * - {@link #saveDataToDB()}
 * <p>
 * Once data has been loaded it is fine to save it at any time.
 * It is also fine to load other data as well.
 */
public abstract class AbstractLoadSaveViewModel extends ViewModel {

    /**
     * Loads the ViewModels data from the database if possible
     *
     * @return true if the load was successful else false
     * @Worked_by s.gund
     */
    abstract boolean loadDataFromDB();

    /**
     * Loads mocked data if needed. Typically used if {@link #loadDataFromDB()}
     * was not successful.
     *
     * @return true if the mock was successful else false
     * @Worked_by k.schneider
     */
    abstract boolean loadMockedData();

    /**
     * Stores the viewmodels data to the database.
     *
     * @Worked_by s.gund
     */
    abstract protected void saveDataToDB();

    /**
     * Coordinates the calls between {@link #loadDataFromDB()} and
     * {@link #loadMockedData()}
     * @param TAG tag to be used for logging purposes. This is given via parameter to have as
     *            meaningful logs as possible.
     */
    @Deprecated()
    protected void loadData(final String TAG) {
        Timber.tag(TAG)
              .d("Trying to load data from the database.");
        boolean loadSuccess = this.loadDataFromDB();
        if (!loadSuccess) {
            Timber.tag(TAG)
                  .d("Could not load data from the database.");
            Timber.tag(TAG)
                  .d("Trying to mock data.");
            loadSuccess = this.loadMockedData();
            if (!loadSuccess) {
                Timber.tag(TAG)
                      .wtf("Could not mock data");
            }
        }
    }


}
