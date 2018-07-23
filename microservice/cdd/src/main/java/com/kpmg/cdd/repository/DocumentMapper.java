package com.kpmg.cdd.repository;

import java.util.List;

import com.kpmg.cdd.entity.Document;
import org.apache.ibatis.annotations.Param;

public interface DocumentMapper {
	int insert(Document record);



	Document selectById(@Param("id") String id);
	List<Document> selectByCaseId(@Param("caseId") String caseId);

	int updateUploadUrl(Document record);

	int deleteListByActivity(@Param("activityId") String activityId,@Param("caseId") String caseId);
}