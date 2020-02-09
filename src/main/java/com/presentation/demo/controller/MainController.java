package com.presentation.demo.controller;

import com.presentation.demo.exceptions.MailSendingException;
import com.presentation.demo.model.MobilePhoneNumber;
import com.presentation.demo.model.User;
import com.presentation.demo.pojo.MapEntryImpl;
import com.presentation.demo.service.mail.MailSendingService;
import com.presentation.demo.service.mobilephonenumber.MobilePhoneNumberService;
import com.presentation.demo.service.parser.ExchangeRateParserService;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.user.security.SecurityService;
import com.presentation.demo.service.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.presentation.demo.constants.Params.CB_URL;
import static com.presentation.demo.constants.Params.DEFAULT_REFERRER;

@Controller
public class MainController {

    private static final Logger MAIN_CONTROLLER_LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MobilePhoneNumberService mobilePhoneNumberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ExchangeRateParserService exchangeRateParserService;

    @Autowired
    private MailSendingService mailSendingService;

    @GetMapping(value = {"/index","/"})
    public String getIndex(Model model, @AuthenticationPrincipal User user) {
        boolean isAuthenticated = Objects.nonNull(user);
        String userName =  isAuthenticated ? user.getUsername() : null;
        model.addAttribute("userName", userName);

        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        if (isAuthenticated){
            namesLinksList.add(new MapEntryImpl<String,String>("Userpage","/userpage"));
            namesLinksList.add(new MapEntryImpl<String,String>("Home","/index"));
            namesLinksList.add(new MapEntryImpl<String,String>("Sign up","/registration"));
        }
        else{
            namesLinksList.add(new MapEntryImpl<String,String>("Home","/index"));
            namesLinksList.add(new MapEntryImpl<String,String>("Sign up","/registration"));
            namesLinksList.add(new MapEntryImpl<String,String>("Sign in","/login"));
        }
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));


        List<String> countryAbbrevations = new LinkedList<String>();
        countryAbbrevations.add("USD");
        countryAbbrevations.add("EUR");

        exchangeRateParserService.setReferrerUrl(DEFAULT_REFERRER);

        exchangeRateParserService.connect(CB_URL);

        MAIN_CONTROLLER_LOGGER.info(exchangeRateParserService.getStatusCode().toString());
        MAIN_CONTROLLER_LOGGER.info(exchangeRateParserService.getStatusMessage());

        List<MapEntryImpl<String,String>> currenciesRates = exchangeRateParserService.select(countryAbbrevations);

        model.addAttribute("currenciesRates", currenciesRates);
        model.addAttribute("date", new Date().toString());
        model.addAttribute("namesLinksList", namesLinksList);
        return "index";
    }

    @ExceptionHandler(MailSendingException.class)
    public final String MailSendingErrorHandler(MailSendingException mailException, Model model){
        User newUser = new User();
        newUser.setMobilePhoneNumber(new MobilePhoneNumber());
        model.addAttribute("user", newUser);
        model.addAttribute("email",String.format("Local mailbox \"%s\" is unavailable: user not found",mailException.getEmail()));
        return "registration";
    }

    @GetMapping("/error")
    @ResponseBody
    public String getError() {
        return "error";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }


    @GetMapping("/registration")
    public String getRegistration(Model model) {
        User newUser = new User();
        newUser.setMobilePhoneNumber(new MobilePhoneNumber());
        model.addAttribute("user", newUser);
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@Valid @ModelAttribute("user") User user, BindingResult resultUser,
                                   Model model){

        MobilePhoneNumber userMobilePhoneNumber = user.getMobilePhoneNumber();
        userValidator.validate(user,resultUser);

        if (resultUser.hasFieldErrors()){
            List<FieldError> validationErrors = resultUser.getFieldErrors();
            for(FieldError validationError: validationErrors){
                MAIN_CONTROLLER_LOGGER.info("In field " + "\"" + validationError.getField() +  "\""  + " : " + validationError.getDefaultMessage());
                model.addAttribute(validationError.getField(),validationError.getDefaultMessage());
            }
            return "registration";
        }
        else if (resultUser.hasGlobalErrors()){
            MAIN_CONTROLLER_LOGGER.info("Global errors!");
            return "registration";
        }
        else{
            String rawPassword = user.getPassword();
            userService.createUserWithActivationCode(user);
            mobilePhoneNumberService.save(userMobilePhoneNumber);
            securityService.autoLogin(user.getUsername(),rawPassword,user.getAuthorities());
            return "redirect:/userpage";
        }

    }

    @GetMapping("/login")
    public String getLogin(Model model, @AuthenticationPrincipal User authenticatedUser) {
        String authenticatedName = Objects.nonNull(authenticatedUser) ? authenticatedUser.getUsername() : null;
        model.addAttribute("authenticatedName",authenticatedName);
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String setLogin(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return "redirect:/index";
    }

    @GetMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") Integer id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return "Delete success!";
    }

    @GetMapping("/activate/{code}")
    public String activateAccount(@PathVariable String code, Model model){//todo:return from service - best practice?
        model.addAttribute("activation_message",userService.activateUser(code));
        return "activation";
    }

}
