import os
import sqlite3
import zipfile
import xml.etree.ElementTree as ET


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
def exctract_zip():
    # родительская папка с папками
    main_directory = "dirs_zips"
    # дочерние папки с зипами
    child_dirs = os.listdir(main_directory)
    for directory in child_dirs:
        # зип файлы в одной папке
        zip_files = os.listdir(main_directory+"\\"+directory)
        for zip_file in zip_files:
            #извлекаем каждый зип в папку unzip
            with zipfile.ZipFile(main_directory+"\\"+directory +"\\"+ zip_file, "r") as zip_file:
                zip_file.extractall("unzip")
def delete_non_xml():
    directory = "unzip"
    dir_files = os.listdir(directory)
    for file in dir_files:
        if file[-3:] != "xml":
            os.remove(directory+"\\"+file)
def read_xml():
    directory = "unzip"
    files = os.listdir(directory)
    for file in files:
        tree = ET.parse(directory + "\\" + file)
        insert_into_checked_table(tree.find("room_record/object/common_data/cad_number").text, tree.find("room_record/address_room/address/address/readable_address").text)
def insert_into_checked_table(cad_number:str, address:str):
    sql = sqlite3.connect("kadastr_db.sqlite")
    cursor = sql.cursor()
    cursor.execute("Select * from all_kadastrs")
    fullset = cursor.fetchall()
    kadastr_set = {tup[0] for tup in fullset}
    try:
        if cad_number in kadastr_set:
            cursor.execute(f"insert into kadastr_checked values ({repr(cad_number)}, {repr(address)})")
        else:
            cursor.execute(f"insert into kadastr_missed values ({repr(cad_number)}, {repr(address)})")
    except sqlite3.IntegrityError as error:
        print(error)
    sql.commit()
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
        sql.commit()
        print(len(cursor.fetchall()))
def select_all():
    sql = sqlite3.connect("kadastr_db.sqlite")
    cursor = sql.cursor()
    cursor.execute("select * from kadastr_missed")
    print(f"пропущенные: {len(fetch:=cursor.fetchall())}")
    print(fetch)
    cursor.execute("select * from kadastr_checked")
    print(f"проверенные: {len(fetch:=cursor.fetchall())}")
    print(fetch)
def main():
    #создаем бд
    create_db()
    #вставляем в общую бд
    insert_addresses_in_sql("new_kadastr_15_10_2024.csv")
    #раскрываем зипы
    exctract_zip()
    # удаляем мусор
    delete_non_xml()
    #парсим xml
    read_xml()
if __name__ == "__main__":
    main()
    select_all()
