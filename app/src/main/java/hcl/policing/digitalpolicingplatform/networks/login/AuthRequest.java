package hcl.policing.digitalpolicingplatform.networks.login;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.login.LoginRequestDTO;

public class AuthRequest implements Serializable {

    public static LoginRequestDTO loginRequest(String userName, String password, String fcm) {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
//        loginRequest.setUserId("");
//        loginRequest.setUserName(userName);
//        loginRequest.setPassword(password);
//        loginRequest.setFCMTokenCode(fcm);
        return loginRequest;
    }
}
