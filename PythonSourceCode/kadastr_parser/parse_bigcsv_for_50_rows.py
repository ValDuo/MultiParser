from datetime import datetime
import os

def main():
    today = datetime.now().strftime("%y-%m-%d-%H-%M-%S")
    source = input("Введите название файла источника")
    dst_folder_name = "csv50_" + today
    try:
        os.mkdir(dst_folder_name)
    except:
        print(f"папка {"csv50_"+today} уже существует")
    with open(source, "r") as source_file:
        count50 = 0
        lines = source_file.readlines()
        # minimum = float("+inf")
        # maximum = float("-inf")
        count_files = 1
        dst_file = open(dst_folder_name+"\\"+"csv50_"+today+"_"+str(count_files)+".csv", "w")
        for line in lines:
            if count50 == 50:
                count50 = 0
                count_files+=1
                dst_file.close()
                dst_file = open(dst_folder_name+"\\"+"csv50_"+today+"_"+str(count_files)+".csv", "w")
            line = line.strip()
            #minimum,maximum = min(len(line.split(";")[0]), minimum), max(maximum,len(line.split(";")[0]))
            if len(line.split(";")[0]) == 0:
                break
            print(line, file=dst_file)
            count50+=1
        # print(minimum, maximum)
        # print(len(lines))



if __name__ == "__main__":
    main()