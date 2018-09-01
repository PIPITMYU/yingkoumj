package com.yzt.logic.mj.function;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yzt.logic.mj.domain.Action;
import com.yzt.logic.mj.domain.ClubInfo;
import com.yzt.logic.mj.domain.DissolveRoom;
import com.yzt.logic.mj.domain.Player;
import com.yzt.logic.mj.domain.RoomResp;
import com.yzt.logic.util.BackFileUtil;
import com.yzt.logic.util.Cnst;
import com.yzt.logic.util.MahjongUtils;
import com.yzt.logic.util.RoomUtil;
import com.yzt.logic.util.GameUtil.JieSuan;
import com.yzt.logic.util.GameUtil.StringUtils;
import com.yzt.logic.util.redis.RedisUtil;
import com.yzt.netty.client.WSClient;
import com.yzt.netty.util.MessageUtils;

/**
 * Created by Administrator on 2017/7/13. 游戏中
 */

public class GameFunctions extends TCPGameFunctions {
	final static Object object = new Object();

	/**
	 * 用户点击准备，用在小结算那里，
	 * 
	 * @param session
	 * @param readData
	 */
	public synchronized static void interface_100200(WSClient channel, Map<String, Object> readData) {
		logger.info("准备,interfaceId -> 100200");

		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Long userId = StringUtils.parseLong(readData.get("userId"));
		Integer roomId = StringUtils.parseInt(readData.get("roomId"));

		RoomResp room = RedisUtil.getRoomRespByRoomId(String.valueOf(roomId));
		Player currentPlayer = null;
		List<Player> players = RedisUtil.getPlayerList(room);
		for (Player p : players) {
			if (p.getUserId().equals(userId)) {
				currentPlayer = p;
				break;
			}
		}

		if (room.getState() == Cnst.ROOM_STATE_GAMIING) {
			return;
		}
		currentPlayer.initPlayer(currentPlayer.getRoomId(), Cnst.PLAYER_STATE_PREPARED, currentPlayer.getScore());

		boolean allPrepared = true;

		for (Player p : players) {
			if (!p.getPlayStatus().equals(Cnst.PLAYER_STATE_PREPARED)) {
				allPrepared = false;
			}
		}

		if (allPrepared && players != null && players.size() == 4) {
			if (room.getRoomType() == Cnst.ROOM_TYPE_2) {
				MessageFunctions.interface_100112(null, room, Cnst.PLAYER_EXTRATYPE_KAIJU);
			}
			// 如果选择铺分选项--进入铺分阶段
			if (room.getRulePuFen().equals(1)) {
				// room.setState(Cnst.ROOM_STATE_PUFEN);
				// // 更新缓存数据
				// RedisUtil.setPlayersList(players);
				// RedisUtil.updateRedisData(room, null);
				// Map<String, Object> info = new HashMap<String, Object>();
				// List<Map<String, Object>> userInfo = new
				// ArrayList<Map<String, Object>>();
				// for (Player p : players) {
				// Map<String, Object> i = new HashMap<String, Object>();
				// i.put("userId", p.getUserId());
				// i.put("playStatus", p.getPlayStatus());
				// userInfo.add(i);
				// }
				// Map<String, Object> roominfo = new HashMap<String, Object>();
				// roominfo.put("state", room.getState());
				// info.put("roomInfo", roominfo);
				// info.put("userInfo", userInfo);
				// JSONObject result = getJSONObj(interfaceId, 1, info);
				// for (Player p : players) {
				// WSClient ws = getWSClientManager().getWSClient(
				// p.getChannelId());
				// if (ws == null)
				// continue;
				// MessageUtils.sendMessage(ws, result.toJSONString());
				// }
				// return;
				// 需求更改,直接所有人的铺分状态改为已经铺分
				for (Player player : players) {
					player.setPuFen(1);
				}
			}
			startGame(room, players);
			BackFileUtil.save(interfaceId, room, players, null, null);// 写入文件内容
		}
		Map<String, Object> info = new HashMap<String, Object>();
		List<Map<String, Object>> userInfo = new ArrayList<Map<String, Object>>();
		for (Player p : players) {
			Map<String, Object> i = new HashMap<String, Object>();
			i.put("userId", p.getUserId());
			i.put("playStatus", p.getPlayStatus());
			userInfo.add(i);
		}
		info.put("userInfo", userInfo);
		Map<String, Object> roominfo = new HashMap<String, Object>();
		roominfo.put("state", room.getState());
		info.put("roomInfo", roominfo);
		for (Player p : players) {
			WSClient ws = getWSClientManager().getWSClient(p.getChannelId());
			if (ws == null)
				continue;
			if (room.getState() == Cnst.ROOM_STATE_GAMIING) {
				// 加入庄的玩家id
				info.put("nextActionUserId", room.getZhuangId());
				info.put("nextAction", room.getCurrentActions());
				info.put("pais", p.getCurrentMjList());
				if (room.getRuleDaiHun().equals(1)) {// 房间选择带混规则
					info.put("dingHunPai", room.getDingHunPai());
				}
			}
			JSONObject result = getJSONObj(interfaceId, 1, info);
			MessageUtils.sendMessage(ws, result.toJSONString());
		}
		RedisUtil.setPlayersList(players);
		RedisUtil.updateRedisData(room, null);
	}

	/**
	 * 铺分接口 铺分状态才会进入----不要了
	 * 
	 * @param channel
	 * @param readData
	 */
//	public static void interface_100202(WSClient channel, Map<String, Object> readData) {
//		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
//		Integer roomId = StringUtils.parseInt(readData.get("roomId"));
//		Integer userId = StringUtils.parseInt(readData.get("userId"));
//		Integer puFen = StringUtils.parseInt(readData.get("puFen"));
//		if (userId == null || roomId == null || puFen == null) {
//			return;
//		}
//		Player player = RedisUtil.getPlayerByUserId(userId + "");
//		// 没有此玩家或者玩家没有房间号
//		if (player == null || player.getRoomId() == null) {
//			return;
//		}
//		// 房间号不一致
//		if (!player.getRoomId().equals(roomId)) {
//			return;
//		}
//		player.setPuFen(puFen);
//		// 更新缓存
//		RedisUtil.setPlayerByUserId(player);
//		// 获取房间信息
//		RoomResp room = RedisUtil.getRoomRespByRoomId(String.valueOf(roomId));
//		List<Player> players = RedisUtil.getPlayerList(room);
//		Boolean allPu = true;
//		Integer puNum = 0;
//		for (Player p : players) {
//			if (p.getPuFen() == null) {
//				allPu = false;
//			} else {
//				puNum++;
//			}
//		}
//		if (allPu) {
//			GameFunctions.startGame(room, players);
//			BackFileUtil.save(100200, room, players, null, null);// 写入文件内容
//		}
//		// 封装玩家信息
//		Map<String, Object> info = new HashMap<String, Object>();
//		List<Map<String, Object>> userInfo = new ArrayList<Map<String, Object>>();
//		for (Player p : players) {
//			Map<String, Object> i = new HashMap<String, Object>();
//			i.put("userId", p.getUserId());
//			if (p.getPuFen() != null) {
//				i.put("hasPuFen", p.getPuFen());
//			} else {
//				// 没铺
//				i.put("hasPuFen", -1);
//			}
//			userInfo.add(i);
//		}
//		info.put("userInfo", userInfo);
//		// 封装房间信息
//		Map<String, Object> roominfo = new HashMap<String, Object>();
//		roominfo.put("state", room.getState());
//		info.put("roomInfo", roominfo);
//		info.put("num", puNum);
//		for (Player p : players) {
//			WSClient ws = getWSClientManager().getWSClient(p.getChannelId());
//			if (ws == null)
//				continue;
//			if (room.getState() == Cnst.ROOM_STATE_GAMIING) {
//				// 加入庄的玩家id
//				info.put("nextActionUserId", room.getZhuangId());
//				info.put("nextAction", room.getCurrentActions());
//				info.put("pais", p.getCurrentMjList());
//				info.put("hunPai", room.getHunPai());
//			}
//			JSONObject result = getJSONObj(interfaceId, 1, info);
//			MessageUtils.sendMessage(ws, result.toJSONString());
//		}
//		RedisUtil.setPlayersList(players);
//		RedisUtil.updateRedisData(room, null);
//	}

