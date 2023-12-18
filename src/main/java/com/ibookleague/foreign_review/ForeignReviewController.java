package com.ibookleague.foreign_review;


import com.ibookleague.foreignbook.ForeignBookService;
import com.ibookleague.foreignbook.domain.ForeignBook;
import com.ibookleague.user.UserService;
import com.ibookleague.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ForeignReviewController {

    private final ForeignBookService foreignBookService;
    private final ForeignReviewService foreignReviewService;
    private final UserService userService;

    @PostMapping("/ibookleague/foreignreview/create/{id}")
    public String createReview(Model model, @PathVariable("id") Integer id, @RequestParam String content, Authentication authentication)
    {

        User user = null;
        if (authentication != null && authentication.isAuthenticated())
        {
            String email = authentication.getName();
            user = this.userService.getUser(email);
        }

        ForeignBook foreignbook = this.foreignBookService.getBook(id);
        this.foreignReviewService.create(foreignbook, content, user);
        return String.format("redirect:/ibookleague/foreignbook/detail/%s", id);
    }
}
