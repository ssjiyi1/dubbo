package cn.zw.controller;

import cn.zw.controller.common.bean.Result;
import cn.zw.entity.Student;
import cn.zw.service.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import static cn.zw.controller.common.bean.ResultBuild.fail;
import static cn.zw.controller.common.bean.ResultBuild.success;

@Controller
@RequestMapping("/student")
public class LoginController {

    @Autowired
    private IStudentService studentService;

    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    /**
     *  添加一个学生
     * @param student
     * @return
     */
    @RequestMapping("/addStudent")
    @ResponseBody
    public Result addStudent(Student student) {
        try {
            studentService.insertStudent(student);
            return success(student);
        } catch (Exception e) {
            LOGGER.error("添加学生失败",e);
            return fail();
        }

    }



    @RequestMapping("/login")
    @ResponseBody
    public  ModelAndView  login(String name,String pwd){
        ModelAndView mav = new ModelAndView("/student/list");
        try {
          Student student =  studentService.findByName(name);
            if(null==student || !student.getPwd().equals(pwd)){
                mav.setViewName("/student/login");
                mav.addObject("info","用户名或者密码错误");
                return mav;
            }
        }catch (Exception e){
            LOGGER.error("登陆失败",e);
            mav.setViewName("/student/login");
            mav.addObject("info",e.getMessage());
        }
        return mav;
    }


    /**
     *   查询所有的学生
     * @return
     */
    @RequestMapping("/listAll")
    @ResponseBody
    public Result listAll() {
        try {
            return success(studentService.findAll());
        } catch (Exception e) {
            LOGGER.error("获取学生列表出错",e);
            return fail();
        }
    }

    /**
     *  分页查询学生
     * @param page 页码
     * @param pageSize 每页显示的条数
     * @return 分页对象
     */
    @RequestMapping("/page")
    @ResponseBody
    public Result page(@RequestParam("page") int page, @RequestParam("rows") int pageSize) {
        try {
            return success(studentService.pageStudent(page,pageSize));
        } catch (Exception e) {
            LOGGER.error("分页获取获取学生列表出错",e);
            return fail();
        }
    }





    @RequestMapping("/index")
    public String index() {
        return "/student/index";
    }



    @RequestMapping("/login")
    public String login() {
        return "/student/login";
    }





}