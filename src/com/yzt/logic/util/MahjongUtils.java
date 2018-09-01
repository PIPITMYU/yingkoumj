package com.yzt.logic.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yzt.logic.mj.domain.Action;
import com.yzt.logic.mj.domain.Player;
import com.yzt.logic.mj.domain.RoomResp;
import com.yzt.logic.util.JudegHu.checkHu.Hulib;
import com.yzt.logic.util.JudegHu.checkHu.TableMgr;
import com.yzt.logic.util.redis.RedisUtil;

/**
 * 
 * @author wsw_007
 *
 */
public class MahjongUtils {
	public static final Log logger = LogFactory.getLog(MahjongUtils.class);

	static {
		// 加载胡的可能
		TableMgr.getInstance().load();
	}

	public static void main(String[] args) {
		// 设置玩家手牌信息
		// List<Integer> currentMjList = new ArrayList<Integer>();
		// int x=0;
		// System.out.println(Integer.valueOf(x)!=null);
		// currentMjList.add(1);
		// List<Integer> faPai = faPai(currentMjList, 1);
		// System.out.println(faPai);
		// boolean a=false;
		// boolean b=true;
		// boolean c=false;
		// boolean d=false;
		// if(a || b || c || d){
		// System.out.println("false");
		// }
//		Random r = new Random();
//
//		int nextInt = r.nextInt(7);
//		System.out.println(nextInt);
		// List<Integer> list = new ArrayList<Integer>();
		// list.add(1);
		// list.add(2);
		// if (list.contains(2)) {
		// System.out.println("-");
		// }
		// // currentMjList.add(2);
		// // currentMjList.add(3);
		// Integer huLeiXingScore=0;
		// List<Integer> huInfoList = new ArrayList<Integer>(); // 获取玩家的胡的类型
		// huInfoList.add(Cnst.QIDUI);
		// huInfoList.add(Cnst.TUIDAOHU);
		// huInfoList.add(Cnst.QINGYISE);
		// for (Integer integer : huInfoList) {
		// if(huLeiXingScore==0){
		// huLeiXingScore=Cnst.huScoreMap.get(integer);
		// }else{
		// huLeiXingScore*=Cnst.huScoreMap.get(integer);
		// }
		// }
		// System.out.println(huLeiXingScore);
		// Integer x=null;
		// System.out.println(x.equals(3));
		// currentMjList.add(4);
		// currentMjList.add(6);
		// currentMjList.add(7);
		// currentMjList.add(8);
		// currentMjList.add(9);
		// currentMjList.add(10);
		// currentMjList.add(10);
		// currentMjList.add(11);
		// currentMjList.add(12);
		// List<Integer> chi =
		// MahjongUtils.getPaiListFromAction(42,Cnst.PLAYER_ACTION_CHI);
		// Integer lastChuPai = 10;
		// MahjongUtils.removePai(chi, lastChuPai,1);
		// // 移除手牌中需要配合组成动作的牌
		// MahjongUtils.removeList(currentMjList, chi);
		// System.out.println(currentMjList);
		// currentMjList.add(6);
		// currentMjList.add(12);
		// currentMjList.add(13);
		// currentMjList.add(15);
		// currentMjList.add(16);
		// currentMjList.add(30);
		// currentMjList.add(30);
		// currentMjList.add(31);
		// currentMjList.add(32);
		// currentMjList.add(33);
		// List<Long> nowPlayerIds=new ArrayList<Long>();
		// List<Player> actionPlayers=new ArrayList<Player>();
		//
		// Player p1=new Player();
		// p1.setUserId(111l);
		// p1.setPosition(1);
		// Player p2=new Player();
		// p2.setUserId(112l);
		// p2.setPosition(2);
		// Player p3=new Player();
		// p3.setUserId(113l);
		// p3.setPosition(3);
		// Player p4=new Player();
		// p4.setUserId(114l);
		// p4.setPosition(4);
		// actionPlayers.add(p1);
		// actionPlayers.add(p2);
		// // actionPlayers.add(p3);
		// // actionPlayers.add(p4);
		// Integer position=3;
		// getNowPositionByOrder(nowPlayerIds, actionPlayers, position);
		// for (Long x : nowPlayerIds) {
		// System.out.println(x);
		// }
		// System.out.println(getKaiPaiFromHunPai(34));

		// List<Integer> c = new ArrayList<Integer>();
		// c.add(1);
		// c.add(30);
		// removeList(currentMjList, c);
		// for (Integer integer : currentMjList) {
		// System.out.println(integer);
		// }
		// 设置玩家信息
		// Player player = new Player();
		// player.setCurrentMjList(currentMjList);
		// removePai(player, 16);
		// for (Integer integer : currentMjList) {
		// System.out.println(integer);
		// }
		// // 设置房间信息
		// RoomResp room = new RoomResp();
		// room.setRuleDaiHun(1);
		// room.setHunPai(32);
		// //1:检测长毛
		// List<Action> actionList =new ArrayList<Action>();
		// Action ac1= new Action();
		// ac1.setType(6);
		// Action ac2= new Action();
		// ac2.setType(7);
		// actionList.add(ac1);
		// actionList.add(ac2);
		// player.setActionList(actionList);
		// List<Integer> checkChangMao = checkChangMao(player, 34);
		// System.out.println();

		// 2:检测吃
		// List<Integer> checkChi = checkChi(player, room,14);
		// for (Integer integer : checkChi) {
		// System.out.println(integer);
		//
		// }
		// 3:检测通过吃动作获取动作集合
		// List<Integer> chiListFromAction = getChiListFromAction(55);
		// for (Integer integer : chiListFromAction) {
		// System.out.println(integer);
		// }
		// 4：检测
		// List<Long> n=new ArrayList<Long>(3);
		// Long[] playerIds=new Long[4];
		// playerIds[0]=111l;
		// playerIds[1]=222l;
		// playerIds[2]=333l;
		// playerIds[3]=666l;
		// Integer position=4;
		// List<Long> guoUserIds=new ArrayList<Long>();
		// guoUserIds.add(111l);
		// guoUserIds.add(222l);
		// guoUserIds.add(333l);
		// guoUserIds.add(666l);
		// List<Long> nowPositionByOrder = getNowPositionByOrder(n, playerIds,
		// position, guoUserIds);
		// for (Long long1 : nowPositionByOrder) {
		// System.out.println(long1);
		// }
		// some
		// List<Long> userIds=new ArrayList<Long>();
		// userIds.add(1111l);
		// userIds.add(1112l);
		// userIds.add(1113l);
		// userIds.add(1114l);
		// userIds.remove(1111l);
		// for (Long long1 : userIds) {
		// System.out.println(long1);
		// }
		// System.out.println("some");
		// System.out.println(-2%9);
		// System.out.println(18%9);
		// System.out.println(10%9);
//		int[] sho={1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//		if(Hulib.getInstance().get_hu_info(sho, 34, 34)){
//			System.out.println("嘿嘿");
//		}else{
//			System.out.println("哈哈");
//		}
		Player p = new Player();
		List<Integer> cards = new ArrayList<Integer>();
		cards.add(11);
		cards.add(12);
		cards.add(14);
		cards.add(15);
		cards.add(16);
		cards.add(17);
		cards.add(17);
		cards.add(10);
		p.setCurrentMjList(cards);
		List<Action> list = new ArrayList<Action>();
		Action a = new Action();
		a.setExtra(31);
		a.setType(2);
		list.add(a);
		Action b = new Action();
		b.setExtra(34);
		b.setType(2);
		list.add(b);
		p.setActionList(list);
		RoomResp room = new RoomResp();
		room.setRuleDaiHun(1);
		room.setRuleQiDui(1);
		room.setRuleQingYiSe(1);
		room.setRuleQiongHu(1);
		room.setRuleTuiDaoHu(1);
		room.setRuleXuanFeng(1);
		room.setRuleJueGang(1);
		room.setRuleChangMao(1);
		room.setHunPai(25);
		System.out.println(checkHu(p, room, null));
	}

	/**
	 * 获得所需要的牌型(干里干) 并打乱牌型
	 * 
	 * @return
	 */
	public static List<Integer> getPais() {
		// 1-9万 ,10-18饼,19-27条,28-31 东南西北,32-34 中发白
		ArrayList<Integer> pais = new ArrayList<Integer>();
		for (int j = 0; j < 4; j++) {
			for (int i = 1; i <= 34; i++) {
				pais.add(i);
			}
		}
		// 2.洗牌
		Collections.shuffle(pais);
		return pais;
	}

	/**
	 * 
	 * @param mahjongs
	 *            房间内剩余麻将的组合
	 * @param num
	 *            发的张数
	 * @return
	 */
	public static List<Integer> faPai(List<Integer> mahjongs, Integer num) {
		// 房间必须有牌，且牌的数量必须大于要发的牌数
		if (mahjongs == null || num == null || mahjongs.size() < num) {
			return null;
		}
		// ArrayList
		// rrayList内部是使用可増长数组实现的，所以是用get和set方法是花费常数时间的，但是如果插入元素和删除元素，除非插入和删除的位置都在表末尾，否则代码开销会很大，因为里面需要数组的移动。
		List<Integer> result = new ArrayList<>();
		for (int i = mahjongs.size() - 1; i >= 0; i--) {
			result.add(mahjongs.get(i));
			mahjongs.remove(i);
			num--;
			if (num == 0) {
				break;
			}
		}
		return result;
	}

	/**
	 * 给手牌排序
	 * 
	 * @param pais
	 * @return
	 */
	public static List<Integer> paiXu(List<Integer> pais) {
		Collections.sort(pais);
		return pais;
	}

