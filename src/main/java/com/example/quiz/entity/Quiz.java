package com.example.quiz.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@IdClass(value = QuizId.class)
@Table(name = "quiz")
public class Quiz {

	@Id
	@Column(name = "quiz_id")
	@JsonProperty("quiz_id")
	private int quizId; // 問卷編號: 第幾張問卷

	@Id
	@Column(name = "qu_id")
	@JsonProperty("qu_id")
	private int quId; // 問題編號: 第幾題問題

	@Column(name = "quiz_name")
	@JsonProperty("qu_name")
	private String quizName; // 問卷名稱: ex.課程滿意度調查

	@Column(name = "quiz_description")
	@JsonProperty("quiz_description")
	private String quizDescription; // 問卷描述: ex.為提升教學品質blablabla，用來描述問卷的目的或相關介紹

	@Column(name = "start_date")
	@JsonProperty("start_date")
	private LocalDate startDate; // 起始時間: 問卷什麼時候開放作答

	@Column(name = "end_date")
	@JsonProperty("end_date")
	private LocalDate endDate; // 結束時間: 問卷什麼時候結束作答

	@Column(name = "question")
	@JsonProperty("question")
	private String question; // 問題內容: ex.您對於岳祥老師的教學滿意度是 ?

	@Column(name = "necessary")
	@JsonProperty("is_necessary")
	private Boolean necessary; // 必填: 此題是否必須填寫

	@Column(name = "published")
	@JsonProperty("is_published")
	private Boolean published; // 是否發布: 這張問卷是否要公告在前台，讓進來網站的訪客看到

	@Column(name = "type")
	@JsonProperty("question_type")
	private String type; // 題型: 單選題、多選題、簡答題

	@Column(name = "options")
	@JsonProperty("question_options")
	private String options; // 選項: 如果是選擇題的話，會是用 ; 隔開的選項 ex.非常滿意;滿意;不滿意;非常不滿意
							// 如果是簡答題的話，為空

	public Quiz() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Quiz(int quizId, int quId, String quizName, String quizDescription, LocalDate startDate, LocalDate endDate,
			String question, Boolean necessary, String type, String options, boolean published) {
		super();
		this.quizId = quizId;
		this.quId = quId;
		this.quizName = quizName;
		this.quizDescription = quizDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.question = question;
		this.necessary = necessary;
		this.type = type;
		this.options = options;
		this.published = published;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getQuId() {
		return quId;
	}

	public void setQuId(int quId) {
		this.quId = quId;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public String getQuizDescription() {
		return quizDescription;
	}

	public void setQuizDescription(String quizDescription) {
		this.quizDescription = quizDescription;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Boolean getNecessary() {
		return necessary;
	}

	public void setNecessary(Boolean necessary) {
		this.necessary = necessary;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

}
