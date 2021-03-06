package com.amcolabs.quizapp.databaseutils;

import java.util.List;

import com.amcolabs.quizapp.QuizApp;
import com.amcolabs.quizapp.appcontrollers.ProgressiveQuizController.UserAnswer;
import com.amcolabs.quizapp.configuration.Config;
import com.amcolabs.quizapp.gameutils.GameUtils;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;

public class LocalQuizHistory {

	@DatabaseField(index=true , unique = true ,  generatedId=true)
	private
	long quizHistoryid; // game with uid
	
	@DatabaseField(index=true)
	private
	String	withUid; // game with uid
	@DatabaseField
	private
	int quizResult; // win loose , tie , server error
	@DatabaseField
	public
	String quizId;
	@DatabaseField
	public
	int xpGain;
	@DatabaseField
	String questionIds;//json array list of string
	@DatabaseField
	private
	String userAnswers1;//json of user answers

	@DatabaseField
	private
	String userAnswers2;//json of user answers
	

	@DatabaseField
	public
	double timestamp;//json of user answers
	
	public List<UserAnswer> getUserAnswers1(QuizApp quizApp) {
		return cachedUserAnswer1==null ? (cachedUserAnswer1 = quizApp.getConfig().getGson().fromJson(userAnswers1, new TypeToken<List<UserAnswer>>(){}.getType())):cachedUserAnswer1;
	}



	public void setUserAnswers1(String userAnswers1) {
		this.userAnswers1 = userAnswers1;
	}
	
	public List<UserAnswer> getUserAnswers2(QuizApp quizApp) {
		return cachedUserAnswer2==null ? (cachedUserAnswer2 = quizApp.getConfig().getGson().fromJson(userAnswers2, new TypeToken<List<UserAnswer>>(){}.getType())):cachedUserAnswer2;
	}



	public void setUserAnswers2(String userAnswers2) {
		this.userAnswers2 = userAnswers2;
	}
	
	
	
	List<UserAnswer> cachedUserAnswer1 = null;
	List<UserAnswer> cachedUserAnswer2 = null;
	
	public int[] getLevelUpAndWinBonus(QuizApp quizApp){ // vague
		int total = GameUtils.getLastElement(getUserAnswers1(quizApp)).whatUserGot;
		int total2 = GameUtils.getLastElement(getUserAnswers2(quizApp)).whatUserGot;
		int winBonus = (int) (total>total2 ?Config.QUIZ_WIN_BONUS :0);
		int levelUpBonus = xpGain - total-winBonus;
		return new int[]{levelUpBonus, winBonus};
	}
	
	
	
	public LocalQuizHistory(String quizId, int quizResult, double xpGain ,String uid, String userAnswers1Json, String userAnswers2Json) {
		setWithUid(uid);
		this.quizId = quizId;
		this.setQuizResult(quizResult);
		this.xpGain = (int) xpGain;
		this.userAnswers1 = userAnswers1Json;
		this.userAnswers1 = userAnswers2Json;
	}
	
	public LocalQuizHistory(){
		
	}

	public int getQuizResult() {
		return quizResult;
	}

	public void setQuizResult(int quizResult) {
		this.quizResult = quizResult;
	}



	public String getWithUid() {
		return withUid;
	}



	public void setWithUid(String withUid) {
		this.withUid = withUid;
	}
		
}