	/**
	 * 开局发牌
	 * 
	 * @param roomId
	 */
	public static void startGame(RoomResp room, List<Player> players) {
		// 局数统计
		room.setXiaoJuNum(room.getXiaoJuNum() == null ? 1 : room.getXiaoJuNum() + 1);
		room.setXjst(System.currentTimeMillis());
		room.setState(Cnst.ROOM_STATE_GAMIING);
		room.setRoomAction(Cnst.CHECK_TYPE_MO);
		// 获得所需要的牌型
		room.setCurrentMjList(MahjongUtils.getPais());
		// 发牌
		Player zhuangPlayer = null;
		List<Integer> roomMjList = room.getCurrentMjList();
		for (Player p : players) {
			p.setPlayStatus(Cnst.PLAYER_STATE_GAME);// 游戏中..
			if (room.getZhuangId().equals(p.getUserId())) {
				zhuangPlayer = p;
				p.setZhuangNum(p.getZhuangNum() == null ? 1 : p.getZhuangNum() + 1);// 坐庄的次数
				room.setLastFaUserId(p.getUserId());
				// p.setCurrentMjList(MahjongUtils.faGang());
				// 风向为庄家的风向
				p.setCurrentMjList(MahjongUtils.faPai(roomMjList, 14));
				room.setPosition(p.getPosition());
			} else {
				// p.setCurrentMjList(MahjongUtils.faPaiQiDui());
				p.setCurrentMjList(MahjongUtils.faPai(roomMjList, 13));
			}
		}
		// 定混牌 (不包含东南西北白发) 把混牌从牌墩中去除.
		if (room.getRuleDaiHun().equals(1)) {
			// 开的牌会减1
			Integer kaiPai = MahjongUtils.faPai(roomMjList, 1).get(0);
			// 测试使用
//			 Integer kaiPai = 32;
			room.setDingHunPai(kaiPai);
			// 通过开的牌获取混牌
			Integer hunPai = MahjongUtils.getHunPaiFromKaiPai(kaiPai);
			room.setHunPai(hunPai);
		}
		// 3.1看庄家有没有暗杠;2：带混检测, 有没有胡牌;3：若是有旋风杠,会不会有旋风杠
		MahjongUtils.checkActions(players, zhuangPlayer, room, Cnst.CHECK_TYPE_MO, null);
		// 以后动作房间里面也要存一份
		room.setCurrentPlayerUserId(zhuangPlayer.getUserId());
		room.setCurrentActions(zhuangPlayer.getCurrentActions());
		// 更新缓存数据
		RedisUtil.setPlayersList(players);
		RedisUtil.updateRedisData(room, null);
		// 设置最后发牌人
		if (room.getXiaoJuNum() == 1) {
			// 获取此俱乐部当天活跃总人数
			if (String.valueOf(room.getRoomId()).length() == 7) {// 说明是俱乐部
				// 今日活跃数 key: cid value ：userId的集合
				Long timesmorning = StringUtils.getTimesmorning();
				Long scard = RedisUtil.scard(room.getClubId() + "_".concat(timesmorning + ""));
				int dieTime = Cnst.REDIS_CLUB_DIE_TIME;
				if (scard == null || scard == 0l) {// 当天没人,有人最少为5
					// 创建一个并设置过期时间(其中1l为假数据)--昨日和前日
					// 假数据主要是为了设置过期时间--此时间只设置一次
					RedisUtil.sadd(room.getClubId() + "_".concat(timesmorning + ""), 1l, dieTime);
					for (Long userId : room.getPlayerIds()) {
						RedisUtil.sadd(room.getClubId() + "_".concat(timesmorning + ""), userId, null);
					}
				} else {// 有人
					for (Long userId : room.getPlayerIds()) {
						RedisUtil.sadd(room.getClubId() + "_".concat(timesmorning + ""), userId, null);
					}
				}
				// 今日俱乐部局数 --昨日和前日
				Integer clubId = room.getClubId();
				Integer todayJuNum = RedisUtil.getTodayJuNum(clubId + "".concat(timesmorning + ""));
				if (todayJuNum == null || todayJuNum == 0) {
					RedisUtil.setTodayJuNum(clubId + "".concat(timesmorning + ""), 1, dieTime);
				} else {
					RedisUtil.setTodayJuNum(clubId + "".concat(timesmorning + ""), 1 + todayJuNum, dieTime);
				}
				Long[] playerIds = room.getPlayerIds();
				// 今日玩家局数 --保存一天
				Integer juNum = null;
				for (Long playerId : playerIds) {
					// key clubId+userId+今天早上时间
					juNum = RedisUtil.getObject(
							Cnst.REDIS_CLUB_TODAYJUNUM_ROE_USER.concat(clubId + "_").concat(playerId + "").concat(timesmorning + ""), Integer.class);
					if (juNum == null || juNum == 0) {
						RedisUtil.setObject(Cnst.REDIS_CLUB_TODAYJUNUM_ROE_USER.concat(clubId + "_").concat(playerId + "").concat(timesmorning + ""),
								1, Cnst.REDIS_CLUB_PLAYERJUNUM_TIME);
					} else {
						RedisUtil.setObject(Cnst.REDIS_CLUB_TODAYJUNUM_ROE_USER.concat(clubId + "_").concat(playerId + "").concat(timesmorning + ""),
								juNum + 1, Cnst.REDIS_CLUB_PLAYERJUNUM_TIME);
					}
				}
				RedisUtil.hdel(Cnst.REDIS_CLUB_ROOM_LIST.concat(String.valueOf(room.getClubId())), String.valueOf(room.getRoomId()));
			}

			RoomUtil.addRoomToDB(room);
			RoomUtil.removeFreeRoomTask(StringUtils.parseLong(room.getRoomId()));
		}
	}

