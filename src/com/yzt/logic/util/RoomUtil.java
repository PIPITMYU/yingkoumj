package com.yzt.logic.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.yzt.logic.mj.dao.ClubMapper;
import com.yzt.logic.mj.dao.RoomMapper;
import com.yzt.logic.mj.dao.UserMapper;
import com.yzt.logic.mj.domain.ClubInfo;
import com.yzt.logic.mj.domain.ClubUserUse;
import com.yzt.logic.mj.domain.Player;
import com.yzt.logic.mj.domain.PlayerMoneyRecord;
import com.yzt.logic.mj.domain.RoomResp;
import com.yzt.logic.mj.function.MessageFunctions;
import com.yzt.logic.mj.function.TCPGameFunctions;
import com.yzt.logic.util.GameUtil.StringUtils;
import com.yzt.logic.util.redis.RedisUtil;
import com.yzt.netty.client.WSClient;
import com.yzt.netty.util.MessageUtils;

public class RoomUtil {
	private static Log log = LogFactory.getLog(RoomUtil.class);

	public static ExecutorService taskExecuter = Executors.newFixedThreadPool(10);

	public static UserMapper userMapper = new UserMapper();
	public static RoomMapper roomMapper = new RoomMapper();

	/**
	 * 将要解散的房间ID 在任何时候 房间取消解散任何 增加定时解散任务 和修改房间解散时间 都要修改这个值
	 */
	private static ConcurrentHashMap<Long, Long> m_willFreeRoomMap = new ConcurrentHashMap<Long, Long>();

