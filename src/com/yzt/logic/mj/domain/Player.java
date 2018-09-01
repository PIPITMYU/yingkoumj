package com.yzt.logic.mj.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2017/6/26.
 */
/**
 * @author wsw_008
 *
 */
public class Player extends User {

	private Integer roomId;// 房间密码，也是roomSn

	// out离开状态（断线）;inline正常在线；
	private Integer state;
	private Integer position;// 位置信息；详见Cnst
	private String ip;
	private String notice;// 跑马灯信息
	private Integer playStatus;// 用户当前状态， dating用户在大厅中; in刚进入房间，等待状态;prepared准备状态;game游戏中; xjs小结算
	private String channelId;// 通道id
	private Long updateTime;// 更新用户数据时间
	private boolean hasHun;// 玩家有混,若是杠或者旋风杠中有混,那么玩家就不能有混（仅仅包括显示的混，绝变混还是要的）
	
	
	private List<Integer> currentMjList;// 用户手中当前的牌
	private List<Integer> chuList;// 出牌的集合
	private Integer lastAction;//玩家的上一个动作
	private List<Integer> currentActions;//玩家当前动作集合
	private List<Integer> currentActionLevers;//玩家当前动作优先级集合(胡>杠=碰>吃)  --为了判断玩家的优先级
	private List<Action> actionList ;//统计用户已经动作的集合 (吃碰杠等)
	private Integer huDePai;//胡的那张牌---
	private List<Integer> pengList;// 用户碰的牌的集合
	private Integer mingJueNum;// 用户明绝得数量,所有玩家都有，算明杠分
	private Integer anJueNum;// 用户暗绝得数量,所有玩家都有，算暗杠分
	private List<Integer> showList;// 每个用户都得显示这个,没有给[]
	
	
	private Boolean hasQiangTouJue;//有的时候设置为true，每次从开都是false
	private Boolean hasChu;//准倍的时候为false;出过一次牌之后设置为true,为了检测旋风杠
	private Boolean isZiMo; // 是不是自摸
	private Integer puFen; //默认为0   选铺分玩法都变成1	
	private Boolean isHu;//小结算用
	private Boolean canHu;//能否胡--  4张牌的时候进行明杠,但是如果不满足飘,就不能胡---以后不再检测胡
	private Boolean isDian;//小结算用
	private Integer score;// 玩家这全游戏的总分
	private Integer thisScore;// 记录当玩家当前局
//	private List<Integer> fanShu; // 牌型记番,具体的番数.
								
	private Integer huNum;// 胡的次数
	private Integer lastFaPai;// 上次发的牌
	private Integer dianNum;// 点炮次数(输的玩家的次数)
	private Integer zhuangNum;// 坐庄次数
	private Integer zimoNum;// 自摸次数
	private Integer gangScore;// 本局杠分：普通杠分+绝杠(如果有绝选项)
	private Integer huScore;// 本局胡分:胡类型的基本分+铺分(如果有铺选项)+绝头混的分(如果有绝选项)
	private Integer puFenScore;//铺分(如果有铺选项才计算)
	
	private Double x_index;
	private Double y_index;

	public void initPlayer(Integer roomId,Integer playStatus,Integer score) {
		if(roomId == null){
			this.position = null;
			this.roomId = null;
			this.huNum = 0;
			this.zhuangNum = 0;
			this.zimoNum = 0;
			this.dianNum = 0;
		}
		this.hasHun=true;
		this.hasQiangTouJue=false;
		this.puFen=0;
		this.playStatus = playStatus;
		this.chuList = null;
		this.isHu = false;
		this.isDian = false;
		this.score = score;
		this.thisScore = 0;
		this.huDePai = null;
		this.isZiMo = false;
//		this.fanShu = null;
		this.hasChu = false;
		this.canHu = true;
		this.actionList = new ArrayList<Action>();
		this.currentMjList = new ArrayList<Integer>();
		this.pengList = new ArrayList<Integer>();
		this.lastAction=null;
		this.currentActions=new ArrayList<Integer>();
		this.currentActionLevers=new ArrayList<Integer>();
		this.gangScore=0;
		this.huScore=0;
		this.mingJueNum=0;
		this.anJueNum=0;
		this.puFenScore=0;
		this.chuList=new ArrayList<Integer>();
		this.showList=new ArrayList<Integer>();
	}




	public Boolean getHasQiangTouJue() {
		return hasQiangTouJue;
	}










	public void setHasQiangTouJue(Boolean hasQiangTouJue) {
		this.hasQiangTouJue = hasQiangTouJue;
	}










	public boolean getHasHun() {
		return hasHun;
	}










	public void setHasHun(boolean hasHun) {
		this.hasHun = hasHun;
	}










	public Integer getMingJueNum() {
		return mingJueNum;
	}




