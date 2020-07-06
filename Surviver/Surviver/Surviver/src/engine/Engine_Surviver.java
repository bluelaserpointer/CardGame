package engine;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_F6;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.awt.event.KeyEvent.VK_W;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Stack;

import core.GHQ;
import core.GHQStage;
import core.Game;
import core.Standpoint;
import gui.AutoResizeMenu;
import gui.BasicButton;
import gui.CombinedButtons;
import gui.GUIGroup;
import gui.TableStorageViewer;
import input.SingleKeyListener;
import paint.ColorFilling;
import paint.HasDotPaint;
import paint.ImageFrame;
import paint.RectPaint;
import paint.SerialImageFrame;
import paint.SerialImageFrameAlpha;
import stage.StageSaveData;
import storage.TableStorage;
import unit.BulletLibrary;
import unit.EffectLibrary;

public class Engine_Surviver extends Game{
	
	//gui
	private static GUIGroup titleScreen, menuScreen, lotteScreen, garrageScreen, formationScreen, enemyScreen, battleScreen;
	private static Stack<GUIGroup> screenHistory = new Stack<GUIGroup>();
	//menuScreen
	private static CombinedButtons resourcesButtons;
	private static BasicButton backToMenuButton;
	//lotteScreen
	private static int lotteAdded[] = new int[3];
	private static Knowledge lottedKnowledge;
	public static int lottedRarity;
	private static int lotteAnimeStartFrame = GHQ.NONE;
	//formationScreen
	static TableStorage<Knowledge> formationTS = new TableStorage<Knowledge>(4, 4);
	static int formationCount;
	private static int formationPlaceID;
	//garrageScreen
	static TableStorage<Knowledge> garrageTS = new TableStorage<Knowledge>(8, 5);
	static int garrageCount;
	private static LinkedList<Knowledge> newLotted = new LinkedList<Knowledge>();
	//battleScreen
	private static boolean doStageIdle;
	
	//stage
	static int stage = 0;
	
	//resourceAmount
	static int resourceAmount[] = new int[3];
	
	//inputkeys

	private static final int inputKeys[] = 
	{
		VK_SPACE,
	};
	public static final SingleKeyListener s_keyL = new SingleKeyListener(inputKeys);
	@Override
	public String getVersion() {
		return "alpha1.0";
	}
	
	@Override
	public String getTitleName() {
		return "Surviver_" + getVersion();
	}
	public static void main(String args[]){
		new GHQ(new Engine_Surviver());
	}

