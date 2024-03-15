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
	private int quizId; // �ݨ��s��: �ĴX�i�ݨ�

	@Id
	@Column(name = "qu_id")
	@JsonProperty("qu_id")
	private int quId; // ���D�s��: �ĴX�D���D

	@Column(name = "quiz_name")
	@JsonProperty("qu_name")
	private String quizName; // �ݨ��W��: ex.�ҵ{���N�׽լd

	@Column(name = "quiz_description")
	@JsonProperty("quiz_description")
	private String quizDescription; // �ݨ��y�z: ex.�����ɱоǫ~��blablabla�A�ΨӴy�z�ݨ����ت��ά�������

	@Column(name = "start_date")
	@JsonProperty("start_date")
	private LocalDate startDate; // �_�l�ɶ�: �ݨ�����ɭԶ}��@��

	@Column(name = "end_date")
	@JsonProperty("end_date")
	private LocalDate endDate; // �����ɶ�: �ݨ�����ɭԵ����@��

	@Column(name = "question")
	@JsonProperty("question")
	private String question; // ���D���e: ex.�z��󩨲��Ѯv���оǺ��N�׬O ?

	@Column(name = "necessary")
	@JsonProperty("is_necessary")
	private Boolean necessary; // ����: ���D�O�_������g

	@Column(name = "published")
	@JsonProperty("is_published")
	private Boolean published; // �O�_�o��: �o�i�ݨ��O�_�n���i�b�e�x�A���i�Ӻ������X�Ȭݨ�

	@Column(name = "type")
	@JsonProperty("question_type")
	private String type; // �D��: ����D�B�h���D�B²���D

	@Column(name = "options")
	@JsonProperty("question_options")
	private String options; // �ﶵ: �p�G�O����D���ܡA�|�O�� ; �j�}���ﶵ ex.�D�`���N;���N;�����N;�D�`�����N
							// �p�G�O²���D���ܡA����

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
