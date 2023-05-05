package in.vishnu.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vishnu.binding.LoginForm;
import in.vishnu.binding.SignUpForm;
import in.vishnu.binding.UnlockForm;
import in.vishnu.entity.UserDtlsEntity;
import in.vishnu.repo.UserDtlsRepo;
import in.vishnu.util.EmailUtils;
import in.vishnu.util.PwdUtils;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserDtlsRepo userDtlsRepo;

	@Autowired
	public EmailUtils emailUtils;
	
	@Autowired
	public HttpSession session;

	// @Override
	public boolean signup(SignUpForm form) {
		UserDtlsEntity user = userDtlsRepo.findByEmail(form.getEmail());

		if (user != null) {
			return false;
		}

		// copy data form binding obj to entity obj
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);

		// generate random pwd and set to object
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);

		// set account status as locked
		entity.setAccStatus("Locked");

		// insert record
		userDtlsRepo.save(entity);

		// send email to unlock the account
		String to = form.getEmail();
		String subject = "Unlock your Account : Ashok IT";
		StringBuffer body = new StringBuffer("");
		body.append("<h1> Use below temporary pwd to unlock your account</h1>");

		body.append("Temparary pwd : " + tempPwd);

		body.append("<br/>");

		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click Here To Unlock Your Account</a>");

		emailUtils.sendEmail(to, subject, body.toString());

		return true;
	}

	// @Override
	public boolean unlockAccount(UnlockForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			userDtlsRepo.save(entity);
			return true;

		} else {
			return false;
		}

	}

	public String login(LoginForm form) {

		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());

		if (entity == null) {
			return "Invalid Credentials";
		}
		if (entity.getAccStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}
      
		//create session and store user data in session
		session.setAttribute("userId",entity.getUserId());
		
		
		return "success";
	}

	public boolean forgotPwd(String email) {

		// check record present in db with givem mail
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);

		// if record not availabe send error msg
		if (entity == null) {
			return false;
		}

		// if record available send pwd to email and send success msg
		String Subject = "Recover Password";
		String body = "Your Pwd :: " + entity.getPwd();

		emailUtils.sendEmail(email, Subject, body);

		return true;
	}

}
