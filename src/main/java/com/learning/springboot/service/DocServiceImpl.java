package com.learning.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.springboot.model.Document;
import com.learning.springboot.repository.DocRepository;

@Service
public class DocServiceImpl implements DocService {

	@Autowired
	private DocRepository docRepository;

	@Override
	public List<Document> findAllDocs(Long id) {
		return docRepository.findUserDocs(id);
	}

}
