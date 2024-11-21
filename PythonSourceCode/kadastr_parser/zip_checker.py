import xml.etree.ElementTree as ET
import zipfile

import os
import sqlite3
#tree = ET.parse()
def create_db():
    sql = sqlite3.connect("kadastr_db.sqlite")
    cursor = sql.cursor()
    try:
        cursor.execute("create table kadastr_missed (kadastr text, address text, UNIQUE(kadastr))")
    except Exception as error:
        print(error)
    try:
        cursor.execute("create table kadastr_checked (kadastr text, address text, UNIQUE(kadastr))")
    except Exception as error:
        print(error)
    try:
        cursor.execute("create table all_kadastrs (kadastr text, address text, UNIQUE(kadastr))")
    except Exception as error:
        print(error)

def extract_all(directory:str):
    zip_files = os.listdir(directory)
    for zip_file in zip_files:
        with zipfile.ZipFile("zip_files/"+ zip_file, "r") as file:
            file.extractall(f"{directory}_unzip")
def get_zips(directory:str) -> list:
    zips = [zip_file for zip_file in os.listdir(directory) if zip_file[-3:] == "zip"]
    return zips

def get_xmls(directory:str):
    result_xmls = [xml_file for xml_file in os.listdir(directory) if xml_file[-3:] == "xml"]
    return result_xmls
def parse_xml(directory, file):
    tree = ET.parse(directory + "/" + file)
    return tree.find("room_record/object/common_data/cad_number").text
def write_checked_files(kadastrs_set:set):
    with open("new_kadastr_checked.csv",encoding="cp1251", mode="w") as file_destination:
        with open("new_kadastr.csv", encoding="cp1251", mode="r") as file_source:
            checked_set = set()
            old_lines = file_source.readlines()
            for i in range(len(old_lines)):
                old_lines[i] = old_lines[i].split(";")
                if old_lines[i][1] in kadastrs_set and old_lines[i][1] not in checked_set:
                    print(";".join(old_lines[i]).strip()+";проверено в росеестре",file = file_destination)
                    checked_set.add(old_lines[i][1])
def insert_addresses_in_sql(file_before_reestr:str):
    with open(file_before_reestr, "r") as file:
        sql = sqlite3.connect("kadastr_db.sqlite")
        cursor = sql.cursor()
        lines = file.readlines()
        lines = [line.strip().split(";") for line in lines]
        for line in lines:
            if line[1] != "None":
                try:
                    cursor.execute(f"insert into all_kadastrs values({repr(line[1])}, {repr(line[0])})")
                except sqlite3.IntegrityError as error:
                    print(f"{line[1]} already in table all_kadastrs")
                    print(error)
        cursor.execute("select * from all_kadastrs")
        print(len(cursor.fetchall()))
def compare_rosseestr_result(directory:str):
    os.listdir(directory)

def insert_checked_in_sql():
    pass
def main1():
    extract_all("zip_files")
    result_xml = get_xmls("zip_files_unzip")
    checked_kadastrs = set()
    for file in result_xml:
        checked_kadastrs.add(parse_xml("zip_files_unzip",file))
    all_kadastr_file = open("new_kadastr_03_10_2024.csv.csv", "r", encoding="cp1251")
    lines = all_kadastr_file.readlines()
    kadastrs = set(line.split(";")[1].strip() for line in lines)
    write_checked_files(kadastrs.intersection(checked_kadastrs))
def main2():
    create_db()
    input_file = input("Введите имя файла для вставки в общий список: ")
    insert_addresses_in_sql(input_file)
    # insert_checked_in_sql()
if __name__ == "__main__":
    main2()
    #create_db()

