package com.example.myapplicationtest1.game.contents.engine;

import android.graphics.Color;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.core.Game;
import com.example.myapplicationtest1.game.paint.ImageFrame;
import com.example.myapplicationtest1.game.physics.stage.GHQStage;
import com.example.myapplicationtest1.game.storage.TableStorage;

import java.util.LinkedList;
import java.util.Stack;

public class Engine_Surviver extends Game {

	//lotteScreen
	public static int lotteAdded[] = new int[3];
	private static Knowledge lottedKnowledge;
	public static int lottedRarity;
	private static int lotteAnimeStartFrame = GHQ.NONE;
	//formationScreen
	static TableStorage<Knowledge> formationTS = new TableStorage<Knowledge>(4, 4, null);
	static int formationCount;
	private static int formationPlaceID;
	//garrageScreen
	static TableStorage<Knowledge> garrageTS = new TableStorage<Knowledge>(8, 5, null);
	static int garrageCount;
	private static LinkedList<Knowledge> newLotted = new LinkedList<Knowledge>();
	//battleScreen
	private static boolean doStageIdle;
	
	//stage
	static int stage = 0;
	
	//resourceAmount
	public static int resourceAmount[] = new int[3];
	
	@Override
	public String getVersion() {
		return "alpha1.0";
	}
	
	@Override
	public String getTitleName() {
		return "Surviver_" + getVersion();
	}
	public static void main(String args[]) {
		new GHQ(new Engine_Surviver());
	}

