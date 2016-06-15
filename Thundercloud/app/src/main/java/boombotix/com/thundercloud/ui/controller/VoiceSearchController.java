package boombotix.com.thundercloud.ui.controller;
import boombotix.com.thundercloud.ui.fragment.VoiceSearchResultFragment;


/**
 * Controller interface for managing communication between player fragment and
 * search result fragment
 *
 * @author Jayd Saucedo
 */
public interface VoiceSearchController {
    /**
     * Adds fragment overlay for te voice search results, if it already exists it  will remove
     * it and recreate it.
     */
    public void addVoiceSearchResultFragment();

    /**
     * Updates text on the voice search fragment overlay
     *
     * @param s
     */
    public void updateVoiceSearchResultFragmentText(String s);

    /**
     * sets voice search fragment text to the query and allows editing via input box
     *
     * @param s
     */
    public void setVoiceSearchResultFragmentQuery(String s);


    /**
     * Gets viuce search result fragment, if it doesn't exist it will create it first
     *
     * @return the voice search result fragment
     */
    public VoiceSearchResultFragment setAndGetVoiceSearchResultFragment();

    /**
     * Searches fragment by tag and removes it
     *
     * @param tag
     */
    public void removeFragmentByTag(String tag);

    /**
     * Retrieves player fragment and stops any in process searches
     */
    public void stopPlayerSearch();

    /**
     * Hides search input from toolbar
     */
    public void hideSearch();

    /**
     * Shows search input in toolbar
     */
    public void showSearch();

    /**
     * Sets title of toolbar...hmm
     *
     * @param title
     */
    public void setToolbarTitle(String title);
}
