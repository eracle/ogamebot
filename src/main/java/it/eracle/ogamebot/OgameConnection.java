package it.eracle.ogamebot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Represent an active current connection with a specified username and password to a specified Ogame server.
 * Created by eracle on 29/07/15.
 */
public class OgameConnection {

    /** Selenium Driver */
    private WebDriver driver;

    /** Singleton instance of the constructionPlanManager*/
    private ConstructionPlanManager constructionPlanManager;

    /** Connection Status
    private enum status {
        DISCONNECTED, CONNECTED
    }
     */
    //private status connectionStatus;

    /**
     * Constructor which also does the action of login to a specified server, and universe,
     * with a specified username and password.
     * TODO: Finish the documentation after implementation phase. FDAIP
     *
     * @param serverUrl
     * @param universe
     * @param username
     * @param password
     * @return
     */
    public OgameConnection(String serverUrl,  String universe, String username, String password) {
        //class initialization related stuffs
        this.driver = new FirefoxDriver();
        this.constructionPlanManager = null;
        //this.connectionStatus = status.DISCONNECTED;

        //login stuffs
        if(!serverUrl.equals("") && !serverUrl.startsWith("http://"))
            serverUrl="http://"+serverUrl;

        driver.get(serverUrl);
        driver.findElement(By.id("loginBtn")).click();
        driver.findElement(By.id("usernameLogin")).clear();
        driver.findElement(By.id("usernameLogin")).sendKeys(username);
        driver.findElement(By.id("passwordLogin")).clear();
        driver.findElement(By.id("passwordLogin")).sendKeys(password);

        new Select(driver.findElement(By.id("serverLogin"))).selectByVisibleText(universe);

        driver.findElement(By.id("loginSubmit")).click();

        //old check if the password was wrong
        /*
        try{
            if(driver.findElement(By.cssSelector("div.icon")).getText().equals("Your username or password is wrong!")) {
                this.close();
                throw new IllegalArgumentException("Your username or password is wrong!");
            }
        }catch(org.openqa.selenium.NoSuchElementException e ){
            //this.connectionStatus = status.CONNECTED;
            //TODO: Logging a successful login
            //TODO: A better handling of the exceptions flow
        }
        */
    }


    /**
     * Return the quantity of the metal resource owned.
     * @return Quantity of metal owned.
     */
    public int getMetal(){
        return Integer.parseInt(driver.findElement(By.id("resources_metal")).getText().replace(".",""));
    }

    /**
     * Return the quantity of the crystal resource owned.
     * @return Quantity of crystal owned.
     */
    public int getCrystal(){
        return Integer.parseInt(driver.findElement(By.id("resources_crystal")).getText().replace(".", ""));
    }

        /**
         * Return the quantity of the deuterium resource owned.
         * @return Quantity of deuterium owned.
         */
     public int getDeuterium(){
         return Integer.parseInt(driver.findElement(By.id("resources_deuterium")).getText().replace(".", ""));
     }

    /**
     * Return the quantity of the energy resource owned.
     * @return Quantity of metal owned.
     */
    public int getEnergy(){
        return Integer.parseInt(driver.findElement(By.id("resources_energy")).getText().replace(".",""));
    }

    /**
     * Close the connection.
     */
    public void close(){
        driver.quit();
        //this.connectionStatus = status.DISCONNECTED;
    }

    /**
     * Check if a mine can be build. Return true if it can, false otherwise.
     * @return  True if the mine can be build, false otherwise.
     */
    public boolean canBuildMetalMine() {
        //TODO: the method return true also if the mine is not buildable
        driver.findElement(By.linkText("Resources")).click();
        driver.findElement(By.id("details")).click();

        //I need to wait for the element a.build-it > span
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.build-it > span")));


        String buildStr = myDynamicElement.getText().trim();
        //System.out.print(buildStr);
        return buildStr.equals("Improve");
    }


    /**
     * Build a mine if it can be build.
     */
    public void buildMetalMine() {
       //TODO: finish the doc and implementation
    }




    /**
     * Return true if the construction queue is empty.
     * @return
     */
    public boolean isEmptyConstructionQueue() {
        //TODO: implementation and docs
        return true;
    }

    /**
     * Returns the singleton instance of the ConstructionPlanManager.
     * @return the singleton instance of the ConstructionPlanManager.
     */
    public ConstructionPlanManager getConstructionPlanManager() {
        if(this.constructionPlanManager==null)
            this.constructionPlanManager = new ConstructionPlanManager(this.driver);
        return this.constructionPlanManager;
    }
}

