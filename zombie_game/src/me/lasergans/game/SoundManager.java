package me.lasergans.game;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	private static HashMap<String, SoundData> soundAttributes = new HashMap<String, SoundData>();
	private static boolean active = true;
	
	public static boolean isActive() {
		return active;
	}
	public static void setActive(boolean active) {
		if(active == false){
			SoundManager.stopBackgroundMusic();
		}
		SoundManager.active = active;
	}
	public static void play(String name){
		SoundManager.play(name, 0);
	}
	public static void play(String name, int loop){
		if(SoundManager.active == false){
			return;
		}
		SoundData attributes = soundAttributes.get(name);
		if (attributes == null){
			SoundManager.prepare("/sounds/" + name + ".wav");
			attributes = soundAttributes.get(name);
		}
		
		AudioFormat af = attributes.audioFormat;
		DataLine.Info info = attributes.info;
		int size = attributes.size;
		byte[] audio = attributes.data;
		
		if(loop < 0 || loop == Clip.LOOP_CONTINUOUSLY){
			loop = 0;
		}
		
		Clip clip = null;
		try {
			clip = (Clip) AudioSystem.getLine(info);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			clip.open(af, audio, 0, size);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		clip.loop(loop);
		clip.start();
	}
	
	public static void prepare(String path){
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(SoundManager.class.getResource(path));
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(audioInputStream == null){
			System.err.println("Couldn't load Sound! <"+ path +">");
			return;
		}
		 AudioFormat af = audioInputStream.getFormat();
		 int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
		 byte[] audio = new byte[size];
		DataLine.Info info = new DataLine.Info(Clip.class, af, size);
		try {
			audioInputStream.read(audio, 0, size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SoundData sd = new SoundData();
		sd.audioFormat = af;
		sd.info = info;
		sd.size = size;
		sd.data = audio;
		
		String key = path.substring(0, path.length() - 4);
		String[] s = key.split("/");
		key = s[s.length-1];
		soundAttributes.put(key, sd);
	}
	
	private static Clip backgroundMusic;
	public static void playBackgroundMusic(String file){
		if(SoundManager.active == false){
			return;
		}
		SoundManager.stopBackgroundMusic();
		AudioInputStream audioInputStream = null;
		String path;
		if (file.contains("/")){
			path = file;
		} else {
			path = "/sounds/" + file + ".wav";
		}
		try {
			audioInputStream = AudioSystem.getAudioInputStream(SoundManager.class.getResource(path));
		} catch (Exception e1) {
			System.err.println("Couldn't load Sound! <"+ path +">");
			e1.printStackTrace();
		}

		try{
		
			//Clip clip = (Clip) AudioSystem.getLine(info);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
			SoundManager.backgroundMusic = clip;
			} catch (Exception e) { 
				System.err.println("Couldn't play BackgroundMusic! <"+ path +">");
				e.printStackTrace();
			}
		 
	}
	
	public static void stopBackgroundMusic(){
		if (backgroundMusic != null){
			backgroundMusic.stop();
			backgroundMusic = null;
		}
	}
}
