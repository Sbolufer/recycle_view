package esmyfirstandroidproject.covalco.recycle_view_example;

import android.app.Service;
import android.content.Intent;
import android.media.Image;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

public class SampleService extends Service {

    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Toast.makeText(this, "Servei Trobat!", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        player= MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

        player.setLooping(true);

        player.start();
        Toast.makeText(this,"Servei Actiu!", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        player.stop();
        Toast.makeText(this,"Servei Parat!", Toast.LENGTH_LONG).show();
    }
}
