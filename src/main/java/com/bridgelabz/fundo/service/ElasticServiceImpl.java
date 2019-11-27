package com.bridgelabz.fundo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.exception.UserNotFoundException;
import com.bridgelabz.fundo.model.Note;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ElasticServiceImpl implements ElasticService {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper mapper;

	private static final String index = "fundoonotes";

	private static final String type = "notes";

	@Override
	public void save(Note note) {
		try {
			Map<String, String> noteMap = mapper.convertValue(note, Map.class);
			IndexRequest request = new IndexRequest(index, type).id("" + note.getId()).source(noteMap);
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
			log.info(response.status().toString());
		} catch (IOException e) {
			throw new UserNotFoundException("Invalid");
		}
	}

//	curl -XGET 'http://localhost:9200/notes_search/pretty=true' 
	@Override
	public void update(Note note) {
		
		Map<String, String> noteMap = mapper.convertValue(note, Map.class);
		UpdateRequest request = new UpdateRequest(index, type, note.getId() + "");
		request.doc(noteMap);
		try {
			UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
			log.info(response.status().toString());
		} catch (IOException e) {
			throw new UserNotFoundException("Invalid EmailId");
		}
	}

	@Override
	public void delete(Integer noteId) {
		DeleteRequest request = new DeleteRequest(index, type, noteId+"");
		try {
			DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
			log.info(response.toString());
		} catch (IOException e) {
			new UserNotFoundException("Invalid EmailId");
		}
	}

	@Override
	public List<Note> search(String search, String field) {
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.termQuery(field, search));
		SearchRequest request = new SearchRequest("fundoonotes");
		request.source(builder);
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(field, search);
		matchQueryBuilder.fuzziness(Fuzziness.AUTO);
		matchQueryBuilder.prefixLength(2);
		matchQueryBuilder.maxExpansions(10);
		
		builder.query(matchQueryBuilder);
		
		List<Note> notes = new ArrayList<Note>();

		try {

			SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);

			searchResponse.getHits().spliterator().forEachRemaining(note -> {
				try {
					notes.add(mapper.readValue(note.getSourceAsString(), Note.class));
				} catch (JsonParseException | JsonMappingException e) {
					throw new UserNotFoundException(e.getMessage());
				} catch (IOException e) {
					throw new UserNotFoundException(e.getMessage());
				}
			});
		} catch (IOException e) {
			throw new UserNotFoundException(e.getMessage());
		}
		System.out.println(notes);
		return notes;
	}

}