	/**
	 * 出牌 行为编码(游戏内主逻辑)
	 * 
	 * @param wsClient
	 * @param readData
	 */
	public synchronized static void interface_100201(WSClient channel, Map<String, Object> readData) {
		logger.info("游戏内主逻辑,interfaceId -> 100201");

		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Integer action = StringUtils.parseInt(readData.get("action")); // 行为编号,牌的信息
		Integer roomId = StringUtils.parseInt(readData.get("roomId")); // 房间号
		Long userId = StringUtils.parseLong(readData.get("userId")); // 玩家ID
		Integer wsw_sole_action_id = StringUtils.parseInt(readData.get("wsw_sole_action_id")); // 每局的验证id
		RoomResp room = RedisUtil.getRoomRespByRoomId(String.valueOf(roomId));
		if (room == null) {
			return;
		}
		if (!room.getWsw_sole_action_id().equals(wsw_sole_action_id)) {
			return;
		}
		room.setWsw_sole_action_id(wsw_sole_action_id + 1);
		Integer state = room.getState();
		if (state == null) {
			return;
		} else {
			// 只要不是游戏阶段就直接不用理会,防止解散房间的时候请求动作报错
			if (!room.getState().equals(Cnst.ROOM_STATE_GAMIING)) {
				return;
			}
		}
		List<Player> players = RedisUtil.getPlayerList(room);
		Player currentPlayer = null;
		for (Player p : players) {
			if (p.getUserId().equals(userId)) {
				currentPlayer = p;
				break;
			}
		}
		if (currentPlayer == null) {
			return;
		}
		List<Integer> currentMjList = currentPlayer.getCurrentMjList();
		Action ac = null;

		// 屏蔽多次请求
		if (action != null && room != null) {
			// 屏蔽是不是当前动作玩家的请求玩家
			if (!room.getCurrentPlayerUserId().equals(userId)) {
				return;
			}
			List<Integer> currentActions = room.getCurrentActions();
			if (currentActions.get(0).equals(Cnst.CHU_ACTION)) {// 出牌
				if (action > 34 || action < 1) {// 出的是1到34
					illegalRequest(interfaceId, channel);
					return;
				}
			} else {
				if (!room.getCurrentActions().contains(action)) {
					illegalRequest(interfaceId, channel);
					return;
				}
			}
		}
		// 判断动作
		in: if (action == -1) {// 发牌
			logger.info("-1 请求发牌!");
			List<Integer> roomMJList = room.getCurrentMjList();
			if (roomMJList.size() == 0) {// 房间没牌了,荒庄
				// 设置空下一个动作人
				room.setCurrentPlayerUserId(null);
				room.setState(Cnst.ROOM_STATE_XJS);// 对面会根据小结算状态请求小结算
				room.setHuangZhuang(true);// 进入小结算后,根据此状态判断是不是荒庄
			} else {
				if ((currentMjList.size() - 2) % 3 == 0) {
					logger.error("不能再发牌了");
					return;
				}
				// 设置上一个动作人是自己，动作是摸牌
				room.setLastPlayerUserId(userId);
				// 风向
				room.setPosition(currentPlayer.getPosition());

				currentPlayer.setLastAction(Cnst.MO_ACTION);
				room.setLastAction(Cnst.MO_ACTION);
				room.setLastFaUserId(userId);
				Integer dongZuoPai = MahjongUtils.faPai(roomMJList, 1).get(0);
				// Integer dongZuoPai = 7;
				ac = new Action(Cnst.ACTION_TYPE_FAPAI, action, userId, null, dongZuoPai);
				room.setLastDongZuoPai(dongZuoPai);
				// 当前动作玩家的牌库加上此牌
				currentMjList.add(dongZuoPai);
				if (roomMJList.size() <= 3) {// 进入海底阶段：只检测胡,不胡下家摸牌,直到没牌位置
				// room.setState(Cnst.ROOM_STATE_HAILAO);
					room.setRoomAction(Cnst.CHECK_TYPE_HAILAO);
					Player actionPlayer = MahjongUtils.checkActions(players, currentPlayer, room, Cnst.CHECK_TYPE_HAILAO, null);
					room.setCurrentPlayerUserId(actionPlayer.getUserId());// 海劳需要设置
					break in;
				}
				// 牌数大于4张,仅仅是摸牌
				room.setRoomAction(Cnst.CHECK_TYPE_MO);
				Player actionPlayer = MahjongUtils.checkActions(players, currentPlayer, room, Cnst.CHECK_TYPE_MO, null);
				room.setCurrentPlayerUserId(actionPlayer.getUserId());
			}

		} else if (action == 0) {// 过
			logger.info("过!!!!!!");
			room.setLastAction(0);
			room.setLastPlayerUserId(userId);
			ac = new Action(Cnst.ACTION_TYPE_GUO, action, userId, null, null);
			Long lastPengGangUserId = room.getLastPengGangUser();
			// 获取房间的动作玩家集合
			List<Long> nowPlayerIds = room.getNowPlayerIds();
			// 移除点过的玩家
			nowPlayerIds.remove(userId);
			if (lastPengGangUserId != null) {// 抢杠胡阶段--不用处理,一直指向杠的玩家
				Player gangPlayer = null;
				if (nowPlayerIds.size() == 0) {// 抢杠胡的玩家都点了过
					// 被抢杠的玩家继续执行未完成的杠
					for (Player p : players) {
						if (p.getUserId().equals(lastPengGangUserId)) {// 执行操作
							gangPlayer = p;
							// 将当前动作改为摸牌
							List<Integer> currentActions = p.getCurrentActions();
							currentActions.clear();
							currentActions.add(Cnst.MO_ACTION);
							room.setCurrentActions(currentActions);
							break;
						}
					}
					// 并设置他的下一个动作为发牌
					room.setCurrentPlayerUserId(gangPlayer.getUserId());
					// 抢杠胡标记的玩家滞空
					room.setLastPengGangUser(null);
				} else {
					Player firstActionPlayer = MahjongUtils.getFirstActionPlayer(nowPlayerIds, players, room);
					room.setCurrentPlayerUserId(firstActionPlayer.getUserId());
					room.setCurrentActions(firstActionPlayer.getCurrentActions());
				}
			} else {// 普通流程点过
				if (room.getRoomAction().equals(Cnst.CHECK_TYPE_MO)) {// 当前是摸牌阶段--风向不变
					// 摸牌人自己点过,将自己的动作修改
					List<Integer> currentActions = currentPlayer.getCurrentActions();
					currentActions.clear();
					currentActions.add(Cnst.CHU_ACTION);
					room.setCurrentPlayerUserId(userId);
					room.setCurrentActions(currentActions);
				} else if (room.getRoomAction().equals(Cnst.CHECK_TYPE_CHU)) {// 当前是出牌阶段,风向会改变
					Long actionPlayerId = null;
					// 移除当前动作玩家的动作
					currentPlayer.getCurrentActions().clear();
					Player firstActionPlayer = null;
					if (nowPlayerIds.size() == 0) {// 如果有动作的人都点了过
						// 获取上一个出牌引发动作的人
						Long lastPlayerUserId = room.getLastChuUserId();
						Player player = RedisUtil.getPlayerByUserId(String.valueOf(lastPlayerUserId));
						Integer nextPlayerPosition = MahjongUtils.getNextPlayerPosition(player.getPosition());
						// 风向
						room.setPosition(currentPlayer.getPosition());
						for (Player p : players) {
							if (p.getPosition().equals(nextPlayerPosition)) {// 找到下一个玩家
								firstActionPlayer = p;
								List<Integer> currentActions = p.getCurrentActions();
								currentActions.clear();
								currentActions.add(Cnst.MO_ACTION);
								actionPlayerId = p.getUserId();
							}
						}
					} else {// 风向不变
						firstActionPlayer = MahjongUtils.getFirstActionPlayer(nowPlayerIds, players, room);
						actionPlayerId = firstActionPlayer.getUserId();
					}
					// 设置当前动作玩家
					room.setCurrentPlayerUserId(actionPlayerId);
					room.setCurrentActions(firstActionPlayer.getCurrentActions());
				}// 海劳没有过
			}
		} else if (action == 500) {// 胡 --所有胡次数，点次数，自摸次数在此改变
			logger.info("当前用户选择胡了!!!");
			// 风向
			room.setPosition(currentPlayer.getPosition());
			ac = new Action(Cnst.ACTION_TYPE_HU, action, userId, null, null);
			room.setWinPlayerId(userId);
			currentPlayer.setIsHu(true);
			currentPlayer.setHuNum(currentPlayer.getHuNum() + 1);
			Long lastPengGangUserId = room.getLastPengGangUser();
			if (lastPengGangUserId != null) {// 抢杠胡阶段--杠不成
				Integer pai = null;
				for (Player p : players) {
					// 排序
					if (p.getUserId().equals(lastPengGangUserId)) {// 点炮玩家就是杠的玩家
						// 1：杠转换为碰
						// 2：移除杠的那张手牌
						Integer gangAction = p.getCurrentActions().get(0);
						List<Action> actionList = p.getActionList();
						int size = actionList.size();
						if(gangAction<127){//说明抢的是杠
							pai = MahjongUtils.getPaiListFromAction(gangAction, Cnst.PLAYER_ACTION_GANG).get(0);
							for (int i = 0; i < size; i++) {
								if (actionList.get(i).getActionId().equals(gangAction)) {
									actionList.remove(i);
								}
							}
							Integer actionPeng = MahjongUtils.getActionFromPai(pai, Cnst.PLAYER_ACTION_PENG);
							Action ac2 = new Action(Cnst.ACTION_TYPE_PENG, actionPeng, p.getUserId(), null, pai);
							// 添加碰动作
							actionList.add(ac2);
							// 添加penglist
							p.getPengList().add(pai);
						}else{
							pai = MahjongUtils.getPaiListFromAction(gangAction, Cnst.PLAYER_ACTION_ZHANGMAO).get(0);
							for (int i = 0; i < size; i++) {
								if (actionList.get(i).getActionId().equals(gangAction)) {
									actionList.remove(i);
									//移除一个长得毛
									break;
								}
							}
						}
						p.setDianNum(p.getDianNum() + 1);
						p.setIsDian(true);
					}
					MahjongUtils.paiXu(p.getCurrentMjList());
				}
				// 将胡的牌加入赢得玩家的手牌
				// 此时胡的牌是没杠成功的那张牌
				currentPlayer.getCurrentMjList().add(pai);
				// MahjongUtils.paiXu(currentMjList2);
				// currentMjList2.add(pai);
			} else {// 普通胡阶段
					// 先保留胡的最后一张牌
				if (room.getRoomAction().equals(Cnst.CHECK_TYPE_MO)) {// 当前是摸牌阶段
					// 自摸
					currentPlayer.setZimoNum(currentPlayer.getZimoNum() + 1);
					currentPlayer.setIsZiMo(true);
				} else if (room.getRoomAction().equals(Cnst.CHECK_TYPE_CHU)) {// 当前是出牌阶段
					Long lastChuUserId = room.getLastChuUserId();
					for (Player p : players) {
						// 排序
						MahjongUtils.paiXu(currentPlayer.getCurrentMjList());
						if (p.getUserId().equals(lastChuUserId)) {// 点炮玩家
							p.setDianNum(p.getDianNum() + 1);
							p.setIsDian(true);
						}
					}
					// 将胡的牌加入赢得玩家的手牌
					currentPlayer.getCurrentMjList().add(room.getLastDongZuoPai());
				} else if (room.getRoomAction().equals(Cnst.CHECK_TYPE_HAILAO)) {// 海劳阶段
					currentPlayer.setZimoNum(currentPlayer.getZimoNum() + 1);
					currentPlayer.setIsZiMo(true);
				}
			}
			// 进入小结算 统计玩家数据
			room.setState(Cnst.ROOM_STATE_XJS);
		} else if (action >= 127 && action <= 133) {// 长毛
			ac = new Action(Cnst.ACTION_TYPE_ZHANGMAO, action, userId, null, null);
			Integer pai = MahjongUtils.getPaiListFromAction(action, Cnst.PLAYER_ACTION_ZHANGMAO).get(0);
			MahjongUtils.removePai(currentMjList, pai, 1);
			// 添加到已经进行的动作集合
			currentPlayer.addActionList(ac);
			// 设置上一个动作人和动作
			currentPlayer.setLastAction(action);
			List<Integer> currentActions = currentPlayer.getCurrentActions();
			room.setLastAction(action);
			// 检测他杠的这张牌-对于其他人能否胡 checkActions的方法已经设置了房间当前动作
			Player actionsPlayer = MahjongUtils.checkActions(players, currentPlayer, room, Cnst.CHECK_TYPE_QIANGGANGHU, pai);
			if (actionsPlayer != null) {// 说明有胡的玩家
				room.setLastPengGangUser(userId);
				currentActions.clear();
				currentActions.add(action);// 只给执行杠的玩家留下执行杠的操作---在胡的人都点过的时候让他执行
				// 设置当前玩家和玩家动作
				room.setCurrentPlayerUserId(actionsPlayer.getUserId());
			} else {// 别的人都没有胡的动作,此玩家直接执行碰杠操作就可以了
				room.setCurrentPlayerUserId(userId);
				currentActions.clear();
				currentActions.add(Cnst.MO_ACTION);
				room.setCurrentActions(currentActions);
				room.setLastPlayerUserId(userId);
			}

		} else if (action <= 34 && action >= 1) {// 出牌
			if ((currentMjList.size() - 1) % 3 == 0) {// 出过牌了
				return;
			}
			// 1:选混不能出混
			if (room.getRuleDaiHun().equals(1)) {// 带混
				if (action == room.getHunPai()) {// 出的是混牌
					if (currentPlayer.getHasHun()) {// 玩家有混牌,就报错
						illegalRequest(interfaceId, channel);
						return;
					}
				}
			}
			// 如果手牌中没有这张牌，也不行
			if (!currentMjList.contains(action)) {
				illegalRequest(interfaceId, channel);
				return;
			}
			// 2:选绝,不能出碰的绝(手种的4张绝可以随便出)
			if (room.getRuleJueGang().equals(1)) {// 绝
				List<Action> actionList = currentPlayer.getActionList();
				if (actionList != null && actionList.size() > 0) {// 有动作
					for (Action action2 : actionList) {
						if (action2.getType().equals(2)) {// 碰动作
							if (action2.getExtra().equals(action)) {// 碰的牌和出的牌相同
								illegalRequest(interfaceId, channel);
								return;
							}
						}
					}
				}
			}
			// 因为摸牌的时候没有排序,所以出牌的时候排一下
			MahjongUtils.paiXu(currentMjList);
			room.setLastChuUserId(userId);
			room.setRoomAction(Cnst.CHECK_TYPE_CHU);// 设置房间为出牌状态
			logger.info("1-34中的牌    出牌!!!");
			currentPlayer.setHasChu(true);// 进行判断能否特殊杠

			ac = new Action(Cnst.ACTION_TYPE_CHUPAI, action, userId, null, action);
			// 设置最后出牌的玩家
			room.setLastPlayerUserId(userId);
			currentPlayer.setLastAction(Cnst.CHU_ACTION);
			room.setLastAction(action);
			// 只有出牌的时候设置，最后出的牌，在吃，碰，杠的时候使用
			room.setLastDongZuoPai(action);
			// 移除手牌 添加已出过的牌
			MahjongUtils.removePai(currentPlayer.getCurrentMjList(), action, 1);
			currentPlayer.getChuList().add(action);
			// 找到动作的玩家
			Player checkPlayer = MahjongUtils.checkActions(players, currentPlayer, room, Cnst.CHECK_TYPE_CHU, action);
			room.setCurrentPlayerUserId(checkPlayer.getUserId());
			if (room.getNowPlayerIds().size() != 0) {
				room.setPosition(currentPlayer.getPosition());
			} else {// 别人没动作,那么checkPlayer为下一家
				room.setPosition(checkPlayer.getPosition());
			}

		} else if (action >= 35 && action <= 55) {// 吃
			logger.info("35-55中的牌    我吃了!!!");
			room.setLastPlayerUserId(userId);
			List<Integer> chi = MahjongUtils.getPaiListFromAction(action, Cnst.PLAYER_ACTION_CHI);
			Integer lastChuPai = room.getLastDongZuoPai();
			MahjongUtils.removePai(chi, lastChuPai, 1);
			// 玩家 动作集合
			ac = new Action(Cnst.ACTION_TYPE_CHI, action, currentPlayer.getUserId(), room.getLastChuUserId(), lastChuPai);
			currentPlayer.addActionList(ac);
			currentPlayer.setLastAction(action);
			room.setLastAction(action);
			// 移除手牌中需要配合组成动作的牌
			MahjongUtils.removeList(currentMjList, chi);
			// 移除出牌玩家的出牌集合
			MahjongUtils.removeCPG(room.getLastChuUserId(), players);
			// 吃完是请求出牌---改需求了,若是没出过牌,先检测手里能不能旋风杠,能的话就杠
			room.setCurrentPlayerUserId(userId);
			List<Integer> currentActions = currentPlayer.getCurrentActions();
			currentActions.clear();
			// 进行判断能否特殊杠
			if (!currentPlayer.getHasChu()) {// 没出过牌,检测旋风杠
				if (room.getRuleXuanFeng().equals(1)) {// 带旋风杠
					// 检测旋风杠
					List<Integer> gangList = MahjongUtils.checkXuanFengGang(currentPlayer, room, null);
					if (gangList != null && gangList.size() > 0) {
						// 添加动作信息
						currentActions.addAll(gangList);
					}
				}
			}
			if (currentActions.size() != 0) {// 能旋风杠,添加过
				room.setRoomAction(Cnst.CHECK_TYPE_MO);// 设置房间为摸牌状态--只有摸牌阶段才会检测旋风杠--并且风向指向自己也没问题
				currentActions.add(Cnst.GUO_ACTION);
			} else {
				currentActions.add(Cnst.CHU_ACTION);
			}
			room.setCurrentActions(currentActions);
			room.setLastChuUserId(null);
			room.setPosition(currentPlayer.getPosition());

		} else if (action >= 57 && action <= 90) {// 碰
			logger.info("57-90中的牌    我碰了啊!!!");
			room.setLastPlayerUserId(userId);

			// 玩家 动作集合
			Integer lastChuPai = room.getLastDongZuoPai();
			ac = new Action(Cnst.ACTION_TYPE_PENG, action, currentPlayer.getUserId(), room.getLastChuUserId(), lastChuPai);
			currentPlayer.addActionList(ac);
			currentPlayer.setLastAction(action);
			room.setLastAction(action);
			
			Long lastPengGangUserId = room.getLastPengGangUser();
			// 添加到玩家的碰集合里面
			List<Integer> pengList = currentPlayer.getPengList();
			pengList.add(lastChuPai);
			if (lastPengGangUserId != null) {// 抢杠胡阶段--杠不成
				Integer pai =null;
				for (Player p : players) {
					if(p.getUserId().equals(lastPengGangUserId)){
						Integer gangAction = p.getCurrentActions().get(0);
						pai = MahjongUtils.getPaiListFromAction(gangAction, Cnst.PLAYER_ACTION_ZHANGMAO).get(0);
						List<Action> actionList = p.getActionList();
						int size = actionList.size();
						if (p.getUserId().equals(lastPengGangUserId)) {// 点炮玩家就是杠的玩家
							for (int i = 0; i < size; i++) {
								if (actionList.get(i).getActionId().equals(gangAction)) {
									actionList.remove(i);
									//移除一个长得毛
									break;
								}
							}
						}
					}
				}
				// 移除手牌
				MahjongUtils.removePai(currentMjList, pai, 2);
				room.setLastPengGangUser(null);
			}else{
				// 移除手牌
				MahjongUtils.removePai(currentMjList, lastChuPai, 2);
				// 移除出牌玩家的出牌集合
				MahjongUtils.removeCPG(room.getLastChuUserId(), players);
			}
			// 碰完是请求出牌
			room.setCurrentPlayerUserId(userId);
			List<Integer> currentActions = currentPlayer.getCurrentActions();
			currentActions.clear();
			// 进行判断能否特殊杠
			if (!currentPlayer.getHasChu()) {// 没出过牌,检测旋风杠
				if (room.getRuleXuanFeng().equals(1)) {// 带旋风杠
					// 检测旋风杠
					List<Integer> gangList = MahjongUtils.checkXuanFengGang(currentPlayer, room, null);
					room.setRoomAction(Cnst.CHECK_TYPE_MO);// 设置房间为摸牌状态--只有摸牌阶段才会检测旋风杠--并且风向指向自己也没问题
					if (gangList != null && gangList.size() > 0) {
						// 添加动作信息
						currentActions.addAll(gangList);
					}
				}
			}
			if (currentActions.size() != 0) {// 能旋风杠,添加过
				room.setRoomAction(Cnst.CHECK_TYPE_MO);// 设置房间为摸牌状态--只有摸牌阶段才会检测旋风杠--并且风向指向自己也没问题
				currentActions.add(Cnst.GUO_ACTION);
			} else {
				currentActions.add(Cnst.CHU_ACTION);
			}
			room.setCurrentActions(currentActions);
			room.setLastChuUserId(null);
			room.setPosition(currentPlayer.getPosition());
		} else if (action >= 91 && action <= 126) {// 杠
													// -!!!!!别人出4张牌或者自摸5张牌,明杠的时候,如果不能组成飘，那么不能胡
			logger.info("91-126中的牌    我杠了啊!!!");
			// 设置上次动作的玩家动作和id
			currentPlayer.setLastAction(action);
			room.setLastPlayerUserId(userId);
			room.setLastAction(action);
			List<Integer> gangList = MahjongUtils.getPaiListFromAction(action, Cnst.PLAYER_ACTION_GANG);
			Integer pai = null;
			Integer num = null;
			// 这里需要检测是碰杠 点杠 或 暗杠
			if (action >= 91 && action <= 124) {// 普通4个的杠
				pai = gangList.get(0);
				num = MahjongUtils.containMany(currentMjList, pai);
			}
			if (room.getRoomAction().equals(Cnst.CHECK_TYPE_MO)) {// 摸牌阶段
				List<Integer> currentActions = currentPlayer.getCurrentActions();
				if (pai == null) {// 说明是旋风杠
									// --没出过牌,那么手中的动作一定都是杠,满足碰碰胡,不用设置玩家canHu属性
					// 设置上次动作的玩家动作和id
					// room.setLastPlayerUserId(userId);
					// currentPlayer.setLastAction(action);
					// 移除手牌的杠集合
					MahjongUtils.removeList(currentMjList, gangList);
					// 添加到动作集合里面
					if (action == 125) {// 风杠
						ac = new Action(Cnst.ACTION_TYPE_FENGGANG, action, userId, null, null);
						// 混排是东南西北
						if (room.getRuleDaiHun().equals(1)) {
							Integer hunPai = room.getHunPai();
							if (hunPai <= 31 && hunPai >= 28) {
								currentPlayer.setHasHun(false);
							}
						}
						currentPlayer.addActionList(ac);
						// 设置当前动作人和动作玩家
						room.setCurrentPlayerUserId(userId);
						currentActions.clear();
						currentActions.add(Cnst.MO_ACTION);
					} else {// 中发白杠
						ac = new Action(Cnst.ACTION_TYPE_ZHONGFABAIGANG, action, userId, null, null);
						// 混排是中发白
						if (room.getRuleDaiHun().equals(1)) {
							Integer hunPai = room.getHunPai();
							if (hunPai <= 34 && hunPai >= 32) {
								currentPlayer.setHasHun(false);
							}
						}
						currentPlayer.addActionList(ac);
						// 设置当前动作人和动作玩家
						room.setCurrentPlayerUserId(userId);
						for (int i = 0; i < currentActions.size(); i++) {
							if (currentActions.get(i).equals(action)) {
								currentActions.remove(i);
							}
						}
						if (currentActions.size() == 1) {// 说明只剩下过。
							currentActions.clear();
							// 检测长毛
							if (room.getRuleChangMao().equals(1)) {// 房间有长毛规则
								// 检测长毛
								if (room.getRuleChangMao().equals(1)) {// 玩家选择了长毛
									List<Integer> changMaoList = MahjongUtils.checkZhangMao(currentPlayer, null);
									if (changMaoList != null && changMaoList.size() > 0) {
										currentActions.addAll(changMaoList);
										currentActions.add(Cnst.GUO_ACTION);
									} else {
										// 出牌动作
										currentActions.add(Cnst.CHU_ACTION);
									}
								}

							}
						}
					}
					room.setCurrentActions(currentActions);
				} else {// 碰杠或者暗杠
					if (num == 4) {// 暗杠
					// //设置上次动作的玩家动作和id
					// room.setLastPlayerUserId(userId);
					// currentPlayer.setLastAction(action);
						// 移除手牌的杠集合
						MahjongUtils.removePai(currentMjList, pai, 4);
						// 添加到动作集合里面
						ac = new Action(Cnst.ACTION_TYPE_ANGANG, action, userId, null, pai);
						currentPlayer.addActionList(ac);
						// 设置当前动作人和动作玩家
						room.setCurrentPlayerUserId(userId);
						currentActions.clear();
						currentActions.add(Cnst.MO_ACTION);
						room.setCurrentActions(currentActions);
					} else {// 碰杠--检测抢杠胡
						List<Action> actionList = currentPlayer.getActionList();
						for (int i = actionList.size() - 1; i >= 0; i--) {
							Action an = actionList.get(i);
							if (an.getType().equals(2) && an.getExtra().equals(pai)) {// 如果是碰，并且碰的牌和此杠的牌相同
								actionList.remove(i);// 移除此碰
							}
						}
						// 将玩家的碰集合移除此碰
						List<Integer> pengList = currentPlayer.getPengList();
						for (int i = 0; i < pengList.size(); i++) {
							if (pengList.get(i).equals(pai)) {
								pengList.remove(i);
							}
						}
						MahjongUtils.removePai(currentMjList, pai, 1);
						// 添加杠
						ac = new Action(Cnst.ACTION_TYPE_PENGGANG, action, userId, null, pai);
						currentPlayer.addActionList(ac);
						
						// 检测他杠的这张牌-对于其他人能否胡 checkActions的方法已经设置了房间当前动作
						Player actionsPlayer = MahjongUtils.checkActions(players, currentPlayer, room, Cnst.CHECK_TYPE_QIANGGANGHU, pai);
						if (actionsPlayer != null) {// 说明有胡的玩家
							room.setLastPengGangUser(userId);
							currentActions.clear();
							currentActions.add(action);// 只给执行杠的玩家留下执行杠的操作---在胡的人都点过的时候让他执行
							// 设置当前玩家和玩家动作
							room.setCurrentPlayerUserId(actionsPlayer.getUserId());
						} else {// 别的人都没有胡的动作,此玩家直接执行碰杠操作就可以了
							room.setCurrentPlayerUserId(userId);
							currentActions.clear();
							currentActions.add(Cnst.MO_ACTION);
							room.setCurrentActions(currentActions);
						}
					}
				}
			} else if (room.getRoomAction().equals(Cnst.CHECK_TYPE_CHU)) { // 出牌阶段// --明杠
				if (currentMjList.size() == 4) {
					// 首先检测能否砰砰胡
					boolean pengHu = true;
					// 1获取动作集合，肯定有动作，不然手牌不会为4
					List<Action> actionList = currentPlayer.getActionList();
					for (Action action2 : actionList) {
						if (action2.getType().equals(1)) {
							pengHu = false;
						}
					}
					// 如果不满足碰碰胡，就再也不能胡了
					if (!pengHu) {
						currentPlayer.setCanHu(false);
					}
				}
				// 移除出牌玩家的出牌集合
				MahjongUtils.removeCPG(room.getLastChuUserId(), players);
				ac = new Action(Cnst.ACTION_TYPE_DIANGANG, action, currentPlayer.getUserId(), room.getLastChuUserId(), pai);
				currentPlayer.addActionList(ac);
				List<Integer> currentActions = currentPlayer.getCurrentActions();
				currentActions.clear();
				room.setCurrentPlayerUserId(userId);
				// 先定义杠动作
				ac = new Action(Cnst.ACTION_TYPE_DIANGANG, action, userId, room.getLastChuUserId(), pai);
				// 移除手牌的杠集合
				Integer dingHunPai = room.getDingHunPai();
				boolean needReoveShouPai=true;
				if(dingHunPai!=null){
					if(pai.equals(dingHunPai)){//绝头杠
						MahjongUtils.removePai(currentMjList, pai, 2);
						// 进行判断能否特殊杠
						if (!currentPlayer.getHasChu()) {// 没出过牌,检测旋风杠
							if (room.getRuleXuanFeng().equals(1)) {// 带旋风杠
								// 检测旋风杠
								List<Integer> gangList2 = MahjongUtils.checkXuanFengGang(currentPlayer, room, null);
								room.setRoomAction(Cnst.CHECK_TYPE_MO);// 设置房间为摸牌状态--只有摸牌阶段才会检测旋风杠--并且风向指向自己也没问题
								if (gangList2 != null && gangList2.size() > 0) {
									// 添加动作信息
									currentActions.addAll(gangList2);
								}
							}
						}
						if (currentActions.size() != 0) {// 能旋风杠,添加过
							room.setRoomAction(Cnst.CHECK_TYPE_MO);// 设置房间为摸牌状态--只有摸牌阶段才会检测旋风杠--并且风向指向自己也没问题
							currentActions.add(Cnst.GUO_ACTION);
						} else {
							currentActions.add(Cnst.CHU_ACTION);
						}
						room.setCurrentActions(currentActions);
						room.setLastChuUserId(null);
						//说明应该执行碰操作
						needReoveShouPai=false;
					}
				}
				if(needReoveShouPai){
					MahjongUtils.removePai(currentMjList, pai, 3);
					// 设置当前动作人和动作玩家
					room.setCurrentPlayerUserId(userId);
					currentActions.add(Cnst.MO_ACTION);
					room.setCurrentActions(currentActions);
					room.setLastChuUserId(null);
				}
			}
			room.setPosition(currentPlayer.getPosition());
		}
		for (Player player : players) {
			player.setShowList(new ArrayList<Integer>());
		}
		RedisUtil.setPlayersList(players);
		RedisUtil.updateRedisData(room, null);
		// 写入回放
		if (ac != null) {
			BackFileUtil.save(interfaceId, room, players, null, ac);
		}
		// 小结算
		if (room.getState() == Cnst.ROOM_STATE_XJS) {
			JieSuan.xiaoJieSuan(String.valueOf(roomId));
		}
		// 统一发消息即可 跟据action 来判断
		MessageFunctions.interface_100104(room, players, 100104, ac);
	}