	public static final int getTotalBet() {
		return lotteAdded[0] + lotteAdded[1] + lotteAdded[2];
	}
	//enemy
	private static final Enemy.EnemyParameter[][][] enemyFormation = new Enemy.EnemyParameter[10][4][4];
	@Override
	public void loadResource() {
		//input;
		battleBGIF = ImageFrame.create("image/BattleBG.png");
		//gui
		/////////////////
		//General
		/////////////////
		//enemyFormation
		Enemy.EnemyParameter[][] section = enemyFormation[0];
		section[0][0] = Enemy.EnemyParameter.CHI_QZ;
		section[0][1] = Enemy.EnemyParameter.CHI_QZ;
		section[0][2] = Enemy.EnemyParameter.CHI_QZ;
		section[0][3] = Enemy.EnemyParameter.CHI_QZ;
		section[1][0] = Enemy.EnemyParameter.ENG_QZ;
		section[1][1] = Enemy.EnemyParameter.ENG_QZ;
		section[1][2] = Enemy.EnemyParameter.ENG_QZ;
		section[1][3] = Enemy.EnemyParameter.MAT_QZ;
		section[2][0] = Enemy.EnemyParameter.MAT_QZ;
		section[2][1] = Enemy.EnemyParameter.MAT_QZ;
		section[2][2] = Enemy.EnemyParameter.MAT_QZ;
		section[2][3] = Enemy.EnemyParameter.MAT_QZ;

		//backButton
		backToMenuButton = new BasicButton() {
			{
				this.setName("backButton");
				this.setBGImage("image/backButton.png");
				this.setBounds(0, 0, 100, 50);
			}
			@Override
			public boolean clicked(MouseEvent e) {
				if(screenHistory.peek() != null)
					screenHistory.pop().disable();
				if(screenHistory.peek() != null)
					screenHistory.peek().enable();
				else
					menuScreen.enable();
				return super.clicked(e);
			}
		};
		/////////////////
		//titleScreen
		/////////////////
		GHQ.addGUIParts(titleScreen = new GUIParts() {
			{
				this.setName("titleScreen");
			}
			/*private SerialImageFrameAlpha SIFA = new SerialImageFrameAlpha(10, "image/title1.png", "image/title2.png", "image/title3.png", "image/title4.png", "image/title5.png");
			@Override
			public void enable() {
				super.enable();
				SIFA.animate();
			}
			@Override
			public void idle() {
				final Graphics2D g2 = GHQ.getG2D();
				g2.setColor(Color.BLACK);
				SIFA.rectPaint(0, 0, GHQ.screenW(), GHQ.screenH());
				GHQ.drawStringGHQ("�����Ǳ��⻭��", 50, 40, 15f);
				GHQ.drawStringGHQ("Presented by \"�Ҵ���\"", GHQ.screenW() - 250, GHQ.screenH() - 50, 20f);
				super.idle();
			}*/
		});
		titleScreen.addLast(new GUIParts() {
			{
				this.setName("titleScreen");
				this.setBGPaint(new ColorFilling(Color.GRAY));
				this.setBounds(GHQ.screenW()/2 - 125,390,250,80);
			}
			@Override
			public boolean clicked(MouseEvent e) {
				titleScreen.disable();
				menuScreen.enable();
				screenHistory.push(menuScreen);
				return super.clicked(e);
			}
		});
		titleScreen.disable();
		/////////////////
		//menuScreen
		/////////////////
		GHQ.addGUIParts(menuScreen = new GUIParts() {
			private SerialImageFrameAlpha SIFA = new SerialImageFrameAlpha(this, 10, "image/title1.png", "image/title2.png", "image/title3.png", "image/title4.png", "image/title5.png");
			{
				this.setName("menuScreen");
			}
			@Override
			public GUIParts enable() {
				super.enable();
				SIFA.animate();
				return this;
			}
			@Override
			public void idle() {
				SIFA.rectPaint(0, 0, GHQ.screenW(), GHQ.screenH());
				super.idle();
			}
		});
		//menuScreen-upper side
		menuScreen.addFirst(resourcesButtons = new GUIParts() {
			{
				this.setName("resources");
				this.setBounds(400, 0, 600, 50);
				this.addFirst(new GUIParts().setBGImage("image/ChineseResourceIcon.png").setBounds(400, 0, 50, 50));
				this.addFirst(new GUIParts().setBGImage("image/MathResourceIcon.png").setBounds(600, 0, 50, 50));
				this.addFirst(new GUIParts().setBGImage("image/EnglishResourceIcon.png").setBounds(800, 0, 50, 50));
			}
			private ImageFrame framePaint = ImageFrame.create("image/Frame.png");
			@Override
			public void idle() {
				framePaint.rectPaint(400, 0, 200, 50);
				framePaint.rectPaint(600, 0, 200, 50);
				framePaint.rectPaint(800, 0, 200, 50);
				super.idle();
				final Graphics2D G2 = GHQ.getG2D();
				if(kejinCount > 0) {
					G2.setColor(Color.RED);
					GHQ.drawStringGHQ("��봽����: " + kejinCount, 200, 30, 20F);
				}
				G2.setColor(Color.WHITE);
				GHQ.drawStringGHQ(String.valueOf(resourceAmount[0]), 460, 30, GHQ.basicFont);
				GHQ.drawStringGHQ(String.valueOf(resourceAmount[1]), 660, 30, GHQ.basicFont);
				GHQ.drawStringGHQ(String.valueOf(resourceAmount[2]), 860, 30, GHQ.basicFont);
			}
		});
		//introduction
		menuScreen.addFirst(new GUIParts() {
			int step = 0;
			private final ImageFrame[] introIFs = new ImageFrame[8];
			{
				this.setName("introduction");
				this.setBounds(50, 50, GHQ.screenW() - 100, GHQ.screenH() - 100);
				for(int i = 0;i < introIFs.length;++i)
					introIFs[i] = ImageFrame.create("image/intro/" + i + ".png");
			}
			@Override
			public void idle() {
				super.idle();
				switch(step) {
				case 0:
				case 1:
				case 2:
				case 3:
					introIFs[step].dotPaint(200, 200);
					break;
				case 4:
					introIFs[step].dotPaint(240, 125);
					break;
				case 5:
					if(garrageCount > 0) {
						++step;
						introIFs[step].dotPaint(450, 230);
					}else
						introIFs[step].dotPaint(400, 200);
					break;
				case 6:
					if(formationCount > 0) {
						++step;
						introIFs[step].dotPaint(450, 275);
					}else
						introIFs[step].dotPaint(450, 230);
					break;
				case 7:
					introIFs[step].dotPaint(450, 275);
					break;
				}
			}
			@Override
			public boolean clicked(MouseEvent e) {
				switch(step) {
				case 0:
				case 1:
				case 2:
				case 3:
					++step;
					break;
				case 4:
					++step;
					for(int i = 0;i < resourceAmount.length;i++)
						resourceAmount[i] += 50;
					break;
				case 5:
				case 6:
					break;
				case 7:
					++step;
					break;
				};
				return super.clicked(e);
			}
		});
		//menuScreen-right side
		menuScreen.addFirst(new GUIParts() {
			{
				this.setName("toFormation");
				this.setBounds(500, 70, 400, 240);
			}
			private final ImageFrame buttonIF = ImageFrame.create("image/formation.png");
			@Override
			public boolean clicked(MouseEvent e) {
				menuScreen.disable();
				formationScreen.enable();
				screenHistory.push(formationScreen);
				return super.clicked(e);
			}
			@Override
			public void idle() {
				super.idle();
				buttonIF.dotPaint_rate(763, 166, 0.3);
			}
		});
		menuScreen.addFirst(new GUIParts() {
			{
				this.setName("toEnemy");
				this.setBounds(630, 325, 370, 125);
			}
			private final ImageFrame buttonIF = ImageFrame.create("image/chuji.png");
			@Override
			public boolean clicked(MouseEvent e) {
				menuScreen.disable();
				battleScreen.enable();
				screenHistory.push(battleScreen);
				return super.clicked(e);
			}
			@Override
			public void idle() {
				super.idle();
				buttonIF.dotPaint_rate(820, 390, 0.3);
			}
		});
		menuScreen.addFirst(new GUIParts() {
			{
				this.setName("toLotte");
				this.setBounds(200, 300, 400, 140);
			}
			private final ImageFrame buttonIF = ImageFrame.create("image/lotte.png");
			@Override
			public boolean clicked(MouseEvent e) {
				menuScreen.disable();
				lotteScreen.enable();
				screenHistory.push(lotteScreen);
				return super.clicked(e);
			}
			@Override
			public void idle() {
				super.idle();
				buttonIF.dotPaint_rate(450, 357, 0.3);
			}
		});
		menuScreen.enable();
		screenHistory.push(menuScreen);	
		/////////////////
		//lotteScreen
		/////////////////
		GHQ.addGUIParts(lotteScreen = new GUIParts() {
			private final int ANM_SIZE = 89;
			private final ImageFrame[] anime = new ImageFrame[ANM_SIZE];
			{
				this.setName("lotteScreen");
				this.setBGImage("image/lotteBG.jpeg");
				for(int i = 0;i < ANM_SIZE;++i)
					anime[i] = ImageFrame.create("image/lotte/" + (i + 1) + ".png");
			}
			private final ImageFrame[] cardsBack = {
				ImageFrame.create("image/Card0.png"),
				ImageFrame.create("image/Card1.png"),
				ImageFrame.create("image/Card2.png"),
				ImageFrame.create("image/Card3.png"),
				ImageFrame.create("image/Card4.png")
			};
			private final ImageFrame CardBGIF = ImageFrame.create("image/CardBG.png");
			@Override
			public void idle() {
				super.idle();
				//lotteAnimation
				if(lotteAnimeStartFrame != GHQ.NONE) {
					int frame = GHQ.passedFrame(lotteAnimeStartFrame);
					int span;
					Animate:{
						if(frame - ANM_SIZE < 0) {
							anime[frame].dotPaint(GHQ.screenW()/2, GHQ.screenH()/2);
							break Animate;
						}
						frame -= ANM_SIZE;
						span = 10;
						final ImageFrame IF = cardsBack[lottedRarity];
						if(frame - span < 0) {
							IF.dotPaint_turn(GHQ.screenW()/2, GHQ.screenH()/2, (double)frame/span*2*Math.PI);
							break Animate;
						}
						frame -= span;
						span = 15;
						if(frame - span < 0) {
							final int W = (int)(cardsBack[lottedRarity].width()*Math.cos((double)frame/span*Math.PI/2));
							final int H = cardsBack[lottedRarity].height();
							IF.rectPaint(GHQ.screenW()/2 - W/2, GHQ.screenH()/2 - H/2, W, H);
							break Animate;
						}
						frame -= span;
						span = 15;
						if(frame - span < 0) {
							final double COS = Math.cos(((double)frame/span + 1)*Math.PI/2);
							final int W = -(int)(cardsBack[lottedRarity].width()*COS);
							final int H = cardsBack[lottedRarity].height();
							CardBGIF.rectPaint(GHQ.screenW()/2 - W/2, GHQ.screenH()/2 - H/2, W, H);
							final int W2 = -(int)(lottedKnowledge.standPaint.width()*COS)*2;
							final int H2 = lottedKnowledge.standPaint.height()*2;
							lottedKnowledge.standPaint.rectPaint(GHQ.screenW()/2 - W2/2, GHQ.screenH()/2 - H2/2, W2, H2);
							break Animate;
						}
						frame -= span;
						{
							CardBGIF.dotPaint(GHQ.screenW()/2, GHQ.screenH()/2);
							lottedKnowledge.standPaint.dotPaint_rate(GHQ.screenW()/2, GHQ.screenH()/2, 2.0);
							GHQ.getG2D(Color.BLUE);
							GHQ.drawStringGHQ("��" + lottedKnowledge.NAME + "��", 270, 70, 20F);
							GHQ.drawStringGHQ(lottedKnowledge.PARAM.appendTalk, 270, 400, Font.BOLD, 20F);
							break Animate;
						}
					}
				}
			}
			@Override
			public boolean clicked(MouseEvent e) {
				if(lottedKnowledge != null) {
					lottedKnowledge = null;
					lotteAnimeStartFrame = GHQ.NONE;
				}
				return super.clicked(e);
			}
			
		}).addFirst(
				new BasicButton() {
					{
						this.setName("cancel");
						this.setBGImage("image/backButton.png");
						this.setBounds(0, 0, 100, 50);
					}
					@Override
					public boolean clicked(MouseEvent e) {
						screenHistory.pop().disable();
						screenHistory.peek().enable();
						for(int i = 0;i < lotteAdded.length;i++) {
							resourceAmount[i] += lotteAdded[i];
							lotteAdded[i] = 0;
						}
						lottedKnowledge = null;
						lotteAnimeStartFrame = GHQ.NONE;
						return super.clicked(e);
					}
				});
		lotteScreen.addLast(resourcesButtons);
		lotteScreen.addLast(new AutoResizeMenu(0, 350, GHQ.screenW(), 90, 10) {
				{
					this.setName("lotteAddMenu");
					addNewLine(
							new BetButton(Subject.CHI, 1), new BetButton(Subject.CHI, 5), new BetButton(Subject.CHI, 10),
							new BetButton(Subject.MAT, 1), new BetButton(Subject.MAT, 5), new BetButton(Subject.MAT, 10),
							new BetButton(Subject.ENG, 1), new BetButton(Subject.ENG, 5), new BetButton(Subject.ENG, 10));
				}
				@Override
				public void idle() {
					super.idle();
					GHQ.getG2D(Color.RED);
					GHQ.drawStringGHQ("CHI: " + lotteAdded[0], 260, 310, GHQ.basicFont);
					GHQ.getG2D(Color.GREEN.darker());
					GHQ.drawStringGHQ("MAT: " + lotteAdded[1], 460, 310, GHQ.basicFont);
					GHQ.getG2D(Color.BLUE);
					GHQ.drawStringGHQ("ENG: " + lotteAdded[2], 660, 310, GHQ.basicFont);
				}
			}
		);
		lotteScreen.addFirst(new GUIParts() {
			{
				this.setName("startLotte");
				this.setBGImage("image/ui/GrayButton.png");
				this.setBounds(350, 450, 300, 150);
			}
			@Override
			public void idle() {
				super.idle();
				GHQ.getG2D(Color.BLACK);
				GHQ.drawStringGHQ("���֪ʶ", 445, 530, 30F);
			}
			@Override
			public boolean clicked(MouseEvent e) {
				final boolean b = super.clicked(e);
				int total = 0;
				for(int ver : lotteAdded)
					total += ver;
				if(total == 0)
					return b;
				//addLottedUnit
				lottedKnowledge = Knowledge.KnowledgeParameter.getLotte(lotteAdded[0],lotteAdded[1],lotteAdded[2]);
				newLotted.add(lottedKnowledge);
				garrageTS.add(lottedKnowledge);
				//setAnimation
				lotteAnimeStartFrame = GHQ.nowFrame();
				++garrageCount;
				for(int i = 0;i < lotteAdded.length;i++)
					lotteAdded[i] = 0;
				return b;
			}
		});
		/////////////////
		//formationScreen
		/////////////////
		GHQ.addGUIParts(formationScreen = new GUIParts().setBGImage("image/garrageBG.png")).addFirst(backToMenuButton).setName("formationScreen");
		formationScreen.addFirst(new TableStorageViewer<Knowledge>() {
			{
				this.setName("formation");
				this.setCellSize(100);
				this.setTableStorage(formationTS);
				this.setXY(100, 100);
			}
			@Override
			public void idle() {
				super.idle();
				final Graphics2D g2 = GHQ.getG2D(Color.BLACK);
				final Font font = g2.getFont();
				g2.setFont(font.deriveFont(15f));
				final Knowledge unit = getMouseHoveredElement();
				if(unit != null) {
					GHQ.getG2D(Color.WHITE);
					GHQ.drawStringGHQ(unit.PARAM.NAME, 550, 75, 30.0F);
					GHQ.drawStringGHQ("SUBJECT: " + unit.PARAM.SUBJECT.toCHIString(), 550, 95, GHQ.basicFont);
					GHQ.drawStringGHQ("Rarity: " + unit.PARAM.getRarity(), 550, 115, GHQ.basicFont);
					GHQ.drawStringGHQ("HP: " + unit.PARAM.iniHP, 550, 135, GHQ.basicFont);
					GHQ.drawStringGHQ("ATK: " + unit.PARAM.iniATK, 550, 155, GHQ.basicFont);
					GHQ.drawStringGHQ("DEF: " + unit.PARAM.iniDEF, 550, 175, GHQ.basicFont);
					GHQ.drawStringGHQ("ATK: " + unit.PARAM.iniATK, 550, 195, GHQ.basicFont);
					GHQ.drawStringGHQ("ATKRange: " + unit.PARAM.iniATKRange, 550, 215, GHQ.basicFont);
					GHQ.drawStringGHQ("ATKCD: " + unit.PARAM.iniCD + " frame per 1 attack", 550, 235, GHQ.basicFont);
					GHQ.getG2D(new Color(255,255,255,200));
					GHQ.getG2D().fillRect(550, 295, 400, 200);
					GHQ.getG2D(Color.BLACK);
					GHQ.drawStringGHQ(unit.PARAM.description, 550, 325, 25, 28);
				}
				g2.setFont(font);
				g2.setColor(Color.WHITE);
				GHQ.drawStringGHQ("�ѱ��֪ʶ����: " + formationCount, 110, 80, g2.getFont().deriveFont(Font.BOLD, 20F));
			}
			private final RectPaint cellRectPaint = ImageFrame.create("image/ui/slot.png");
			@Override
			protected void paintOfCell(int index, HasDotPaint object, int x,int y) {
				cellRectPaint.rectPaint(x, y, cellSize);
				if(object != null)
					object.getDotPaint().dotPaint_capSize(x + cellSize/2, y + cellSize/2, (int)(cellSize*0.8));
			}
			@Override
			public boolean clicked(MouseEvent e) { //move to garrageScreen
				/*final int HOVERED_ID = getMouseHoveredIndex();
				if(itemMouseHook.hasObject()) {
					itemMouseHook.hook(storage.set(HOVERED_ID, itemMouseHook.get()));
				}else {
					itemMouseHook.hook(storage.remove(HOVERED_ID));
				}*/
				formationPlaceID = getMouseHoveredIndex();
				formationScreen.disable();
				garrageScreen.enable();
				return super.clicked(e);
			}
			@Override
			public void released(MouseEvent e) {
				super.released(e);
				/*final int HOVERED_ID = getMouseHoveredIndex();
				if(itemMouseHook.hasObject()) {
					itemMouseHook.hook(storage.set(HOVERED_ID, itemMouseHook.get()));
				}else {
					itemMouseHook.hook(storage.remove(HOVERED_ID));
				}*/
			}
			@Override
			public void outsideReleased() {
				//itemMouseHook.hook(null);
			}
			@Override
			public Knowledge objectToT(Object object) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		formationScreen.disable();
		/////////////////
		//garrageScreen
		/////////////////
		GHQ.addGUIParts(garrageScreen = new GUIParts() {
			{
				this.setName("garrageScreen");
				this.setBGImage("image/garrageBG.png");
			}
		}).addFirst(new BasicButton() {
				{
					this.setName("cancel");
					this.setBGImage("image/backButton.png");
					this.setBounds(0, 0, 100, 50);
				}
				@Override
				public boolean clicked(MouseEvent e) {
					formationPlaceID = GHQ.NONE;
					garrageScreen.disable();
					formationScreen.enable();
					return super.clicked(e);
				}
			}
		);
		garrageScreen.addFirst(new TableStorageViewer<Knowledge>() {
			{
				this.setName("knowledgeStorage");
				this.setTableStorage(garrageTS);
				this.setCellSize(100);
				this.setXY(100, 100);
			}
			@Override
			public void idle() {
				final Graphics2D g2 = GHQ.getG2D();
				super.idle();
				g2.setColor(Color.WHITE);
				GHQ.drawStringGHQ("����֪ʶ����: " + garrageCount, 110, 80, g2.getFont().deriveFont(Font.BOLD, 20F));
			}
			private final RectPaint cellRectPaint = ImageFrame.create("image/ui/slot.png");
			@Override
			protected void paintOfCell(int id, HasDotPaint object, int x, int y) {
				cellRectPaint.rectPaint(x, y, cellSize);
				if(object != null) {
					object.getDotPaint().dotPaint_capSize(x + cellSize/2, y + cellSize/2, (int)(cellSize*0.8));
					if(formationTS.contains(object)) {
						GHQ.getG2D().setColor(Color.ORANGE);
						GHQ.drawStringGHQ("�ѱ��", x + 10, y + 85, GHQ.getG2D().getFont().deriveFont(Font.BOLD, 15f));
					}
				}
			}
			@Override
			public boolean clicked(MouseEvent e) {
				final Knowledge NEW_MEMBER = garrageTS.get(getMouseHoveredIndex());
				if(NEW_MEMBER != null) { //join member
					if(formationTS.contains(NEW_MEMBER))
						formationTS.remove(NEW_MEMBER);
					else
						++formationCount;
					formationTS.set(formationPlaceID, NEW_MEMBER);
				}else { //leave member
					if(formationTS.get(formationPlaceID) != null) {
						formationTS.set(formationPlaceID, null);
						--formationCount;
					}
				}
				garrageScreen.disable();
				formationScreen.enable();
				return super.clicked(e);
			}
			@Override
			public Knowledge objectToT(Object object) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		garrageScreen.disable();
		/////////////////
		//enemyScreen
		/////////////////
		GHQ.addGUIParts(enemyScreen = new GUIParts() {
			{
				this.setName("enemyScreen");
				this.setBGColor(Color.RED);
			}
		}).addFirst(backToMenuButton);
		enemyScreen.disable();
		/////////////////
		//battleScreen
		/////////////////
		GHQ.addGUIParts(battleScreen = new GUIParts() {
			{
				this.setName("enemyScreen");
			}
			boolean isGameClear;
			boolean isGameOver;
			@Override
			public void idle() {
				super.idle();
				isGameClear = isGameOver = true;
				for(Unit unit : GHQ.stage().units) {
					if(!isGameClear && !isGameOver)
						break;
					if(isGameClear && unit instanceof Enemy) {
						isGameClear = false;
					}
					if(isGameOver && unit instanceof Knowledge) {
						isGameOver = false;
					}
				}
				//count lest enemy
				if(isGameClear) {
					GHQ.getG2D(Color.RED);
					GHQ.drawStringGHQ("EXAM FINISH", 500, 300, GHQ.basicFont.deriveFont(Font.BOLD, 50f));
				} else if(isGameOver) {
					GHQ.getG2D(Color.RED);
					GHQ.drawStringGHQ("FAILED...", 500, 300, GHQ.basicFont.deriveFont(Font.BOLD, 50f));
				}
			}
			@Override
			public GUIParts enable() {
				super.enable();
				doStageIdle = true;
				GHQ.stage().clear();
				//friend
				for(int x = 0;x < 4;x++) {
					for(int y = 0;y < 4;y++) {
						final Knowledge unit = formationTS.get(x + y*4);
						if(unit != null) {
							GHQ.stage().addUnit(unit).respawn(100 + x*100, 100 + y*100);
						}
					}
				}
				for(int x = 0;x < enemyFormation[0].length;x++) {
					for(int y = 0;y < enemyFormation[0][x].length;y++) {
						final Enemy.EnemyParameter PARAM = enemyFormation[0][x][y];
						if(PARAM != null)
							GHQ.stage().addUnit(PARAM.generate()).respawn(500 + x*100, 100 + y*100);
					}
				}
				Enemy.chiCount = Enemy.matCount = Enemy.engCount = 0;
				for(Knowledge unit : formationTS) {
					if(unit == null)
						continue;
					switch(unit.PARAM.SUBJECT) {
					case CHI:
						++Enemy.chiCount;
						break;
					case MAT:
						++Enemy.matCount;
						break;
					case ENG:
						++Enemy.engCount;
						break;
					default:
					}
				}
				return this;
			}
			@Override
			public GUIParts disable() {
				super.disable();
				doStageIdle = false;
				if(isGameClear)
					++stage;
				return this;
			}
		}).addFirst(
				new BasicButton() {
					{
						this.setName("backToMenu");
						this.setBGImage("image/backButton.png");
						this.setBounds(0, 0, 100, 50);
					}
					@Override
					public boolean clicked(MouseEvent e) {
						screenHistory.clear();
						screenHistory.push(menuScreen);
						battleScreen.disable();
						menuScreen.enable();
						return super.clicked(e);
					}
				});
		battleScreen.disable();
	}

	@Override
	public GHQStage loadStage() {
		return new GHQStage(1000, 1000);
	}

	private static ImageFrame battleBGIF;
	private static int kejinCount = 0;
	@Override
	public void idle(int stopEventKind) {
		if(doStageIdle) {
			battleBGIF.rectPaint(0, 0, GHQ.screenW(), GHQ.screenH());
			GHQ.stage().idle();
			GHQ.getG2D(Color.ORANGE);
			GHQ.drawStringGHQ("STAGE: " + stage, 60, 60, GHQ.basicFont);
		}else {
			if(GHQ.key_shift){
				++kejinCount;
				resourceAmount[0] += 50;
				resourceAmount[1] += 50;
				resourceAmount[2] += 50;
			}
		}
	}
}
