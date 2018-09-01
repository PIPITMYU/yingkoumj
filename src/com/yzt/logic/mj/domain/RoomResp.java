package com.yzt.logic.mj.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yzt.netty.client.WSClient;

/**
 * Created by Administrator on 2017/7/8.
 */
public class RoomResp extends Room {

	private static final long serialVersionUID = -5308844344084689942L;
	// 本房间状态，1等待玩家入坐；2游戏中；3小结算
	private Integer state;
	
	//游戏里使用
	//坐庄方式(逆时针坐庄),庄胡牌继续坐庄,荒庄下庄
	private Integer lastNum;// 房间剩余圈数
	private Integer circleNum;// 总圈
	private Integer xiaoJuNum;// 局数
	private Integer roomAction;// 房间状态  3种:出,摸和海底
	//这来字段断线重连给,为了显示风向和发牌
	private Long lastFaUserId;// 最后一个发牌玩家
	private Long lastChuUserId;// 最后一个出牌玩家
	private Integer position;// 找到当前的玩家风向
	
	private Long xjst;// 小局开始时间
	private Integer circleWind;// 当前庄的位置...
	private Long currentPlayerUserId;// 当前动作玩家
	private Long lastPlayerUserId;// 上一个动作玩家
	private List<Integer> currentActions;//当前玩家动作集合--本来存player里面了,非要改到房间
	private Integer lastAction;//上一个玩家的动作的上一个动作--本来存player里面了,非要改到房间
	
	
	private List<Long> nowPlayerIds;// 从出牌玩家的下家开始的有动作的玩家集合
	private Long[] playerIds;// 玩家id集合
	private List<Integer> currentMjList;// 房间内剩余麻将集合；
	private Long zhuangId;
	private Long lastPengGangUser;// 最后碰杠的玩家
	
	private Integer hunPai; // 混牌
	private Integer dingHunPai; // 开混的牌
	private Integer lastDongZuoPai; // 出或者摸,或者碰杠的牌
	private List<List<Integer>> xiaoJuInfo = new ArrayList<List<Integer>>();// 小结算info
	private Boolean huangZhuang;
	private Long winPlayerId;//当局胡牌的玩家
	
	
	

	
	//申请解散使用
	private Integer createDisId;
	private Integer applyDisId;
	private Integer outNum;
	private DissolveRoom dissolveRoom;// 申请解散信息
	
//	private Integer wsw_sole_main_id;// 大接口id
	private Integer wsw_sole_action_id;// 吃碰杠出牌发牌id
	private String openName;
	private Collection<WSClient> group;// 房间 4个channel集合

	public void initRoom() {
		this.lastPengGangUser = null;
		this.nowPlayerIds= new ArrayList<Long>(4);
		this.hunPai = null;
		this.dingHunPai = null;
		this.dissolveRoom = null;
		this.huangZhuang = false;
		this.xjst = null;
		this.winPlayerId = null;
		this.lastDongZuoPai=null;
		this.lastAction=null;
		this.currentActions=new ArrayList<Integer>();
		this.lastChuUserId=null;
		this.lastFaUserId=null;
	}


	
	public Integer getDingHunPai() {
		return dingHunPai;
	}



	public void setDingHunPai(Integer dingHunPai) {
		this.dingHunPai = dingHunPai;
	}



	public Integer getPosition() {
		return position;
	}



	public void setPosition(Integer position) {
		this.position = position;
	}



	public Long getLastFaUserId() {
		return lastFaUserId;
	}



	public void setLastFaUserId(Long lastFaUserId) {
		this.lastFaUserId = lastFaUserId;
	}



	public Long getLastChuUserId() {
		return lastChuUserId;
	}

	public void setLastChuUserId(Long lastChuUserId) {
		this.lastChuUserId = lastChuUserId;
	}



	public List<Integer> getCurrentActions() {
		return currentActions;
	}


	public void setCurrentActions(List<Integer> currentActions) {
		this.currentActions = currentActions;
	}





	public Integer getLastAction() {
		return lastAction;
	}

	public void setLastAction(Integer lastAction) {
		this.lastAction = lastAction;
	}





	public Integer getLastDongZuoPai() {
		return lastDongZuoPai;
	}





	public void setLastDongZuoPai(Integer lastDongZuoPai) {
		this.lastDongZuoPai = lastDongZuoPai;
	}





