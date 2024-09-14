package org.example.flappy.listeners;

import org.example.flappy.Event;
import org.example.flappy.EventType;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundListener implements Listener {
    Clip clipBg;
    Clip clipIntro;
    boolean intro_played = false;

    public SoundListener() {
    }

    @Override
    public void handle(Event event) {

        EventType eventType = event.getEventType();
        switch (eventType) {
            case INTRO_START:
                if (!intro_played) {
                    playIntro("intro.wav", true);
                    intro_played = true;
                }
                break;
            case INTRO_END:
                stopIntro();
                break;
            case GAME_START:
                playBG("soundtrack.wav", true);
                break;
            case GAME_END:
                stopBG();
                break;
            case POINT:
                play("point.wav");
                break;
            case DIE:
                play("die.wav");
                break;
            case COLLISION:
                play("collision.wav");
                break;
            case SWOOSH:
                play("swoosh.wav");
                break;

        }


    }

    public void playBG(String fileName, boolean loop) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/" + fileName));
            clipBg = AudioSystem.getClip();
            clipBg.open(audioIn);
            if (loop) {
                clipBg.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clipBg.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playIntro(String fileName, boolean loop) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/" + fileName));
            clipIntro = AudioSystem.getClip();
            clipIntro.open(audioIn);
            if (loop) {
                clipIntro.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clipIntro.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBG() {
        clipBg.stop();
    }
    public void stopIntro() {
        clipIntro.stop();
    }

    public void play(String fileName) {
        play(fileName,false);
    }
    public void play(String fileName,boolean loop) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/" + fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
