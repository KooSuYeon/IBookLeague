package com.ibookleague.rate;


import com.ibookleague.book.BookService;
import com.ibookleague.book.domain.Book;
import com.ibookleague.config.UserSecurityService;
import com.ibookleague.rate.domain.Rate;
import com.ibookleague.user.UserService;
import com.ibookleague.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/ibookleague/book/rate")
@RequiredArgsConstructor
@Controller
public class RateController {

    private final BookService bookService;
    private final RateService rateService;
    private final UserService userService;

    // id -> 책 id
    @PostMapping("/create/{id}")
    public String createRate(Model model, @PathVariable("id") Integer id, HttpServletRequest request, Authentication authentication)
    {
        Book book = this.bookService.getBook(id);

        User user = null;
        if (authentication != null && authentication.isAuthenticated())
        {
            String email = authentication.getName();
            user = this.userService.getUser(email);
        }

        // 지금 로그인한 사용자가 이전에 해당 책에 대한 평점을 남겼는지 검사
        // if 남긴 적이 없다면 평점을 새로 생성하기
        if (!rateService.hasUserRatedBook(user, book))
        {
            int rateCode = Integer.parseInt(request.getParameter("rate"));
            this.rateService.create(book, rateCode, user);
        }
        // else 남긴 적이 있다면 화면에 경고 메시지 띄우기 -> 로그인 하지 않은 사용자도 warningMessage를 보냄..
        else if (authentication != null && authentication.isAuthenticated())
        {
            model.addAttribute("warningMessage", "이미 평가한 책입니다.");
        }
        return String.format("redirect:/ibookleague/book/detail/%s", id);
    }


    // 이미 평점에 대해서는 사용자가 일대일 대응이 되어 있음.
    // id -> 평점 id
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String rateModify(HttpServletRequest request,  @PathVariable("id") Integer id)
    {
        Rate rate = this.rateService.getRate(id);
        int new_rateCode = Integer.parseInt(request.getParameter("newrate"));
        this.rateService.modify(rate, new_rateCode);
        return String.format("redirect:/ibookleague/book/detail/%s", rate.getBook().getId());

    }

}
