package Controller;

import View.AgencyPage;
import View.HomePage;
import View.UserPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController {

    private HomePage home;
    private UserController userController;
    private AgencyController agencyController;

    public HomeController(){
        home = new HomePage();

        home.setButtonAgencyAction(new HomeController.ButtonAgencyAction());
        home.setButtonUserAction(new HomeController.ButtonUserAction());

    }
    /**
     * home page: redirecting to agency page
     */
    private class ButtonAgencyAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            agencyController = new AgencyController(new AgencyPage());
            home.setVisible(false);
        }
    }

    /**
     * home page: redirecting to user page
     */
    private class ButtonUserAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            userController = new UserController(new UserPage());
            home.setVisible(false);
        }
    }

}