	/**
	 * 向数据库添加玩家分数信息 大结算
	 */
	public static void updateDatabasePlayRecord(RoomResp room) {
		if (room == null)
			return;
		if (String.valueOf(room.getRoomId()).length() == 7) {
			addupdateDatabasePlayRecord(room);
			return;
		}
		// 刷新数据库
		taskExecuter.execute(new Runnable() {
			public void run() {
				RoomMapper.updateRoomState(room.getRoomId(), room.getXiaoJuNum());
			}
		});

		// 判断totalNum 在小结算时+1
		Integer roomType = room.getRoomType();

		Map<String, String> roomSave = new HashMap<String, String>();
		if (room.getXiaoJuNum() != null && room.getXiaoJuNum() != 0) {

			// roomSave.put("endTime",
			// String.valueOf(System.currentTimeMillis()));
			List<Player> players = RedisUtil.getPlayerList(room);
			// 因为杠分是立刻结算的--所以在解散房间的时候，添加它的杠分--跟流局一样
			// 中途解散房间
			if (room.getLastNum() != 0) {
				// 因为杠分是立刻结算的--所以在解散房间的时候，添加它的杠分--跟流局一样
				MahjongUtils.jieSanGangScore(players, room);
			}
			// MahjongUtils.jiSuanGangScore(players, room);
			List<Map> redisRecord = new ArrayList<Map>();
			for (Player p : players) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", p.getUserId());
				map.put("score", p.getScore());
				map.put("huNum", p.getHuNum());
				map.put("dianNum", p.getDianNum());
				map.put("zhuangNum", p.getZhuangNum());
				map.put("ziMoNum", p.getZimoNum());
				redisRecord.add(map);
			}
			// 解散房间是 xiaoJSInfo 写入0
			if (room.getState() == Cnst.ROOM_STATE_GAMIING) {
				// 中途准备阶段解散房间不计入回放中
				List<Integer> xiaoJSInfo = new ArrayList<Integer>();
				for (int i = 0; i < room.getPlayerIds().length; i++) {
					xiaoJSInfo.add(players.get(i).getGangScore());
				}
				room.addXiaoJuInfo(xiaoJSInfo);
			}
			// 写入回放
			// BackFileUtil.save(100103, room, null, redisRecord, null);
			// setOverInfo 信息 大结算时 调用
			String key = room.getRoomId() + "-" + room.getCreateTime();
			RedisUtil.setObject(Cnst.REDIS_PLAY_RECORD_PREFIX_OVERINFO.concat(key), redisRecord, Cnst.OVERINFO_LIFE_TIME_COMMON);
			List<Map<String, Object>> userInfo = new ArrayList<Map<String, Object>>();
			for (Player p : players) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", p.getUserId());
				map.put("userName", p.getUserName());
				map.put("score", p.getScore());
				userInfo.add(map);
			}
			roomSave.put("userInfo", JSONObject.toJSONString(userInfo));
			roomSave.put("roomId", String.valueOf(room.getRoomId()));
			roomSave.put("createTime", room.getCreateTime());
			roomSave.put("circleNum", String.valueOf(room.getCircleNum()));
			roomSave.put("roomType", String.valueOf(room.getRoomType()));
			roomSave.put("userId", String.valueOf(room.getCreateId()));
			// 开房选项
			roomSave.put("scoreType", String.valueOf(room.getScoreType()));
			roomSave.put("ruleChangMao", String.valueOf(room.getRuleChangMao()));
			roomSave.put("ruleDaiHun", String.valueOf(room.getRuleDaiHun()));
			roomSave.put("ruleJueGang", String.valueOf(room.getRuleJueGang()));
			roomSave.put("ruleQiDui", String.valueOf(room.getRuleQiDui()));
			roomSave.put("ruleQingYiSe", String.valueOf(room.getRuleQingYiSe()));
			roomSave.put("ruleQiongHu", String.valueOf(room.getRuleQiongHu()));
			roomSave.put("ruleTuiDaoHu", String.valueOf(room.getRuleTuiDaoHu()));
			roomSave.put("ruleXuanFeng", String.valueOf(room.getRuleXuanFeng()));
			roomSave.put("rulePuFen", String.valueOf(room.getRulePuFen()));
			roomSave.put("playType", String.valueOf(room.getPlayType()));
			// 房间规则
			roomSave.put("xiaoJuNum", String.valueOf(room.getXiaoJuNum()));
			// 小局结算信息 回放用
			roomSave.put("xiaoJuInfo", JSONObject.toJSONString(room.getXiaoJuInfo()));
			String fineName = new StringBuffer().append("http://").append(room.getIp()).append(":8086/").append(Cnst.BACK_FILE_PATH).toString();
			roomSave.put("backUrl", fineName);
			// 更新redis 缓存
			RedisUtil.hmset(Cnst.REDIS_PLAY_RECORD_PREFIX.concat(key), roomSave, Cnst.PLAYOVER_LIFE_TIME);
			for (Player p : players) {
				haveRedisRecord(String.valueOf(p.getUserId()), key);
			}
			if (roomType != null && roomType == Cnst.ROOM_TYPE_2) {
				// 代开模式
				String key1 = Cnst.REDIS_PLAY_RECORD_PREFIX_ROE_DAIKAI.concat(String.valueOf(room.getCreateId()));
				RedisUtil.lpush(key1, null, key);
				String daiKaiKey = Cnst.REDIS_PREFIX_DAI_ROOM_LIST.concat(String.valueOf(room.getCreateId()));
				if (RedisUtil.exists(daiKaiKey)) {
					RedisUtil.hdel(daiKaiKey, String.valueOf(room.getRoomId()));
				}
			}
			// 如果是中途解散房间 写回放文件
			if (room.getDissolveRoom() != null) {
				BackFileUtil.write(room);
			}
		} else {
			return;
		}

	}

	public static void haveRedisRecord(String userId, String value) {
		String key = Cnst.REDIS_PLAY_RECORD_PREFIX_ROE_USER.concat(userId);
		RedisUtil.lpush(key, null, value);
	}

	/**
	 * 在房间正式开始发牌之后 调用此接口
	 * 
	 * @param room
	 */
	public static void addRoomToDB(RoomResp room) {
		if (String.valueOf(room.getRoomId()).length() == 7) {
			addClubTODB(room);
			return;
		}
		Integer circle = room.getCircleNum();
		Long createId = room.getCreateId();
		Long[] playerIds = room.getPlayerIds();

		taskExecuter.execute(new Runnable() {
			public void run() {
				// 扣除房主房卡
				UserMapper.updateMoney(UserMapper.getUserMoneyByUserId(createId) - Cnst.moneyMap.get(circle), createId + "");
			}
		});
		// 添加玩家消费记录
		PlayerMoneyRecord mr = new PlayerMoneyRecord();
		mr.setUserId(createId);
		mr.setMoney(Cnst.moneyMap.get(circle));
		mr.setType(room.getRoomType());
		mr.setCreateTime(new Date().getTime());
		UserMapper.insertPlayerMoneyRecord(mr);// 消费记录

		/* 向数据库添加房间信息 */
		Map<String, String> roomSave = new HashMap<String, String>();
		roomSave.put("roomId", String.valueOf(room.getRoomId()));
		roomSave.put("createId", String.valueOf(room.getCreateId()));
		roomSave.put("createTime", String.valueOf(room.getCreateTime()));
		roomSave.put("userId1", String.valueOf(playerIds[0]));
		roomSave.put("userId2", String.valueOf(playerIds[1]));
		roomSave.put("userId3", String.valueOf(playerIds[2]));
		roomSave.put("userId4", String.valueOf(playerIds[3]));
		roomSave.put("isPlaying", "1");
		roomSave.put("roomType", String.valueOf(room.getRoomType()));
		roomSave.put("circleNum", String.valueOf(room.getCircleNum()));
		roomSave.put("ip", room.getIp());
		roomSave.put("scoreType", String.valueOf(room.getScoreType()));
		roomSave.put("xiaoJuNum", String.valueOf(room.getXiaoJuNum()));

		roomSave.put("playType", String.valueOf(room.getPlayType()));
		roomSave.put("ruleChangMao", String.valueOf(room.getRuleChangMao()));
		roomSave.put("ruleDaiHun", String.valueOf(room.getRuleDaiHun()));
		roomSave.put("ruleJueGang", String.valueOf(room.getRuleJueGang()));
		roomSave.put("rulePuFen", String.valueOf(room.getRulePuFen()));
		roomSave.put("ruleQiDui", String.valueOf(room.getRuleQiDui()));
		roomSave.put("ruleQingYiSe", String.valueOf(room.getRuleQingYiSe()));
		roomSave.put("ruleQiongHu", String.valueOf(room.getRuleQiongHu()));
		roomSave.put("ruleTuiDaoHu", String.valueOf(room.getRuleTuiDaoHu()));
		roomSave.put("ruleXuanFeng", String.valueOf(room.getRuleXuanFeng()));

		RoomMapper.insert(roomSave);// 房间信息

		// 统计消费
//		taskExecuter.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					PostUtil.doCount(createId, Cnst.moneyMap.get(circle), room.getRoomType(), room.getRoomId());
//				} catch (Exception e) {
//					System.out.println("调用统计借口出错");
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * 添加俱乐部房间信息
	 * 
	 * @param room
	 */
	private static void addClubTODB(RoomResp room) {
		Long[] playerIds = room.getPlayerIds();

		RedisUtil.hdel(Cnst.REDIS_CLUB_ROOM_LIST.concat(String.valueOf(room.getClubId())), String.valueOf(room.getRoomId()));
		taskExecuter.execute(new Runnable() {
			public void run() {
				// 扣除俱乐部房卡
				ClubMapper.updateClubMoney(room.getClubId(), Cnst.moneyMap.get(room.getCircleNum()));
			}
		});
		// 添加玩家消费记录
		ClubUserUse cuu = new ClubUserUse();
		cuu.setClubId(room.getClubId());
		cuu.setCreateTime(System.currentTimeMillis());
		cuu.setMoney(Cnst.moneyMap.get(room.getCircleNum()) / 4);
		cuu.setRoomId(room.getRoomId());

		/* 向数据库添加房间信息 */
		HashMap<String, String> roomSave = new HashMap<String, String>();
		roomSave.put("clubId", String.valueOf(room.getClubId()));
		roomSave.put("roomId", String.valueOf(room.getRoomId()));
		roomSave.put("createId", String.valueOf(room.getCreateId()));
		roomSave.put("createTime", String.valueOf(room.getCreateTime()));
		roomSave.put("userId1", String.valueOf(playerIds[0]));
		roomSave.put("userId2", String.valueOf(playerIds[1]));
		roomSave.put("userId3", String.valueOf(playerIds[2]));
		roomSave.put("userId4", String.valueOf(playerIds[3]));
		roomSave.put("isPlaying", "1");
		roomSave.put("roomType", String.valueOf(room.getRoomType()));
		roomSave.put("circleNum", String.valueOf(room.getCircleNum()));
		roomSave.put("ip", room.getIp());
		roomSave.put("scoreType", String.valueOf(room.getScoreType()));
		roomSave.put("xiaoJuNum", String.valueOf(room.getXiaoJuNum()));

		roomSave.put("playType", String.valueOf(room.getPlayType()));
		roomSave.put("ruleChangMao", String.valueOf(room.getRuleChangMao()));
		roomSave.put("ruleDaiHun", String.valueOf(room.getRuleDaiHun()));
		roomSave.put("ruleJueGang", String.valueOf(room.getRuleJueGang()));
		roomSave.put("rulePuFen", String.valueOf(room.getRulePuFen()));
		roomSave.put("ruleQiDui", String.valueOf(room.getRuleQiDui()));
		roomSave.put("ruleQingYiSe", String.valueOf(room.getRuleQingYiSe()));
		roomSave.put("ruleQiongHu", String.valueOf(room.getRuleQiongHu()));
		roomSave.put("ruleTuiDaoHu", String.valueOf(room.getRuleTuiDaoHu()));
		roomSave.put("ruleXuanFeng", String.valueOf(room.getRuleXuanFeng()));

		ClubMapper.saveRoom(roomSave);// 房间信息
		taskExecuter.execute(new Runnable() {
			public void run() {
				for (Long long1 : playerIds) {
					cuu.setUserId(long1);
					ClubMapper.saveUserUse(cuu);// 消费记录
				}
			}
		});

		// 俱乐部统计消费
//		taskExecuter.execute(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					PostUtil.doCount(room.getCreateId(), Cnst.moneyMap.get(room.getCircleNum()), room.getRoomType(), room.getRoomId());
//				} catch (Exception e) {
//					System.out.println("调用统计借口出错");
//					e.printStackTrace();
//				}
//			}
//		});

	}

	private static void addupdateDatabasePlayRecord(RoomResp room) {
		// 刷新数据库
		taskExecuter.execute(new Runnable() {
			public void run() {
				ClubMapper.updateRoomState(room.getRoomId(), room.getXiaoJuNum());
			}
		});
		// 判断totalNum 在小结算时+1
		Map<String, String> roomSave = new HashMap<String, String>();
		if (room.getXiaoJuNum() != null && room.getXiaoJuNum() != 0) {
			List<Player> players = RedisUtil.getPlayerList(room);
			// 中途解散房间
			if (room.getLastNum() != 0) {
				// 因为杠分是立刻结算的--所以在解散房间的时候，添加它的杠分--跟流局一样
				MahjongUtils.jieSanGangScore(players, room);
			}
			List<Map> redisRecord = new ArrayList<Map>();
			for (Player p : players) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", p.getUserId());
				map.put("score", p.getScore());
				map.put("huNum", p.getHuNum());
				map.put("dianNum", p.getDianNum());
				map.put("zhuangNum", p.getZhuangNum());
				map.put("ziMoNum", p.getZimoNum());
				redisRecord.add(map);
			}

			// 解散房间是 xiaoJSInfo 写入0
			if (room.getState() == Cnst.ROOM_STATE_GAMIING) {
				// 中途准备阶段解散房间不计入回放中
				List<Integer> xiaoJSInfo = new ArrayList<Integer>();
				for (int i = 0; i < room.getPlayerIds().length; i++) {
					xiaoJSInfo.add(players.get(i).getGangScore());
				}
				room.addXiaoJuInfo(xiaoJSInfo);
			}
			// setOverInfo 信息 大结算时 调用
			String key = room.getRoomId() + "_" + room.getCreateTime();
			RedisUtil.setObject(Cnst.REDIS_PLAY_RECORD_PREFIX_OVERINFO.concat(room.getRoomId() + "-" + room.getCreateTime()), redisRecord,
					Cnst.OVERINFO_LIFE_TIME_COMMON);
			List<Map<String, Object>> userInfo = new ArrayList<Map<String, Object>>();
			for (Player p : players) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", p.getUserId());
				map.put("userName", p.getUserName());
				map.put("score", p.getScore());
				userInfo.add(map);
			}
			roomSave.put("userInfo", JSONObject.toJSONString(userInfo));
			roomSave.put("roomId", String.valueOf(room.getRoomId()));
			roomSave.put("createTime", room.getCreateTime());

			// 更新redis 缓存
			RedisUtil.hmset(Cnst.REDIS_CLUB_PLAY_RECORD_PREFIX.concat(key), roomSave, Cnst.PLAYOVER_LIFE_TIME);
			for (Player p : players) {
				haveClubRedisRecord(room.getClubId(), p.getUserId(), key, p.getScore());
			}
		} else {
			return;
		}
	}

	private static void haveClubRedisRecord(Integer clubId, Long userId, String key, Integer score) {
		String key1 = Cnst.REDIS_CLUB_PLAY_RECORD_PREFIX_ROE_USER + clubId + "_" + userId + "_" + StringUtils.getTimesmorning();
		RedisUtil.lpush(key1, Cnst.PLAYOVER_LIFE_TIME, key);
		// 更新每个玩家每天的分数
		String key2 = Cnst.REDIS_CLUB_TODAYSCORE_ROE_USER + clubId + "_" + userId + "_" + StringUtils.getTimesmorning();
		if (RedisUtil.exists(key2)) {
			Integer oldSocre = RedisUtil.getObject(key2, Integer.class);
			RedisUtil.setObject(key2, String.valueOf(oldSocre + score), Cnst.PLAYOVER_LIFE_TIME);
		} else {
			RedisUtil.setObject(key2, String.valueOf(score), Cnst.PLAYOVER_LIFE_TIME);
		}
	}

	/**
	 * 增加解散房间的任务
	 * 
	 * @param roomId
	 * @param time
	 */
	public static void addFreeRoomTask(Long roomId, Long time) {
		m_willFreeRoomMap.put(roomId, time);
	}

	/**
	 * 移除解散房间的任务
	 * 
	 * @param roomId
	 * @param time
	 */
	public static void removeFreeRoomTask(Long roomId) {
		m_willFreeRoomMap.remove(roomId);
	}

	public static void checkFreeRoomTask() {
		try {
			Set<Entry<Long, Long>> entrySet = m_willFreeRoomMap.entrySet();
			Iterator<Entry<Long, Long>> iterator = entrySet.iterator();
			long now = System.currentTimeMillis();
			while (iterator.hasNext()) {
				Map.Entry<Long, Long> entry = (Map.Entry<Long, Long>) iterator.next();
				if (entry.getValue() < now) {
					Long roomId = entry.getKey();
					// 这个房间要解散了
					m_willFreeRoomMap.remove(roomId);

					RoomResp room = RedisUtil.getRoomRespByRoomId(String.valueOf(roomId));

					if (room == null)
						continue;

					// 下面所有的房间解散 都要验证下玩家当时的roomId是否=当前的roomId 否则可能会出现串房现象
					// 如果roomId和当前房间不相同,那么玩家可能在其他房间玩 所以不能修改他的RoomID 也不修改他的任何数据
					// 仅仅处理房间数据 和其他玩家数据

					// FIXME 判断游戏是未开局
					if (room.getState() == 1) {
						// 解散房间 通知房主 和所有其他人
						// 给房主返回最新房卡
						// 还原玩家状态等
						List<Player> players = RedisUtil.getPlayerList(room);

						// 通知房间内用户房间被解散
						MessageFunctions.interface_100111(Cnst.REQ_STATE_13, players, room.getRoomId());
						if (room.getRoomType() == Cnst.ROOM_TYPE_2) {
							MessageFunctions.interface_100112(null, room, Cnst.PLAYER_EXTRATYPE_JIESANROOM);
						}
						if (players != null && players.size() > 0) {
							for (Player p : players) {
								if (p.getRoomId().equals(room.getRoomId())) {
									// TODO
									p.initPlayer(null, Cnst.PLAYER_STATE_DATING, null);
									RedisUtil.updateRedisData(null, p);
								}
							}
						}
						if (String.valueOf(roomId).length() == 6) {
							Player fangzhu = RedisUtil.getPlayerByUserId(String.valueOf(room.getCreateId()));
							if (fangzhu != null) {
								fangzhu.setMoney(fangzhu.getMoney() + Cnst.moneyMap.get(room.getCircleNum()));
								RedisUtil.updateRedisData(null, fangzhu);
							}
						}

						if (String.valueOf(roomId).length() == 7) {
							// 退还俱乐部房卡
							ClubInfo clubInfo = RedisUtil.getClubInfoByClubId(room.getClubId().toString());
							clubInfo.setRoomCardNum(clubInfo.getRoomCardNum() + Cnst.moneyMap.get(room.getCircleNum()));
							RedisUtil.setClubInfoByClubId(String.valueOf(room.getClubId()), clubInfo);
						}
						RedisUtil.deleteByKey(Cnst.REDIS_PREFIX_ROOMMAP.concat(String.valueOf(roomId)));
						// 通知房间内用户房间被解散
						MessageFunctions.interface_100111(Cnst.REQ_STATE_13, players, room.getRoomId());
						for (Player player : players) {
							player.initPlayer(null, Cnst.PLAYER_STATE_DATING, null);
						}
					} else if (room.getDissolveRoom() != null) {
						// 有人申请解散 这个是5分钟没人响应 解散房间
						// 还原玩家状态等
						List<Map<String, Object>> otherAgreeList = room.getDissolveRoom().getOthersAgree();
						if (otherAgreeList != null && otherAgreeList.size() > 0) {
							for (int i = 0; i < otherAgreeList.size(); i++) {
								otherAgreeList.get(i).put("agree", 1);
							}
						}

						Map<String, Object> info = new HashMap<>();
						info.put("dissolveTime", room.getDissolveRoom().getDissolveTime());
						info.put("userId", room.getDissolveRoom().getUserId());
						info.put("othersAgree", otherAgreeList);
						JSONObject result = TCPGameFunctions.getJSONObj(100204, 1, info);

						if (room.getRoomType() == Cnst.ROOM_TYPE_2) {
							MessageFunctions.interface_100112(null, room, Cnst.PLAYER_EXTRATYPE_JIESANROOM);
						}

						RoomUtil.updateDatabasePlayRecord(room);
						room.setState(Cnst.ROOM_STATE_YJS);

						List<Player> players = RedisUtil.getPlayerList(room);

						for (Player p : players) {

							if (p.getRoomId().equals(room.getRoomId())) {
								WSClient ws = TCPGameFunctions.getWSClientManager().getWSClient(p.getChannelId());
								if (ws != null) {
									MessageUtils.sendMessage(ws, result.toJSONString());
								}
								// TODO
								p.initPlayer(null, Cnst.PLAYER_STATE_DATING, null);
								RedisUtil.updateRedisData(null, p);
							}
						}
						RedisUtil.updateRedisData(room, null);
					} else {
						log.error(" 有房间 没有因为任何原因,被莫名其妙关闭 " + roomId);
						// 通知房间所有人 房间解散 解散房间
						// 还原玩家状态等
						List<Player> players = RedisUtil.getPlayerList(room);
						// 通知房间内用户房间被解散
						MessageFunctions.interface_100111(Cnst.REQ_STATE_13, players, room.getRoomId());
						if (players != null && players.size() > 0) {
							for (Player p : players) {
								if (p.getRoomId().equals(room.getRoomId())) {
									// TODO
									p.initPlayer(null, Cnst.PLAYER_STATE_DATING, null);
									RedisUtil.updateRedisData(null, p);
								}
							}
						}
						RedisUtil.deleteByKey(Cnst.REDIS_PREFIX_ROOMMAP.concat(String.valueOf(roomId)));
						// 通知房间内用户房间被解散
						MessageFunctions.interface_100111(Cnst.REQ_STATE_13, players, room.getRoomId());

					}

					// 重新制定迭代器
					entrySet = m_willFreeRoomMap.entrySet();
					iterator = entrySet.iterator();
				}
			}
		} catch (Exception e) {
			log.error("ERROR", e);
		}
	}

	public static void main(String[] args) {
		JSONObject info = new JSONObject();
		info.put("a", 100401);
		info.put("k", 894935);
		info.put("b", 100);
		System.out.println(info);
	}

	/**
	 * 大厅-战绩造数据
	 */
	public static void interface_100400(WSClient channel, Map<String, Object> readData) {
		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Integer userId = StringUtils.parseInt(readData.get("userId"));

		// 要造的数量
		Integer num = StringUtils.parseInt(readData.get("state"));
		if (userId == null || num == null) {
			return;
		}
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			Integer roomId = Cnst.TEST_ROOMID;
			// 获取房间id
			while (true) {
				if (RedisUtil.getRoomRespByRoomId(roomId + "") == null) {
					roomId++;
					break;
				}
			}
			// String key = room.getRoomId()+"-"+room.getCreateTime();
			String key = roomId + "-" + (currentTimeMillis + i);
			haveRedisRecord(String.valueOf(userId), key);
			// 保存房间信息
			Map<String, String> roomSave = new HashMap<String, String>();

			List<Map<String, Object>> userInfo = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < 3; j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", 1);
				map.put("userName", "God");
				map.put("score", 0);
				userInfo.add(map);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", 894935);
			map.put("userName", "Godv");
			map.put("score", 2);
			userInfo.add(map);
			roomSave.put("userInfo", JSONObject.toJSONString(userInfo));
			roomSave.put("roomId", String.valueOf(roomId));
			roomSave.put("createTime", (currentTimeMillis + i) + "");
			roomSave.put("circleNum", String.valueOf(4));
			roomSave.put("lastNum", String.valueOf(4));
			// 小局结算信息 回放用
			RedisUtil.hmset(Cnst.REDIS_PLAY_RECORD_PREFIX.concat(key), roomSave, Cnst.TEST_PLAYOVER_LIFE_TIME);
		}
		Map<String, Object> info = new HashMap<String, Object>();
		JSONObject result = MessageFunctions.getJSONObj(interfaceId, 1, info);
		MessageUtils.sendMessage(channel, result.toJSONString());
	}

	/**
	 * 俱乐部玩家战绩造数据
	 */
	public static void interface_100402(WSClient channel, Map<String, Object> readData) {
		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Integer userId = StringUtils.parseInt(readData.get("userId"));
		Integer clubId = StringUtils.parseInt(readData.get("clubId"));
		Integer date = StringUtils.parseInt(readData.get("date"));// 1今天 0昨天

		// 要造的数量
		Integer num = StringUtils.parseInt(readData.get("state"));
		if (userId == null || num == null) {
			return;
		}
		String redisDate = null;
		if (date == 1) {
			// 今天
			redisDate = StringUtils.toString(StringUtils.getTimesmorning());
		} else {
			redisDate = StringUtils.toString(StringUtils.getYesMoring());
		}
		String userKey = Cnst.REDIS_CLUB_PLAY_RECORD_PREFIX_ROE_USER + clubId + "_" + userId + "_" + redisDate;
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			Integer roomId = Cnst.TEST_CLUB_ROOMID;
			// 获取房间id
			while (true) {
				if (RedisUtil.getRoomRespByRoomId(roomId + "") == null) {
					roomId++;
					break;
				}
			}
			// TODO
			// String key = room.getRoomId()+"-"+room.getCreateTime();
			// 俱乐部的房间连接为_ 正常游戏端的为-
			String key = roomId + "_" + (currentTimeMillis + i);
			RedisUtil.lpush(userKey, Cnst.PLAYOVER_LIFE_TIME, key);
			haveRedisRecord(String.valueOf(userId), key);
			// 保存房间信息
			Map<String, String> roomSave = new HashMap<String, String>();

			List<Map<String, Object>> userInfo = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < 3; j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", 1);
				map.put("userName", "God");
				map.put("score", 0);
				userInfo.add(map);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("userName", "Godv");
			map.put("score", 2);
			userInfo.add(map);
			roomSave.put("userInfo", JSONObject.toJSONString(userInfo));
			roomSave.put("roomId", String.valueOf(roomId));
			roomSave.put("createTime", (currentTimeMillis + i) + "");
			roomSave.put("circleNum", String.valueOf(4));
			roomSave.put("lastNum", String.valueOf(4));
			// 小局结算信息 回放用
			// 更新redis 缓存
			RedisUtil.hmset(Cnst.REDIS_CLUB_PLAY_RECORD_PREFIX.concat(key), roomSave, Cnst.PLAYOVER_LIFE_TIME);
		}
		Map<String, Object> info = new HashMap<String, Object>();
		JSONObject result = MessageFunctions.getJSONObj(interfaceId, 1, info);
		MessageUtils.sendMessage(channel, result.toJSONString());
	}

	/**
	 * 大厅代开历史造数据
	 */
	public static void interface_100401(WSClient channel, Map<String, Object> readData) {
		Integer interfaceId = StringUtils.parseInt(readData.get("interfaceId"));
		Integer userId = StringUtils.parseInt(readData.get("userId"));
		// 要造的数量
		Integer num = StringUtils.parseInt(readData.get("state"));
		if (userId == null || num == null) {
			return;
		}
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			Integer roomId = Cnst.TEST_DAIKAI_ROOMID;
			// 获取房间id
			while (true) {
				if (RedisUtil.getRoomRespByRoomId(roomId + "") == null) {
					roomId++;
					break;
				}
			}
			// String key = room.getRoomId()+"-"+room.getCreateTime();
			String key = roomId + "-" + (currentTimeMillis + i);
			String key1 = Cnst.REDIS_PLAY_RECORD_PREFIX_ROE_DAIKAI.concat(String.valueOf(userId));
			RedisUtil.lpush(key1, null, key);
			// 保存房间信息
			Map<String, String> roomSave = new HashMap<String, String>();

			List<Map<String, Object>> userInfo = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < 3; j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", 1);
				map.put("userName", "Satan");
				map.put("score", 0);
				userInfo.add(map);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", 894935);
			map.put("userName", "Godv");
			map.put("score", 2);
			userInfo.add(map);
			roomSave.put("userInfo", JSONObject.toJSONString(userInfo));
			roomSave.put("roomId", String.valueOf(roomId));
			roomSave.put("createTime", (currentTimeMillis + i) + "");
			roomSave.put("circleNum", String.valueOf(4));
			roomSave.put("lastNum", String.valueOf(4));
			// 小局结算信息 回放用
			RedisUtil.hmset(Cnst.REDIS_PLAY_RECORD_PREFIX.concat(key), roomSave, Cnst.TEST_PLAYOVER_LIFE_TIME);
		}

		Map<String, Object> info = new HashMap<String, Object>();
		JSONObject result = MessageFunctions.getJSONObj(interfaceId, 1, info);
		MessageUtils.sendMessage(channel, result.toJSONString());
	}

}
