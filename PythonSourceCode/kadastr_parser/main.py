import time
import re
from datetime import datetime

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import csv
def get_len_of_new_file():
    file = open("new_kadastr_28_10_2024.csv", "r")
    lines = file.readlines()
    file.close()
    return len(lines)
def read_excel(file:str):
    with open(file) as file:
        file = csv.reader(file, delimiter=";")
        return ["Ростовская область, "+line[0] for line in file][2:]
def add_home(addresses:list):
    for index in range(len(addresses)):
        address_split = addresses[index].split(",")
        try:
            pass
            #address_split[3] = "Д." + address_split[3]
        except IndexError:
            pass
        addresses[index] = ", ".join(address_split)
def main():
    src_file=input("Введите файл, откуда получить адреса")
    addresses = read_excel(src)
    add_home(addresses)
    #print(*addresses,sep="\n")

    driver = webdriver.Chrome()
    driver.get("https://egrpru.com/")
    numbers = []
    new_file = open(f"new_kadastr_{datetime.today().strftime("%d-%m-%y")}.csv", "a")
    for i in range(get_len_of_new_file()+1,len(addresses)):
        form_input = driver.find_element(By.ID, "form_search")
        button_input = driver.find_element(By.ID, "btn_search")
        form_input.send_keys(addresses[i])
        #form_input.send_keys("Ростовская область,  х. Жуково-Татарский,  ул. Ленина, Д. 33,  кв 4")
        button_input.click()
        time.sleep(7.5)
        results = driver.find_elements(By.CLASS_NAME, "search-result__row")
        result_text = None
        for result in results:
            #print(result.text)
            if result.text.find("Жилое помещение") > -1:
                kadastr = result.text.find("Адрес объекта:") - len("Кадастровый номер:")
                result_text = result.text[len("Кадастровый номер:"):result.text.find("Адрес объекта:")].strip()
                #print(addresses[i], result_text)
                break
            else:
                pass
                #print(addresses[i], "Не найдено")

        print(addresses[i] + ";" + str(result_text)+";", file=new_file)
        form_input = driver.find_element(By.ID, "form_search")
        form_input.clear()
        print(i)

if __name__ == "__main__":
    main()