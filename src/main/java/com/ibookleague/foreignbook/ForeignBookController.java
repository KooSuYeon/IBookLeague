package com.ibookleague.foreignbook;


import com.ibookleague.foreignbook.domain.ForeignBook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequestMapping("/ibookleague/foreignbook")
@Controller
public class ForeignBookController
{

    private final ForeignBookService foreignBookService;

    public ForeignBookController(ForeignBookService foreignBookService) {
        this.foreignBookService = foreignBookService;
    }


    @GetMapping("/ranking")
    public String list(Model model)
    {
        List<ForeignBook> winNobelFalseAndWinHugoFalseBooks = this.foreignBookService.getwinNobelFalseAndWinHugoFalseList();
        List<ForeignBook> winNobelBooks = this.foreignBookService.getNobelList();

        // 새로운 리스트에 두 리스트의 모든 요소를 추가
        List<ForeignBook> combinedList = new ArrayList<>(winNobelFalseAndWinHugoFalseBooks);
        combinedList.addAll(winNobelBooks);
        Collections.sort(combinedList, Comparator.comparing(ForeignBook::calculateAvg).reversed());
        model.addAttribute("combinedList", combinedList);
        return "foreign_book_list";
    }

    @GetMapping("/nobelprize")
    public String nobelPrize(Model model)
    {
        List<ForeignBook> winNobelBooks = this.foreignBookService.getNobelList();
        Collections.sort(winNobelBooks, Comparator.comparing(ForeignBook::calculateAvg).reversed());
        model.addAttribute("winNobelBooks", winNobelBooks);
        return "nobelprize_list";
    }

    @GetMapping("/hugoaward")
    public String hugoAward(Model model)
    {
        List<ForeignBook> winHugoBooks = this.foreignBookService.getHugoList();
        Collections.sort(winHugoBooks, Comparator.comparing(ForeignBook::calculateAvg).reversed());
        model.addAttribute("winHugoBooks", winHugoBooks);
        return "hugoaward_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        ForeignBook foreignBook = this.foreignBookService.getBook(id);
        model.addAttribute("foreignBook", foreignBook);
        return "foreign_book_detail";
    }

    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        ForeignBook foreignBook = this.foreignBookService.getBook(id);
        this.foreignBookService.delete(foreignBook);
        return "redirect:/ibookleague/foreignbook/list";
    }

    @GetMapping("images/{filename}")
    public ResponseEntity<Resource> showImages(@PathVariable String filename) throws MalformedURLException {
        String path = "foreginBook_image/";
        Resource resource = new ClassPathResource(path + filename + ".png");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-type", "image/png");
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);

    }
}
