# -*- coding: utf-8 -*-
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import NoAlertPresentException
import unittest, time, re

class LoginMetalMiningConstruction(unittest.TestCase):


    def setUp(self):
        self.driver = webdriver.Firefox()
        self.driver.implicitly_wait(30)
        self.base_url = "http://it.ogame.gameforge.com"
        self.verificationErrors = []
        self.accept_next_alert = True
	

    
    def test_login_metal_mining_construction(self):
## LOGIN PHASE
	driver = self.driver
        driver.get(self.base_url + "/")
        driver.find_element_by_id("loginBtn").click()
        Select(driver.find_element_by_id("serverLogin")).select_by_visible_text("Fidis")
        driver.find_element_by_id("usernameLogin").clear()
        driver.find_element_by_id("usernameLogin").send_keys("ercole.adeluca@gmail.com")
        driver.find_element_by_id("passwordLogin").clear()
        driver.find_element_by_id("passwordLogin").send_keys("arkos1987?")
        driver.find_element_by_id("loginSubmit").click()


	if(int(driver.find_element_by_id("energy_box").text)<0):
		##if the energy is less than zero I build a solar power plant
		driver.find_element_by_xpath("//ul[@id='menuTable']/li[2]/a/span").click()
		driver.find_element_by_css_selector("div.supply4 > div.buildingimg > #details").click()
		driver.find_element_by_css_selector("a.build-it > span").click()
		driver.find_element_by_css_selector("a.build-it > span").click()
		driver.find_element_by_xpath("//li[@id='button4']/div/div/a[2]").click()
	else:
		## I build an metal mine
		driver.get(self.base_url + "/game/index.php?page=overview")
		driver.find_element_by_xpath("//ul[@id='menuTable']/li[2]/a/span").click()
		driver.find_element_by_id("details").click()
		driver.find_element_by_css_selector("a.build-it > span").click()
    
    def is_element_present(self, how, what):
        try: self.driver.find_element(by=how, value=what)
        except NoSuchElementException, e: return False
        return True
    
    def is_alert_present(self):
        try: self.driver.switch_to_alert()
        except NoAlertPresentException, e: return False
        return True
    
    def close_alert_and_get_its_text(self):
        try:
            alert = self.driver.switch_to_alert()
            alert_text = alert.text
            if self.accept_next_alert:
                alert.accept()
            else:
                alert.dismiss()
            return alert_text
        finally: self.accept_next_alert = True
    
    def tearDown(self):
        self.driver.quit()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
