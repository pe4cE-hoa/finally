package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import bullet.*;
import display.GameFrame;
import display.GamePanel;
import enemies.Boss;
import enemies.Bull;
import enemies.DarkDuck;
import enemies.Enemy;
import enemies.LittleGirl;
import enemies.Slime;
import enemies.Snail;
import gameobject.ParticularObject;
import gameobject.ParticularObjectManager;
import item.ItemDame;
import item.ItemHP;
import item.ItemHeart;
import item.ItemManager;
import map.BackgroundMap;
import map.Camera;
import map.PhysicalMap;
import player.Player;

import graphic.StateManager;

public class GameWorldState extends State {
	
    private BufferedImage bufferedImage;
    private int lastState;

    private StateManager stateManager;
    private ItemManager itemManager;
    private PhysicalMap phys_map;
    private BackgroundMap map;
    private Camera camera;
	private Player player;
	private Enemy boss;

    private BulletManager bulletManager;
    private ParticularObjectManager particularObjectManager;
        
    public static final int INIT_GAME = 0;
    public static final int TUTORIAL = 1;
    public static final int GAMEPLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int GAMEWIN = 4;
    public static final int PAUSEGAME = 5;
    
    public static final int INTROGAME = 0;
    public static final int MEETFINALBOSS = 1;
    
    public int openIntroGameY = 0;
    public int state = INIT_GAME;
    public int previousState = state;
    public int tutorialState = INTROGAME;
    
    public int storyTutorial = 0; 
    public String[] texts1 = new String[4];

    public String textTutorial;
    public int currentSize = 1; 
    private Image image = Toolkit.getDefaultToolkit().getImage("data/player/player_017.png");
    private Image heart = Toolkit.getDefaultToolkit().getImage("data/player/heart.png");
    private boolean changeMap2 = true;
    
    public GameWorldState(GamePanel gamePanel){
        super(gamePanel);
        
        texts1[0] = "\nYou are a HERO, and your mission is protecting your Village...";
        texts1[1] = "There were Monsters from University on this Village in 10 years\n"
                  + "and you lived in the scare in that 10 years...\n";
        texts1[2] = "\nNow is the time for you, find and kill all the Monters \nand BOSS DRAGON!\n";
        texts1[3] = "\n         LET'S GO!....";
        textTutorial = texts1[0];

        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        stateManager = new StateManager();
        stateManager.getPlayerImage();
        itemManager = new ItemManager();
        itemManager.geItemImage();
		player = new Player(500, 500, this);
		phys_map = new PhysicalMap(0 ,0, this);
		map = new BackgroundMap(0, 0, this);
		camera = new Camera(0, 50, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this );
		
		particularObjectManager = new ParticularObjectManager(this);
		bulletManager = new BulletManager(this);
        bulletManager.getBulletImage();
        initEnemies1(); 
        particularObjectManager.addObject(player);
    }


    public PhysicalMap getPhys_map (PhysicalMap phys_map) {
    	return phys_map;
    }    
    
    private void initEnemies1(){        
    	Enemy[] snail = new Snail[3];stateManager.getSnailImage();
        snail[0] = new Snail(680, 1645, this);
        snail[1] = new Snail(1720, 316, this);
        snail[2] = new Snail(2132, 564, this);
        for(int i = 0; i < snail.length; i++)
        	particularObjectManager.addObject(snail[i]);
        
        
        Enemy [] bull = new Bull[3];stateManager.getBullImage();
        bull[0] = new Bull(2500, 612, this);
        bull[1] = new Bull(2350, 306, this);
        bull[2] = new Bull(1945, 1084, this);
        for(int i = 0; i < bull.length; i++)
        	particularObjectManager.addObject(bull[i]);
       
        
        Enemy[] slime = new Slime[2];stateManager.getSlimeImage();
        slime[0] = new Slime(230, 250, this);
        slime[1] = new Slime(280, 1180, this);
        for(int i = 0; i < slime.length; i++)
            particularObjectManager.addObject(slime[i]);
        
       
        DarkDuck[] duck = new DarkDuck[3]; stateManager.getDarkDuckImage();
        duck[0] = new DarkDuck(2226, 192, this);
        duck[0].setXY(2036, 2390, 128, 344);
        duck[1] = new DarkDuck(1610, 600, this);
        duck[1].setXY(1600, 1620, 605, 630);
        duck[2] = new DarkDuck(2000, 150, this);
        duck[2].setXY(2000, 2400, 150, 400);
        for(int i = 0; i < duck.length; i++)
        	particularObjectManager.addObject(duck[i]);

    }
    
