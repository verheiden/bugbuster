package apps.yuzaco.com.bugbuster;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

/**
 * Created by jung on 8/27/15.
 */
public class MusicPlayer
{
    MediaPlayer mediaPlayer;
    boolean playOn = true;
    static Context uiContext;
    int music;

    float mMusicVolume;


    public MusicPlayer(Context context,  int musicId, boolean musicOn, float volume)
    {
        uiContext = context.getApplicationContext();
        playOn = musicOn;
        music = musicId;
        mMusicVolume = volume;
        if ( musicOn)
            startMusicPlayer(musicId);
    }

    public static void  playOnce(int soundId, float volume)
    {
        MediaPlayer soundPlayer = MediaPlayer.create( uiContext, soundId );
        soundPlayer.setVolume(volume, volume);
       soundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mp != null) {
                    if (mp.isPlaying())
                        mp.stop();
                    mp.reset();
                    mp.release();
                    mp = null;
                }
            }
        });
        soundPlayer.start();
    }
    public void  startMusicPlayer(int musicId)
    {
        mediaPlayer = MediaPlayer.create( uiContext, musicId );
        mediaPlayer.setVolume(mMusicVolume, mMusicVolume);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });

        mediaPlayer.setLooping(true);
    }
    public void start()
    {
        if ( mediaPlayer != null && playOn )
            mediaPlayer.start();
    }
    public void stop()
    {
            if ( mediaPlayer  != null ) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
    }
    public void pause()
    {
        if ( mediaPlayer  != null ) {
            mediaPlayer.pause();
        }
    }
    public void halt()
    {
        if ( mediaPlayer !=  null ) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public void On()
    {
      if (playOn == false) {
          playOn = true;
          startMusicPlayer(music);
      }
    }
    public void Off()
    {

      if ( playOn ) {
          stop();
          playOn = false;
      }
    }

    public  void playNotes( int milli) {
            try {
                mediaPlayer.start();
            }catch(Exception e) {
                e.printStackTrace();
            }

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                try {
                    mediaPlayer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                    }
                }
            }, milli);
        }
    }