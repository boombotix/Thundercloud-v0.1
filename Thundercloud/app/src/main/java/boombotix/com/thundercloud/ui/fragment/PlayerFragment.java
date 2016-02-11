package boombotix.com.thundercloud.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.fasterxml.jackson.databind.JsonNode;
import com.hound.android.libphs.PhraseSpotterReader;
import com.hound.android.sdk.VoiceSearch;
import com.hound.android.sdk.VoiceSearchInfo;
import com.hound.android.sdk.VoiceSearchListener;
import com.hound.android.sdk.VoiceSearchState;
import com.hound.android.sdk.audio.SimpleAudioByteStreamSource;
import com.hound.android.sdk.util.HoundRequestInfoFactory;
import com.hound.core.model.sdk.HoundRequestInfo;
import com.hound.core.model.sdk.HoundResponse;
import com.hound.core.model.sdk.PartialTranscript;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.activity.MainActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;


public class PlayerFragment extends BaseFragment {
    public static final String TAG = "PlayerFragment";
    @Bind(R.id.okhound_button)
    ImageButton okhoundButton;
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    public static final String CLIENT_ID = "c_8C8K2wLaES-YQIyZ0O6Q==";
    public static final String CLIENT_KEY = "rPtyAYtTw904zafr1zBNNx1qLEVNnSlisF_BCewyq09dXyY7FhpkxVgctYVc7l4tlWlGIx7z04QSyFxjAZEHTA==";

    private PhraseSpotterReader phraseSpotterReader;
    private VoiceSearch voiceSearch;

    private JsonNode lastConversationState;

    public PlayerFragment() {
        // Required empty public constructor
    }

    public static PlayerFragment newInstance() {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        ButterKnife.bind(this, view);

        okhoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("PlayerFragment", "Clicky click");
                MainActivity activity = (MainActivity) getActivity();
                activity.addVoiceSearchFragmentOverlay();

                if (voiceSearch == null) {
//                    resetUIState();

                    stopPhraseSpotting();
                    startSearch();
                } else {
                    // voice search has already started.
                    if (voiceSearch.getState() == VoiceSearchState.STATE_STARTED) {
                        voiceSearch.stopRecording();
                    } else {
                        voiceSearch.abort();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        startPhraseSpotting();
    }

    private void startPhraseSpotting() {
        phraseSpotterReader = new PhraseSpotterReader(new SimpleAudioByteStreamSource());
        phraseSpotterReader.setListener(phraseSpotterListener);
        phraseSpotterReader.start();
    }

    private void stopPhraseSpotting() {
        phraseSpotterReader.stop();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (voiceSearch != null) {
            voiceSearch.abort();
        }
        // if we don't, we must still be listening for "ok hound" so teardown the phrase spotter
        else if (phraseSpotterReader != null) {
            stopPhraseSpotting();
        }
    }


    public void stopSearch(){
        if (voiceSearch != null) {
            voiceSearch.abort();
        }
    }

    private HoundRequestInfo getHoundRequestInfo() {
        final HoundRequestInfo requestInfo = HoundRequestInfoFactory.getDefault(getContext());

        requestInfo.setUserId("User ID");
        requestInfo.setRequestId(UUID.randomUUID().toString());

        // for the first search lastConversationState will be null, this is okay.  However any future
        // searches may return us a conversation state to use.  Add it to the request info when we have one.
        requestInfo.setConversationState(lastConversationState);

        return requestInfo;
    }

    private void startSearch() {
        if (voiceSearch != null) {
            return; // We are already searching
        }


        voiceSearch = new VoiceSearch.Builder()
                .setRequestInfo(getHoundRequestInfo())
                .setAudioSource(new SimpleAudioByteStreamSource())
                .setClientId(CLIENT_ID)
                .setClientKey(CLIENT_KEY)
                .setListener(voiceListener)
                .build();
// TODO add listening prompt
//        textView.setText("Listening...");
//        button.setText("Stop Recording");

        voiceSearch.start();
    }

    private final PhraseSpotterReader.Listener phraseSpotterListener = new PhraseSpotterReader.Listener() {
        @Override
        public void onPhraseSpotted() {

            // It's important to note that when the phrase spotter detects "Ok Hound" it closes
            // the input stream it was provided.

            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    startSearch();
                }
            });
        }

        @Override
        public void onError(final Exception ex) {

            // for this sample we don't care about errors from the "Ok Hound" phrase spotter.

        }

    };

    private final VoiceSearchListener voiceListener = new VoiceSearchListener() {
        @Override
        public void onTranscriptionUpdate(final PartialTranscript transcript) {
            final StringBuilder str = new StringBuilder();
            switch (voiceSearch.getState()) {
                case STATE_STARTED:
                    str.append("Listening...");
                    break;
                case STATE_SEARCHING:
                    str.append("Receiving...");
                    break;
                default:
                    str.append("Unknown");
                    break;
            }
            str.append("\n\n");
            str.append(transcript.getPartialTranscript());

            updateText(str.toString());
        }

        @Override
        public void onResponse(final HoundResponse response, final VoiceSearchInfo info) {
            voiceSearch = null;
//            resetUIState();

            if (!response.getResults().isEmpty()) {
                // Save off the conversation state.  This information will be returned to the server
                // in the next search. Note that at some point in the future the results CommandResult list
                // may contain more than one item. For now it does not, so just grab the first result's
                // conversation state and use it.
                lastConversationState = response.getResults().get(0).getConversationState();
            }

//            textView.setText("Received response...displaying the JSON");

//            // We put pretty printing JSON on a separate thread as the server JSON can be quite large and will stutter the UI
//            JsonNode tracks = response.getResults().get(0).getNativeData().get("Tracks").get(0);
//            JsonNode thirdPartyInfo = tracks.get("MusicThirdPartyIds");
//            String spotifyId;
//            for(final JsonNode infoNode: thirdPartyInfo){
//                if(infoNode.get("MusicThirdParty").get("Name").textValue().equals("Spotify")){
//                    spotifyId = infoNode.get("Ids").get(0).textValue();
////                    mPlayer.play(spotifyId);
//                    break;
//                }
//            }

//            // Not meant to be configuration change proof, this is just a demo
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String message = "";
//                    try {
//                        message += "Response\n\n" + new JSONObject(info.getContentBody()).toString(4);
//                    }
//                    catch (final JSONException ex) {
////                        textView.setText("Bad JSON\n\n" + response);
//                        message += "Bad JSON\n\n" + response;
//                    }
//
//                    final String finalMessage = message;
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            textView.setText(finalMessage);
//                        }
//                    });
//                }
//            }).start();

            startPhraseSpotting();
        }

        @Override
        public void onError(final Exception ex, final VoiceSearchInfo info) {
            voiceSearch = null;
//            resetUIState();
//            textView.setText(OK_HOUND_TEXT + "\n\n" + exceptionToString(ex));

            startPhraseSpotting();
        }

        @Override
        public void onRecordingStopped() {
//            button.setText("Receiving");
//            textView.setText("Receiving...");
        }

        @Override
        public void onAbort(final VoiceSearchInfo info) {
            voiceSearch = null;
//            resetUIState();
//            textView.setText("Aborted");

            startPhraseSpotting();
        }
    };

    private void updateText(String s) {
        ((MainActivity) getActivity()).updateVoiceSearchFragmentOverlayText(s);
    }

}
