package com.example.quiz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.BaseRes;
import com.example.quiz.vo.CreateOrUpdateReq;

@SpringBootTest
public class QuizServiceTest {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuizService quizService;

	@BeforeEach
	private void addData() {
		System.out.println("teach each test");
	}

	@BeforeAll
	private static void testAdd() {
		System.out.println("before all test");
	}

	@AfterEach
	private void aftereach() {
		quizDao.deleteById(null);
		System.out.println("after each test");
	}

	@AfterAll
	private void afterall() {
		System.out.println("after all test");
	}

	@Test
	public void createTest() {
		CreateOrUpdateReq req = new CreateOrUpdateReq();
		BaseRes res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test fail");
		quizIdTest(req, res);
		quIdTest(req, res);
		// ================ 測試 quizId
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test quizId fail");
		// ================ 測試 quId
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test quId fail");
		// ================= 測試 quizName
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, -1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test quizName fail");
		// ================= 測試 startDate
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test startDate fail");
		// ================= 測試 endDate
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test endDate fail");
		// ================= 測試 question 問題名稱
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test question fail");
		// ================= 測試 type
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test type fail");
		// ================= 測試 startDate > endDate
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test date range fail");
		// ================= 測試成功
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, -1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 200, "create test success fail");
		// ================= 測試已存在資料
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test fail");
		// ================= 刪除測試資料
	}

	private Quiz newQuiz() {
		return new Quiz(2, 2, "test", "test", LocalDate.now().plusDays(2), LocalDate.now().plusDays(9), "q_test", true,
				"single", "A;B;C;D", false);

	}

	private void quizIdTest(CreateOrUpdateReq req, BaseRes res) {
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test quizId fail");
	}

	private void quIdTest(CreateOrUpdateReq req, BaseRes res) {
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "q_test", true, "single", "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test quizId fail");
	}

	@Test
	public void updateTest() {
		addData();
	}

}
