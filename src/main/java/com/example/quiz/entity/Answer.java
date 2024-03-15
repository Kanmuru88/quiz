package com.example.quiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "answer")
public class Answer {

	// 因為 id 此欄位在 D8 g4 AI(Auto Incremental), 所以要加上此註釋
	// GenerationType.IDENTITY 是指主鍵的數字增長交由資料庫
	// 當屬性的資料型態是 Integer 時，要加
	// 當屬性的資料型態 int 時，非必須: 但若要在新增資料後，即時取得該筆資料的流水號，就要加
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;  // 流水號，不重要

	@Column(name = "quizId")
	private int quizId;  // 問卷編號: 告知這個問題屬於哪張問卷

	@Column(name = "quId")
	private int quId;    // 問題編號: 第幾題問題

	@Column(name = "phone")
	private String phone;    // 手機號碼: 要求使用者填的資料

	@Column(name = "name")
	private String name;    // 手機號碼: 要求使用者填的資料

	@Column(name = "answer")
	private String answer;

	@Column(name = "age")
	private int age;

	@Column(name = "email")
	private String email;

	public Answer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Answer(int quizId, int quId, String phone, String name, String answer, int age, String email) {
		super();
		this.quizId = quizId;
		this.quId = quId;
		this.phone = phone;
		this.name = name;
		this.answer = answer;
		this.age = age;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