	/**
	 * 玩家申请解散房间
	 * 
	 * @param session
	 * @param readData
	 * @throws Exception
	 */
	public synchronized static void interface_100203(WSClient channel, Map<String, Object> readData) throws Exception {
		logger.info("玩家请求解散房间,interfaceId -> 100203");
		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Integer roomId = StringUtils.parseInt(readData.get("roomId"));
		Long userId = StringUtils.parseLong(readData.get("userId"));
		Long useId2 = channel.getUserId();
		if (userId != null && !useId2.equals(userId)) {
			return;
		}
		RoomResp room = RedisUtil.getRoomRespByRoomId(String.valueOf(roomId));
		if (room.getDissolveRoom() != null) {
			return;
		}
		DissolveRoom dis = new DissolveRoom();
		dis.setDissolveTime(new Date().getTime());
		dis.setUserId(userId);
		List<Map<String, Object>> othersAgree = new ArrayList<>();
		List<Player> players = RedisUtil.getPlayerList(room);
		for (Player p : players) {
			if (!p.getUserId().equals(userId)) {
				Map<String, Object> map = new HashMap<>();
				map.put("userId", p.getUserId());
				map.put("agree", 0);// 1同意；2解散；0等待
				othersAgree.add(map);
			}
		}
		dis.setOthersAgree(othersAgree);
		room.setDissolveRoom(dis);

		Map<String, Object> info = new HashMap<>();
		info.put("dissolveTime", dis.getDissolveTime());
		info.put("userId", dis.getUserId());
		info.put("othersAgree", dis.getOthersAgree());
		JSONObject result = getJSONObj(interfaceId, 1, info);
		for (Player p : players) {
			WSClient ws = getWSClientManager().getWSClient(p.getChannelId());
			if (ws != null) {
				MessageUtils.sendMessage(ws, result.toJSONString());
			}
		}

		for (Player p : players) {
			RedisUtil.updateRedisData(null, p);
		}
		RedisUtil.updateRedisData(room, null);

		// 解散房间超时任务开启 TODO
		RoomUtil.addFreeRoomTask(StringUtils.parseLong(room.getRoomId()), System.currentTimeMillis() + Cnst.ROOM_DIS_TIME);
	}

