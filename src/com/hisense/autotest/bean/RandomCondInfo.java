package com.hisense.autotest.bean;

import java.util.ArrayList;

public class RandomCondInfo {

	private float minInterval;
	private float maxInterval;
	private int scriptLimit;
	private int maxTime;
	private int stepCnt;
//	private int keyRangeFlag;
	private int randNum;
	private String[] rangeKeys;
	private String[] rangeKeysName;
	private ArrayList<SequenceInfo[]> sequenceList;
	
	public float getMinInterval() {
		return minInterval;
	}

	public void setMinInterval(float minInterval) {
		this.minInterval = minInterval;
	}

	public float getMaxInterval() {
		return maxInterval;
	}

	public void setMaxInterval(float maxInterval) {
		this.maxInterval = maxInterval;
	}

	public int getScriptLimit() {
		return scriptLimit;
	}

	public void setScriptLimit(int scriptLimit) {
		this.scriptLimit = scriptLimit;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public int getStepCnt() {
		return stepCnt;
	}

	public void setStepCnt(int stepCnt) {
		this.stepCnt = stepCnt;
	}

//	public int getKeyRangeFlag() {
//		return keyRangeFlag;
//	}
//
//	public void setKeyRangeFlag(int keyRangeFlag) {
//		this.keyRangeFlag = keyRangeFlag;
//	}

	public int getRandNum() {
		return randNum;
	}

	public void setRandNum(int randNum) {
		this.randNum = randNum;
	}

	public String[] getRangeKeys() {
		return rangeKeys;
	}

	public void setRangeKeys(String[] rangeKeys) {
		this.rangeKeys = rangeKeys;
	}

	public String[] getRangeKeysName() {
		return rangeKeysName;
	}

	public void setRangeKeysName(String[] rangeKeysName) {
		this.rangeKeysName = rangeKeysName;
	}
	
	public void setRangeSequence(ArrayList<SequenceInfo[]> sequenceList){
	    this.sequenceList =sequenceList;
	}
	
	public ArrayList<SequenceInfo[]> getRangeSequence(){
	    return sequenceList;
	}
}