	/**
	 * 检测玩家是否胡 没有动作拍说明是自摸，手牌数量为3n+2 有动作拍说明是别人出牌或者抢杠胡，手牌为3n+1
	 * 
	 * @param currentPlayer
	 * @param room
	 * @param dongZuoPai
	 * @param chuMoType
	 * @return
	 */
	private static Boolean checkHu(Player currentPlayer, RoomResp room, Integer dongZuoPai) {
		// 胡牌规则
		List<Integer> currentMjList = currentPlayer.getCurrentMjList();
		List<Integer> newList = getNewList(currentMjList, dongZuoPai);
		int[] shouPaiArr = getArrFromList(newList);
		Integer hunPai = null;
		Integer hunNum = 0;

		if (room.getRuleDaiHun().equals(1)) {// 带混
//			if (currentPlayer.getHasHun()) {// 玩家的混没杠过
				hunPai = room.getHunPai();
				// 获取混牌的数量
				hunNum = shouPaiArr[hunPai - 1];
				// 将混牌的位置置为0
				shouPaiArr[hunPai - 1] = 0;
//			}
		}
		// 如果混牌为null,设置混
		if (hunPai == null) {
			if (hunNum == 0) {// 无论带不带混，手中是没混的
				for (int i = 0; i < shouPaiArr.length; i++) {
					if (shouPaiArr[i] == 0) {
						// hunPai一定不为null
						hunPai = i + 1;
						break;
					}
				}
			}
		}

		// 1:七对胡不要求花色和幺九
		if (room.getRuleQiDui().equals(1)) {
			if (newList.size() == 14) {// 七对的手牌数量必须满足14张
				if (checkQiDui(shouPaiArr, hunNum, room)) {
					return true;
				}
			}
		}
		// 对于普通
		// 2.1：必须有19(风和字牌可以顶)--这个检测不需要绝,因为绝前提是手中有3张相同的牌
		Boolean hasYiJiu = false;
		// 2.2：三门齐或者 清一色(清一色必须选择才行)
		Boolean hasSanSe = false;
		// 2.3：必须有刻牌
		Boolean hasKe = false;
		// 2.4：必须开门
		Boolean isKaiMen = false;
		// 选了推倒胡，不需要开门
		if (room.getRuleTuiDaoHu().equals(1)) {
			isKaiMen = true;
		}
		Set<Integer> sanSe = new HashSet<Integer>();
		List<Integer> pengPaiList = new ArrayList<Integer>();
		// 3.1 检测已经有的动作牌
		List<Action> actionList = currentPlayer.getActionList();
		if (actionList != null && actionList.size() > 0) {
			for (Action action : actionList) {
				// 处理三色
				if (action.getExtra() != null && action.getExtra() <= 27) {
					// (action.getExtra()-1)/9 ---1-9为0;10-18为1;19-27为2
					sanSe.add((action.getExtra() - 1) / 9);
				}
				if (action.getType().equals(6) || action.getType().equals(7)) {// 风杠或者字杠---可顶幺九,可顶刻
					hasKe = true;
					hasYiJiu = true;
					if (action.getType().equals(7)) {
						isKaiMen = true;
					}
				} else if (action.getType().equals(1)) {// 吃
					isKaiMen = true;
					if (action.getActionId() == 35 || action.getActionId() == 41 || action.getActionId() == 42 || action.getActionId() == 48
							|| action.getActionId() == 49 || action.getActionId() == 55) {
						hasYiJiu = true;
					}
				} else if (action.getType().equals(8)) {// 长毛 ---不做处理
					hasYiJiu = true;
				} else {// 碰和杠
					isKaiMen = true;
					hasKe = true;
					if (action.getType().equals(2)) {
						pengPaiList.add(action.getExtra());
					}
					if (action.getExtra() > 27) {// 风或者字的碰和杠
						hasYiJiu = true;
					} else {
						if (action.getExtra() % 9 == 1 || action.getExtra() % 9 == 0) {
							hasYiJiu = true;
						}
					}
				}
			}
		}
		// 3.2 检测手牌--带混的话也已经移除掉了
		for (int i = 0; i < shouPaiArr.length; i++) {
			// 有这张牌
			if (shouPaiArr[i] > 0) {
				// 处理幺九
				if (i < 27) {
					if ((i + 1) % 9 == 1 || (i + 1) % 9 == 0) {
						hasYiJiu = true;
					}
					// 处理三色
					if (i < 9) {
						sanSe.add(0);
					} else if (i < 18) {
						sanSe.add(1);
					} else if (i < 27) {
						sanSe.add(2);
					}
				} else {
					// 中发白可以定19,可以顶刻
					hasKe = true;
					hasYiJiu = true;
				}
			}
		}
		// 2：三门齐或者 清一色(清一色必须选择才行)
		if (sanSe.size() == 3) {
			hasSanSe = true;
		}
		if (!hasSanSe) {
			if (room.getRuleQingYiSe().equals(1)) {// 有清一色
				if (sanSe.size() == 1) {
					hasSanSe = true;
				}
			}
		}
		// 之所以先不加刻牌判断,是因为手中可能有刻牌
		if (!hasSanSe || !hasYiJiu || !isKaiMen) {
			return false;
		}
		// 检测手牌能否胡
		Boolean hu = false;
		if (room.getRuleJueGang().equals(1)) {// 带绝---混的数量会变化
			if (hasKe) {// 已经碰的牌还有的话,放在手里当混使用
				// 找到绝当混的数量
				for (Integer pai : pengPaiList) {
					if (shouPaiArr[pai - 1] == 1 && pai != dongZuoPai) {// 手中有碰的牌,并且这张牌必须是自己摸的
						// 移除这张混牌
						shouPaiArr[pai - 1] = 0;
						// 放入混牌中
						hunNum++;
						shouPaiArr[hunPai - 1] = hunNum;
						// break;
					}
					// }
				}
			}
			// 找到手中的四张
			for (int i = 0; i < shouPaiArr.length; i++) {
				// 移除一张当混,并且这张牌必须是自己摸的,剩下的3张移除
				// 规则改了，别人出的牌也算
				// if (shouPaiArr[i] == 4 && Integer.valueOf(i + 1) !=
				// dongZuoPai) {
				if (shouPaiArr[i] == 4) {
					hasKe = true;
					shouPaiArr[i] = 0;
					hunNum++;
					shouPaiArr[hunPai - 1] = hunNum;
				}
			}
			// 加入现在混的数量
			shouPaiArr[hunPai - 1] = hunNum;
			if (!hasKe) {// 说明没绝,查看手中还有没有刻
				// 手中的没混的手牌数量的单牌数最大为3！！！
				for (int i = 0; i < shouPaiArr.length; i++) {
					if (hunNum + shouPaiArr[i] >= 3) {// 将其移除顶刻牌,就满足检测胡的需求了
						// 让它当刻
						int j = shouPaiArr[i];
						// 设置顶刻时需要混的数量
						int needHunNum = 3 - j;
						// 顶完之后剩余混的数量
						int newHunNum = hunNum - needHunNum;
						// !!!--先设置此牌再设置混牌是为了防止这张牌就是混牌,如果四张混
						// 刻牌数量变为0
						shouPaiArr[i] = 0;
						// 从新设置混的数量
						shouPaiArr[hunPai - 1] = newHunNum;
						// 检测手牌能否胡
						if (Hulib.getInstance().get_hu_info(shouPaiArr, 34, hunPai - 1)) {
							return true;
						} else {
							shouPaiArr[i] = j;
							// 混牌位置不用设置,因为上面每次都会从新设置
							// shouPaiArr[hunPai-1]=0;
						}
					}
				}
				// 2：若是手中有可以对倒的牌型,也可以胡
				return false;
			} else {// 上面有刻，不用管手中是否有刻
					// 检测手牌能否胡
				if (Hulib.getInstance().get_hu_info(shouPaiArr, 34, hunPai - 1)) {
					return true;
				} else {
					return false;
				}
			}

		} else {// 不带绝--需要想到手牌中一样的牌可能会有4张!!!(和绝不同的地方),需要减少3张
			if (hasKe) {// 动作中有刻,直接检测手牌是否可以胡
				if (hunNum != 0) {
					shouPaiArr[hunPai - 1] = hunNum;
					hu = Hulib.getInstance().get_hu_info(shouPaiArr, 34, hunPai - 1);
				} else {
					hu = Hulib.getInstance().get_hu_info(shouPaiArr, 34, 34);
				}
				if (hu) {
					return true;
				}
			} else {// 不带刻,检测手中是否有刻
				for (int i = 0; i < shouPaiArr.length; i++) {
					if ((hunNum + shouPaiArr[i]) >= 3) {// --让此牌顶刻
						if (shouPaiArr[i] >= 3) {// 这张牌不需要混顶
							int j = shouPaiArr[i];// 移除了3张顶混牌
							shouPaiArr[i] = j - 3;
							// 给混牌加上数量
							shouPaiArr[hunPai - 1] = hunNum;
							hu = Hulib.getInstance().get_hu_info(shouPaiArr, 34, hunPai - 1);
							if (hu) {
								return true;
							} else {
								shouPaiArr[i] = j;
							}
						} else {// 这张牌是小于3的
								// 让它当刻
							int j = shouPaiArr[i];
							// 设置顶刻时需要混的数量
							int needHunNum = 3 - j;
							// 顶完之后剩余混的数量
							int newHunNum = hunNum - needHunNum;
							// ！！！--先设置此牌再设置混牌是为了防止这张牌就是混牌,如果四张混
							// 刻牌数量变为0
							shouPaiArr[i] = 0;
							// 从新设置混的数量
							shouPaiArr[hunPai - 1] = newHunNum;
							// 检测手牌能否胡
							if (Hulib.getInstance().get_hu_info(shouPaiArr, 34, hunPai - 1)) {
								return true;
							} else {
								shouPaiArr[i] = j;
								// shouPaiArr[hunPai-1]=0;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 检测七对 --此方法会对绝选项进行区分检测
	 * 
	 * @param shouPaiArr
	 * @param hunNum
	 * @param room
	 * @return
	 */
	private static boolean checkQiDui(int[] shouPaiArr, Integer hunNum, RoomResp room) {
		int oneNum = 0;
		Boolean jue = false;

		if (room.getRuleJueGang().equals(1)) {// 房间带有绝
			jue = true;
		}
		Integer roomAction = room.getRoomAction();
		Integer dongZuoPai = null;
		// 只考虑出牌阶段不用管抢杠阶段,因为那张牌对自己而言肯定是单张(别人杠了都)
		if (roomAction.equals(Cnst.CHECK_TYPE_CHU)) {// 找到别的玩家出的那张牌
			// 找到玩家出的那张牌
			dongZuoPai = room.getLastDongZuoPai();

		}
		for (int i = 0; i < shouPaiArr.length; i++) {
			if (shouPaiArr[i] % 2 == 1) {
				oneNum++;
			}
			if (jue) {// 带绝选项,手中的3张当刻使用,无法形成七对--别人出的四张不俗按绝
				if (shouPaiArr[i] == 4 && Integer.valueOf(i + 1) != dongZuoPai) {
					return false;
				}
			}
		}
		if (oneNum <= hunNum) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 检测玩家动作 ，分为出牌检测和摸牌检测 出牌,必须是出去手中的牌 摸牌,指摸剩下牌库里的牌
	 * 如果是自动执行的动作，那么不给过选项,前端判断没有过的时候,自动201接口执行动作
	 * 
	 * @param players
	 * @param currentPlayer
	 * @param action
	 * @param room
	 * @param extraPai
	 */
	public static Player checkActions(List<Player> players, Player currentPlayer, RoomResp room, Integer chuMoType, Integer dongZuoPai) {
		if (room.getState() == null || room.getState() != Cnst.ROOM_STATE_GAMIING) {
			logger.error("房间状态有无");
			return null;
		}
		// 定义要返回的动作玩家
		Player actionPlayer = null;
		if (chuMoType == Cnst.CHECK_TYPE_MO) {// 摸牌检测---前面动作牌传的null
			actionPlayer = currentPlayer;
			// 获取当前玩家动作
			List<Integer> currentActions = currentPlayer.getCurrentActions();
			// 为了判断玩家的优先级
			List<Integer> currentActionLevers = currentPlayer.getCurrentActionLevers();
			if (currentActions == null) {
				currentActions = new ArrayList<Integer>();
			}
			if (currentActionLevers == null) {
				currentActionLevers = new ArrayList<Integer>();
			}
			// 每次检测动作之前，将要检测的玩家清空
			currentActions.clear();
			currentActionLevers.clear();
			if (currentPlayer.getCanHu()) {
				if (checkHu(currentPlayer, room, dongZuoPai)) {// 胡
					// 胡的动作是500
					currentActions.add(Cnst.HU_ACTION);// 500
				}
			}
			// 检测玩家的碰杠和暗杠
			if (room.getRuleJueGang().equals(2)) {// 选择可以普通杠
				// 剩下4张的时候只有碰碰胡才可以检测杠
				List<Integer> gangList = checkGang(currentPlayer, room, dongZuoPai, chuMoType);
				if (gangList != null) {
					currentActions.addAll(gangList);
				}
			}
			// 选择的话玩家是不是出过牌
			if (!currentPlayer.getHasChu()) {// 玩家没出过牌
				// 房间规则是否选择旋风杠
				if (room.getRuleXuanFeng().equals(1)) {// 选择了旋风杠
					// 检测旋风杠
					List<Integer> gangList = checkXuanFengGang(currentPlayer, room, null);
					if (gangList != null && gangList.size() > 0) {
						// 添加动作信息
						currentActions.addAll(gangList);
					}
				}
			}
			if (room.getRuleXuanFeng().equals(1)) {// 选择了旋风杠
				if (room.getRuleChangMao().equals(1)) {// 玩家选择了长毛
					List<Integer> changMaoList = checkZhangMao(currentPlayer, dongZuoPai);
					if (changMaoList != null && changMaoList.size() > 0) {
						currentActions.addAll(changMaoList);
					}
				}
			}
			// 如果摸牌玩家没有任何动作
			if (currentActions.size() == 0) {
				// 将该玩家的动作设置成出牌
				currentActions.add(Cnst.CHU_ACTION);
			} else {
				currentActions.add(Cnst.GUO_ACTION);
			}
		} else if (chuMoType == Cnst.CHECK_TYPE_CHU) {// 出牌检测
			// 每次出牌检测重置此集合
			List<Long> nowPlayerIds = room.getNowPlayerIds();
			if (nowPlayerIds == null) {
				nowPlayerIds = new ArrayList<Long>(3);
			} else {// 清空上次检测的数据
				nowPlayerIds.clear();
			}
			room.setNowPlayerIds(nowPlayerIds);
			// 获取当前玩家的位置
			Integer position = currentPlayer.getPosition();
			// 定义下一个玩家
			Player nextPlayer = null;
			// 获取下家玩家的位置
			Integer nextPlayerPosition = getNextPlayerPosition(position);
			// 获取归0的人
			// 设置一个存储玩家位置的集合，目的是根据位置获取nowPlayerIds;
			List<Player> actionPlayers = new ArrayList<Player>(3);
			// 定义要检测的玩家
			Player needCheckplayer = null;

			// 获取房间玩家集合
			// Long[] playerIds = room.getPlayerIds();
			for (Player p : players) {
				Long playerId = p.getUserId();
				if (playerId == null) {
					System.err.println("玩家丢失");
					continue;
				}
				if (playerId.equals(currentPlayer.getUserId())) {// 不检测出牌玩家
					continue;
				} else {// 检测剩下3个玩家的动作
					needCheckplayer = p;
					// 获取当前玩家动作
					List<Integer> currentActions = needCheckplayer.getCurrentActions();
					// 为了判断玩家的优先级
					List<Integer> currentActionLevers = needCheckplayer.getCurrentActionLevers();
					if (currentActions == null) {
						currentActions = new ArrayList<Integer>();
					}
					if (currentActionLevers == null) {
						currentActionLevers = new ArrayList<Integer>();
					}
					currentActions.clear();
					currentActionLevers.clear();
					// 检测胡
					if (needCheckplayer.getCanHu()) {
						if (checkHu(needCheckplayer, room, dongZuoPai)) {// 胡
							// 胡的动作是500
							currentActions.add(Cnst.HU_ACTION);// 500
							currentActionLevers.add(Cnst.ACTION_TYPE_HU);
						}
					}
					//--如果是别人出顶的牌，检测出碰之后，返回杠
					Boolean needCheck=true;
					Integer dingHunPai = room.getDingHunPai();
					if(dingHunPai!=null){
						if(dongZuoPai.equals(dingHunPai)){//说明出的是定的牌，只需要检测
							List<Integer> newList = getNewList(needCheckplayer.getCurrentMjList(), null);
							// 手牌的数组形式
							int[] shouPaiArr = null;
							// 现在混排可以杠
							shouPaiArr = getArrFromList(newList);
							if(shouPaiArr[dongZuoPai-1]==2){//手里有两张,那么返回杠(绝头杠)
								Integer action = getActionFromPai(dongZuoPai, Cnst.PLAYER_ACTION_GANG);
								currentActions.add(action);
								currentActionLevers.add(Cnst.ACTION_TYPE_DIANGANG);
							}
							needCheck=false;
						}
					}
					// 检测杠
					if(needCheck){
						if (room.getRuleJueGang().equals(2)) {// 选择可以普通杠
							// 剩下4张的时候只有碰碰胡才可以检测杠
							List<Integer> gangList = checkGang(needCheckplayer, room, dongZuoPai, chuMoType);
							if (gangList != null) {
								currentActions.addAll(gangList);
								currentActionLevers.add(Cnst.ACTION_TYPE_DIANGANG);
							}
						}
						boolean checkPeng = true;
						if (room.getRuleDaiHun().equals(1)) {// 规则带混排
							if (room.getHunPai().equals(dongZuoPai)) {// 别人出的混排
								checkPeng = false;
							}
						}
						List<Integer> pengGangList = null;
						if (checkPeng) {
							pengGangList = checkPeng(needCheckplayer, dongZuoPai, room);
						}
						if (pengGangList != null) {
							currentActions.addAll(pengGangList);
							currentActionLevers.add(Cnst.ACTION_TYPE_PENG);
						}
					}
					// 检测是不是下家
					if (needCheckplayer.getPosition().equals(nextPlayerPosition)) {
						// 给下一个玩家赋值
						nextPlayer = needCheckplayer;
						// 检测吃
						List<Integer> chiActionsList = checkChi(needCheckplayer, room, dongZuoPai);
						if (chiActionsList != null) {
							currentActions.addAll(chiActionsList);
							currentActionLevers.add(Cnst.ACTION_TYPE_CHI);
						}
					}
					// 如果玩家没有动作
					if (currentActionLevers.size() != 0) {// 有动作
						currentActions.add(Cnst.GUO_ACTION);
						actionPlayers.add(needCheckplayer);
					}
				}
			}

			if (actionPlayers.size() == 0) {// 说明都没动作，那么下个玩家就是动作玩家--动作是摸牌
				actionPlayer = nextPlayer;
				List<Integer> currentActions = nextPlayer.getCurrentActions();
				currentActions.add(Cnst.MO_ACTION);
				RedisUtil.updateRedisData(null, nextPlayer);
			} else {// 有动作的玩家至少有1人
					// 根据有动作的玩家集合,获取一个按照位置优先级的玩家集合
				getNowPositionByOrder(nowPlayerIds, actionPlayers, position);
				for (Player p : actionPlayers) {
					RedisUtil.updateRedisData(null, p);
				}
				actionPlayer = getFirstActionPlayer(nowPlayerIds, players, room);
				room.setNowPlayerIds(nowPlayerIds);
			}
		} else if (chuMoType == Cnst.CHECK_TYPE_HAILAO) {// 海劳阶段
			actionPlayer = currentPlayer;
			// 获取当前玩家动作
			List<Integer> currentActions = currentPlayer.getCurrentActions();
			if (currentActions == null) {
				currentActions = new ArrayList<Integer>();
			}
			// 每次检测动作之前，将要检测的玩家清空
			currentActions.clear();
			// 检测海捞摸牌的玩家是否能胡
			if (currentPlayer.getCanHu() && checkHu(currentPlayer, room, dongZuoPai)) {// 胡
				// 胡的动作是500
				currentActions.add(Cnst.HU_ACTION);// 500
			} else {
				// 不胡的话,将下一个玩家的状态设置为摸牌
				Integer nextPlayerPosition = getNextPlayerPosition(currentPlayer.getPosition());
				for (Player p : players) {
					if (p.getPosition().equals(nextPlayerPosition)) {
						actionPlayer = p;
						List<Integer> currentActions2 = actionPlayer.getCurrentActions();
						if (currentActions2 == null) {
							currentActions2 = new ArrayList<Integer>();
						}
						currentActions2.clear();
						currentActions2.add(Cnst.MO_ACTION);
						break;
					}
				}
			}
		} else {// 抢杠胡 --检测其他三家会不会胡
				// 每次出牌检测重置此集合
			List<Long> nowPlayerIds = room.getNowPlayerIds();
			if (nowPlayerIds == null) {
				nowPlayerIds = new ArrayList<Long>(3);
			} else {// 清空上次检测的数据
				nowPlayerIds.clear();
			}
			room.setNowPlayerIds(nowPlayerIds);
			// 获取当前玩家的位置
			Integer position = currentPlayer.getPosition();
			// 设置一个存储玩家位置的集合，目的是根据位置获取nowPlayerIds;
			List<Player> actionPlayers = new ArrayList<Player>(3);
			// 定义要检测的玩家
			Player needCheckplayer = null;

			// 获取房间玩家集合
			for (Player p : players) {
				Long playerId = p.getUserId();
				if (playerId == null) {
					System.err.println("玩家丢失");
					continue;
				}
				if (playerId.equals(currentPlayer.getUserId())) {// 不检测执行碰杠玩家
					continue;
				} else {// 检测剩下3个玩家的胡动作
					needCheckplayer = p;
					// 获取当前玩家动作
					List<Integer> currentActions = needCheckplayer.getCurrentActions();
					// 为了判断玩家的优先级
					List<Integer> currentActionLevers = needCheckplayer.getCurrentActionLevers();
					if (currentActions == null) {
						currentActions = new ArrayList<Integer>();
					}
					if (currentActionLevers == null) {
						currentActionLevers = new ArrayList<Integer>();
					}
					currentActions.clear();
					currentActionLevers.clear();
					// 检测胡
					if (needCheckplayer.getCanHu()) {
						if (checkHu(needCheckplayer, room, dongZuoPai)) {// 胡
							// 胡的动作是500
							currentActions.add(Cnst.HU_ACTION);// 500
							currentActionLevers.add(Cnst.ACTION_TYPE_HU);
						}
					}
					//检测碰
					boolean checkPeng = true;
					if (room.getRuleDaiHun().equals(1)) {// 规则带混排
						if (room.getHunPai().equals(dongZuoPai)) {// 别人出的混排
							checkPeng = false;
						}
					}
					List<Integer> pengGangList = null;
					if (checkPeng) {
						pengGangList = checkPeng(needCheckplayer, dongZuoPai, room);
					}
					if (pengGangList != null) {
						currentActions.addAll(pengGangList);
						currentActionLevers.add(Cnst.ACTION_TYPE_PENG);
					}
					// 如果玩家没有动作
					if (currentActionLevers.size() != 0) {// 有胡动作
						currentActions.add(Cnst.GUO_ACTION);
						actionPlayers.add(needCheckplayer);
						room.setCurrentActions(currentActions);
					}
				}
			}

			if (actionPlayers.size() != 0) {// 有动作的玩家至少有1人
				// 根据有动作的玩家集合,获取一个按照位置优先级的玩家集合
				getNowPositionByOrder(nowPlayerIds, actionPlayers, position);
				actionPlayer = getFirstActionPlayer(nowPlayerIds, players, room);
				room.setNowPlayerIds(nowPlayerIds);
			}
		}
		if (actionPlayer != null) {// 抢杠胡阶段ac
			room.setCurrentActions(actionPlayer.getCurrentActions());
		}
		return actionPlayer;
	}

	/**
	 * 按照顺序动作玩家的集合
	 * 
	 * @param nowPlayerIds
	 * @param actionPlayers
	 * @param position
	 */
	private static void getNowPositionByOrder(List<Long> nowPlayerIds, List<Player> actionPlayers, Integer position) {
		Long[] arr = new Long[3];
		for (Player player : actionPlayers) {
			int x = (player.getPosition() - position + 4) % 4 - 1;
			arr[x] = player.getUserId();
		}
		for (Long long1 : arr) {
			if (long1 != null) {
				nowPlayerIds.add(long1);
			}
		}
	}

	/**
	 * --注意,此方法检测是玩家至少为1 获取优先级最高的玩家
	 * 
	 * @param currentPlayer
	 * @param players
	 * @param room
	 * @param room
	 * @return
	 */
	public static Player getFirstActionPlayer(List<Long> nowPlayerIds, List<Player> players, RoomResp room) {
		List<Player> needCheck = new ArrayList<Player>(4);
		// 按照顺序优先级获取所有玩家集合
		for (Long long1 : nowPlayerIds) {
			for (Player p : players) {
				if (p.getUserId().equals(long1)) {
					needCheck.add(p);
				}
			}
		}
		// 将优先级最高的设置为第一个玩家
		Player firstPlayer = needCheck.get(0);
		// 如果有动作的只有一个人,那么返回此人
		if (needCheck.size() == 1) {
			return firstPlayer;
		}
		// 按照优先级来显示
		Integer firstPlayerAction = firstPlayer.getCurrentActionLevers().get(0);
		Integer otherPlayerAction = 0;
		for (int i = 1; i < needCheck.size(); i++) {
			otherPlayerAction = needCheck.get(i).getCurrentActionLevers().get(0);
			if (firstPlayerAction <= otherPlayerAction) {
				if (firstPlayerAction.equals(Cnst.ACTION_TYPE_HU) && firstPlayerAction == otherPlayerAction) {
					// 看两个人谁胡的分大
					Integer lastDongZuoPai = room.getLastDongZuoPai();
					List<Integer> fMjList = firstPlayer.getCurrentMjList();
					List<Integer> sMjList = needCheck.get(i).getCurrentMjList();
					fMjList.add(lastDongZuoPai);
//					Integer firstScore = getHuLeiXingScoreFromHuXingList(checkHuInfo(players, firstPlayer, room));
					List<Integer> firstInfo = checkHuInfo(players, firstPlayer, room);
					Integer firstLevel=getLevelFromHuInfo(firstInfo);
					sMjList.add(lastDongZuoPai);
//					Integer secondScore = getHuLeiXingScoreFromHuXingList(checkHuInfo(players, needCheck.get(i), room));
					List<Integer> secondInfo = checkHuInfo(players, needCheck.get(i), room);
					Integer secondLevel=getLevelFromHuInfo(secondInfo);
					if (firstLevel < secondLevel) {// 只有第一个人胡的分少的时候才会更改第一个显示的玩家
						firstPlayer = needCheck.get(i);
					}
					// 移除加到玩家手里的手牌
					fMjList.remove(fMjList.size() - 1);
					sMjList.remove(sMjList.size() - 1);
					// 清空比较的胡list
					// firstPlayer.setShowList(new ArrayList<Integer>());
					// needCheck.get(i).setShowList(new ArrayList<Integer>());
				} else {
					firstPlayer = needCheck.get(i);
					firstPlayerAction=needCheck.get(i).getCurrentActionLevers().get(0);
				}
			}
		}
		return firstPlayer;

	}
	/**
	 * 第一段： 平胡   ,穷胡
	 * 第二段：穷胡和清一色,穷胡和清一色,飘胡
	 * 第三段：飘胡清一色,七对
	 * 第四段：七对清一色
	 * @param infoList
	 * @return
	 */
	private static Integer getLevelFromHuInfo(List<Integer> infoList) {
		int size = infoList.size();
		Integer integer = infoList.get(0);
		int level;
		if(size==1){
			if(integer.equals(Cnst.PINGHU) || integer.equals(Cnst.QIONGHU)){
				level= 1;
			}else if(integer.equals(Cnst.PIAOHU)){
				level= 2;
			}else{
				level= 3;
			}
		}else{
			if(integer.equals(Cnst.PINGHU) || integer.equals(Cnst.QIONGHU)){
				level= 2;
			}else if(integer.equals(Cnst.PIAOHU)){
				level= 3;
			}else{
				level= 4;
			}
		}
		return level;
	}

	/**
	 * --能出的牌肯定不是混牌 检测能否碰 --因为旋风杠可以杠混，导致之后混牌可以打出，如过别人出的是混,自己的混排不能显示碰
	 * 
	 * @param currentPlayer
	 * @param room
	 * @param dongZuoPai
	 * @param room
	 * @return
	 */
	private static List<Integer> checkPeng(Player currentPlayer, Integer dongZuoPai, RoomResp room) {

		// 获取手牌
		List<Integer> currentMjList = currentPlayer.getCurrentMjList();
		// 获取手牌数组集合
		int[] shouPaiArr = getArrFromList(currentMjList);
		// 定义碰杠的集合
		List<Integer> pengGangActionList = new ArrayList<Integer>(2);
		// 检测杠--有杠就有碰
		// 没有杠,检测碰
		Boolean checkPeng = true;
		if (currentMjList.size() == 4) {// 4张牌检测对于 碰有限制,只有满足砰砰胡才能出碰
			// 此时肯定有动作--不然手牌怎么会是4
			List<Action> actionList = currentPlayer.getActionList();
			for (Action action : actionList) {
				if (action.getType().equals(1)) {// 有吃
					checkPeng = false;
				}
			}
		}
		if (checkPeng) {
			if (shouPaiArr[dongZuoPai - 1] >= 2) {// 如果有大于两张这样的牌,添加碰和杠的动作
				if (checkPeng) {// 能检测碰
					pengGangActionList.add(getActionFromPai(dongZuoPai, Cnst.PLAYER_ACTION_PENG));
				}
				return pengGangActionList;
			}
		}
		return null;
	}

	/**
	 * 
	 * 检测玩家吃
	 * 
	 * @param needCheckplayer
	 * @param room
	 * @param dongZuoPai
	 * @return
	 */
	private static List<Integer> checkChi(Player needCheckplayer, RoomResp room, Integer dongZuoPai) {
		// 玩家手牌为4张时,只能碰(构成碰碰胡的话)或者杠
		List<Integer> currentMjList = needCheckplayer.getCurrentMjList();
		if (currentMjList.size() == 4) {
			return null;
		}
		List<Integer> chiActionList = null;
		if (dongZuoPai >= 28) {// 风和字牌不检测
			return null;
		} else {// 万饼条检测
			List<Integer> newList = getNewList(currentMjList, null);
			int[] shouPaiArr = getArrFromList(newList);
			if (room.getRuleDaiHun() == 1) {// 带混
				Integer hunPai = room.getHunPai();
				shouPaiArr[hunPai - 1] = 0;// 将混的位置设置牌数为0,混不能落地
			}
			// 房间选择了绝
			if (room.getRuleJueGang().equals(1)) {
				// 找到碰的集合
				List<Integer> pengList = needCheckplayer.getPengList();
				if (pengList != null && pengList.size() > 0) {
					// 将绝的牌置为0
					for (Integer pengPai : pengList) {
						shouPaiArr[pengPai - 1] = 0;
					}
				}
			}
			chiActionList = new ArrayList<Integer>(3);
			if (dongZuoPai % 9 == 1) {// 1
				// 是否有大1的 和大2的
				if (shouPaiArr[dongZuoPai] > 0 && shouPaiArr[dongZuoPai + 1] > 0) {
					chiActionList.add(getActionFromPai(dongZuoPai + 1, Cnst.PLAYER_ACTION_CHI));
				}
			} else if (dongZuoPai % 9 == 2) {// 2
				// 是否有小1的 和大1的
				if (shouPaiArr[dongZuoPai - 2] > 0 && shouPaiArr[dongZuoPai] > 0) {
					chiActionList.add(getActionFromPai(dongZuoPai, Cnst.PLAYER_ACTION_CHI));
				}
				// 是否有大1的 和大2的
				if (shouPaiArr[dongZuoPai] > 0 && shouPaiArr[dongZuoPai + 1] > 0) {
					chiActionList.add(getActionFromPai(dongZuoPai + 1, Cnst.PLAYER_ACTION_CHI));
				}
			} else if (dongZuoPai % 9 == 8) {// 8
				// 是否有小2的 和小1的
				if (shouPaiArr[dongZuoPai - 3] > 0 && shouPaiArr[dongZuoPai - 2] > 0) {
					chiActionList.add(getActionFromPai(dongZuoPai - 1, Cnst.PLAYER_ACTION_CHI));
				}
				// 是否有小1的 和大1的
				if (shouPaiArr[dongZuoPai - 2] > 0 && shouPaiArr[dongZuoPai] > 0) {
					chiActionList.add(getActionFromPai(dongZuoPai, Cnst.PLAYER_ACTION_CHI));
				}
			} else if (dongZuoPai % 9 == 0) {// 9
				// 是否有小2的 和小1的
				if (shouPaiArr[dongZuoPai - 3] > 0 && shouPaiArr[dongZuoPai - 2] > 0) {
					chiActionList.add(getActionFromPai(dongZuoPai - 1, Cnst.PLAYER_ACTION_CHI));
				}
			} else {// 3--7
					// 是否有小2的 和小1的
				if (shouPaiArr[dongZuoPai - 3] > 0 && shouPaiArr[dongZuoPai - 2] > 0) {
					chiActionList.add(getActionFromPai(dongZuoPai - 1, Cnst.PLAYER_ACTION_CHI));
				}
				// 是否有小1的 和大1的
				if (shouPaiArr[dongZuoPai - 2] > 0 && shouPaiArr[dongZuoPai] > 0) {
					chiActionList.add(getActionFromPai(dongZuoPai, Cnst.PLAYER_ACTION_CHI));
				}
				// 是否有大1的 和大2的
				if (shouPaiArr[dongZuoPai] > 0 && shouPaiArr[dongZuoPai + 1] > 0) {
					chiActionList.add(getActionFromPai(dongZuoPai + 1, Cnst.PLAYER_ACTION_CHI));
				}
			}

		}
		if (chiActionList == null) {
			return null;
		} else {
			return chiActionList.size() > 0 ? chiActionList : null;
		}
	}

	/**
	 * --进入此方法的前提是,房间规则允许长毛 检测长毛
	 * 
	 * @param currentPlayer
	 * @param dongZuoPai
	 * @return
	 */
	public static List<Integer> checkZhangMao(Player currentPlayer, Integer dongZuoPai) {
		List<Action> actionList = currentPlayer.getActionList();
		List<Integer> changMaoList = new ArrayList<Integer>(7);
		if (actionList != null && actionList.size() > 0) {
			Boolean hasFengGang = false;
			Boolean hasZhongFaBaiGang = false;
			for (Action action : actionList) {
				if (action.getType() == 6) {// 东南西北
					hasFengGang = true;
				} else if (action.getType() == 7) {// 中发白
					hasZhongFaBaiGang = true;
				}
			}
			if (hasFengGang || hasZhongFaBaiGang) {
				// 如果有动作,会自动加入并且排序
				List<Integer> newList = getNewList(currentPlayer.getCurrentMjList(), dongZuoPai);
				int[] shouPaiArr = getArrFromList(newList);
				if (hasFengGang) {
					if (shouPaiArr[27] > 0) {
						changMaoList.add(getActionFromPai(27 + 1, Cnst.PLAYER_ACTION_ZHANGMAO));
					}
					if (shouPaiArr[28] > 0) {
						changMaoList.add(getActionFromPai(28 + 1, Cnst.PLAYER_ACTION_ZHANGMAO));
					}
					if (shouPaiArr[29] > 0) {
						changMaoList.add(getActionFromPai(29 + 1, Cnst.PLAYER_ACTION_ZHANGMAO));
					}
					if (shouPaiArr[30] > 0) {
						changMaoList.add(getActionFromPai(30 + 1, Cnst.PLAYER_ACTION_ZHANGMAO));
					}
				}
				if (hasZhongFaBaiGang) {
					if (shouPaiArr[31] > 0) {
						changMaoList.add(getActionFromPai(31 + 1, Cnst.PLAYER_ACTION_ZHANGMAO));
					}
					if (shouPaiArr[32] > 0) {
						changMaoList.add(getActionFromPai(32 + 1, Cnst.PLAYER_ACTION_ZHANGMAO));
					}
					if (shouPaiArr[33] > 0) {
						changMaoList.add(getActionFromPai(33 + 1, Cnst.PLAYER_ACTION_ZHANGMAO));
					}
				}
			}
		}
		return changMaoList.size() > 0 ? changMaoList : null;
	}

	/**
	 * 能进入此方法肯定可以进行碰杠和暗杠 检测玩家的杠
	 * 
	 * @param currentPlayer
	 * @param room
	 * @param dongZuoPai
	 * @param chuMoType
	 * @return 没有为null
	 */
	private static List<Integer> checkGang(Player currentPlayer, RoomResp room, Integer dongZuoPai, Integer chuMoType) {
		// 如果有动作,会自动加入并且排序 -- 出牌的时候传的null,动作拍的时候也不会传
		List<Integer> newList = getNewList(currentPlayer.getCurrentMjList(), null);
		// 手牌的数组形式
		int[] shouPaiArr = null;
		// 有4个杠就能死他了=.=!!
		Integer action = null;
		List<Integer> gangList = new ArrayList<Integer>(4);
		// 现在混排可以杠
		shouPaiArr = getArrFromList(newList);
		// 获取玩家的动作集合
		List<Action> actionList = currentPlayer.getActionList();
		if (chuMoType.equals(Cnst.CHECK_TYPE_MO)) {// 摸牌检测 --多检测一个碰杠
			// 检测碰里面的杠
			if (actionList != null && actionList.size() > 0) {
				for (Action ac : actionList) {
					// 如果是碰，检测手牌和碰的牌
					if (ac.getType().equals(2)) {

						if (shouPaiArr[ac.getExtra() - 1] == 1) {
							action = getActionFromPai(ac.getExtra(), Cnst.PLAYER_ACTION_GANG);
							gangList.add(action);
						}
					}
				}
			}
			// 手中4张为杠
			for (int i = 0; i < shouPaiArr.length; i++) {
				if (shouPaiArr[i] == 4) {
					action = getActionFromPai(i + 1, Cnst.PLAYER_ACTION_GANG);
					gangList.add(action);
				}
			}
		} else if (chuMoType.equals(Cnst.CHECK_TYPE_CHU)) {// 手中有三张
			// 手中3张为杠 ,且和出的牌一样
			if (shouPaiArr[dongZuoPai - 1] == 3) {
				action = getActionFromPai(dongZuoPai, Cnst.PLAYER_ACTION_GANG);
				gangList.add(action);
			}
		}
		return gangList.size() > 0 ? gangList : null;
	}

	/**
	 * 进此方法的前提就是1：摸牌阶段 2：没出过牌 检测旋风杠--(单写是因为不管选不选杠都会检测这个特殊的杠)
	 * 
	 * @param currentPlayer
	 * @param room
	 * @param dongZuoPai
	 * @param chuMoType
	 * @return
	 */
	public static List<Integer> checkXuanFengGang(Player currentPlayer, RoomResp room, Integer dongZuoPai) {
		// 如果有动作,会自动加入并且排序
		List<Integer> newList = getNewList(currentPlayer.getCurrentMjList(), dongZuoPai);
		// 手牌的数组形式
		int[] shouPaiArr = null;
		// 定义旋风杠集合
		List<Integer> gangList = null;
		List<Integer> needCheck = new ArrayList<Integer>(2);
		// 获取手牌集合 --带混的旋风杠不能落地---更改了,混的旋风杠可以落地
		// if (room.getRuleDaiHun().equals(1)) {// 带混
		// // 手牌和混牌分开
		// shouPaiArr = getArrFromList(newList);
		// // 移除混牌---(4混牌不能组成杠)
		// shouPaiArr[room.getHunPai() - 1] = 0;
		// } else {//
		// shouPaiArr = getArrFromList(newList);
		// }
		// // ！！！改完需求1：之后,只要没出国牌，可以碰和吃之后再检测旋风杠,检测碰之后，如果3张红中
		// if (room.getRuleJueGang().equals(1)) {
		// // 带角选项
		// // 是否上次动作是碰
		// List<Integer> pengList = currentPlayer.getPengList();
		// if (pengList != null && pengList.size() > 0) {
		// // 获取碰的牌
		// Integer pengPai = pengList.get(0);
		// // 将手中的碰的牌滞空,因为变成绝了
		// shouPaiArr[pengPai - 1] = 0;
		// }
		// }
		// ！！！改完需求2：之后,混或绝都可以杠出去,杠出去后混就不再是混了
		shouPaiArr = getArrFromList(newList);

		// 检测手牌能组成的旋风杠
		if (shouPaiArr[27] > 0 && shouPaiArr[28] > 0 && shouPaiArr[29] > 0 && shouPaiArr[30] > 0) {
			needCheck.add(35);
		}
		if (shouPaiArr[31] > 0 && shouPaiArr[32] > 0 && shouPaiArr[33] > 0) {
			needCheck.add(36);
		}
		// 检测已经进行过的旋风杠的集合
		List<Integer> needRemove = new ArrayList<Integer>(2);
		List<Action> actionList = currentPlayer.getActionList();
		if (actionList != null && actionList.size() > 0) {
			for (Action action : actionList) {
				if (action.getType() == 6) {// 风杠杠
					needRemove.add(35);
				} else if (action.getType() == 7) {// 中发白杠
					needRemove.add(36);
				}
			}
		}

		needCheck.removeAll(needRemove);
		if (needCheck != null && needCheck.size() > 0) {
			gangList = new ArrayList<Integer>(2);
			for (Integer pai : needCheck) {
				gangList.add(getActionFromPai(pai, Cnst.PLAYER_ACTION_GANG));
			}
		}
		return gangList == null ? null : gangList;
	}

	/**
	 * 通过动作寻找牌
	 * 
	 * @param action
	 *            动作
	 * @param dongZuo
	 *            吃或者碰或者杠
	 * @return
	 */
	public static List<Integer> getPaiListFromAction(Integer action, Integer dongZuo) {
		List<Integer> actionList = new ArrayList<Integer>(4);
		if (dongZuo.equals(Cnst.PLAYER_ACTION_CHI)) {
			if (action <= 41) {// 万
				actionList.add(action - 34);
				actionList.add(action - 33);
				actionList.add(action - 32);
			} else if (action >= 49) {// 条
				actionList.add(action - 30);
				actionList.add(action - 29);
				actionList.add(action - 28);
			} else {// 饼
				actionList.add(action - 32);
				actionList.add(action - 31);
				actionList.add(action - 30);
			}
		} else if (dongZuo.equals(Cnst.PLAYER_ACTION_PENG)) {// 并没有用到
			actionList.add(action - 56);
			actionList.add(action - 56);
			actionList.add(action - 56);
		} else if (dongZuo.equals(Cnst.PLAYER_ACTION_GANG)) {
			if (action == 125) {
				actionList.add(28);
				actionList.add(29);
				actionList.add(30);
				actionList.add(31);
			} else if (action == 126) {
				actionList.add(32);
				actionList.add(33);
				actionList.add(34);
			} else {
				actionList.add(action - 90);
				actionList.add(action - 90);
				actionList.add(action - 90);
				actionList.add(action - 90);
			}

		} else if (dongZuo.equals(Cnst.PLAYER_ACTION_ZHANGMAO)) {
			actionList.add(action - 99);
		}
		return actionList;
	}

	/**
	 * 获取下一个玩家的位置
	 * 
	 * @param position
	 * @return
	 */
	public static Integer getNextPlayerPosition(Integer position) {
		Integer nextPlayerPosition = null;
		if (position == 4) {
			nextPlayerPosition = 1;
		} else {
			nextPlayerPosition = position + 1;
		}
		return nextPlayerPosition;
	}

	/**
	 * 将手牌转换成数组
	 * 
	 * @param newList
	 * @return
	 */
	private static int[] getArrFromList(List<Integer> newList) {
		int[] shouPaiArr = new int[34];
		for (Integer integer : newList) {
			int x = shouPaiArr[integer - 1] + 1;
			shouPaiArr[integer - 1] = x;
		}
		return shouPaiArr;
	}

	/**
	 * 
	 * @param pai
	 *            (引发动作的牌;吃的话,放中间的那张牌;长毛的传能长毛的牌)
	 * @param action
	 *            吃,碰,杠,长毛
	 * @return
	 */
	public static Integer getActionFromPai(Integer pai, Integer dongZuo) {
		Integer action = null;
		if (dongZuo == Cnst.PLAYER_ACTION_CHI) {// 吃
			// 因为传递的时中间那张牌,所以根据中间牌找动作
			if (pai / 9 == 0) {// 万
				action = pai + 33;
			} else if (pai / 9 == 1) {// 饼
				action = pai + 31;
			} else if (pai / 9 == 2) {// 条
				action = pai + 29;
			}
		} else if (dongZuo == Cnst.PLAYER_ACTION_PENG) {// 碰
			action = pai + 56;
		} else if (dongZuo == Cnst.PLAYER_ACTION_GANG) {// 杠
			if (pai == 35) {// 东南西北时传35
				action = 125;
			} else if (pai == 36) {// 中发白时传36
				action = 126;
			} else {// 普通的杠 --------- 4张的杠
				action = pai + 90;
			}
		} else if (dongZuo == Cnst.PLAYER_ACTION_ZHANGMAO) {// 张毛
			// 这个dongZuo
			action = pai + 99;
		}
		return action;
	}

	/**
	 * 获取新的手牌集合(排好序的)
	 * 
	 * @param currentMjList
	 * @param dongZuoPai
	 * @return
	 */
	private static List<Integer> getNewList(List<Integer> currentMjList, Integer dongZuoPai) {
		List<Integer> newList = new ArrayList<Integer>(currentMjList.size() + 1);
		for (Integer pai : currentMjList) {
			newList.add(pai);
		}
		if (dongZuoPai != null) {
			newList.add(dongZuoPai);
		}
		Collections.sort(newList);
		return newList;
	}

	/**
	 * 移除手牌
	 * 
	 * @param list
	 * @param action
	 */
	public static void removePai(List<Integer> list, Integer pai, int needRemovenum) {
		int num = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) == pai) {
				num++;
				list.remove(i);
				if (num == needRemovenum) {
					break;
				}
			}
		}
	}

	/**
	 * 移除出牌玩家的出牌集合
	 * 
	 * @param chuUserId
	 * @param players
	 */
	public static void removeCPG(Long chuUserId, List<Player> players) {
		for (Player player : players) {
			if (player.getUserId().equals(chuUserId)) {
				List<Integer> chuList = player.getChuList();
				chuList.remove(chuList.size() - 1);
			}
		}

	}

	/**
	 * currentMjList中移除集合needRemoveList
	 * 
	 * @param currentMjList
	 * @param chi
	 */
	public static void removeList(List<Integer> currentMjList, List<Integer> needRemoveList) {
		for (int j = needRemoveList.size() - 1; j >= 0; j--) {
			for (int i = currentMjList.size() - 1; i >= 0; i--) {
				if (needRemoveList.get(j) == currentMjList.get(i)) {
					currentMjList.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * 查找currentMjList中num张pai
	 * 
	 * @param currentMjList
	 * @param pai
	 * @return
	 */
	public static Integer containMany(List<Integer> currentMjList, Integer pai) {
		Integer num = 0;
		for (Integer integer : currentMjList) {
			if (pai == integer) {
				num++;
			}
		}
		return num;
	}

	/**
	 * 返回玩家胡的类型
	 * 
	 * @param players
	 * @param winPlayer2
	 * @param room
	 * @return
	 */
	public static List<Integer> checkHuInfo(List<Player> players, Player winPlayer, RoomResp room) {
		// 获取胡的类型的集合
		List<Integer> huInfoList = winPlayer.getShowList();
		// 获取赢得人的手牌
		List<Integer> currentMjList = winPlayer.getCurrentMjList();
		// 排序
		List<Integer> newList = getNewList(currentMjList, null);
		paiXu(newList);
		int[] shouPaiArr = getArrFromList(newList);
		// 获取混牌数量
		Integer hunPai = null;
		Integer kaiPai = null;
		Integer hunNum = 0;
		if (room.getRuleDaiHun().equals(1)) {// 带混
//			if (winPlayer.getHasHun()) {
				hunPai = room.getHunPai();
				kaiPai = room.getDingHunPai();
				// 获取混牌的数量
				hunNum = shouPaiArr[hunPai - 1];
				// 将混牌的位置置为0
				shouPaiArr[hunPai - 1] = 0;
//			}
		}

		// 1:七对
		if (room.getRuleQiDui().equals(1)) {
			if (newList.size() == 14) {// 七对的手牌数量必须满足14张
				if (checkQiDui(shouPaiArr, hunNum, room)) {
					huInfoList.add(Cnst.QIDUI);
				}
			}
		}
		if (room.getRuleJueGang().equals(1)) {// 带绝
			Integer dongZuoPai = null;
			Integer roomAction = room.getRoomAction();

			// 只考虑出牌阶段不用管抢杠阶段,因为那张牌对自己而言肯定是单张(别人杠了都)
			if (roomAction.equals(Cnst.CHECK_TYPE_CHU)) {// 找到别的玩家出的那张牌
				// 找到玩家出的那张牌
				dongZuoPai = room.getLastDongZuoPai();

			}
			List<Integer> pengPaiList = new ArrayList<Integer>();
			List<Action> actionList = winPlayer.getActionList();
			if (actionList != null && actionList.size() > 0) {
				for (Action action : actionList) {
					if (action.getType().equals(2)) {
						pengPaiList.add(action.getExtra());
					}
				}
			}
			if (pengPaiList.size() != 0) {// 检测手中是否有碰的绝
				for (Integer pai : pengPaiList) {
					// if (shouPaiArr[pai - 1] == 1 && pai != dongZuoPai) {
					if (shouPaiArr[pai - 1] == 1) {
						hunNum++;
						shouPaiArr[pai - 1] = 0;
					}
				}
			}
			// 获取手牌中的绝
			for (int i = 0; i < shouPaiArr.length; i++) {
				// if (shouPaiArr[i] == 4 && Integer.valueOf(i + 1) !=
				// dongZuoPai) {
				if (shouPaiArr[i] == 4) {
					shouPaiArr[i] = 0;// 之所以置为0,是因为剩下的3张只能当刻使用,不能分开
					hunNum++;
				}
			}
		}
		// 检测是不是碰碰胡
		if (huInfoList.size() == 0) {// 没有七对,再检测飘
			if (checkPengHu(winPlayer, shouPaiArr, hunNum)) {
				huInfoList.add(Cnst.PIAOHU);
			}
		}
		// 混的数量为0
		if (hunNum == 0) {// 检测是不是穷胡
//			if (huInfoList.size() == 0) {// 没有七对和穷胡
				huInfoList.add(Cnst.QIONGHU);
//			}
		}
		// 如果既没有七对或者飘，也没有穷胡，那么就是平胡
		if (huInfoList.size() == 0) {// 没有七对,再检测飘
			huInfoList.add(Cnst.PINGHU);
		}
		// 如果选推倒胡，并且不开门赢了，只算推倒胡的分数
//		if (room.getRuleTuiDaoHu().equals(1)) {// 房间有推倒胡选项
//			// 是推倒胡
//			if (checkTuiDaoHu(winPlayer)) {
//				// 如果不包含七小对
//				if (!huInfoList.contains(Cnst.QIDUI)) {// 不包含七对--否则还是按七对处理
//					huInfoList.clear();
//					if (hunNum == 0) {// 检测是不是穷胡
//						huInfoList.add(Cnst.QIONGHU);
//					} else {
//						huInfoList.add(Cnst.PINGHU);
//					}
//					// huInfoList.add(Cnst.TUIDAOHU);
//				}
//			}
//		}

		// 新规定,推倒胡要是满足清一色 也要计算清一色
		// 检测清一色
		if (room.getRuleQingYiSe().equals(1)) {// 房间有清一色规则
			if (checkQingYiSe(winPlayer, room)) {
				huInfoList.add(Cnst.QINGYISE);
			}
		}
		return huInfoList;
	}

	/**
	 * 检测有没有墙头绝
	 * 
	 * @param winPlayer
	 * @param kaiPai
	 * @return 1 明杠 2 暗杠 0没杠
	 */
	private static Integer checkQiangTouJue(Player winPlayer, Integer kaiPai) {
		 List<Action> actionList = winPlayer.getActionList();
//		 1：执行的动作中有没有碰的此三张
		 if (actionList != null && actionList.size() > 0) {
		 for (Action action : actionList) {
		 if (action.getType().equals(3)) {// 碰的是开的那张牌
		 if (action.getExtra().equals(kaiPai)) {
		 return 1;
		 }
		 }
		 }
		 }
		// 2：若没有,手牌中有没有此三张
		List<Integer> currentMjList = winPlayer.getCurrentMjList();
		int[] arrFromList = getArrFromList(currentMjList);
		if (arrFromList[kaiPai - 1] == 3) {
			if(winPlayer.getIsHu() && winPlayer.getIsZiMo() == false){
				if(winPlayer.getCurrentMjList().get(winPlayer.getCurrentMjList().size()-1).equals(kaiPai)){
					return 1;
				}
			}
			return 2;
		}
		return 0;
	}

	/**
	 * 通过混牌获取开门的牌 --已检测
	 * 
	 * @param hunPai
	 * @return
	 */
	public static Integer getKaiPaiFromHunPai(Integer hunPai) {
		Integer kaiPai = null;
		if (hunPai <= 27) {// 万饼条
			if (hunPai % 9 == 1) {
				kaiPai = hunPai + 8;
			} else {
				kaiPai = hunPai - 1;
			}
		} else if (hunPai <= 31) {// 风
			if (hunPai == 28) {
				kaiPai = 31;
			} else {
				kaiPai = hunPai - 1;
			}
		} else if (hunPai <= 34) {// 字
			if (hunPai == 32) {
				kaiPai = 34;
			} else {
				kaiPai = hunPai - 1;
			}
		}
		return kaiPai;
	}

	/**
	 * 通过混牌获取开门的牌 --已检测
	 * 
	 * @param hunPai
	 * @return
	 */
	public static Integer getHunPaiFromKaiPai(Integer kaiPai) {
		Integer hunPai = null;
		if (kaiPai <= 27) {// 万饼条
			if (kaiPai % 9 == 0) {
				hunPai = kaiPai - 8;
			} else {
				hunPai = kaiPai + 1;
			}
		} else if (kaiPai <= 31) {// 风
			if (kaiPai == 31) {
				hunPai = 28;
			} else {
				hunPai = kaiPai + 1;
			}
		} else if (kaiPai <= 34) {// 字
			if (kaiPai == 34) {
				hunPai = 32;
			} else {
				hunPai = kaiPai + 1;
			}
		}
		return hunPai;
	}

	/**
	 * 检测玩家是不是推倒胡
	 * 
	 * @param winPlayer
	 * @return
	 */
	private static boolean checkTuiDaoHu(Player winPlayer) {
		List<Action> actionList = winPlayer.getActionList();
		if (actionList != null && actionList.size() > 0) {
			for (Action action : actionList) {// 暗杠 风杠 和 长毛
				if (action.getType() == 1 || action.getType() == 2 || action.getType() == 3 || action.getType() == 4 || action.getType() == 7) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 检测玩家是不是清一色
	 * 
	 * @param winPlayer
	 * @param room
	 */
	private static Boolean checkQingYiSe(Player winPlayer, RoomResp room) {
		List<Action> actionList = winPlayer.getActionList();
		Integer paiSe = null;
		// 检测动作
		if (actionList != null && actionList.size() > 0) {
			for (Action action : actionList) {
				if (action.getExtra() != null && action.getExtra() < 28) {
					if (paiSe == null) {// 第一次
						paiSe = (action.getExtra() - 1) / 9;
					} else {
						if (paiSe != (action.getExtra() - 1) / 9) {
							return false;
						}
					}
				}
			}
		}
		// 检测手牌--首先移除混牌
		List<Integer> currentMjList = winPlayer.getCurrentMjList();
		// 排序
		List<Integer> newList = getNewList(currentMjList, null);
		paiXu(newList);
		int[] shouPaiArr = getArrFromList(newList);
		// 混牌
		Integer hunPai = null;
		if (room.getRuleDaiHun().equals(1)) {// 带混
			hunPai = room.getHunPai();
			// 将混牌的位置置为0
			shouPaiArr[hunPai - 1] = 0;
		}
		// 检测手中
		for (int i = 0; i < shouPaiArr.length; i++) {
			if (i < 27 && shouPaiArr[i] > 0) {
				if (paiSe == null) {
					paiSe = i / 9;
				} else {
					if (paiSe != i / 9) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 检测是不是碰碰胡
	 * 
	 * @param winPlayer
	 * @param shouPaiArr
	 * @param hunNum
	 * @return
	 */
	private static boolean checkPengHu(Player winPlayer, int[] shouPaiArr, Integer hunNum) {
		List<Action> actionList = winPlayer.getActionList();
		// 1：动作里面不能有吃
		if (actionList != null && actionList.size() > 0) {
			for (Action action : actionList) {
				if (action.getType().equals(1)) {
					return false;
				}
			}
		}
		int oneNum = 0;
		int twoNum = 0;
		// 2： 剩下的牌必须组成砰砰
		for (int i : shouPaiArr) {
			if (i == 4) {
				return false;
			} else if (i == 1) {// 1
				oneNum++;
			} else if (i == 2) {
				twoNum++;
			}
		}
		if ((twoNum - 1 + 2 * oneNum) <= hunNum) {
			return true;
		}
		return false;
	}

	/**
	 * 此方法会计算绝头杠的分 获取玩家的杠分，并保存再玩家的gangScore属性中 ---在点炮的时候，别人出的那张牌不算绝
	 * 
	 * @param players
	 * @param room
	 */
	public static void jiSuanGangScore(List<Player> players, RoomResp room) {
		Integer allGangScore = 0;
		Integer gangScore = 0;
		Integer hunPai = null;
		Integer dingHunPai = null;
		if (room.getRuleDaiHun().equals(1)) {
			hunPai = room.getHunPai();
			dingHunPai = room.getDingHunPai();
		}
		Integer dongZuoPai = null;
		if (room.getRoomAction().equals(Cnst.CHECK_TYPE_CHU)) {// 出牌类型--说明是点炮
			if (room.getRuleJueGang().equals(1)) {// 有绝选项--记录动作拍
				dongZuoPai = room.getLastDongZuoPai();
			}
		}
		for (Player player : players) {
			List<Integer> currentMjList = player.getCurrentMjList();
			int[] shouPaiArr = getArrFromList(currentMjList);
			// 获取普通杠的分
			gangScore = player.getGangScore();// 初始化的时候为0
			if (hunPai != null) {// 防止将4个混牌当成绝
				//如果手里有四个混牌,先结算暗杠分,再滞成0
				if(shouPaiArr[hunPai-1]==4){
					gangScore+=4;
				}
				shouPaiArr[hunPai - 1] = 0;
			}
			// 获取玩家的动作信息
			List<Action> actionList = player.getActionList();
			// 获取玩家没有混牌的手牌
			Integer mingJueNum = 0;
			Integer anJueNum = 0;
			if (room.getRuleJueGang().equals(1)) {// 有绝选项--输的玩家添加杠，赢得玩家添加绝数量
				for (int i = 0; i < shouPaiArr.length; i++) {
					if (shouPaiArr[i] == 4) {// 对于四张的
						// if (player.getIsHu()) {// 算胡分里面,不做处理,最后设置到玩家的绝数量里面
						if (Integer.valueOf(i + 1) != dongZuoPai) {
							anJueNum++;
						} else {
							mingJueNum++;
						}
					} else if (shouPaiArr[i] == 1) {// 查找手牌中的碰的集合
						// 获取玩家的碰集合---不用再移除了,走着一遍就没什么用了
						List<Integer> pengList = player.getPengList();
						if (pengList.contains(i + 1)) {// 碰的里面有这张牌,那么可以组成绝头明杠
							if (Integer.valueOf(i + 1) != dongZuoPai) {
								mingJueNum++;
							}
						}
					}
				}
				player.setMingJueNum(mingJueNum);
				player.setAnJueNum(anJueNum);
			}
			if (actionList != null && actionList.size() > 0) {
				for (Action action : actionList) { // 2碰 3点杠 4碰杠
													// 5暗杠,6风杠,7中发白杠,8长毛
					Integer type = action.getType();
					if (type.equals(3) || type.equals(4) || type.equals(7)) {// 明杠
																				// //
																				// 和中发白
						gangScore += 2;
						// 检测镢头明杠
						if (dingHunPai != null) {
							if (type.equals(4)) {
								if (action.getExtra().equals(dingHunPai)) {
									player.setHasQiangTouJue(true);
								}
							}
						}
					} else if (type.equals(5) || type.equals(6)) {// 暗杠
						gangScore += 4;
					} else if (type.equals(8)) {// 长毛
						if (action.getActionId() < 131) {// 东南西北
							gangScore += 2;
						} else {
							gangScore += 1;
						}
					}
				}
			}
			// 获取墙头绝(开的那三张牌)的杠分
			if (dingHunPai != null) {
				Integer qiangTouJueGang = checkQiangTouJue(player, dingHunPai);
				if(qiangTouJueGang.equals(1)){//明杠
					player.setHasQiangTouJue(true);
					gangScore+=2;
				}else
				if (qiangTouJueGang.equals(2)) {// 暗杠
					player.setHasQiangTouJue(true);
					gangScore += 4;
				}
			}
			// 杠是3家付,只获取自己赢得分
			player.setGangScore((mingJueNum * 2 + anJueNum * 4 + gangScore) * 3);
			allGangScore += player.getGangScore();
		}

		for (Player player1 : players) {
			gangScore = player1.getGangScore();
			player1.setGangScore(gangScore - (allGangScore - gangScore) / 3);
		}
	}

	/**
	 * 
	 * 流局结算和同意解散房间来结算玩家的杠分 此方法不会计算绝头杠的分
	 * 
	 * @param players
	 * @param room
	 */
	public static void jieSanGangScore(List<Player> players, RoomResp room) {
		Integer allGangScore = 0;
		Integer gangScore = 0;
		Integer hunPai = null;
		Integer dingHunPai = null;
		if (room.getRuleDaiHun().equals(1)) {
			hunPai = room.getHunPai();
			dingHunPai = room.getDingHunPai();
		}
		for (Player player : players) {
			List<Integer> currentMjList = player.getCurrentMjList();
			int[] shouPaiArr = getArrFromList(currentMjList);
			gangScore = player.getGangScore();// 初始化的时候为0
			if (hunPai != null) {// 防止将4个混牌当成绝
				//如果手里有四个混牌,先结算暗杠分,再滞成0
				if(shouPaiArr[hunPai-1]==4){
					gangScore+=4;
				}
				shouPaiArr[hunPai - 1] = 0;
			}
			// 获取玩家的动作信息
			List<Action> actionList = player.getActionList();
			// 获取普通杠的分
			if (actionList != null && actionList.size() > 0) {
				for (Action action : actionList) { // 3点杠 4碰杠 5暗杠,6风杠,7中发白杠,8长毛
					if (action.getType().equals(3) || action.getType().equals(4) || action.getType().equals(7)) {// 明杠//
																													// 和中发白
						gangScore += 2;
						// 检测镢头明杠
						if (dingHunPai != null) {
							if (action.getType().equals(3)) {
								if (action.getExtra().equals(dingHunPai)) {
									player.setHasQiangTouJue(true);
								}
							}
						}
					} else if (action.getType().equals(5) || action.getType().equals(6)) {// 暗杠
						gangScore += 4;
					} else if (action.getType().equals(8)) {// 长毛
						if (action.getActionId() < 131) {// 东南西北
							gangScore += 2;
						} else {
							gangScore += 1;
						}
					}
				}
			}
			// 获取墙头绝(开的那三张牌)的杠分
			if (dingHunPai != null) {
				Integer qiangTouJueGang = checkQiangTouJue(player, dingHunPai);
				if (qiangTouJueGang.equals(2)) {// 暗杠
					player.setHasQiangTouJue(true);
					gangScore += 4;
				}
			}
			// 杠是3家付,只获取自己赢得分
			player.setGangScore(gangScore * 3);
			allGangScore += gangScore * 3;
		}
		for (Player player1 : players) {
			gangScore = player1.getGangScore();
			// (allGangScore-gangScore)/3是自己输的分
			player1.setGangScore(gangScore - (allGangScore - gangScore) / 3);
			// 更改玩家的分数
			player1.setScore(player1.getScore() + player1.getGangScore());
		}
	}

	/**
	 * 获取玩家的铺分 铺分： 对于赢的人：只能自己和输得人(有没有铺分有关系)有关系 对于输的人：只能自己和赢得人(有没有铺分有关系)有关系
	 * 
	 * @param players
	 * @param winPlayer
	 */
	public static void getPlayerPufen(List<Player> players, Player winPlayer) {
		// 获取玩家选的铺分(0或者1)
		Integer winPuFenBase = winPlayer.getPuFen();
		Integer winPuFenScore = winPlayer.getPuFenScore();
		winPuFenScore = winPuFenBase * 3;// 对于自己的话赢3北

		for (Player player : players) {
			if (!player.getUserId().equals(winPlayer.getUserId())) {// 对于输的玩家
				// 获取玩家选的铺分(0或者1)
				Integer losePuFenBase = player.getPuFen();
				Integer losePuFenScore = player.getPuFenScore();
				// 输的人输掉： 赢得玩家选的铺分基础分和自己选的铺分基础分
				losePuFenScore = -losePuFenBase - winPuFenBase;
				// 赢得人铺总分加上此玩家选择的铺分基础分
				winPuFenScore += losePuFenBase;
				// 设置输的玩家这把输掉的铺总分
				player.setPuFenScore(losePuFenScore);
			}
		}
		// 设置赢的玩家这把赢的铺总分
		winPlayer.setPuFenScore(winPuFenScore);
	}

	public static List<Integer> faPaiQiDui() {
		List<Integer> qiDuiPai = new ArrayList<Integer>();
		qiDuiPai.add(1);
		qiDuiPai.add(1);
		qiDuiPai.add(2);
		qiDuiPai.add(2);
		qiDuiPai.add(3);
		qiDuiPai.add(3);
		qiDuiPai.add(4);
		qiDuiPai.add(4);
		qiDuiPai.add(5);
		qiDuiPai.add(5);
		qiDuiPai.add(6);
		qiDuiPai.add(6);
		qiDuiPai.add(7);
		return qiDuiPai;
	}

	public static Integer getHuLeiXingScoreFromHuXingList(List<Integer> huInfoList) {
		Integer huLeiXingScore = 0;
		for (Integer integer : huInfoList) {
			if (huLeiXingScore == 0) {
				huLeiXingScore = Cnst.huScoreMap.get(integer);
			} else {
				huLeiXingScore *= Cnst.huScoreMap.get(integer);
			}
		}
		return huLeiXingScore;
	}

	public static List<Integer> faGang() {
		List<Integer> qiDuiPai = new ArrayList<Integer>();
		qiDuiPai.add(1);
		qiDuiPai.add(11);
		qiDuiPai.add(3);
		qiDuiPai.add(3);
		qiDuiPai.add(11);
		qiDuiPai.add(1);
		qiDuiPai.add(1);
		qiDuiPai.add(1);
		qiDuiPai.add(3);
		qiDuiPai.add(11);
		qiDuiPai.add(3);
		qiDuiPai.add(11);
		qiDuiPai.add(12);
		return qiDuiPai;
	}

	public static Integer randow() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 27; i <= 34; i++) {
			list.add(i);
		}
		Random r = new Random();
		int nextInt = r.nextInt(7);
		return list.get(nextInt);
	}

}
