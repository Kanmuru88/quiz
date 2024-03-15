package com.example.quiz.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Answer;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.AnswerDao;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.AnswerReq;
import com.example.quiz.vo.BaseRes;
import com.example.quiz.vo.CreateOrUpdateReq;
import com.example.quiz.vo.SearchRes;
import com.example.quiz.vo.StatisticsRes;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private AnswerDao answerDao;

	@Override
	public BaseRes create(CreateOrUpdateReq req) {
		return checkParams(req, true);
	}

//		// 檢查必填項目
//		for (Quiz item : req.getQuizList()) {
//			if (item.getQuizId() == 0 || item.getQuId() == 0 || !StringUtils.hasText(item.getQuizName())
//					|| item.getStartDate() == null || item.getEndDate() == null
//					|| !StringUtils.hasText(item.getQuestion()) || !StringUtils.hasText(item.getType())) {
//				return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
//			}
//		}
	// 蒐集 req 中所有的 quizId
	// 原則上是一個 req 只會有一個 quizId 會相同，但也是有可能其中一筆資料的 quizId 是錯的
	// 為保證所有資料的正確性，就先去蒐集 req 中所有的 quizId
//		List<Integer> quizIds = new ArrayList<>(); // List 允許重複的值存在
//		for (Quiz item : req.getQuizList()) {
//			if (!quizIdList.contains(item.getQuizId())) {
//				quizIdList.add(item.getQuizId());
//			}
//		}
	// 以下用 set 的寫法與上面用 List的寫法結果一模一樣
//		Set<Integer> quizIds = new HashSet<>(); // set 不會存在相同的值，就是 set 中已存在相同的值，就不會新增
//		Set<Integer> quIds = new HashSet<>(); // 檢查問題編號是否有重複，正常應該是都不重複
//		for (Quiz item : req.getQuizList()) {
//			quizIds.add(item.getQuizId());
//			quIds.add(item.getQuId());
//		}
//		if (quizIds.size() != 1) {
//			return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
//		}
//
//		if (quIds.size() != req.getQuizList().size()) {
//			return new BaseRes(RtnCode.DUPLICATED_QUESTION.getCode(), RtnCode.DUPLICATED_QUESTION.getMessage());
//		}
//		// 檢查開始時間不能大於結束時間
//		for (Quiz item : req.getQuizList()) {
//			if (item.getStartDate().isAfter(item.getEndDate())) {
//				return new BaseRes(RtnCode.TIME_FORMAT_ERROR.getCode(), RtnCode.TIME_FORMAT_ERROR.getMessage());
//			}
//		}
//		BaseRes res = checkParams(req);
//		if (res != null) {
//			return res;
//		}
//		// 檢查問卷是否已存在
//		if (quizDao.existsByQuizId(req.getQuizList().get(0).getQuizId())) {
//			return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
//		}
//		// 根據是否要發布，再把 published 的值 Set 到傳送過來的 quizList 中
//		for (Quiz item : req.getQuizList()) {
//			item.setPublished(req.isPublished());
//		}
//		quizDao.saveAll(req.getQuizList());
//		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
//
//	}

	@Override
	public SearchRes search(String quizName, LocalDate startDate, LocalDate endDate, boolean isBackend) {
		if (!StringUtils.hasText(quizName)) {
			quizName = ""; // containing 帶的參數值為空字串，表示會撈取全部
		}
		if (startDate == null) {
			startDate = LocalDate.of(1970, 1, 1); // 將開始時間設定為很早之前的時間
		}
		if (endDate == null) {
			endDate = LocalDate.of(2077, 12, 31); // 將結束時間設定在很久之後的時間
		}
		if (isBackend) {
			return new SearchRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(),
					quizDao.findByQuizNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(quizName,
							startDate, endDate));
		} else {
			return new SearchRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(),
					quizDao.findByQuizNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(
							quizName, startDate, endDate));
		}