    private void initEnemies2() {
    	boss = new Boss(2200, 1050, this); stateManager.getBossImage();
    	particularObjectManager.addObject(boss);
    	
    	Enemy litl[] = new LittleGirl[4];stateManager.getLittleGirlImage();
    	litl[0] = new LittleGirl(600, 190, this);
    	litl[1] = new LittleGirl(1178, 583, this);
    	litl[2] = new LittleGirl(1630, 1041, this);
    	litl[3] = new LittleGirl(2290, 1041, this);
    	for(int i = 0; i < litl.length; i++)
    		particularObjectManager.addObject(litl[i]);
    	
    	Enemy bul[] = new Bull[2];
    	bul[0] = new Bull(1498, 550, this);
    	bul[1] = new Bull(746, 545, this);
    	for(int i = 0; i < bul.length; i++)
    		particularObjectManager.addObject(bul[i]);
    	
    	DarkDuck duck[] = new DarkDuck[4];
    	duck[0] = new DarkDuck(1654, 190, this);
    	duck[0].setXY(1442, 1818, 120, 279);
    	
    	duck[1] = new DarkDuck(1992, 993, this);
    	duck[1].setXY(1742, 2142, 970, 1120);
    	
    	duck[2] = new DarkDuck(422, 1037, this);
    	duck[2].setXY(274, 610, 970, 1126);
    	
    	duck[3] = new DarkDuck(794, 1030, this);
    	duck[3].setXY(590, 942, 970, 1120);
    	for(int i = 0; i < duck.length; i++)
    		particularObjectManager.addObject(duck[i]);
    	
    	Enemy snai[] = new Snail[3];
        snai[0] = new Snail(546, 190, this);
        snai[1] = new Snail(2100, 185, this);
        snai[2] = new Snail(1826, 185, this);
        for(int i = 0; i < snai.length; i++)
        	particularObjectManager.addObject(snai[i]);

    }

    public void switchState(int state){
        previousState = this.state;
        this.state = state;
    }
    
    private void TutorialUpdate(){
        switch(tutorialState){
        
            case INTROGAME:
                if(storyTutorial == 0){
                    if(openIntroGameY < 450) {
                        openIntroGameY += 4;
                    }else storyTutorial ++;                    
                }else{                
                    if(currentSize < textTutorial.length()) currentSize++; 
                }
                break;                
        }
    }
    
    private void drawString(Graphics2D g2, String text, int x, int y){
        for(String str : text.split("\n"))
            g2.drawString(str, x, y += g2.getFontMetrics().getHeight());
    }
    
    private void updateMap() {
    	if( particularObjectManager.getCount() < 1 && changeMap2 == true ) {
        	phys_map.phys_map[18][51] = 0;
        	phys_map.phys_map[19][51] = 0;
        	
        	map.map[18][51]= 37;
        	map.map[18][52]= 38;
        	map.map[19][51]= 39;
        	map.map[19][52]= 40;
        	
        	if(player.getPosX() > 2529 && player.getPosY() > 886) {
                phys_map.setPhys_map2();
                map.setBackground_map2();
                phys_map.phys_map[2][0] = 0;
                phys_map.phys_map[2][1] = 0;
                phys_map.phys_map[3][0] = 0;
                phys_map.phys_map[3][1] = 0;

                map.map[2][0] = 37;
                map.map[2][1] = 38;
                map.map[3][0] = 39;
                map.map[3][1] = 40;

                player.setPosX(50);
                player.setPosY(150);
                synchronized (particularObjectManager.particularObjects) {
                    particularObjectManager.particularObjects.removeIf(object -> object != player);
                }
            	initEnemies2();
            	changeMap2 = false;
            }
        }
    }
    
