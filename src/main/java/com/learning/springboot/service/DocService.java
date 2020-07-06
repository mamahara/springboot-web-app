package com.learning.springboot.service;

import java.util.List;

import com.learning.springboot.model.Document;

public interface DocService {

	List<Document> findAllDocs(Long id);

}
