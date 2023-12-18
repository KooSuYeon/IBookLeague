package com.ibookleague.foreign_rate;


import com.ibookleague.book.domain.Book;
import com.ibookleague.foreign_rate.domain.ForeignRate;
import com.ibookleague.foreignbook.ForeignBookService;
import com.ibookleague.foreignbook.domain.ForeignBook;
import com.ibookleague.rate.domain.Rate;
import com.ibookleague.user.UserService;
import com.ibookleague.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ibookleague/foreignbook/rate")
@RequiredArgsConstructor
@Controller
public class ForeignRateController {

    private final ForeignBookService foreignBookService;
    private final ForeignRateService foreignRateService;
    private final UserService userService;

    @PostMapping("/create/{id}")
    public String createRate(Model model, @PathVariable("id") Integer id, HttpServletRequest request, Authentication authentication)
    {
        ForeignBook foreignBook = this.foreignBookService.getBook(id);

        User user = null;
        if (authentication != null && authentication.isAuthenticated())
        {
            String email = authentication.getName();
            user = this.userService.getUser(email);
        }

        // 지금 로그인한 사용자가 이전에 해당 책에 대한 평점을 남겼는지 검사
        // if 남긴 적이 없다면 평점을 새로 생성하기
        if (!foreignRateService.hasUserRatedBook(user, foreignBook))
        {
            int rateCode = Integer.parseInt(request.getParameter("rate"));
            this.foreignRateService.create(foreignBook, rateCode, user);
        }
        // else 남긴 적이 있다면 화면에 경고 메시지 띄우기 -> 로그인 하지 않은 사용자도 warningMessage를 보냄..
        else if (authentication != null && authentication.isAuthenticated())
        {
            model.addAttribute("warningMessage", "이미 평가한 책입니다.");
        }
        return String.format("redirect:/ibookleague/foreignbook/detail/%s", id);
    }


    // 이미 평점에 대해서는 사용자가 일대일 대응이 되어 있음.
    // id -> 평점 id
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String rateModify(HttpServletRequest request,  @PathVariable("id") Integer id)
    {
        ForeignRate foreignRate = this.foreignRateService.getRate(id);
        int new_rateCode = Integer.parseInt(request.getParameter("newrate"));
        this.foreignRateService.modify(foreignRate, new_rateCode);
        return String.format("redirect:/ibookleague/foreignbook/detail/%s", foreignRate.getForeignBook().getId());

    }

}
