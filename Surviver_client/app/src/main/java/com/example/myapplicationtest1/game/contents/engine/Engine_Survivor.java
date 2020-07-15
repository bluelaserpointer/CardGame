package com.example.myapplicationtest1.game.contents.engine;

import android.graphics.Color;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.core.Game;
import com.example.myapplicationtest1.game.paint.ImageFrame;
import com.example.myapplicationtest1.game.physics.stage.GHQStage;
import com.example.myapplicationtest1.game.preset.unit.Unit;
import com.example.myapplicationtest1.game.storage.TableStorage;

import java.util.Arrays;

public class Engine_Survivor extends Game {

	//lotteScreen
	public static int[] lotteAdded = new int[3];
	private static Knowledge lottedKnowledge;
	public static int lottedRarity;
	private static int lotteAnimeStartFrame = GHQ.NONE;
	//formationScreen
	static TableStorage<Knowledge> formationTS = new TableStorage<>(4, 4, null);
	static int formationCount;
	//garrageScreen
	static TableStorage<Knowledge> garrageTS = new TableStorage<>(8, 5, null);
	static int garrageCount;
	//battleScreen
	private static boolean doStageIdle;
	
	//stage
	static int stage = 0;
	
	//resourceAmount
	public static int[] resourceAmount = new int[3];
	
	@Override
	public String getVersion() {
		return "alpha1.0";
	}
	
	@Override
	public String getTitleName() {
		return "Surviver_" + getVersion();
	}
	public static void main(String[] args) {
		new GHQ(new Engine_Survivor());
	}

	public static int getTotalBet() {
		return lotteAdded[0] + lotteAdded[1] + lotteAdded[2];
	}
	//enemy
	private static final Enemy.EnemyParameter[][][] enemyFormation = new Enemy.EnemyParameter[10][4][4];
	@Override
	public void loadResource() {
		//input;
		battleBGIF = ImageFrame.create(R.drawable.battlebg);
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

		//introduction
		new Object() {
			int step = 0;
			private final ImageFrame[] introIFs = new ImageFrame[] {
					ImageFrame.create(R.drawable.guide0),
					ImageFrame.create(R.drawable.guide1),
					ImageFrame.create(R.drawable.guide2),
					ImageFrame.create(R.drawable.guide3),
					ImageFrame.create(R.drawable.guide4),
					ImageFrame.create(R.drawable.guide5),
					ImageFrame.create(R.drawable.guide6),
					ImageFrame.create(R.drawable.guide7),
			};
			public void idle() {
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
		};
		new Object() {
			private final int ANM_SIZE = 89;
			private final ImageFrame[] anime = new ImageFrame[ANM_SIZE];

			{
//				for (int i = 0; i < ANM_SIZE; ++i)
//					anime[i] = ImageFrame.create("image/lotte/" + (i + 1) + ".png");
			}

			private final ImageFrame[] cardsBack = {
					ImageFrame.create(R.drawable.card0),
					ImageFrame.create(R.drawable.card1),
					ImageFrame.create(R.drawable.card2),
					ImageFrame.create(R.drawable.card3),
					ImageFrame.create(R.drawable.card4),
			};
			private final ImageFrame CardBGIF = ImageFrame.create(R.drawable.cardbg);

			public void idle() {
				//lotteAnimation
				if (lotteAnimeStartFrame != GHQ.NONE) {
					int frame = GHQ.passedFrame(lotteAnimeStartFrame);
					int span;
					Animate:
					{
						if (frame - ANM_SIZE < 0) {
							anime[frame].dotPaint(GHQ.screenW() / 2, GHQ.screenH() / 2);
							break Animate;
						}
						frame -= ANM_SIZE;
						span = 10;
						final ImageFrame IF = cardsBack[lottedRarity];
						if (frame - span < 0) {
							IF.dotPaint_turn(GHQ.screenW() / 2, GHQ.screenH() / 2, (double) frame / span * 2 * Math.PI);
							break Animate;
						}
						frame -= span;
						span = 15;
						if (frame - span < 0) {
							final int W = (int) (cardsBack[lottedRarity].width() * Math.cos((double) frame / span * Math.PI / 2));
							final int H = cardsBack[lottedRarity].height();
							IF.rectPaint(GHQ.screenW() / 2 - W / 2, GHQ.screenH() / 2 - H / 2, W, H);
							break Animate;
						}
						frame -= span;
						span = 15;
						if (frame - span < 0) {
							final double COS = Math.cos(((double) frame / span + 1) * Math.PI / 2);
							final int W = -(int) (cardsBack[lottedRarity].width() * COS);
							final int H = cardsBack[lottedRarity].height();
							CardBGIF.rectPaint(GHQ.screenW() / 2 - W / 2, GHQ.screenH() / 2 - H / 2, W, H);
							final int W2 = -(int) (lottedKnowledge.standPaint.width() * COS) * 2;
							final int H2 = lottedKnowledge.standPaint.height() * 2;
							lottedKnowledge.standPaint.rectPaint(GHQ.screenW() / 2 - W2 / 2, GHQ.screenH() / 2 - H2 / 2, W2, H2);
							break Animate;
						}
						frame -= span;
						{
							CardBGIF.dotPaint(GHQ.screenW() / 2, GHQ.screenH() / 2);
							lottedKnowledge.standPaint.dotPaint_rate(GHQ.screenW() / 2, GHQ.screenH() / 2, 2.0);
							//GHQ.drawStringGHQ("��" + lottedKnowledge.NAME + "��", 270, 70, 20F);
							//GHQ.drawStringGHQ(lottedKnowledge.PARAM.appendTalk, 270, 400, Font.BOLD, 20F);
						}
					}
				}
			}
			public void clicked() {
				if (lottedKnowledge != null) {
					lottedKnowledge = null;
					lotteAnimeStartFrame = GHQ.NONE;
				}
			}
			public void clicked2() {
				for (int i = 0; i < lotteAdded.length; i++) {
					resourceAmount[i] += lotteAdded[i];
					lotteAdded[i] = 0;
				}
				lottedKnowledge = null;
				lotteAnimeStartFrame = GHQ.NONE;
			}
			public void setAnimation() {
				lotteAnimeStartFrame = GHQ.nowFrame();
				++garrageCount;
				Arrays.fill(lotteAdded, 0);
			}
		};
		/////////////////
		//battleScreen
		/////////////////
		new Object() {
			boolean isGameClear;
			boolean isGameOver;
			public void idle() {
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
					GHQ.drawStringGHQ("EXAM FINISH", 500, 300, GHQ.generatePaint(Color.RED));
				} else if(isGameOver) {
					GHQ.drawStringGHQ("FAILED...", 500, 300, GHQ.generatePaint(Color.RED));
				}
			}
			public void enable() {
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
			public void disable() {
				doStageIdle = false;
				if(isGameClear)
					++stage;
			}
		};
	}

	@Override
	public GHQStage loadStage() {
		return new GHQStage(1000, 1000);
	}

	private static ImageFrame battleBGIF;
	@Override
	public void idle(int stopEventKind) {
		if(doStageIdle) {
			battleBGIF.rectPaint(0, 0, GHQ.screenW(), GHQ.screenH());
		}else {
		}
	}
}