	/**
	 * 同意或者拒绝解散房间
	 * 
	 * @param session
	 * @param readData
	 * @throws Exception
	 */

	public synchronized static void interface_100204(WSClient channel, Map<String, Object> readData) throws Exception {
		logger.info("同意或者拒绝解散房间,interfaceId -> interface_100204");
		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Integer roomId = StringUtils.parseInt(readData.get("roomId"));
		Long userId = StringUtils.parseLong(readData.get("userId"));
		Integer userAgree = StringUtils.parseInt(readData.get("userAgree"));
		RoomResp room = RedisUtil.getRoomRespByRoomId(String.valueOf(roomId));
		if (room == null) {// 房间已经自动解散
			Map<String, Object> info = new HashMap<>();
			info.put("reqState", Cnst.REQ_STATE_4);
			JSONObject result = getJSONObj(interfaceId, 1, info);
			MessageUtils.sendMessage(channel, result.toJSONString());
			return;
		}
		if (room.getDissolveRoom() == null) {
			Map<String, Object> info = new HashMap<>();
			JSONObject result = getJSONObj(interfaceId, 1, info);
			MessageUtils.sendMessage(channel, result.toJSONString());
			return;
		}
		List<Map<String, Object>> othersAgree = room.getDissolveRoom().getOthersAgree();
		for (Map<String, Object> m : othersAgree) {
			if (String.valueOf(m.get("userId")).equals(String.valueOf(userId))) {
				m.put("agree", userAgree);
				break;
			}
		}
		Map<String, Object> info = new HashMap<>();
		info.put("dissolveTime", room.getDissolveRoom().getDissolveTime());
		info.put("userId", room.getDissolveRoom().getUserId());
		info.put("othersAgree", room.getDissolveRoom().getOthersAgree());
		JSONObject result = getJSONObj(interfaceId, 1, info);

		int agreeNum = 0;
		int rejectNum = 0;
		for (Map<String, Object> m : othersAgree) {
			if (m.get("agree").equals(1)) {
				agreeNum++;
			} else if (m.get("agree").equals(2)) {
				rejectNum++;
			}
		}
		if (rejectNum >= 2) {
			// 有玩家拒绝解散房间//关闭解散房间计时任务 TODO
			RoomUtil.removeFreeRoomTask(StringUtils.parseLong(room.getRoomId()));
			room.setDissolveRoom(null);
			RedisUtil.setObject(Cnst.REDIS_PREFIX_ROOMMAP.concat(String.valueOf(roomId)), room, Cnst.ROOM_LIFE_TIME_CREAT);
		}
		RedisUtil.updateRedisData(room, null);
		List<Player> players = RedisUtil.getPlayerList(room);

		if (agreeNum >= 2) {

			if (room.getRoomType() == Cnst.ROOM_TYPE_2) {
				MessageFunctions.interface_100112(null, room, Cnst.PLAYER_EXTRATYPE_JIESANROOM);
			}

			// RedisUtil.setPlayersList(players);
			RoomUtil.updateDatabasePlayRecord(room);
			room.setState(Cnst.ROOM_STATE_YJS);
			for (Player p : players) {

				p.initPlayer(null, Cnst.PLAYER_STATE_DATING, null);
			}
			room.setDissolveRoom(null);
			RedisUtil.setObject(Cnst.REDIS_PREFIX_ROOMMAP.concat(String.valueOf(roomId)), room, Cnst.ROOM_LIFE_TIME_DIS);
			RedisUtil.setPlayersList(players);
			// 关闭解散房间计时任务 TODO
			RoomUtil.removeFreeRoomTask(StringUtils.parseLong(room.getRoomId()));
		}

		for (Player p : players) {
			WSClient ws = getWSClientManager().getWSClient(p.getChannelId());
			if (ws != null) {
				MessageUtils.sendMessage(ws, result.toJSONString());
			}
		}
	}

