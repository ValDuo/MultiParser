import os
import zipfile
import xml.etree.ElementTree as ET
from datetime import date
def exctract_zip():
    # родительская папка с папками
    main_directory = "Скаченные Выписки всего"
    # дочерние папки с зипами
    zip_files = os.listdir(main_directory)
    for zip_file in zip_files:           #извлекаем каждый зип в папку unzip
        with zipfile.ZipFile(main_directory+"\\"+ zip_file, "r") as zip_file:
            zip_file.extractall("unzip13_11")
def delete_non_xml():
    directory = "unzip13_11"
    dir_files = os.listdir(directory)
    for file in dir_files:
        if file[-3:] != "xml":
            os.remove(directory+"\\"+file)

def get_xmls(directory:str):
    result_xmls = [xml_file for xml_file in os.listdir(directory) if xml_file[-3:] == "xml"]
    return result_xmls
def parse_xml(directory, file):
    tree = ET.parse(directory + "/" + file)
    try:
        return tree.find("room_record/object/common_data/cad_number").text
    except AttributeError:
        #return "нежилое"
        return tree.find("build_record/object/common_data/cad_number").text
def get_response_set(path):
    xmls = get_xmls(path)
    response_set = {parse_xml(path,elem) for elem in xmls}
    return response_set
def get_request_set(path):
    # file = "21_10\\csv_21_10_2024-2.csv"
    with open(path, "r") as file:
        lines = file.readlines()
        lines = [line.strip().split(";")[0] for line in lines]
        return set(lines)

def main():
    exctract_zip()
    delete_non_xml()
if __name__ == "__main__":
    main()
    request_set = get_request_set("Кадастровый_адрес в3-9900.csv")
    response_set = get_response_set("unzip13_11")
    #print(request_set)
    print(len(response_set))
    print(len(request_set))

    print(len(response_set.difference(request_set)))
    print("Пошло в россеестр, но не вернулось")
    print(len(request_set.difference(response_set)))
    print(*request_set.difference(response_set), sep="\n", file=open(f"{date.today()} не_вернулось.csv","w"))
    # print(*[x for x in request_set][:5])
    #print(*request_set.difference(response_set),sep="\n")
    print("Вернулось с россеестра")
    print(len(response_set.intersection(request_set)))
    print(*response_set.intersection(request_set), sep="\n", file=open(f"{date.today()} вернулось.csv", "w"))
    # print(*[x for x in response_set][:5])
    #print(*response_set.intersection(request_set),sep="\n")