//		List<Quiz> res = quizDao.findByQuizNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(quizName,
//				startDate, endDate);

	}

	@Override
	public BaseRes deleteQuiz(List<Integer> quizIds) {
		if (CollectionUtils.isEmpty(quizIds)) { // 同時判斷 quizIds 是否為 null以及空集合
			return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		quizDao.deleteAllByQuizIdInAndPublishedFalseOrQuizIdInAndStartDateAfter(quizIds, quizIds, LocalDate.now());
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public BaseRes deleteQuestions(int quizId, List<Integer> quIds) {
		if (quizId <= 0 || CollectionUtils.isEmpty(quIds)) {
			return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		// 根據(quizId and 未發布) or (quizId and 尚未開始) 找問卷
		List<Quiz> res = quizDao.findByQuizIdAndPublishedFalseOrQuizIdAndStartDateAfterOrderByQuId(quizId, quizId,
				LocalDate.now());
		if (res.isEmpty()) {
			return new BaseRes(RtnCode.QUIZ_NOT_FOUND.getCode(), RtnCode.QUIZ_NOT_FOUND.getMessage());
		}
//		int j = 0;
//		for(int item : quIds) { // quIds = 1, 4
		// 1: j = 0, item = 1, - 1 - j = 1 - 1 - 0 = 0
		// 2: j = 1, item = 4, item - 1 - j = 4 -1 -1 = 2
//			res.remove(item - 1 -j);
//			j++;
//		}

//		for(int i =0; i < res.size(); i++) {
//		res.get(i).setQuId((i + 1);
//		}
		List<Quiz> retainList = new ArrayList<>();
		for (Quiz item : res) {
			if (!quIds.contains(item.getQuId())) { // 保留不在刪除清單中的
				retainList.add(item);
			}
		}
		for (int i = 0; i < retainList.size(); i++) {
			retainList.get(i).setQuId(i + 1);
		}
		// 刪除整張問卷
		quizDao.deleteByQuizId(quizId);
		// 將保留的問題存回 DB
		if (!retainList.isEmpty()) {
			quizDao.saveAll(retainList);
		}
		return new SearchRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public BaseRes update(CreateOrUpdateReq req) {
		return checkParams(req, false);
	}

//	// 檢查 這些資料是否是同一張問卷
//	Set<Integer> quizIdSet = new HashSet<>();for(
//	Quiz item:req.getQuizList())
//	{
//		quizIdSet.add(item.getQuizId());
//	}if(quizIdSet.size()!=1)
//	{ // 因為一次只能修改一次問卷
//		return new BaseRes(RtnCode.QUIZ_ID_ERROR.getCode(), RtnCode.QUIZ_ID_ERROR.getMessage());
//	} // 確認傳過來的 quizId 是否真的可以刪除(可以刪除的條件是: 尚未發布或尚未開始)
//	if(!quizDao.existsByQuizIdAndPublishedFalseOrQuizIdAndStartDateAfter(req.getQuizList().get(0).getQuizId(),req.getQuizList().get(0).getQuizId(),LocalDate.now()))
//	{
//		return new BaseRes(RtnCode.QUIZ_NOT_FOUND.getCode(), RtnCode.QUIZ_NOT_FOUND.getMessage());
//	}
//		// 刪除整張問卷
//		quizDao.deleteByQuizId(req.getQuizList().get(0).getQuizId());
//		// 根據是否要發布，再把 published 的值 Set 到傳送過來的 quizList 中
//		for (Quiz item : req.getQuizList()) {
//			item.setPublished(req.isPublished());
//		}
	// 存回 DB

//		quizDao.saveAll(req.getQuizList());
//		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());

	private BaseRes checkParams(CreateOrUpdateReq req, boolean isCreate) {
		if (CollectionUtils.isEmpty(req.getQuizList())) {
			return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		for (Quiz item : req.getQuizList()) {
			if (item.getQuizId() <= 0 || item.getQuId() <= 0 || !StringUtils.hasText(item.getQuizName())
					|| item.getStartDate() == null || item.getEndDate() == null
					|| !StringUtils.hasText(item.getQuestion()) || !StringUtils.hasText(item.getType())) {
				return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
			}
		}
		// 蒐集 req 中所有的 quizId
		// 原則上是一個 req 只會有一個 quizId 會相同，但也是有可能其中一筆資料的 quizId 是錯的
		// 為保證所有資料的正確性，就先去蒐集 req 中所有的 quizId
//	List<Integer> quizIds = new ArrayList<>(); // List 允許重複的值存在
//	for (Quiz item : req.getQuizList()) {
//		if (!quizIdList.contains(item.getQuizId())) {
//			quizIdList.add(item.getQuizId());
//		}
//	}
		// 以下用 set 的寫法與上面用 List的寫法結果一模一樣
		Set<Integer> quizIds = new HashSet<>(); // set 不會存在相同的值，就是 set 中已存在相同的值，就不會新增
		Set<Integer> quIds = new HashSet<>(); // 檢查問題編號是否有重複，正常應該是都不重複
		for (Quiz item : req.getQuizList()) {
			quizIds.add(item.getQuizId());
			quIds.add(item.getQuId());
		}
		if (quizIds.size() != 1) {
			return new BaseRes(RtnCode.QUIZ_ID_DOES_NOT_MATCH.getCode(), RtnCode.QUIZ_ID_DOES_NOT_MATCH.getMessage());
		}

		if (quIds.size() != req.getQuizList().size()) {
			return new BaseRes(RtnCode.DUPLICATED_QUESTION.getCode(), RtnCode.DUPLICATED_QUESTION.getMessage());
		}
		// 檢查開始時間不能大於結束時間
		for (Quiz item : req.getQuizList()) {
			if (item.getStartDate().isAfter(item.getEndDate())) {
				return new BaseRes(RtnCode.TIME_FORMAT_ERROR.getCode(), RtnCode.TIME_FORMAT_ERROR.getMessage());
			}
		}
		if (isCreate) {
			if (quizDao.existsByQuizId(req.getQuizList().get(0).getQuizId())) {
				return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
			}
		}
		if (isCreate) { // isCreate == true，執行原本 create 中的方法
			if (quizDao.existsByQuizId(req.getQuizList().get(0).getQuizId())) {
				return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
			}
		} else {// isCreate == false，執行原本 create 中的方法
			if (!quizDao.existsByQuizIdAndPublishedFalseOrQuizIdAndStartDateAfter(req.getQuizList().get(0).getQuizId(),
					req.getQuizList().get(0).getQuizId(), LocalDate.now())) {
				return new BaseRes(RtnCode.QUIZ_NOT_FOUND.getCode(), RtnCode.QUIZ_NOT_FOUND.getMessage());
			}
			quizDao.deleteByQuizId(req.getQuizList().get(0).getQuId());
		}

		// 防呆
		for (Quiz item : req.getQuizList()) {
			item.setPublished(req.isPublished());
		}
		quizDao.saveAll(req.getQuizList());
		return new SearchRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public BaseRes answer(AnswerReq req) {
		if (CollectionUtils.isEmpty(req.getAnswerList())) {
			return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		for (Answer item : req.getAnswerList()) {
			if (!StringUtils.hasText(item.getName()) || !StringUtils.hasText(item.getPhone())
					|| !StringUtils.hasText(item.getEmail()) || item.getQuizId() <= 0 || item.getQuId() <= 0
					|| item.getAge() < 0) {
				return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
			}
		}
		// 檢查資料列表中，所有的 quizId 都一樣，以及 quId 都不重複
		Set<Integer> quizIds = new HashSet<>(); // set 不會存在相同的值，就是 set 中已存在相同的值，就不會新增
		Set<Integer> quIds = new HashSet<>(); // 檢查問題編號是否有重複，正常應該是都不重複
		for (Answer item : req.getAnswerList()) {
			quizIds.add(item.getQuizId());
			quIds.add(item.getQuId());
		}
		if (quizIds.size() != 1) {
			return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
		}
		if (quIds.size() != req.getAnswerList().size()) {
			return new BaseRes(RtnCode.DUPLICATED_QUESTION.getCode(), RtnCode.DUPLICATED_QUESTION.getMessage());
		}
		// 檢查必填問題是否有回答
		List<Integer> res = quizDao.findQuIdsByQuizIdAndNecessaryTrue(req.getAnswerList().get(0).getQuizId());
		for (Answer item : req.getAnswerList()) {
			if (res.contains(item.getQuId()) && !StringUtils.hasText(item.getAnswer())) {
				return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
			}
		}
//		for (int item : res) {
//			Answer ans = req.getAnswerList().get(item - 1);
//			if (!StringUtils.hasText(ans.getAnswer())) {
//				return new BaseRes(RtnCode.QUESTION_NO_ANSWER.getCode(), RtnCode.QUESTION_NO_ANSWER.getMessage());
//			}
//		}
		answerDao.saveAll(req.getAnswerList());
		return new SearchRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

//	@Override
//	public BaseRes statistics(int quizId) {
//		if (quizId <= 0) {
//			return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
//		}
//		List<Answer> res = answerDao.findByQuizId(quizId);
//		// 答案與次數的 map
//		Map<String, Integer> answerCountMap = new HashMap<>();
//		// 處理單選題
//		for (Answer item : res) {
//			if (answerCountMap.containsKey(item.getAnswer())) {
//				// answerCountMap.get(key): 根據 key 取得對應的 value
//				int count = answerCountMap.get(item.getAnswer()) + 1;
//				answerCountMap.put(item.getAnswer(), count);
//			} else {
//				answerCountMap.put(item.getAnswer(), 1);
//			}
//		}

	@Override
	public StatisticsRes statistics(int quizId) {
		if (quizId <= 0) {
			return new StatisticsRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		// 撈取問卷取得問題的 type 是非簡答題
		List<Quiz> quizs = quizDao.findByQuizId(quizId);
		// qus 是非簡答題題目編號的集合
		List<Integer> qus = new ArrayList<>();
		// 若是簡答題 options 是空的
		for (Quiz item : quizs) {
			if (StringUtils.hasText(item.getOptions())) {
				qus.add(item.getQuId());
			}
		}
		List<Answer> answers = answerDao.findByQuizIdOrderByQuId(quizId);
		// quIdAnswerMap: 問題編號與答案的 mapping
		Map<Integer, String> quIdAnswerMap = new HashMap<>(); // map 相同 key 的值，後面覆蓋前面
		// 把非簡答題的答案串成字串
		for (Answer item : answers) {
			// 若是包含在 qus 此 List 中的，就表示是選擇題(單，多選)
			if (qus.contains(item.getQuId())) {
				// 若 key 已存在
				if (quIdAnswerMap.containsKey(item.getQuId())) {
					// 1. 透過 key 取得對應的 value
					String str = quIdAnswerMap.get(item.getQuId());
					// 2. 把原有的值和這次取得的值串接變成新的值
					str += item.getAnswer();
					// 3. 將新的值放回到原本的 key 之下
					quIdAnswerMap.put(item.getQuId(), str);
				} else { // key 不存在，是直接新增 key 和 value
					quIdAnswerMap.put(item.getQuId(), item.getAnswer());
				}

			}
		}
		// 計算每題每個選項的次數
		// Map 中的 Map<String, Integer>
		Map<Integer, Map<String, Integer>> quizIdAndAnsCountMap = new HashMap<>();
		// 使用 foreach 遍歷 map 中的每個項目
		// 遍歷的對象要從 map 轉成 entrySet，好處就是可以直接取得 map 中的 key 和 value
		for (Entry<Integer, String> item : quIdAnswerMap.entrySet()) {
			// answerCountMap: 選項(答案)與次數的 mapping
			Map<String, Integer> answerCountMap = new HashMap<>();
			// 取得每個問題的選項
			String[] optionList = quizs.get(item.getKey() - 1).getOptions().split(";");
			// 把問題的選項與次數做 mapping
			for (String option : optionList) {
				String newStr = item.getValue();
				int length = newStr.length();
				newStr = newStr.replace(option, "");
				int length2 = newStr.length();
				// 要除 option 的原因是 option 是選項的內容，而不是選項的編號
				int count = (length - length2) / option.length();
				answerCountMap.put(option, count);
			}
			quizIdAndAnsCountMap.put(item.getKey(), answerCountMap);
		}
		return new StatisticsRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

}