	/**
	 * 退出房间
	 * 
	 * @param session
	 * @param readData
	 * @throws Exception
	 */
	public synchronized static void interface_100205(WSClient channel, Map<String, Object> readData) throws Exception {
		logger.info("退出房间,interfaceId -> 100205");
		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Integer roomId = StringUtils.parseInt(readData.get("roomId"));
		Long userId = StringUtils.parseLong(readData.get("userId"));

		RoomResp room = RedisUtil.getRoomRespByRoomId(String.valueOf(roomId));
		if (room == null) {
			roomDoesNotExist(interfaceId, channel);
			return;
		}
		if (room.getState() == Cnst.ROOM_STATE_CREATED) {
			List<Player> players = RedisUtil.getPlayerList(room);
			Map<String, Object> info = new HashMap<>();
			info.put("userId", userId);
			if (room.getCreateId().equals(userId)) {// 房主退出，
				if (room.getRoomType().equals(Cnst.ROOM_TYPE_1)) {// 房主模式
					int circle = room.getCircleNum();

					info.put("type", Cnst.EXIST_TYPE_DISSOLVE);
					if (String.valueOf(roomId).length() == 6) {
						for (Player p : players) {
							if (p.getUserId().equals(userId)) {
								p.setMoney(p.getMoney() + Cnst.moneyMap.get(circle));
								break;
							}
						}
					} else if (String.valueOf(roomId).length() == 7) {
						// 退还俱乐部房卡
						ClubInfo clubInfo = RedisUtil.getClubInfoByClubId(room.getClubId().toString());
						clubInfo.setRoomCardNum(clubInfo.getRoomCardNum() + Cnst.moneyMap.get(circle));
						RedisUtil.setClubInfoByClubId(String.valueOf(room.getClubId()), clubInfo);
						// 移除俱乐部创建房间缓存
						RedisUtil.hdel(Cnst.REDIS_CLUB_ROOM_LIST.concat(String.valueOf(room.getClubId())), String.valueOf(room.getRoomId()));
					}

					RedisUtil.deleteByKey(Cnst.REDIS_PREFIX_ROOMMAP.concat(String.valueOf(roomId)));

					for (Player p : players) {
						p.initPlayer(null, Cnst.PLAYER_STATE_DATING, null);
					}
					// 关闭解散房间计时任务 TODO
					RoomUtil.removeFreeRoomTask(StringUtils.parseLong(room.getRoomId()));
				} else {// 自由模式，走正常退出
					info.put("type", Cnst.EXIST_TYPE_EXIST);
					existRoom(room, players, userId);
					RedisUtil.updateRedisData(room, null);
				}
			} else {// 正常退出
				for (Player player : players) {
					if (player.getUserId().equals(userId)) {// 找到退出的玩家
						// 如果加入的代开房间 通知房主
						if (room.getRoomType() == Cnst.ROOM_TYPE_2 && !userId.equals(room.getCreateId())) {
							MessageFunctions.interface_100112(player, room, Cnst.PLAYER_EXTRATYPE_EXITROOM);
						}
					}
				}
				info.put("type", Cnst.EXIST_TYPE_EXIST);
				existRoom(room, players, userId);
				RedisUtil.updateRedisData(room, null);
			}
			JSONObject result = getJSONObj(interfaceId, 1, info);
			for (Player p : players) {
				RedisUtil.updateRedisData(null, p);
			}

			for (Player p : players) {
				WSClient ws = getWSClientManager().getWSClient(p.getChannelId());
				if (ws != null) {
					MessageUtils.sendMessage(ws, result.toJSONString());
				}
			}

		} else {
			roomIsGaming(interfaceId, channel);
		}
	}

