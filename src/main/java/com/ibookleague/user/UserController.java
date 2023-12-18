package com.ibookleague.user;

import com.ibookleague.foreign_rate.ForeignRateService;
import com.ibookleague.foreign_rate.domain.ForeignRate;
import com.ibookleague.foreign_review.ForeignReviewRepository;
import com.ibookleague.foreign_review.domain.ForeignReview;
import com.ibookleague.rate.RateService;
import com.ibookleague.rate.domain.Rate;
import com.ibookleague.review.ReviewRepository;
import com.ibookleague.review.domain.Review;
import com.ibookleague.user.domain.User;
import com.ibookleague.user.dto.UserCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/ibookleague/user")
public class UserController {

    private final UserService userService;
    private final ForeignRateService foreignRateService;
    private final RateService rateService;
    private final ReviewRepository reviewRepository;
    private final ForeignReviewRepository foreignReviewRepository;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }


    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try
        {
            userService.create(userCreateForm.getEmail(), userCreateForm.getUsername(), userCreateForm.getPassword1());
        } catch(DataIntegrityViolationException e)
        {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/ibookleague/user/signup_success";
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup_success")
    public String signup_success(@RequestParam(name = "error", required = false) String error, Model model) {
        return "signup_success";
    }

    @GetMapping("/mypage")
    public String myPage(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        List<Rate> rates = rateService.getRateByUserId(user.getUserId());
        List<ForeignRate> foreignRates = foreignRateService.getRateByUserId(user.getUserId());
        List<Review> reviews = reviewRepository.findAllByUser(user);
        List<ForeignReview> foreignReviews = foreignReviewRepository.findAllByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("rates", rates);
        model.addAttribute("foreignRates", foreignRates);
        model.addAttribute("reviews", reviews);
        model.addAttribute("foreignReviews", foreignReviews);
        return "my_page";
    }

}
