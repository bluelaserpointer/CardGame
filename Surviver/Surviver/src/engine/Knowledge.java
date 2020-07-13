package engine;

import java.util.List;

import preset.bullet.Bullet;
import core.GHQ;
import core.GHQObject;
import physics.HitGroup;
import paint.dot.DotPaint;
import paint.ImageFrame;
import preset.unit.Unit;
import weapon.Weapon;

public class Knowledge extends MyUnit {
	final KnowledgeParameter PARAM;
	final DotPaint standPaint;
	
	private Knowledge(KnowledgeParameter param, DotPaint additionalStandImage) {
		super(param.NAME, param.PAINT, FRIEND);
		standPaint = additionalStandImage;
		PARAM = param;
	}
	private Knowledge(KnowledgeParameter param) {
		super(param.NAME, param.PAINT, FRIEND);
		standPaint = param.PAINT;
		PARAM = param;
	}
	@Override
	public Knowledge respawn(int x, int y) {
		super.respawn(x, y);
		maxHP = hp = PARAM.iniHP;
		atk = PARAM.iniATK;
		def = PARAM.iniDEF;
		atkRange = PARAM.iniATKRange;
		cd = PARAM.iniCD;
		spd = PARAM.iniSPD;
		return this;
	}
	@Override
	public void idle() {
		super.idle();
		Unit targetUnit = null;
		double distance = GHQ.MAX;
		final boolean IS_DMG = atk > 0;
		for(Unit unit : GHQ.stage().units) {
			if(unit != this && (IS_DMG ? unit instanceof Enemy : unit instanceof Knowledge)) {
				final double DISTANCE = unit.point().distance(this);
				if(DISTANCE < distance) {
					distance = DISTANCE;
					targetUnit = unit;
				}
			}
		}
		if(targetUnit != null) {
			angle().set(point().angleTo(targetUnit));
			if(distance > (int)(atkRange*0.8))
				point().approach(targetUnit, spd);
			if(distance < atkRange) {
				mainWeapon.trigger(this);
			}
		}
	}
	@Override
	public void claimDeleteFromStage() {
		--Engine_Surviver.garrageCount;
		--Engine_Surviver.formationCount;
		Engine_Surviver.formationTS.remove(this);
		Engine_Surviver.garrageTS.remove(this);
		super.claimDeleteFromStage();
	}
	static enum KnowledgeParameter{
		//name , paint , hp , atk , def , range, CD, speed, description
		MAT_N1(Subject.MAT, "九九乘法表", ImageFrame.create("image/tongyongM.png"), 60,60, 10,400,20,3.3,"希望九九八十一次的战斗之后,还能为你效力！","作为进入乘除法世界大门的钥匙,九九乘法表既是你进入小学要面临的几座大山,却也会是陪伴你走过千山万水的可靠的队友。它虽然简单,却是基础中的基础。如果想成就一番事业,九九乘法表就会是你新手期最好的伙伴。"),
		MAT_N2(Subject.MAT, "长*宽*高", ImageFrame.create("image/MAT_N2.png"), 60,4, 10,600,150,3.3,"我一直在寻找,一个立体的世界。","它为你打开几何的大门,踏上数学的康庄大道。或许回首往昔你会觉得它看起来简单至极,但它却始终在这条大道的门口屹立。"),//攻击方式是以攻击目标为中心,边长为两格的正方形区域内持续群体伤害,持续两秒钟,期间每帧造成攻击力大小的真实伤害。
		MAT_N3(Subject.MAT, "0.3333", ImageFrame.create("image/tongyongM.png"), 33,33, 3,333,3,3.3,"0.333333333333.....","无限循环小数！是你最爱的超简单送分选择题。"),
		CHI_N1(Subject.CHI, "鹅！鹅！鹅！", ImageFrame.create("image/CHI_N1.png"), 300,10, 60,100,30,2.2,"鹅！","白毛浮绿水,红掌拨清波。——《咏鹅》骆宾王"),
		CHI_N2(Subject.CHI, "煮豆燃豆萁", ImageFrame.create("image/CHI_N2.png"), 300,20, 50,50,30,4.4,"我希望,不会有与你为敌的那一天。","本是同根生,相煎何太急。——《七步诗》曹植"),
		CHI_N3(Subject.CHI, "粒粒皆辛苦", ImageFrame.create("image/CHI_N3.png"), 300,120,50,50,120,1.1,"活下去的秘诀是,能够懂得珍惜。","谁知盘中餐,粒粒皆辛苦。——《悯农》李绅"),
		ENG_N1(Subject.ENG, "ABCDEFG", ImageFrame.create("image/ENG_N1.png"), 120,-40, 40,200,20,3.3,"我一定是你第一首会唱的外文歌！","ABCEDFG,HIGKLMN——字母歌（？）"),//辅助的攻击力不是攻击敌人的,是给队友回血的。
		ENG_N2(Subject.ENG, "ɑ:  i: u:", ImageFrame.create("image/tongyongE.png"), 80,-60, 20,100,20,9.9,"拒绝哑巴英语,从我做起！","今天课上我们学的音标大家回去复习一下,明天听写。——英语老师"),
		ENG_N3(Subject.ENG, "hello thank you how are you", ImageFrame.create("image/tongyongE.png"), 120,-30, 20,500,30,1.1,"Hello, how are you? I'm fine, thank you.","毕生所学"),
		MAT_R1(Subject.MAT, "解、设、答", ImageFrame.create("image/MAT_R1.png"), 80, 180, 15,500,20,3.3,"相信我,我们以后每天都会见面的。","就算你什么都不会,把它写上去,也有两分格式分"),
		MAT_R2(Subject.MAT, "正切余弦", ImageFrame.create("image/MAT_R2.png"), 70, 290, 10,100,30,7.7,"三角函数可是重点考试内容哦！","当你第一次明白绝大多数的三角函数都是用的弧度制而非角度制的时候,你开始怀疑过去的人生"),
		MAT_R3(Subject.MAT, "等角不等边", ImageFrame.create("image/MAT_R3.png"), 60, 230, 20,300,40,2.2,"...............","别慌！你还可以证相似！"),
		MAT_R4(Subject.MAT, "辅助线", ImageFrame.create("image/tongyongM.png"),75,200,15,400,30,3.3,"你想把我摆在哪里？","一根辅助线,千军万马来相见"),
		MAT_R5(Subject.MAT, "山巅一寺一壶酒", ImageFrame.create("image/tongyongM.png"), 314,159, 26,535,89,7.9,"我是一个无限不循环的数！","3.14159265358979......."),
		CHI_R1(Subject.CHI, "壮志饥餐胡虏肉", ImageFrame.create("image/CHI_R1.png"),700,50, 100,100,30,3.3,"匈奴,在哪儿？","血祭祖灵,颅献京观！——汉族人民传统艺能"),
		CHI_R2(Subject.CHI, "十万旌旗斩阎罗", ImageFrame.create("image/tongyongC.png"), 500,90,120,100,40,4.4,"同志们,你们的元帅回来了！","破邪显正！"),
		CHI_R3(Subject.CHI, "五十弦翻塞外声", ImageFrame.create("image/CHI_R3.png"), 600,80, 80,100,40,2.2,"zzz.........","可惜是在梦里......"),
		CHI_R4(Subject.CHI, "我花开后百花杀", ImageFrame.create("image/CHI_R4.png"), 750,30,90,100,20,3.3,"答应我,一定要去一次长安。","等我farm一年,然后你们都得死！"),
		CHI_R5(Subject.CHI, "人间正道是沧桑", ImageFrame.create("image/tongyongC.png"),1000,70,110,100,30,2.2,"革命尚未成功,同志仍需努力。","坚持建设社会主义初级阶段一个百年不动摇！"),
		ENG_R1(Subject.ENG, "Eddie and hobo", ImageFrame.create("image/tongyongE.png"), 240,-80, 60,300,30,3.3,"is it time for breakfast?","机械狗无法理解活物的食欲。"),
		ENG_R2(Subject.ENG, "trick or treat", ImageFrame.create("image/ENG_R2.png"), 250,-70, 70,200,40,3.3,"不给糖就捣蛋！！","据说,在某些国家,万圣节那天你砸别人家的玻璃不犯法。"),
		ENG_R3(Subject.ENG, "B.I.N.G.O", ImageFrame.create("image/tongyongE.png"), 200,-120, 40,500,20,3.3,"恭喜你,中大奖啦！","jackpot！"),
		ENG_R4(Subject.ENG, "I have a dream", ImageFrame.create("image/tongyongE.png"), 300,-150, 100,50,10,6.6,"This nation will rise up,live up to the true meaning of it's creed,that all man are created equal!","难得的一篇英文演讲稿,即使老师不要求你背诵,你自己还是把全文都背下来了。"),
		ENG_R5(Subject.ENG, "to be or not to be", ImageFrame.create("image/tongyongE.png"), 280,-50, 60,200,40,4.4,"莎士比亚真的这么说过。","有些时候,生存还是毁灭,这是一道送命题。"),
		MAT_SR1(Subject.MAT, "勾股定理", ImageFrame.create("image/tongyongM.png"), 160,420, 10,300,30,3.3,"勾股定理,前来待命！","勾股定理有几十种证明方法,你想了解哪一个？"),//这个单位是造成一个宽度为100,长度为300的长方形范围伤害,可以理解为一个大号的回旋镖横扫过去造成伤害。
		MAT_SR2(Subject.MAT, "鸡兔同笼", ImageFrame.create("image/tongyongM.png"), 350,500, 10,100,15,8.8,"本质上说,我其实是一个通俗易懂的二元一次方程组。","考虑到某些情况下,鸡不一定只有两只脚,其实这个问题的答案并不是唯一的。"),//这个单位选定敌方攻击力最低的一个单位进行单挑,对方只能攻击他,他也只能攻击对方,其他人的所有伤害都无法影响到他们俩。如果对面最低攻击力的单位不止一个,那就把那些单位全部选中,这个角色会同时与对方所有攻击力最低的单位战斗。
		MAT_SR3(Subject.MAT, "倒序相加法", ImageFrame.create("image/MAT_SR3.png"), 120,120, 5,400,60,1.1,"什么？数学王子高斯？他可没那么无聊。","当我们把一条数列首尾相连,胜利的方程式已然全部集齐！3+4,1+6,同调召唤,机巧将军无零！再将两只机巧将军叠放,出来吧,幻兽机哥萨克龙！"),//这个单位最优先攻击敌方场上攻击力最高的单位。
		CHI_SR1(Subject.CHI, "噫吁嚱！", ImageFrame.create("image/tongyongC.png"), 2000,120, 260,100,30,3.3,"我走过千山万水,最长的路,就是民族伟大复兴之路。","世人皆说蜀道难,风枪打通万重山"),//这个单位攻击的时候造成正常击退距离的两倍击退效果。
		CHI_SR2(Subject.CHI, "钿头银篦击节碎", ImageFrame.create("image/CHI_SR2.png"), 1700,340, 190,100,270,5.5,"莫等闲,白了少年头,空悲切","一百块都不给的后果大约就是血色罗裙翻酒污吧......."),//当这个单位击杀敌方单位的时候立刻刷新重置普通攻击的冷却
		CHI_SR3(Subject.CHI, "取之尽锱铢", ImageFrame.create("image/CHI_SR3.png"), 2500,80, 240,300,30,2.2,"我,有的是钱！","奈何用之如泥沙？"),//这个单位受到伤害的时候会将伤害的20%反弹给对手。
		ENG_SR1(Subject.ENG, "no=not any", ImageFrame.create("image/ENG_SR1.png"), 420,-160, 120,300,30,3.3,"一般来说,我,都是适用的。","当你在写作文需要想办法凑字数的时候,可以用到这一招。"),
		ENG_SR2(Subject.ENG, "不及物动词", ImageFrame.create("image/ENG_SR2.png"), 430,-120, 110,100,10,5.5,"你要问我为什么是go home而不是go to home？没有为什么！","很多时候,语言这种东西,是不讲逻辑的。"),
		ENG_SR3(Subject.ENG, "一般现在过去将来完成进行时", ImageFrame.create("image/ENG_SR3.png"), 470,-140, 150,600,90,1.1,"从前,有一个人,他和自己结婚,生下了自己,并杀死了自己。","哈！你们这些回魂尸！"),
		MAT_UR1(Subject.MAT, "奇变偶不变", ImageFrame.create("image/tongyongM.png"), 320,800,20,100,60,3.3,"在？看看三角函数？","奇变偶不变,正负看象限！"),//这个角色只会站在原地不动,以自己为原点向外发射十字架的持续性激光,并让激光光束以十秒转一圈的速度逆时钟旋转。每次激光光束碰到敌人会造成一次伤害。
		MAT_UR2(Subject.MAT, "数形结合", ImageFrame.create("image/tongyongM.png"), 3000,60, 20,100,60,3.3,"数形结合,帮你及格！","很多时候,当你把抽象的数学符号转化为图形的时候,你会发现问题变得简单的不可思议。但有时候,这么做并没有什么特别大的帮助。"),//这个单位会直接秒杀生命值低于其本身当前生命值40%的单位。
		CHI_UR1(Subject.CHI, "欧亨利式结尾", ImageFrame.create("image/tongyongC.png"),500,100, 300,60,2,11,"没想到吧？我在这儿等着你呐！","既在预料之外,又在情理之中。"),//每当这个单位进行了三次攻击后,接下来的一次攻击会造成真实伤害并且冷却间隔（两帧）中会闪避所有攻击。
		CHI_UR2(Subject.CHI, "生动形象", ImageFrame.create("image/tongyongC.png"), 3000,200, 500,100,30,3.3,"我就是一块儿砖,哪里需要往哪儿搬。","只要你做语文的阅读理解,不管什么题目,总是能用得到它的。"),
		ENG_UR1(Subject.ENG, "主观阅读", ImageFrame.create("image/tongyongE.png"), 900,-310, 300,400,30,3.3,"我,并不是直接从文章当中把单词找出来那么简单的。","这个题目,满分十分,高考的人均得分就没超过2.5分过。"),//对范围内的所有队友同时进行治疗
		ENG_UR2(Subject.ENG, "首字母填空", ImageFrame.create("image/tongyongE.png"), 900,-620, 300,600,20,3.3,"我是谁？我就是我,是不一样的烟火！","这个题目,满分十分,因为高考均分实在是太低,06年推出,在08年就被取消了。"),
		MAT_SER(Subject.MAT, "分类讨论", ImageFrame.create("image/MAT_SER.png"), 600,1400, 50,500,60,3.3,"哈！分类讨论！无论是区分正负还是划分空间,统统都不在话下！要想将敌人拆成零件的话,一定要叫上我！","分类讨论,顾名思义,是一名具有将敌人分成两半的高伤害法师。通过发射冰与火力量结合的法术,能够将以她面对方向为中轴线的敌人向两侧击飞,从而达到让敌方输出脱离防御者保护,敌方治疗远离伤残单位的效果。作为在高中阶段常用的一种解题技巧,这种分类的能力是解决许多难题的基础操作。"
				+ "\"分而治之,万军辟易\""),//她的普通攻击是在攻击范围内优先选择最近的单位朝着她面朝方向的左右两边各自移动四格并造成伤害,一次同时攻击两个目标。
		CHI_SER(Subject.CHI, "拒绝平庸", ImageFrame.create("image/CHI_SER.png"), 5000,100, 1000,100,30,3.3,"拒绝平庸,要在这个世界上留下今生今世存在过的证据。","拒绝平庸,是一种生存的态度,有无数人失去了理想,只是在家庭与社会的驱赶下按部就班的活着。这样的一生,有些人在不甘中渐渐归于平凡,而有些人从一开始闲适而又自得。而很多人并没有意识到,人活一辈子的意义,究竟是为了什么。当然,拒绝平庸本就是逆天而行,被岁月磨平了棱角很正常。但他依旧顽固而倔强的存在在这里,饱经风霜而屹立不倒,正如那些企图胜天半子的梦想家一般狂妄。"
				+"作为一个饱经风霜,承担了无数伤害的重装战士,所有无法杀死他的东西,都会使得他变得更强。每当他受到伤害的时候,全属性都会增加。"
				+"\"为天地立心,为生民立命,为往圣继绝学,为万世开太平！\""),//在每次受到伤害的时候,生命值上限增加20,攻击力增加2,攻击速度增加0.05,移动速度增加0.1.
		ENG_SER(Subject.ENG, "九磅十五便士", ImageFrame.create("image/ENG_SER.png"), 2000,500, 500,300,30,6.6,"衬衫的价格为9磅15便士,所以你选择[B]项,并在试卷上将其标出.....咳咳咳,开玩笑的,好了,让我们开始听力吧！","九磅十五便士,是曾经英国衬衫的价格。然而,时过境迁,随着时代的发展与社会的进步,随着中国纺织轻工业的崛起,这一价格渐渐成为了过去大英帝国的一个标签。现在,在网购平台上,你可以很轻松的用低于二十元的价格买到衬衫。语言,也如同国家的崛起一样,在过去,我们趋之若鹜的膜拜那些会英语的人,而现在,中国的教学课本已经被英国政府引入推广。迟早有一天,九磅十五便士的衬衫价格将会被淹没在历史的故纸堆之中,当初振聋发聩的播音,将悄然无声。"
				+"作为一位从上古时代流传至今的辅助角色,九磅十五便士将运用时代的力量,增强我方角色,削弱敌方角色。"
				+"\"王权没有永恒,终归有一天,我将老去,而你,将加冕为王！\"");//在攻击敌方单位的同时为攻击范围内我方受伤最为严重的单位恢复相同的数值,每一次攻击在造成伤害以外还会偷取敌方单位5点攻击力与20点血量上限加到被治疗的我方单位身上。
		