	private static void existRoom(RoomResp room, List<Player> players, Long userId) {
		for (Player p : players) {
			if (p.getUserId().equals(userId)) {
				// TODO
				p.initPlayer(null, Cnst.PLAYER_STATE_DATING, null);
				break;
			}
		}
		Long[] pids = room.getPlayerIds();
		if (pids != null) {
			for (int i = 0; i < pids.length; i++) {
				if (userId.equals(pids[i])) {
					pids[i] = null;
					break;
				}
			}
		}
	}

	/**
	 * 语音表情
	 * 
	 * @param session
	 * @param readData
	 * @throws Exception
	 */
	public static void interface_100206(WSClient channel, Map<String, Object> readData) throws Exception {
		logger.info("语音表情,interfaceId -> 100206");
		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Integer roomId = StringUtils.parseInt(readData.get("roomId"));
		String userId = String.valueOf(readData.get("userId"));
		String type = String.valueOf(readData.get("type"));
		String idx = String.valueOf(readData.get("idx"));

		Map<String, Object> info = new HashMap<>();
		info.put("roomId", roomId);
		info.put("userId", userId);
		info.put("type", type);
		info.put("idx", idx);
		JSONObject result = getJSONObj(interfaceId, 1, info);
		List<Player> players = RedisUtil.getPlayerList(roomId);
		for (Player p : players) {
			if (!p.getUserId().equals(userId)) {
				WSClient ws = getWSClientManager().getWSClient(p.getChannelId());
				if (ws != null) {
					MessageUtils.sendMessage(ws, result.toJSONString());
				}
			}
		}
	}

