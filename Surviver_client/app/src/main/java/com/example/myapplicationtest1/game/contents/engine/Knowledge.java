package com.example.myapplicationtest1.game.contents.engine;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.core.GHQObject;
import com.example.myapplicationtest1.game.paint.ImageFrame;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;
import com.example.myapplicationtest1.game.physics.HitGroup;
import com.example.myapplicationtest1.game.preset.bullet.Bullet;
import com.example.myapplicationtest1.game.preset.unit.Unit;
import com.example.myapplicationtest1.game.weapon.Weapon;

import java.util.List;

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
		double distance = Integer.MAX_VALUE;
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
		MAT_N1(Subject.MAT, "�žų˷���", ImageFrame.create("image/tongyongM.png"), 60,60, 10,400,20,3.3,"ϣ���žŰ�ʮһ�ε�ս��֮��,����Ϊ��Ч����","��Ϊ����˳���������ŵ�Կ��,�žų˷�����������СѧҪ���ٵļ�����ɽ,ȴҲ����������߹�ǧɽ��ˮ�Ŀɿ��Ķ��ѡ�����Ȼ��,ȴ�ǻ����еĻ����������ɾ�һ����ҵ,�žų˷���ͻ�������������õĻ�顣"),
		MAT_N2(Subject.MAT, "��*��*��", ImageFrame.create("image/MAT_N2.png"), 60,4, 10,600,150,3.3,"��һֱ��Ѱ��,һ����������硣","��Ϊ��򿪼��εĴ���,̤����ѧ�Ŀ�ׯ������������������������������������,����ȴʼ��������������ſ�������"),//������ʽ���Թ���Ŀ��Ϊ����,�߳�Ϊ����������������ڳ���Ⱥ���˺�,����������,�ڼ�ÿ֡��ɹ�������С����ʵ�˺���
		MAT_N3(Subject.MAT, "0.3333", ImageFrame.create("image/tongyongM.png"), 33,33, 3,333,3,3.3,"0.333333333333.....","����ѭ��С����������ĳ����ͷ�ѡ���⡣"),
		CHI_N1(Subject.CHI, "�죡�죡�죡", ImageFrame.create("image/CHI_N1.png"), 300,10, 60,100,30,2.2,"�죡","��ë����ˮ,���Ʋ��岨��������ӽ�졷�����"),
		CHI_N2(Subject.CHI, "��ȼ��ݽ", ImageFrame.create("image/CHI_N2.png"), 300,20, 50,50,30,4.4,"��ϣ��,����������Ϊ�е���һ�졣","����ͬ����,����̫�����������߲�ʫ����ֲ"),
		CHI_N3(Subject.CHI, "����������", ImageFrame.create("image/CHI_N3.png"), 300,120,50,50,120,1.1,"����ȥ���ؾ���,�ܹ�������ϧ��","˭֪���в�,���������ࡣ��������ũ������"),
		ENG_N1(Subject.ENG, "ABCDEFG", ImageFrame.create("image/ENG_N1.png"), 120,-40, 40,200,20,3.3,"��һ�������һ�׻ᳪ�����ĸ裡","ABCEDFG,HIGKLMN������ĸ�裨����"),//�����Ĺ��������ǹ������˵�,�Ǹ����ѻ�Ѫ�ġ�
		ENG_N2(Subject.ENG, "��:  i: u:", ImageFrame.create("image/tongyongE.png"), 80,-60, 20,100,20,9.9,"�ܾ��ư�Ӣ��,��������","�����������ѧ�������һ�ȥ��ϰһ��,������д������Ӣ����ʦ"),
		ENG_N3(Subject.ENG, "hello thank you how are you", ImageFrame.create("image/tongyongE.png"), 120,-30, 20,500,30,1.1,"Hello, how are you? I'm fine, thank you.","������ѧ"),
		MAT_R1(Subject.MAT, "�⡢�衢��", ImageFrame.create("image/MAT_R1.png"), 80, 180, 15,500,20,3.3,"������,�����Ժ�ÿ�춼�����ġ�","������ʲô������,����д��ȥ,Ҳ�����ָ�ʽ��"),
		MAT_R2(Subject.MAT, "��������", ImageFrame.create("image/MAT_R2.png"), 70, 290, 10,100,30,7.7,"���Ǻ��������ص㿼������Ŷ��","�����һ�����׾�����������Ǻ��������õĻ����ƶ��ǽǶ��Ƶ�ʱ��,�㿪ʼ���ɹ�ȥ������"),
		MAT_R3(Subject.MAT, "�Ƚǲ��ȱ�", ImageFrame.create("image/MAT_R3.png"), 60, 230, 20,300,40,2.2,"...............","��ţ��㻹����֤���ƣ�"),
		MAT_R4(Subject.MAT, "������", ImageFrame.create("image/tongyongM.png"),75,200,15,400,30,3.3,"������Ұ������","һ��������,ǧ�����������"),
		MAT_R5(Subject.MAT, "ɽ��һ��һ����", ImageFrame.create("image/tongyongM.png"), 314,159, 26,535,89,7.9,"����һ�����޲�ѭ��������","3.14159265358979......."),
		CHI_R1(Subject.CHI, "׳־���ͺ�²��", ImageFrame.create("image/CHI_R1.png"),700,50, 100,100,30,3.3,"��ū,���Ķ���","Ѫ������,­�׾��ۣ�������������ͳ����"),
		CHI_R2(Subject.CHI, "ʮ�����ն����", ImageFrame.create("image/tongyongC.png"), 500,90,120,100,40,4.4,"ͬ־��,���ǵ�Ԫ˧�����ˣ�","��а������"),
		CHI_R3(Subject.CHI, "��ʮ�ҷ�������", ImageFrame.create("image/CHI_R3.png"), 600,80, 80,100,40,2.2,"zzz.........","��ϧ��������......"),
		CHI_R4(Subject.CHI, "�һ�����ٻ�ɱ", ImageFrame.create("image/CHI_R4.png"), 750,30,90,100,20,3.3,"��Ӧ��,һ��Ҫȥһ�γ�����","����farmһ��,Ȼ�����Ƕ�������"),
		CHI_R5(Subject.CHI, "�˼������ǲ�ɣ", ImageFrame.create("image/tongyongC.png"),1000,70,110,100,30,2.2,"������δ�ɹ�,ͬ־����Ŭ����","��ֽ��������������׶�һ�����겻��ҡ��"),
		ENG_R1(Subject.ENG, "Eddie and hobo", ImageFrame.create("image/tongyongE.png"), 240,-80, 60,300,30,3.3,"is it time for breakfast?","��е���޷��������ʳ����"),
		ENG_R2(Subject.ENG, "trick or treat", ImageFrame.create("image/ENG_R2.png"), 250,-70, 70,200,40,3.3,"�����Ǿ͵�������","��˵,��ĳЩ����,��ʥ���������ұ��˼ҵĲ�����������"),
		ENG_R3(Subject.ENG, "B.I.N.G.O", ImageFrame.create("image/tongyongE.png"), 200,-120, 40,500,20,3.3,"��ϲ��,�д�����","jackpot��"),
		ENG_R4(Subject.ENG, "I have a dream", ImageFrame.create("image/tongyongE.png"), 300,-150, 100,50,10,6.6,"This nation will rise up,live up to the true meaning of it's creed,that all man are created equal!","�ѵõ�һƪӢ���ݽ���,��ʹ��ʦ��Ҫ���㱳��,���Լ����ǰ�ȫ�Ķ��������ˡ�"),
		ENG_R5(Subject.ENG, "to be or not to be", ImageFrame.create("image/tongyongE.png"), 280,-50, 60,200,40,4.4,"ɯʿ���������ô˵����","��Щʱ��,���滹�ǻ���,����һ�������⡣"),
		MAT_SR1(Subject.MAT, "���ɶ���", ImageFrame.create("image/tongyongM.png"), 160,420, 10,300,30,3.3,"���ɶ���,ǰ��������","���ɶ����м�ʮ��֤������,�����˽���һ����"),//�����λ�����һ�����Ϊ100,����Ϊ300�ĳ����η�Χ�˺�,�������Ϊһ����ŵĻ����ں�ɨ��ȥ����˺���
		MAT_SR2(Subject.MAT, "����ͬ��", ImageFrame.create("image/tongyongM.png"), 350,500, 10,100,15,8.8,"������˵,����ʵ��һ��ͨ���׶��Ķ�Ԫһ�η����顣","���ǵ�ĳЩ�����,����һ��ֻ����ֻ��,��ʵ�������Ĵ𰸲�����Ψһ�ġ�"),//�����λѡ���з���������͵�һ����λ���е���,�Է�ֻ�ܹ�����,��Ҳֻ�ܹ����Է�,�����˵������˺����޷�Ӱ�쵽�����������������͹������ĵ�λ��ֹһ��,�ǾͰ���Щ��λȫ��ѡ��,�����ɫ��ͬʱ��Է����й�������͵ĵ�λս����
		MAT_SR3(Subject.MAT, "������ӷ�", ImageFrame.create("image/MAT_SR3.png"), 120,120, 5,400,60,1.1,"ʲô����ѧ���Ӹ�˹������û��ô���ġ�","�����ǰ�һ��������β����,ʤ���ķ���ʽ��Ȼȫ�����룡3+4,1+6,ͬ���ٻ�,���ɽ������㣡�ٽ���ֻ���ɽ�������,������,���޻�����������"),//�����λ�����ȹ����з����Ϲ�������ߵĵ�λ��
		CHI_SR1(Subject.CHI, "��������", ImageFrame.create("image/tongyongC.png"), 2000,120, 260,100,30,3.3,"���߹�ǧɽ��ˮ,���·,��������ΰ����֮·��","���˽�˵�����,��ǹ��ͨ����ɽ"),//�����λ������ʱ������������˾������������Ч����
		CHI_SR2(Subject.CHI, "��ͷ����������", ImageFrame.create("image/CHI_SR2.png"), 1700,340, 190,100,270,5.5,"Ī����,��������ͷ,�ձ���","һ�ٿ鶼�����ĺ����Լ����Ѫɫ��ȹ�����۰�......."),//�������λ��ɱ�з���λ��ʱ������ˢ��������ͨ��������ȴ
		CHI_SR3(Subject.CHI, "ȡ֮������", ImageFrame.create("image/CHI_SR3.png"), 2500,80, 240,300,30,2.2,"��,�е���Ǯ��","�κ���֮����ɳ��"),//�����λ�ܵ��˺���ʱ��Ὣ�˺���20%���������֡�
		ENG_SR1(Subject.ENG, "no=not any", ImageFrame.create("image/ENG_SR1.png"), 420,-160, 120,300,30,3.3,"һ����˵,��,�������õġ�","������д������Ҫ��취��������ʱ��,�����õ���һ�С�"),
		ENG_SR2(Subject.ENG, "�����ﶯ��", ImageFrame.create("image/ENG_SR2.png"), 430,-120, 110,100,10,5.5,"��Ҫ����Ϊʲô��go home������go to home��û��Ϊʲô��","�ܶ�ʱ��,�������ֶ���,�ǲ����߼��ġ�"),
		ENG_SR3(Subject.ENG, "һ�����ڹ�ȥ������ɽ���ʱ", ImageFrame.create("image/ENG_SR3.png"), 470,-140, 150,600,90,1.1,"��ǰ,��һ����,�����Լ����,�������Լ�,��ɱ�����Լ���","����������Щ�ػ�ʬ��"),
		MAT_UR1(Subject.MAT, "���ż����", ImageFrame.create("image/tongyongM.png"), 320,800,20,100,60,3.3,"�ڣ��������Ǻ�����","���ż����,���������ޣ�"),//�����ɫֻ��վ��ԭ�ز���,���Լ�Ϊԭ�����ⷢ��ʮ�ּܵĳ����Լ���,���ü��������ʮ��תһȦ���ٶ���ʱ����ת��ÿ�μ�������������˻����һ���˺���
		MAT_UR2(Subject.MAT, "���ν��", ImageFrame.create("image/tongyongM.png"), 3000,60, 20,100,60,3.3,"���ν��,���㼰��","�ܶ�ʱ��,����ѳ������ѧ����ת��Ϊͼ�ε�ʱ��,��ᷢ�������ü򵥵Ĳ���˼�顣����ʱ��,��ô����û��ʲô�ر��İ�����"),//�����λ��ֱ����ɱ����ֵ�����䱾��ǰ����ֵ40%�ĵ�λ��
		CHI_UR1(Subject.CHI, "ŷ����ʽ��β", ImageFrame.create("image/tongyongC.png"),500,100, 300,60,2,11,"û�뵽�ɣ���������������ţ�","����Ԥ��֮��,��������֮�С�"),//ÿ�������λ���������ι�����,��������һ�ι����������ʵ�˺�������ȴ�������֡���л��������й�����
		CHI_UR2(Subject.CHI, "��������", ImageFrame.create("image/tongyongC.png"), 3000,200, 500,100,30,3.3,"�Ҿ���һ���ש,������Ҫ���Ķ��ᡣ","ֻҪ�������ĵ��Ķ����,����ʲô��Ŀ,�������õõ����ġ�"),
		ENG_UR1(Subject.ENG, "�����Ķ�", ImageFrame.create("image/tongyongE.png"), 900,-310, 300,400,30,3.3,"��,������ֱ�Ӵ����µ��аѵ����ҳ�����ô�򵥵ġ�","�����Ŀ,����ʮ��,�߿����˾��÷־�û����2.5�ֹ���"),//�Է�Χ�ڵ����ж���ͬʱ��������
		ENG_UR2(Subject.ENG, "����ĸ���", ImageFrame.create("image/tongyongE.png"), 900,-620, 300,600,20,3.3,"����˭���Ҿ�����,�ǲ�һ�����̻�","�����Ŀ,����ʮ��,��Ϊ�߿�����ʵ����̫��,06���Ƴ�,��08��ͱ�ȡ���ˡ�"),
		MAT_SER(Subject.MAT, "��������", ImageFrame.create("image/MAT_SER.png"), 600,1400, 50,500,60,3.3,"�����������ۣ������������������ǻ��ֿռ�,ͳͳ�����ڻ��£�Ҫ�뽫���˲������Ļ�,һ��Ҫ�����ң�","��������,����˼��,��һ�����н����˷ֳ�����ĸ��˺���ʦ��ͨ����������������ϵķ���,�ܹ���������Է���Ϊ�����ߵĵ������������,�Ӷ��ﵽ�õз������������߱���,�з�����Զ���˲е�λ��Ч������Ϊ�ڸ��н׶γ��õ�һ�ֽ��⼼��,���ַ���������ǽ���������Ļ���������"
				+ "\"�ֶ���֮,�������\""),//������ͨ�������ڹ�����Χ������ѡ������ĵ�λ�������泯������������߸����ƶ��ĸ�����˺�,һ��ͬʱ��������Ŀ�ꡣ
		CHI_SER(Subject.CHI, "�ܾ�ƽӹ", ImageFrame.create("image/CHI_SER.png"), 5000,100, 1000,100,30,3.3,"�ܾ�ƽӹ,Ҫ��������������½����������ڹ���֤�ݡ�","�ܾ�ƽӹ,��һ�������̬��,��������ʧȥ������,ֻ���ڼ�ͥ�����������°����Ͱ�Ļ��š�������һ��,��Щ���ڲ����н�������ƽ��,����Щ�˴�һ��ʼ���ʶ����Եá����ܶ��˲�û����ʶ��,�˻�һ���ӵ�����,������Ϊ��ʲô����Ȼ,�ܾ�ƽӹ�������������,������ĥƽ����Ǻ�����������������̶���ǿ�Ĵ���������,������˪����������,������Щ��ͼʤ����ӵ������һ�������"
				+"��Ϊһ��������˪,�е��������˺�����װսʿ,�����޷�ɱ�����Ķ���,����ʹ������ø�ǿ��ÿ�����ܵ��˺���ʱ��,ȫ���Զ������ӡ�"
				+"\"Ϊ�������,Ϊ��������,Ϊ��ʥ�̾�ѧ,Ϊ������̫ƽ��\""),//��ÿ���ܵ��˺���ʱ��,����ֵ��������20,����������2,�����ٶ�����0.05,�ƶ��ٶ�����0.1.
		ENG_SER(Subject.ENG, "�Ű�ʮ���ʿ", ImageFrame.create("image/ENG_SER.png"), 2000,500, 500,300,30,6.6,"�����ļ۸�Ϊ9��15��ʿ,������ѡ��[B]��,�����Ծ��Ͻ�����.....�ȿȿ�,����Ц��,����,�����ǿ�ʼ�����ɣ�","�Ű�ʮ���ʿ,������Ӣ�������ļ۸�Ȼ��,ʱ����Ǩ,����ʱ���ķ�չ�����Ľ���,�����й���֯�Ṥҵ������,��һ�۸񽥽���Ϊ�˹�ȥ��Ӣ�۹���һ����ǩ������,������ƽ̨��,����Ժ����ɵ��õ��ڶ�ʮԪ�ļ۸��򵽳���������,Ҳ��ͬ���ҵ�����һ��,�ڹ�ȥ,������֮���͵�Ĥ����Щ��Ӣ�����,������,�й��Ľ�ѧ�α��Ѿ���Ӣ�����������ƹ㡣������һ��,�Ű�ʮ���ʿ�ĳ����۸񽫻ᱻ��û����ʷ�Ĺ�ֽ��֮��,�������������Ĳ���,����Ȼ������"
				+"��Ϊһλ���Ϲ�ʱ����������ĸ�����ɫ,�Ű�ʮ���ʿ������ʱ��������,��ǿ�ҷ���ɫ,�����з���ɫ��"
				+"\"��Ȩû������,�չ���һ��,�ҽ���ȥ,����,������Ϊ����\"");//�ڹ����з���λ��ͬʱΪ������Χ���ҷ�������Ϊ���صĵ�λ�ָ���ͬ����ֵ,ÿһ�ι���������˺����⻹��͵ȡ�з���λ5�㹥������20��Ѫ�����޼ӵ������Ƶ��ҷ���λ���ϡ�
		
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
			if(this == MAT_N2) { //��*��*��
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
			}else if(this == MAT_SER) { //��������
				UNIT = new Knowledge(this);
				UNIT.mainWeapon = new Weapon() {
					{
						this.setCoolTime(UNIT.cd);
					}
					@Override
					public List<Bullet> setBullets(GHQObject shooter, HitGroup standpoint) {
						MAT_N2_IF.dotPaint_capSize(shooter.point(), 200);
						double distance1 = Integer.MAX_VALUE, distance2 = Integer.MAX_VALUE;
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
						//����ֵ��������20,����������2,�ƶ��ٶ�����0.1
						maxHP += 20; atk += 2; spd += 0.1;
						return REAL_DMG;
					}
				};
			}else if(this == ENG_SER) {
				//�ڹ����з���λ��ͬʱΪ������Χ���ҷ�������Ϊ���صĵ�λ�ָ���ͬ����ֵ, ÿһ�ι���������˺����⻹��͵ȡ�з���λ5�㹥������20��Ѫ�����޼ӵ������Ƶ��ҷ���λ���ϡ�
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
