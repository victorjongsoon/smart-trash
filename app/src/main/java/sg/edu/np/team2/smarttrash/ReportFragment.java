package sg.edu.np.team2.smarttrash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReportFragment extends Fragment {
    private static final int PICK_FROM_GALLERY = 101;
    private EditText mEditTextMessage;
    private View view;
    String attachmentFile;
    int columnIndex;
    Uri URIpath = null;
    private Button buttonSend;
    private Button buttonSelectImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        view = lf.inflate(R.layout.fragment_report, container, false);
        //mEditTextTo = view.findViewById(R.id.edit_text_to);
        //mEditTextSubject = view.findViewById(R.id.edit_text_subject);
        mEditTextMessage = view.findViewById(R.id.edit_text_message);
        buttonSend = view.findViewById(R.id.button_send);
        buttonSelectImage = view.findViewById(R.id.upload_photo);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        //attachment button listener
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();
            }
        });



        return view;
    }

    //PUT ALL THE TEXT, SUBJECT INTO EMAIL
    private void sendMail() {
        String recipientList = "victorjongsoon@hotmail.com";
        String[] recipients = recipientList.split(",");

        String subject = "Making Report";
        String message = mEditTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (URIpath != null) {
            intent.putExtra(Intent.EXTRA_STREAM, URIpath);
        }

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    //UPLOAD PHOTOS
    public void openFolder() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
    }


}