    public void Update(){    	
        switch(state){
            case INIT_GAME:                
                break;
                
            case TUTORIAL:
                TutorialUpdate();                
                break;
                
            case GAMEPLAY:
            	particularObjectManager.UpdateObjects();
                camera.Update();
                bulletManager.UpdateObjects();
                updateMap();
                
                if(player.getState() == ParticularObject.DEATH){
                	player.setNumberOfLife(player.getNumberOfLife() - 1);
                    if(player.getNumberOfLife() >= 0){
                    	player.setBlood(player.getMaxHP());
                    	player.setPosY(player.getPosY());
                    	player.setState(ParticularObject.IMMORTAL);
                        particularObjectManager.addObject(player);
                    }else{
                        switchState(GAMEOVER);
                    }
                }
                if(changeMap2 == false) {
                	 if(boss.getState() == ParticularObject.DEATH) {
                     	switchState(GAMEWIN);
                     }
                }               
                break;
                
            case GAMEOVER:                
                break;
                
            case GAMEWIN:                
                break;
        }
    }
    
    private void TutorialRender(Graphics2D g2){
        switch(tutorialState){
        case INTROGAME:
            int yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
            int y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
            int y2 = yMid + openIntroGameY/2;

            g2.setColor(Color.BLACK);
            g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
            g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);

            if(storyTutorial >= 1){ 
                g2.drawImage(image, 612, 418, 100, 100, null);
                g2.setColor(Color.BLUE);
                g2.fillRect(280, 450, 350, 80); 
                g2.setColor(Color.WHITE);
                String text = textTutorial.substring(0, currentSize - 1);
                drawString(g2, text, 290, 460);
            }                
            break;
        }
    }    
    
    public void Render(){
       Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
        if(g2 != null){  
            switch(state){
            	
            case INIT_GAME:
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
                g2.drawString("PRESS ENTER TO CONTINUE", 210, 310); 
                break;
                
            case PAUSEGAME:
                g2.setColor(Color.BLACK); 
                g2.fillRect(200, 260, 550, 70); 
                g2.setColor(Color.WHITE); 
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
                g2.drawString("PRESS ENTER TO CONTINUE", 210, 310);
                break;
                
            case TUTORIAL:
                map.draw(g2);
                TutorialRender(g2);
                break;
                
            case GAMEWIN:
            	Image winimage = Toolkit.getDefaultToolkit().getImage("data/win_game.png");
//            	g2.setColor(Color.BLACK);
//                g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
//                g2.setColor(Color.WHITE);
//                g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
//                g2.drawString("All monster have been slain, mission completed!", 100, 300);
            	g2.drawImage(winimage, 0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, null);
                break;
                
            case GAMEPLAY:
           	    map.draw(g2);
                synchronized(particularObjectManager.particularObjects){
                    for(ParticularObject object: particularObjectManager.particularObjects)
                        if(!object.isObjectOutOfCameraView()){
                        if( object instanceof Player ) stateManager.draw((Player) object,g2);
                        else if( object instanceof Boss) stateManager.draw((Boss) object,g2);
                        else if ( object instanceof DarkDuck ) stateManager.draw((DarkDuck) object,g2);
                        else if ( object instanceof LittleGirl) stateManager.draw((LittleGirl) object,g2);
                        else if ( object instanceof Slime) stateManager.draw((Slime) object,g2);
                        else if ( object instanceof Snail) stateManager.draw((Snail) object,g2);
                        else if ( object instanceof Bull) stateManager.draw((Bull) object,g2);
                        else if ( object instanceof ItemDame) itemManager.draw((ItemDame) object,g2);
                        else if ( object instanceof ItemHeart) itemManager.draw((ItemHeart) object,g2);
                        else if ( object instanceof ItemHP) itemManager.draw((ItemHP) object,g2);
                    }
                }
                  synchronized(bulletManager.particularObjects){
                      for(ParticularObject bullet: bulletManager.particularObjects)
                          if(!bullet.isObjectOutOfCameraView()){
                        if (bullet instanceof DuckBullet) bulletManager.draw((DuckBullet) bullet,g2);
                        else if (bullet instanceof Flame) bulletManager.draw((Flame) bullet,g2);
                        else if (bullet instanceof LittleGirlBullet) bulletManager.draw((LittleGirlBullet) bullet,g2);
                        else if (bullet instanceof PlayerBullet) bulletManager.draw((PlayerBullet) bullet,g2);
                        else if (bullet instanceof RedFlame1) bulletManager.draw((RedFlame1) bullet,g2);
                        else if (bullet instanceof RedFlame2) bulletManager.draw((RedFlame2) bullet,g2);
                    }
                }

              
                g2.setColor(Color.GRAY);
                g2.fillRect(19, 59, 102, 22);
                g2.setColor(Color.red);
                g2.fillRect(20, 60, player.getBlood()/(player.getMaxHP()/100), 20); 
                g2.setColor(Color.RED);
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                g2.drawString("HP: " + player.getBlood()+"/" + player.getMaxHP(), 125, 80 );
                g2.setColor(Color.YELLOW);
                g2.drawString("Dame: " + player.getCurrentDame(), 20, 100);
                g2.setColor(Color.BLUE);
                g2.drawString("Enemies: " + particularObjectManager.getCount(), 20, 120);
                for(int i = 0; i < player.getNumberOfLife(); i++){
                    g2.drawImage(heart, 15 + i*40, 18, null);
                }
                break;
                
            case GAMEOVER:
//                g2.setColor(Color.BLACK);
//                g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
//                g2.setColor(Color.WHITE);
//                g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
//                g2.drawString("GAME OVER!", 350, 300);
            	Image losegame = Toolkit.getDefaultToolkit().getImage("data/losegame.png");
            	g2.drawImage(losegame, 0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, null);

                break;

            }
            
       	   
        }

    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    @Override
    public void setPressedButton(int code) {
       switch(code){
           case KeyEvent.VK_W:
	        	player.setDirection(ParticularObject.UP_DIR);
	        	player.run();
	            break;
            
	        case KeyEvent.VK_S:
	        	player.setDirection(ParticularObject.DOWN_DIR);
	        	player.run();
	            break;
	            
	        case KeyEvent.VK_D:
	        	player.setDirection(ParticularObject.RIGHT_DIR);
	        	player.run();
	            break;
	            
	        case KeyEvent.VK_A:
	        	player.setDirection(ParticularObject.LEFT_DIR);
	        	player.run();
	            break;
	            
	        case KeyEvent.VK_ENTER:
	            if(state == INIT_GAME){
	                if(previousState == GAMEPLAY)
	                    switchState(GAMEPLAY);
	                else switchState(TUTORIAL);
	            }
	            if(state == TUTORIAL && storyTutorial >= 1){
	                if(storyTutorial<=3){
	                    storyTutorial ++;
	                    currentSize = 1;
	                    textTutorial = texts1[storyTutorial - 1];
	                }else{
	                    switchState(GAMEPLAY);
	                }
	                
	                if(tutorialState == MEETFINALBOSS){
	                    switchState(GAMEPLAY);
	                }
	            }
	            break;
	            
	        case KeyEvent.VK_SPACE:
	        	player.attack();
	            break;	 
	        case KeyEvent.VK_F:
	        	player.shooting();
	        	break;
        }
    }

    @Override
    public void setReleasedButton(int code) {
        switch(code){
            
            case KeyEvent.VK_W:
            		player.stopRun();
                break;
                
            case KeyEvent.VK_S:
            		player.stopRun();
                break;
                
            case KeyEvent.VK_D:
            		player.stopRun();
                break;
                
            case KeyEvent.VK_A:
            		player.stopRun();
                break;
                
            case KeyEvent.VK_ENTER:
                if(state == GAMEOVER || state == GAMEWIN) {
                    gamePanel.setState(new MenuState(gamePanel));
                } else
                	if(state == PAUSEGAME) {
                    state = lastState; 
                }
                break;
                
            case KeyEvent.VK_SPACE:
                break;
                
            case KeyEvent.VK_ESCAPE:
                lastState = state;
                state = PAUSEGAME;
                break;
                
        }
    }

	public PhysicalMap getPhys_map() {
		return phys_map;
	}

	public void setPhys_map(PhysicalMap phys_map) {
		this.phys_map = phys_map;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public BulletManager getBulletManager() {
		return bulletManager;
	}

	public void setBulletManager(BulletManager bulletManager) {
		this.bulletManager = bulletManager;
	}

	public ParticularObjectManager getParticularObjectManager() {
		return particularObjectManager;
	}

	public void setParticularObjectManager(ParticularObjectManager particularObjectManager) {
		this.particularObjectManager = particularObjectManager;
	}

	public BackgroundMap getMap() {
		return map;
	}

	public void setMap(BackgroundMap map) {
		this.map = map;
	}
    
	@Override
	public void actionMenu() {}

	
}