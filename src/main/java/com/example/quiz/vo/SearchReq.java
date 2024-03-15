package com.example.quiz.vo;

import java.time.LocalDate;

public class SearchReq {

	private String quizName;

	private LocalDate StartDate;

	private LocalDate endDate;

	private boolean backend;

	public SearchReq() {
		super();
	}

	public SearchReq(String quizName, LocalDate startDate, LocalDate endDate, boolean backend) {
		super();
		this.quizName = quizName;
		StartDate = startDate;
		this.endDate = endDate;
		this.backend = backend;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isBack() {
		return backend;
	}

	public void setBack(boolean isBack) {
		this.backend = isBack;
	}

}
