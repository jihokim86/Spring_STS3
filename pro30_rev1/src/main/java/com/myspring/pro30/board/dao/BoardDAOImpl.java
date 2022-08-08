package com.myspring.pro30.board.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;


@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List selectAllArticlesList() throws DataAccessException {
		List<ArticleVO> articlesList = sqlSession.selectList("mapper.board.selectAllArticlesList");
		return articlesList;
	}

	
	@Override
	public int insertNewArticle(Map articleMap) throws DataAccessException {
		int articleNO = selectNewArticleNO();  //max No + 1 시켜 저장한다.
		
		articleMap.put("articleNO", articleNO);
		
		sqlSession.insert("mapper.board.insertNewArticle",articleMap);
		
		return articleNO;
	}
    
	//다중 파일 업로드
	
	@Override
	public void insertNewImage(Map articleMap) throws DataAccessException {
		
		List<ImageVO> imageFileList = (ArrayList)articleMap.get("imageFileList");
		
		//articleMap안에는 
		//articleMap.put(name,value) ,articleMap.put("id",id);, articleMap.put("parentNO", 0);
		//articleMap.put("imageFileList", imageFileList);
		
		
		int articleNO = (Integer)articleMap.get("articleNO"); //service에서 put .....넣어버렸네.......ㄷㄷㄷ
		
		
		int imageFileNO = selectNewImageFileNO(); //이미지파일의 maxNO?값?
		
		
		for(ImageVO imageVO : imageFileList){
			imageVO.setImageFileNO(++imageFileNO);
			imageVO.setArticleNO(articleNO);
		}
		sqlSession.insert("mapper.board.insertNewImage",imageFileList);
	}
	
  
	
	@Override
	public ArticleVO selectArticle(int articleNO) throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectArticle", articleNO);
	}

	@Override
	public void updateArticle(Map articleMap) throws DataAccessException {
		sqlSession.update("mapper.board.updateArticle", articleMap);
	}

	@Override
	public void deleteArticle(int articleNO) throws DataAccessException {
		sqlSession.delete("mapper.board.deleteArticle", articleNO);
		
	}
	
	@Override
	public List selectImageFileList(int articleNO) throws DataAccessException {
		List<ImageVO> imageFileList = null;
		imageFileList = sqlSession.selectList("mapper.board.selectImageFileList",articleNO);
		return imageFileList;
	}
	
	private int selectNewArticleNO() throws DataAccessException { // max No에 +1을 해준다.
		return sqlSession.selectOne("mapper.board.selectNewArticleNO");
	}
	
	//<select id="selectNewArticleNO" resultType="int"  >
	//		<![CDATA[
	//			SELECT  nvl(max(articleNO), 0) + 1 from t_board		
	//		]]>
	//	</select>
	
	private int selectNewImageFileNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewImageFileNO");
	}
	
	 // <select id="selectNewImageFileNO" resultType="int"  >
	 // <![CDATA[
	 //   SELECT  nvl(max(imageFileNO),0) from t_imageFile		
	 // ]]>
	 //  </select>

}
