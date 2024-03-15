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

	// �]�� id �����b D8 g4 AI(Auto Incremental), �ҥH�n�[�W������
	// GenerationType.IDENTITY �O���D�䪺�Ʀr�W����Ѹ�Ʈw
	// ���ݩʪ���ƫ��A�O Integer �ɡA�n�[
	// ���ݩʪ���ƫ��A int �ɡA�D����: ���Y�n�b�s�W��ƫ�A�Y�ɨ��o�ӵ���ƪ��y�����A�N�n�[
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;  // �y�����A�����n

	@Column(name = "quizId")
	private int quizId;  // �ݨ��s��: �i���o�Ӱ��D�ݩ���i�ݨ�

	@Column(name = "quId")
	private int quId;    // ���D�s��: �ĴX�D���D

	@Column(name = "phone")
	private String phone;    // ������X: �n�D�ϥΪ̶񪺸��

	@Column(name = "name")
	private String name;    // ������X: �n�D�ϥΪ̶񪺸��

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
