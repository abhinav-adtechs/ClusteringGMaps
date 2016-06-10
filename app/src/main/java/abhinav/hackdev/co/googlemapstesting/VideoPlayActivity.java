package abhinav.hackdev.co.googlemapstesting;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import java.io.IOException;

public class VideoPlayActivity extends AppCompatActivity implements
        TextureView.SurfaceTextureListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener{

    private static final String TAG = "SPLTAG";
    private MediaPlayer mediaPlayer ;
    private Surface surface ;
    TextureView vidTextureView;
    String filePathIntent= "https://s3-ap-southeast-1.amazonaws.com/swis-profile-pic/VID_20160426_151140.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        init();
    }

    private void init(){
        vidTextureView = (TextureView)findViewById(R.id.vidTextureView);
    }


    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        vidTextureView.setSurfaceTextureListener(this);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height)  {
        this.surface = new Surface(surface) ;

        try {
            mediaPlayer = new MediaPlayer();
            if(filePathIntent != null){
                mediaPlayer.setDataSource(filePathIntent);

                mediaPlayer.setSurface(this.surface);
                mediaPlayer.prepare();
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnCompletionListener(this);

                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.start();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "onPrepared: ");
    }

    @Override
    protected void onPause() {
        mediaPlayer.stop();
        super.onPause();
    }
}
