package com.yzt.logic.util.GameUtil;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yzt.logic.mj.domain.Action;
import com.yzt.logic.mj.domain.Player;
import com.yzt.logic.mj.domain.RoomResp;
import com.yzt.logic.util.BackFileUtil;
import com.yzt.logic.util.Cnst;
import com.yzt.logic.util.MahjongUtils;
import com.yzt.logic.util.RoomUtil;
import com.yzt.logic.util.redis.RedisUtil;

/**
 * 玩家分的统计
 * 
 * @author wsw_007
 *
 */
public class JieSuan {
	public static void xiaoJieSuan(String roomId) {
		RoomResp room = RedisUtil.getRoomRespByRoomId(roomId);
		List<Player> players = RedisUtil.getPlayerList(room);
		//获取玩家的胡分
 		if(room.getHuangZhuang() != null && room.getHuangZhuang() == true){//荒庄--庄不变,计算杠分
 			//此方法会直接计算并赋值玩家的杠分
 			MahjongUtils.jiSuanGangScore(players, room);
 			//更改玩家的总分
 			for (Player player1 : players) {
 				//更改玩家的分数
 				player1.setScore(player1.getScore()+player1.getGangScore());
 			}		
			//显示绝头混
 			for (Player player : players) {
				if((player.getMingJueNum()+player.getAnJueNum())>0){//如果玩家绝数量大于0
					player.getShowList().add(Cnst.JUETOUHUN);
				}
			}
//			room.setLastNum(room.getLastNum());
		}else{
			//获取玩家杠分---并且设置到赢得玩家中觉得数量
			MahjongUtils.jiSuanGangScore(players,room);
			//获取赢得玩家
			Player winPlayer=null;
			Player dianPlayer=null;
			for (Player player : players) {
				if(player.getIsHu()){//找到赢得玩家
					winPlayer=player;
				}
				if(player.getIsDian()){
					dianPlayer=player;
				}
			}
			//胡的牌型分
			Integer huLeiXingScore=0;
			//铺分计算：计算每个玩家的铺分总分并且设置在玩家属性puFenScore中
			if(room.getRulePuFen().equals(1)){//房间类型带铺分
//				MahjongUtils.getPlayerPufen(players,winPlayer);
				//因为铺分固定都铺,所以赢得人6分,输的人-2分
				for (Player player : players) {
					if(player.getIsHu()){
						player.setPuFenScore(3);
					}else{
						player.setPuFenScore(-1);
					}
				}
			}

			// 获取玩家的胡的类型
			List<Integer> huInfoList = MahjongUtils.checkHuInfo(players,winPlayer,room); 
			//获取胡的牌型分
			huLeiXingScore=MahjongUtils.getHuLeiXingScoreFromHuXingList(huInfoList);
			
			boolean ziMo = false;
			boolean zhuang = false;
			if(room.getWinPlayerId().equals(room.getZhuangId())){ //赢家是庄
				huInfoList.add(Cnst.ZHUANGJIA);
				zhuang=true;
			}
			if(winPlayer.getIsZiMo()){
				huInfoList.add(Cnst.ZIMO);
				ziMo = true;
			}
			//点炮不再显示
//			else{
//				huInfoList.add(Cnst.DIANPAO);
//			}
			//绝头分也不显示了,算杠分
//			if(winPlayer.getJueNum()>0){
//				huInfoList.add(Cnst.JUETOUHUN);
//			}
			if(winPlayer.getPuFenScore()>0){
				huInfoList.add(Cnst.PUFEN);
			}
//			winPlayer.setFanShu(huInfoList);

			// 计分方式：1,点炮三家付   --不会添加点num,自摸num,胡num,致死算分
			Integer huScore;
			if (room.getScoreType() == 1) {
				if(zhuang){ //赢家是庄
					if(ziMo){ //赢家是庄  自摸
						for(Player p: players){
							if(p.getIsHu()){ //基础分*3家*庄家*自摸
								huScore=huLeiXingScore * 3 * 2 *2 + p.getPuFenScore();
								p.setHuScore(huScore);
								p.setThisScore(huScore+p.getGangScore());
								p.setScore(p.getThisScore() + p.getScore());
							}else{
								//基础分*庄家*自摸  ;铺分已经计算了,如果输的话是负的分,直接加上就行
								huScore=-huLeiXingScore*2*2 +p.getPuFenScore();
								p.setHuScore(huScore);
								p.setThisScore(huScore+p.getGangScore());
								p.setScore(p.getThisScore() + p.getScore());
							}
						}
					}else{//赢家是庄 点炮三家付 不是自摸 
						for(Player p: players){
							if(p.getIsHu()){ 
								//基础分*庄家*(不是点的两家各1,点的那家2)
								huScore=huLeiXingScore  * 2 *(1*2+2) + p.getPuFenScore();
								p.setHuScore(huScore);
								p.setThisScore(huScore+p.getGangScore());
								p.setScore(p.getThisScore() + p.getScore());
							}else{
								if(p.getIsDian()){
									//基础分*庄家*点炮
									huScore=-huLeiXingScore*2*2 +p.getPuFenScore();
									p.setHuScore(huScore);
									p.setThisScore(huScore+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}else{//基础分*庄家
									huScore=-huLeiXingScore*2 +p.getPuFenScore();
									p.setHuScore(huScore);
									p.setThisScore(huScore+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}
							}
						}
					}
				}else{ //赢家不是庄
					if(ziMo){ //赢家不是庄，点炮三家付,是自摸
						for(Player p: players){
							if(p.getIsHu()){
								//基础分*自摸*(不是点的两家各1,庄的那家2)
								huScore=huLeiXingScore * 2 *(1*2+2)+ p.getPuFenScore();
								p.setHuScore(huScore);
								p.setThisScore(huScore+p.getGangScore());
								p.setScore(p.getThisScore() + p.getScore());
							}else{
								//基础分*自摸*庄家
								if(p.getUserId().equals(room.getZhuangId())){ //输家庄,翻倍
									huScore=-huLeiXingScore * 2 *2 + p.getPuFenScore();
									p.setHuScore(huScore);
									p.setThisScore(huScore+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}else{
									//基础分*自摸
									huScore=-huLeiXingScore * 2 + p.getPuFenScore();
									p.setHuScore(huScore);
									p.setThisScore(huScore+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}
							}
						}
					}else{ //点炮
						Integer winScore=0;
						//计算输的玩家
						for(Player p: players){
							if(!p.getIsHu()){//输的玩家
								//基础分*点炮
								if(p.getUserId().equals(dianPlayer.getUserId())){ //点炮那家
									if(p.getUserId().equals(room.getZhuangId())){ //输家庄,翻倍
										huScore=-huLeiXingScore  * 2*2 + p.getPuFenScore();
										winScore+=huLeiXingScore  * 2*2;
									}else{
										huScore=-huLeiXingScore  * 2  + p.getPuFenScore();
										winScore+=huLeiXingScore  * 2;
									}
									p.setHuScore(huScore);
									p.setThisScore(huScore+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}else{
									if(p.getUserId().equals(room.getZhuangId())){ //输家庄,翻倍
										huScore=-huLeiXingScore*2+ p.getPuFenScore();
										winScore+=huLeiXingScore * 2;
									}else{
										huScore=-huLeiXingScore+ p.getPuFenScore();
										winScore+=huLeiXingScore ;
									}
									//基础分*自摸
									p.setHuScore(huScore);
									p.setThisScore(huScore+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}
							}
						}
						winPlayer.setHuScore(winScore+winPlayer.getPuFenScore());
						winPlayer.setThisScore(winPlayer.getHuScore()+winPlayer.getGangScore());
						winPlayer.setScore(winPlayer.getThisScore() + winPlayer.getScore());
						
					}
				}
			} else {// 计分方式：2,点炮包三家,只包胡分----他没的还不包铺分
				if(zhuang){ //赢家是庄
					if(ziMo){ //赢家是庄  自摸
						for(Player p: players){
							if(p.getIsHu()){ //基础分*3家*庄家*自摸
								huScore=huLeiXingScore * 3 * 2 *2 + p.getPuFenScore();
								p.setHuScore(huScore);
								p.setThisScore(huScore+p.getGangScore());
								p.setScore(p.getThisScore() + p.getScore());
							}else{
								//基础分*庄家*自摸  ;铺分已经计算了,如果输的话是负的分,直接加上就行
								huScore=-huLeiXingScore*2*2 +p.getPuFenScore();
								p.setHuScore(huScore);
								p.setThisScore(huScore+p.getGangScore());
								p.setScore(p.getThisScore() + p.getScore());
							}
						}
					}else{//赢家是庄 点炮三家付 不是自摸 
						Integer loseHuFen=0;
						//基础分*庄家*(不是点的两家各1,点的那家2)
						huScore=huLeiXingScore  * 2 *(1*2+2)+winPlayer.getPuFenScore();
						loseHuFen=-huScore;//减去自己一个人的铺分
						for(Player p: players){
							if(p.getIsHu()){ 
								p.setHuScore(huScore);
								p.setThisScore(p.getHuScore()+p.getGangScore());
								p.setScore(p.getThisScore() + p.getScore());
							}else{
								if(p.getIsDian()){//包三家胡分--改了1：不包含铺分--又改了2：包铺分
									p.setHuScore(loseHuFen);
									p.setThisScore(p.getHuScore()+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}else{//不输胡分
									p.setHuScore(0);//输铺分--改了,铺分被别人包了
									p.setThisScore(p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}
							}
						}
					}
				}else{ //赢家不是庄
					if(ziMo){ //赢家不是庄，点炮三家付,是自摸
						for(Player p: players){
							if(p.getIsHu()){
								//基础分*自摸*(不是点的两家各1,庄的那家2)
								huScore=huLeiXingScore * 2 *(1*2+2) + p.getPuFenScore();
								p.setHuScore(huScore);
								p.setThisScore(huScore+p.getGangScore());
								p.setScore(p.getThisScore() + p.getScore());
							}else{
								//基础分*自摸*庄家
								if(p.getUserId().equals(room.getZhuangId())){ //输家庄,翻倍
									huScore=-huLeiXingScore * 2 *2 + p.getPuFenScore();
									p.setHuScore(huScore);
									p.setThisScore(huScore+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}else{
									//基础分*自摸
									huScore=-huLeiXingScore * 2 + p.getPuFenScore();
									p.setHuScore(huScore);
									p.setThisScore(huScore+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}
							}
						}
					}else{ //点炮
						Integer winScore=0;
						for(Player p: players){
							if(p.getIsHu()){
								//基础分*(不是点的两家各1,点的那家2)
							}else{
								//基础分*点炮
								if(p.getUserId().equals(dianPlayer.getUserId())){ //点炮那家
									if(p.getUserId().equals(room.getZhuangId())){ //输家庄,翻倍
										winScore=huLeiXingScore *(2*2+1*2)+winPlayer.getPuFenScore() ;
										huScore=-winScore;
									}else{
										winScore=huLeiXingScore *(2+1*2+1)+winPlayer.getPuFenScore() ;
										huScore=-winScore;
									}
									p.setHuScore(huScore);
									p.setThisScore(p.getHuScore()+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}else{
									p.setHuScore(0);
									p.setThisScore(p.getHuScore()+p.getGangScore());
									p.setScore(p.getThisScore() + p.getScore());
								}
							}
						}
						winPlayer.setHuScore(winScore);
						winPlayer.setThisScore(winPlayer.getHuScore()+winPlayer.getGangScore());
						winPlayer.setScore(winPlayer.getThisScore() + winPlayer.getScore());
					}
				}
			}
			for (Player player : players) {
				List<Integer> showList = player.getShowList();
				if((player.getMingJueNum()+player.getAnJueNum())>0){//如果玩家绝数量大于0
					showList.add(Cnst.JUETOUHUN);
				}
				if(player.getHasQiangTouJue()){
					showList.add(Cnst.QIANGTOUJUE);
				}
			}
			
			if(winPlayer.getUserId().equals(room.getZhuangId())){//庄赢
				//庄不变 --剩余圈数不变
			}else{
				//下个人坐庄
				int index = -1;
				Long[] playIds = room.getPlayerIds();
				for(int i=0;i<playIds.length;i++){
					if(playIds[i].equals(room.getZhuangId())){
						index = i+1;
						if(index == 4){
							index = 0;
						}
						break;
					}
				}
				room.setZhuangId(playIds[index]);
				
				room.setCircleWind(index+1);
				
				//不是第一局,并且圈风是东风 ,证明是下一圈了.
				if(room.getXiaoJuNum() != 1 && room.getCircleWind() == Cnst.WIND_EAST){
					//剩余圈数
					room.setLastNum(room.getLastNum()-1);
				}
			}
		}
		// 更新redis
		RedisUtil.setPlayersList(players);
		
		// 添加小结算信息
		List<Integer> xiaoJS = new ArrayList<Integer>();
		for (Player p : players) {
			xiaoJS.add(p.getThisScore());
		}
		room.addXiaoJuInfo(xiaoJS);
		// 初始化房间
		room.initRoom();
		RedisUtil.updateRedisData(room, null);
		// 写入文件
		List<Map<String, Object>> userInfos = new ArrayList<Map<String, Object>>();
		for (Player p : players) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", p.getUserId());
			map.put("score", p.getThisScore());
			map.put("huScore", p.getHuScore());
			map.put("gangScore", p.getGangScore());
			map.put("pais", p.getCurrentMjList());
			map.put("winInfo", p.getShowList());
			if(p.getIsHu()){
				map.put("isWin", 1);
			}else{
//				map.put("winInfo", p.getShowList());
				map.put("isWin", 0);
			}
			if(p.getIsDian()){
//				map.put("winInfo", p.getShowList());
				map.put("isDian", 1);
			}else{
//				map.put("winInfo", p.getShowList());
				map.put("isDian", 0);
			}
			if(p.getActionList() != null && p.getActionList().size() > 0){
				List<Object> actionList = new ArrayList<Object>();
				for(Action action : p.getActionList()){
					if(action.getType() == Cnst.ACTION_TYPE_CHI){
						Map<String,Integer> actionMap = new HashMap<String, Integer>();
						actionMap.put("action", action.getActionId());
						actionMap.put("extra", action.getExtra());
						actionList.add(actionMap);
					}else if(action.getType() == Cnst.ACTION_TYPE_ANGANG){
						Map<String,Integer> actionMap = new HashMap<String, Integer>();
						actionMap.put("action", action.getActionId());
						actionMap.put("extra", action.getExtra());
						actionList.add(actionMap);
					}else{
						actionList.add(action.getActionId());
					}
				}
				map.put("actionList", actionList);
			}			
			userInfos.add(map);
		}
		JSONObject info = new JSONObject();
		info.put("lastNum", room.getLastNum());
		info.put("userInfo", userInfos);
		BackFileUtil.save(100102, room, null, info,null);
		// 小结算 存入一次回放
		BackFileUtil.write(room);

		// 大结算判定 (玩的圈数等于选择的圈数)
		if (room.getLastNum() == 0) {
			// 最后一局 大结算
			room = RedisUtil.getRoomRespByRoomId(roomId);
			room.setState(Cnst.ROOM_STATE_YJS);
			RedisUtil.updateRedisData(room, null);
			// 这里更新数据库吧
			RoomUtil.updateDatabasePlayRecord(room);
		}
	}
}
