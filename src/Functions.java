import java.io.File;
import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Functions {
	
	protected int max, min, dim, CasNumber, minimo, massimo, lifepoints, pow, tips, tipsOptions, timeSecLeft = 120, timeSecToStart = 5, secToStartFX = 15;
	protected boolean corrected = false;
	protected boolean corrected2 = false;
	protected boolean tipsFinished = false;
	protected boolean isTimed = false;
	protected boolean timeFXText = true;
	protected Clip clip, clip2, clipbgm;
	protected long cliprepeat;
	
	Random rand = new Random();
	
	protected Functions(int a, int b, int c) {
		max = a;
		min = 11;
		pow = rand.nextInt((max - min) + 1) + min;
		dim = (int) Math.pow(2, pow);
		lifepoints = b-1;
		minimo = 1;
		massimo = dim;
		CasNumber = rand.nextInt((dim - 1) + 1) + 1;
		tips = c;
		
	}
	
	protected Functions() {
		max = 15;
		min = 11;
		pow = rand.nextInt((max - min) + 1) + min;
		dim = (int) Math.pow(2, pow);
		lifepoints = pow-1;
		minimo = 1;
		massimo = dim;
		CasNumber = rand.nextInt((dim - 1) + 1) + 1;
		tips = 3;
	}
	
	// UN FILE MUSICALE VIENE ASSEGNATO A "clip"
	// E RIPRODOTTO UNA SOLA VOLTA
	protected void PlaySound(File Sound) {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	// UN FILE MUSICALE VIENE ASSEGNATO A "clip"
	// E RIPRODOTTO ALL'INFINITO
	protected void PlayLoop(File Sound) {
		try {
			clipbgm = AudioSystem.getClip();
			clipbgm.open(AudioSystem.getAudioInputStream(Sound));
			clipbgm.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	// INTERROMPE IL FILE MUSICALE ASSEGNATO A "clipbgm"
	protected void StopSound() {
			cliprepeat = clipbgm.getMicrosecondPosition();
			clipbgm.stop();
	}
	
	// INTERROMPE IL FILE MUSICALE ASSEGNATO A "clipbgm"
	protected void ReplayLoop() {
			cliprepeat = clipbgm.getMicrosecondPosition();
			clipbgm.setMicrosecondPosition(cliprepeat);
			clipbgm.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	
}