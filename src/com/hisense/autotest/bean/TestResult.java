
package com.hisense.autotest.bean;

import java.util.ArrayList;
import java.util.List;

public class TestResult {

    private List<String[]> rstStepNodes = new ArrayList<String[]>(); // 测试步骤
    private boolean globalFlag;// 用例执行结果
    private List<Boolean> stepFlag = new ArrayList<Boolean>();// 存放每一步的执行结果
    private List<ArrayList<String>> picList = new ArrayList<ArrayList<String>>();// 存放对应步骤的截图
    private StringBuffer errorMsg = new StringBuffer();// 存放出错信息

    // init
    public TestResult(int nStep) {
        globalFlag = false;
        rstStepNodes.clear();
        stepFlag.clear();
        picList.clear();
        for (int i = 0; i < nStep; i++) {
            rstStepNodes.add(i, null);
            stepFlag.add(i, null);
            picList.add(i, new ArrayList<String>());
        }
    }

    public void initDupTestRst(int nStep) {
        int stepCnt = rstStepNodes.size();
        int index = 0;
        for (int i = 0; i < nStep; i++) {
            index = stepCnt + i;
            rstStepNodes.add(index, null);
            stepFlag.add(index, null);
            picList.add(index, new ArrayList<String>());
        }
    }
    
    public int getTestRstStepCnt(){
        return rstStepNodes.size();
    }

    public void addStep() {
        rstStepNodes.add(rstStepNodes.size(), null);
        stepFlag.add(stepFlag.size(), null);
        picList.add(picList.size(), new ArrayList<String>());
    }

    public String[] getRstStepNodes(int index) {
        return rstStepNodes.get(index);
    }

    public void setRstStepNodes(int index, String[] step) {
        this.rstStepNodes.set(index, step);
    }

    public boolean getGlobalFlag() {
        return globalFlag;
    }

    public void setGlobalFlag(boolean globalFlag) {
        this.globalFlag = globalFlag;
    }

    public Boolean getStepFlag(int index) {
        return stepFlag.get(index);
    }

    public void setStepFlag(int index, Boolean testRst) {
        this.stepFlag.set(index, testRst);
    }

    public ArrayList<String> getPicList(int index) {
        return picList.get(index);
    }

    public void setPicList(int index, ArrayList<String> pics) {
        this.picList.set(index, pics);
    }

    public void addPic(int index, String pic) {
        this.picList.get(index).add(pic);
    }

    public String getErrorMsg() {
        return errorMsg.toString();
    }

    public void addErrorMsg(String errorMsg) {
        this.errorMsg.append(errorMsg);
    }

}
