/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2017
 */

package com.yzt.logic.mj.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * 
 * @author up72
 * @version 1.0
 * @since 1.0
 */
public class Room implements java.io.Serializable {

	private Long id;
	private Integer roomId;
	private Long createId;
	private String createTime;
	private Integer isPlaying;
	private Long userId1;
	private Long userId2;
	private Long userId3;
	private Long userId4;
	private Integer circleNum; // 圈数
	private Integer clubId;// 俱乐部id
	private String ip;// 当前房间所在服务器的ip
	private Integer scoreType;// 计分方式：1点炮三家付；2点炮包三家
	//游戏规则
	private Integer roomType;// 房间模式，房主模式1；自由模式2
		
	private Integer ruleJueGang;// 绝杠选项：1绝；2杠
	private Integer ruleXuanFeng;//是否有旋风杠：1有 ;2无
	private Integer ruleChangMao;// 是否长毛：1是；2无
	private Integer ruleTuiDaoHu;// 是否有推倒胡：1有；2无---不开门也可以胡
	private Integer ruleDaiHun;// 是否带混：1带；2不带
	private Integer ruleQiongHu;// 是否带穷胡：1是；2无
	private Integer ruleQingYiSe;// 是否有清一色：1是；2无
	private Integer ruleQiDui;// 是否有七对：1是；2无
	private Integer rulePuFen;// 是否带铺分：1是；2无
	private Integer playType;// 玩法类型：1经典；2盖州


	public Integer getRuleJueGang() {
		return ruleJueGang;
	}

	public void setRuleJueGang(Integer ruleJueGang) {
		this.ruleJueGang = ruleJueGang;
	}

	public Integer getRuleXuanFeng() {
		return ruleXuanFeng;
	}

	public void setRuleXuanFeng(Integer ruleXuanFeng) {
		this.ruleXuanFeng = ruleXuanFeng;
	}

	public Integer getRuleChangMao() {
		return ruleChangMao;
	}

	public void setRuleChangMao(Integer ruleChangMao) {
		this.ruleChangMao = ruleChangMao;
	}

	public Integer getRuleTuiDaoHu() {
		return ruleTuiDaoHu;
	}

	public void setRuleTuiDaoHu(Integer ruleTuiDaoHu) {
		this.ruleTuiDaoHu = ruleTuiDaoHu;
	}

	public Integer getRuleDaiHun() {
		return ruleDaiHun;
	}

	public void setRuleDaiHun(Integer ruleDaiHun) {
		this.ruleDaiHun = ruleDaiHun;
	}

	public Integer getRuleQiongHu() {
		return ruleQiongHu;
	}

	public void setRuleQiongHu(Integer ruleQiongHu) {
		this.ruleQiongHu = ruleQiongHu;
	}

	public Integer getRuleQingYiSe() {
		return ruleQingYiSe;
	}

	public void setRuleQingYiSe(Integer ruleQingYiSe) {
		this.ruleQingYiSe = ruleQingYiSe;
	}

	public Integer getRuleQiDui() {
		return ruleQiDui;
	}

	public void setRuleQiDui(Integer ruleQiDui) {
		this.ruleQiDui = ruleQiDui;
	}

	public Integer getRulePuFen() {
		return rulePuFen;
	}

	public void setRulePuFen(Integer rulePuFen) {
		this.rulePuFen = rulePuFen;
	}

	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof Room == false)
			return false;
		if (this == obj)
			return true;
		Room other = (Room) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public Long getUserId1() {
		return userId1;
	}

	public void setUserId1(Long userId1) {
		this.userId1 = userId1;
	}

	public Long getUserId2() {
		return userId2;
	}

	public void setUserId2(Long userId2) {
		this.userId2 = userId2;
	}

	public Long getUserId3() {
		return userId3;
	}

	public void setUserId3(Long userId3) {
		this.userId3 = userId3;
	}

	public Long getUserId4() {
		return userId4;
	}

	public void setUserId4(Long userId4) {
		this.userId4 = userId4;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getIsPlaying() {
		return isPlaying;
	}

	public void setIsPlaying(Integer isPlaying) {
		this.isPlaying = isPlaying;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public Integer getCircleNum() {
		return circleNum;
	}

	public void setCircleNum(Integer circleNum) {
		this.circleNum = circleNum;
	}

	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getScoreType() {
		return scoreType;
	}

	public void setScoreType(Integer scoreType) {
		this.scoreType = scoreType;
	}

}
