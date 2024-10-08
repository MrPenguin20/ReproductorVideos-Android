package mx.edu.itl2018b.a85360673.u4reproduccionvideoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private String ruta;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //preparar el progres dialogue
        progressDialog = new ProgressDialog ( this );
        progressDialog.setTitle ( "Reproduccion" );
        progressDialog.setMessage ( "CARGANDO..." );
        progressDialog.setCanceledOnTouchOutside ( false );
        progressDialog.show();

        videoView = findViewById ( R.id.videoView );
        ruta = getIntent().getStringExtra ( "rutaVideo" );

        //Establecer el video que s eva a reproducir y agregar los controles de reproduccion
        videoView.setVideoURI ( Uri.parse(ruta) );
        videoView.setMediaController ( new MediaController ( this ) );

        //al terminar de cargar el video (preparado) se inicia la reproduccion
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping( false );
                videoView.requestFocus();
                progressDialog.dismiss();
                videoView.start();
            }
        });

        //al completar la reproduccion del video terminar la actividad
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt ( "position", videoView.getCurrentPosition() );
        videoView.pause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int position = savedInstanceState.getInt ( "position" );
        videoView.seekTo ( position );
    }
}