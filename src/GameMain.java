/* Indovina il Numero ver. 1.0
 * Author: Nicola Piacquaddio
 * Build Date: 22/01/2017
 * 
 * FUNZIONALITA':
 * - FUNZIONI BASE (CONTROLLO MIN&MAX, INPUT)
 * - BACKGROUND MUSIC
 * - BOTTONI DINAMICI
 * - PUNTI VITA
 * - PUNTI SUGGERIMENTO
 * - CRONOLOGIA NUMERI
 * - CHECKBOX PER MUTARE
 * - TASTO REPLAY
 * 
 * */


import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class GameMain extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/* DICHIARAZIONE OGGETTI
	 * LA CLASSE FUNCTIONS CONTIENE
	 * I PRINCIPALI ELEMENTI PER
	 * IL FUNZIONAMENTO DEL GIOCO.
	 * 
	 * L'OGGETTO BGMx HA IL COMPITO
	 * DI LOCALIZZARE IL FILE MUSICALE
	 * ALL'INTERNO DELLA CARTELLA
	 * /ASSETS/ DEL GIOCO */
	
	Functions Funzione = new Functions();
	
	File BGMStart = new File("assets/music/gtn_countdown.wav");
	File BGMMain1 = new File("assets/music/gtn_bgm_game.wav");
	File BGMMain2 = new File("assets/music/gtn_bgm_game_b.wav");
	File BGMLose = new File("assets/music/gtn_bgm_lose.wav");
	File BGMWin = new File("assets/music/gtn_bgm_win.wav");
	File SFXLifeline1 = new File("assets/music/gtn_lifeline_1.wav");
	File SFXLifeline2 = new File("assets/music/gtn_lifeline_2.wav");
	File SFXLifeline3 = new File("assets/music/gtn_lifeline_3.wav");
	File SFXTimeOut = new File("assets/music/gtn_timeout.wav");
	
	
	ActionListener timerListener = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			Funzione.timeSecLeft--;
			Timer.setText(String.format("%02d:%02d", Funzione.timeSecLeft / 60, Funzione.timeSecLeft % 60));
			if (Funzione.timeSecLeft <= 0) {
				timeLeft.stop();
				TimeAlertFX.stop();
				gameStopTimeOut();
			}
			
			if (Funzione.timeSecLeft <= 10) {
				TimeAlertFX.start();
			}
		}
	};   
	
	ActionListener TimeAlertFXListener = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			
			if(Funzione.timeSecLeft <= 0)
				TimeAlertFX.stop();
			
			if(Funzione.timeFXText) {
				Timer.setVisible(false);
				Funzione.timeFXText = false;
			}
			else {
				Timer.setVisible(true);
				Funzione.timeFXText = true;
			}
		}
	}; 
	
	ActionListener countdownToStart = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			
			if(Funzione.timeSecToStart < 1) {
				
				CountdownBegin.stop();
				
		        // AVVIO BGM
			    if(Funzione.isTimed)
			    	Funzione.PlayLoop(BGMMain1);
			    else
			    	Funzione.PlayLoop(BGMMain2);   
			    
			    // MOSTRO IL MASSIMO E IL MINIMO
		        MaxArea.setText(String.format(TextArea, Funzione.massimo));
		        MinArea.setText(String.format(TextArea, Funzione.minimo));	
		        
		        // ATTIVO L'UI PER L'UTENTE
		        gameCountdown.setVisible(false); 	// TOLGO IL COUNTDOWN
		        jButton1.setEnabled(true);			// ATTIVO IL BOTTONE CONTROLLA
		        inputNumber.setEnabled(true);		// ATTIVO LO SPAZIO INPUT NUMERI
		        LifeNum.setVisible(true);			// MOSTRO LE VITE DEL GIOCATORE
		        muteBGM.setEnabled(true);			// ATTIVO L'OPZIONE TOGLI BGM
		        inputNumber.requestFocus();			// IMPOSTO IL FOCUS ALLO SPAZIO INPUT
			    
		        // AVVIO TIMER
		        if(Funzione.isTimed)
		        	timeLeft.start();
			}
			else {
				Funzione.timeSecToStart--;
				Funzione.secToStartFX = Funzione.secToStartFX + 3;
		        gameCountdown.setFont(new java.awt.Font("Ubuntu", 1, Funzione.secToStartFX));
		        gameCountdown.setText(""+Funzione.timeSecToStart);
			}
		
		}
	}; 
	
	Timer TimeAlertFX = new Timer(250, TimeAlertFXListener);
	Timer timeLeft = new Timer(1000, timerListener);
	Timer CountdownBegin = new Timer(800, countdownToStart);
	
	StringBuffer oldNums = new StringBuffer();
	
    Rectangle dimension = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	
	// CREAZIONE FIRMA CON IL METODO
	// PER LA COSTRUZIONE DEL FRAME
    private GameMain() {
        frameConstruction();
    }
    
    // METODO COSTRUZIONE FRAME
    private void frameConstruction() {
    	
    	// DICHIARAZIONE DEI COMPONENTI DEL FRAME
    	gameAlert = new JLabel();
    	gameCountdown = new JLabel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        LifeNum = new JLabel();
        Timer = new JLabel();
        alert = new JLabel();
        inputNumber = new JTextField();
        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();
        jScrollPane3 = new JScrollPane();
        MaxArea = new JTextPane();
        MinArea = new JTextPane();
        OldNumList = new JTextArea();
        jButton1 = new JButton();
        tipsButton = new JButton();
        restartButton = new JButton();
        startButton = new JButton();
        muteBGM = new JCheckBox();

        // DICHIARAZIONE PROPRIETA' FRAME
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Guess the Number");
        setContentPane(new JLabel(new ImageIcon("assets/images/bg/bg2.png")));
        setResizable(false);
        getContentPane().setLayout(null);
        
        // PROPRIETA' Timer (Tempo rimanente sul FRAME)
        Timer.setFont(new java.awt.Font("Ubuntu", 1, 14));
		Timer.setText(String.format("%02d:%02d", Funzione.timeSecLeft / 60, Funzione.timeSecLeft % 60));
        getContentPane().add(Timer);
        Timer.setBounds(300, 206, 100, 23);
        if(!Funzione.isTimed)
        	Timer.setVisible(false);
        
        // PROPRIETA' gameAlert (Scritta "TIMED" sul FRAME)
        gameAlert.setFont(new java.awt.Font("Ubuntu", 1, 12));
        gameAlert.setText("TIMED");
        getContentPane().add(gameAlert);
        gameAlert.setBounds(285, 30, 100, 100);
        gameAlert.setVisible(false);
        
        // PROPRIETA' jLabel1 (Scritta "MINIMO" sul FRAME)
        gameCountdown.setFont(new java.awt.Font("Ubuntu", 1, 15));
        gameCountdown.setText(""+Funzione.timeSecToStart);
        getContentPane().add(gameCountdown);
        gameCountdown.setBounds(313, 30, 100, 100);
        gameCountdown.setVisible(true);

        // PROPRIETA' jLabel1 (Scritta "MINIMO" sul FRAME)
        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 14));
        jLabel1.setText("Minimo");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 40, 50, 20);

        // PROPRIETA' jLabel2 (Scritta "MASSIMO" sul FRAME)
        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 14));
        jLabel2.setText("Massimo");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(500, 40, 60, 20);

        // PROPRIETA' inputNumber (BOX al centro della finestra)
        // Attualmente mostra solo il ToolTip.
        inputNumber.setFont(new java.awt.Font("Ubuntu", 1, 26));
        inputNumber.setToolTipText("Inserisci qui il numero che presumi sia il vincente");
        getContentPane().add(inputNumber);
        inputNumber.setHorizontalAlignment(JTextField.CENTER);
        inputNumber.setBounds(220, 100, 200, 40);
        inputNumber.setEnabled(false);

        // PROPRIETA' MaxArea
        // E' L'ENTITA' CHE DOVRA' TENERE
        // IL VALORE MASSIMO SCRIVIBILE IN
        // inputNumber BOX
        MaxArea.setEditable(false);
        MaxArea.setContentType("text/html");
        MaxArea.setText(null);
        jScrollPane1.setViewportView(MaxArea);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(430, 70, 200, 120);

        // PROPRIETA' MinArea
        // E' L'ENTITA' CHE DOVRA' TENERE
        // IL VALORE MINIMO SCRIVIBILE IN
        // inputNumber BOX
        MinArea.setEditable(false);
        MinArea.setContentType("text/html");
        MinArea.setText(null);
        jScrollPane2.setViewportView(MinArea);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 70, 200, 120);

        // Proprietà e ActionListener per jButton1 (il tasto "Controlla")
        jButton1.setText("Controlla");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(213, 180, 100, 23);
        jButton1.setEnabled(false);
        
        // Proprietà e ActionListener per tipsButton (il tasto "Aiuto (x)")
        tipsButton.setText("Aiuto ("+Funzione.tips+")");
        tipsButton.setEnabled(false);
        tipsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipsButtonActionPerformed(evt);
            }
        });
        getContentPane().add(tipsButton);
        tipsButton.setBounds(326, 180, 100, 23);
        
        // Proprietà e ActionListener per restartButton (il tasto "Rigioca")
        restartButton.setText("Rigioca");
        restartButton.setEnabled(false);
        restartButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		restartButtonActionPerformed(evt);
        	}
        });
        getContentPane().add(restartButton);
        restartButton.setBounds(270, 60, 100, 23);
        restartButton.setVisible(false);
        
        
        // PROPRIETA' OldNumList, stampa tutti i valori
        // precedentemente inseriti nel BoxInput
        OldNumList.setFont(new java.awt.Font("Ubuntu", 2, 15));
        OldNumList.setEditable(false);
        OldNumList.setText("\tNon c'è ancora nessun valore");
        jScrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setViewportView(OldNumList);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(120, 230, 400, 40);

        // IMPOSTAZIONI CONTATORE VITA
        LifeNum.setText("Vite Rimaste: "+Funzione.lifepoints);
        getContentPane().add(LifeNum);
        LifeNum.setBounds(273, 150, 110, 20);
        LifeNum.setVisible(false);
        
        // IMPOSTAZIONI ALERT (Scritta Guess the Number sul Frame)
        alert.setFont(new java.awt.Font("Ubuntu", 1, 20));
        alert.setIcon(new ImageIcon("assets/images/other/logo_mini.png"));
        getContentPane().add(alert);
        alert.setBounds(200, -65, 290, 200);
        alert.setVisible(true);
        
        // IMPOSTAZIONI CHECKBOX MUTE MUSIC
        muteBGM.setText("Rimuovi BGM");
        muteBGM.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		muteBGMActionPerformed(evt);
        	}
        });
        muteBGM.setBorderPainted(false);
        muteBGM.setContentAreaFilled(false);
        muteBGM.setBounds(-2, 265, 100, 15);
        getContentPane().add(muteBGM);
        muteBGM.setEnabled(false);
        
        // IMPOSTAZIONE SU SCHERMO DEL FRAME
        setBounds(0, 0, 650, 320);
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
       
	    
	    // AVVIO LA PARTITA
	    CountdownBegin.start();
	    Funzione.PlaySound(BGMStart);
        
    }                                                               

    private void gameStopTimeOut() {
    	try {
    		Funzione.StopSound();
        	Funzione.PlaySound(SFXTimeOut);
        	alert.setFont(new java.awt.Font("Ubuntu", 1, 30));
        	alert.setText("Tempo finito!");
        	restartButton.setVisible(true);
        	restartButton.setEnabled(true);
        	muteBGM.setEnabled(false);
        	tipsButton.setEnabled(false);
        	jButton1.setEnabled(false);
        	inputNumber.setEnabled(false);
        	inputNumber.setText(""+Funzione.CasNumber);
            inputNumber.setToolTipText("Questo è il numero vincente");
        	JOptionPane.showMessageDialog(this, "Tempo scaduto! Il numero corretto era "+Funzione.CasNumber);
    	}
    	catch (Exception e) {
    		System.err.print("ERROR");
    	}
    }
    
    // COMANDI DI AZIONE QUANDO SI VERIFICA L'EVENTO "TASTO PREMUTO" su "Controlla"
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        try {
        	
        	timeLeft.stop();
        	
        	Funzione.corrected = false;
        	Funzione.corrected2 = false;
        	
            int numberInp = Integer.parseInt(inputNumber.getText());
            String S1 = inputNumber.getText();
            
            if (OldNumList.getText().equals("\tNon c'è ancora nessun valore"))
            	OldNumList.setText(null);
            
            OldNumList.append("   "+Funzione.lifepoints+") "+S1);
            
            if (Funzione.lifepoints == 6) {
            	tipsButton.setEnabled(true);
            }
            
            
            if (numberInp == Funzione.CasNumber) {
            	Funzione.StopSound();
            	Funzione.PlaySound(BGMWin);
                if(Funzione.isTimed)
                	timeLeft.stop();
            	alert.setFont(new java.awt.Font("Ubuntu", 1, 30));
            	alert.setText("Hai Vinto! :-)");
            	restartButton.setVisible(true);
            	Timer.setVisible(false);
            	restartButton.setEnabled(true);
            	muteBGM.setEnabled(false);
            	tipsButton.setEnabled(false);
            	jButton1.setEnabled(false);
            	inputNumber.setEnabled(false);
            	JOptionPane.showMessageDialog(this, "Hai trovato il numero!");
            }
            else if ( (numberInp == Funzione.CasNumber) && (Integer.parseInt(MinArea.getText()) == Funzione.CasNumber - 1) && (Integer.parseInt(MaxArea.getText()) == Funzione.CasNumber + 1) ) {
            	
            }
            else {
            	Funzione.lifepoints--;
            	LifeNum.setText("Vite Rimaste: "+Funzione.lifepoints);
            	JOptionPane.showMessageDialog(this, "Non hai trovato il numero!\nHai ancora: "+Funzione.lifepoints+" vite.");
            	
                if (Funzione.lifepoints == 0) {
                	Funzione.StopSound();
                	Funzione.PlaySound(BGMLose);
                    if(Funzione.isTimed)
                    	timeLeft.stop();
                	alert.setFont(new java.awt.Font("Ubuntu", 1, 30));
                	alert.setText("Hai Perso! :-(");
                	restartButton.setVisible(true);
                	Timer.setVisible(false);
                	restartButton.setEnabled(true);
                	muteBGM.setEnabled(false);
                	tipsButton.setEnabled(false);
                	inputNumber.setEnabled(false);
                	jButton1.setEnabled(false);
                	inputNumber.setText(""+Funzione.CasNumber);
                    inputNumber.setToolTipText("Questo è il numero vincente");
                	JOptionPane.showMessageDialog(this, "Hai perso tutte le vite!\nNumero vincente: "+Funzione.CasNumber);
                }
                else if (numberInp < Funzione.minimo) {
            		JOptionPane.showMessageDialog(this, "Il valore non deve essere minore del limite Minimo");
            		timeLeft.start();
                }
            	else {
            		Funzione.corrected = true;
            		if (numberInp > Funzione.massimo) {
            			JOptionPane.showMessageDialog(this, "Il valore non deve essere maggiore del limite Massimo");
            		}
            		else {
            			Funzione.corrected2 = true;
            	        if(Funzione.isTimed)
            	        	timeLeft.start();
            			if (Funzione.corrected == false || Funzione.corrected2 == false)
            				Funzione.corrected = false;
            			else if (numberInp < Funzione.CasNumber) {
            				Funzione.minimo = numberInp;
            				MinArea.setText(String.format(TextArea, Funzione.minimo));
            			}
            			else {
            				Funzione.massimo = numberInp;
            				MaxArea.setText(String.format(TextArea, Funzione.massimo));
            			}     
            		}
            	} 
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore nell'input!", "Errore", JOptionPane.ERROR_MESSAGE);
            if(Funzione.isTimed)
            	timeLeft.start();
            return;
        } 
    }

    // COMANDI DI AZIONE QUANDO SI VERIFICA L'EVENTO "TASTO PREMUTO" su "Aiuto (x)"
    private void tipsButtonActionPerformed(java.awt.event.ActionEvent evt) { 
    	try {
    		Funzione.tips--;
    		if (Funzione.tipsFinished == true) {
    			tipsButton.setText("Aiuto (0)");
    		}
    		else {
    			tipsButton.setText("Aiuto ("+Funzione.tips+")");
    		}
    		
    		
    		if (Funzione.tips == 2) {
    			Funzione.PlaySound(SFXLifeline1);
    			JOptionPane.showMessageDialog(this, "Il valore è tra "+(Funzione.CasNumber-15)+" e "+(Funzione.CasNumber+15));

    		}
    		else if (Funzione.tips == 1) {
    			Funzione.PlaySound(SFXLifeline2);
    			if(Funzione.isTimed) {
    				Funzione.timeSecLeft -= 15;
    			}
    			JOptionPane.showMessageDialog(this, "Il valore è tra "+(Funzione.CasNumber-6)+" e "+(Funzione.CasNumber+6));
    		}
    		else if (Funzione.tips == 0) {
    			tipsButton.setEnabled(false);
    			Funzione.PlaySound(SFXLifeline3);
    			if(Funzione.isTimed) {
    				Funzione.timeSecLeft -= 30;
    			}
    			JOptionPane.showMessageDialog(this, "Il valore è tra "+(Funzione.CasNumber-4)+" e "+(Funzione.CasNumber+6));
    			Funzione.tipsFinished = true;
    		}
    	}
    	catch (Exception e) {
    		JOptionPane.showMessageDialog(this, "Errore Sconosciuto", "Errore", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    }
   
    private void muteBGMActionPerformed(java.awt.event.ActionEvent evt) {
    	if (muteBGM.isSelected() == true) {
    		Funzione.StopSound();
    	}
    	else
    		Funzione.ReplayLoop();
    }
    
    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	try {
    		Runtime.getRuntime().exec("java -jar GuesstheNumber.jar");
    		System.exit(0);
    	}
    	catch (Exception e) {
    		JOptionPane.showMessageDialog(this, "Impossibile trovare il file eseguibile", "Errore", JOptionPane.ERROR_MESSAGE);
    	}
    }   
    
    public static void main(String args[]) { 
    	
    	// ESEGUE IL COMANDO SETVISIBLE
    	// E ATTENDE CHE IL THREAD "EDT"
    	// ESEGUITO PER LA COSTRUZIONE DEL
    	// JFRAME SIA COMPLETO PRIMA DI
    	// RENDERE VISIBILE IL JFRAME.
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameMain().setVisible(true);
            }
        });
    }

    // DICHIARAZIONE TIPO VARIABILI 
    private JLabel gameAlert;
    private JLabel gameCountdown;
    private JLabel LifeNum;
    private JLabel Timer;
    protected JTextPane MaxArea;
    protected JTextPane MinArea;
    protected JTextArea OldNumList;
    protected JTextField inputNumber;
    protected JButton jButton1;
    protected JButton tipsButton;
    protected JButton restartButton;
    protected JButton startButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel alert;
    protected JScrollPane jScrollPane1;
    protected JScrollPane jScrollPane2;
    protected JScrollPane jScrollPane3;
    protected JCheckBox muteBGM;
    String TextArea = "<html><center><b><font size=30 color=rgb(1,1,1) font face=ubuntu>  %01d   </font></b></center></html>";
    String TextAreaUI = "<html><center><b><font size=30 color=rgb(255,255,255) font face=ubuntu>  %01d   </font></b></center></html>";
                      
}
