import xml.etree.ElementTree as ET
import os
def get_xmls(directory:str):
    result_xmls = [xml_file for xml_file in os.listdir(directory) if xml_file[-3:] == "xml"]
    return result_xmls
def parse_xml_kadastr(directory,file):
    tree = ET.parse(directory + "/" + file)
    try:
        return tree.find("room_record/object/common_data/cad_number").text
    except AttributeError:
        #return "нежилое"
        return tree.find("build_record/object/common_data/cad_number").text
def parse_xml_adrress(directory,file):
    tree = ET.parse(directory + "/" + file)
    try:
        return tree.find("room_record/address_room/address/address/readable_address").text
    except AttributeError:
        # return "нежилое"
        return tree.find("build_record/address_location/address/readable_address").text
def parse_xml_owner(directory, file):
    tree = ET.parse(directory + "/" + file)
    try:
        surname = tree.find("right_records/right_record/right_holders/right_holder/individual/surname").text
        name = tree.find("right_records/right_record/right_holders/right_holder/individual/name").text
        patronymic = tree.find("right_records/right_record/right_holders/right_holder/individual/patronymic").text
    except AttributeError:
        surname = None
        name = None
        patronymic = None
    return (str(elem) for elem in [surname,name,patronymic])
def main():
    directory = "unzip"
    with open("30_10_2024_owner.csv", "w") as file:
        xmls = os.listdir(directory)
        for xml_file in xmls:
            print(parse_xml_kadastr(directory,xml_file),parse_xml_adrress(directory,xml_file),*parse_xml_owner(directory,xml_file), file=file, sep=";")
    #print(*parse_xml_owner("unzip","report-0bca9ab7-9139-49ef-aeef-d16c52bf9175-OfSite-2024-10-08-568660-61-01[0].xml"))
    #print(parse_xml_kadastr("unzip","report-0bca9ab7-9139-49ef-aeef-d16c52bf9175-OfSite-2024-10-08-568660-61-01[0].xml"))

if __name__ == "__main__":
    main()