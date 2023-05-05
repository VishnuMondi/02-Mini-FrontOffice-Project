package in.vishnu.service;

import in.vishnu.binding.LoginForm;
import in.vishnu.binding.SignUpForm;
import in.vishnu.binding.UnlockForm;

public interface UserService {
	
	public boolean signup(SignUpForm form);
	
	public boolean unlockAccount(UnlockForm form);
		
    public String login(LoginForm form);
    
    public boolean forgotPwd(String email);
    
	}


