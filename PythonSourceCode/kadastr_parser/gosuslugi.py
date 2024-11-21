import time

from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService

# binary_yandex_driver_file = r'C:\Users\d.kostukov\AppData\Local\Yandex\YandexBrowser\Application\browser.exe' # path to YandexDriver
#
# options = webdriver.ChromeOptions()
#
# service = ChromeService(executable_path=binary_yandex_driver_file)
# driver = webdriver.Chrome(service=service)
# driver.get()
driver = webdriver.Chrome()
driver.get()

def main():
    driver.get("https://egrpru.com/")
    time.sleep(10)
    pass

if __name__ == "__main__":
    main()