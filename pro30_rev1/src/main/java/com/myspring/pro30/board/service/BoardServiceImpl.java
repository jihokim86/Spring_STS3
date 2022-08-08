package com.myspring.pro30.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro30.board.dao.BoardDAO;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;


@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl  implements BoardService{
	@Autowired
	private BoardDAO boardDAO;
	
	public List<ArticleVO> listArticles() throws Exception{
		List<ArticleVO> articlesList =  boardDAO.selectAllArticlesList();
        return articlesList;
	}

	/*
	//단일 이미지 추가하기
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		return boardDAO.insertNewArticle(articleMap);
	}
	*/
	 //다중 이미지 추가하기
	
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		
		int articleNO = boardDAO.insertNewArticle(articleMap);
		
		articleMap.put("articleNO", articleNO); //최대 NO를 반환받아서
		
		boardDAO.insertNewImage(articleMap); 
		//articleMap안에는 
		//articleMap.put(name,value) ,articleMap.put("id",id);, articleMap.put("parentNO", 0);
		//articleMap.put("imageFileList", imageFileList);
		// 쿼리문에 필요한 파라미터가 무엇인가 고민해봐야한다!! 
		// 어쩌면 쿼리문부터 작성하는게 먼저일듯하다.
		
		return articleNO;
	}
	
	
	//다중 파일 보이기
	@Override
	public Map viewArticle(int articleNO) throws Exception {
		
		Map articleMap = new HashMap();
		
		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
		List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNO);
		
		articleMap.put("article", articleVO);
		articleMap.put("imageFileList", imageFileList);
		return articleMap;
	}
	//왜 map으로 해야하는가? jsp를 봐야할듯하다.
	// map안에 vo와 리스트를 저장한것같다!! 맞나? 왜 이렇게 했을까?
	// VO가 두개 있으니~map에 저장해서 넘겨주었다.
	/*
	 //단일 파일 보이기
	@Override
	public ArticleVO viewArticle(int articleNO) throws Exception {
		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
		return articleVO;
	}
	*/
	
	@Override
	public void modArticle(Map articleMap) throws Exception {
		boardDAO.updateArticle(articleMap);
	}
	
	@Override
	public void removeArticle(int articleNO) throws Exception {
		boardDAO.deleteArticle(articleNO);
	}
	
}
