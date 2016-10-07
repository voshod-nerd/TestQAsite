package com.infiniteskills.mvc.controllers;

import com.infiniteskills.mvc.entity.Answers;
import com.infiniteskills.mvc.entity.Category;
import com.infiniteskills.mvc.entity.Questions;
import com.infiniteskills.mvc.entity.Typeusers;
import com.infiniteskills.mvc.entity.Users;
import com.infiniteskills.mvc.repository.AnswersRepository;
import com.infiniteskills.mvc.repository.CategoryRepository;
import com.infiniteskills.mvc.repository.QuestionRepository;
import com.infiniteskills.mvc.repository.TypeUsersRepository;
import com.infiniteskills.mvc.repository.UsersRepository;
import java.util.Date;

import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Main controller of program,which process request from clients page
 *
 * @author Олег Соколов
*
 */
@Controller
public class IndexController {

    private static final Logger log = Logger.getLogger(IndexController.class.getName());
    private UsersRepository usersDAO;
    private TypeUsersRepository typeUserDAO;
    private CategoryRepository categoryDAO;
    private QuestionRepository questionDAO;
    private AnswersRepository answerDAO;

    @Autowired(required = false)
    public void setAnswersRepository(AnswersRepository answerDAO) {
        this.answerDAO = answerDAO;
    }

    @Autowired(required = false)
    public void setQuestionRepository(QuestionRepository questionDAO) {
        this.questionDAO = questionDAO;
    }

    @Autowired(required = false)
    public void setUsersRepository(UsersRepository userDAO) {
        this.usersDAO = userDAO;
    }

    @Autowired(required = false)
    public void setCategoryRepository(CategoryRepository categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Autowired(required = false)
    public void setTypeUserRepository(TypeUsersRepository typeUserDAO) {
        this.typeUserDAO = typeUserDAO;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String goEnter(ModelMap model) {
        log.info("Это информационное сообщение!");
        List<Category> allCategories = categoryDAO.findAll();
        List<Questions> allQuestion = questionDAO.findAll();
        model.addAttribute("listCategories", allCategories);
        model.addAttribute("listQuestions", allQuestion);
        return "index";
    }

    /*
    * Method return name  main jsp page of site
    @return name of jsp page  
     */
    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String goIndex(ModelMap model) {
        List<Category> allCategories = categoryDAO.findAll();
        List<Questions> allQuestion = questionDAO.findAll();
        model.addAttribute("listCategories", allCategories);
        model.addAttribute("listQuestions", allQuestion);
        return "index";
    }

    /*
    * Method return name  main jsp with certains category goods page
    @param Int category of foods
    @param ModelMap
    @return name of jsp page  
     */
    @RequestMapping(path = "/indexcat", method = RequestMethod.GET)
    public String goIndexCategory(@RequestParam("category") Integer category, ModelMap model) {

        List<Category> allCategories = categoryDAO.findAll();
        List<Questions> allQuestion = questionDAO.getQuestionsByCategory(category);
        model.addAttribute("listCategories", allCategories);
        model.addAttribute("listQuestions", allQuestion);
        return "index";
    }

    
     /*
    * Method return name  login page
    @return name of jsp page  
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String goLoginUser() {
        return "login";
    }

    
     /*
    * Method return name   jsp page to add answer to certain question
    @param Id of question
    @param ModelMap
    @return name of jsp page  
     */
    @RequestMapping(value = "/addanswer", method = RequestMethod.GET)
    public String addAnswer(@RequestParam("id") Integer id, ModelMap model) {

        Questions question = questionDAO.getById(id);
        List<Answers> listAnswers = answerDAO.getByQuestion(question);

        for (Answers x : listAnswers) {
            System.out.println(x.getText());
        }

        model.addAttribute("listAnswers", listAnswers);
        model.addAttribute("question", question);

        return "addanswer";
    }

    
     /*
    * Method return name jsp page to adding question
    @param ModelMap
    @return name of jsp page  
     */
    @RequestMapping(value = "/addquestion", method = RequestMethod.GET)
    public String addQuestion(ModelMap model) {

        List<Category> allCategories = categoryDAO.findAll();

        model.addAttribute("listCategories", allCategories);

        return "addquestion";
    }
    
     /*
    * Method gets question from client and save it  
    @param name of question
    @param fulltext of question
    @param category of foods
     
     */
    @RequestMapping(value = "/postquestion", method = RequestMethod.POST)
    public String postQuestion(@RequestParam("name") String name, @RequestParam("fulltext") String fulltext, @RequestParam("category") int category) {

        Questions question = new Questions();
        question.setDateadd(new Date());
        question.setFulltext(fulltext);
        question.setName(name);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = usersDAO.findUserByLogin(username);

        question.setIduser(user);
        List<Category> list = categoryDAO.findAll();
        Category cat = null;
        for (Category x : list) {
            if ((x.getId().compareTo(category) == 0)) {
                cat = x;
                break;
            }
        }
        question.setIdcategory(cat);
        questionDAO.create(question);
        return "redirect:index";
    }

    /* Method gets answer from client and save it  
    *  @param id question
    *  @param textanswer - full text answer
     */
    @RequestMapping(value = "/postanswer", method = RequestMethod.POST)
    public String postAnswer(@RequestParam("question") Integer idQuestion, @RequestParam("textanswer") String text) {

        Answers answer = new Answers();
        answer.setDateadd(new Date());
        answer.setText(text);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = usersDAO.findUserByLogin(username);
        answer.setIduser(user);
        Questions question = questionDAO.getById(idQuestion);
        answer.setIdquestion(question);
        answerDAO.create(answer);
        return "redirect:addanswer?id=" + question.getId().toString();
    }
    
    /* Method return name   jsp page to view profil user 
    @param ModelMap
    @return name of jsp page
    */
    @RequestMapping(value = "/viewprofil", method = RequestMethod.GET)
    public String viewProfil(ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = usersDAO.findUserByLogin(username);
        model.addAttribute("user", user);
        return "viewprofil";
    }
    
     /* Method return name   jsp page to edit profil user 
    @param ModelMap
    @return name of jsp page
    */
    @RequestMapping(value = "/editprofil", method = RequestMethod.GET)
    public String editProfil(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = usersDAO.findUserByLogin(username);
        model.addAttribute("user", user);

        return "editprofil";
    }
    
     /* Method return name login jsp page and save name,email and password new registered user 
    @param Users class 
    @return name of login jsp page
    */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String testValidation(@Valid Users user) {
        Typeusers typeUser = typeUserDAO.getTypeUserByName("ROLE_USER");
        user.setIdtypeuser(typeUser);
        BCryptPasswordEncoder n = new BCryptPasswordEncoder();
        user.setPassword(n.encode(user.getPassword()));
        usersDAO.create(user);
        return "login";
    }

}