	public Long getLastPlayerUserId() {
		return lastPlayerUserId;
	}



	public void setLastPlayerUserId(Long lastPlayerUserId) {
		this.lastPlayerUserId = lastPlayerUserId;
	}



	public Long getCurrentPlayerUserId() {
		return currentPlayerUserId;
	}



	public void setCurrentPlayerUserId(Long currentPlayerUserId) {
		this.currentPlayerUserId = currentPlayerUserId;
	}



	public Integer getRoomAction() {
		return roomAction;
	}




	public void setRoomAction(Integer roomAction) {
		this.roomAction = roomAction;
	}




	public Integer getXiaoJuNum() {
		return xiaoJuNum;
	}



	public void setXiaoJuNum(Integer xiaoJuNum) {
		this.xiaoJuNum = xiaoJuNum;
	}


	public Integer getHunPai() {
		return hunPai;
	}

	public void setHunPai(Integer hunPai) {
		this.hunPai = hunPai;
	}


	public List<Integer> getCurrentMjList() {
		return currentMjList;
	}

	public void setCurrentMjList(List<Integer> currentMjList) {
		this.currentMjList = currentMjList;
	}

	public Long getZhuangId() {
		return zhuangId;
	}

	public void setZhuangId(Long zhuangId) {
		this.zhuangId = zhuangId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getLastNum() {
		return lastNum;
	}

	public void setLastNum(Integer lastNum) {
		this.lastNum = lastNum;
	}




	public Integer getCircleNum() {
		return circleNum;
	}



	public void setCircleNum(Integer circleNum) {
		this.circleNum = circleNum;
	}



	public Long getXjst() {
		return xjst;
	}

	public void setXjst(Long xjst) {
		this.xjst = xjst;
	}

	public Integer getCircleWind() {
		return circleWind;
	}

	public void setCircleWind(Integer circleWind) {
		this.circleWind = circleWind;
	}

	public DissolveRoom getDissolveRoom() {
		return dissolveRoom;
	}

	public void setDissolveRoom(DissolveRoom dissolveRoom) {
		this.dissolveRoom = dissolveRoom;
	}

	public Integer getCreateDisId() {
		return createDisId;
	}

	public void setCreateDisId(Integer createDisId) {
		this.createDisId = createDisId;
	}

	public Integer getApplyDisId() {
		return applyDisId;
	}

	public void setApplyDisId(Integer applyDisId) {
		this.applyDisId = applyDisId;
	}

	public Integer getOutNum() {
		return outNum;
	}

	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}



	public Integer getWsw_sole_action_id() {
		return wsw_sole_action_id;
	}

	public void setWsw_sole_action_id(Integer wsw_sole_action_id) {
		this.wsw_sole_action_id = wsw_sole_action_id;
	}

	public String getOpenName() {
		return openName;
	}

	public void setOpenName(String openName) {
		this.openName = openName;
	}

	public Long[] getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(Long[] playerIds) {
		this.playerIds = playerIds;
	}

	public Long getLastPengGangUser() {
		return lastPengGangUser;
	}

	public void setLastPengGangUser(Long lastPengGangUser) {
		this.lastPengGangUser = lastPengGangUser;
	}

	public Collection<WSClient> getGroup() {
		return group;
	}

	public void setGroup(Collection<WSClient> group) {
		this.group = group;
	}

	public List<List<Integer>> getXiaoJuInfo() {
		return xiaoJuInfo;
	}

	public void setXiaoJuInfo(List<List<Integer>> xiaoJuInfo) {
		this.xiaoJuInfo = xiaoJuInfo;
	}

	public void addXiaoJuInfo(List<Integer> list) {
		xiaoJuInfo.add(list);
	}


	public Boolean getHuangZhuang() {
		return huangZhuang;
	}

	public void setHuangZhuang(Boolean huangZhuang) {
		this.huangZhuang = huangZhuang;
	}


	public Long getWinPlayerId() {
		return winPlayerId;
	}

	public void setWinPlayerId(Long winPlayerId) {
		this.winPlayerId = winPlayerId;
	}




	public List<Long> getNowPlayerIds() {
		return nowPlayerIds;
	}




	public void setNowPlayerIds(List<Long> nowPlayerIds) {
		this.nowPlayerIds = nowPlayerIds;
	}








	


	
}