		final String getRarity() {
			final String str = this.toString();
			return str.substring(4, str.length() - 1);
		}
		
		static final KnowledgeParameter[][][] lotteSpring
			= new KnowledgeParameter[Subject.values().length][5][]; //subject, rarity
		private static final void setMember(Subject subject, int rarity, KnowledgeParameter... members)
		{
			lotteSpring[subject.ordinal()][rarity] = members;
		}
		static final Knowledge getLotte(int chi, int mat, int eng) {
			final int SUM = chi + mat + eng;
			final int RAND = GHQ.random2(0, 999);
			int rarity;
			if(SUM <= 5) {
				if(RAND < 900 - 10*SUM)
					rarity = 0;
				else
					rarity = 1;
			}else if(SUM <= 20) {
				if(RAND < 700 - 10*SUM)
					rarity = 0;
				else if(RAND < 900 - 3*SUM)
					rarity = 1;
				else
					rarity = 2;
			}else if(SUM <= 50) {
				if(RAND < 600 - 10*SUM)
					rarity = 0;
				else if(RAND < 800 - 4*SUM)
					rarity = 1;
				else if(RAND < 950 - 1*SUM)
					rarity = 2;
				else
					rarity = 3;
			}else if(SUM <= 100) {
				if(RAND < 700 - 6*SUM)
					rarity = 1;
				else if(RAND < 1500 - 10*SUM)
					rarity = 2;
				else if(RAND < 1150 - 3*SUM)
					rarity = 3;
				else
					rarity = 4;
			}else {
				System.out.println("too much bet bug?");
				rarity = 4;
			}
			final int RAND2 = GHQ.random2(0, SUM - 1);
			int subject;
			if(RAND2 < chi)
				subject = Subject.CHI.ordinal();
			else if(RAND2 < chi + mat)
				subject = Subject.MAT.ordinal();
			else
				subject = Subject.ENG.ordinal();
			final KnowledgeParameter[] group = lotteSpring[subject][rarity];
			Engine_Surviver.lottedRarity = rarity;
			return group[GHQ.random2(0, group.length - 1)].generate();
		}
		static {
			setMember(Subject.CHI, 0, CHI_N1, CHI_N2, CHI_N3);
			setMember(Subject.MAT, 0, MAT_N1, MAT_N2, MAT_N3);
			setMember(Subject.ENG, 0, ENG_N1, ENG_N2, ENG_N3);
			setMember(Subject.CHI, 1, CHI_R1, CHI_R2, CHI_R3, CHI_R4, CHI_R5);
			setMember(Subject.MAT, 1, MAT_R1, MAT_R2, MAT_R3, MAT_R4, MAT_R5);
			setMember(Subject.ENG, 1, ENG_R1, ENG_R2, ENG_R3, ENG_R4, ENG_R5);
			setMember(Subject.CHI, 2, CHI_SR1, CHI_SR2, CHI_SR3);
			setMember(Subject.MAT, 2, MAT_SR1, MAT_SR2, MAT_SR3);
			setMember(Subject.ENG, 2, ENG_SR1, ENG_SR2, ENG_SR3);
			setMember(Subject.CHI, 3, CHI_UR1, CHI_UR2);
			setMember(Subject.MAT, 3, MAT_UR1, MAT_UR2);
			setMember(Subject.ENG, 3, ENG_UR1, ENG_UR2);
			setMember(Subject.CHI, 4, CHI_SER);
			setMember(Subject.MAT, 4, MAT_SER);
			setMember(Subject.ENG, 4, ENG_SER);
		}
		
