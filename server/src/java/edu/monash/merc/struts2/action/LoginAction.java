package edu.monash.merc.struts2.action;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author: Simon Yu
 * @email: Xiaoming.Yu@monash.edu
 *
 * Date: 5/01/12
 * Time: 2:01 PM
 * @version: 1.0
 */
@Scope("prototype")
@Controller("user.loginAction")
public class LoginAction extends DMBaseAction {

    private String stringResult;

      private Logger logger = Logger.getLogger(this.getClass().getName());

    public String login() {

        stringResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>Ok</test>";

        return SUCCESS;
    }

    public String getStringResult() {
        return stringResult;
    }

    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
    }
}