	public void setMingJueNum(Integer mingJueNum) {
		this.mingJueNum = mingJueNum;
	}




	public Integer getAnJueNum() {
		return anJueNum;
	}




	public void setAnJueNum(Integer anJueNum) {
		this.anJueNum = anJueNum;
	}




	public List<Integer> getShowList() {
		return showList;
	}




	public void setShowList(List<Integer> showList) {
		this.showList = showList;
	}




	public Integer getPuFenScore() {
		return puFenScore;
	}




	public void setPuFenScore(Integer puFenScore) {
		this.puFenScore = puFenScore;
	}







	public List<Integer> getPengList() {
		return pengList;
	}




	public void setPengList(List<Integer> pengList) {
		this.pengList = pengList;
	}




	public Integer getGangScore() {
		return gangScore;
	}



	public void setGangScore(Integer gangScore) {
		this.gangScore = gangScore;
	}



	public Integer getHuScore() {
		return huScore;
	}



	public void setHuScore(Integer huScore) {
		this.huScore = huScore;
	}



	public Boolean getCanHu() {
		return canHu;
	}



	public void setCanHu(Boolean canHu) {
		this.canHu = canHu;
	}



	public Integer getLastAction() {
		return lastAction;
	}



	public void setLastAction(Integer lastAction) {
		this.lastAction = lastAction;
	}



	public List<Integer> getCurrentActionLevers() {
		return currentActionLevers;
	}



	public void setCurrentActionLevers(List<Integer> currentActionLevers) {
		this.currentActionLevers = currentActionLevers;
	}



	public List<Integer> getCurrentActions() {
		return currentActions;
	}



	public void setCurrentActions(List<Integer> currentActions) {
		this.currentActions = currentActions;
	}



	public Integer getPuFen() {
		return puFen;
	}



	public void setPuFen(Integer puFen) {
		this.puFen = puFen;
	}



	public Boolean getIsZiMo() {
		return isZiMo;
	}

	public void setIsZiMo(Boolean isZiMo) {
		this.isZiMo = isZiMo;
	}





//	public List<Integer> getFanShu() {
//		return fanShu;
//	}
//
//	public void setFanShu(List<Integer> fanShu) {
//		this.fanShu = fanShu;
//	}




	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public List<Integer> getCurrentMjList() {
		return currentMjList;
	}

	public void setCurrentMjList(List<Integer> currentMjList) {
		this.currentMjList = currentMjList;
	}

	public List<Integer> getChuList() {
		return chuList;
	}

	public void setChuList(List<Integer> chuList) {
		this.chuList = chuList;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	public Boolean getIsHu() {
		return isHu;
	}

	public void setIsHu(Boolean isHu) {
		this.isHu = isHu;
	}

	public Boolean getIsDian() {
		return isDian;
	}

	public void setIsDian(Boolean isDian) {
		this.isDian = isDian;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getThisScore() {
		return thisScore;
	}

	public void setThisScore(Integer thisScore) {
		this.thisScore = thisScore;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getPlayStatus() {
		return playStatus;
	}

	public void setPlayStatus(Integer playStatus) {
		this.playStatus = playStatus;
	}


	public Integer getHuNum() {
		return huNum;
	}

	public void setHuNum(Integer huNum) {
		this.huNum = huNum;
	}

	public Integer getLastFaPai() {
		return lastFaPai;
	}

	public void setLastFaPai(Integer lastFaPai) {
		this.lastFaPai = lastFaPai;
	}

	public Integer getDianNum() {
		return dianNum;
	}

	public void setDianNum(Integer dianNum) {
		this.dianNum = dianNum;
	}

	public Integer getZhuangNum() {
		return zhuangNum;
	}

	public void setZhuangNum(Integer zhuangNum) {
		this.zhuangNum = zhuangNum;
	}

	public Integer getZimoNum() {
		return zimoNum;
	}

	public void setZimoNum(Integer zimoNum) {
		this.zimoNum = zimoNum;
	}


	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public List<Action> getActionList() {
		return actionList;
	}

	public void setActionList(List<Action> actionList) {
		this.actionList = actionList;
	}
	//添加 动作
	public void addActionList(Action action){
		this.actionList.add(action);
	}



	public Integer getHuDePai() {
		return huDePai;
	}



	public void setHuDePai(Integer huDePai) {
		this.huDePai = huDePai;
	}



	public Boolean getHasChu() {
		return hasChu;
	}



	public void setHasChu(Boolean hasChu) {
		this.hasChu = hasChu;
	}




	public Double getX_index() {
		return x_index;
	}




	public void setX_index(Double x_index) {
		this.x_index = x_index;
	}




	public Double getY_index() {
		return y_index;
	}




	public void setY_index(Double y_index) {
		this.y_index = y_index;
	}







	
}