		final Subject SUBJECT;
		final String NAME;
		final DotPaint PAINT;
		final int iniHP, iniATK, iniDEF, iniCD;
		final double iniATKRange, iniSPD;
		final String appendTalk, description;
		
		private KnowledgeParameter(Subject subject, String name, DotPaint paint, int hp, int atk, int def, double atkRange, int cd, double spd, String appendTalk, String description) {
			SUBJECT = subject;
			NAME = name;
			PAINT = paint;
			iniHP = hp;
			iniATK = atk;
			iniDEF = def;
			iniATKRange = atkRange;
			iniCD = cd;
			iniSPD = spd;
			this.appendTalk = appendTalk;
			this.description = description;
		}
		private static final ImageFrame 
			MAT_N2_IF = ImageFrame.create("image/GreenSquare.png"),
			MAT_SER_IF1 = ImageFrame.create("image/Fenlei1.png"),
			MAT_SER_IF2 = ImageFrame.create("image/Fenlei2.png");
		Knowledge generate() {
			final Knowledge UNIT;
			if(this == MAT_N2) { //长*宽*高
				UNIT = new Knowledge(this);
				UNIT.mainWeapon = new Weapon() {
					{
						this.setCoolTime(0);
						this.setMagazineSize(60);
						this.setReloadSpeed(UNIT.cd);
					}
					@Override
					public List<Bullet> setBullets(GHQObject shooter, HitGroup standpoint) {
						MAT_N2_IF.dotPaint_capSize(shooter.point(), 200);
						for(Unit unit : GHQ.stage().units) {
							if(unit instanceof Enemy)
								((MyUnit)unit).damage_amount(UNIT.atk);
						}
						return null;
					}
				};
			}else if(this == MAT_SER) { //分类讨论
				UNIT = new Knowledge(this);
				UNIT.mainWeapon = new Weapon() {
					{
						this.setCoolTime(UNIT.cd);
					}
					@Override
					public List<Bullet> setBullets(GHQObject shooter, HitGroup standpoint) {
						MAT_N2_IF.dotPaint_capSize(shooter.point(), 200);
						double distance1 = GHQ.MAX, distance2 = GHQ.MAX;
						MyUnit assume1 = null, assume2 = null;
						for(Unit unit : GHQ.stage().units) {
							if(unit instanceof Enemy) {
								final double DISTANCE = UNIT.point().distance(unit);
								if(DISTANCE > MAT_SER.iniATKRange)
									continue;
								if(assume1 == null || distance1 > DISTANCE) {
									assume2 = assume1;
									distance2 = distance1;
									assume1 = (MyUnit)unit;
									distance1 = DISTANCE;
								}else if(assume2 == null || distance2 > DISTANCE) {
									assume2 = (MyUnit)unit;
									distance2 = DISTANCE;
								}
							}
						}
						if(assume1 != null) {
							if(assume2 != null) {
								MAT_SER_IF1.dotPaint(assume1.point());
								MAT_SER_IF2.dotPaint(assume2.point());
								assume1.damage_amount(UNIT.atk);
								assume2.damage_amount(UNIT.atk);
							}else {
								MAT_SER_IF1.dotPaint(assume1.point());
								MAT_SER_IF2.dotPaint(assume1.point());
								assume1.damage_amount(UNIT.atk*3);
							}
						}
						return null;
					}
				};
			}else if(this == CHI_SER) {
				UNIT = new Knowledge(this){
					@Override
					public int damage_amount(int dmg) {
						final int REAL_DMG = super.damage_amount(dmg);
						//生命值上限增加20,攻击力增加2,移动速度增加0.1
						maxHP += 20; atk += 2; spd += 0.1;
						return REAL_DMG;
					}
				};
			}else if(this == ENG_SER) {
				//在攻击敌方单位的同时为攻击范围内我方受伤最为严重的单位恢复相同的数值, 每一次攻击在造成伤害以外还会偷取敌方单位5点攻击力与20点血量上限加到被治疗的我方单位身上。
				UNIT = new Knowledge(this);
				UNIT.mainWeapon = new Weapon() {
					{
						this.setCoolTime(UNIT.cd);
					}
					@Override
					public List<Bullet> setBullets(GHQObject shooter, HitGroup standpoint) {
						final MyUnit TARGET = (MyUnit)GHQ.stage().getNearstEnemy(UNIT);
						if(TARGET != null && UNIT.point().distance(TARGET) < UNIT.atkRange) {
							final int DMG = TARGET.damage_amount(UNIT.atk);
							final int ATK_DRAIN = Math.min(TARGET.atk, 5);
							TARGET.atk -= ATK_DRAIN;
							final int HP_DRAIN = TARGET.drain_maxHP(UNIT.atk);
							if(DMG > 0) {
								final MyUnit HEAL_TARGET = (MyUnit)GHQ.stage().getNearstEnemy(new HitGroup(ENEMY), UNIT.point());
								if(HEAL_TARGET != null && UNIT.point().distance(HEAL_TARGET) < UNIT.atkRange) {
									HEAL_TARGET.atk += ATK_DRAIN;
									HEAL_TARGET.maxHP += HP_DRAIN;
									HEAL_TARGET.damage_amount(-DMG);
								}
							}
						}
						return null;
					}
				};
			}else
				UNIT = new Knowledge(this);
			return UNIT;
		}
	}
}
