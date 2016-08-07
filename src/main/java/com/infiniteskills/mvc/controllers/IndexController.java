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
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

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

        List<Category> allCategories = categoryDAO.findAll();
        List<Questions> allQuestion = questionDAO.findAll();
        model.addAttribute("listCategories", allCategories);
        model.addAttribute("listQuestions", allQuestion);
        return "index";
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String goIndex(ModelMap model) {
        List<Category> allCategories = categoryDAO.findAll();
        List<Questions> allQuestion = questionDAO.findAll();
        model.addAttribute("listCategories", allCategories);
        model.addAttribute("listQuestions", allQuestion);
        return "index";
    }
    
      @RequestMapping(path = "/indexcategory", method = RequestMethod.GET)
    public String goIndexCategory(@RequestParam("category") Integer category,ModelMap model) {
        
        List<Category> allCategories = categoryDAO.findAll();
        List<Questions> allQuestion = questionDAO.getQuestionsByCategory(category);
        model.addAttribute("listCategories", allCategories);
        model.addAttribute("listQuestions", allQuestion);
        return "index";
    }
    
    
    

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String logUser() {
        return "login";
    }

    @RequestMapping(value = "/addanswer", method = RequestMethod.GET)
    public String addAnswer(@RequestParam("id") Integer id, ModelMap model) {

        Questions question = questionDAO.getById(id);
        List<Answers> listAnswers = answerDAO.getByQuestion(question);
        
        for(Answers x:listAnswers) System.out.println(x.getText());
        
        model.addAttribute("listAnswers", listAnswers);
        model.addAttribute("question", question);

        return "addanswer";
    }

    @RequestMapping(value = "/addquestion", method = RequestMethod.GET)
    public String addQuestion(ModelMap model) {

        List<Category> allCategories = categoryDAO.findAll();

        model.addAttribute("listCategories", allCategories);

        return "addquestion";
    }

    @RequestMapping(value = "/postquestion", method = RequestMethod.GET)
    public String postQuestion(@RequestParam("name") String name, @RequestParam("fulltext") String fulltext, @RequestParam("category") int category) {

        Questions question = new Questions();
        question.setDateadd(new Date());
        question.setFullltext(fulltext);
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

    
    @RequestMapping(value = "/postanswer", method = RequestMethod.GET)
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
        return "redirect:addanswer?id="+question.getId().toString();
    }
    
    
    
    
    
    
    @RequestMapping(value = "/viewprofil", method = RequestMethod.GET)
    public String viewProfil(ModelMap model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = usersDAO.findUserByLogin(username);
        model.addAttribute("user",user);       
        return "viewprofil";
    }

    @RequestMapping(value = "/editprofil", method = RequestMethod.GET)
    public String editProfil(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = usersDAO.findUserByLogin(username);
        model.addAttribute("user",user);
        
        return "editprofil";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String regsuc(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email) throws UnsupportedEncodingException {

        //String p = new String(fio.getBytes("ISO8859-1"), "UTF-8");
        //String orgname = new String(org.getBytes("ISO8859-1"), "UTF-8");
        //String usr = new String(log.getBytes("ISO8859-1"), "UTF-8");
        Users user = new Users();

        user.setUsername(username);
        user.setEmail(email);
        Typeusers typeUser = typeUserDAO.getTypeUserByName("ROLE_USER");
        user.setIdtypeuser(typeUser);
        BCryptPasswordEncoder n = new BCryptPasswordEncoder();
        user.setPassword(n.encode(password));
        usersDAO.create(user);
        return "login";
    }

}
