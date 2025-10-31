package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundurl[] = new URL[30];

    public Sound() {
        soundurl[0] = getClass().getResource("/res/sound/gamebgm2.wav");
        soundurl[1] = getClass().getResource("/res/sound/chest01.wav");
        soundurl[2] = getClass().getResource("/res/sound/coin01.wav");
        soundurl[3] = getClass().getResource("/res/sound/Door01.wav");
        soundurl[4] = getClass().getResource("/res/sound/jump01.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundurl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();   
        }
    }
    
    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

}