	/**
	 * 定位
	 * 
	 * @param session
	 * @param readData
	 * @throws Exception
	 */
	public static void interface_100207(WSClient channel, Map<String, Object> readData) throws Exception {
		logger.info("定位,interfaceId -> 100207");
		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Integer roomId = StringUtils.parseInt(readData.get("roomId"));
		RoomResp room = RedisUtil.getRoomRespByRoomId(String.valueOf(roomId));
		if (room == null) {
			return;
		}
		List<Player> players = RedisUtil.getPlayerList(room);
		List<Map<String, Object>> info = new ArrayList<Map<String, Object>>();
		List<Player> agreePlayers = new ArrayList<Player>();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getX_index() != null && players.get(i).getY_index() != null) {
				agreePlayers.add(players.get(i));
			}
		}
		if (agreePlayers.size() > 1) {
			for (int i = 0; i < agreePlayers.size(); i++) {
				for (int m = i + 1; m < agreePlayers.size(); m++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", agreePlayers.get(i).getUserId());
					map.put("toUserId", agreePlayers.get(m).getUserId());
					Double x1 = agreePlayers.get(i).getX_index() - agreePlayers.get(m).getX_index();
					Double y1 = agreePlayers.get(i).getY_index() - agreePlayers.get(m).getY_index();
					map.put("distance", (int) Math.floor(Math.sqrt(x1 * x1 + y1 * y1)));
					info.add(map);
				}
			}
		}
		JSONObject result = getJSONObj(interfaceId, 1, info);
		MessageUtils.sendMessage(channel, result.toJSONString());
	}

}
