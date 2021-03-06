package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.service.AccountService;
import com.example.demo.utility.RoleType;
import com.example.demo.model.LoginInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api(tags = "Dashboard account management")
@RestController
@RequestMapping(value = "dashaccount")
public class DashboardAccountController {
    @Autowired
    AccountService accountService;

    @ApiOperation("api to add a user - UserType: 0 = customer, 1= admin; Gender: 0 = Male, 1 = Female")
    @PostMapping("/user")
    /**
     * This method adds an user;
     * @param user user information;
     * @return message "User registered successfully".
     */

    public Object add(@RequestBody User user) throws Exception {

        accountService.add(user);
        return new ResponseEntity("User registered successfully", HttpStatus.OK);
    }


    @ApiOperation(value = "api to get a user by username", notes = "", response = User.class)
    @GetMapping("/user/name/{name}")
    /**
     * This method gets a user information by username.
     * @param name username.
     * @return user information.
     */
    public User getByUserName(@PathVariable("name") String name) throws Exception {
        User user = accountService.getByUserName(name);
        return user;
    }

    @ApiOperation(value = "api to login")
    @PostMapping("/user/login")
    /**
     * This method determines whether the username and password are correct for login.
     * @param loginInfo the input username and password.
     * @return related user or show message"Credential not valid".
     */
    public Object login(@RequestBody LoginInfo loginInfo) throws Exception {
        User UserModel = accountService.login(loginInfo.getUsername(), loginInfo.getPassword(), RoleType.Admin.name());
        if (UserModel == null) {
            return new ResponseEntity("Credential not valid", HttpStatus.FORBIDDEN);

        }
        return UserModel;

    }
}
