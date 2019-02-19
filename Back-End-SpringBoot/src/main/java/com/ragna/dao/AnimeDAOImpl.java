package com.ragna.dao;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragna.pojos.Anime;

@Repository
public class AnimeDAOImpl implements AnimeDAO {

	/*@Value("${path-file-anime}")
	private String pathFile; */
	
	private File file = new File("src/main/resources/anime-list.json");
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void createAnime(Anime anime) throws IOException {
		List<Anime> animeList = this.getAnimes();
		animeList.add(anime);
		this.saveAnimes(animeList);
	}

	@Override
	public Anime getAnime(int id) throws IOException{
		List<Anime> animeList = this.getAnimes();
		Anime animeReturn = new Anime(); 
		for(Anime a : animeList) {
			if(a.getId() == id) {
				animeReturn = a;
				break;
			}
		}
		return animeReturn;
	}

	@Override
	public void updateAnime(Anime anime) throws IOException{
		List<Anime> animeList = this.getAnimes();
		Anime animeToDelete = new Anime(); 
		for(Anime a : animeList) {
			if(a.getId() == anime.getId()) {
				animeToDelete = a;
				break;
			}
		}
		animeToDelete = anime;
		animeList.remove(animeToDelete);
		animeList.add(anime.getId(), anime);
		this.saveAnimes(animeList);
	}

	@Override
	public void deleteAnime(int id) throws IOException{
		List<Anime> animeList = this.getAnimes();
		for(Anime a : animeList) {
			if(a.getId() == id) {
				animeList.remove(a);
				break;
			}
		}
	}
	
	@Override
	public void saveAnimes(List<Anime> animeList) throws IOException {
		new PrintWriter("src/main/resources/anime-list.json").close();
		this.mapper.writeValue(file, animeList);
	}

	@Override
	public List<Anime> getAnimes() throws IOException {
		
		List<Anime> animeList = this.mapper.readValue(this.file, new TypeReference<List<Anime>>() {});
		return animeList;
	}

}