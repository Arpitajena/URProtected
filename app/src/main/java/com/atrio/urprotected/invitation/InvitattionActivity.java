package com.atrio.urprotected.invitation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.atrio.urprotected.R;
import com.atrio.urprotected.invitation.DeepLinkManager;
import com.google.android.gms.appinvite.AppInviteInvitation;

public class InvitattionActivity extends AppCompatActivity implements
        DeepLinkManager.DeepLinkListener,View.OnClickListener
        {
            private static final String TAG = InvitattionActivity.class.getSimpleName();
            private static final int REQUEST_INVITE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitattion);
        // Invite button click listener
        findViewById(R.id.invite_button).setOnClickListener(this);

        DeepLinkManager deepLinkManager = new DeepLinkManager(this, this);
        deepLinkManager.checkForInvites(true);
    }

            /**
             * User has clicked the 'Invite' button, launch the invitation UI with the proper
             * title, message, and deep link
             */
            private void onInviteClicked() {
                Uri deepLink = Uri.parse(getString(R.string.invitation_deep_link));
                Uri deepLinkPlus = Uri.withAppendedPath(deepLink, "arpita");
                Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                        .setMessage(getString(R.string.invitation_message))
                        .setDeepLink(deepLinkPlus)
                        .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                        .setCallToActionText(getString(R.string.invitation_cta))
                        .build();
                startActivityForResult(intent, REQUEST_INVITE);
            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

                if (requestCode == REQUEST_INVITE) {
                    if (resultCode == RESULT_OK) {
                        // Get the invitation IDs of all sent messages
                        String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                        for (String id : ids) {
                            Log.d(TAG, "onActivityResult: sent invitation " + id);
                        }
                    } else {
                        // Sending failed or it was canceled, show failure message to the user
                        // [START_EXCLUDE]
                        showMessage(getString(R.string.send_failed));
                        // [END_EXCLUDE]
                    }
                }
            }

            private void showMessage(String msg) {
                /*ViewGroup container = (ViewGroup) findViewById(R.id.snackbar_layout);
                 Snackbar.make(container, msg, Snackbar.LENGTH_SHORT).show();*/
            }

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.invite_button:
                        onInviteClicked();
                        break;
                }
            }

            @Override
            public void onConnectionError(String errorMessage) {
                showMessage(getString(R.string.google_play_services_error));
            }
        }