	private final int getTotalBet() {
		return lotteAdded[0] + lotteAdded[1] + lotteAdded[2];
	}
	//enemy
	private static final Enemy.EnemyParameter[][][] enemyFormation = new Enemy.EnemyParameter[10][4][4];
	@Override
	public void loadResource() {
		BulletLibrary.loadResource();
		EffectLibrary.loadResource();
		//input
		GHQ.addListenerEx(s_keyL);
		battleBGIF = new ImageFrame("image/BattleBG.png");
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
		backToMenuButton = new BasicButton("backButton", new ImageFrame("image/backButton.png"), 0, 0, 100, 50) {
			@Override
			public void clicked() {
				if(screenHistory.peek() != null)
					screenHistory.pop().disable();
				if(screenHistory.peek() != null)
					screenHistory.peek().enable();
				else
					menuScreen.enable();
			}
		};
		/////////////////
		//titleScreen
		/////////////////
		GHQ.addGUIParts(titleScreen = new GUIGroup("titleScreen", null) {
			/*private SerialImageFrameAlpha SIFA = new SerialImageFrameAlpha(10, "image/title1.png", "image/title2.png", "image/title3.png", "image/title4.png", "image/title5.png");
			@Override
			public void enable() {
				super.enable();
				SIFA.animate();
			}
			@Override
			public void idle() {
				final Graphics2D g2 = GHQ.getGraphics2D();
				g2.setColor(Color.BLACK);
				SIFA.rectPaint(0, 0, GHQ.getScreenW(), GHQ.getScreenH());
				GHQ.drawStringGHQ("这里是标题画面", 50, 40, 15f);
				GHQ.drawStringGHQ("Presented by \"幸存者\"", GHQ.getScreenW() - 250, GHQ.getScreenH() - 50, 20f);
				super.idle();
			}*/
		});
		titleScreen.addParts(new BasicButton("titleScreen", new ColorFilling(Color.GRAY),GHQ.getScreenW()/2 - 125,390,250,80) {
			@Override
			public void clicked() {
				titleScreen.disable();
				menuScreen.enable();
				screenHistory.push(menuScreen);				
			}
		});
		titleScreen.disable();
		/////////////////
		//menuScreen
		/////////////////
		GHQ.addGUIParts(menuScreen = new GUIGroup("menuScreen", null) {
			private SerialImageFrameAlpha SIFA = new SerialImageFrameAlpha(10, "image/title1.png", "image/title2.png", "image/title3.png", "image/title4.png", "image/title5.png");

			@Override
			public void enable() {
				super.enable();
				SIFA.animate();
			}
			@Override
			public void idle() {
				SIFA.rectPaint(0, 0, GHQ.getScreenW(), GHQ.getScreenH());
				super.idle();
			}
		});
		//menuScreen-upper side
		menuScreen.addPartsToTop(resourcesButtons = new CombinedButtons("resources", null, 400, 0, 600, 50) {
			private ImageFrame framePaint = new ImageFrame("image/Frame.png");
			@Override
			public void idle() {
				framePaint.rectPaint(400, 0, 200, 50);
				framePaint.rectPaint(600, 0, 200, 50);
				framePaint.rectPaint(800, 0, 200, 50);
				super.idle();
				final Graphics2D G2 = GHQ.getGraphics2D();
				if(kejinCount > 0) {
					G2.setColor(Color.RED);
					GHQ.drawStringGHQ("已氪金次数: " + kejinCount, 200, 30, 20F);
				}
				G2.setColor(Color.WHITE);
				GHQ.drawStringGHQ(String.valueOf(resourceAmount[0]), 460, 30, GHQ.basicFont);
				GHQ.drawStringGHQ(String.valueOf(resourceAmount[1]), 660, 30, GHQ.basicFont);
				GHQ.drawStringGHQ(String.valueOf(resourceAmount[2]), 860, 30, GHQ.basicFont);
			}
		});
		resourcesButtons.addButton(0, new ImageFrame("image/ChineseResourceIcon.png"),400,0,50,50);
		resourcesButtons.addButton(1, new ImageFrame("image/MathResourceIcon.png"),600,0,50,50);
		resourcesButtons.addButton(2, new ImageFrame("image/EnglishResourceIcon.png"),800,0,50,50);
		//introduction
		menuScreen.addPartsToTop(new BasicButton("introduction", null, 50, 50, GHQ.getScreenW() - 100, GHQ.getScreenH() - 100) {
			int step = 0;
			private final ImageFrame[] introIFs = new ImageFrame[8];
			{
				for(int i = 0;i < introIFs.length;++i)
					introIFs[i] = new ImageFrame("image/intro/" + i + ".png");
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
			public void clicked() {
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
			}
		});
		//menuScreen-right side
		menuScreen.addPartsToTop(new BasicButton("toFormation", null, 500, 70, 400, 240) {
			private final ImageFrame buttonIF = new ImageFrame("image/formation.png");
			@Override
			public void clicked() {
				menuScreen.disable();
				formationScreen.enable();
				screenHistory.push(formationScreen);
			}
			@Override
			public void idle() {
				super.idle();
				buttonIF.dotPaint_rate(763, 166, 0.3);
			}
		});
		menuScreen.addPartsToTop(new BasicButton("toEnemy", null, 630, 325, 370, 125) {
			private final ImageFrame buttonIF = new ImageFrame("image/chuji.png");
			@Override
			public void clicked() {
				menuScreen.disable();
				battleScreen.enable();
				screenHistory.push(battleScreen);
			}
			@Override
			public void idle() {
				super.idle();
				buttonIF.dotPaint_rate(820, 390, 0.3);
			}
		});
		menuScreen.addPartsToTop(new BasicButton("toLotte", null, 200, 300, 400, 140) {
			private final ImageFrame buttonIF = new ImageFrame("image/lotte.png");
			@Override
			public void clicked() {
				menuScreen.disable();
				lotteScreen.enable();
				screenHistory.push(lotteScreen);
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
		GHQ.addGUIParts(lotteScreen = new GUIGroup("lotteScreen", new ImageFrame("image/lotteBG.jpeg")) {
			private final int ANM_SIZE = 89;
			private final ImageFrame[] anime = new ImageFrame[ANM_SIZE];
			{
				for(int i = 0;i < ANM_SIZE;++i)
					anime[i] = new ImageFrame("image/lotte/" + (i + 1) + ".png");
			}
			private final ImageFrame[] cardsBack = {
				new ImageFrame("image/Card0.png"),
				new ImageFrame("image/Card1.png"),
				new ImageFrame("image/Card2.png"),
				new ImageFrame("image/Card3.png"),
				new ImageFrame("image/Card4.png")
			};
			private final ImageFrame CardBGIF = new ImageFrame("image/CardBG.png");
			@Override
			public void idle() {
				super.idle();
				//lotteAnimation
				if(lotteAnimeStartFrame != GHQ.NONE) {
					int frame = GHQ.getPassedFrame(lotteAnimeStartFrame);
					int span;
					Animate:{
						if(frame - ANM_SIZE < 0) {
							anime[frame].dotPaint(GHQ.getScreenW()/2, GHQ.getScreenH()/2);
							break Animate;
						}
						frame -= ANM_SIZE;
						span = 10;
						final ImageFrame IF = cardsBack[lottedRarity];
						if(frame - span < 0) {
							IF.dotPaint_turn(GHQ.getScreenW()/2, GHQ.getScreenH()/2, (double)frame/span*2*Math.PI);
							break Animate;
						}
						frame -= span;
						span = 15;
						if(frame - span < 0) {
							final int W = (int)(cardsBack[lottedRarity].getDefaultW()*Math.cos((double)frame/span*Math.PI/2));
							final int H = cardsBack[lottedRarity].getDefaultH();
							IF.rectPaint(GHQ.getScreenW()/2 - W/2, GHQ.getScreenH()/2 - H/2, W, H);
							break Animate;
						}
						frame -= span;
						span = 15;
						if(frame - span < 0) {
							final double COS = Math.cos(((double)frame/span + 1)*Math.PI/2);
							final int W = -(int)(cardsBack[lottedRarity].getDefaultW()*COS);
							final int H = cardsBack[lottedRarity].getDefaultH();
							CardBGIF.rectPaint(GHQ.getScreenW()/2 - W/2, GHQ.getScreenH()/2 - H/2, W, H);
							final int W2 = -(int)(lottedKnowledge.standPaint.getDefaultW()*COS)*2;
							final int H2 = lottedKnowledge.standPaint.getDefaultH()*2;
							lottedKnowledge.standPaint.rectPaint(GHQ.getScreenW()/2 - W2/2, GHQ.getScreenH()/2 - H2/2, W2, H2);
							break Animate;
						}
						frame -= span;
						{
							CardBGIF.dotPaint(GHQ.getScreenW()/2, GHQ.getScreenH()/2);
							lottedKnowledge.standPaint.dotPaint_rate(GHQ.getScreenW()/2, GHQ.getScreenH()/2, 2.0);
							GHQ.getGraphics2D(Color.BLUE);
							GHQ.drawStringGHQ("【" + lottedKnowledge.NAME + "】", 270, 70, 20F);
							GHQ.drawStringGHQ(lottedKnowledge.PARAM.appendTalk, 270, 400, Font.BOLD, 20F);
							break Animate;
						}
					}
				}
			}
			@Override
			public void clicked() {
				if(lottedKnowledge != null) {
					lottedKnowledge = null;
					lotteAnimeStartFrame = GHQ.NONE;
				}
				super.clicked();
			}
			
		}).addPartsToTop(
				new BasicButton("cancel", new ImageFrame("image/backButton.png"), 0, 0, 100, 50) {
					@Override
					public void clicked() {
						screenHistory.pop().disable();
						screenHistory.peek().enable();
						for(int i = 0;i < lotteAdded.length;i++) {
							resourceAmount[i] += lotteAdded[i];
							lotteAdded[i] = 0;
						}
						lottedKnowledge = null;
						lotteAnimeStartFrame = GHQ.NONE;
					}
				});
		lotteScreen.addParts(resourcesButtons);
		lotteScreen.addParts(new AutoResizeMenu("lotteAddMenu", null, 0, 350, GHQ.getScreenW(), 90, 10) {
				@Override
				public void idle() {
					super.idle();
					GHQ.getGraphics2D(Color.RED);
					GHQ.drawStringGHQ("CHI: " + lotteAdded[0], 260, 310, GHQ.basicFont);
					GHQ.getGraphics2D(Color.GREEN.darker());
					GHQ.drawStringGHQ("MAT: " + lotteAdded[1], 460, 310, GHQ.basicFont);
					GHQ.getGraphics2D(Color.BLUE);
					GHQ.drawStringGHQ("ENG: " + lotteAdded[2], 660, 310, GHQ.basicFont);
				}
			}
			.addNewLine(
				new BasicButton("add1" + Subject.values()[0].toString(), new ImageFrame("image/CHI_BET.png")) {
					@Override
					public void idle() {
						super.idle();
						GHQ.getGraphics2D(Color.RED);
						GHQ.drawStringGHQ("BET+1", x, y + 90, GHQ.basicFont.deriveFont(Font.BOLD));
					}
					@Override
					public void clicked() {
						if(resourceAmount[0] > 0 && getTotalBet() < 100) {
							++lotteAdded[0];
							--resourceAmount[0];
						}
					}
				},
				new BasicButton("add5" + Subject.values()[0].toString(), new ImageFrame("image/CHI_BET.png")) {
					@Override
					public void idle() {
						super.idle();
						GHQ.getGraphics2D(Color.RED);
						GHQ.drawStringGHQ("BET+5", x, y + 90, GHQ.basicFont.deriveFont(Font.BOLD));
					}
					@Override
					public void clicked() {
						if(resourceAmount[0] >= 5 && getTotalBet() <= 95) {
							lotteAdded[0] += 5;
							resourceAmount[0] -= 5;
						}
					}
				},
				new BasicButton("add10" + Subject.values()[0].toString(), new ImageFrame("image/CHI_BET.png")) {
					@Override
					public void idle() {
						super.idle();
						GHQ.getGraphics2D(Color.RED);
						GHQ.drawStringGHQ("BET+10", x, y + 90, GHQ.basicFont.deriveFont(Font.BOLD));
					}
					@Override
					public void clicked() {
						if(resourceAmount[0] >= 10 && getTotalBet() <= 90) {
							lotteAdded[0] += 10;
							resourceAmount[0] -= 10;
						}
					}
				},
				new BasicButton("add1" + Subject.values()[1].toString(), new ImageFrame("image/MAT_BET.png")) {
					@Override
					public void idle() {
						super.idle();
						GHQ.getGraphics2D(Color.GREEN);
						GHQ.drawStringGHQ("BET+1", x, y + 90, GHQ.basicFont.deriveFont(Font.BOLD));
					}
					@Override
					public void clicked() {
						if(resourceAmount[1] > 0 && getTotalBet() < 100) {
							++lotteAdded[1];
							--resourceAmount[1];
						}
					}
				},
				new BasicButton("add5" + Subject.values()[1].toString(), new ImageFrame("image/MAT_BET.png")) {
					@Override
					public void idle() {
						super.idle();
						GHQ.getGraphics2D(Color.GREEN);
						GHQ.drawStringGHQ("BET+5", x, y + 90, GHQ.basicFont.deriveFont(Font.BOLD));
					}
					@Override
					public void clicked() {
						if(resourceAmount[1] >= 5 && getTotalBet() <= 95) {
							lotteAdded[1] += 5;
							resourceAmount[1] -= 5;
						}
					}
				},
				new BasicButton("add10" + Subject.values()[1].toString(), new ImageFrame("image/MAT_BET.png")) {
					@Override
					public void idle() {
						super.idle();
						GHQ.getGraphics2D(Color.GREEN);
						GHQ.drawStringGHQ("BET+10", x, y + 90, GHQ.basicFont.deriveFont(Font.BOLD));
					}
					@Override
					public void clicked() {
						if(resourceAmount[1] >= 10 && getTotalBet() <= 90) {
							lotteAdded[1] += 10;
							resourceAmount[1] -= 10;
						}
					}
				},
				new BasicButton("add1" + Subject.values()[2].toString(), new ImageFrame("image/ENG_BET.png")) {
					@Override
					public void idle() {
						super.idle();
						GHQ.getGraphics2D(Color.BLUE);
						GHQ.drawStringGHQ("BET+1", x, y + 90, GHQ.basicFont.deriveFont(Font.BOLD));
					}
					@Override
					public void clicked() {
						if(resourceAmount[2] > 0 && getTotalBet() < 100) {
							++lotteAdded[2];
							--resourceAmount[2];
						}
					}
				},
				new BasicButton("add5" + Subject.values()[2].toString(), new ImageFrame("image/ENG_BET.png")) {
					@Override
					public void idle() {
						super.idle();
						GHQ.getGraphics2D(Color.BLUE);
						GHQ.drawStringGHQ("BET+5", x, y + 90, GHQ.basicFont.deriveFont(Font.BOLD));
					}
					@Override
					public void clicked() {
						if(resourceAmount[2] >= 5 && getTotalBet() <= 95) {
							lotteAdded[2] += 5;
							resourceAmount[2] -= 5;
						}
					}
				},
				new BasicButton("add10" + Subject.values()[2].toString(), new ImageFrame("image/ENG_BET.png")) {
					@Override
					public void idle() {
						super.idle();
						GHQ.getGraphics2D(Color.BLUE);
						GHQ.drawStringGHQ("BET+10", x, y + 90, GHQ.basicFont.deriveFont(Font.BOLD));
					}
					@Override
					public void clicked() {
						if(resourceAmount[2] >= 10 && getTotalBet() <= 90) {
							lotteAdded[2] += 10;
							resourceAmount[2] -= 10;
						}
					}
				}
			)
		);
		lotteScreen.addParts(new BasicButton("startLotte", new ImageFrame("picture/GrayButton.png"), 350, 450, 300, 150) {
			@Override
			public void idle() {
				super.idle();
				GHQ.getGraphics2D(Color.BLACK);
				GHQ.drawStringGHQ("获得知识", 445, 530, 30F);
			}
			@Override
			public void clicked() {
				int total = 0;
				for(int ver : lotteAdded)
					total += ver;
				if(total == 0)
					return;
				//addLottedUnit
				lottedKnowledge = Knowledge.KnowledgeParameter.getLotte(lotteAdded[0],lotteAdded[1],lotteAdded[2]);
				newLotted.add(lottedKnowledge);
				garrageTS.add(lottedKnowledge);
				//setAnimation
				lotteAnimeStartFrame = GHQ.getNowFrame();
				++garrageCount;
				for(int i = 0;i < lotteAdded.length;i++)
					lotteAdded[i] = 0;
			}
		});
		/////////////////
		//formationScreen
		/////////////////
		GHQ.addGUIParts(formationScreen = new GUIGroup("formationScreen", new ImageFrame("image/garrageBG.png"))).addPartsToTop(backToMenuButton);
		formationScreen.addPartsToTop(new TableStorageViewer<Knowledge>("formation", new ImageFrame("picture/gui/slot.png"), 100, 100, 100, formationTS) {
			@Override
			public void idle() {
				super.idle();
				final Graphics2D g2 = GHQ.getGraphics2D(Color.BLACK);
				final Font font = g2.getFont();
				g2.setFont(font.deriveFont(15f));
				final Knowledge unit = getMouseHoveredElement();
				if(unit != null) {
					GHQ.getGraphics2D(Color.WHITE);
					GHQ.drawStringGHQ(unit.PARAM.NAME, 550, 75, 30.0F);
					GHQ.drawStringGHQ("SUBJECT: " + unit.PARAM.SUBJECT.toCHIString(), 550, 95, GHQ.basicFont);
					GHQ.drawStringGHQ("Rarity: " + unit.PARAM.getRarity(), 550, 115, GHQ.basicFont);
					GHQ.drawStringGHQ("HP: " + unit.PARAM.iniHP, 550, 135, GHQ.basicFont);
					GHQ.drawStringGHQ("ATK: " + unit.PARAM.iniATK, 550, 155, GHQ.basicFont);
					GHQ.drawStringGHQ("DEF: " + unit.PARAM.iniDEF, 550, 175, GHQ.basicFont);
					GHQ.drawStringGHQ("ATK: " + unit.PARAM.iniATK, 550, 195, GHQ.basicFont);
					GHQ.drawStringGHQ("ATKRange: " + unit.PARAM.iniATKRange, 550, 215, GHQ.basicFont);
					GHQ.drawStringGHQ("ATKCD: " + unit.PARAM.iniCD + " frame per 1 attack", 550, 235, GHQ.basicFont);
					GHQ.getGraphics2D(new Color(255,255,255,200));
					GHQ.getGraphics2D().fillRect(550, 295, 400, 200);
					GHQ.getGraphics2D(Color.BLACK);
					GHQ.drawStringGHQ(unit.PARAM.description, 550, 325, 25, 28);
				}
				g2.setFont(font);
				g2.setColor(Color.WHITE);
				GHQ.drawStringGHQ("已编队知识总数: " + formationCount, 110, 80, g2.getFont().deriveFont(Font.BOLD, 20F));
			}
			private final RectPaint cellRectPaint = new ImageFrame("picture/gui/slot.png");
			@Override
			protected void paintOfCell(HasDotPaint object, int x,int y) {
				cellRectPaint.rectPaint(x, y, CELL_SIZE);
				if(object != null)
					object.getPaintScript().dotPaint_capSize(x + CELL_SIZE/2, y + CELL_SIZE/2, (int)(CELL_SIZE*0.8));
			}
			@Override
			public void clicked() { //move to garrageScreen
				/*final int HOVERED_ID = getMouseHoveredID();
				if(itemMouseHook.hasObject()) {
					itemMouseHook.hook(storage.set(HOVERED_ID, itemMouseHook.get()));
				}else {
					itemMouseHook.hook(storage.remove(HOVERED_ID));
				}*/
				formationPlaceID = getMouseHoveredID();
				formationScreen.disable();
				garrageScreen.enable();
			}
			@Override
			public void released() {
				/*final int HOVERED_ID = getMouseHoveredID();
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
		});
		formationScreen.disable();
		/////////////////
		//garrageScreen
		/////////////////
		GHQ.addGUIParts(garrageScreen = new GUIGroup("garrageScreen", new ImageFrame("image/garrageBG.png")))
			.addPartsToTop(new BasicButton("cancel", new ImageFrame("image/backButton.png"), 0, 0, 100, 50) {
				@Override
				public void clicked() {
					formationPlaceID = GHQ.NONE;
					garrageScreen.disable();
					formationScreen.enable();
				}
			}
		);
		garrageScreen.addPartsToTop(new TableStorageViewer<Knowledge>("knowledgeStorage", new ImageFrame("picture/gui/slot.png"), 100, 100, 100, garrageTS) {
			@Override
			public void idle() {
				final Graphics2D g2 = GHQ.getGraphics2D();
				super.idle();
				g2.setColor(Color.WHITE);
				GHQ.drawStringGHQ("现有知识总数: " + garrageCount, 110, 80, g2.getFont().deriveFont(Font.BOLD, 20F));
			}
			private final RectPaint cellRectPaint = new ImageFrame("picture/gui/slot.png");
			@Override
			protected void paintOfCell(HasDotPaint object, int x,int y) {
				cellRectPaint.rectPaint(x, y, CELL_SIZE);
				if(object != null) {
					object.getPaintScript().dotPaint_capSize(x + CELL_SIZE/2, y + CELL_SIZE/2, (int)(CELL_SIZE*0.8));
					if(formationTS.contains(object)) {
						GHQ.getGraphics2D().setColor(Color.ORANGE);
						GHQ.drawStringGHQ("已编队", x + 10, y + 85, GHQ.getGraphics2D().getFont().deriveFont(Font.BOLD, 15f));
					}
				}
			}
			@Override
			public void clicked() {
				final Knowledge NEW_MEMBER = garrageTS.get(getMouseHoveredID());
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
			}
		});
		garrageScreen.disable();
		/////////////////
		//enemyScreen
		/////////////////
		GHQ.addGUIParts(enemyScreen = new GUIGroup("enemyScreen", new ColorFilling(Color.RED))).addPartsToTop(backToMenuButton);
		enemyScreen.disable();
		/////////////////
		//battleScreen
		/////////////////
		GHQ.addGUIParts(battleScreen = new GUIGroup("enemyScreen", null){
			@Override
			public void idle() {
				super.idle();
				//count lest enemy
				if(GHQ.stage().getUnits_standpoint(new Standpoint(MyUnit.ENEMY), true).size() == 0){ //game clear
					GHQ.getGraphics2D(Color.RED);
					GHQ.drawStringGHQ("EXAM FINISH", 500, 300, GHQ.basicFont.deriveFont(Font.BOLD, 50f));
				}else if(GHQ.stage().getUnits_standpoint(new Standpoint(MyUnit.FRIEND), true).size() == 0) { //game over
					GHQ.getGraphics2D(Color.RED);
					GHQ.drawStringGHQ("FAILED...", 500, 300, GHQ.basicFont.deriveFont(Font.BOLD, 50f));
				}
			}
			@Override
			public void enable() {
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
			}
			@Override
			public void disable() {
				super.disable();
				doStageIdle = false;
				if(GHQ.stage().getUnits_standpoint(new Standpoint(MyUnit.ENEMY), true).size() == 0) { //gameCleared
					++stage;
				}
			}
		}).addPartsToTop(
				new BasicButton("backToMenu", new ImageFrame("image/backButton.png"), 0, 0, 100, 50) {
					@Override
					public void clicked() {
						screenHistory.clear();
						screenHistory.push(menuScreen);
						battleScreen.disable();
						menuScreen.enable();
					}
				});
		battleScreen.disable();
	}

	@Override
	public GHQStage loadStage() {
		return new GHQStage(1000, 1000);
	}

	@Override
	public StageSaveData getStageSaveData() {
		return null;
	}

	private static ImageFrame battleBGIF;
	private static int kejinCount = 0;
	@Override
	public void idle(Graphics2D g2, int stopEventKind) {
		if(doStageIdle) {
			battleBGIF.rectPaint(0, 0, GHQ.getScreenW(), GHQ.getScreenH());
			GHQ.stage().idle();
			GHQ.getGraphics2D(Color.ORANGE);
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

	@Override
	public void resetStage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getEngineGameFrame() {
		// TODO Auto-generated method stub
		return 0;
	}

}
