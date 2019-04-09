package cn.easybuy.web.pre;

import cn.easybuy.entity.User;
import cn.easybuy.service.user.UserService;
import cn.easybuy.service.user.UserServiceImpl;
import cn.easybuy.utils.*;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/Register"},name = "Register")
public class RegisterServlet extends AbstractServlet {
    private UserService userService;

    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    /**
     * 返回
     *
     * @return
     */
    @Override
    public Class getServletClass() {
        return RegisterServlet.class;
    }

    /**
     * 跳到注册页面
     *
     * @param request
     * @param response
     * @return
     */
    public String toRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/pre/register";
    }

    public ReturnResult doRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取前台传递过来的对象
        //对数据进行验证
        //对密码进行md5加密
        //调用service里面的save方法，对用户进行保存
        ReturnResult result = new ReturnResult();
        try {
            User user = new User();
            String loginName = request.getParameter("loginName");
            User oldUser = userService.getUserByLoginName(loginName);
            //判断oldUser是否为空
            if (EmptyUtils.isNotEmpty(oldUser)) {
                result.returnFail("用户已经存在");
                return result;
            }
            String sex = request.getParameter("sex");
            String password = request.getParameter("password");
            String userName = request.getParameter("userName");
            String identityCode = request.getParameter("identityCode");
            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");
            user.setLoginName(loginName);
            user.setPassword(SecurityUtils.md5Hex(password));
            user.setSex(EmptyUtils.isEmpty(sex) ? 1 : 0);
            user.setIdentityCode(identityCode);
            user.setEmail(email);
            user.setMobile(mobile);
            user.setUserName(userName);
            user.setType(Constants.UserType.PRE);
            result = checkUser(user);
            //判断是否验证通过
            if (result.getStatus() == Constants.ReturnResult.SUCCESS) {
                boolean flag = userService.save(user);
                if (!flag) {
                    return result.returnFail("注册失败");
                } else {
                    return result.returnSuccess("注册成功");
                }
            } else {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            result.returnSuccess("注册成功");
            return result;
    }

    //对手机号码，身份证号和邮箱格式进行校验
    public ReturnResult checkUser(User user) {
        ReturnResult result = new ReturnResult();
        if(EmptyUtils.isEmpty(user.getMobile())){
            if(!RegUtils.checkMobile(user.getMobile())){
                return  result.returnFail("手机格式不正确");
            }
        }

        if(EmptyUtils.isEmpty(user.getIdentityCode())){
            if(!RegUtils.checkIdentityCodeReg(user.getIdentityCode())){
                return  result.returnFail("身份证格式不正确");
            }
        }
        if(EmptyUtils.isEmpty(user.getEmail())){
            if(!RegUtils.checkEmail(user.getEmail())){
                return  result.returnFail("邮箱格式不正确");
            }
        }
        return result.returnSuccess();
    }

}
