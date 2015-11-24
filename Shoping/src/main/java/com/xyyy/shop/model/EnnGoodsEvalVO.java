package com.xyyy.shop.model;


public class EnnGoodsEvalVO  {
	
	private EnnGoodsEval eval;
	private String evalMembImg;
	
	/**
	 * 红星
	 */
	private String  score;
	/**
	 * 白星
	 */
	private String  blankscore;

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public EnnGoodsEval getEval() {
		return eval;
	}

	public void setEval(EnnGoodsEval eval) {
		this.eval = eval;
	}

	public String getBlankscore() {
		return blankscore;
	}

	public void setBlankscore(String blankscore) {
		this.blankscore = blankscore;
	}

	public String getEvalMembImg() {
		return evalMembImg;
	}

	public void setEvalMembImg(String evalMembImg) {
		this.evalMembImg = evalMembImg;
	}
	

}
