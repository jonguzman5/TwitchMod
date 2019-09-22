package tv.twitch.android.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.mod.utils.TwitchUsers;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String[] emotes = {"LULW", "OMEGALUL"};
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("emote", String.valueOf(EmotesManager.getInstance().getEmote(emotes[i++%emotes.length], 22484632)));
                }
            }
        });
        th.setDaemon(true);
        th.start();
    }
}
