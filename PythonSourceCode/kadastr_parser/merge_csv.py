import os
from datetime import datetime
def main():
    now = datetime.now().strftime("%y-%m-%d-%H-%M-%S")
    with open(f"{now}_allKadastr.csv", "w") as file_dst:
        csvs = os.listdir("все")
        for file_src in csvs:
            with open("все\\"+file_src, "r") as file_src:
                for line in file_src.readlines():
                    print(line, file=file_dst, end="")
if __name__  == "__main__":
    main()