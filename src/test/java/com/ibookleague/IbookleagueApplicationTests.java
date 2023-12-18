package com.ibookleague;

import com.ibookleague.book.domain.Book;
import com.ibookleague.book.BookRepository;
import com.ibookleague.foreignbook.domain.ForeignBook;
import com.ibookleague.foreignbook.ForeignBookRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;

@SpringBootTest
class IbookleagueApplicationTests {

	@Autowired
	private ForeignBookRepository foreignBookRepository;

	@Autowired
	private BookRepository bookRepository;

	//노벨 문학상 책 데이터 추가 - 책 내용 추가함.
	@Test
	void testNobelForeign() {
		try
		{

			FileInputStream file = new FileInputStream("src/excel/nobelprize.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex=0;
			XSSFSheet sheet=workbook.getSheetAt(0);
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();
			for(rowindex=1;rowindex<rows;rowindex++){
				//행을 읽는다
				XSSFRow row=sheet.getRow(rowindex);
				if(row !=null){
					XSSFCell year = row.getCell(1);
					XSSFCell name = row.getCell(2);
					XSSFCell author = row.getCell(3);
					XSSFCell content = row.getCell(4);
					String book_name = String.valueOf(name);
					String author_name = String.valueOf(author);
					String content_text = String.valueOf(content);
					String year_s = String.valueOf(year);
					Integer year_i = Integer.parseInt(year_s.split("\\.")[0]);
					System.out.println(rowindex+"작품명 : " + name + "      작가명 : " + author + " 	내용 : " + content_text);
					ForeignBook b = new ForeignBook();
					b.setSubject(book_name);
					b.setAuthor(author_name);
					b.setContent(content_text);
					b.setWinNobel(true);
					b.setWinNobelYear(year_i);
					b.setWinHugo(false);
					foreignBookRepository.save(b);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// 휴고 상
	@Test
	void testHugoForeign() {
		try
		{

			FileInputStream file = new FileInputStream("src/excel/hugoprize.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex=0;
			XSSFSheet sheet=workbook.getSheetAt(0);
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();
			for(rowindex=1;rowindex<rows;rowindex++){
				//행을 읽는다
				XSSFRow row=sheet.getRow(rowindex);
				if(row !=null){
					XSSFCell year = row.getCell(1);
					XSSFCell name = row.getCell(2);
					XSSFCell author = row.getCell(3);
					XSSFCell content = row.getCell(4);
					String book_name = String.valueOf(name);
					String author_name = String.valueOf(author);
					String content_text = String.valueOf(content);
					String year_s = String.valueOf(year);
					Integer year_i = Integer.parseInt(year_s.split("\\.")[0]);
					System.out.println(rowindex+"작품명 : " + name + "      작가명 : " + author + " 	내용 : " + content_text);
					ForeignBook b = new ForeignBook();
					b.setSubject(book_name);
					b.setAuthor(author_name);
					b.setContent(content_text);
					b.setWinNobel(false);
					b.setWinHugoYear(year_i);
					b.setWinHugo(true);
					foreignBookRepository.save(b);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//외국 문학 책 데이터 추가 - 책 내용추가함.
	@Test
	void testForeign() {
		try
		{

			FileInputStream file = new FileInputStream("src/excel/foreign_literary.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex=0;
			XSSFSheet sheet=workbook.getSheetAt(0);
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();
			for(rowindex=1;rowindex<rows;rowindex++){
				//행을 읽는다
				XSSFRow row=sheet.getRow(rowindex);
				if(row !=null){
					XSSFCell name = row.getCell(1);
					XSSFCell author = row.getCell(2);
					XSSFCell content = row.getCell(3);
					String book_name = String.valueOf(name);
					String author_name = String.valueOf(author);
					String content_text = String.valueOf(content);
					System.out.println(rowindex+"작품명 : " + name + "      작가명 : " + author + " 	내용 : " + content_text);
					ForeignBook b = new ForeignBook();
					b.setSubject(book_name);
					b.setAuthor(author_name);
					b.setContent(content_text);
					b.setWinNobel(false);
					b.setWinHugo(false);
					foreignBookRepository.save(b);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	void testKorean() {
		try
		{

			FileInputStream file = new FileInputStream("src/excel/korean_literary.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex=0;
			XSSFSheet sheet=workbook.getSheetAt(0);
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();
			for(rowindex=1;rowindex<rows;rowindex++){
				//행을 읽는다
				XSSFRow row=sheet.getRow(rowindex);
				if(row !=null){
					XSSFCell name = row.getCell(1);
					XSSFCell author = row.getCell(2);
					XSSFCell content = row.getCell(3);
					String book_name = String.valueOf(name);
					String author_name = String.valueOf(author);
					String content_text = String.valueOf(content);
					System.out.println(rowindex+"작품명 : " + name + "      작가명 : " + author + " 	내용 : " + content_text);
					Book b = new Book();
					b.setSubject(book_name);
					b.setAuthor(author_name);
					b.setContent(content_text);
					bookRepository.save(b);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
